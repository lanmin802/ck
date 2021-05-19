package com.lemon.service.impl;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;











import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.lemon.common.ApiRunResult;
import com.lemon.common.CaseEditVo;
import com.lemon.common.ReportVo;
import com.lemon.mapper.TestReportMapper;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.TestReport;
import com.lemon.pojo.TestRule;
import com.lemon.pojo.User;
import com.lemon.service.TestReportService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@Service
public class TestReportServiceImpl extends ServiceImpl<TestReportMapper, TestReport> implements TestReportService {

	@Autowired
	TestReportMapper testReportMapper;
	public List<TestReport> run(Integer suiteId) throws JsonProcessingException {
		List<TestReport> testReports = new ArrayList<TestReport>();
		// 根据suiteId查询
		List<CaseEditVo> list= testReportMapper.findCaseEditVoUnderSuite(suiteId);
		
		//执行每个case
		for(CaseEditVo caseEditVo : list){
			TestReport testReport = runAndGetReport(caseEditVo);
			testReports.add(testReport);
			
		}
		//得到测试报告，先删testrepot表，再插入
		testReportMapper.deleteTestReport(suiteId);
		this.saveBatch(testReports);
		return(testReports);
		
	}
	
	public TestReport runAndGetReport(CaseEditVo caseEditVo) throws JsonProcessingException {
		TestReport  testReport = new TestReport();
		String url = caseEditVo.getHost()+caseEditVo.getUrl();
		RestTemplate restTemplate  = new RestTemplate();
		String method = caseEditVo.getMethod();
		List<ApiRequestParam> apiRequestParams = caseEditVo.getRequestParams();
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
					testReport.setRequestBody(new ObjectMapper().writeValueAsString(body));
				}else {
				requestEntity = new HttpEntity(jsonbody, headers);
				responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
				testReport.setRequestBody(jsonbody);
				}			
			}else if (method.equalsIgnoreCase("put")) {
			
			requestEntity = new HttpEntity(body, headers);
			responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			}else if (method.equalsIgnoreCase("delete")) {
			
			requestEntity = new HttpEntity(headers);
			responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
			}
			System.out.println(responseEntity.getBody().toString());
			testReport.setCaseId(caseEditVo.getId());
			testReport.setRequestHeaders(new ObjectMapper().writeValueAsString(headers));
			testReport.setRequestUrl(url);		
			testReport.setRequestBody(new ObjectMapper().writeValueAsString(requestEntity.getBody()));		
			testReport.setResponseHeaders(new ObjectMapper().writeValueAsString(responseEntity.getHeaders()));
			testReport.setResponseBody(responseEntity.getBody().toString());
			testReport.setPassFlag(assertByTestRule(responseEntity.getBody().toString(),caseEditVo.getTestRules()));
			
			
		
			
			}catch(HttpStatusCodeException e){
			e.printStackTrace();
		
			}
		
		
			return testReport;
		}
	
	public String assertByTestRule(String responseBody,List<TestRule> testRules) {
		
		boolean flag =true;
		for(TestRule testRule:testRules){
			
			
			//Object value=JsonPath.read(responseBody, testRule.getExpression()); //根据jsonpath获取value, $.name
			
			Object value =JSONPath.read(responseBody, testRule.getExpression()); //JsonPath.read(responseBody, testRule.getExpression(), filters)
			String operator = testRule.getOperator();
			String actual = (String) value;
			if(operator.equals("=")){
				if(!actual.equals(testRule.getExpected())){
					flag = false;
				}
				
			}else {
				if(!actual.contains(testRule.getExpected())){
					flag = false;
				}
			}
		}
		
		if(flag!=true){
			return "不通过";
		}else {
			return "通过";
		}
	}

	@Override
	public TestReport findByCaseId(Integer caseId) {
		// TODO Auto-generated method stub
		return  testReportMapper.findByCaseId(caseId);
		
	}

	@Override
	public ReportVo getReport(Integer suiteId) {
		
		ReportVo reportVo = testReportMapper.getReport(suiteId);
		User user  = (User) SecurityUtils.getSubject().getPrincipal();
		reportVo.setUsername(user.getUsername());
		reportVo.setCreateReportTime(new Date());
		
		return reportVo;
	}
}
