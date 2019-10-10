package com.hui.atcrowdfunding.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class NoListener implements ExecutionListener {

	public void notify(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("审核不通过");
	}

}
