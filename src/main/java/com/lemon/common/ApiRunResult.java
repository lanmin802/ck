package com.lemon.common;

import lombok.Data;

@Data
public class ApiRunResult {
	private String statusCode;
	private String header; //HttpHeaders 是MultiValueMap 需要转String
	private String body;
	

}
