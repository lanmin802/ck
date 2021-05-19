package com.lemon.controller;


import java.util.List;

import io.swagger.annotations.ApiOperation;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.common.ApiVo;
import com.lemon.common.CaseEditVo;
import com.lemon.common.CaseListVo;
import com.lemon.common.Result;
import com.lemon.pojo.ApiClassification;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.Cases;
import com.lemon.pojo.User;
import com.lemon.service.CaseParamValueService;
import com.lemon.service.CasesService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@RestController
@RequestMapping("/cases")
public class CasesController {
	
	@Autowired
	CasesService casesServive;

	
	@PostMapping("/add")
	@ApiOperation(value="添加用例",httpMethod="POST")
	public Result add(Cases caseVo,ApiVo apiVo){
		Result result = null;
		
		casesServive.add(caseVo, apiVo);
			
	
		
		return new Result("1", "添加到用例套件");
		
	}
	
	@GetMapping("/showCaseUnderProject")
	@ApiOperation(value="显示显示项目下所有用例和组件",httpMethod="GET")
	public Result showCaseUnderProject(Integer projectId){
		
		List<CaseListVo> list=casesServive.showCaseUnderProject(projectId);
			
	
		
		return new Result("1", "显示项目下所有用例和组件",list);
		
	}
	
	@GetMapping("/findCasefromSuite")
	@ApiOperation(value="添加某套件下所有用例",httpMethod="GET")
	public Result findCasefromSuite(Integer suiteId){
		
		List<CaseListVo> list=casesServive.findCasefromSuite(suiteId);
			
	
		
		return new Result("1", "加某套件下所有用例",list);
		
	}
	
	@GetMapping("/toCaseEdit")
	@ApiOperation(value="编辑用例详情",httpMethod="GET")
	public Result toCaseEdit(Integer caseId){
		
		CaseEditVo caseEditVo=casesServive.findCaseEditVo(caseId);
			
	
		
		return new Result("1", "编辑用例详情",caseEditVo);
		
	}
	
	@PutMapping("/update")
	@ApiOperation(value="更新用例",httpMethod="PUT")
	public Result updateCase(CaseEditVo caseEditVo){
		Result result = null;
		
		casesServive.updateCase(caseEditVo);
			
	
		
		return new Result("1", "更新用例成功");
		
	}
	

}
