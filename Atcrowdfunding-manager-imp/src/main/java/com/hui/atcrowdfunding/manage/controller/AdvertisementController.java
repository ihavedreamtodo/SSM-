package com.hui.atcrowdfunding.manage.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hui.atcrowdfunding.bean.Advertisement;
import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.service.AdvertisementService;
import com.hui.atcrowdfunding.util.AjaxResult;
import com.hui.atcrowdfunding.util.Const;
import com.hui.atcrowdfunding.util.Page;
import com.hui.atcrowdfunding.util.StringUtil;

 

@Controller
@RequestMapping("/advert")
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	
	@RequestMapping("/index")
	public String toIndex()  {
		return "advert/index";
	}
	@RequestMapping("/toUpdate")
	public String toUpdate()  {
		return "advert/update";
	}
	@RequestMapping("/toAdd")
	public String toAdd()  {
		return "advert/add";
	}
	
	/**
	 * 新增资质数据
	 * 含文件上传
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(HttpServletRequest request, Advertisement advert ,HttpSession session) {
		AjaxResult result = new AjaxResult();
		
		
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;
			
			MultipartFile mfile = mreq.getFile("advpic");
			
			String name = mfile.getOriginalFilename();//java.jpg
			String extname = name.substring(name.lastIndexOf(".")); // .jpg
			
			String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg
			
			ServletContext servletContext = session.getServletContext();
			String realpath = servletContext.getRealPath("/pics");
			
			String path =realpath+ "\\adv\\"+iconpath;
			
			mfile.transferTo(new File(path));
			
			User user = (User)session.getAttribute(Const.LOGIN_USER);
			advert.setUserid(user.getId());
			advert.setStatus("1");
			advert.setIconpath(iconpath);
			
			int count = advertisementService.insertAdvert(advert);
			result.setSuccess(count==1);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object index(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno,
				@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize,String queryText){
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
				Page page = advertisementService.queryAdvertPage(paramMap);

				result.setSuccess(true);
				result.setPage(page);
			

			} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("查询数据失败!");
		}
		 
		return result; //将对象序列化为JSON字符串,以流的形式返回.
	}
	
	
}
