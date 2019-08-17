package com.hui.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartSystemListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent sce) {
		 
	}
/*
	 * 在服务器启动时，创建application对象时需要执行的方法
	 */
	public void contextInitialized(ServletContextEvent sce) {
	
		 
		//将项目上下文路径（request.getContextPath()）放置在application域中
		System.out.println("=============");
		ServletContext application = sce.getServletContext();
		String contextPath = application.getContextPath();
		application.setAttribute("APP_PATH", contextPath);
		System.out.println("APP_PATH.....");

	}
	
	

}
