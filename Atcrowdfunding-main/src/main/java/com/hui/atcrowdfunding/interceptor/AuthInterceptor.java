package com.hui.atcrowdfunding.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.manage.service.PermissionService;
import com.hui.atcrowdfunding.util.Const;

//访问拦截
public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private PermissionService permissionService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.查询所有的URL
		
		//如果每次拦截都查询数据库，效率会很低
	/*	List<Permission> queryAllURL = permissionService.queryAllPermission();
		
		//获取她所有的uri
		Set<String> allURL = new HashSet<String>();
		for(Permission perssion : queryAllURL){
			allURL.add("/" + perssion.getUrl());
		}*/
		//改进：就在服务器启动时就加载所有的许可路径在application域中
		//这时候就需要在StartSystemListener.java里面写入方法，让一启动就加载
		 Set<String> allURL = (Set<String>) request.getSession().getServletContext().getAttribute(Const.ALL_PERMISSION_URI);
		//2.判断请求的路径是否在所有的许可范围内
		
		String servletPath = request.getServletPath();
		if(allURL.contains(servletPath)) {
			 
			//3.判断请求路径是否在用户所拥有的权限内、
			Set<String> myUri = (Set<String>)request.getSession().getAttribute(Const.MY_URIS);
			
			if(myUri.contains(servletPath)) {
				return true;
			}else {
				//拦截了的话就重定向去login
				response.sendRedirect(request.getContextPath()+"/login.htm");
				return false;
			}
		}else{
			return true;
		}
	 
	}
	 
}
