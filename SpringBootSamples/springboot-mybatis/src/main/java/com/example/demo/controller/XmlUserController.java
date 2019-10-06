package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.result.JSONResult;
import com.example.demo.service.XmlUserService;

@RestController
@RequestMapping("xmlUser")
public class XmlUserController {
	
	@Autowired
	private XmlUserService userService;
	
	@RequestMapping("/queryUser")
	public JSONResult queryUser(String id) {
		return JSONResult.ok(userService.queryUser(id));
	}
}
