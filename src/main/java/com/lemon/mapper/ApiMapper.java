package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lemon.common.ApiListVo;
import com.lemon.common.ApiVo;
import com.lemon.pojo.Api;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface ApiMapper extends BaseMapper<Api> {
	
	@Select("select * from api where api_classification_id = #{apiclassificationId}")
	public List<Api> findApi(Integer apiClassificationId);
	
	@Select("select t1.id,t1.`name`,t1.method,t1.url,t2.`name`  classificationName  from api t1,api_classification t2 where t2.project_id=#{projectId} and t1.api_classification_id=t2.id;")
	public List<ApiListVo> showApiListByProject(Integer projectId) ;
	
	@Select("select t1.id,t1.`name`,t1.method,t1.url,t2.`name` classificationName from api t1,api_classification t2 where t2.id=#{apiClassificationId} and t1.api_classification_id=t2.id;")
	public List<ApiListVo> showApiListByApiClassification(Integer apiClassificationId);
	
	@Select("select t1.*,t2.username createUserName from api t1,`user` t2 where t1.id=#{apiId} and t1.create_user=t2.id;")
	@Results({
	@Result(property = "id",column = "id"),
	@Result(property = "name",column = "name"),
	@Result(property = "url",column = "url"),
	@Result(property = "requestParams", column="id",javaType=List.class, many =@Many(select="com.lemon.mapper.ApiRequestParamMapper.findAll"))})
	public ApiVo findApiViewVo(Integer apiId);
	
	
	
	

}
