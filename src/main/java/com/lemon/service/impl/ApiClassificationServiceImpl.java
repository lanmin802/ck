package com.lemon.service.impl;

import java.util.List;

import com.lemon.pojo.ApiClassification;
import com.lemon.common.ApiClassificationVo;
import com.lemon.mapper.ApiClassificationMapper;
import com.lemon.service.ApiClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@Service
public class ApiClassificationServiceImpl extends ServiceImpl<ApiClassificationMapper, ApiClassification> implements ApiClassificationService {
	
	@Autowired
	ApiClassificationMapper apiClassificationMapper;
	public List<ApiClassificationVo> getWithApi(Integer projectId){
		return apiClassificationMapper.getWithApi(projectId);
	}
}
