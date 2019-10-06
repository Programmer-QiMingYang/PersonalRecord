package com.example.demo.controller;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.result.JSONResult;

@RestController
@RequestMapping("hello")
public class HelloController {
	
	//hello springboot
	@RequestMapping("/sayhello")
	public String sayHello() {
		return "Hello SpringBoot!";
	}
	
	//返回一个对象
	@RequestMapping("/getuser")
	public Object getUser() {
		User user=new User();
		
		user.setUserId("001");
		user.setName("王二麻子");
		user.setPassword("123456");
		user.setBirthday(DateFormat.getDateInstance().format(new Date()));
		user.setDesc("自我介绍一下：我叫王二麻子，是个...");
		
		return user;
	}
	
	//统一结果集返回数据
	@RequestMapping("/user")
	public JSONResult getAllUser() {
		User user=new User();
		user.setUserId("001");
		user.setName("王二麻子");
		user.setPassword("123456");
		user.setBirthday(DateFormat.getDateInstance().format(new Date()));
		user.setDesc("自我介绍一下：我叫王二麻子，是个...");
		
		return JSONResult.ok(user);
	}
}
