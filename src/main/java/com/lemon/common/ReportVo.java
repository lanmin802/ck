package com.lemon.common;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lemon.pojo.TestReport;

import lombok.Data;

@Data
public class ReportVo {
	private Integer id;
	private String name; //套件名
	private String username; //执行人
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createReportTime; //执行时间
	
	private int totalCases; //总用例数
	private int successes; //执行成功数
	private int failures; //失败数
	
	private List<CaseListVo> caseList;
	
	public int getTotalCases() {
		return caseList.size();
	}
	
	public int getSuccesses() {
		
		int suc=0;
		int fail=0;
	
		
		for(CaseListVo caseListVo :caseList){
			TestReport testReport = caseListVo.getTestReport();
			if(testReport!=null){
				if(testReport.getPassFlag().equals("通过")){
					suc++;
				}else {
					fail++;
				}
			}
		}
		this.successes=suc;
		this.failures=fail;
		return successes;
	}
public int getFailures() {
		

		return failures;
	}

}
