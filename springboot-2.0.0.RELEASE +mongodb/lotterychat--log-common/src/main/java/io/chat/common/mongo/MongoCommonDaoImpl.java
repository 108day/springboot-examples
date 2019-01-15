package io.chat.common.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexField;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import io.chat.common.validator.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import com.mongodb.client.result.DeleteResult;

import io.chat.common.vo.CustmerCriteria;
import io.chat.common.vo.PageResult;

public abstract class MongoCommonDaoImpl implements IMongoCommonDao{

	@Autowired
    private MongoTemplate mongoTemplate;
	
	protected List<String> getUniqueIndex(String collectionName) {
		Assert.isNull(collectionName,"The parameter 'query'  must not be null !");
		List<String> result =new ArrayList<>();
		IndexOperations operations = mongoTemplate.indexOps(collectionName);
		List<IndexInfo> list = operations.getIndexInfo();
		for(int i =0 ;i<list.size();i++) {
			IndexInfo indexInfo = list.get(i);
			if(indexInfo.isUnique()) {
				List<IndexField> fieldList = indexInfo.getIndexFields();
				fieldList.forEach(field->{
					result.add(field.getKey());
				});
			}
		}
		return result;
	} 
	
	/**
	 * 根据Map构建查询语句
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	protected Query buildQuery(Query query,Map<String,CustmerCriteria> params) {
		Assert.isEmpty(query,"The parameter 'query'  must not be empty !");
		if(params != null ) {
			params.forEach((key,custmerCriteria)->{
				String express = custmerCriteria.getExpress();
				 Object value = custmerCriteria.getValue();
				 Assert.isNull(key,"The parameter key of params must not be null !");
				 Assert.isNull(express,"The parameter CustmerCriteria.express of "+key+" must not be null !");
				 Assert.isNull(value,"The parameter CustmerCriteria.value of "+key+" must not be null !");
				 if("=".equals(express)) {
					 query.addCriteria(Criteria.where(key).is(value));//查询条件
				 }else if(">=".equals(express)) {
					 query.addCriteria(Criteria.where(key).gte(value));
				 }else if("<=".equals(express)) {
					 query.addCriteria(Criteria.where(key).lte(value));
				 }else if(">".equals(express)) {
					 query.addCriteria(Criteria.where(key).gt(value));//查询条件
				 }else if("<".equals(express)) {
					 query.addCriteria(Criteria.where(key).lt(value));//查询条件
				 }else if("like".equals(express)) {
					 query.addCriteria(Criteria.where(key).regex(custmerCriteria.getValue().toString()));
				 }else if("between".equals(express)) {
					 if(value instanceof Long []) {
						 Long [] list= (Long[])value ;
						 if(list.length != 2) {
							 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" length must be 2 !");
						 }
						 if(list[0] > list[1]) {
							 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" first  must be bigger than second !");
						 }
						 query.addCriteria(Criteria.where(key).gte(list[0]).lte(list[1]));
					 }else {
						 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" format must be array !");
					 }
				 }else if("in".equals(express)) {
					 if(value instanceof String [] || value instanceof List ) {
						 query.addCriteria(Criteria.where(key).in(value));//查询条件
					 }else {
						 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" format must be array !");
					 }
				 }
			 });
		}
		return query;
	}
	
	@Override
	public <T> void insertOne(T t){
		Assert.isNull(t,"The parameter must not be null !");
		mongoTemplate.save(t);
	}
	
	@Override
	public <T> void insertMany(List<T> list) throws Exception {
		Assert.isEmpty(list,"The parameter must not be empty !");
		list.forEach(t->{
			mongoTemplate.save(t);
		});
	}
	
	public <T> T findOne(Map<String, CustmerCriteria> params,Class<T> t) throws Exception {
		Query query =  buildQuery(new Query(),params);
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
		Query query =  buildQuery(new Query(),params);
		 List<T> result = (List<T>) mongoTemplate.find(query,t);
		 return result;
	}
	
	@Override
	public  <T> PageResult<T> page(Map<String, CustmerCriteria> params, Integer currentPage,
			Integer pageSize, Class<T> t) throws Exception {
		Assert.isNull(currentPage,"The parameter currentPage of method page must not be null !");
		Assert.isNull(pageSize,"The parameter pageSize of method page must not be null !");
		Query query = buildQuery(new Query(),params);
		
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
		Assert.isEmpty(params,"The parameter params must not be empty !");
		Query query =  buildQuery(new Query(),params);
		DeleteResult deleteResult = mongoTemplate.remove(query, t);
		return deleteResult.getDeletedCount();
	}

	@Override
	public <T> void drop(Class<T> entityClass) throws Exception {
		mongoTemplate.dropCollection(entityClass);
	}

	@Override
	public <T> T update(Map<String, CustmerCriteria> params,T t,Class<T> entityClass) throws Exception {
		Assert.isEmpty(params,"The parameter params  must not be empty !");
		Assert.isNull(t,"The parameter t must not be null !");
		Update update = new Update();
		JSONObject jsonObject = (JSONObject) JSON.toJSON(t);
		jsonObject.forEach((key,value)->{
			update.set(key, value);
		});
		Query query =  buildQuery(new Query(),params);
		return mongoTemplate.findAndModify(query, update, entityClass);
	}
	
	/************************************/
	@Override
	public void insertOne(String collectionName,Object obj) throws Exception  {
		
		Assert.isNull(collectionName,"The parameter collectionName must not be null !");
		Assert.isEmpty(obj,"The parameter map must not be null !");
		JSONObject jsonObject=(JSONObject)JSON.toJSON(obj);
		mongoTemplate.save(jsonObject, collectionName);
	}
	
	@Override
	public void insertMany(String collectionName,List<?> list) throws Exception  {
		
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		Assert.isEmpty(list,"The parameter list must not be empty !");
		
		list.forEach(obj->{
			JSONObject jsonObject=(JSONObject)JSON.toJSON(obj);
			mongoTemplate.save(jsonObject, collectionName);
		});
	}

	@Override
	public Object page(String collectionName,Map<String,CustmerCriteria> params, Integer currentPage,Integer pageSize) throws Exception  {
		
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		Assert.isNull(currentPage,"The parameter currentPage must not be null !");
		Assert.isNull(pageSize,"The parameter pageSize must not be null !");
		
		Query query = buildQuery(new Query(),params);
		
		long totalCount =  mongoTemplate.count(query,collectionName);
		if(totalCount == 0) {
			return null;
		}
		
		query.skip((currentPage-1)*pageSize).limit(pageSize); //分页
		return new PageResult<>(mongoTemplate.find(query,Object.class,collectionName),totalCount);
	}
	@Override
	public Object findMany(String collectionName,Map<String, CustmerCriteria> params) throws Exception{
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		Query query =  buildQuery(new Query(),params);
		return mongoTemplate.find(query,Object.class,collectionName);
	}
	@Override
	public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn,Integer currentPage,Integer pageSize) throws Exception{
		Assert.isNull(collectionName,"The parameter 'collectionName' must not be null!");
		Assert.isNull(currentPage,"The parameter 'currentPage' must not be null !");
		Assert.isNull(pageSize,"The parameter 'pageSize' must not be null !");
		Query query =  buildQuery(new Query(),params);
		if(sortColumn!=null) {
			 query.with(Sort.by(Direction.DESC,sortColumn)); //排序
		 }else {
			 query.with(Sort.by(Direction.DESC,"_id")); //排序
		 }
		query.skip((currentPage-1)*pageSize).limit(pageSize); //分页
		return mongoTemplate.find(query,Object.class,collectionName);
	}
	@Override
	public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn,Sort.Direction sort ,Integer currentPage,Integer pageSize) throws Exception{
		Assert.isNull(collectionName,"The parameter 'collectionName' must not be null!");
		Assert.isNull(currentPage,"The parameter 'currentPage' must not be null !");
		Assert.isNull(pageSize,"The parameter 'pageSize' must not be null !");
		
		Query query =  buildQuery(new Query(),params);
		if(sortColumn!=null) {
			 query.with(Sort.by(sort,sortColumn)); //排序
		 }else {
			 query.with(Sort.by(sort,"_id")); //排序
		 }
		query.skip((currentPage-1)*pageSize).limit(pageSize); //分页
		return mongoTemplate.find(query,Object.class,collectionName);
	}
	
	@Override
	public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn) throws Exception{
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		Query query =  buildQuery(new Query(),params);
		if(sortColumn!=null) {
			 query.with(Sort.by(Direction.DESC,sortColumn)); //排序
		 }else {
			 query.with(Sort.by(Direction.DESC,"_id")); //排序
		 }
		return mongoTemplate.find(query,Object.class,collectionName);
	}
	/**
	 * 删除数据必须要加参数，如果要删除全部的话，可以直接drop整个集合
	 */
	@Override
	public long delete(String collectionName,Map<String,CustmerCriteria> params) throws Exception{
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		Assert.isEmpty(params,"The parameter params must not be null !");
		Query query =  buildQuery(new Query(),params);
		DeleteResult deleteResult = mongoTemplate.remove(query,collectionName);
		return deleteResult.getDeletedCount();
	}

	@Override
	public void drop(String collectionName) throws Exception {
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		mongoTemplate.dropCollection(collectionName);
	}

	@Override
	public void updateOne(String collectionName,Map<String, CustmerCriteria> params,Object obj) throws Exception {
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		Assert.isEmpty(obj,"The parameter document must not be empty !");
		Assert.isEmpty(params,"The parameter document must not be empty !");
		Update update = new Update();
		JSONObject document = (JSONObject) JSON.toJSON(obj);
		document.forEach((key,value)->{
			update.set(key, value);
		});
		Query query =  buildQuery(new Query(),params);
		mongoTemplate.updateFirst(query, update, collectionName);
	}
	
	@Override
	public void updateOrSave(String collectionName,Map<String, CustmerCriteria> params,Object obj) throws Exception {
		Assert.isNull(collectionName,"The parameter collectionName must not be null!");
		Assert.isEmpty(obj,"The parameter document must not be empty!");
		Query query =  buildQuery(new Query(),params);
		JSONObject document = (JSONObject)obj;
 		List<Map<String,Object>> list = (List<Map<String,Object>>) findMany(collectionName,params);
 		if(list ==null || list.size() == 0) {
 			insertOne(collectionName, document);
 		}else if(list.size() == 1) {
 			Update update = new Update();
			document.forEach((key,value)->{
				update.set(key, value);
			});
			mongoTemplate.updateFirst(query, update, collectionName);
 		}else {
 			for(int i =0;i<list.size();i++) {
 				if(list.get(i) instanceof Map) {
 					Map<String,Object>  map = (Map<String,Object>)list.get(i);
 	 				Update update = new Update();
 	 				document.forEach((key,value)->{
 	 					update.set(key, value);
 	 				});
 	 				Query queryOne = new Query().addCriteria(Criteria.where("_id").is(map.get("_id")));
 	 				mongoTemplate.updateFirst(queryOne, update, collectionName);
 				}else {
 					throw new Exception("返回对象类型错误！");
 				}
 			}
 		}
	}
	
	/**
	 * 根据唯一索引，或者_ID修改
	 */
	public void updatMany(String collectionName,List<?> list) throws Exception {
		Assert.isNull(collectionName,"The parameter update must not be null!");
		Assert.isEmpty(list,"The parameter list must not be empty!");
		/*查询唯一主键*/
		List<String> indexList = getUniqueIndex(collectionName);
		/*要插入的数据列表*/
		for(Object obj:list) {
			JSONObject jsonObject= (JSONObject)obj;
			//将要修改的数据放到Update
			Update update = new Update();
			jsonObject.forEach((key,value)->{
					update.set(key, value);
			});
			Map<String, CustmerCriteria> params = new HashMap<>();
			/*要插入的数据列表，必须带上_id，或者唯一主键，如果MongoDB集合中没有创建唯一主键，那就根据_id来更新*/
			if(indexList==null || indexList.size()==0) {//mongodb集合没有创建唯一主键
				params.put("_id", new CustmerCriteria("=",jsonObject.get("_id")));
			}else {
				for(String index:indexList) {
					params.put(index, new CustmerCriteria("=",jsonObject.get(index)));
				}
			}
			//根据_id或者唯一索引查询，然后更新第一个对象
			Query queryOne =  buildQuery(new Query(),params);
			mongoTemplate.updateFirst(queryOne, update, collectionName);
		}
		
	}
}
