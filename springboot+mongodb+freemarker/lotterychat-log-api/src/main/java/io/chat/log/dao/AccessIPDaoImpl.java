package io.chat.log.dao;

import org.springframework.stereotype.Service;

import io.chat.log.common.CommonEntityDaoImpl;
import io.chat.log.entity.AccessIpEntity;
@Service("accessIPDao")
public class AccessIPDaoImpl extends CommonEntityDaoImpl<AccessIpEntity> 
																implements IAccessIPDao {
	
}
