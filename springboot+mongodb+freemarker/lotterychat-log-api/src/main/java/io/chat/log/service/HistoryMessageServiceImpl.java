package io.chat.log.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.chat.log.dao.IHistoryChatMessageDao;
import io.chat.log.vo.CustmerCriteria;

@Service("historyMessageService")
public class HistoryMessageServiceImpl implements IHistoryMessageService {
	
	@Autowired
	private IHistoryChatMessageDao iHistoryChatMessageDao;

	/**
	 * 保存
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	@Override
	public void save(String collectionName, Map<String,Object> message) throws Exception {
		iHistoryChatMessageDao.insertOne(collectionName, message);
	}

	/**
	 * 查询前面固定条数据
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	@Override
	public Object find(String collectionName, long startTime, int pageSize) throws Exception{
		Map<String,CustmerCriteria> params = new HashMap<>();
		params.put("msgtime", new CustmerCriteria("<",startTime));
		return iHistoryChatMessageDao.findMany(collectionName, params,"msgtime" , 1, pageSize);
	}
	
	@Override
	public Object find(String collectionName, long startTime,String orderby,Sort.Direction sort,int pageSize) throws Exception{
		Map<String,CustmerCriteria> params = new HashMap<>();
		params.put("msgtime", new CustmerCriteria("<",startTime));
		return iHistoryChatMessageDao.findMany(collectionName, params,"msgtime" ,sort, 1, pageSize);
	}
	
	/**
	 * 删除消息
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	@Override
	public long delete(String collectionName, String msgId) throws Exception{
		Map<String,CustmerCriteria> params = new HashMap<>();
		params.put("msgId", new CustmerCriteria("=",msgId));
		return iHistoryChatMessageDao.delete(collectionName, params);
	}

	/**
	 * 按天删除
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	@Override
	public long deleteByDays(String collectionName, int days) throws Exception{
		Map<String,CustmerCriteria> params = new HashMap<>();
		params.put("msgtime", new CustmerCriteria("<",60000*60*24*days));
		return iHistoryChatMessageDao.delete(collectionName, params);
	}
}
