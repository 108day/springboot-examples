package controller;


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

import io.chat.log.admin.AdminApplication;

/**
 * 测试添加IP授权接口
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月9日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)//这里的Application是springboot的启动类名
public class ApiAdminControllerTest {

    @Autowired
    private WebApplicationContext context;
    

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test1() throws Exception {
		//调用接口，传入添加的用户参数
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/accessip/save")
		        .contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content("")).andDo(MockMvcResultHandlers.print());
    }
}