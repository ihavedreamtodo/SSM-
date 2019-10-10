package com.hui.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.service.UserService;
import com.hui.atcrowdfunding.util.AjaxResult;
import com.hui.atcrowdfunding.util.Const;
import com.hui.atcrowdfunding.util.MD5Util;


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
	public String main(HttpSession session){
		
	

		return "main";
	}
	
	@RequestMapping("/loginout")
	public String loginout(HttpSession session){
		
		//销毁session对象
		session.invalidate();
		
		return "redirect:/index.htm";
		//这里用redirect 更好，因为时重定向之后就刷新的时候不会重复提交表单。刷新的是index页面
	}
	
	
	
	//异步请求，不允许页面刷新,@ResponseBody
	//@ResponseBody 结合Jackson组件，将返回结果转换为字符串，将Json串以流的形式返回给客户端
	@ResponseBody
	@RequestMapping("/doLogin")
	public Object doLogin(String loginacct,String userpswd,String type, HttpSession session){
		
		AjaxResult result =	new AjaxResult();
		
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			
			
			paramMap.put("loginacct", loginacct);
			paramMap.put("userpswd", MD5Util.digest(userpswd));//将密码加密 MD5Util.digest(userpswd)
			paramMap.put("type", type);
			
			
			
			if("member".equals(type)){//管理员
			    User user = userService.queryUserLogin(paramMap);
				
				
				 session.setAttribute(Const.LOGIN_USER, user);
				// ==============================
				//加载当前登录用户的所拥有的许可权限.
					
					//User user = (User)session.getAttribute(Const.LOGIN_USER);
					//通过id找他的许可
					List<Permission> myPermissions = userService.queryPermissionByUserid(user.getId());
					
					Permission permissionRoot = null;
					                          
					Map<Integer,Permission> map = new HashMap<Integer,Permission>();
					
					Set<String> allPermissionUrls = new HashSet<String>();//用于拦截器拦截许可权限

				 
					for (Permission innerpermission : myPermissions) {
						map.put(innerpermission.getId(), innerpermission);
						//把她的许可权限放进allPermissionUrls
						allPermissionUrls.add("/"+innerpermission.getUrl());
					}
					//把她的许可权限放进session域中
					session.setAttribute(Const.MY_URIS, allPermissionUrls);
					for (Permission permission : myPermissions) {
						//通过子查找父
						//子菜单
						Permission child = permission ; //假设为子菜单
						if(child.getPid() == null ){
							permissionRoot = permission;
						}else{
							//父节点
							Permission parent = map.get(child.getPid());
							parent.getChildren().add(child);
						}
					}
					
					
					session.setAttribute("permissionRoot", permissionRoot);
					 
				 // ==============================
				 result.setSuccess(true);
			}else if("user".equals(type)){//普通会员
				
				//查询数据库比对数据是否一致，Service mapper都还没弄
				//=======================================================
				
			}
			
							
		
		//	 {success:true}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("登录失败");
			e.printStackTrace();
			result.setSuccess(false);
			// {success:false,message:"登录失败"}
		}
		return result;
	}
	/*//同步请求
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
	}*/
}
