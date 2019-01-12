package io.chat.log.controller;


import java.util.Date;

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
import io.chat.common.service.IAppLogService;
/**
 * 测试添加IP授权接口
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月9日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatLogApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class ApiAccessIPControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
	protected IAppLogService appLogService;
    

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testSaveEntity() throws Exception {
    	AccessIpEntity accessIpEntity = new AccessIpEntity();
    	accessIpEntity.setTitle("小飞");
    	accessIpEntity.setIp("10.10.105.14");
    	accessIpEntity.setScope("internet");
    	accessIpEntity.setCreatedBy("admin");
    	accessIpEntity.setCreatedTime(new Date());
    	accessIpEntity.setLastModifyBy("admin");
    	accessIpEntity.setLastModifyTime(new Date());
		//调用接口，传入添加的用户参数
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/accessip/save")
		        .contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content(JSON.toJSONString(accessIpEntity)))
				.andDo(MockMvcResultHandlers.print());
    }
}