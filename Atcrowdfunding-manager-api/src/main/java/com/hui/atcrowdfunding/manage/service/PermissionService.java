package com.hui.atcrowdfunding.manage.service;

import java.util.List;

import com.hui.atcrowdfunding.bean.Permission;

public interface PermissionService {

	List<Permission> queryAllPermission();

	int savePermission(Permission permission);

}
