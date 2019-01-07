package io.chat.log.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.chat.log.vo.CustmerCriteria;
import io.chat.log.dao.IAccessIPDao;
import io.chat.log.entity.AccessIpEntity;

@Service("accessIPService")
public class AccessIPServiceImpl implements IAccessIPService{

	@Autowired
	private IAccessIPDao accessIPDao;
	
	@Override
	public Object page(Map<String, CustmerCriteria> params, Integer currentPage,
			Integer pageSize)  throws Exception {
		return accessIPDao.page(params, currentPage, pageSize, AccessIpEntity.class);
	}

	@Override
	public void save(AccessIpEntity t) throws Exception {
		accessIPDao.insertOne(t);
		
	}

	@Override
	public void saveBatch(List<AccessIpEntity> list) throws Exception {
		accessIPDao.insertMany(list);
	}

	@Override
	public List<AccessIpEntity> query(Map<String, CustmerCriteria> params) throws Exception {
		return accessIPDao.findMany(params, AccessIpEntity.class);
	}

	@Override
	public List<AccessIpEntity> list() throws Exception {
		return accessIPDao.findMany(null, AccessIpEntity.class);
	}


	
}
