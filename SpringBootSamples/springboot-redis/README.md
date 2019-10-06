### SpringBoot整合Redis

[TOC]



#### 一、需求分析

##### （1）需求

A、从redis中存取字符串

B、从redis中存取对象

C、每次访问都访问redis，当redis没有数据，从数据库读取，读取之后再存入redis缓存中，下一次访问从redis中读取数据

##### （2）准备

数据库驱动

mybatis

druid连接池

redis

方便测试我们开启devtools

##### （3）pom依赖导入

```xml
<!-- devtools -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>

<!-- mysql connector jdbc -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<!-- druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.20</version>
</dependency>

<!-- mybatis -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.1</version>
</dependency>

<!-- redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

##### （4）properties配置

```properties
###############################
## devtools
###############################
spring.devtools.restart.enabled=true
###############################
##配置数据源
###############################
spring.datasource.url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=818903

###############################
##druid连接池
###############################
# 使用 druid 作为连接池  更多配置的说明可以参见 druid starter 中文文档 https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
#初始化时建立物理连接的个数。初始化发生在显示调用 init 方法，或者第一次 getConnection 时
spring.datasource.druid.initial-size=5
#最小连接池数量
spring.datasource.druid.min-idle=5
#最大连接池数量
spring.datasource.druid.max-active=10
###############################
# mybatis配置
###############################
mybatis.type-aliases-package=com.example.demo.pojo
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.configuration.jdbc-type-for-null=null
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
###############################
## redis
###############################
#redis数据库索引，默认为0
spring.redis.database=1
#redis服务器地址
spring.redis.host=localhost
#redis服务器连接端口
spring.redis.port=6379
#redis服务器连接密码（默认为空）
spring.redis.password=
#连接池最大连接数（使用负值表示没有限制）
#spring.redis.pool.max-active=1000这个已经被官方抛弃，提示使用下面的
spring.redis.jedis.pool.max-active=1000
#连接池的最大阻塞等待时间(使用负值表示没有限制)
#spring.redis.pool.max-wait=-1这个也被官方放抛弃了，提示使用下面的
spring.redis.jedis.pool.max-wait=-1
#连接池中最大的空闲连接
#spring.redis.pool.max-idle=10这个也被官方遗弃了，提示使用下面的
spring.redis.jedis.pool.max-idle=10
#连接池中最小的空闲连接
#spring.redis.pool.min-idle=2这个也被官方遗弃了，提示使用下面的
spring.redis.jedis.pool.min-idle=2
#连接超时时间(套秒)(设置0不太可能啊，我实际设置5000)
spring.redis.timeout=5000
```

#### 二、业务代码编写

##### （1）使用StringRedisTemplate完成业务逻辑

在启动类加入mapper扫描

```java
@MapperScan(basePackages = "com.example.demo.mapper")
```

controller层：

```java
@RestController
@RequestMapping("redis")
public class TestRedisController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 存入redis中一个字符串，在取出
	 * @return json
	 */
	@RequestMapping("/test01")
	public JSONResult test01() {
		stringRedisTemplate.opsForValue().set("first", "test redis");
		return JSONResult.ok(stringRedisTemplate.opsForValue().get("first"));
	}
	
	/**
	 * 存入redis一个对象，然后取出
	 * @param id
	 * @return json
	 */
	@RequestMapping("/test02")
	public JSONResult test02(String id) {
		User user=userService.querrUser(id);
		stringRedisTemplate.opsForValue().set("user", JsonUtils.objectToJson(user));
		return JSONResult.ok(JsonUtils.jsonToPojo(stringRedisTemplate.opsForValue().get("user"), User.class));
	}
	
	/**
	 * 从redis取数据，如果redis没有数据就从数据库取，取出来的数据存储到redis中，以后都从redis中取数据
	 * 这里暂时忽略数据库和缓存redis里面都没有的情况
	 */
	@RequestMapping("/test03")
	public JSONResult test03(String id) {
		User user1=JsonUtils.jsonToPojo(stringRedisTemplate.opsForValue().get("testuser"), User.class);
		if (user1==null) {
			user1=userService.querrUser(id);
			stringRedisTemplate.opsForValue().set("testuser", JsonUtils.objectToJson(user1));
		}
		return JSONResult.ok(user1);
	}
```

service层

```java
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper UserMapper;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User querrUser(String id) {
		List<User> users=UserMapper.queryUser(id);
		if (users!=null && !users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
}
```

dao（mapper接口）层

```java
@Mapper
public interface UserMapper {
	//根据id查询用户
	List<User> queryUser(String id);
}
```

mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.demo.mapper.UserMapper">
	
	<!-- 根据id查询所有的user对象 -->
	<select id="queryUser" parameterType="string" resultType="com.example.demo.pojo.User" >
	select * from user where id=#{id}	
	</select>

</mapper>
```



##### （2）封装StringRedisTemplate类

这样使用没有什么问题，但是有些时候我们希望redis的操作就像redis窗口命令一样简单，不想再输入什么opsForValue()的。我们对它做一个简单的封装,叫做RedisOperator类。具体代码参见RedisOperator类代码

这里仅仅表现RedisOperator操作

```java
/**
 * 使用RedisOperator存取一个字符串
* @return
*/
@RequestMapping("/test04")
public JSONResult test04() {
    RedisOperator.set("RedisOperator", "test RedisOperator");
    return JSONResult.ok(RedisOperator.get("RedisOperator"));

}

/**
	 * 使用RedisOperator存取一个字符串并设置过期时间，我们让线程休息一样的时间再取这个key
	 * @throws Exception 这里测试先抛出异常
	 */
@RequestMapping("/test05")
public JSONResult test05() throws Exception {
    RedisOperator.set("RedisOperator02", "test RedisOperator time out", 1);
    String str1=RedisOperator.get("RedisOperator02");
    Thread.sleep(1000);
    String str2=RedisOperator.get("RedisOperator02");
    return JSONResult.ok(str1+":"+str2);//str2应该是一个null
}

/**
	 * 使用RedisOperator存取一个对象
	 */
@RequestMapping("/test06")
public JSONResult test06(String id) {
    User user = userService.querrUser(id);
    RedisOperator.set("RedisOperator_User", JsonUtils.objectToJson(user));
    return JSONResult.ok(JsonUtils.jsonToPojo(RedisOperator.get("RedisOperator_User"), User.class));
}
/**
	 * 先从redis取数据，如果redis没有数据就从数据库取，取出来的数据存储到redis中，以后都从redis中取数据
	 * 这里暂时忽略数据库和缓存redis里面都没有的情况
	 */
@RequestMapping("/test07")
public JSONResult test07(String id) {
    User user = JsonUtils.jsonToPojo(RedisOperator.get("RedisOperator_User_Test01"), User.class);
    if (user==null) {
        user=userService.querrUser(id);
        RedisOperator.set("RedisOperator_User_Test01", JsonUtils.objectToJson(user));		    }
    return JSONResult.ok(user);
}
```

可以看出还是稍微要直观一点。

#### 三、小问题

列举一些，偶尔面试会出现的小问题：

##### （1）redis常用数据类型

##### （2）redis穿透怎么办

##### （3）redis如何集群

##### （4）加入有1w个key如何保证它是热点数据

##### （5）使用redis实现session共享

##### （6）为什么redis是单线程

##### （7）使用redis实现登陆保护：限制一个小时内每个用户id只能登陆5次

##### （8）redis的主从复制模型

##### （9）redis中的事务

##### （10）redis如何做内存优化







