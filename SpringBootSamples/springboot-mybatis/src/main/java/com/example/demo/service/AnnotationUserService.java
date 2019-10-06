package com.example.demo.service;

import java.util.List;

import com.example.demo.pojo.User;

public interface AnnotationUserService {
	List<User> selectAllUser();

	void saveUser(User user);

	User selectUserById(String id);

	void modifyUser(User user);

	void deleteUserById(String id);
}
