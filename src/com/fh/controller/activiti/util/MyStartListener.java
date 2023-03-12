package com.fh.controller.activiti.util;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.session.Session;

import com.fh.util.Jurisdiction;

/**
 * 说明：指定下一任务待办人
 * 作者：FH Admin huangjianling
 * 官网：
 */
@SuppressWarnings("serial")
public class MyStartListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
    	/*
    	EventName:start
		ProcessDefinitionId:KEY_plan:2:97504
		ProcessInstanceId:97505
		System.out.println("流程启动");
        System.out.println("EventName:" + delegateExecution.getEventName());
        System.out.println("ProcessDefinitionId:" + delegateExecution.getProcessDefinitionId());
        System.out.println("ProcessInstanceId:" + delegateExecution.getProcessInstanceId());
        System.out.println("=======");
    	* */
    }
}
