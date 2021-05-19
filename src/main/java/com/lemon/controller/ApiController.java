package com.lemon.controller;


import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lemon.common.ApiListVo;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVo;
import com.lemon.common.Result;
import com.lemon.pojo.Api;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.User;
import com.lemon.service.ApiRequestParamService;
import com.lemon.service.ApiService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	ApiRequestParamService apiRequestParamService;
	
	@GetMapping("/showApiUnderProject")
	@ApiOperation(value="根据项目ID获取api",httpMethod="GET")
	public Result showApiListByProject(Integer projectId){
		List<ApiListVo> list = apiService.showApiListByProject(projectId);
		Result result = new Result("1", "查询某项目下所有接口",list);
		return result;
		
	}
	
	
	@GetMapping("/showApiUnderApiClassification")
	@ApiOperation(value="根据接口类别获取api",httpMethod="GET")
	public Result showApiUnderApiClassification(Integer apiClassificationId){
		List<ApiListVo> list = apiService.showApiListByApiClassification(apiClassificationId);
		Result result = new Result("1", "查询某类别下所有接口",list);
		return result;
		
	}
	
	@PostMapping("/addApi")
	@ApiOperation(value="添加api接口",httpMethod="POST")
	public Result add(Api api){
		System.out.println("------");
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		api.setCreateUser(user.getId());
		apiService.save(api);
		Result result = new Result("1", "添加接口");
		return result;
		
	}
	
	@GetMapping("/toApiView")
	@ApiOperation(value="接口api",httpMethod="GET")
	public Result toApiView(Integer apiId){
		ApiVo apiVo= apiService.findApiViewVo(apiId);
		Result result = new Result("1", "查询接口详情",apiVo);
		return result;
	}
	
	@PostMapping("/edit")
	@ApiOperation(value="更新api接口",httpMethod="POST")
	public Result toApiEdit(ApiVo apiVo){
		//直接根据主键id更新
		//1.先对api信息做一次更新
		apiService.updateById(apiVo);
		//2.删除原来的requestParams
		QueryWrapper<ApiRequestParam> queryWrapper=new  QueryWrapper();
		queryWrapper.eq("api_Id", apiVo.getId());
		apiRequestParamService.remove(queryWrapper);
		//3.插入新的requestParams
		apiVo.getRequestParams().addAll(apiVo.getQueryParams());
		//apiVo.getRequestParams().addAll(apiVo.getBodyParams());
		apiVo.getRequestParams().addAll(apiVo.getBodyParams());
		apiVo.getRequestParams().addAll(apiVo.getBodyRawParams());
		apiVo.getRequestParams().addAll(apiVo.getHeaderParams());	
		apiRequestParamService.saveBatch(apiVo.getRequestParams());
		Result result = new Result("1", "更新接口成功");
		return result;
		
	}
	
	@PostMapping("/run")
	@ApiOperation(value="运行单例",httpMethod="POST")
	public Result run(ApiVo apiVo) throws JsonProcessingException {
		ApiRunResult  apiRunResult = apiService.run(apiVo);
		Result result = new Result("1", "运行单例成功",apiRunResult);
		return result;
		
	}
}
