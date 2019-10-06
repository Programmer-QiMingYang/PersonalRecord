### SpringBoot整合定时人去

[TOC]

#### 一、SpringBoot整合定时任务

##### （1）开启定时任务

在SpringBoot启动类添加`@EnableScheduling`注解，打开定时任务

##### （2）编写定时任务类

```java
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
```

在类上面加上了`@Component`注解，在方法上加上了`@Scheduled(fixedDelay = 3000)`注解。

fixedDelay使用很少，我这里比较常用是cron表达式。

##### （3）cron表达式

cron表达式：秒、分、时、日、月、年、周

SpringBoot不支持年，**只有6位：秒、分、时、日、月、周**

比如每秒执行一次：`@Scheduled(cron="* * * * * ?")//cron表达式：每秒执行一次`

#### 二、SpringBoot整合异步任务

##### （1）开启异步任务

在SpirngBoot启动类中添加`@EnableAsync`注解开启异步任务

##### （2）编写任务类

```java
package com.example.demo.task;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {
	
	@Async
	public Future<Boolean> doTask1() throws Exception{
		long start=System.currentTimeMillis();
		Thread.sleep(1000);
		long end=System.currentTimeMillis();
		System.out.println("任务1耗时："+(end-start)+"毫秒");
		return new AsyncResult<Boolean>(true);
	}
	
	@Async
	public Future<Boolean> doTask2() throws Exception{
		long start=System.currentTimeMillis();
		Thread.sleep(1000);
		long end=System.currentTimeMillis();
		System.out.println("任务2耗时："+(end-start)+"毫秒");
		return new AsyncResult<Boolean>(true);
	}
	
	@Async
	public Future<Boolean> doTask3() throws Exception{
		long start=System.currentTimeMillis();
		Thread.sleep(1000);
		long end=System.currentTimeMillis();
		System.out.println("任务3耗时："+(end-start)+"毫秒");
		return new AsyncResult<Boolean>(true);
	}
}

```

在类上添加`@Component`注解

在方法上添加`@Async`注解

##### （3）调用任务类

```java
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
		
        //确保三个线程都执行完毕
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
```

整个过程还是比较简单的。

##### （4）线程同步类java.util.concurrent.Future

Java 在并发方面引入了 「 将来 」( Future ) 这个概念。把所有不在主线程执行的代码都附加了将来这个灵魂。主线程只负责其它并发线程的创建、启动、监视和处理并发线程完成任务或发生异常时的回调。其它情况，则交给并发线程自己去处理。而双方之间的沟通，就是通过一个个被称之为 「 将来 」 的类出处理。

`Future` 定义在 `java.util.concurrent` 包中，这是一个接口，自 Java 1.5 以来一直存在的接口，用于处理异步调用和处理并发编程。

Future一般用于比较耗时的操作，但不处理又不行，那么就可以把这个任务交给Future来处理，我们可以做其他的事情，等Future处理好这个耗时的任务后，会返回给我们处理后信息。

下面这篇博文是对Future模式的理解:`https://www.cnblogs.com/cz123/p/7693064.html`值得参考一下。

#### 三、异步任务的使用场景

使用场景比较多了：发送邮件、短信验证码、App消息推送.....