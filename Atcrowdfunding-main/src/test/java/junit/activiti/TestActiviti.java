package junit.activiti;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hui.atcrowdfunding.activiti.listener.NoListener;
import com.hui.atcrowdfunding.activiti.listener.YesListener;

public class TestActiviti {

	ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");
	
	
	//测试流程监听器
	
	@Test
	public void test05() {
		//有变量的流程实例
		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("yesListener", new YesListener());
		hashMap.put("noListener", new NoListener());
		
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById(processDefinition.getId(),hashMap);
		
		System.out.println(startProcessInstanceById);
		}
		//领取任务
	@Test
	public void test051() {
		
		 ProcessDefinition singleResult = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
	 TaskService taskService = processEngine.getTaskService();
	 
	 TaskQuery taskQuery = taskService.createTaskQuery();
	 List<Task> list = taskQuery.taskAssignee("zhangsan").list();
	 
	/*	
		List<Task> list = taskService.createTaskQuery().taskAssignee("zhangsan").list();
	*/	System.out.println("zhangsan的任务量"+list.size());
		
		for (Task task : list) {
			taskService.setVariable(task.getId(), "flag", "false");//这个数据是通过表单传进来的
			taskService.complete(task.getId());//完成任务
		}
		System.out.println("流程结束");
		
	}
	
	//8.流程变量，
	//如果存在流程变量，则在启动流程实例的时候，要给流程变量赋值。否则会报错
	
	//7.领取任务
	//6.查询历史实例
	//5.查询流程实例的任务
	//4.启动流程定义实例*
	@Test
	public void test04() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess").latestVersion().singleResult();
		
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById(processDefinition.getId());
		
		System.out.println(startProcessInstanceById);
	}
	
	
	//3.查询流程
	//2.部署流程定义*
	@Test
	public void test01() {
		System.out.println("processEngine="+processEngine);
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess3.bpmn").deploy();
		System.out.println(deploy);
	}
	//创建流程引擎，创建23张表
	/*	@Test
	public void test01() {
		ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");
		System.out.println("processEngine="+processEngine);
	}*/
	
}
