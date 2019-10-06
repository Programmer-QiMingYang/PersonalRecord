### SpringBoot使用拦截器

[TOC]

使用拦截器的步骤：

- 编写拦截器类

- 使用@configuration配置拦截器

- 实现webmvcconfigurer类，springboot1.x是继承webmvcconfigureradapter类

- 重写addInterceptors
- 手动添加到拦截器

#### 一、编写拦截器类

```java
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.result.JSONResult;
import com.example.demo.utils.JsonUtils;

@Component
public class OneInterceptor implements HandlerInterceptor {

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		System.out.println("被one拦截，放行...");
		return true;

		/*
		 * if (true) { returnErrorResponse(response, JSONResult.errorMsg("被one拦截..."));
		 * }
		 * 
		 * return false;
		 */
	}

	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("视图渲染之前，拦截");

	}

	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行 （主要是用于进行资源清理工作）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void returnErrorResponse(HttpServletResponse response, JSONResult result)
			throws IOException, UnsupportedEncodingException {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			out = response.getOutputStream();
			out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}

```

```java 
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.result.JSONResult;
import com.example.demo.utils.JsonUtils;

@Component
public class TwoInterceptor implements HandlerInterceptor {

	final static Logger log = LoggerFactory.getLogger(TwoInterceptor.class);

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		if (true) {
			returnErrorResponse(response, JSONResult.errorMsg("被two拦截..."));
		}

		System.out.println("被two拦截...");

		return false;
	}

	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void returnErrorResponse(HttpServletResponse response, JSONResult result)
			throws IOException, UnsupportedEncodingException {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			out = response.getOutputStream();
			out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}

```

#### 二、编写config

```java
@Configuration
public class MyConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//添加到拦截器，拦截器按照顺序来执行
		registry.addInterceptor(new OneInterceptor()).addPathPatterns("/one/**");
		registry.addInterceptor(new TwoInterceptor()).addPathPatterns("/two/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}	
}
```

@Configuration表明我们当前是一个适配器

SpringBoot 确实为我们做了很多事情， 但有时候我们想要自己定义一些Handler，Interceptor，ViewResolver，MessageConverter，该怎么做呢。在Spring Boot 1.5版本都是靠重写WebMvcConfigurerAdapter的方法来添加自定义拦截器，消息转换器等。SpringBoot 2.0 后，该类被标记为@Deprecated。因此我们只能靠实现WebMvcConfigurer接口来实现。

#### 三、编写测试类

```java
@Controller
@RequestMapping("one")
public class OneController {

	@RequestMapping("/string")
    public String oncontroller(ModelMap map) { 		
       return "center";
    }	
}
```

```java
@RestController
@RequestMapping("two")
public class TwoController {

	@RequestMapping("/json")
    public JSONResult twocontroller() {
        return JSONResult.ok("two controller");
    }	
}
```

通过运行我们可以清晰看到我们的访问请求被拦截。

#### 四、HandlerInterceptor类

##### （1）执行过程简析

单个HandlerInterceptor实现类的执行过程
preHandler() -> Controller -> postHandler() -> model渲染-> afterCompletion()

多个HandlerInterceptor实现类的执行过程
1.preHandler()
2.preHandler()
Controller
2.postHandler()
1.postHandler()
2.afterCompletion()
1.afterCompletion()



##### （2）总结

Web开发中，我们除了使用 Filter 来过滤请web求外，还可以使用Spring提供的HandlerInterceptor（拦截器）。

HandlerInterceptor 的功能跟**过滤器**类似，但是提供更精细的的控制能力：在request被响应之前、request被响应之后、视图渲染之前以及request全部结束之后。我们**不能通过拦截器修改request内容**，**但是可以通过抛出异常（或者返回false）来暂停request的执行**。

只有经过DispatcherServlet 的请求，才会走拦截器链，我们自定义的Servlet 请求是不会被拦截的，比如我们自定义的Servlet地址`http://localhost:8080/myServlet1` 是不会被拦截器拦截的。并且不管是属于哪个Servlet 只要复合过滤器的过滤规则，过滤器都会拦截。

##### （3）使用场景

拦截器最常用的登录拦截、或是权限校验、或是防重复提交、或是根据业务像12306去校验购票时间,总之可以去做很多的事情。

