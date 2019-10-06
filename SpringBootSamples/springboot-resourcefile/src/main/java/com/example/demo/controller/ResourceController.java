package com.example.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Resource;
import com.example.demo.result.JSONResult;

@RestController
@RequestMapping("res")
public class ResourceController {
	
	@Autowired
	private Resource resource;
	
	@RequestMapping("getres")
	public JSONResult getResource() {
		Resource bean=new Resource();
		BeanUtils.copyProperties(resource, bean);
		return JSONResult.ok(bean);
	}
	
}
