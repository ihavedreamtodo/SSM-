package com.hui.atcrowdfunding.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.manage.service.PermissionService;
import com.hui.atcrowdfunding.util.Const;

public class StartSystemListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent sce) {
		 
	}
/*
	 * 在服务器启动时，创建application对象时需要执行的方法
	 */
	public void contextInitialized(ServletContextEvent sce) {
	
		 
		//1.将项目上下文路径（request.getContextPath()）放置在application域中
		System.out.println("=============");
		ServletContext application = sce.getServletContext();
		String contextPath = application.getContextPath();
		application.setAttribute("APP_PATH", contextPath);
		System.out.println("APP_PATH.....");

		//2.就在服务器启动时就加载所有的许可路径在application域中
		//permissionService可以通过IOC来获取
		ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);
		PermissionService permissionService = ioc.getBean(PermissionService.class);
		
		
		List<Permission> queryAllURL = permissionService.queryAllPermission();
		Set<String> allURL = new HashSet<String>();
		for(Permission perssion : queryAllURL){
			allURL.add("/" + perssion.getUrl());
		}
		
		application.setAttribute(Const.ALL_PERMISSION_URI, allURL);
	}
	
	

}
