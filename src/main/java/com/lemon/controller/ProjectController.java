package com.lemon.controller;


import java.awt.print.Printable;
import java.util.List;

import javax.websocket.server.PathParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.Project;
import com.lemon.pojo.User;
import com.lemon.service.ProjectService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@RestController
@RequestMapping("/project")
@Api("项目模块")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@GetMapping("/toList")
	@ApiOperation(value="注册方法",httpMethod="GET")
	public Result toList(Integer userId) {
		
		Result result = null;
		QueryWrapper<Project> queryWrapper = new QueryWrapper();
		queryWrapper.eq("create_user",userId );
		List<Project> myProjects = projectService.list(queryWrapper);
		result = new Result("1", "项目列表", myProjects);
		return result;
	}
	
	@PostMapping("/add")
	@ApiOperation(value="注册方法",httpMethod="GET")
	public Result add(Project project) {
		
		Result result = null;
		User user = (User) SecurityUtils.getSubject().getPrincipal();//得到用户
		project.setCreateUser(user.getId());
		projectService.save(project);
		result = new Result("1", "添加项目成功");
		return result;
	}
	
	@GetMapping("/{projectId}")
	@ApiOperation(value="根据ID查询项目",httpMethod="GET")
	public Result getById(@PathVariable("projectId") Integer projectId) {
		
		Result result = null;
		Project project = projectService.getById(projectId);
		result = new Result("1", "查询项目",project);
		return result;
	}
	
	@PutMapping("/{projectId}")
	@ApiOperation(value="更新项目",httpMethod="POST")
	public Result putById(@PathVariable("projectId") Integer projectId,Project project) {
		
		Result result = null;
		project.setId(projectId);
		User user = (User) SecurityUtils.getSubject().getPrincipal();//得到用户
		project.setCreateUser(user.getId());
		projectService.updateById(project);
		result = new Result("1", "更新项目",project);
		return result;
	}
	
	@DeleteMapping("/{projectId}")
	@ApiOperation(value="删除项目",httpMethod="DELETE")
	public Result deleteById(@PathVariable("projectId") Integer projectId) {
		
		Result result = null;
		projectService.removeById(projectId);
		result = new Result("1", "删除项目成功");
		return result;
	}
	

}
