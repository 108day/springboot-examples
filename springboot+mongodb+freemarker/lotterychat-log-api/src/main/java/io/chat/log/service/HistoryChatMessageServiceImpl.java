package io.chat.log.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.chat.log.entity.HistoryChatMessageEnity;
import io.chat.log.dao.IHistoryChatMessageDao;
import io.chat.log.vo.CustmerCriteria;
import io.chat.log.vo.PageResult;

@Service("historyChatMessageService")
public class HistoryChatMessageServiceImpl implements IHistoryChatMessageService {
	
	@Autowired
	private IHistoryChatMessageDao iHistoryChatMessageDao;

	@Override
	public void saveChatMessage(HistoryChatMessageEnity chatMessageEnity) throws Exception {
		
		 iHistoryChatMessageDao.insertOne(chatMessageEnity);
	}

	@Override
	public PageResult<HistoryChatMessageEnity> selectPageChatMessageByMap(Map<String, CustmerCriteria> params, Integer currentPage,
			Integer pageSize) throws Exception {
		
		return iHistoryChatMessageDao.page(params, currentPage, pageSize,HistoryChatMessageEnity.class);
	}

	@Override
	public List<HistoryChatMessageEnity> selectListChatMessageByMap(Map<String, CustmerCriteria> params) throws Exception {
		// TODO Auto-generated method stub
		return iHistoryChatMessageDao.findMany(params, HistoryChatMessageEnity.class);
	}

	@Override
	public long delete(Map<String, CustmerCriteria> params) throws Exception {
		return iHistoryChatMessageDao.delete(params, HistoryChatMessageEnity.class);
	}

	@Override
	public HistoryChatMessageEnity update(HistoryChatMessageEnity chatMessageEnity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
