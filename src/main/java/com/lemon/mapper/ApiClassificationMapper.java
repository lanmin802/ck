package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;

import com.lemon.common.ApiClassificationVo;
import com.lemon.pojo.ApiClassification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface ApiClassificationMapper extends BaseMapper<ApiClassification> {
	
	//两表延迟加载，先查询分类信息(List<Api>)，然后按需加载(此时查另一张表)
	
	@Select("select * from api_classification where project_id = #{projectId}")
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "name",column = "name"),
		@Result(property = "projectId",column = "project_id"),
		@Result(property = "apis", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.ApiMapper.findApi"))})
	public List<ApiClassificationVo> getWithApi(Integer projectId);

}
