package io.chat.log.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.mongo.MongoCommonDaoImpl;
import io.chat.log.dao.IHistoryChatMessageDao;

@Service("historyChatMessageDao")
public class HistoryChatMessageDaoImpl extends MongoCommonDaoImpl implements IHistoryChatMessageDao {
	
}
