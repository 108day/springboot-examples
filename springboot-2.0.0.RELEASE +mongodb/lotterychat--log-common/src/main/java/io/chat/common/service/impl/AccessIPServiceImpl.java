package io.chat.common.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import io.chat.common.constants.MonoDBCollections;
import io.chat.common.dao.IAccessIPDao;
import io.chat.common.service.IAccessIPService;
import io.chat.common.vo.CustmerCriteria;
import io.chat.common.vo.PageResult;


@Service("accessIPService")
public class AccessIPServiceImpl implements IAccessIPService{

	@Autowired
	private IAccessIPDao accessIPDao;

	@Override
	public PageResult<?> page( Map<String, CustmerCriteria> params, Integer currentPage, Integer pageSize)
			throws Exception {
		
		return (PageResult<?>) accessIPDao.page(MonoDBCollections.ACCESS_IP_COLLECTION,params, currentPage, pageSize);
	}

	@Override
	public List<?> findMany(Map<String, CustmerCriteria> params) throws Exception {
		return (List<?>) accessIPDao.findMany(MonoDBCollections.ACCESS_IP_COLLECTION, params);
	}

	@Override
	public List<?> list() throws Exception {
		return (List<?>) accessIPDao.findMany(MonoDBCollections.ACCESS_IP_COLLECTION, null);
	}

	@Override
	public void save(Object object) throws Exception {
		accessIPDao.insertOne(MonoDBCollections.ACCESS_IP_COLLECTION, object);
	}

	@Override
	public void saveBatch(List<?> list) throws Exception {
		accessIPDao.insertMany(MonoDBCollections.ACCESS_IP_COLLECTION, list);
	}
	
}
