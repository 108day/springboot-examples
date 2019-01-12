package io.chat.log.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.chat.log.dao.IAppLogDao;

@Service("appLogService")
public class AppLogServiceImpl implements IAppLogService {
	
	@Autowired
	private IAppLogDao appLogDao;

	@Override
	public void save(String collectionName,Map<String,Object> map) throws Exception {
		appLogDao.insertOne(collectionName, map);
		
	}

	
	
}
