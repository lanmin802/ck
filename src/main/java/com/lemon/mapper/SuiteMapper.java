package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lemon.pojo.Suite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface SuiteMapper extends BaseMapper<Suite> {
	
	@Select("select * from suite s where s.project_id = #{projectId}")
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "name",column = "name"),
		@Result(property = "cases", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.CasesMapper.findBySuite"))})
	public List<Suite> findSuiteAndRelatedCasesBy(Integer projectId);
		
	

}
