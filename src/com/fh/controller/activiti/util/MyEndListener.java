package com.fh.controller.activiti.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;


public class MyEndListener implements ExecutionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void notify(DelegateExecution delegateExecution) {
		
        System.out.println("流程结束");
        System.out.println("EventName:" + delegateExecution.getEventName());
        System.out.println("ProcessDefinitionId:" + delegateExecution.getProcessDefinitionId());
        System.out.println("ProcessInstanceId:" + delegateExecution.getProcessInstanceId());
        System.out.println("=======");
		/*
		Map<String,String> pd = new HashMap<String,String>();
		pd.put("procID",delegateExecution.getProcessInstanceId());
		pd.put("st","5");
		try {
			planService.setTaskIDByTopID(pd);
			planService.setPlanStatus(pd);//设置方案状态表
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//设置方案主表
        Map<String,String> pd = new HashMap<String,String>();
		pd.put("procID",delegateExecution.getProcessInstanceId());
		pd.put("st","5");
		try {
			topicManager.setTopicStatus(delegateExecution.getProcessInstanceId(),"3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
}
