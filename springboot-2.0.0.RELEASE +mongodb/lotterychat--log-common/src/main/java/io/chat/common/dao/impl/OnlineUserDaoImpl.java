package io.chat.common.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.dao.IOnlineUserDao;
import io.chat.common.mongo.MongoCommonDaoImpl;

@Service("onlineUserDao")
public class OnlineUserDaoImpl extends MongoCommonDaoImpl implements IOnlineUserDao {
	
}
