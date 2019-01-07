package io.chat.log.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.time.StopWatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import io.chat.log.ChatLogApplication;
import io.chat.log.entity.HistoryChatMessageEnity;
import io.chat.log.service.IAppLogService;
import io.chat.log.util.ComputerMonitorUtil;
import io.chat.log.vo.SendMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatLogApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class ApiAppLogControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
	protected IAppLogService appLogService;
    

    private MockMvc mockMvc;

    private final String roomId = "66666";
    private final String COLLECTION_NAME = "mongodb_log";
    
    @Before
    public void setupMockMvc() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testSaveCollection() throws Exception {
    	
		HistoryChatMessageEnity chatMessageEntity = new HistoryChatMessageEnity();
    	SendMessage sendMessage = new SendMessage();
    	String json ="{\"msg\":\"黑夜给了我黑色的皮肤，但是我的牙齿却可以很白！Are you understand my heart for me ? \"}";
    	sendMessage.setContent(JSON.parseObject(json));
    	sendMessage.setMsgid((UUID.randomUUID()+"+"+System.currentTimeMillis()).replaceAll("-", ""));
    	sendMessage.setMsgtime(System.currentTimeMillis());
    	sendMessage.setFromnickname("封神独秀");
    	sendMessage.setFromuserid("12300");
    	sendMessage.setMtype("red");
    	sendMessage.setTousernickname("西门一支");
    	sendMessage.setTouserid("95271");
    	chatMessageEntity.setJsonObject(sendMessage);
    	chatMessageEntity.setRoomId(roomId);
    	chatMessageEntity.setCreatedTime(System.currentTimeMillis());
    	Map<String,Object> map = new HashMap<String,Object>();
    	//调用接口，传入添加的用户参数
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/log/save")
		        .contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content(JSON.toJSONString(chatMessageEntity)))
				.andDo(MockMvcResultHandlers.print());
	
    }
}