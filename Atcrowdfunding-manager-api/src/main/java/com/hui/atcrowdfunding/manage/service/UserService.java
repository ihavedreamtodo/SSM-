package com.hui.atcrowdfunding.manage.service;

import java.util.List;
import java.util.Map;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.bean.Role;
import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.util.Page;
import com.hui.atcrowdfunding.vo.Data;

public interface UserService {

	User queryUserLogin(Map<String, Object> paramMap);

	//Page queryUserPage(Integer pageno ,Integer pagesize);
	
	int saveUser(User user);

	Page queryUserPage(Map<String,Object> paramMap);

	int updateUser(User user);

	User getUserById(Integer id);

	int deleteById(Integer id);
/*
	int deleteBatchUser(Integer[] id);
*/
	int deleteBatchUserByVO(Data data);

	List<Role> queryAllRole();

	List<Role> queryRoleById(Integer id);

	int saveUserRoleRelationship(Integer userid, Data data);

	int deleteUserRoleRelationship(Integer userid, Data data);

	List<Permission> queryPermissionByUserid(Integer id);

}
