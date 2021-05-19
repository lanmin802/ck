package com.lemon.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.lemon.pojo.Api;
import com.lemon.pojo.ApiRequestParam;

@Data
public class ApiVo extends Api{
	private String createUserName;
	private String host;
	
	private List<ApiRequestParam> requestParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> queryParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> headerParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> bodyParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> bodyRawParams = new ArrayList<ApiRequestParam>();

}
