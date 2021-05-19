package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lemon.common.CaseEditVo;
import com.lemon.common.CaseListVo;
import com.lemon.pojo.Cases;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface CasesMapper extends BaseMapper<Cases> {
	
	@Select("select * from cases c where c.suite_id = #{suiteId}")
	public List<Cases> findBySuite(Integer suiteId);
	
	@Select("SELECT distinct t1.*, t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t2.id = t1.suite_id JOIN project t3 ON t2.project_id = t3.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t3.id = #{projectId}")
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "testReport", column="id", one =@One(select="com.lemon.mapper.TestReportMapper.findByCaseId"))
		})
	public List<CaseListVo> showCaseUnderProject(Integer projectId);
	
	@Select("SELECT distinct t1.*, t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t2.id = t1.suite_id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t1.suite_id = #{suiteId}")
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "testReport", column="id", one =@One(select="com.lemon.mapper.TestReportMapper.findByCaseId"))
		})
	public List<CaseListVo> findCasefromSuite(Integer suiteId);
	
	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.url, t6.method FROM cases t1 JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t1.id = #{caseId}")
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "requestParams", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.ApiRequestParamMapper.findByCaseId")),
		@Result(property = "testRules", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))
		})
	public CaseEditVo  findCaseEditVo(Integer caseId);
	
	

}
