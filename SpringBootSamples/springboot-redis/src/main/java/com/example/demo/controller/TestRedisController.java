package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.result.JSONResult;
import com.example.demo.service.UserService;
import com.example.demo.utils.JsonUtils;
import com.example.demo.utils.RedisOperator;

@RestController
@RequestMapping("redis")
public class TestRedisController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisOperator RedisOperator;
	
	/**
	 * 存入redis中一个字符串，在取出
	 * @return
	 */
	@RequestMapping("/test01")
	public JSONResult test01() {
		stringRedisTemplate.opsForValue().set("first", "test redis");
		return JSONResult.ok(stringRedisTemplate.opsForValue().get("first"));
	}
	
	/**
	 * 存入redis一个对象，然后取出
	 * @param id
	 * @return
	 */
	@RequestMapping("/test02")
	public JSONResult test02(String id) {
		User user=userService.querrUser(id);
		stringRedisTemplate.opsForValue().set("user", JsonUtils.objectToJson(user));
		return JSONResult.ok(JsonUtils.jsonToPojo(stringRedisTemplate.opsForValue().get("user"), User.class));
	}
	
	/**
	 * 先从redis取数据，如果redis没有数据就从数据库取，取出来的数据存储到redis中，以后都从redis中取数据
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
			RedisOperator.set("RedisOperator_User_Test01", JsonUtils.objectToJson(user));			
		}
		return JSONResult.ok(user);
	}
	
}
