package io.chat.log.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.alibaba.fastjson.JSON;

import io.chat.log.entity.HistoryChatMessageEnity;
import io.chat.log.service.IAppLogService;
import io.chat.log.util.ComputerMonitorUtil;
import io.chat.log.vo.SendMessage;

/*
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月7日
 */
public class MyThread extends Thread implements Runnable {

	private IAppLogService appLogService;
	
	private String threadName;
	private String roomId ;
	private MockMvc mockMvc;
	private CountDownLatch cdl ;
	private ConcurrentHashMap<String,Object> currentHashMap ;
	private Long count;
	private String collectionName;
	 
	public MyThread(String collectionName,String roomId,Long count, 
			MockMvc mockMvc,IAppLogService appLogService,ConcurrentHashMap<String,Object> currentHashMap ,CountDownLatch cdl) {
		super();
		this.collectionName = collectionName;
		this.count = count;
		this.roomId = roomId;
		this.threadName = roomId;
		this.mockMvc = mockMvc;
		this.appLogService = appLogService;
	}




	@Override
	public void run() {
		Map<String,Object> map = new HashMap<String,Object>();
		System.err.println("start=============>"+threadName);
		for(int i = 0; i< this.count ;i++) {
			
			long startTime = System.currentTimeMillis();
    		HistoryChatMessageEnity chatMessageEntity = new HistoryChatMessageEnity();
        	SendMessage sendMessage = new SendMessage();
        	String json ="{\"msg\":\""+i+"黑夜给了我黑色的皮肤，但是我的牙齿却可以很白！Are you understand my heart for me ? \"}";
        	sendMessage.setContent(JSON.parseObject(json));
        	sendMessage.setMsgid((UUID.randomUUID()+"+"+System.currentTimeMillis()).replaceAll("-", ""));
        	sendMessage.setMsgtime(System.currentTimeMillis());
        	sendMessage.setFromnickname("封神独秀");
        	sendMessage.setFromuserid("12300"+i);
        	sendMessage.setMtype("red");
        	sendMessage.setTousernickname("西门一支");
        	sendMessage.setTouserid("95271"+i);
        	chatMessageEntity.setJsonObject(sendMessage);
        	chatMessageEntity.setRoomId(roomId);
        	chatMessageEntity.setCreatedTime(System.currentTimeMillis());
    		//调用接口，传入添加的用户参数
            try {
				mockMvc.perform(MockMvcRequestBuilders.post("/api/chatlog/save")
				        .contentType(MediaType.APPLICATION_JSON_UTF8)
				        .content(JSON.toJSONString(chatMessageEntity)));
						//.andDo(MockMvcResultHandlers.print());
				map.put("耗时(ms)",System.currentTimeMillis()-startTime);
	    		map.put("CPU", ComputerMonitorUtil.getCpuUsage());
	    		map.put("内存(%)", ComputerMonitorUtil.getCpuUsage());
	    		appLogService.save(this.collectionName, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		currentHashMap.put(threadName, map);
		cdl.countDown();
		System.err.println("end=============>"+threadName);
	}

}
