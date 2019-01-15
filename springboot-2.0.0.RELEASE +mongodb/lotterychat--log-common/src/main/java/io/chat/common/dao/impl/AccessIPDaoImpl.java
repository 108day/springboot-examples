package io.chat.common.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.dao.IAccessIPDao;
import io.chat.common.mongo.MongoCommonDaoImpl;

@Service("accessIPDao")
public class AccessIPDaoImpl extends MongoCommonDaoImpl implements IAccessIPDao {
	
}
