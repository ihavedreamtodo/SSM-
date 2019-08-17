package com.hui.atcrowdfunding.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.exception.LoginFailException;
import com.hui.atcrowdfunding.manage.dao.UserMapper;
import com.hui.atcrowdfunding.manage.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	public User queryUserLogin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		
	User user =	userMapper.queryUserLogin(paramMap);
	if(user==null) {
		//自定义异常
		throw new LoginFailException("用户账号或密码不正确");
		
	}
		return user;
	}
	
	
}
