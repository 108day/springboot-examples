package io.chat.common.mongo;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import io.chat.common.vo.CustmerCriteria;
import io.chat.common.vo.PageResult;

public interface IMongoCommonDao {

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
    public void insertOne(String collectionName,Object obj) throws Exception ;
    public void insertMany(String collectionName,List<?> document) throws Exception ;
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
    /**
     * 查询列表
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public Object findMany(String collectionName,Map<String, CustmerCriteria> params) throws Exception;
    /**
     * 分页查询，并且排序
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn,Integer currentPage,Integer pageSize) throws Exception;
    /**
     * 查询列表，并且排序
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn) throws Exception;
    /**
     * 查询列表，并且指定规则排序
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public Object findMany(String collectionName,Map<String, CustmerCriteria> params,String sortColumn,Sort.Direction sort ,Integer currentPage,Integer pageSize) throws Exception;
    /**
     * 按照条件删除数据
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public long delete(String collectionName,Map<String,CustmerCriteria> params) throws Exception;
    /**
     * 删除整个集合
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public void drop(String collectionName) throws Exception ;
    /**
     * 查询并且修改查询到的第一个对象
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public void updateOne(String collectionName,Map<String, CustmerCriteria> query,Object obj) throws Exception ;
    public void updatMany(String collectionName,List<?> list) throws Exception ;
    /**
     * 根据条件查询，没有查到就插入，查到一个就更新，查到多个就全部更新
     * @Description: TODO(用一句话描述该文件做什么)
     * @author sheji zhaosheji.kevin@gmail.com
     * @date 2019年1月10日
     */
    public void updateOrSave(String collectionName,Map<String, CustmerCriteria> params,Object obj) throws Exception ;
    
    
}
