package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("one")
public class OneController {

	@RequestMapping("/string")
    public String oncontroller(ModelMap map) { 
		
       return "center";
    }
	
	
}