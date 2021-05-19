package com.lemon.service;

import java.util.List;

import com.lemon.common.ApiListVo;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVo;
import com.lemon.pojo.Api;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface ApiService extends IService<Api> {
	public List<ApiListVo> showApiListByProject(Integer projectId);
	
	public List<ApiListVo> showApiListByApiClassification(Integer apiClassificationId);
	
	public ApiVo findApiViewVo(Integer apiId);

	public ApiRunResult run(ApiVo apiVo) throws JsonProcessingException;

}
