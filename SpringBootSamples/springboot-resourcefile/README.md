### SpringBoot资源属性文件读取



<nav>
    <a href="#一、SpringBoot资源属性文件读取">一、SpringBoot资源属性文件读取</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1需求信息">1、需求信息</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2创建resource.properties">2、创建resource.properties</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#3pom文件引入依赖">3、pom文件引入依赖</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#4编写pojo类">4、编写pojo类</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#5测试结果">5、测试结果</a><br/>
</nav>


##### 
#### 一、SpringBoot资源属性文件读取

##### 1、需求信息

需求：资源文件信息提取

案例描述需求：resource.properties里面存储了网站的信息，有name，website，language等信息，我们想要提取输出。

##### 2、创建resource.properties

```properties
fit.programmer.www.name=practise
fit.programmer.www.website=www.programmer.fit
fit.programmer.www.language=java
```

##### 3、pom文件引入依赖

```xml
<!-- 配置文件处理器，资源文件映射 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>	
</dependency>
```

##### 4、编写pojo类

```java
@Configuration//代表这是一个配置
@ConfigurationProperties(prefix = "fit.programmer.www")//这个是前缀，映射的时候，只会把前缀后面的字段映射进来
@PropertySource(value = "classpath:resource.properties")//路径地址
public class Resource {
	private String name;
	private String website;
	private String language;
	//对应的get和set方法	
}
```

##### 2.5 测试结果

```java
@RestController
@RequestMapping("res")
public class ResourceController {
	
	@Autowired
	private Resource resource;
	
	@RequestMapping("/getres")
	public Object getResource() {
		Resource bean=new Resource();
		BeanUtils.copyProperties(resource, bean);
		return JSONResult.ok(bean);
	}

}
```

结果:

```json
{
    "status":200,
    "msg":"OK",
    "data":{
        "name":"rubbish",
        "website":"www.programmer.fit",
        "language":"java"
    },
    "ok":null
}
```





