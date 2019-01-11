package io.chat.log.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.chat.common.vo.CustmerCriteria;
import io.chat.common.vo.PageResult;
import io.chat.log.entity.SendMessage;
import io.chat.log.service.IHistoryMessageService;
import io.chat.log.vo.R;

/**
 * 操作聊天记录API
 * @author kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月4日
 */
@RestController
@RequestMapping(value="/api/chatlog/")
public class ApiHistoryMessageController{
	
	
	@Autowired
	protected IHistoryMessageService historyMessageService;
	
	private Logger logger = LoggerFactory.getLogger(ApiHistoryMessageController.class);
	
	/**
	 * 分页查询
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	@RequestMapping("/page/{collectionName}/{currentPage}/{pageSize}")
	public Object page(@PathVariable("collectionName") String collectionName,@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,Long startTime,Long endTime) {
		try {
			if(pageSize>1000) {
				return R.error(500, "每次最多可以查询1000条数据！");
			}
			if(pageSize<=0) {
				pageSize = 20;
			}
			Map<String, CustmerCriteria> params =  new HashMap<>();
			//查询 minutes 分钟 到 现在的数据
			if(startTime!=null && endTime!=null) {
				params.put("msgtime", new CustmerCriteria("between",new Long[]{startTime,endTime}));
			}else if(startTime!=null && endTime==null ) {
				params.put("msgtime", new CustmerCriteria(">=",startTime));
			}else if(startTime==null && endTime!=null ) {
				params.put("msgtime", new CustmerCriteria("<=",endTime));
			}
			return R.okwithdata(this.historyMessageService.find(collectionName, startTime, pageSize));
		} catch (Exception e) {
			logger.error("系统错误，请联系管理员！", e);
			return R.error(500, "系统错误，请联系管理员！");
		}
	}
	
	/**
	 * 保存
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	@RequestMapping(value="/save/{collectionName}",method=RequestMethod.POST)
	public Object save(@PathVariable("collectionName") String collectionName,@RequestBody SendMessage sendMessage) {
		try {
			if(sendMessage==null) {
				return R.error(500, "保存失败，消息不能为空！");
			}
			if(sendMessage.getMsgid()==null) {
				return R.error(500, "保存失败，消息ID不能为空！");
			}
			JSONObject jsonObject = (JSONObject) JSON.toJSON(sendMessage);
			this.historyMessageService.save(collectionName, jsonObject);
			return R.ok();
		} catch (Exception e) {
			logger.error("保存失败，请联系管理员！", e);
			return R.error(500, "保存失败，请联系管理员！");
		}
	}
	
	/**
	 * 根据条件删除历史数据
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	@GetMapping("/delete/{collectionName}")
	public Object delete(@PathVariable("collectionName") String collectionName,@PathParam("msgId") String msgId) {
		try {
			Map<String, CustmerCriteria> params =  new HashMap<>();
			
			params.put("msgid", new CustmerCriteria("=",msgId));
			long count = historyMessageService.delete(collectionName, msgId);
			return R.okwithdata(count);
		} catch (Exception e) {
			logger.error("删除失败，请联系管理员！", e);
			return R.error(500, "保存失败，请联系管理员！");
		}
	}
	
	
}
