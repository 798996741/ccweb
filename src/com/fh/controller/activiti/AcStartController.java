package com.fh.controller.activiti;

import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.fh.controller.base.BaseController;
import com.fh.service.system.operatelog.impl.OperateLogService;
import com.xxgl.service.impl.WorkorderService;
import com.xxgl.utils.SpringContextHolder;

/**
 * 说明：启动流程用
 */
public class AcStartController extends BaseController {
	
	@Autowired
	private RuntimeService runtimeService1; 		//与正在执行的流程实例和执行对象相关的Service(执行管理，包括启动、推进、删除流程实例等操作)
	RuntimeService runtimeService = (RuntimeService) SpringContextHolder.getSpringBean("runtimeService");  
	
	/**通过KEY启动流程实例(不带变量)
	 * @param processInstanceKey //流程定义的KEY
	 * @return 返回流程实例ID
	 */
	protected String startProcessInstanceByKey(String processInstanceKey){
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceKey);			//用流程定义的KEY启动，会自动选择KEY相同的流程定义中最新版本的那个(KEY为模型中的流程唯一标识)
		return processInstance.getId();	//返回流程实例ID
	}
	
	/**通过KEY启动流程实例(带变量)
	 * @param processInstanceKey //流程定义的KEY
	 * @return 返回流程实例ID
	 */
	protected String startProcessInstanceByKeyHasVariables(String processInstanceKey,Map<String,Object> map){
		//RuntimeService runtimeService=new RuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceKey, map);	//map存储变量 用流程定义的KEY启动，会自动选择KEY相同的流程定义中最新版本的那个(KEY为模型中的流程唯一标识)
		return processInstance.getId();	//返回流程实例ID
	}
	
	/**通过ID启动流程实例
	 * @param processInstanceId //流程定义的ID
	 * @return 返回流程实例ID
	 */
	protected String startProcessInstanceById(String processInstanceId){
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processInstanceId);			//用流程定义的ID启动
		return processInstance.getId();	//返回流程实例ID
	}
	
	/**
    * 终止正在运行的流程实例
    * @author:kaka
    * @param dkey:流程定义key,businessKey:业务id
    */
	public void stopRunProcessInstance(String processInstanceId) {
		//删除流程：
		//流程没有结束：
		//taskService.addComment(taskId, processInstanceId, comment);
		if(runtimeService==null){
			runtimeService=(RuntimeService) runtimeService1;
		}
		runtimeService.deleteProcessInstance(processInstanceId,"退回");
		//historyService.deleteHistoricProcessInstance(processInstanceId);//(顺序不能换)
		//流程已经结束：historyService.deleteHistoricProcessInstance(procesInstanceId);
		//runtimeService.suspendProcessInstanceById(processInstanceId);
	} 
   
}
