package com.hui.atcrowdfunding.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.dao.RoleMapper;
import com.hui.atcrowdfunding.manage.service.RoleService;
import com.hui.atcrowdfunding.util.Page;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	public Page queryRolePage(Map paramMap) {
		
		Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
		
		Integer startIndex = page.getStartIndex();
	 paramMap.put("startIndex", startIndex);

		List<User> datas  = roleMapper.queryList(paramMap);
		
	
		page.setDatas(datas);
		
		Integer totalsize = roleMapper.queryCount();
		page.setTotalsize(totalsize);
		
		
		// TODO Auto-generated method stub
		return page;
	}

}
