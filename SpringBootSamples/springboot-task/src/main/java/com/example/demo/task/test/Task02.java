package com.example.demo.task.test;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.task.AsyncTask;

@RestController
@RequestMapping("task")
public class Task02 {

	@Autowired
	private AsyncTask aTask;
	
	@RequestMapping("/async")
	public String async() throws Exception{
		long start =System.currentTimeMillis();
		
		//调用任务
		Future<Boolean> task1=aTask.doTask1();
		Future<Boolean> task2=aTask.doTask2();
		Future<Boolean> task3=aTask.doTask3();
		
		while(!task1.isDone() || !task2.isDone()|| !task3.isDone()) {
			if (task1.isDone()&&task2.isDone()&&task3.isDone()) {
				break;
			}
		}
		
		long end=System.currentTimeMillis();
		String time = String.valueOf((end-start));
		System.out.println("总耗时:"+time+"毫秒");
		return "总耗时："+time+"毫秒";
	}
}
