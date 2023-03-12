package com.xxgl.utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


public class HandlerPeocess  {
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managerService;
	@Autowired
	private RuntimeService runtimeService;


	/**
     * 通过任务id跳转到上一步任务
     * @param taskId 任务id
     * @return
     */
    public String executeAndJumpOneTask(String taskId) {
        try {
            /** 取得当前需要撤回的任务 ***/
            HistoricTaskInstance currTask = historyService
                    .createHistoricTaskInstanceQuery().taskId(taskId)
                    .singleResult();

            /** 获取当前任务所属流程定义，进而获取流程定义key **/
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(currTask.getProcessDefinitionId());

            /** 获取当前任务的前一个任务id **/
            String taskHisId = getPrevTaskByCurrentTaskId(processDefinition.getKey(),currTask.getProcessInstanceId(),taskId);

            /** 取回流程节点 taskId当前任务id，taskHisId需要取回的任务的id***/
            callBackProcess(taskId,taskHisId);
            //删除历史流程走向记录
            historyService.deleteHistoricTaskInstance(currTask.getId());
            historyService.deleteHistoricTaskInstance(taskId);
        } catch (Exception e) {
            return "撤回失败 ：" + e.getMessage();
        }

        return "success";
    }


    /**
     * 取回流程中当前任务的上一个任务
     * @param taskId 当前任务ID
     * @param activityId 取回节点ID （这里不一定是上一个流程节点id）
     * @throws Exception
     */
    public void callBackProcess(String taskId, String activityId) throws Exception {
        if (StringUtils.isEmpty(activityId)) {
            throw new Exception("目标节点ID为空！");
        }
        //通过任务id获取流程实例id
        String processInstanceId = findProcessInstanceByTaskId(taskId).getId();

        //通过任务id获取任务定义key
        String taskDefinitionKey = findTaskById(taskId).getTaskDefinitionKey();

        // 查找所有并行任务节点，同时取回
        List<Task> taskList = findTaskListByKey(processInstanceId, taskDefinitionKey);
        for (Task task : taskList) {
            commitProcess(task.getId(), null, activityId);
        }
    }


    /**  
     * 提交流程/流程转向 
     * @param taskId 当前任务ID  
     * @param variables 流程变量  
     * @param activityId 流程转向执行任务节点ID,此参数为空，默认为提交操作  
     * @throws Exception  
     */
    private void commitProcess(String taskId, Map<String, Object> variables,
                               String activityId) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
        } else {
            // 流程转向操作    
            turnTransition(taskId, activityId, variables);
        }
    }

    /**
     * 流程转向操作
     * @param taskId  当前任务ID
     * @param activityId 目标节点任务ID
     * @param variables 流程变量
     * @throws Exception
     */
    private void turnTransition(String taskId, String activityId, Map<String, Object> variables) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);
        // 创建新流向,即将当前节点的流向指向需要跳转的节点流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标任务的活动节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点，将当前节点指向需要跳转的节点
        newTransition.setDestination(pointActivity);

        /** 执行转向任务 这种方法转向执行任务获取到的任务id将不再是原来的任务id ***/
        // todo 这里需要将撤回操作的操作人定义为当前登录操作的用户（即撤回用户）？？？？ ***/
        //fixme 算法待修改
        taskService.complete(taskId, variables);

        /** 任务执行完需要将任务还原 ***/
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);
        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    /**
     * 还原指定活动节点流向
     * @param activityImpl 活动节点
     * @param oriPvmTransitionList  原有节点流向集合
     */
    private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }


    /**
     * 根据任务ID和目标节点ID获取活动节点
     * @param taskId  任务ID
     * @param activityId 活动节点ID，如果为null或""，则默认查询当前活动节点 如果为"end"，则查询结束节点
     * @return
     * @throws Exception
     */
    private ActivityImpl findActivitiImpl(String taskId, String activityId) throws Exception {
        /** 通过当前任务id，取得流程定义 **/
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        /** 获取当前活动节点ID，如果目标节点为空，获取当前活动节点 ，不为空，获取目标节点**/
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }else{
            HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(activityId) .singleResult();
            activityId = currTask.getTaskDefinitionKey();
        }

        /** 根据流程定义，获取该流程实例的结束节点 **/
        if (activityId.toUpperCase().equals("END")) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                /** 获取所有当前活动节点的输出节点 **/
                List<PvmTransition> pvmTransitionList = activityImpl .getOutgoingTransitions();

                /** 如果当前节点没有输出路径了，那么当前节点为结束节点 **/
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }

        /** 根据节点ID，获取对应的活动节点 **/
        ActivityImpl activityImpl = ((ProcessDefinitionImpl)processDefinition).findActivity(activityId);
        return activityImpl;
    }

    /**
     * 清空指定活动节点流向
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();
        return oriPvmTransitionList;
    }

    /**
     * 根据任务ID获取流程定义
     * @param taskId  任务ID
     * @return 流程定义实体
     * @throws Exception
     */
    public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId( String taskId) throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService) .getDeployedProcessDefinition(findTaskById(taskId) .getProcessDefinitionId());
        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }
        return processDefinition;
    }


    /**
     * 根据任务ID获得任务实例
     * @param taskId 任务ID
     * @return 任务实例
     * @throws Exception
     */
    private TaskEntity findTaskById(String taskId) throws Exception {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new Exception("任务实例未找到!");
        }
        return task;
    }

    /**
     * 根据流程实例ID和任务key值查询所有同级任务集合
     * @param processInstanceId  流程实例id
     * @param key 任务定义key
     * @return
     * */
    private List<Task> findTaskListByKey(String processInstanceId, String key) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(key).list();
    }

    /**
     * 根据任务ID获取对应的流程实例
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    public ProcessInstance findProcessInstanceByTaskId(String taskId) throws Exception {
        // 找到流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId( findTaskById(taskId).getProcessInstanceId()) .singleResult();
        if (processInstance == null) {
            throw new Exception("流程实例未找到!");
        }
        return processInstance;
    }


    /**
     * 通过流程定义key和当前任务id来获取流程图中，当前任务所处节点的上一个节点信息
     * @param dkey
     * @param currentTaskId
     *  1.通过taskId获得executionid
     *  2.通过executionId获得currentActivityId
     *  3.通过currentActivityId获得ActivityImpl对象
     *  4.通过ActivityImpl对象获得incomingTransitions()
     *  5.通过transitionImpl对象获得source（ActivityImpl类型）
     *  6.通过ActivityImpl获得activityId
     *  7.通过activityId（这个activityId就是当前任务所在节点的前一个节点了）在流程历史里查询对应的task。
     * @return
     */
    public List<String> getPrevActivitiByCurrentTaskId(String dkey,String currentTaskId){
        /** 用于存储当前节点的所有上一个节点id **/
        List<String>  preTaskIds = new ArrayList<>();
        //任务查询条件
        TaskQuery taskQuery = taskService.createTaskQuery();
        //流程实力查询条件
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();

        //获取当前任务
        Task task = taskQuery.taskId(currentTaskId).singleResult();
        if(task != null){
            //获取当前流程任务的执行路线id
            String executionId = task.getExecutionId();

            //获取运行时流程执行实例
            Execution execution = executionQuery.executionId(executionId).singleResult();

            String activityId = execution.getActivityId();

            /** 根据流程定义的key获取流程定义所有的activity模型时期对象，即获取待所有的流程组件对象信息 ***/
            List<ActivityImpl> allActivities = getAllActivities(dkey);
            for(ActivityImpl activity : allActivities){

                /** 当获取到当前节点的时候，才执行之后的操作，主要需要通过当前节点来获取指向该节点的上一个节点 **/
                String id = activity.getId();
                if(!id.equals(activityId)){
                    continue;
                }

                /** PVM
                 PVM:Process Virtal Machine,流程虚拟机API暴露了流程虚拟机的POJO核心，
                 流程虚拟机API描述了一个工作流流程必备的组件，这些组件包括：
                 PvmProcessDefinition：流程的定义，形象点说就是用户画的那个图。静态含义。
                 PvmProcessInstance：流程实例，用户发起的某个PvmProcessDefinition的一个实例，动态含义。
                 PvmActivity：流程中的一个节点
                 PvmTransition：衔接各个节点之间的路径，形象点说就是图中各个节点之间的连接线。
                 PvmEvent：流程执行过程中触发的事件 **/
                /** 这里获取 当前活动的所有入口 ,首先获取指向该节点的所有路径**/
                List<PvmTransition> incomingTransitions = activity.getIncomingTransitions();

                /** 遍历路径，获取路径的源节点， 然后通过远点获取源节点的id，保存到集合中**/
                for(PvmTransition transition : incomingTransitions){
                    //获取路径源节点
                    PvmActivity source = transition.getSource();
                    //源节点id
                    String preTaskId = source.getId();
                    //加入集合
                    preTaskIds.add(preTaskId);
                }
            }
        }
        return preTaskIds;
    }


    /**
     * 通过当前任务id获取前一个任务
     * @param dkey 流程定义key
     * @param processInstanceId 流程实例id
     * @param taskId 当前任务id
     * @return 离当当前任务最近的上一个任务
     */
    public String getPrevTaskByCurrentTaskId(String dkey,String processInstanceId,String taskId){
        HistoricTaskInstance historicTaskInstance = null;

        /** 这里是获取当前流程定义所对应流程图中指向当前节点的所有上一个节点id **/
        List<String> prevTaskIds = getPrevActivitiByCurrentTaskId(dkey, taskId);

        if(prevTaskIds != null && prevTaskIds.size() > 0){
            /** 用于存放当前任务的历史节点信息，在后面进行时间比较，获取最新的上一个节点 **/
            Map<String,HistoricActivityInstance> instanceMap = new HashMap<>();

            /** 遍历当前节点的所有指向当前节点的源节点。判断最新的节点是哪个 **/
            for(String preact : prevTaskIds){
                /** 根据当前流程实例id获取 HistoricActivityInstance ，HistoricActivityInstance包含一个活动(流程上的节点)的执行信息 **/
                HistoricActivityInstance activityInstance = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId).activityId(preact).singleResult();

                if(activityInstance == null){
                    continue;
                }

                /** 获取当前节点实例的结束时间 ***/
                Date endTime = activityInstance.getEndTime();

                /** 这里首先获取上一个遍历存放的历史节点信息**/
                HistoricActivityInstance activityInstance1 = instanceMap.get(taskId);

                /** 如果已存放上一个节点信息，那么，进行时间比较，放入最近的时间的历史节点信息，反之加入集合，用于下一个循环比较 **/
                if(activityInstance1 != null){
                    Date endTime1 = activityInstance1.getEndTime();
                    if(endTime1.after(endTime)){
                        continue;
                    }
                    instanceMap.put(taskId,activityInstance1);
                }else{
                    instanceMap.put(taskId,activityInstance);
                }

            }

            /** 获取前一步通过时间比较获取的结束时间最近的当前任务节点的上一个节点 **/
            HistoricActivityInstance activityInstance = instanceMap.get(taskId);

            /** 获取挂接在当前历史节点下的当前任务的上一个任务 **/
            if(activityInstance != null){
                String hisTaskId = activityInstance.getTaskId();
                historicTaskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskId(hisTaskId).singleResult();
            }
        }

        return historicTaskInstance.getId();
    }


    /**
     * activity 是模型时期对象
     *
     * @params: [dkey 流程定义的key]
     * @return:
     * @描述: 根据流程定义的key获取流程定义所有的activity
     *
     * 释义：在设计流程时每一个组件在Activiti中都可以称之为——Activity，
     *       部署流程时引擎把XML文件保存到数据库；
     *       当启动流程、完成任务时会从数据库读取XML并转换为Java对象，
     *       如果想在处理任务时获取任务的一些配置，
     *       例如某个任务配置了哪些监听器或者条件Flow配置了什么条件表达式，
     *       可以获取具体的Activity。
     */
    public List<ActivityImpl> getAllActivities(String dkey) {
        List<ActivityImpl> activities = new ArrayList<>();

        /** 根据流程定义的key获取流程定义对象 **/
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(dkey).singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(definition.getId());

        /** 获取所有的activity -- 即获取所有节点信息**/
        activities = processDefinition.getActivities();

        return activities;
    }
}

