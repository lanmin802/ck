package com.lemon.service.impl;

import java.util.List;

import com.lemon.pojo.Suite;
import com.lemon.mapper.SuiteMapper;
import com.lemon.service.SuiteService;
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
public class SuiteServiceImpl extends ServiceImpl<SuiteMapper, Suite> implements SuiteService {
	
	@Autowired
	SuiteMapper suiteMapper;
	
	public List<Suite> findSuiteAndRelatedCasesBy(Integer projectId){
		return suiteMapper.findSuiteAndRelatedCasesBy(projectId);
	}

}
