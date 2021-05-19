package com.lemon.controller;

import static org.junit.Assert.*;

import java.awt.print.Printable;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//junit+mockmvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSONPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestUserController {
	
	@Autowired
	private MockMvc mockMvc;
	
	String sessionId="";

	
	//用户登录 form入参
	@Before
	public void testLogin() throws Exception {
		String resultJson="";
		resultJson=mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
				.param("username", "001@qq.com")
				.param("password", "e10adc3949ba59abbe56e057f20f883e")
				)
			.andDo(MockMvcResultHandlers.print()) //结果处理器
			.andExpect(MockMvcResultMatchers.status().isOk()) //验证结果
			.andReturn().getResponse().getContentAsString();//获取响应
		
		System.out.println(resultJson);
		sessionId = (String) JSONPath.read(resultJson, "$.message");
	}
	
	


	//用户验重
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/find")
				.header("Authorization", sessionId)
				.param("username", "001@qq.com")
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("用户名已注册"))
				.andReturn();
	}
	
	//json入参
	
	@Test
	public void testAddProject() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/project/add2")
				.header("Authorization", sessionId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"testProject\",\"host\":\"admin.ck.org\"}")
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("1"))
				.andReturn();
	}
	
}
