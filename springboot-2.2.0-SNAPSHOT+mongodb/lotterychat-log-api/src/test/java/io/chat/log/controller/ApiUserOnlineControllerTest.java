package io.chat.log.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import io.chat.log.entity.AccessIpEntity;
import io.chat.log.service.IAppLogService;
import io.chat.log.service.IOnlineUserService;
/**
 * 测试添加IP授权接口
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月9日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatLogApplication.class)//这里的Application是springboot的启动类名
public class ApiUserOnlineControllerTest {

    @Autowired
	protected IOnlineUserService onlineUserService;
    
   
    @Test
    public void testSaveOrUpdate() throws Exception {
    	Map<String,Object> user= new HashMap<String,Object>();
    	user.put("sessionid", 6);
    	for(int i = 0 ;i< 10 ; i++) {
    		
        	onlineUserService.saveOrUpdateOnline(user);
    	}
    }
}