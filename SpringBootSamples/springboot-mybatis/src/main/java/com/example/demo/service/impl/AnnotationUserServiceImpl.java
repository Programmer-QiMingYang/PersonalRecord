package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AnnotationUserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.AnnotationUserService;
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
