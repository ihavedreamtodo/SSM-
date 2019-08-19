package com.hui.atcrowdfunding.manage.service;

import java.util.Map;

import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.util.Page;

public interface UserService {

	User queryUserLogin(Map<String, Object> paramMap);

	Page queryUserPage(Integer pageno ,Integer pagesize);
	
	int saveUser(User user);

}
