### SpringBoot整合Mybatis

[TOC]

#### 一、SpringBoot+druid+mybatis

##### （1）pom依赖引入

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
```

##### （2）Application.properties配置

```properties
############################
## devtools
############################
spring.devtools.restart.enabled=true
#############################
##配置数据源
#############################
spring.datasource.url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=818903
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
###############################
##druid连接池
###############################
# 使用 druid 作为连接池  更多配置的说明可以参见 druid starter 中文文档 https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
#初始化时建立物理连接的个数。初始化发生在显示调用 init 方法，或者第一次 getConnection 时
spring.datasource.druid.initial-size=1
#最小连接池数量
spring.datasource.druid.min-idle=1
#最大连接池数量
spring.datasource.druid.max-active=20
# 获取连接时最大等待时间，单位毫秒。配置了 maxWait 之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置 useUnfairLock 属性为 true 使用非公平锁。
spring.datasource.druid.max-wait=60000
# Destroy 线程会检测连接的间隔时间，如果连接空闲时间大于等于 minEvictableIdleTimeMillis 则关闭物理连接。
spring.datasource.druid.time-between-eviction-runs-millis=60000
# 连接保持空闲而不被驱逐的最小时间    
spring.datasource.druid.min-evictable-idle-time-millis=300000
# 用来检测连接是否有效的 sql 因数据库方言而差, 例如 oracle 应该写成 SELECT 1 FROM DUAL
spring.datasource.druid.validation-query=SELECT 1
# 建议配置为 true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于 timeBetweenEvictionRunsMillis，执行 validationQuery 检测连接是否有效。
spring.datasource.druid.test-while-idle=true
# 申请连接时执行 validationQuery 检测连接是否有效，做了这个配置会降低性能。
spring.datasource.druid.test-on-borrow=false
# 归还连接时执行 validationQuery 检测连接是否有效，做了这个配置会降低性能。
spring.datasource.druid.test-on-return=false
# 是否自动回收超时连接
spring.datasource.druid.remove-abandoned=true
# 超时时间 (以秒数为单位)
spring.datasource.druid.remove-abandoned-timeout=180

###############################
##druid 监控的配置 如果不使用 druid 的监控功能的话
##以下配置就不是必须的
##本项目监控台访问地址: http://localhost:8080/druid/login.html
################################
# WebStatFilter 用于采集 web-jdbc 关联监控的数据。
# 更多配置可参见: https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_%E9%85%8D%E7%BD%AEWebStatFilter

# 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙  
#spring.datasource.filters=stat,wall,log4j
spring.datasource.druid.filters=stat,wall
# 通过connectProperties属性来打开mergeSql功能；慢SQL记录  
#spring.datasource.connectionProperties=druid.stat.mergeSql=true;
#druid.stat.slowSqlMillis=5000 
spring.datasource.druid.connection-properties=druid.stat.mergeSql=true
spring.datasource.druid.filter.stat.slow-sql-millis=5000

# 是否开启 WebStatFilter 默认是 true
spring.datasource.druid.web-stat-filter.enabled=true
# 需要拦截的 url
spring.datasource.druid.web-stat-filter.url-pattern=/*
# 排除静态资源的请求
spring.datasource.druid.web-stat-filter.exclusions=*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*
# Druid 内置提供了一个 StatViewServlet 用于展示 Druid 的统计信息。
# 更多配置可参见:https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatViewServlet%E9%85%8D%E7%BD%AE
#是否启用 StatViewServlet 默认值 true
spring.datasource.druid.stat-view-servlet.enabled=true
# 需要拦截的 url
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
# 允许清空统计数据
spring.datasource.druid.stat-view-servlet.reset-enable=true
# druid监控登陆用户名
spring.datasource.druid.stat-view-servlet.login-username=druid
# druid监控登陆密码
spring.datasource.druid.stat-view-servlet.login-password=druid

###############################
# mybatis配置
###############################
mybatis.type-aliases-package=fit.programmer.www.pojo
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.configuration.jdbc-type-for-null=null
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

##### （3）yaml方式的配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/mysql?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

    # 使用 druid 作为连接池  更多配置的说明可以参见 druid starter 中文文档 https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      # 初始化时建立物理连接的个数。初始化发生在显示调用 init 方法，或者第一次 getConnection 时
      initialSize: 5
      # 最小连接池数量
      minIdle: 5
      # 最大连接池数量
      maxActive: 10
      # 获取连接时最大等待时间，单位毫秒。配置了 maxWait 之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置 useUnfairLock 属性为 true 使用非公平锁。
      maxWait: 60000
      # Destroy 线程会检测连接的间隔时间，如果连接空闲时间大于等于 minEvictableIdleTimeMillis 则关闭物理连接。
      timeBetweenEvictionRunsMillis: 60000
      # 连接保持空闲而不被驱逐的最小时间
      minEvictableIdleTimeMillis: 300000
      # 用来检测连接是否有效的 sql 因数据库方言而差, 例如 oracle 应该写成 SELECT 1 FROM DUAL
      validationQuery: SELECT 1
      # 建议配置为 true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于 timeBetweenEvictionRunsMillis，执行 validationQuery 检测连接是否有效。
      testWhileIdle: true
      # 申请连接时执行 validationQuery 检测连接是否有效，做了这个配置会降低性能。
      testOnBorrow: false
      # 归还连接时执行 validationQuery 检测连接是否有效，做了这个配置会降低性能。
      testOnReturn: false
      # 是否自动回收超时连接
      removeAbandoned: true
      # 超时时间 (以秒数为单位)
      remove-abandoned-timeout: 180

      # druid 监控的配置 如果不使用 druid 的监控功能的话 以下配置就不是必须的
      # 本项目监控台访问地址: http://localhost:8080/druid/login.html

      # WebStatFilter 用于采集 web-jdbc 关联监控的数据。
      # 更多配置可参见: https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_%E9%85%8D%E7%BD%AEWebStatFilter
      web-stat-filter:
        # 是否开启 WebStatFilter 默认是 true
        enabled: true
        # 需要拦截的 url
        url-pattern: /*
        # 排除静态资源的请求
        exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"

      # Druid 内置提供了一个 StatViewServlet 用于展示 Druid 的统计信息。
      # 更多配置可参见:https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatViewServlet%E9%85%8D%E7%BD%AE
      stat-view-servlet:
        #是否启用 StatViewServlet 默认值 true
        enabled: true
        # 需要拦截的 url
        url-pattern: /druid/*
        # 允许清空统计数据
        reset-enable: true
        login-username: druid
        login-password: druid



# mybatis 相关配置
mybatis:
    configuration:
      # 当没有为参数提供特定的 JDBC 类型时，为空值指定 JDBC 类型。
      # oracle 数据库建议配置为 JdbcType.NULL, 默认是 Other
      jdbc-type-for-null: 'null'
      # 是否打印 sql 语句 调试的时候可以开启
log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

##### （4）pojo类

```java
public class User {
	private String id;// id
	private String name;// 姓名
	private String password;// 密码
	private Integer age;// 年龄
	private String birthday;// 生日
	private String intro;// 描述
	//getter and setter
	}
```

##### （5）导入twitter的分布式主键id生成器

网上有以pom插件的方式使用，超级简单，这里使用另外一种方式：

1. ###### 将包连同文件复制到项目src/main/java下面

2. ###### 在springbootapplication启动类添加注解

   ```java
   @ComponentScan(basePackages = {"com.example.demo","org.n3r.idworker"})
   ```

3. ###### 使用的时候先注入sid：

   ```java
   @Autowired
   private Sid sid
   ```

   然后

   ```java
   String id=sid.nextShort();
   ```

   即可

4. ###### @ComponentScan 的作用就是根据定义的扫描路径，把符合扫描规则的类装配到spring容器中，注解定义如下,看源码:

   ```java
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   @Documented
   @Repeatable(ComponentScans.class)
   public @interface ComponentScan {
       @AliasFor("basePackages")
       String[] value() default {};
       @AliasFor("value")
       String[] basePackages() default {};
       Class<?>[] basePackageClasses() default {};
       Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;
       Class<? extends ScopeMetadataResolver> scopeResolver() default AnnotationScopeMetadataResolver.class;
       ScopedProxyMode scopedProxy() default ScopedProxyMode.DEFAULT;
       String resourcePattern() default "**/*.class";
       boolean useDefaultFilters() default true;
       ComponentScan.Filter[] includeFilters() default {};
       ComponentScan.Filter[] excludeFilters() default {};
       boolean lazyInit() default false;
       @Retention(RetentionPolicy.RUNTIME)
       @Target({})
       public @interface Filter {
           FilterType type() default FilterType.ANNOTATION; 
           @AliasFor("classes")
           Class<?>[] value() default {}; 
           @AliasFor("value")
           Class<?>[] classes() default {};
           String[] pattern() default {};
       }
   }
   ```

   basePackages与value:  用于指定包的路径，进行扫描
   basePackageClasses: 用于指定某个类的包的路径进行扫描
   nameGenerator: bean的名称的生成器
   useDefaultFilters: 是否开启对@Component，@Repository，@Service，@Controller的类进行检测
   includeFilters: 包含的过滤条件
   FilterType.ANNOTATION：按照注解过滤
   FilterType.ASSIGNABLE_TYPE：按照给定的类型
   FilterType.ASPECTJ：使用ASPECTJ表达式
   FilterType.REGEX：正则
   FilterType.CUSTOM：自定义规则
   excludeFilters: 排除的过滤条件，用法和includeFilters一样



##### （6）添加mapper扫描

```java
@MapperScan(basePackages = "com.example.demo.mapper")
```

建立mapper包，基本齐活了


#### 二、实现crud

##### （1）注解实现

###### mapper接口

```java
@Mapper
public interface AnnotationUserMapper {
	
	@Select("select * from user")
	List<User> selectAllUser();
	
	@Insert("insert into user ( id, name, password, age, birthday, intro ) values (#{id},#{name},#{password},#{age},#{birthday},#{intro})")
	void saveUser(User user);
	
	@Select("select * from user where id=#{id}")
	User selectUserById(String id);
	
	@Update("update user set name=#{name},password=#{password},age=#{age},birthday=#{birthday},intro=#{intro} where id=#{id}")
	void modify(User user);
	
	@Delete("delete from user where id=#{id}")
	void deleteUserById(String id);
	
}
```

###### service接口

```java
public interface AnnotationUserService {
	List<User> selectAllUser();

	void saveUser(User user);

	User selectUserById(String id);

	void modifyUser(User user);

	void deleteUserById(String id);
}
```

###### serviceImpl

```java
@Service
public class AnnotationUserServiceImpl implements AnnotationUserService{
	
	@Autowired
	private AnnotationUserMapper userMapper;

	//事务传播supports用于查询，增加、删除、修改用required
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<User> selectAllUser() {		
		return userMapper.selectAllUser();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(User user) {
		userMapper.saveUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User selectUserById(String id) {
		return userMapper.selectUserById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void modifyUser(User user) {
		userMapper.modify(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUserById(String id) {
		userMapper.deleteUserById(id);
	}

}
```

###### controller

```java
@RestController
@RequestMapping("annotationUser")
public class AnnotationUserController {

	@Autowired
	private AnnotationUserService userService;

	@Autowired
	private Sid Sid;

	// 查询所有的用户
	@RequestMapping("/selectAllUser")
	public JSONResult selectAllUser() {
		return JSONResult.ok(userService.selectAllUser());
	}

	// 添加一个用户
	@RequestMapping("/saveuser")
	public JSONResult saveUser() {
		User user = new User();
		user.setId(Sid.nextShort());
		user.setName("王二麻子");
		user.setAge(35);
		user.setBirthday(DateFormat.getDateInstance().format(new Date()));// 2019-09-20
		user.setPassword("123456");
		user.setIntro("我叫王二麻子");
		userService.saveUser(user);
		return JSONResult.ok("保存用户成功");
	}

	// 根据id查询一个用户
	@RequestMapping("/selectUserById")
	public JSONResult selectUserById(String id) {
		return JSONResult.ok(userService.selectUserById(id));
	}

	// 根据id更新一个用户
	@RequestMapping("/modifyUser")
	public JSONResult modifyUser() {
		User user = new User();
		user.setId("190920CX63F31XWH");
		user.setName("王二麻子的儿子");
		user.setAge(365);
		user.setBirthday(DateFormat.getDateInstance().format(new Date()));// 2019-09-20
		user.setPassword("123456");
		user.setIntro("王二麻子是谁？别骗人，俺爹叫秃子王");
		userService.modifyUser(user);
		return JSONResult.ok("更新成功");
	}

	// 根据id删除一个用户
	@RequestMapping("/deleteUserById")
	public JSONResult deleteUserById(String id) {
		userService.deleteUserById(id);
		return JSONResult.ok("删除成功");
	}
}
```



###### 测试

service没有加事务的话，控制台会打印的消息有下面这么一段，大概意思就是“没注册，jdbc连接spring也不管”：

`SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6e9647e2] was not registered for synchronization because synchronization is not active
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@2fab4dd9] will not be managed by Spring`

主要原因是没有加上事务控制。

我们事务传播行为：一般是`@Transactional(propagation = Propagation.SUPPORTS)`用于查询；`@Transactional(propagation = Propagation.REQUIRED)`用于增加、删除、修改。



##### （2）自定义mapper

自定义mapper的场景可能是开发中最常用的，我们以根据id查询用户为例

###### 新建mapper接口

```java
public interface XmlUserMapper {
	//根据id查询用户
	List<User> queryUser(String id);
}
```

###### 创建mapper.xml文件

xml文件位置，就是配置文件配置的位置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.example.demo.mapper.XmlUserMapper" >
  <select id="queryUser" resultType="com.example.demo.pojo.User" parameterType="java.lang.String">
  	select * from user where id=#{id}
  </select>
</mapper>
```

###### service

```java
public interface XmlUserService {
	User queryUser(String id);
}
```

###### serviceImpl

```java
@Service
public class XmlUserServiceImpl implements XmlUserService{

	@Autowired
	private XmlUserMapper userMapper;
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public User queryUser(String id) {
		List<User> users=userMapper.queryUser(id);
		if (users != null && !users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
}
```

###### controller

```java
@RestController
@RequestMapping("xmlUser")
public class XmlUserController {
	
	@Autowired
	private XmlUserService userService;
	
	@RequestMapping("/queryUser")
	public JSONResult queryUser(String id) {
		return JSONResult.ok(userService.queryUser(id));
	}
}
```



#### 三、配置druid监控中心

我们在配置里面已经配置了监控中心

访问地址：http://localhost:8080/druid/login.html

用户名：druid

密码：duid

可以看到sql统计和uri统计，甚至慢sql。

#### 四、简单介绍一下这个idworker

唯一ID值可以用UUID，但是UUID是无序的，而且是字符串，在数据库中效率低，插入时间慢，一般是自己写算法是生成一个唯一的ID，大家可能说使用数据库自增，但是如果数据到了**分表分库**的时候，那么数据库唯一ID将不再满足需求。

分布式系统中，有一些需要使用全局唯一ID的场景，这种时候为了防止ID冲突可以使用36位的UUID，但是UUID有一些缺点，首先他相对比较长，另外**UUID一般是无序**的。

有些时候我们希望能使用一种简单一些的ID，并且希望**ID能够按照时间有序**生成。

而twitter的snowflake解决了这种需求，最初Twitter把存储系统从MySQL迁移到Cassandra，因为Cassandra没有顺序ID生成机制，所以开发了这样一套全局唯一ID生成服务。





