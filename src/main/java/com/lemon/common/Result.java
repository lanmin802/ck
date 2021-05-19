package com.lemon.common;

import lombok.Data;

@Data
public class Result {
	
	private String status;
	private String message;
	private Object data;
	
	//失败时
	public Result(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	//成功时
	public Result(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}
	public Result(String status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	

	

	
}
