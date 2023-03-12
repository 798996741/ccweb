package com.fh.controller.activiti.listener;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.Task;
/**
 * 会签监听
 * @author huangjianling
 *
 */
public class SignListener implements ExecutionListener{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	@Override	
	public void notify(DelegateExecution execution) throws Exception {	
		System.out.println("会签监听");
		//获取流程id
		String s_proc_id = execution.getCurrentActivityId();
		//获取流程参数pass，会签人员完成自己的审批任务时会添加流程参数pass，false为拒绝，true为同意
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = engine.getRuntimeService();
		TaskService taskService = engine.getTaskService();
		//boolean pass = (Boolean) runtimeService.getVariable(s_proc_id, "pass");
		//System.out.println(pass+"pass"+"d");
		/*
		 * false：有一个人拒绝，整个流程就结束了，
		 * 	因为Complete condition的值为pass == false，即，当流程参数为pass时会签就结束开始下一个任务
		 * 	所以，当pass == false时，直接设置下一个流程跳转需要的参数
		 * true：审批人同意，同时要判断是不是所有的人都已经完成了，而不是由一个人同意该会签就结束
		 * 	值得注意的是如果一个审批人完成了审批进入到该监听时nrOfCompletedInstances的值还没有更新，因此需要+1
		 */
		/*if(pass == false){
			//会签结束，设置参数result为N，下个任务为申请
			//runtimeService.setVariable(exId, "result", "N");
			//下个任务
			//String processInstanceId = delegateTask.getProcessInstanceId();
			//Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
			//System.out.println("下个任务编码：" + task.getId() + "，下个任务名称：" + task.getName());
		}else{*/
			//Integer complete = (Integer) runtimeService.getVariable(s_proc_id, "nrOfCompletedInstances");
			//Integer all = (Integer) runtimeService.getVariable(s_proc_id, "nrOfInstances");
			//说明都完成了并且没有人拒绝
			//System.out.println(all);
			//if((complete + 1) / all == 1){
				
				//判断样式
				//runtimeService.setVariable(s_proc_id, "RESULT", "no");
				//下个任务
				//String processInstanceId = execution.getProcessInstanceId();
				//Task task = taskService.createTaskQuery().processInstanceId(s_proc_id).singleResult();
				//System.out.println("下个任务编码：" + task.getId() + "，下个任务名称：" + task.getName());
			//}
		//}
	}

	/*@Override
	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("执行");
		
	}*/
	
}