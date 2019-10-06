### SpringBoot基本入门

----
![sts](https://img.shields.io/static/v1?label=STS&message=4.3.2.RELEASE&color=brightgreen)&nbsp;&nbsp;![springboot](https://img.shields.io/static/v1?label=springboot&message=2.1.8.RELEASE&color=brightgreen)&nbsp;&nbsp;


<nav>
    <a href="#一使用sts构建SpringBoot工程">一、使用sts构建SpringBoot工程</a><br/>
    <a href="#二返回一个对象">二、返回一个对象</a><br/>
    <a href="#三统一结果集封装">三、统一结果集封装</a><br/>
    <a href="#四Jackson常用注解">四、Jackson常用注解</a><br/>
    <a href="#五devtools热启动">五、devtools热启动</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#5.1pom文件添加依赖">5.1 pom文件添加依赖</a><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#5.2Application.properties配置">5.2 Application.properties配置</a><br/>
</nav>



#### 一、使用sts构建SpringBoot工程

我们这里使用的maven，所以你首先配置好你的maven。

alt+shift+n弹出选项，选择Spring Starter Project

![](README.assets/%E6%9E%84%E5%BB%BASpringBoot%E5%B7%A5%E7%A8%8B.png)

| Name         | 中文解释 | Name         | 中文解释     | Name        | 中文解释 |
| ------------ | -------- | ------------ | ------------ | ----------- | -------- |
| Name         | 项目全名 | Location     | 项目工作空间 | Type        | 构建方式 |
| Java version | jdk版本  | Language     | 语言         | Group       | 组织名称 |
| Artifact     | 项目名称 | Version      | 版本         | Description | 描述     |
| Package      | 包名     | Working sets | 工作集合     | Packaging   | 打包方式 |

- Artifact：项目名称，用来区分同一个groupId下面的子项目，一般和Name同名。
- Group：组织名称，很多时候与包名相同
- Working sets：工作集，管理project的
- Packaging：打包方式，jar或者war

填好以后选择next下一步，选择你想要用的starter；如下图：

![](README.assets/%E6%9E%84%E5%BB%BASpringBoot%E5%B7%A5%E7%A8%8B2.png)

你可以什么都不选，直接点击finish完成进入项目,项目结构如下:

![](README.assets/%E6%9E%84%E5%BB%BASpringBoot%E5%B7%A5%E7%A8%8B3%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png)

pom文件引入web依赖

```xml
<!-- SpringBoot web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

新建controller包，在controller包创建HelloController.java文件

```java
package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {
	
	@RequestMapping("sayhello")
	public String sayHello() {
		return "Hello SpringBoot!";
	}
}

```

启动`@SpringBootApplication`注解的启动类。浏览器打开：`localhost:8080/hello/sayhello`，就可以看到结果了。

@RestController返回的是一个json对象，不是json字符串，相当于@Controller+@ResponseBody。

@Controller返回的是一个一个view对象，需要视图解析器或者模板引擎来解析呈现。

#### 二、返回一个对象

实际开发中，我们经常是返回一个对象。

创建user对象

```java
package com.example.demo.pojo;

public class User {
	private String userId;// 用户id
	private String name;// 用户姓名
	private String password;// 用户密码
	private String birthday;// 用户生日
	private String desc;// 用户描述
	
    //getter and setter...
}

```

创建controller方法

```java
@RequestMapping("/getuser")
public Object getUser() {
    User user=new User();

    user.setUserId("001");
    user.setName("王二麻子");
    user.setPassword("123456");
    user.setBirthday(DateFormat.getDateInstance().format(new Date()));
    user.setDesc("自我介绍一下：我叫王二麻子，是个...");

    return user;
}
```

结果：

```json
{"userId":"001","name":"王二麻子","password":"123456","birthday":"2019-9-18","desc":"自我介绍一下：我叫王二麻子，是个..."}
```

#### 三、统一结果集封装

从上面看出结果是返回来了，但是开发中一般都有固定格式的返回结果。自定义返回结果集参考JESONResult.java。

创建controller方法，用结果集封装返回：

```java
//统一结果集返回数据
@RequestMapping("/user")
public JSONResult getAllUser() {
    User user=new User();

    user.setUserId("001");
    user.setName("王二麻子");
    user.setPassword("123456");
    user.setBirthday(DateFormat.getDateInstance().format(new Date()));
    user.setDesc("自我介绍一下：我叫王二麻子，是个...");

    return JSONResult.ok(user);
}
```

结果如下：

```json
{
    "status":200,
    "msg":"OK",
    "data":{
        "userId":"001",
        "name":"王二麻子",
        "password":"123456",
        "birthday":"2019-9-18",
        "desc":"自我介绍一下：我叫王二麻子，是个..."
    },
    "ok":null
}
```

感觉还是这种格式亲切一点。

#### 四、Jackson常用注解

到这里感觉还是不是很满意，比如我们肯定不能让密码这样的敏感信息暴露，那么我们可以使用一些Jackson的常用注解来满足我们的要求。

```java
@JsonIgnore//不显示该注解的字段信息，比如id
@JsonFormat(pattern="yyyy/MM/dd",locale="zh",timezone="GMT")//格式化时间，我们按照中国的时间，东八区，格式：yyyy/MM/dd
@JsonInclude(Include.NON_NULL)//当有内容我们显示，没有我们就不显示
```

比如这里我们不需要显示密码，那我们就在User类的password上面添加@JsonIgnore，在返回的结果中就没有password的字段信息了。

#### 五、devtools热启动

##### 5.1 pom文件添加依赖

```xml
<!-- devtols热启动 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <!-- optional=true ,依赖不会传递，该项目依赖devtools； -->
    <!-- 之后依赖的boot项目如果想要使用devtools，需要重新引入 -->
    <optional>true</optional>
</dependency>
```

##### 5.2 Application.properties配置

```properties
#######################
## devtools
#######################
#热部署生效
spring.devtools.restart.enabled=true
#设置重启的目录
#spring.devtools.restart.additional-paths=src/main/java
#classpath目录下的WEB-INF文件夹内容修改不重启
spring.devtools.restart.exclude=WEB-INF/**
```

