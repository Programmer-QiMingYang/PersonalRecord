package com.example.demo.task.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task01 {
	@Scheduled(fixedDelay = 3000)
	public void reportCurrentTime() {
		System.out.println("现在时间"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
	}
}
