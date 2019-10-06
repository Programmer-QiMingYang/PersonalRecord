package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.stat.DruidStatManagerFacade;

@RestController
public class DruidStatController {
	
	@GetMapping("/stat")
	public Object druidStat() {
		 // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
	}
}
