package com.lemon.handler;

import java.util.Date;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
@Component
public class MyMetaObjectHandler implements MetaObjectHandler{
	
	
	public void insertFill(MetaObject metaObject) {
		
		this.setFieldValByName("regtime",new Date(),metaObject);
		this.setFieldValByName("create_time",new Date(),metaObject);
		
	}

	public void updateFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		
	}

}
