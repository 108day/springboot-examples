package io.chat.log.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.mongo.MongoCommonDaoImpl;
import io.chat.log.dao.IOnlineUserDao;

@Service("onlineUserDao")
public class OnlineUserDaoImpl extends MongoCommonDaoImpl implements IOnlineUserDao {
	
}
