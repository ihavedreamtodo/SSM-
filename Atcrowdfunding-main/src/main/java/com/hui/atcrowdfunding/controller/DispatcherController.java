package com.hui.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.service.UserService;
import com.hui.atcrowdfunding.util.Const;


/*把这个控制器放在这里的原因是前台后台都需要用到，所以就放在main里面。可以公用*/
@Controller
public class DispatcherController {

	@Autowired
	private UserService userService;
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(String loginacct,String userpswd,String type, HttpSession session){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginacct", loginacct);
		paramMap.put("userpswd", userpswd);
		paramMap.put("type", type);
		
		//<tx:method name="query*" read-only="true"/>配置事务那里设置了query开头就是查询操作
	User user = userService.queryUserLogin(paramMap);
	
	
	//如果登录出现异常，就会走springmvc-context.xml里面的异常解析器
	session.setAttribute(Const.LOGIN_USER, user);
		return "redirect:/main.htm";
		//如果是直接return "main.htm";，那这个是转发，则刷新页面的时候会重复提交表单
		//只有redirect，重定向之后刷新的时候刷新的是main页面
	}
}
