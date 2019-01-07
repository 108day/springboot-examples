package io.chat.log.service;

import java.util.List;
import java.util.Map;

import io.chat.log.entity.AccessIpEntity;
import io.chat.log.vo.CustmerCriteria;

public interface IAccessIPService {

	public Object page(Map<String, CustmerCriteria> params, Integer currentPage,
			Integer pageSize) throws Exception;
	
	public List<AccessIpEntity> query(Map<String, CustmerCriteria> params) throws Exception;
	public List<AccessIpEntity> list() throws Exception;
	
	public void save(AccessIpEntity t) throws Exception;
	
	public void saveBatch(List<AccessIpEntity> list) throws Exception;
}
