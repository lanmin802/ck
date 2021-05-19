package com.lemon.controller;


import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.common.ApiClassificationVo;
import com.lemon.common.Result;
import com.lemon.pojo.ApiClassification;
import com.lemon.pojo.Suite;
import com.lemon.pojo.User;
import com.lemon.service.ApiClassificationService;
import com.lemon.service.SuiteService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@RestController
@RequestMapping("/apiClassification")
public class ApiClassificationController {
	@Autowired
	ApiClassificationService apiClassificationService;
	
	@Autowired
	SuiteService suiteService;
	
	@GetMapping("/toIndex")
	@ApiOperation(value="获取api",httpMethod="GET")
	public Result toIndex(Integer projectId,Integer tab){
		Result result = null;
		

		if (tab==1) {
			//接口列表
			
			List<ApiClassificationVo> list = apiClassificationService.getWithApi(projectId);
			result = new Result("1","查询分类同时也延迟加载",list);
			
		}else {
			//用例集合tab=2
			List<Suite> list2 = suiteService.findSuiteAndRelatedCasesBy(projectId);
			result = new Result("1","查询用例套件及用例",list2);
		}
		
		return result;
		
	}
	@PostMapping("/add/{projectId}")
	@ApiOperation(value="添加分类",httpMethod="POST")
	public Result addClassification(@PathVariable("projectId") Integer id,ApiClassification apiClassification){
		Result result = null;
		
			apiClassification.setProjectId(id);
			User user=(User) SecurityUtils.getSubject().getPrincipal();
			apiClassification.setCreateUser(user.getId());
			apiClassificationService.save(apiClassification);
			result = new Result("1","添加成功");
			
	
		
		return result;
		
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value="删除接口类",httpMethod="DELETE")
	public Result delClassification(@PathVariable("id") Integer id){
		Result result = null;
			
		apiClassificationService.removeById(id);
		result = new Result("1","删除成功");
			
	
		return result;
		
	}
	
	@GetMapping("/searchClassification")
	@ApiOperation(value="按名字查询接口类",httpMethod="GET")
	public Result searchClassification(String name){
		Result result = null;
		
		QueryWrapper queryWrapper =  new QueryWrapper();
		queryWrapper.like("name", name);
		List<ApiClassification> apiClassification=apiClassificationService.list(queryWrapper);
		result = new Result("1","查询完成",apiClassification);
			
	
		return result;
	}
	
	//根据projectId查询接口分类
	@GetMapping("/findAll")
	@ApiOperation(value="根据projectId查询接口分类",httpMethod="GET")
	public Result findAll(Integer projectId){
		Result result= null;
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("project_id", projectId);
		List<ApiClassification> list = apiClassificationService.list(queryWrapper);
		return result = new Result("1", "接口分类",list);
		
	}
	
	//处理json格式入参
	@PostMapping("/add2")
	@ApiOperation(value="jsonbody",httpMethod="POST")
	public Result add2(@RequestBody String jsonString) throws JsonMappingException, JsonProcessingException{
		Result result= null;
		System.out.println(jsonString);
		//jsonString = jsonString.substring(jsonString.indexOf("[")+2, jsonString.indexOf("[")-1);
		//将json转java对象
		ApiClassification apiClassification = new ObjectMapper().readValue(jsonString, ApiClassification.class);

		apiClassificationService.save(apiClassification);
		return result = new Result("1", "添加接口分类");
		
	}
	
}
