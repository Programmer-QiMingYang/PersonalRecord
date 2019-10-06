package com.example.demo.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.result.JSONResult;

//@RestControllerAdvice
public class AjaxExceptionHandler {

	//@ExceptionHandler(value = Exception.class)
	public JSONResult errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		e.printStackTrace();
		return JSONResult.errorException(e.getMessage());
	}
}