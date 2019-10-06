package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.pojo.User;

/**
 * thymeleaf常用标签测试
 * @author Programmer_qmy
 *
 */
@Controller
@RequestMapping("thymeleaflabels")
public class ThymeleafLabelsController {
	
	@RequestMapping("/test01")
	public String baseMethod(ModelMap map) {
		User user=new User();
		user.setId("00001");
		user.setName("王二麻子");
		user.setPassword("123456");
		user.setAge(32);
		user.setBirthday(new Date());
		user.setDesc("<font color='red'><b>大家好，我是王二麻子，就是看看text和utext的区别....</b></font>");
		map.addAttribute("user", user);
		
		
		User user1=new User();
		user1.setId("00002");
		user1.setName("张三李四");
		user1.setPassword("123456");
		user1.setAge(26);
		user1.setBirthday(new Date());
		user1.setDesc("<font color='red'><b>大家好，我是张三李四，就是看看text和utext的区别....</b></font>");
		
		User user2=new User();
		user2.setId("00003");
		user2.setName("大刀王五");
		user2.setPassword("123456");
		user2.setAge(36);
		user2.setBirthday(new Date());
		user2.setDesc("<font color='red'><b>大家好，我是大刀王五，就是看看text和utext的区别....</b></font>");
		
		List<User> users=new ArrayList<>();
		users.add(user);
		users.add(user1);
		users.add(user2);
		map.addAttribute("userList",users);
		
		return "thymeleaf/thymeleaflabels";
	}
	
	@RequestMapping("postform")
	public String postForm(User user) {
		System.out.println(user.getName()+":"+user.getAge());
		return "redirect:/thymeleaflabels/test01";
	}
}
