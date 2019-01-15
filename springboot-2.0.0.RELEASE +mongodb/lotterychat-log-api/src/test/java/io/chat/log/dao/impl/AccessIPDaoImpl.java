package io.chat.log.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.mongo.MongoCommonDaoImpl;
import io.chat.log.dao.IAccessIPDao;

@Service("accessIPDao")
public class AccessIPDaoImpl extends MongoCommonDaoImpl implements IAccessIPDao {
	
}
