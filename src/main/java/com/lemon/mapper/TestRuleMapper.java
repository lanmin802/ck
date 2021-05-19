package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.lemon.pojo.TestRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface TestRuleMapper extends BaseMapper<TestRule> {
	
	@Select("select * from test_rule t where t.case_id =#{caseId}")
	public List<TestRule> findByCase(Integer caseId);
	

}
