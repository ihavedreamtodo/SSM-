package com.hui.atcrowdfunding.manage.service;

import java.util.Map;

import com.hui.atcrowdfunding.bean.Advertisement;
import com.hui.atcrowdfunding.util.Page;

public interface AdvertisementService {

	Page queryAdvertPage(Map paramMap);

	int insertAdvert(Advertisement advert);

}
