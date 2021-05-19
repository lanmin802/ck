package com.lemon.controller;


import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.Accessors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.User;
import com.lemon.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lm
 * @since 2021-05-07
 */
@RestController
@RequestMapping("/user")
@Api("用户模块")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	//注册
	//@RequestMapping("/register")//默认get
	@PostMapping("/register")
	@ApiOperation(value="注册方法",httpMethod="POST")
	public Result register(User user){
		
		user.setRegtime(new Date());
		
		//调用业务层方法，插入数据库，统一处理异常
		userService.save(user);//返回值是boolean
		Result result = new Result("1", "成功");
		return result;
		
	}
	
	//账号验重
	@GetMapping("/find")
	@ApiOperation(value="验重方法",httpMethod="GET")
	public Result find(String username){
		//调用业务层方法，查询非主键列，统一处理异常
		Result result = null;
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("username", username);//列名，前端对应的输入名
		User user = userService.getOne(queryWrapper);
		if (user == null) {
			result = new Result("1", "用户名未注册");
		}else {
			result = new Result("0", "用户名已注册");
		}
		
		return result;
		
	}
	
	//登录
	//@RequestMapping("/register")//默认get
	@PostMapping("/login")
	@ApiOperation(value="登录方法",httpMethod="POST")
	public Result login(User user){
		Result result = null;
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		
		//shiro
		Subject subject=SecurityUtils.getSubject();
		try{
		subject.login(usernamePasswordToken);
		//返回sessionId
		String sessionId = (String) subject.getSession().getId();
		User loginUser = (User) subject.getPrincipal();
		result = new Result("1", sessionId,loginUser.getId());
		}catch(AuthenticationException e){
			if(e instanceof UnknownAccountException){
				result=new Result("0", "用户名错误");
			}else {
				result = new Result("0", "密码错误");
			}
			e.printStackTrace();
		}
		//调用业务层方法，查询数据库，根据username,统一处理异常
		
		return result;
		
	}
	
	@GetMapping("/logout")
	@ApiOperation(value="退出",httpMethod="GET")
	public Result logout(){
		Result result = null;
		//从shiro退出，统一处理异常
		SecurityUtils.getSubject().logout();
		result = new Result("1", "账号未登录");
		return result;
		
	}
	
	@GetMapping("/unauth")
	@ApiOperation(value="未授权方法",httpMethod="GET")
	public Result unauth(){
		Result result = null;

		result = new Result("1", "账号未登录");
		return result;
		
	}
}
