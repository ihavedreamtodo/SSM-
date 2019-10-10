package com.hui.atcrowdfunding.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.manage.dao.PermissionMapper;
import com.hui.atcrowdfunding.manage.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
     @Autowired
     private PermissionMapper permissionMapper;

	public List<Permission> queryAllPermission() {
		// TODO Auto-generated method stub
		return permissionMapper.queryAllPermission();
	}

	public int savePermission(Permission permission) {
		// TODO Auto-generated method stub
		return permissionMapper.insert(permission);
	}

	public List<Integer> queryAllPermissionByRoleId(Integer roleid) {
		// TODO Auto-generated method stub
		return permissionMapper.queryAllPermissionByRoleId(roleid);
	}

 
	 
}
