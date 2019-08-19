package com.hui.atcrowdfunding.manage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hui.atcrowdfunding.manage.service.UserService;
import com.hui.atcrowdfunding.util.Page;



@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	//同步请求
	@RequestMapping("/user")
	public String queryUserPage(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno ,
			@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize,Map map) {
		
		
		Page page = service.queryUserPage( pageno , pagesize);
		map.put("page", page);
		return "user/user";
	}
}
