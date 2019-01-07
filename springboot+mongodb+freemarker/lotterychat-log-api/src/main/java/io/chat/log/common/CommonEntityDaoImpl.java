package io.chat.log.common;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.result.DeleteResult;

import io.chat.log.util.MongoQueryUtil;
import io.chat.log.vo.CustmerCriteria;
import io.chat.log.vo.PageResult;
import net.minidev.json.JSONObject;

public class CommonEntityDaoImpl<T> implements ICommonEntityDao<T>{

	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Override
	public void insertOne(T t){
		Assert.notNull(t,"The parameter must not be null !");
		mongoTemplate.save(t);
	}
	
	@Override
	public void insertMany(List<T> list) throws Exception {
		Assert.notNull(list,"The parameter must not be null !");
		list.forEach(t->{
			mongoTemplate.save(t);
		});
	}
	
	public T findOne(Map<String, CustmerCriteria> params,Class<T> t) throws Exception {
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
	public List<T> findMany(Map<String, CustmerCriteria> params,Class<T> t) throws Exception {
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		 List<T> result = (List<T>) mongoTemplate.find(query,t);
		 return result;
	}
	
	@Override
	public  PageResult<T> page(Map<String, CustmerCriteria> params, Integer currentPage,
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
	public long delete(Map<String, CustmerCriteria> params,Class<T> t) throws Exception {
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		DeleteResult deleteResult = mongoTemplate.remove(query, t);
		return deleteResult.getDeletedCount();
	}

	@Override
	public void drop(Class<T> entityClass) throws Exception {
		mongoTemplate.dropCollection(entityClass);
	}

	@Override
	public T update(Map<String, CustmerCriteria> params,T t,Class<T> entityClass) throws Exception {
		Update update = new Update();
		JSONObject jsonObject = (JSONObject) JSON.toJSON(t);
		jsonObject.forEach((key,value)->{
			update.set(key, value);
		});
		Query query =  MongoQueryUtil.buildQuery(new Query(),params);
		return mongoTemplate.findAndModify(query, update, entityClass);
	}
	
	
}
