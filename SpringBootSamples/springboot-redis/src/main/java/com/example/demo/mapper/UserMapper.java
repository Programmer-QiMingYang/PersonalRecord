package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.pojo.User;
@Mapper
public interface UserMapper {
	//根据id查询用户
	List<User> queryUser(String id);
}
