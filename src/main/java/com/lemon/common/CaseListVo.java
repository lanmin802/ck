package com.lemon.common;

import com.lemon.pojo.TestReport;

import lombok.Data;

@Data
public class CaseListVo {
	private Integer id;
	private String name;
	private String apiId;
	private String apiUrl;
	private TestReport testReport;
	
	
}
