package com.lemon.controller;


import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.ApiClassification;
import com.lemon.pojo.Suite;
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
@RequestMapping("/suite")
public class SuiteController {
	
	@Autowired
	SuiteService suiteService;
	
	@GetMapping("/listAll")
	@ApiOperation(value="查询套件",httpMethod="GET")
	public Result findAll(Integer projectId){
		Result result= null;
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("project_id", projectId);
		List<Suite> list = suiteService.list(queryWrapper);
		return result = new Result("1", "套件",list);
		
	}
	
	@PostMapping("/add")
	@ApiOperation(value="添加套件",httpMethod="POST")
	public Result add(Suite suite){
		Result  result = null;
		suiteService.save(suite);
		return result = new Result("1", "套件");
		
	}

}
