package com.hui.atcrowdfunding.manage.service;

import java.util.List;
import java.util.Map;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.util.Page;
import com.hui.atcrowdfunding.vo.Data;

public interface RoleService {

	Page queryRolePage(Map paramMap);

	int saveRolePermissionRelationship(Integer roleid, Data datas);
 
}
