package io.chat.common.dao.impl;

import org.springframework.stereotype.Service;

import io.chat.common.dao.IHistoryChatMessageDao;
import io.chat.common.mongo.MongoCommonDaoImpl;

@Service("historyChatMessageDao")
public class HistoryChatMessageDaoImpl extends MongoCommonDaoImpl implements IHistoryChatMessageDao {
	
}
