### SpringBoot整合thymeleaf模板引擎

[TOC]

#### 一、SpringBoot整合thymeleaf模板引擎

##### （1）需求信息分析

需求：跟freemarker的一样，不过我们希望使用thymeleaf模板引擎

##### （2）pom文件依赖

```xml
<!-- themeleaf模板引擎 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

##### （3）Application.properties配置

```properties
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
```

##### （4）模板文件

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>thymeleaf模板引擎index页面</h1>
	<h1 th:text="${resource.name}">${resource.name}</h1>
	<h1 th:text="${resource.website}">${resource.website}</h1>
	<h1 th:text="${resource.language}">${resource.language}</h1>
</body>
</html>
```

##### （5）controller类

```java
@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {
	@Autowired
	private Resource resource;
	
	@RequestMapping("/index")
	public String index(ModelMap map) {
		map.addAttribute("resource",resource);
		return "thymeleaf/index";
	}
}
```

##### （6）总结

可以看出thymeleaf非常适合目前前后端分离的方式，因为thymeleaf不仅可以被浏览器动态解析，同时也可以被浏览器静态打开原型时候指定的值。

这有助于设计人员和开发人员使用完全相同的模板文件，减少将静态原型转换为工作模板文件所需要的工作量。

#### 二、thymeleaf常用标签

##### 1、标准方言

Thymeleaf是非常非常可扩展的，它允许自定义的名字来定义一组模板属性(或者甚至是标签)，用自定语法评估计算表达式和应用逻辑。它更像是一个模板引擎框架。

它还带有一些称为标准方言(称为*Standard*和*SpringStandard*)的东西，它们定义了一组功能，这些功能应该足以满足大多数情况。可以识别这些标准方言在模板中的使用，因为它将包含以`th`前缀开头的属性，如`<span th:text="...">`。

请注意，*Standard*和*SpringStandard*方言几乎完全相同，只是*SpringStandard*包含了集成到Spring MVC应用程序中的特定功能(例如，使用Spring表达式语言进行表达式评估而不是OGNL)。

##### 2、标准表达式语法

大多数Thymeleaf属性允许将它们的值设置为或包含表达式，由于它们使用的方言，我们将其称为标准表达式。这些表达式可以有五种类型:

- `${...}` : 变量表达式。
- `*{...}` : 选择表达式。
- `#{...}` : 消息 (i18n) 表达式。
- `@{...}` : 链接 (URL) 表达式。
- `~{...}` : 片段表达式。

##### 3、常用th标签

| 关键字         |               功能介绍               | 案例                                                         |
| -------------- | :----------------------------------: | :----------------------------------------------------------- |
| th:id          |                替换id                | `<input th:id="'xxx' + ${collect.id}"/>`                     |
| **th:text**    |               文本替换               | `<p th:text="${collect.description}">description</p>`        |
| **th:utext**   |          支持html的文本替换          | `<p th:utext="${htmlcontent}">content</p>`                   |
| **th:object**  |               替换对象               | `<div th:object="${session.user}">`                          |
| **th:value**   |               属性赋值               | `<input th:value = "${user.name}" />`                        |
| th:with        |             变量赋值运算             | `<div th:with="isEvens = ${prodStat.count}%2 == 0"></div>`   |
| th:style       |               设置样式               | `<div th:style="'display:' + @{(${sitrue} ? 'none' : 'inline-block')} + ''"></div>` |
| th:onclick     |               点击事件               | `<td th:onclick = "'getCollect()'"></td>`                    |
| **th:each**    |               属性赋值               | `<tr th:each = "user,userStat:${users}">`                    |
| **th:if**      |               判断条件               | `<a th:if = "${userId == collect.userId}">`                  |
| **th:unless**  |           和th:if判断相反            | `<a th:href="@{/login} th:unless=${session.user != null}">Login</a>` |
| th:href        |               链接地址               | `<a th:href="@{/login}" th:unless=${session.user != null}>Login</a>` |
| **th:switch**  |     多路选择配合**th:case**使用      | `<div th:switch="${user.role}">`                             |
| th:fragment    |         th:switch的一个分支          | `<p th:case = "'admin'">User is an administrator</p>`        |
| th:includ      |    布局标签，替换内容到引入的文件    | `<head th:include="layout :: htmlhead" th:with="title='xx'"></head>` |
| th:replace     |  布局标签，替换整个标签到引入的文件  | `<div th:replace="fragments/header :: title"></div>`         |
| **th:selectd** |          selected选择框选中          | `th:selected="(${xxx.id} == ${configObj.dd})"`               |
| **th:src**     |            图片类地址引入            | `<img class="img-responsive" alt="App Logo" th:src="@{/img/logo.png}" />` |
| th:inline      |        定义js脚本可以使用变量        | `<script type="text/javascript" th:inline="javascript">`     |
| **th:action**  |            表单提交的地址            | `<form action="subscribe.html" th:action="@{/subscribe}">`   |
| th:remove      |             删除某个属性             | `<tr th:remove="all"> 1.all:删除包含标签和所有的孩子。2.body:不包含标记删除,但删除其所有的孩子。3.tag:包含标记的删除,但不删除它的孩子。4.all-but-first:删除所有包含标签的孩子,除了第一个。5.none:什么也不做。这个值是有用的动态评估。` |
| th:attr        | 设置标签属性，多个属性可以用逗号分隔 | `比如 th:attr="src=@{/image/aa.jpg},title=#{logo}"，此标签不太优雅，一般用的比较少。` |

##### 4、常用标签测试

###### （1）基本使用方式和对象使用方式

###### （2）thymeleaf格式化时间

###### （3）text和utext

上代码：

```html
<div>
    <h2>thymeleaf基本使用方式</h2>
    姓名:<span th:text="${user.name}">姓名</span><br/>
    密码:<span th:text="${user.password}">密码</span><br/>
    年龄:<span th:text="${user.age}">年龄</span><br/>
    <!-- 日期 -->
    生日:<span th:text="${user.birthday}">生日</span><br/>
    <!-- thymeleaf格式化日期 -->
    生日:<span th:text="${#dates.format(user.birthday,'yyyy-MM-dd')}">格式化生日</span><br/>
    <!-- text -->
    描述:<span th:text="${user.desc}">text描述</span><br/>
    <!-- utext -->
    描述:<span th:utext="${user.desc}">utext描述</span><br/>
</div>
<div th:object="${user}">
    <h2>thymeleaf对象使用方式</h2>
    姓名:<span th:text="*{name}">姓名</span><br/>
    密码:<span th:text="*{password}">密码</span><br/>
    年龄:<span th:text="*{age}">年龄</span><br/>
    <!-- 日期 -->
    生日:<span th:text="*{birthday}">生日</span><br/>
    <!-- thymeleaf格式化日期 -->
    生日:<span th:text="*{#dates.format(birthday,'yyyy-MM-dd')}">格式化生日</span><br/>
    <!-- text -->
    描述:<span th:text="*{desc}">text描述</span><br/>
    <!-- utext -->
    描述:<span th:utext="*{desc}">utext描述</span><br/>
</div>
```

**总结**

基本使用方式格式：`th:标签=“${对象.属性}”`

对象使用格式：对使用标签的上级标签`th:object="${对象}"`，在内容使用的地方用`th:标签=“*{属性}”`即可

thymeleaf格式化时间：`th:text=${#dates.format(对象.属性，'格式')}`,例如：`th:text="${#dates.format(user.birthday,'yyyy-MM-dd')}"`

text和utext的区别：text是对文本的输出；而utext确是对文本的解析，更像富文本编辑器

###### （4）URL的使用

```html
<h2>URL使用</h2>
<a href="http://www.baidu.com">普通方式的链接百度</a> <br />
<a th:href="@{http://www.baidu.com}">th方式的链接百度</a><br />
```

###### （5）静态资源css/js的引入

```properties
############################
# 设定静态文件路径js/css等
############################
#我们要使用的静态js和css文件就去static下面去找
spring.mvc.static-path-pattern=/static/**
```

引入js文件

```html
<!--引入静态资源文件js/css  -->
<script th:src="@{/static/js/test.js}"></script>
```

###### （6）表单的使用

```html
<div>
    <h2>form表单</h2>
    <form th:action="@{/thylabels/postform}" th:object="${user}" th:method="post">
        <input type="text" th:field="*{name}" />
        <input type="text" th:field="*{age}" />
        <input type="submit"/>
    </form>
</div>
```

###### （6）条件判断th:if和th:unless

```html
<div >
	<h1>th:if判断的使用</h1><br/>	
	<span th:text="${user.name}"></span>
	<span th:text="${user.age}"></span>
</div>
<div th:if="${user.age}==18" >年龄等于18</div><br/>
<div th:if="${user.age} gt 22" >年龄大于22</div><br/>
<div th:if="${user.age} lt 35" >年龄小于35</div><br/>
<div th:if="${user.age} ge 30" >年龄大于等于30</div><br/>
<div th:if="${user.age} le 29" >年龄小于等于29</div><br/>	
```

unless和if一样都是判断，if true 执行，unless就是各种条件相反才能执行。

###### （7）选择框

```html
<div>
    <h2>选择框的使用</h2>
    <select>
        <option>请选择</option>
        <option th:selected="${user.name} eq '张三'">张三</option>
        <option th:selected="${user.name} eq '李四'">李四</option>
        <option th:selected="${user.name} eq '王二麻子'">王二麻子</option>
    </select>
</div>
```

默认值就是selected的值,如果selected对应的没有值就是请选择.

###### （8）循环th:each

```html
<tr th:each="person:${userList}">
    <td th:text="${person.name}"></td>
    <td th:text="${person.password}"></td>
    <td th:text="${#dates.format(person.birthday,'yyyy-MM-dd')}"></td>
    <td th:text="${person.age}"></td>
    <td th:utext="${person.desc}"></td>
    <td th:text="${person.age gt 30}?(${person.age lt 35}?快到35了:立即劝退):挺好"></td>	
</tr>
```

可以看出th:each循环跟java的foreach有异曲同工之妙。同时我们也发现thymeleaf可以使用**三元运算符**。

###### （9）th:switch和th:case

pom文件依赖引入

```properties
############################
## i18n配置,一般可以利用它做国际化
############################
spring.messages.basename=i18n/messages
#spring.messages.cache-seconds=3600这个已经被抛弃了，使用下面的
spring.messages.cache-duration=3600
spring.messages.encoding=UTF-8
```

编写messags.prperties

```properties
########################
## i18n自定义权限、角色、都可以了
########################
roles.manager=manager
roles.super=super
```

th:switch和th:case

```html
<div>
    <h1>th:switch和th:case</h1>
    <div th:switch="${user.name}">
        <p th:text="${user.name}"></p>
        <p th:case="'张三'">程序员</p>
        <p th:case="#{roles.manager}">高级程序员</p>
        <p th:case="#{roles.super}">项目总监</p>
        <p th:case="*">其它</p>
    </div>
</div>
```





