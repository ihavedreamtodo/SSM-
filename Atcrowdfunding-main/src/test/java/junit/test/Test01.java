/*package junit.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hui.atcrowdfunding.bean.User;
import com.hui.atcrowdfunding.manage.service.UserService;
 
import com.hui.atcrowdfunding.util.MD5Util;

public class Test01 {

	
	//模拟加数据
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring*.xml");
		
		UserService userService = ioc.getBean(UserService.class);
		
		 for(int i = 1; i<= 100; i++) {
			 User user = new User();
			 user.setLoginacct("f那就好"+i);
			 user.setUserpswd(MD5Util.digest("123")); 
			 user.setEmail("ddd@hui.com");
			 user.setCreatetime("2017-01-15 10:41:00");
			 user.setUsername("dfd"+i);
			 userService.saveUser(user);
		 }
		

	}

}
*/