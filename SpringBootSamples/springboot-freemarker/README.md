### SpringBoot整合Freemarker模板引擎

<nav>
    <a href="#1需求信息分析">1、需求信息分析</a><br/>
    <a href="#2pom文件依赖">2、pom文件依赖</a><br/>
    <a href="#3Application.properties配置">3、Application.properties配置</a><br/>
    <a href="#4index.ftl模板文件">4、index.ftl模板文件</a><br/>
    <a href="#5FreemarkerController">5、FreemarkerController</a><br/>
    <a href="#6Spring家族的Model、ModelAndView、ModelMap">6、Spring家族的Model、ModelAndView、ModelMap</a><br/>
</nav>



##### 1、需求信息分析

需求：将resource里面的内容输出到页面，页面采用freemarker模板引擎

##### 2、pom文件依赖

```xml
<!--freemarker模板引擎 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

##### 3、Application.properties配置

```properties
##############################
#freemarker 静态模板引擎
##############################
#设定ftl文件路径
spring.freemarker.template-loader-path=classpath:/templates
# 关闭缓存，即时刷新，上线生产环境需要改为true
spring.freemarker.cache=false
spring.freemarker.charset=UTF-8
spring.freemarker.check-template-location=true
spring.freemarker.content-type=text/html
spring.freemarker.expose-request-attributes=true
spring.freemarker.expose-session-attributes=true
spring.freemarker.request-context-attribute=request
spring.freemarker.suffix=.ftl
```

##### 4、index.ftl模板文件

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>freemarker模板引擎index页面</h1>
	<h1>${resource.name}</h1>
	<h1>${resource.website}</h1>
	<h1>${resource.language}</h1>
</body>
</html>
```

##### 5、FreemarkerController

```java
@Controller
@RequestMapping("ftl")
public class FreemarkerController {
	@Autowired
	private Resource resource;
	
	@RequestMapping("/index")
	public String index(ModelMap map) {
		map.addAttribute("resource",resource);
		return "ftl/index";
	}
}
```

我们可以看出，都已经被正确解析并且输入到freemarker模板引擎页面上。

##### 6、Spring家族的Model、ModelAndView、ModelMap

**概述**

Model是一个接口，包含addAttribute方法，其实现类是ExtendedModelMap。ExtendedModelMap继承了ModelMap类，ModelMap类实现了Map接口。

ModelMap继承LinkedHashMap，Spring框架自动创建实力并作为controller的入参，所以用户无需自己创建。

ModelAndView：指的是模型和视图的集合，需要开发者自己手动创建，这也是和ModelMap的主要区别之一。

**区别**

第一点：Model只是用来传输数据的，并不会进行业务的寻址。ModelAndView 却是可以进行业务寻址的；所以Model的返回值是url地址，而ModelAndView的返回值是ModelAndView对象；

第二点：Model是每一次请求可以自动创建，但是ModelAndView 是需要我们自己去new的。所以使用Model时Controller的参数是Model。
**使用**
Model的使用

　　数据传递：Model是通过addAttribute方法向页面传递数据的；

　　数据获取：JSP页面可以通过el表达式或C标签库的方法获取数据；

　　return：return返回的是指定的页面路径

ModelMap的使用

　　ModelMap的使用与Model相同，ModelMap是一种特殊的Model，一般来说，Model可以接收各种类型的数据，如果接收的数据是List，那么这个时候Model实际上是ModelMap。

ModelAndView的使用

　　数据传递：ModelAndView通过addObject方法向页面传递数据；

　　数据获取：JSP页面可以通过el表达式或C标签库的方法获取数据（与Model的获取方式相同）；

　　return：return返回的是ModelAndView对象；

　　ModelAndView设置跳转地址有两个方式：

　　第一种：在new ModelAndView时添加地址参数，如：

```java
ModelAndView mav = new ModelAndView("test");
```

　　第二种：使用ModelAndView的setViewname(String)方法去设置，如：

```java
mav.setViewName("test");
```


