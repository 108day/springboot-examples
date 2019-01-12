package io.chat.common.dao.impl;


import org.springframework.stereotype.Service;

import io.chat.common.dao.IAppLogDao;
import io.chat.common.mongo.CommonMongoDaoImpl;


@Service("appLogDao")
public class AppLogDaoImpl extends CommonMongoDaoImpl implements IAppLogDao {

	
}
