package io.chat.log.dao.impl;


import org.springframework.stereotype.Service;

import io.chat.common.mongo.MongoCommonDaoImpl;
import io.chat.log.dao.IAppLogDao;


@Service("appLogDao")
public class AppLogDaoImpl extends MongoCommonDaoImpl implements IAppLogDao {

	
}
