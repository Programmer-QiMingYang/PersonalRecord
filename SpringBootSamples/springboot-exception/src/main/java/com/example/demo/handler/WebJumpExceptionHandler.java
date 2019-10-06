package com.example.demo.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class WebJumpExceptionHandler {
	private static final String ERROR_VIEW="thymeleaf/error";
	
	//@ExceptionHandler(value = Exception.class)
	public ModelAndView errorHandler(HttpServletRequest request,HttpServletResponse response,Exception e) {
		//打印错误信息
		e.printStackTrace();
		//将错误和请求添加到ModelAndView
		ModelAndView mav=new ModelAndView();
		mav.addObject("exception",e);
		mav.addObject("url",request.getRequestURL());
		mav.setViewName(ERROR_VIEW);
		return mav;
	}
}
