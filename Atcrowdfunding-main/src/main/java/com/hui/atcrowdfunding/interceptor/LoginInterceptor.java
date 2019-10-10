package com.hui.atcrowdfunding.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.util.Const;


//结束之后需要在springmvc的xml里面声明
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1.定义哪些路径是不需要拦截的
	Set<String> uri = new HashSet<String>();
	
	uri.add("/user/reg.do");
	uri.add("/user/reg.htm");
	uri.add("/login.htm");
	uri.add("/doLogin.do");
	uri.add("/loginout.do");
	
	//获取请求路径
	String servletPath = request.getServletPath();//getServletPath()获取的是servlet路径。也就是/user/login.do，若是夹路Atcrowdfunding那是uri路径
	if(uri.contains(servletPath)){
		return true;
	}
		
		//2.判断用户是否登录，如果登录就放行，。通过session获取
	HttpSession session = request.getSession();
	User user= (User)session.getAttribute(Const.LOGIN_USER);
	if(user != null) {
		return true;
	}else {
		//没有登录的话就重定向回到登录界面
		response.sendRedirect(request.getContextPath() + "/login.htm");
		return false;
	}
	 
	}
}
