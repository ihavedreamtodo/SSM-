package com.hui.atcrowdfunding.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.service.PermissionService;
import com.hui.atcrowdfunding.util.AjaxResult;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	 
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "permission/index";
	}
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "permission/add";
	}
	
	
	 
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(Permission permission){
			AjaxResult result = new AjaxResult();
		try {
			int count = permissionService.savePermission(permission);//保存成功返回1，失败返回0
			
			result.setSuccess(count==1);
		 
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("保存许可失败!");
		}
		 
		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}
	
	
	
	
	
	
	
	//Demo5 - 用Map集合来查找父,来组合父子关系.减少循环的次数 ,提高性能.
	
	//@ResponseBody的作用是：要返回值或者对象给前台。所以必须要加上
	@ResponseBody
		
	@RequestMapping("/loadData")
	public Object loadData() {

			AjaxResult result = new AjaxResult();
		
		try {
			
			List<Permission> root = new ArrayList<Permission>();

			
			List<Permission> childredPermissons =  permissionService.queryAllPermission();
			
			
			Map<Integer,Permission> map = new HashMap<Integer,Permission>();//100
			
			for (Permission innerpermission : childredPermissons) {
				map.put(innerpermission.getId(), innerpermission);
			}
			
			
			for (Permission permission : childredPermissons) { //100
				//通过子查找父
				//子菜单
				Permission child = permission ; //假设为子菜单
				if(child.getPid() == null ){
					root.add(permission);
				}else{
					//父节点
					Permission parent = map.get(child.getPid());
					parent.getChildren().add(child);
				}
			}

			
			
			result.setSuccess(true);
			result.setData(root);
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("加载许可树数据失败!");
		}
		

		return result ;
	}

		
	}
 
