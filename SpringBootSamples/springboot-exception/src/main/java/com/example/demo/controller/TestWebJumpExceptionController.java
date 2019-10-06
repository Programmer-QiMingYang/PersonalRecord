package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class TestWebJumpExceptionController {

	@RequestMapping("/test01")
	public Object test01() {
		int a=1/0;
		return "thymeleaf/test01";
	}
}
