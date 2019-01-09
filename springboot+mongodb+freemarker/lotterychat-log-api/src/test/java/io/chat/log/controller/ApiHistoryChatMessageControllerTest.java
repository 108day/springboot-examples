package io.chat.log.controller;


import java.lang.Thread.State;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
import io.chat.log.service.IAppLogService;
import io.chat.log.vo.SendMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatLogApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class ApiHistoryChatMessageControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
	protected IAppLogService appLogService;
    

    private MockMvc mockMvc;

    private final String roomId = "66666";
    
    @Before
    public void setupMockMvc() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
    public void testSave() throws Exception {
    	int threadNumber = 300 ; //300个线程
    	ConcurrentHashMap<String,Object> currentHashMap   = new ConcurrentHashMap<>();
    	CountDownLatch cdl = new CountDownLatch(threadNumber);
    	String collectionName="";
    	String roomId ="";
    	Long count = 100000L; // 每个线程处理一百万的数据
    	for(int i = 0;i<threadNumber; i++) {
    		new MyThread (collectionName,roomId,count, 
    				mockMvc,appLogService,currentHashMap ,cdl);
    	}
    	cdl.await(60,TimeUnit.MINUTES);
    	currentHashMap.forEach((key,value)->{
    		if(value instanceof Thread) {
    			Thread th= ((Thread)value);
    			State state =th.getState();
    	    	if(state.equals(State.RUNNABLE)) {
    	    		th.interrupt();
    	    	}
    		}
    	});
    	
    }

    @Test
    public void testSaveEntity() throws Exception {
    	for(int i = 0;i<20000000; i++) {
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
    		//调用接口，传入添加的用户参数
        	mockMvc.perform(MockMvcRequestBuilders.post("/api/chatlog/save/"+new Random().nextInt(10))
    		        .contentType(MediaType.APPLICATION_JSON_UTF8)
    		        .content(JSON.toJSONString(sendMessage)))
    				.andDo(MockMvcResultHandlers.print());
    	}
    }
    
    /**
     * 分页查询数据
     * @Description: TODO(分页查询数据，可以指定开始时间，结束时间和分页数，分页数最大可以设置1000)
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
   */
    @Test
    public void testPage() throws Exception {
    	Long currentTime = System.currentTimeMillis();
    	Long startTime = currentTime - 60000*10;/*查询10分钟之前到现在的数据*/
    	Long endTime = currentTime;
    	String uri = "/api/chatlog/page/"+roomId+"/"+1+"/"+20+"?startTime="+startTime+"&"+"endTime="+endTime;
        //调用接口，传入添加的用户参数
    	mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8))
               .andDo(MockMvcResultHandlers.print());

    }

    /**
     * 删除数据
     * @Description: TODO(删除10分钟之前的数据)
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    @After
    public void testDelete() throws Exception {
    	String uri ="/api/chatlog/delete/"+roomId+"?minutesBefore="+1;
        //调用接口，传入添加的用户参数
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
        .andDo(MockMvcResultHandlers.print());

    }
}