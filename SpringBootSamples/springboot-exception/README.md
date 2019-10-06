### SpringBoot异常处理

[TOC]



#### 一、分析，准备

##### （1）分析

方便调试：devtools

错误页面：thymeleaf

错误维护者信息：i18n

实现ajax跳转：jquery.js

统一结果集封装类：JSONResult.java

##### （2）pom依赖引入

```xml
<!-- devtols热启动 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <!-- optional=true ,依赖不会传递，该项目依赖devtools； -->
    <!-- 之后依赖的boot项目如果想要使用devtools，需要重新引入 -->
    <optional>true</optional>
</dependency>

<!-- themeleaf模板引擎 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
		
```

##### （3）Application.properties配置文件

```properties
#############################
#devtools
#############################
spring.devtools.restart.enabled=true
################################
# thymeleaf模板配置
################################
#关闭缓存，即时刷新，上线生产环境需要改为true
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.servlet.content-type=text/html; charset=utf-8
#spring.thymeleaf.content-type=text/html这个已经被替代
############################
## i18n配置,一般可以利用它做国际化
############################
spring.messages.basename=i18n/person
#spring.messages.cache-seconds=3600这个已经被抛弃了，使用下面的
spring.messages.cache-duration=3600
spring.messages.encoding=UTF-8
############################
# 设定静态文件路径js/css等
############################
#我们要使用的静态js和css文件就去static下面去找
spring.mvc.static-path-pattern=/static/**
```

##### （4）维护者者资源文件person.properties

```properties
#########################
## 系统人员信息
########################
person.name=Programmer_qmy
person.email=programmer_qmy@163.com
```

#### 二、异常捕获-web页面跳转方式

##### （1）异常类捕获类

```java
@ControllerAdvice
public class WebJumpExceptionHandler {
	private static final String ERROR_VIEW="thymeleaf/error";
	
	@ExceptionHandler(value = Exception.class)
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
```

##### （2）错误页面

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<div>
			<h1 style="color:red">有错误了</h1><br/>
			错误请求地址：<span th:text="${url}">发生错误的请求</span><br/>
			错误信息描述：<span th:text="${exception.message}">错误的信息</span><br/>
		</div>
		<hr />
		<div>
			<h4>紧急联系</h4><br/>
			联系人：<span th:text="#{person.name}">联系人</span><br/>
			联系方式：<span th:text="#{person.email}">联系方式</span><br/>
		</div>
	</body>
</html>
```

##### （3）异常捕获测试类

```java
@RequestMapping("/test01")
public Object test01() {
    int a=1/0;//手动创建一个 by zero的错误
    return "thymeleaf/test01";
}
```

##### （4）总结

流程分析：

1. 访问路由test01
2. test01程序启动
3. 执行到int a=1/0,出现ArithmeticException: / by zero
4. 异常捕获类捕获所有exception
5. 异常捕获handler将异常e的信息和请求连接存入ModelAndView中
6. 返回错误页面

注解：

@ControllerAdvice：对controller进行增强

源码：

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//bean对象交给spring管理生成
@Component
public @interface ControllerAdvice {
    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] assignableTypes() default {};

    Class<? extends Annotation>[] annotations() default {};
}
```

@ExceptionHandler：统一处理某一类异常，从而能够减少代码重复率和复杂度

源码：

```java
//该注解作用对象为方法
@Target({ElementType.METHOD})
//在运行时有效
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandler {
	//value()可以指定异常类
    Class<? extends Throwable>[] value() default {};
}
```

当@ExceptionHandler单独使用，只对本类有用，只有结合@ControllerAdvice才能实现全局异常类，**本质上就是AOP**的体现。

#### 三、异常捕获-ajax形式

##### （1）异常捕获类

```java
@RestControllerAdvice
public class AjaxExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public JSONResult errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		e.printStackTrace();
		return JSONResult.errorException(e.getMessage());
	}
}
```

##### （2）错误页面

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script th:src="@{/static/js/jquery.min.js}"></script>
	</head>
	<body>
		<h1>测试ajax异常</h1>
		<script th:src="@{/static/js/ajaxerror.js}"></script>
	</body>
</html>
```

##### （3）模拟ajax异步请求

```js
$.ajax({
    url: "/ajaxerror/ajaxexception",
    type: "POST",
    async: false,
    success: function(data) {
        debugger;
        if(data.status == 200 && data.msg == "OK") {
            alert("success");
        } else {
            alert("发生异常：" + data.msg);
        }
    },
    error: function (response, ajaxOptions, thrownError) {
        debugger;
        alert("error");       
    }
});
```

##### （4）异常捕获测试类

```java
@Controller
@RequestMapping("ajaxerror")
public class TestAjaxExceptionController {
	
	@RequestMapping("/ajaxerror")
	public String ajaxError() {
		return "thymeleaf/ajaxerror";
	}
	
	@RequestMapping("/ajaxexception")
	@ResponseBody
	public JSONResult ajaxException() {
		int a=1/0;
		return JSONResult.ok();
	}
}
```

##### （5）总结

1. 请求地址
2. return "thymeleaf/ajaxerror"
3. 在html里面加载js文件,加载ajaxerror.js
4. 发送ajax异步请求/ajaxerror/ajaxexception
5. ajaxexception路由程序里面a=1/0，出现异常，
6. 被全局异常类AjaxExceptionHandler捕获，将e.getMessage错误信息存入JSONResult.errorException，
7. JSONResult.errorException里面的status我们定义的是555
8. 直接跳转到ajaxerror.js判断失败的情况，alert（"发生异常：" + data.msg）
9. 点击确定，返回ajaxerror.html输出“测试ajax异常”。当然第9步并不是我们最想要的，可有可无。

#### 四、异常捕获-同时兼容web与ajax形式

##### （1）分析

主要是判断请求是否是异步请求

##### （2）全局异常类

```java
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
	
    //通用判断请求是否是ajax请求
	private boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-with")!=null)&&("XMLHttpRequest".equals(request.getHeader("X-Requested-with").toString()));
	}

}

```

##### （3）总结

@ControllerAdvice：当使用ajax请求时候，会发生异常

`org.thymeleaf.exceptions.TemplateInputException: Error resolving template [ajaxerror/ajaxexception], template might not exist or might not be accessible by any of the configured Template Resolvers`大概意思是没有解析到

实际开发如果想要使用可以给自己在开发一个，用来解析这种情况的发生。

@RestControllerAdvice：则是两种异常都ok。

**两者的异同**：

两者的区别应该是类似Controller和RestController的区别吧，希望有人指教。