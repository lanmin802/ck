package com.lemon.service;

import java.util.List;

import com.lemon.pojo.Suite;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface SuiteService extends IService<Suite> {
	public List<Suite> findSuiteAndRelatedCasesBy(Integer projectId);

}
