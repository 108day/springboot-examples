package io.chat.common.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.dao.IOnlineUserDao;
import io.chat.common.mongo.CommonMongoDaoImpl;

@Service("onlineUserDao")
public class OnlineUserDaoImpl extends CommonMongoDaoImpl implements IOnlineUserDao {
	
}
