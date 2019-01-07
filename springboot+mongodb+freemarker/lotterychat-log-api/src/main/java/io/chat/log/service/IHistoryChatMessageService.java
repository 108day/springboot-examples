package io.chat.log.service;

import java.util.List;
import java.util.Map;

import io.chat.log.entity.HistoryChatMessageEnity;
import io.chat.log.vo.CustmerCriteria;
import io.chat.log.vo.PageResult;

public interface IHistoryChatMessageService {

	/**
     * 创建对象
     * @param user
     */
    public void saveChatMessage(HistoryChatMessageEnity chatMessageEnity) throws Exception ;
    
    /**
     * 分页查询
     * @param params 带表达式
     * @param currentPage
     * @param pageSize
     * @param t 最终返回的对象，以及查询的集合对象
     * @return PageResult<T>
     * @throws Exception
     */
    public PageResult<HistoryChatMessageEnity> selectPageChatMessageByMap(Map<String,CustmerCriteria> params, Integer currentPage,Integer pageSize) throws Exception ;
    
    public List<HistoryChatMessageEnity>  selectListChatMessageByMap(Map<String, CustmerCriteria> params) throws Exception;
    
    public long delete(Map<String,CustmerCriteria> params) throws Exception;
    
    public HistoryChatMessageEnity update(HistoryChatMessageEnity chatMessageEnity) throws Exception ;
}
