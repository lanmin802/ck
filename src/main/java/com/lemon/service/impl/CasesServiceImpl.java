package com.lemon.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.CaseParamValue;
import com.lemon.pojo.Cases;
import com.lemon.common.ApiVo;
import com.lemon.common.CaseEditVo;
import com.lemon.common.CaseListVo;
import com.lemon.common.Result;
import com.lemon.mapper.CasesMapper;
import com.lemon.mapper.TestRuleMapper;
import com.lemon.service.CaseParamValueService;
import com.lemon.service.CasesService;
import com.lemon.service.TestRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class CasesServiceImpl extends ServiceImpl<CasesMapper, Cases> implements CasesService {
	
	@Autowired
	CaseParamValueService caseParamValueService;
	
	@Autowired
	CasesMapper casesMapper;
	
	@Autowired
	TestRuleService testRuleService;
	
	public void add(Cases caseVo,ApiVo apiVo){
		
		
			//添加到case表
		this.save(caseVo);
		
		//批量操作接口参数
		List<ApiRequestParam> apiRequestParams = apiVo.getRequestParams();
		List<CaseParamValue> caseParamValues = new ArrayList<CaseParamValue>();
		
		for(ApiRequestParam apiRequestParam:apiRequestParams){
			CaseParamValue caseParamValue = new CaseParamValue();
			caseParamValue.setCaseId(caseVo.getId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValues.add(caseParamValue);
		}
		
		
		caseParamValueService.saveBatch(caseParamValues);
		
		
		
	}

	public List<CaseListVo> showCaseUnderProject(Integer projectId) {
		return casesMapper.showCaseUnderProject(projectId);
		 
	}

	public List<CaseListVo> findCasefromSuite(Integer suiteId) {
		// TODO Auto-generated method stub
		return casesMapper.findCasefromSuite(suiteId);
	}

	public CaseEditVo  findCaseEditVo(Integer caseId){
		return casesMapper.findCaseEditVo(caseId);
	}
	
	public void updateCase(CaseEditVo caseEditVo){
		//更新case表
		
		casesMapper.updateById(caseEditVo);
		//更新caseParamValue表
		List<ApiRequestParam> apiRequestParams = caseEditVo.getRequestParams();
		List<CaseParamValue> caseParamValues = new ArrayList<CaseParamValue>();
		for (ApiRequestParam apiRequestParam:apiRequestParams){
			CaseParamValue caseParamValue = new CaseParamValue();
			caseParamValue.setId(apiRequestParam.getValueId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValue.setCaseId(caseEditVo.getId());
			caseParamValues.add(caseParamValue);
		}
		
		caseParamValueService.updateBatchById(caseParamValues);
		
		//更新或插入或删除testRule-------先删除再插入
		//testRuleService.removeById(caseEditVo.getId());
		
		QueryWrapper queryWrapper= new QueryWrapper();
		queryWrapper.eq("case_id",caseEditVo.getId() );
		testRuleService.remove(queryWrapper);
		testRuleService.saveBatch(caseEditVo.getTestRules());
		
	}
}
