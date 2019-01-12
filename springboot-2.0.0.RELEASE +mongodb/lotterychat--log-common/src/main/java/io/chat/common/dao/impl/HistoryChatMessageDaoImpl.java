package io.chat.common.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.dao.IHistoryChatMessageDao;
import io.chat.common.mongo.CommonMongoDaoImpl;

@Service("historyChatMessageDao")
public class HistoryChatMessageDaoImpl extends CommonMongoDaoImpl implements IHistoryChatMessageDao {
	
}
