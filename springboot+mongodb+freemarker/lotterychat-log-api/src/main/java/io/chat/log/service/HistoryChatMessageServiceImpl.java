package io.chat.log.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.chat.log.entity.SendMessage;
import io.chat.log.dao.IHistoryChatMessageDao;
import io.chat.log.vo.CustmerCriteria;
import io.chat.log.vo.PageResult;

@Service("historyChatMessageService")
public class HistoryChatMessageServiceImpl implements IHistoryChatMessageService {
	
	@Autowired
	private IHistoryChatMessageDao iHistoryChatMessageDao;

	@Override
	public void saveChatMessage(SendMessage sendMessage) throws Exception {
		
		 iHistoryChatMessageDao.insertOne(sendMessage);
	}

	@Override
	public PageResult<SendMessage> selectPageChatMessageByMap(Map<String, CustmerCriteria> params, Integer currentPage,
			Integer pageSize) throws Exception {
		
		return iHistoryChatMessageDao.page(params, currentPage, pageSize,SendMessage.class);
	}

	@Override
	public List<SendMessage> selectListChatMessageByMap(Map<String, CustmerCriteria> params) throws Exception {
		// TODO Auto-generated method stub
		return iHistoryChatMessageDao.findMany(params, SendMessage.class);
	}

	@Override
	public long delete(Map<String, CustmerCriteria> params) throws Exception {
		return iHistoryChatMessageDao.delete(params, SendMessage.class);
	}

	@Override
	public SendMessage update(SendMessage chatMessageEnity) throws Exception {
		return iHistoryChatMessageDao.update(null,chatMessageEnity,SendMessage.class);
	}

	
	public void insertChatMessage(String collectionName,Map<String,Object> map) throws Exception {
		iHistoryChatMessageDao.insertOne(collectionName, map);
	}
	
}
