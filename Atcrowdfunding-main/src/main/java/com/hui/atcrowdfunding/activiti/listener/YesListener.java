package com.hui.atcrowdfunding.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class YesListener implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("审核通过");
	}
}
