package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.result.JSONResult;

@Controller
@RequestMapping("ajaxerror")
public class TestAjaxExceptionController {
	
	@RequestMapping("/ajaxerror")
	public String ajaxError() {
		return "thymeleaf/ajaxerror";
	}
	
	@RequestMapping("/ajaxexception")
	@ResponseBody
	public JSONResult ajaxException() {
		int a=1/0;
		return JSONResult.ok();
	}
}
