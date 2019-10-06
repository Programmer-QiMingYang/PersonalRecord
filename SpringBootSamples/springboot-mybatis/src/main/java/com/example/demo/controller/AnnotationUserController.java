package com.example.demo.controller;

import java.text.DateFormat;
import java.util.Date;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.result.JSONResult;
import com.example.demo.service.AnnotationUserService;

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
