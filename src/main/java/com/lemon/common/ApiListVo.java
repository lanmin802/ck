package com.lemon.common;

import lombok.Data;

import com.lemon.pojo.Api;

@Data
public class ApiListVo{
	private String  id;
	private String  name;
	private String  method;
	private String  url;
	private String classificationName;
	
}
