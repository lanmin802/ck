package com.lemon.service;

import java.util.List;

import com.lemon.common.CaseEditVo;
import com.lemon.common.ReportVo;
import com.lemon.pojo.TestReport;
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
public interface TestReportService extends IService<TestReport> {
	public List<TestReport> run(Integer SuiteId) throws JsonProcessingException;

	public TestReport findByCaseId(Integer caseId);

	public ReportVo getReport(Integer suiteId);

}
