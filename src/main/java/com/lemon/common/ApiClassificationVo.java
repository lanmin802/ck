package com.lemon.common;

import java.util.List;

import lombok.Data;

import com.lemon.pojo.Api;
import com.lemon.pojo.ApiClassification;

@Data
public class ApiClassificationVo extends ApiClassification {
	//关联
	List<Api> apis;

}
