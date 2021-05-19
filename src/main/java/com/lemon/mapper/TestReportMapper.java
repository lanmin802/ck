package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lemon.common.CaseEditVo;
import com.lemon.common.ReportVo;
import com.lemon.pojo.TestReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface TestReportMapper extends BaseMapper<TestReport> {
	
	
	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.method, t6.url url, t3.`host` FROM cases t1 JOIN suite t2 ON t2.id = t1.suite_id JOIN project t3 ON t2.project_id = t3.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t2.id =#{suiteId}")
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "requestParams", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.ApiRequestParamMapper.findByCaseId")),
		@Result(property = "testRules", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))
		})
	public List<CaseEditVo> findCaseEditVoUnderSuite(Integer suiteId);
	
	@Delete("DELETE from test_report t where t.case_id IN (SELECT case_id from cases where cases.suite_id= #{suiteId})")
	public void deleteTestReport(Integer suiteId);
	
	@Select("select * from test_report where case_id=#{caseId}")
	public TestReport findByCaseId(Integer caseId);
	
	@Select("select * from suite where id=#{suiteId}")
	@Results({
		@Result(property = "id",column = "id"),
		
		@Result(property = "caseList", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.CasesMapper.findCasefromSuite"))
		})
	public ReportVo getReport(Integer suiteId);

}
