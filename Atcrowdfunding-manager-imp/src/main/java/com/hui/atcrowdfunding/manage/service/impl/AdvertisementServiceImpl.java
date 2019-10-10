package com.hui.atcrowdfunding.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hui.atcrowdfunding.bean.Advertisement;
import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.dao.AdvertisementMapper;
import com.hui.atcrowdfunding.manage.service.AdvertisementService;
import com.hui.atcrowdfunding.util.Page;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired
	private AdvertisementMapper advertisementMapper;
	
	public Page queryAdvertPage(Map paramMap) {
		
		Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
		
		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndex", startIndex);
		
		
		List<User> datas  = advertisementMapper.queryList(paramMap);
		
	
		page.setDatas(datas);
		
		Integer totalsize = advertisementMapper.queryCount();
		page.setTotalsize(totalsize);
		
		
		// TODO Auto-generated method stub
		return page;
	}

	public int insertAdvert(Advertisement advert) {
		// TODO Auto-generated method stub
		return advertisementMapper.insert(advert);
	}

}
