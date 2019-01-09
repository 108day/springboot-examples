package io.chat.log.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;

import io.chat.log.util.MongoQueryUtil;
import io.chat.log.vo.CustmerCriteria;
import io.chat.log.vo.PageResult;
import net.minidev.json.JSONObject;

public class CommonEntityDaoImpl implements ICommonEntityDao{

	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Override
	public <T> void insertOne(T t){
		Assert.notNull(t,"The parameter must not be null !");
		mongoTemplate.save(t);
	}
	
	@Override
	public <T> void insertMany(List<T> list) throws Exception {
		Assert.notNull(list,"The parameter must not be null !");
		list.forEach(t->{
			mongoTemplate.save(t);
		});
	}
	
	public <T> T findOne(Map<String, CustmerCriteria> params,Class<T> t) throws Exception {
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		List<T> result = mongoTemplate.find(query,t);
		if(result == null || result.size() == 0) {
			return null;
		}else if(result.size() == 1) {
			return result.get(0);
		}else {
			throw new Exception("查询到多条数据！但是只要求一条数据！请检查输入参数！");
		}
	}
	
	@Override
	public <T> List<T> findMany(Map<String, CustmerCriteria> params,Class<T> t) throws Exception {
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		 List<T> result = (List<T>) mongoTemplate.find(query,t);
		 return result;
	}
	
	@Override
	public  <T> PageResult<T> page(Map<String, CustmerCriteria> params, Integer currentPage,
			Integer pageSize, Class<T> t) throws Exception {
		Assert.notNull(currentPage,"The parameter currentPage of method page must not be null !");
		Assert.notNull(pageSize,"The parameter pageSize of method page must not be null !");
		Query query = MongoQueryUtil.buildQuery(new Query(),params);
		
		long totalCount =  mongoTemplate.count(query, t);
		if(totalCount == 0) {
			return null;
		}
		 
		query.skip((currentPage-1)*pageSize).limit(pageSize); //分页
		List<T> list = (List<T>) mongoTemplate.find(query,t);
		 
		return new PageResult<T>(list,totalCount);
	}
	
	@Override
	public <T> long delete(Map<String, CustmerCriteria> params,Class<T> t) throws Exception {
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		DeleteResult deleteResult = mongoTemplate.remove(query, t);
		return deleteResult.getDeletedCount();
	}

	@Override
	public <T> void drop(Class<T> entityClass) throws Exception {
		mongoTemplate.dropCollection(entityClass);
	}

	@Override
	public <T> T update(Map<String, CustmerCriteria> params,T t,Class<T> entityClass) throws Exception {
		Update update = new Update();
		JSONObject jsonObject = (JSONObject) JSON.toJSON(t);
		jsonObject.forEach((key,value)->{
			update.set(key, value);
		});
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		return mongoTemplate.findAndModify(query, update, entityClass);
	}
	
	/************************************/
	@Override
	public void insertOne(String collectionName,Map<String,Object> map) throws Exception  {
		Assert.notNull(map,"The parameter database of method insertOne must not be null !");
		Assert.notNull(collectionName,"The parameter collectionName of method insertOne must not be null !");
		MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
		collection.insertOne(new Document(map));
	}
	
	@Override
	public void insertMany(String collectionName,List<Map<String,Object>> list) throws Exception  {
		Assert.notNull(list,"The parameter list of method insertMany must not be null !");
		Assert.notNull(collectionName,"The parameter collectionName of method insertMany must not be null!");
		MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
		List<Document> documents= new ArrayList<Document>(list.size());
		list.forEach(map -> {
			documents.add(new Document(map));
		});
		collection.insertMany(documents);
	}

	@Override
	public Object page(String collectionName,Map<String,CustmerCriteria> params, Integer currentPage,Integer pageSize) throws Exception  {
		Assert.notNull(currentPage,"The parameter currentPage of method page must not be null !");
		Assert.notNull(pageSize,"The parameter pageSize of method page must not be null !");
		Assert.notNull(collectionName,"The parameter collectionName of method insertMany must not be null!");
		
		Query query = MongoQueryUtil.buildQuery(new Query(),params);
		
		long totalCount =  mongoTemplate.count(query,collectionName);
		if(totalCount == 0) {
			return null;
		}
		
		query.skip((currentPage-1)*pageSize).limit(pageSize); //分页
		return new PageResult<>(mongoTemplate.find(query,Object.class,collectionName),totalCount);
	}
	@Override
	public Object findMany(String collectionName,Map<String, CustmerCriteria> params) throws Exception{
		Assert.notNull(collectionName,"The parameter findMany of method insertMany must not be null!");
		Assert.notNull(params,"The parameter 'currentPage' of method findMany must not be null !");
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		 return mongoTemplate.find(query,Object.class,collectionName);
	}
	@Override
	public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn,Integer currentPage,Integer pageSize) throws Exception{
		Assert.notNull(collectionName,"The parameter findMany of method insertMany must not be null!");
		Assert.notNull(params,"The parameter 'currentPage' of method findMany must not be null !");
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		if(sortColumn!=null) {
			 query.with(Sort.by(Direction.DESC,sortColumn)); //排序
		 }else {
			 query.with(Sort.by(Direction.DESC,"_id")); //排序
		 }
		query.skip((currentPage-1)*pageSize).limit(pageSize); //分页
		return mongoTemplate.find(query,Object.class,collectionName);
	}

	@Override
	public long delete(String collectionName,Map<String,CustmerCriteria> params) throws Exception{
		Assert.notNull(collectionName,"The parameter collectionName of method delete must not be null!");
		Assert.notNull(params,"The parameter key of method delete must not be null !");
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		DeleteResult deleteResult = mongoTemplate.remove(query,collectionName);
		return deleteResult.getDeletedCount();
	}

	@Override
	public void drop(String collectionName) throws Exception {
		Assert.notNull(collectionName,"The parameter collectionName of method insertMany must not be null!");
		mongoTemplate.dropCollection(collectionName);
	}

	@Override
	public void update(String collectionName,Map<String, CustmerCriteria> params,Map<String,Object> document) throws Exception {
		Assert.notNull(collectionName,"The parameter collectionName of method update must not be null!");
		Assert.notNull(document,"The parameter document of method update must not be null!");
		Update update = new Update();
		document.forEach((key,value)->{
			update.set(key, value);
		});
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		mongoTemplate.updateFirst(query, update, collectionName);
	}
	
	
}
