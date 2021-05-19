package com.lemon.service;

import java.util.List;

import com.lemon.common.ApiVo;
import com.lemon.common.CaseEditVo;
import com.lemon.common.CaseListVo;
import com.lemon.pojo.Cases;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
public interface CasesService extends IService<Cases> {
	

	public void add(Cases caseVo, ApiVo apiVo);
	
	public List<CaseListVo> showCaseUnderProject(Integer projectId);
	
	public List<CaseListVo> findCasefromSuite(Integer suiteId);
	
	public CaseEditVo  findCaseEditVo(Integer caseId);

	public void updateCase(CaseEditVo caseEditVo);

}
