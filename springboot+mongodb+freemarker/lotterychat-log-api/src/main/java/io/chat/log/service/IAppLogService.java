package io.chat.log.service;

import java.util.Map;

public interface IAppLogService {

	/**
     * 创建对象
     * @param user
     */
    public void save(String collectionName,Map<String,Object> map) throws Exception ;
    
}
