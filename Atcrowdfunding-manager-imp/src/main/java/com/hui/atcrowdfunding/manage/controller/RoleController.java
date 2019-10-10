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

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.bean.Role;
import com.hui.atcrowdfunding.manage.service.PermissionService;
import com.hui.atcrowdfunding.manage.service.RoleService;
import com.hui.atcrowdfunding.util.AjaxResult;
import com.hui.atcrowdfunding.util.Page;
import com.hui.atcrowdfunding.util.StringUtil;
import com.hui.atcrowdfunding.vo.Data;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "role/user";
	}

	@RequestMapping("/assignPermission")
	public String assignPermission() {
		return "role/assignPermission";
	}
	
	@ResponseBody
	@RequestMapping("/doAssignPermission")
	public Object doAssignPermission(Integer roleid, Data datas){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.saveRolePermissionRelationship(roleid,datas);
			
			result.setSuccess(count==datas.getIds().size());
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/role")
	 	//条件查询
  public Object role(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno,
					@RequestParam(value="pagesize",required=false,defaultValue="5") Integer pagesize,String queryText){
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
					Page page = roleService.queryRolePage(paramMap);

					result.setSuccess(true);
					result.setPage(page);
				

				} catch (Exception e) {
				result.setSuccess(false);
				e.printStackTrace();
				result.setMessage("查询数据失败!");
			}
			 
			return result; //将对象序列化为JSON字符串,以流的形式返回.
		 
				
	}
	
	
	
 
	

	//Demo5 - 用Map集合来查找父,来组合父子关系.减少循环的次数 ,提高性能.
	
	//@ResponseBody的作用是：要返回值或者对象给前台。所以必须要加上
	@ResponseBody
		
	@RequestMapping("/loadDataAsync")
	public Object loadDataAsync(Integer roleid) {
 
			List<Permission> root = new ArrayList<Permission>();

			
			List<Permission> childredPermissons =  permissionService.queryAllPermission();
			
			//根据roleid 查到已经分配的许可
			List<Integer> permissionsForRoleid = permissionService.queryAllPermissionByRoleId(roleid);
			
			Map<Integer,Permission> map = new HashMap<Integer,Permission>();//100
			
			for (Permission innerpermission : childredPermissons) {
				map.put(innerpermission.getId(), innerpermission);
				if(permissionsForRoleid.contains(innerpermission.getId())){
					innerpermission.setChecked(true);
				}
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
		return root ;
	}

	
}
