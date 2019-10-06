package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
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
