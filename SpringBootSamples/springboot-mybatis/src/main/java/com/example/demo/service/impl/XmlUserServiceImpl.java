package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.XmlUserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.XmlUserService;
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
