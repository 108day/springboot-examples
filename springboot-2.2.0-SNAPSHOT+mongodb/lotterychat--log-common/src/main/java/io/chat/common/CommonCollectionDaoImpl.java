package io.chat.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;

import io.chat.common.util.MongoQueryUtil;
import io.chat.common.vo.CustmerCriteria;
import io.chat.common.vo.PageResult;


public abstract class CommonCollectionDaoImpl implements ICommonDao{

	@Autowired
    private MongoTemplate mongoTemplate;

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
	public <T> void insertOne(T entityClass) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void insertMany(List<T> list) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T findOne(Map<String, CustmerCriteria> params, Class<T> t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findMany(Map<String, CustmerCriteria> params, Class<T> entityClass) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> PageResult<T> page(Map<String, CustmerCriteria> params, Integer currentPage, Integer pageSize,
			Class<T> entityClass) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> long delete(Map<String, CustmerCriteria> query, Class<T> entityClass) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> void drop(Class<T> entityClass) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T update(Map<String, CustmerCriteria> query, T t, Class<T> entityClass) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findMany(String collectionName, Map<String, CustmerCriteria> params, String sortColumn,
			Integer currentPage, Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findMany(String collectionName, Map<String, CustmerCriteria> params, String sortColumn,
			Direction sort, Integer currentPage, Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOrSave(String collectionName, Map<String, CustmerCriteria> query, Map<String, Object> document)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
