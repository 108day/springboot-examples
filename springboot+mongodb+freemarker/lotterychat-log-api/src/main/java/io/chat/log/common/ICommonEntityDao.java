package io.chat.log.common;

import java.util.List;
import java.util.Map;

import io.chat.log.vo.CustmerCriteria;
import io.chat.log.vo.PageResult;

public interface ICommonEntityDao {

	/**
     * 创建对象
     * @param user
     */
    public <T> void insertOne(T entityClass) throws Exception ;
    public <T> void insertMany(List<T> list) throws Exception ;
    
    public <T> T findOne(Map<String, CustmerCriteria> params,Class<T> t) throws Exception ;
    /**
     * 根据条件查询集合内文档列表
     * @Description: TODO(用一句话描述该文件做什么)
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    public <T> List<T> findMany(Map<String, CustmerCriteria> params,Class<T> entityClass) throws Exception;
    /**
     * 分页查询
     * @param params 带表达式
     * @param currentPage
     * @param pageSize
     * @param t 最终返回的对象，以及查询的集合对象
     * @return PageResult<T>
     * @throws Exception
     */
    public <T> PageResult<T> page(Map<String,CustmerCriteria> params, Integer currentPage,Integer pageSize,Class<T> entityClass) throws Exception ;
    
  
    
    /**
     * 根据条件删除集合中文档
     * @param  entityClass java对象类型
     * @param query mongo查询条件组织
     * @Description: TODO(根据条件删除集合中文档)
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    public <T> long delete(Map<String,CustmerCriteria> query,Class<T> entityClass) throws Exception;
    
    /**
     * 删除
     * @param  entityClass java对象类型
     * @Description: TODO(删除整个集合)
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    public <T> void drop(Class<T> entityClass) throws Exception ;

    /**
     * @param entityClass 更新对象的java类型
     * @param t 更新的POJO
     * @param query mongo查询条件组织
     * @Description: TODO(用一句话描述该文件做什么)
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    public <T> T update(Map<String, CustmerCriteria> query,T t,Class<T> entityClass) throws Exception ;
    
    
    /*******************************/
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
    public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn,Integer currentPage,Integer pageSize) throws Exception;
    public long delete(String collectionName,Map<String,CustmerCriteria> params) throws Exception;
    
    public void drop(String collectionName) throws Exception ;

    public void update(String collectionName,Map<String, CustmerCriteria> query,Map<String,Object> document) throws Exception ;
}
