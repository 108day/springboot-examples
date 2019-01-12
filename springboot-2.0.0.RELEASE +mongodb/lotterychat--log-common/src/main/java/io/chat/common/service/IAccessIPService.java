package io.chat.common.service;

import java.util.List;
import java.util.Map;

import io.chat.common.vo.CustmerCriteria;
import io.chat.common.vo.PageResult;

public interface IAccessIPService {

	public PageResult<?> page(Map<String, CustmerCriteria> params, Integer currentPage,
			Integer pageSize) throws Exception;
	
	public List<?> findMany(Map<String, CustmerCriteria> params) throws Exception;
	public List<?> list() throws Exception;
	
	public void save(Object object) throws Exception;
	
	public void saveBatch(List<?> list) throws Exception;
}
