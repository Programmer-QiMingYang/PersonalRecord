package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.result.JSONResult;



@RestController
@RequestMapping("two")
public class TwoController {

	@RequestMapping("/json")
    public JSONResult twocontroller() {
        return JSONResult.ok("two controller");
    }
	
	
}