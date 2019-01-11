package io.chat.log.controller;


import java.lang.Thread.State;
import java.util.List;
import java.util.Map;
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
import org.springframework.data.domain.Sort;
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
import io.chat.log.service.IHistoryMessageService;
import io.chat.log.vo.SendMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatLogApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class ApiHistoryMessageControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
	protected IAppLogService appLogService;

	@Autowired
	private IHistoryMessageService historyMessageService;

    private MockMvc mockMvc;

    private static final String roomId = "66666";
    private static final String collectionName = "historyChatMessageEnity";
    
    @Before
    public void setupMockMvc() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testSaveEntity() throws Exception {
    	for(int i = 0;i<200; i++) {
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
        	mockMvc.perform(MockMvcRequestBuilders.post("/api/chatlog/save/"+collectionName)
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
    //@Test
    public void testPage() throws Exception {
    	Long currentTime = System.currentTimeMillis();
    	Long startTime = currentTime - 60000*10;/*查询10分钟之前到现在的数据*/
    	Long endTime = currentTime;
    	String uri = "/api/chatlog/page/"+collectionName+"/"+1+"/"+20+"?startTime="+startTime+"&"+"endTime="+endTime;
        //调用接口，传入添加的用户参数
    	mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8))
               .andDo(MockMvcResultHandlers.print());

    }

    //@Test
    public void testFind() throws Exception {
    	Long currentTime = System.currentTimeMillis();
    	Long startTime = currentTime - 60000*10;/*查询10分钟之前到现在的数据*/
    	List<Map<String,Object>> list =(List<Map<String,Object>>) historyMessageService.find(collectionName, startTime,"msgtime", Sort.Direction.ASC,20);
    	list.forEach(obj->{
    		System.out.println(obj);
    	});
    }
    
    /**
     * 删除数据
     * @Description: TODO(删除10分钟之前的数据)
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    //@After
    public void testDelete() throws Exception {
    	String uri ="/api/chatlog/delete/"+collectionName+"?msgId="+1;
        //调用接口，传入添加的用户参数
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
        .andDo(MockMvcResultHandlers.print());

    }
}