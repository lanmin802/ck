package com.lemon.controller;


import java.util.List;

import io.swagger.annotations.ApiOperation;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.common.ReportVo;
import com.lemon.common.Result;
import com.lemon.pojo.Project;
import com.lemon.pojo.TestReport;
import com.lemon.pojo.User;
import com.lemon.service.TestReportService;
import com.lemon.service.TestRuleService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@RestController
@RequestMapping("/testReport")
public class TestReportController {
	
	@Autowired
	TestReportService testReportService;
	
	@PostMapping("/run")
	@ApiOperation(value="批量执行用例",httpMethod="POST")
	public Result run(Integer suiteId) throws JsonProcessingException {
		
		Result result = null;
		List<TestReport> list=testReportService.run(suiteId);
		result = new Result("1", "测试批量执行成功",list);
		return result;
	}
	
	
	@PostMapping("/findCaseRunResult")
	@ApiOperation(value="获取单个测试报告",httpMethod="POST")
	public Result findCaseRunResult(Integer caseId) throws JsonProcessingException {
		
		Result result = null;
		TestReport testReport=testReportService.findByCaseId(caseId);
		result = new Result("1", "获取单个测试报告",testReport);
		return result;
	}
	
	//获取总套件的测试报告
	@GetMapping("/get")
	@ApiOperation(value="获取套件测试报告",httpMethod="GET")
	public Result get(Integer suiteId){
		
		Result result = null;
		ReportVo reportVo=testReportService.getReport(suiteId);
		result = new Result("1", "获取套件测试报告",reportVo);
		return result;
	}
}
