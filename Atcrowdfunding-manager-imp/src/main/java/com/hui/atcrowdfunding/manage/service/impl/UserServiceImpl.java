package com.hui.atcrowdfunding.manage.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui.atcrowdfunding.bean.Permission;
import com.hui.atcrowdfunding.bean.Role;
import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.exception.LoginFailException;
import com.hui.atcrowdfunding.manage.dao.UserMapper;
import com.hui.atcrowdfunding.manage.service.UserService;
import com.hui.atcrowdfunding.util.Const;
import com.hui.atcrowdfunding.util.MD5Util;
import com.hui.atcrowdfunding.util.Page;
import com.hui.atcrowdfunding.vo.Data;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	public User queryUserLogin(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		
	User user =	userMapper.queryUserLogin(paramMap);
	if(user==null) {
		//自定义异常
		throw new LoginFailException("用户账号或密码不正确");
		
	}
		return user;
	}

	/*public Page queryUserPage(Integer pageno ,Integer pagesize) {
		
		Page page = new Page(pageno, pagesize);
		
		Integer startIndex = page.getStartIndex();
		
		List<User> datas  = userMapper.queryList(startIndex,pagesize);
		
	
		page.setDatas(datas);
		
		Integer totalsize = userMapper.queryCount();
		page.setTotalsize(totalsize);
		
		
		// TODO Auto-generated method stub
		return page;
	}
*/
	public int saveUser(User user) {
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		Date date = new Date();
		String createtime = sdf.format(date);
		
		user.setCreatetime(createtime);
		user.setUserpswd(MD5Util.digest(Const.PASSWORD));
		return userMapper.insert(user);
	}

	public Page queryUserPage(Map<String,Object> paramMap) {
		Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
		
		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndex", startIndex);
		
		
		List<User> datas  = userMapper.queryList(paramMap);
		
	
		page.setDatas(datas);
		
		Integer totalsize = userMapper.queryCount();
		page.setTotalsize(totalsize);
		
		
		// TODO Auto-generated method stub
		return page;
	}

	public int updateUser(User user) {
		// TODO Auto-generated method stub
		
		return userMapper.updateByPrimaryKey(user);
	}

	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	/*public int deleteBatchUser(Integer[] id) {
		// TODO Auto-generated method stub
		int totalCount = 0;
		for (Integer integer : id) {
		int count =	userMapper.deleteByPrimaryKey(integer);
		totalCount+=count;
		}
		if(totalCount != id.length) {
			throw new RuntimeException("批量删除失败");
		}
		return totalCount;
	}*/

	public int deleteBatchUserByVO(Data data) {
		// TODO Auto-generated method stub
		return userMapper.deleteBatchUserByVO(data.getDatas());
	}

	public List<Role> queryAllRole() {
		// TODO Auto-generated method stub
		return userMapper.queryAllRole();
	}

	public List<Role> queryRoleById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.queryRoleById(id);
	}

	public int saveUserRoleRelationship(Integer userid, Data data) {
		// TODO Auto-generated method stub
		return userMapper.saveUserRoleRelationship(userid,data);
	}

	public int deleteUserRoleRelationship(Integer userid, Data data) {
		// TODO Auto-generated method stub
		return userMapper.deleteUserRoleRelationship(userid,data);
	}

	public List<Permission> queryPermissionByUserid(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.queryPermissionByUserid(id);
	}

 
	
	
}
