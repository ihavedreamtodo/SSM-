package com.hui.atcrowdfunding.manage.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui.atcrowdfunding.bean.Role;
import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.service.UserService;
import com.hui.atcrowdfunding.util.AjaxResult;
import com.hui.atcrowdfunding.util.Page;
import com.hui.atcrowdfunding.util.StringUtil;
import com.hui.atcrowdfunding.vo.Data;



@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	
	@RequestMapping("/toIndex")
	public String toIndex(){		
		return "user/user";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(){		
		return "user/add";
	}
	
	//分配角色
		@ResponseBody
		@RequestMapping("/doAssignRole")
		public Object doAssignRole(Integer userid,Data data){
			AjaxResult result = new AjaxResult();
			try {
				
				service.saveUserRoleRelationship(userid,data);
				result.setSuccess(true);
			} catch (Exception e) {
				result.setSuccess(false);
				e.printStackTrace();
				result.setMessage("分配角色数据失败!");
			}
			 
			return result; 
		}
		
		//取消分配角色
		@ResponseBody
		@RequestMapping("/doUnAssignRole")
		public Object doUnAssignRole(Integer userid,Data data){
			AjaxResult result = new AjaxResult();
			try {
				
				service.deleteUserRoleRelationship(userid,data);
				result.setSuccess(true);
			} catch (Exception e) {
				result.setSuccess(false);
				e.printStackTrace();
				result.setMessage("取消分配角色数据失败!");
			}
			 
			return result; 
		}
		
		//显示分配页面数据.
	@RequestMapping("/assingnrole")
	public String assingnrole(Integer id,Map map){	
		List<Role> allRole = service.queryAllRole();
		List<Role> roleId = service.queryRoleById(id);
		
		ArrayList<Role> leftRole = new ArrayList<Role>();
		ArrayList<Role> rightRole = new ArrayList<Role>();
		
		for (Role role : allRole) {
			if(roleId.contains(role.getId())){
			rightRole.add(role);
			}else {
				leftRole.add(role);

			}
		}
		map.put("rightRole",rightRole);
		map.put("leftRole", leftRole);
		
		return "user/assingnrole";
	}
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id , Map map){	
		User user = service.getUserById(id);
		map.put("user", user);
		return "user/update";
	}
	
	/*
	 * //接收多条数据.
	 * */
	@ResponseBody
	@RequestMapping("/doDeleteBatch")
	public Object doDeleteBatch(Data data){
			AjaxResult result = new AjaxResult();
		try {
			int count = service.deleteBatchUserByVO(data);
			result.setSuccess(count==data.getDatas().size());
		 
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("删除数据失败!");
		}
		 
		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}
	
	//接收一个参数名带多个值.
	//删除数据
	/*@ResponseBody
	@RequestMapping("/doDeleteBatch")
	public Object doDeleteBatch(Integer[] id){
			AjaxResult result = new AjaxResult();
		try {
			int count = service.deleteBatchUser(id);
			result.setSuccess(count==id.length);
		 
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("删除数据失败!");
		}
		 
		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}*/

	//删除数据
			@ResponseBody
			@RequestMapping("/doDelete")
			public Object doDelete(Integer id){
					AjaxResult result = new AjaxResult();
				try {
					int count = service.deleteById(id);
					result.setSuccess(count==1);
				 
				} catch (Exception e) {
					result.setSuccess(false);
					e.printStackTrace();
					result.setMessage("删除数据失败!");
				}
				 
				return result; //将对象序列化为JSON字符串,以流的形式返回.
			}
	
	//修改数据
		@ResponseBody
		@RequestMapping("/doUpdate")
		public Object update(User user){
				AjaxResult result = new AjaxResult();
			try {
				int count = service.updateUser(user);
				result.setSuccess(count==1);
			 
			} catch (Exception e) {
				result.setSuccess(false);
				e.printStackTrace();
				result.setMessage("修改数据失败!");
			}
			 
			return result; //将对象序列化为JSON字符串,以流的形式返回.
		}
	//条件查询
	@ResponseBody
	@RequestMapping("/user")
	public Object index(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno,
				@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize,String queryText){
		AjaxResult result = new AjaxResult();
		try {
			Map paramMap = new HashMap();
			
			paramMap.put("pageno", pageno);
			paramMap.put("pagesize", pagesize);
			
			if(StringUtil.isNotEmpty(queryText)) {
				if(queryText.contains("%")) {
					queryText = queryText.replaceAll("%", "\\\\%");
				}
				paramMap.put("queryText", queryText); 
			}
				Page page = service.queryUserPage(paramMap);

				result.setSuccess(true);
				result.setPage(page);
			

			} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("查询数据失败!");
		}
		 
		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}
	
	
	//增加数据
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(User user){
			AjaxResult result = new AjaxResult();
		try {
			int count = service.saveUser(user);//保存成功返回1，失败返回0
			
			result.setSuccess(count==1);
		 
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("保存数据失败!");
		}
		 
		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}
	/*//异步请求
	@ResponseBody
	@RequestMapping("/user")
	public Object index(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno,
				@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize){
		AjaxResult result = new AjaxResult();
		try {
			Page page = service.queryUserPage(pageno,pagesize);
			
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("查询数据失败!");
		}
		 
		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}
	*/
	
	/*
	//同步请求
	@RequestMapping("/user")
	public String queryUserPage(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno ,
			@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize,Map map) {
		
		
		Page page = service.queryUserPage( pageno , pagesize);
		map.put("page", page);
		return "user/user";
	}*/
	


}
