package com.example.demo.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.result.JSONResult;

/**
 * 全局异常捕获类
 * @author Programmer_qmy
 *
 */

@RestControllerAdvice
public class AllExceptionhandler {
	
	private static final String ERROR_VIEW="thymeleaf/error";
	
	@ExceptionHandler(value = Exception.class)
	public Object exceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception e) {
		e.printStackTrace();
		
		if (isAjax(request)) {
			return JSONResult.errorException(e.getMessage());
		}else {
			ModelAndView mav=new ModelAndView();
			mav.addObject("exception",e);
			mav.addObject("url",request.getRequestURL());
			mav.setViewName(ERROR_VIEW);
			return mav;
		}
	}
	
	private boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-with")!=null)&&("XMLHttpRequest".equals(request.getHeader("X-Requested-with").toString()));
	}

}
