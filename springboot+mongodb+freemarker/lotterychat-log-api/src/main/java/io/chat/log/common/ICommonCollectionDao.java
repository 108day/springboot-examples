package io.chat.log.common;

import java.util.List;
import java.util.Map;

import io.chat.log.vo.CustmerCriteria;

public interface ICommonCollectionDao {

	/**
     * 创建对象
     * @param user
     */
    public void insertOne(String collectionName,Map<String,Object> map) throws Exception ;
    public void insertMany(String collectionName,List<Map<String,Object>> document) throws Exception ;
    /**
     * 分页查询
     * @param params 带表达式
     * @param currentPage
     * @param pageSize
     * @param t 最终返回的对象，以及查询的集合对象
     * @return PageResult<T>
     * @throws Exception
     */
    public Object page(String collectionName,Map<String,CustmerCriteria> params, Integer currentPage,Integer pageSize) throws Exception ;
    
    public Object findMany(String collectionName,Map<String, CustmerCriteria> params) throws Exception;
    
    public long delete(String collectionName,Map<String,CustmerCriteria> params) throws Exception;
    
    public void drop(String collectionName) throws Exception ;

    public void update(String collectionName,Map<String, CustmerCriteria> query,Map<String,Object> document) throws Exception ;

}
