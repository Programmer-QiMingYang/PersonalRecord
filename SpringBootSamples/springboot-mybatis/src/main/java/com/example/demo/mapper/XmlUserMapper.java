package com.example.demo.mapper;

import java.util.List;

import com.example.demo.pojo.User;

public interface XmlUserMapper {
	//根据id查询用户
	List<User> queryUser(String id);
}
