package io.chat.log.dao;

import org.springframework.stereotype.Service;

import io.chat.log.common.CommonEntityDaoImpl;
import io.chat.log.entity.HistoryChatMessageEnity;

@Service("historyChatMessageDao")
public class HistoryChatMessageDaoImpl extends CommonEntityDaoImpl<HistoryChatMessageEnity> 
																implements IHistoryChatMessageDao {
	
}
