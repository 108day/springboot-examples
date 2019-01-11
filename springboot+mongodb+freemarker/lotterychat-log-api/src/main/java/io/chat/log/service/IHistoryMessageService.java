package io.chat.log.service;


import java.util.Map;

import org.springframework.data.domain.Sort;

import io.chat.log.vo.CustmerCriteria;

/**
 * 聊天历史消息保存，查询，删除
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月9日
 */
public interface IHistoryMessageService {
	/**
	 * 保存
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	public void save(String collectionName,Map<String,Object> message)throws Exception;
	/**
	 * 查询前面固定条数据
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	public Object find(String collectionName,long startTime,int pageSize)throws Exception;
	public Object find(String collectionName, long startTime,String orderby,Sort.Direction sort, int pageSize) throws Exception;
	/**
	 * 删除消息
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	public long delete(String collectionName,String msgId)throws Exception;
	/**
	 * 按天删除
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	public long deleteByDays(String collectionName,int days)throws Exception;
	
}
