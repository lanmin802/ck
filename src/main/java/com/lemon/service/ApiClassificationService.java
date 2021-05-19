package com.lemon.service;

import java.util.List;

import com.lemon.common.ApiClassificationVo;
import com.lemon.pojo.ApiClassification;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface ApiClassificationService extends IService<ApiClassification> {
	public List<ApiClassificationVo> getWithApi(Integer projectId);

}
