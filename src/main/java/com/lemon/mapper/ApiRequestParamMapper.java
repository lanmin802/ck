package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.lemon.pojo.ApiRequestParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface ApiRequestParamMapper extends BaseMapper<ApiRequestParam> {
	
	@Select("select * from api_request_param e where e.api_id =#{apiId}")
	public List<ApiRequestParam> findAll(Integer apiId);
	
	@Select("SELECT DISTINCT t1.api_request_param_value VALUE, t5.* ,t1.id valueId FROM case_param_value t1 JOIN api_request_param t5 ON t1.api_request_param_id = t5.id WHERE t1.case_id = #{caseId}")
	public  List<ApiRequestParam> findByCaseId(Integer caseId);
}
