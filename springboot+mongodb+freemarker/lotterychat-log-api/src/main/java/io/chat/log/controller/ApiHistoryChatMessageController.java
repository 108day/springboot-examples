package io.chat.log.controller;

import java.util.HashMap;
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

import io.chat.log.entity.SendMessage;
import io.chat.log.service.IHistoryChatMessageService;
import io.chat.log.vo.CustmerCriteria;
import io.chat.log.vo.PageResult;
import io.chat.log.vo.R;

/**
 * 操作聊天记录API
 * @author kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月4日
 */
@RestController
@RequestMapping(value="/api/chatlog/")
public class ApiHistoryChatMessageController{
	
	
	@Autowired
	protected IHistoryChatMessageService historyChatMessageService;
	
	private Logger logger = LoggerFactory.getLogger(ApiHistoryChatMessageController.class);
	
	/**
	 * 分页查询
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	@RequestMapping("/page/{roomId}/{currentPage}/{pageSize}")
	public Object page(@PathVariable("roomId") String roomId,@PathVariable("currentPage") Integer currentPage,@PathVariable("pageSize") Integer pageSize,Long startTime,Long endTime) {
		try {
			if(pageSize>1000) {
				return R.error(500, "每次最多可以查询1000条数据！");
			}
			if(pageSize<=0) {
				pageSize = 20;
			}
			Map<String, CustmerCriteria> params =  new HashMap<>();
			params.put("roomId", new CustmerCriteria("=",roomId));
			//查询 minutes 分钟 到 现在的数据
			if(startTime!=null && endTime!=null) {
				params.put("createdTime", new CustmerCriteria("between",new Long[]{startTime,endTime}));
			}else if(startTime!=null && endTime==null ) {
				params.put("createdTime", new CustmerCriteria(">=",startTime));
			}else if(startTime==null && endTime!=null ) {
				params.put("createdTime", new CustmerCriteria("<=",endTime));
			}
			PageResult<SendMessage> result = this.historyChatMessageService.selectPageChatMessageByMap(params, currentPage, pageSize);
			return R.okwithdata(result);
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
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Object save(@RequestBody SendMessage message) {
		try {
			if(message==null) {
				return R.error(500, "保存失败，消息不能为空！");
			}
			if(message.getMsgid()==null) {
				return R.error(500, "保存失败，消息ID不能为空！");
			}
			this.historyChatMessageService.saveChatMessage(message);
			return R.ok();
		} catch (Exception e) {
			logger.error("保存失败，请联系管理员！", e);
			return R.error(500, "保存失败，请联系管理员！");
		}
	}
	
	/**
	 * 保存
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	@RequestMapping(value="/save/{roomId}",method=RequestMethod.POST)
	public Object save(@PathVariable("roomId") String roomId,@RequestBody Map<String,Object> param) {
		try {
			if(param==null) {
				return R.error(500, "保存失败，消息不能为空！");
			}
			String collctionName = "historyChatMessageEnity"+roomId;
			param.put("roomId", collctionName);
			param.put("createdTime", param.get("msgtime")==null ? System.currentTimeMillis():param.get("msgtime"));
			this.historyChatMessageService.insertChatMessage(collctionName, param);
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
	@GetMapping("/delete/{roomId}")
	public Object delete(@PathVariable("roomId") String roomId,@PathParam("minutesBefore") Long minutesBefore) {
		try {
			Map<String, CustmerCriteria> params =  new HashMap<>();
			params.put("roomId", new CustmerCriteria("=",roomId));
			
			if(minutesBefore!=null) {
				params.put("createdTime", new CustmerCriteria("<=",System.currentTimeMillis()-60000*minutesBefore));
			}
			long count = historyChatMessageService.delete(params);
			return R.okwithdata(count);
		} catch (Exception e) {
			logger.error("删除失败，请联系管理员！", e);
			return R.error(500, "保存失败，请联系管理员！");
		}
	}
	
	
}
