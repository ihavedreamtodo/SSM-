package com.hui.atcrowdfunding.manage.service;

import java.util.Map;

import com.hui.atcrowdfunding.bean.User;

public interface UserService {

	User queryUserLogin(Map<String, Object> paramMap);

}
