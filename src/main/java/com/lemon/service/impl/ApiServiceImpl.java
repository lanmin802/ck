package com.lemon.service.impl;

import java.util.List;







import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.pojo.Api;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.common.ApiListVo;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVo;
import com.lemon.common.Result;
import com.lemon.mapper.ApiMapper;
import com.lemon.service.ApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {
	
	@Autowired
	ApiMapper apiMapper;
	public List<ApiListVo> showApiListByProject(Integer projectId){
		return apiMapper.showApiListByProject(projectId);
	}
	
	public List<ApiListVo> showApiListByApiClassification(Integer apiClassificationId){
		return apiMapper.showApiListByApiClassification(apiClassificationId);
	}
	
	public ApiVo findApiViewVo(Integer apiId){
		return apiMapper.findApiViewVo(apiId);
	}

	public ApiRunResult run(ApiVo apiVo) throws JsonProcessingException {
		//https
		
		// 远程调用接口http
		String url = apiVo.getHost()+apiVo.getUrl();
		RestTemplate restTemplate  = new RestTemplate();
		String method = apiVo.getMethod();
		List<ApiRequestParam> apiRequestParams = apiVo.getRequestParams();
		MultiValueMap body = new LinkedMultiValueMap();
		MultiValueMap headers = new LinkedMultiValueMap();
		HttpEntity requestEntity = null;
		ResponseEntity responseEntity = null;
		ApiRunResult apiRunResult = new ApiRunResult();
		String paramString = "?";
		String jsonbody="";
		for(ApiRequestParam apiRequestParam : apiRequestParams){
			if(apiRequestParam.getType()==3){
				headers.add(apiRequestParam.getName(), apiRequestParam.getValue());
			}else if (apiRequestParam.getType()==2) {
				body.add(apiRequestParam.getName(), apiRequestParam.getValue());
			}else if (apiRequestParam.getType()==1) {
				paramString+=apiRequestParam.getName()+"="+apiRequestParam.getValue()+"&";
			}else if (apiRequestParam.getType()==4) {
				jsonbody = apiRequestParam.getValue();
			}
		}
		if(!paramString.equals("?")){
			
			paramString = paramString.substring(0, paramString.lastIndexOf("&"));
		}
		//System.out.println(url+paramString);
		try{
			if(method.equalsIgnoreCase("get")){
				requestEntity = new HttpEntity(headers);
			 
				responseEntity = restTemplate.exchange(url+paramString, HttpMethod.GET, requestEntity, String.class);
			}else if (method.equalsIgnoreCase("post")) {
				if(jsonbody.equals("")){
					requestEntity = new HttpEntity(body, headers);
					responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
					
				}else {
				requestEntity = new HttpEntity(jsonbody, headers);
				responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
				
				}			
			}else if (method.equalsIgnoreCase("put")) {
			requestEntity = new HttpEntity(body, headers);
			System.out.println("3-------------------"+requestEntity.getBody()+url);
			responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			System.out.println("3-------------------"+responseEntity.getBody());
			}else if (method.equalsIgnoreCase("delete")) {
			requestEntity = new HttpEntity(headers);
			responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
			}
		
			apiRunResult.setStatusCode(responseEntity.getStatusCodeValue()+"");
			apiRunResult.setHeader(new ObjectMapper().writeValueAsString(responseEntity.getHeaders()));
			if(responseEntity.getBody()!=null){
				apiRunResult.setBody(responseEntity.getBody().toString());
			}
			
		}catch(HttpStatusCodeException e){
		//调用异常，body可能没有
			apiRunResult.setStatusCode(e.getRawStatusCode()+"");
			apiRunResult.setHeader(new ObjectMapper().writeValueAsString(e.getResponseHeaders()));
			apiRunResult.setBody(e.getResponseBodyAsString());
		}	
		
		
		return apiRunResult;
	}

}
