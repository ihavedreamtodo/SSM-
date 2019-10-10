package com.hui.atcrowdfunding.manage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hui.atcrowdfunding.util.AjaxResult;
import com.hui.atcrowdfunding.util.Page;


//这里的分页查询做失败了，用list可以,用listpage步可以
@Controller
@RequestMapping("/process")
public class ProcessController {
	
	@Autowired
private RepositoryService repositoryService;
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "process/process";
		}
	
	@RequestMapping("/toShow")
	public String toshow() {
		return "process/showimg";
		}
	
	
	
	
	@ResponseBody
	@RequestMapping("/showimgProDef")
	public void showimgProDef(String id ,HttpServletResponse response) throws Exception {//这个id是流程定义id
		 	ProcessDefinition singleResult = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
			InputStream inputStream = repositoryService.getResourceAsStream(singleResult.getDeploymentId(), singleResult.getDiagramResourceName());//输入流
			//输出流,将图片的src路径返回给客户端
			ServletOutputStream outputStream = response.getOutputStream();
		 IOUtils.copy(inputStream, outputStream);
		  
	}
	
	//这个类使用activiti框架的好处是不需要写业务层
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object doIndex(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno, 
			@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize) {
		AjaxResult result = new AjaxResult();
		try {
			Page page = new Page(pageno, pagesize);
		/*	ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
			List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pagesize);
			*/
			ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		System.out.println("============================");	
		System.out.println(processDefinitionQuery.list());
		//List<ProcessDefinition> listPage = processDefinitionQuery.list();//用这个可以查到流程定义表
		//这里用下面这一句查询步可以，不知道为什么她查询的是job表，那就只能自己想办法弄成分页的，或者就不弄成分页显示了吧】
			List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pagesize);
			List<Map<String,Object>> myListPage = new ArrayList<Map<String, Object>>();
			for (ProcessDefinition processDefinition : listPage) {
				Map<String, Object> pd = new HashMap<String, Object>();
				pd.put("name", processDefinition.getName());
				pd.put("version", processDefinition.getVersion());
				pd.put("key", processDefinition.getKey());
				pd.put("id", processDefinition.getId());
				myListPage.add(pd);
			}
			Long totalsize = processDefinitionQuery.count();
			page.setTotalsize(totalsize.intValue());
			page.setDatas(myListPage);
			 result.setPage(page);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setSuccess(false);
			result.setMessage("查询流程定义失败");
			e.printStackTrace();
			
		}
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(String id,String username) {//这个id是流程定义id
		AjaxResult result = new AjaxResult();
		try {
			
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
			repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);//true是级联删除，这个id需要部署id
		 
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setSuccess(false);
			result.setMessage("删除流程定义失败");
			e.printStackTrace();
			
		}
		
		return result;
	}
	

	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(HttpServletRequest request) {
		AjaxResult result = new AjaxResult();
		try {
			//注意上传文件和其他的不同
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		 MultipartFile multipartFile = multipartHttpServletRequest.getFile("processDefFile");
		 
		 //部署
		 repositoryService.createDeployment()
		 		.addInputStream(multipartFile.getOriginalFilename(), multipartFile.getInputStream())
		 		.deploy();
		 
		 
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setSuccess(false);
			result.setMessage("查询流程定义失败");
			e.printStackTrace();
			
		}
		
		return result;
	}
	

}
