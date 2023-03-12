package com.fh.service.activiti.ruprocdef.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.IPUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.areamanage.impl.AreaManageService;
import com.fh.service.system.operatelog.OperateLogManager;
import com.fh.service.system.operatelog.impl.OperateLogService;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.user.impl.UserService;
import com.xxgl.service.impl.WorkorderService;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.utils.SpringContextHolder;

/** 
 * 说明： 正在运行的流程
 * 创建人：FH Q351412933
 * @version
 */
@Service("ruprocdefService")
public class RuprocdefService implements RuprocdefManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Autowired
	private TaskService taskService1; 			//任务管理 与正在执行的任务管理相关的Service
	@Resource(name="userService")
	private UserManager userService1;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService1;
	
	@Resource(name="workorderService")
	private WorkorderManager workorderService1;
	
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService1;
	@Resource(name = "operatelogService")
	private OperateLogManager operatelogService1;
	
	WorkorderService workorderService = (WorkorderService) SpringContextHolder.getSpringBean("workorderService");  
	TaskService taskService = (TaskService) SpringContextHolder.getSpringBean("taskService");  
	RuprocdefService ruprocdefService = (RuprocdefService) SpringContextHolder.getSpringBean("ruprocdefService");  
	UserService userService = (UserService) SpringContextHolder.getSpringBean("userService");  
	AreaManageService areamanageService = (AreaManageService) SpringContextHolder.getSpringBean("areamanageService");  
	OperateLogService operatelogService = (OperateLogService) SpringContextHolder.getSpringBean("operatelogService");  
	
	
	
	
	public  RuprocdefService(){
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		if(taskService==null){
			taskService=(TaskService) taskService1;
		}
		if(ruprocdefService==null){
			ruprocdefService=(RuprocdefService) ruprocdefService1;
		}
		if(userService==null){
			userService=(UserService) userService1;
		}
	}
	
	/*private static ApplicationContext ctx=null;
	private static WorkorderService workorderService;
	
	static{
		ctx = new ClassPathXmlApplicationContext("classpath:/spring/ApplicationContext-mvc.xml");
		workorderService = (WorkorderService) ctx.getBean("workorderService");	
	}*/
	
	//@Autowired
	//private RuprocdefService ruprocdefService;
	
	//@Autowired
	//private WorkorderService workorderService;
	
	//private TaskService taskService=new TaskService();
	/**待办任务 or正在运行任务列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		
		List<PageData> varList=(List<PageData>)dao.findForList("RuprocdefMapper.datalist", page);
		for(PageData pd_s:varList){
			if(!pd_s.getString("type").equals("4")&&!pd_s.getString("type").equals("5")){
				pd_s.put("iscs", workorderService.getCsnum(pd_s));
			}else{
				pd_s.put("iscs", "0");
			}
			
			
		}
		return varList;
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.datalistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public String getTaskID(String INSTID)throws Exception{
		return (String) dao.findForObject("RuprocdefMapper.getTaskID", INSTID);
	}
	
	/**流程变量列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> varList(PageData pd)throws Exception{
		List<PageData> varList=(List<PageData>)dao.findForList("RuprocdefMapper.varList", pd);
		
		return varList;
	}
	
	/**历史任务节点列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hiTaskList(PageData pd)throws Exception{
		List<PageData> varList=(List<PageData>)dao.findForList("RuprocdefMapper.hiTaskList", pd);
		
		return varList;
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getDealByDeptList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.getDealByDeptList", pd);
	}
	
	/**已办任务列表列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hitasklist(Page page)throws Exception{
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		List<PageData> varList=(List<PageData>)dao.findForList("RuprocdefMapper.hitaskdatalist", page);

		for(PageData pd_s:varList){
			if(!pd_s.getString("type").equals("4")&&!pd_s.getString("type").equals("5")){
				pd_s.put("iscs", workorderService.getCsnum(pd_s));
			}
			
		}
		return varList;
	}
	
	/**激活or挂起任务(指定某个任务)
	 * @param pd
	 * @throws Exception
	 */
	public void onoffTask(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.onoffTask", pd);
	}
	
	
	public void receive(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.receive", pd);
		dao.save("RuprocdefMapper.saveReceive", pd);
	}
	
	public PageData findTaskById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.findTaskById", pd);
	}
	
	public PageData findTaskByProc_id(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.findTaskByProc_id", pd);
	}
	
	
	/**激活or挂起任务(指定某个流程的所有任务)
	 * @param pd
	 * @throws Exception
	 */
	public void onoffAllTask(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.onoffAllTask", pd);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getTaskMapByID(String taskId)throws Exception{
		return (Map<String,String>)dao.findForObject("RuprocdefMapper.getTaskMapByID", taskId);
	}
	
	public PageData getDealByDept(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.getDealByDept", pd);
	}
	
	public void saveDealDept(PageData pd)throws Exception{
		dao.save("RuprocdefMapper.saveDealDept", pd);
	}
	
	public void delDealDept(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.delDealDept", pd);
	}
	
	
	public PageData getDealByOffice(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.getDealByOffice", pd);
	}
	
	public List<PageData> getDealByOfficeDept(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("RuprocdefMapper.getDealByOfficeDept", pd);
	}
	
	public void saveDealOffice(PageData pd)throws Exception{
		dao.save("RuprocdefMapper.saveDealOffice", pd);
	}
	
	public void delDealOffice(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.delDealOffice", pd);
	}
	
	
	public PageData getOffices(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.getOffices", pd);
	}
	
	public void saveOffices(PageData pd)throws Exception{
		dao.save("RuprocdefMapper.saveOffices", pd);
	}
	
	public void delOffices(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.delOffices", pd);
	}
	
	
	public List<PageData> getYj(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.getYj", pd);
	}
	
	public List<PageData> getOfficesListByDept(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.getOfficesListByDept", pd);
	}
	
	
	/*
	 * 获取处理信息
	 */
	
	public List<PageData> getClList(PageData pd,List<PageData> varList,String taskname)throws Exception{
		
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		if(taskService==null){
			taskService=(TaskService) taskService1;
		}
		if(ruprocdefService==null){
			ruprocdefService=(RuprocdefService) ruprocdefService1;
		}
		if(userService==null){
			userService=(UserService) userService1;
		}
		if(areamanageService==null){
			areamanageService=(AreaManageService) areamanageService1;
		}
		
		
		
		String lasttime="";
		List<PageData>	clList=new ArrayList();
		
		
		

		PageData pdcl=new PageData();
		PageData pdarea=new PageData();
		PageData pduser=new PageData();
		String[] arrcl=null;
		////System.out.println(varList+"varList");
		for(int i=0;i<varList.size();i++){							//根据耗时的毫秒数计算天时分秒
			
			if(varList.get(i).getString("TEXT_")!=null&&varList.get(i).getString("TEXT_").indexOf(",huangfege,")>=0){
				pdcl=new PageData();
				arrcl=varList.get(i).getString("TEXT_").split(",huangfege,");
				//System.out.println(arrcl+"arrcl"+varList.get(i).getString("TEXT_"));
				pdcl.put("clman", arrcl[0]);
				pdcl.put("USERNAME", arrcl[0]);
				pdcl.put("NAME_", varList.get(i).getString("NAME_"));
				if(arrcl.length==1){
					pdcl.put("clcont", "");
				}else if(arrcl.length>1){
					pdcl.put("clcont", arrcl[1]==null?"":arrcl[1]);
				}
				pdcl.put("cldate", String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_")==null?"":varList.get(i).get("LAST_UPDATED_TIME_")).substring(0, 19));
				//pdcl.put("cldate1", String.valueOf(varList.get(i).get("czdate")==null?"":varList.get(i).get("czdate")).substring(0, 16));
				
				//System.out.println(userService+"userService");
				
				pduser=userService.getUserInfoByUsername(pdcl);
				if(pduser!=null){
					pdcl.put("areaname", pduser.getString("areaname"));
					pdcl.put("name", pduser.getString("NAME"));
				}
				
				clList.add(pdcl);
				lasttime=String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_"));
			}
		}
		
		
		
		if(taskname.equals("多部门工单处理")||taskname.equals("部门领导审批")){
			pd.put("isdel","0");
			if(taskname.equals("多部门工单处理")){
				pd.put("iscl","1");
			}
			if(taskname.equals("部门领导审批")){
				pd.put("iscl","2");
			}
			List<PageData>	clList1=clList;
			List<PageData>	dealList = ruprocdefService.getDealByDeptList(pd);
			for(int i=0;i<dealList.size();i++){				
				if(dealList.get(i).getString("remark")!=null&&dealList.get(i).getString("remark").indexOf(",huangfege,")>=0){
					pdcl=new PageData();
					arrcl=dealList.get(i).getString("remark").split(",huangfege,");
					pdcl.put("clman", arrcl[0]);
					pdcl.put("USERNAME", arrcl[0]);
					pdcl.put("clcont", arrcl[1]==null?"":arrcl[1]);
					pdcl.put("cldate", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 19));
					//pdcl.put("cldate1", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 16));
					pduser=userService.getUserInfoByUsername(pdcl);
					if(pduser!=null){
						pdcl.put("areaname", pduser.getString("areaname"));
						pdcl.put("name", pduser.getString("NAME"));
					}
					
					
					for(int k=0;k<clList1.size();k++){
						if(pdcl.getString("clcont").equals(clList1.get(k).getString("clcont"))&&pdcl.getString("areaname").equals(clList1.get(k).getString("areaname"))&&pdcl.getString("cldate").substring(0, 16).equals(clList1.get(k).getString("cldate").substring(0, 16))){
							
						}else{
							clList.add(pdcl);
						}
					}
					
					
				}
			}
		}
		
		pd.put("isdel","");
		pd.put("iscl","1");
		List<PageData>	clList1=clList;
		List<PageData>	dealList = ruprocdefService.getOfficesListByDept(pd);
		for(int i=0;i<dealList.size();i++){				
			if(dealList.get(i).getString("remark")!=null&&dealList.get(i).getString("remark").indexOf(",huangfege,")>=0){
				pdcl=new PageData();
				arrcl=dealList.get(i).getString("remark").split(",huangfege,");
				pdcl.put("clman", arrcl[0]);
				pdcl.put("USERNAME", arrcl[0]);
				pdcl.put("clcont", arrcl[1]==null?"":arrcl[1]);
				pdcl.put("cldate", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 19));
				//pdcl.put("cldate1", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 16));
				pduser=userService.getUserInfoByUsername(pdcl);
				if(pduser!=null){
					pdcl.put("areaname", pduser.getString("areaname"));
					pdcl.put("name", pduser.getString("NAME"));
				}
				
				for(int k=0;k<clList1.size();k++){
					if(pdcl.getString("clcont").equals(clList1.get(k).getString("clcont"))&&pdcl.getString("areaname").equals(clList1.get(k).getString("areaname"))&&pdcl.getString("cldate").substring(0, 16).equals(clList1.get(k).getString("cldate").substring(0, 16))){
						
					}else{
						clList.add(pdcl);
					}
				}
			}
		}
		
		
		
		
		pd.put("isdel","");
		pd.put("iscl","1");
		clList1=clList;
		dealList = ruprocdefService.getDealByOfficeDept(pd);
		for(int i=0;i<dealList.size();i++){				
			if(dealList.get(i).getString("remark")!=null&&dealList.get(i).getString("remark").indexOf(",huangfege,")>=0){
				pdcl=new PageData();
				arrcl=dealList.get(i).getString("remark").split(",huangfege,");
				pdcl.put("clman", arrcl[0]);
				pdcl.put("USERNAME", arrcl[0]);
				pdcl.put("clcont", arrcl[1]==null?"":arrcl[1]);
				pdcl.put("cldate", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 19));
				//pdcl.put("cldate1", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 16));
				pduser=userService.getUserInfoByUsername(pdcl);
				if(pduser!=null){
					pdcl.put("areaname", pduser.getString("areaname"));
					pdcl.put("name", pduser.getString("NAME"));
				}
				
				
				for(int k=0;k<clList1.size();k++){
					if(pdcl.getString("clcont").equals(clList1.get(k).getString("clcont"))&&pdcl.getString("areaname").equals(clList1.get(k).getString("areaname"))&&pdcl.getString("cldate").substring(0, 16).equals(clList1.get(k).getString("cldate").substring(0, 16))){
						
					}else{
						clList.add(pdcl);
					}
				}
				
				
			}
		}
		
		List<PageData> listTemp = new ArrayList();
		
		PageData pd_task=ruprocdefService.findTaskByProc_id(pd);
		if(pd_task!=null){
			pdcl=new PageData();
			pdarea=new PageData();
			if(pd_task.getString("ASSIGNEE_")!=null&&!pd_task.getString("ASSIGNEE_").equals("")){
				pdcl.put("clman", pd_task.getString("ASSIGNEE_"));
				pdcl.put("USERNAME", pd_task.getString("ASSIGNEE_"));
				pdcl.put("NAME_", pd_task.getString("NAME_"));
				if(pd_task.getString("ISRECEIVE")!=null&&pd_task.getString("ISRECEIVE").equals("1")){
					pdcl.put("clcont", pd_task.getString("RECEIVEMANNAME")+"已接收");
				}else{
					pdcl.put("clcont", "未接收");
				}
				if(pd_task.get("RECEIVEDATE")!=null){
					pdcl.put("cldate", String.valueOf(pd_task.get("RECEIVEDATE")).substring(0, 19));
				}
				
				pduser=userService.getUserInfoByUsername(pdcl);
				if(pduser!=null){
					pdcl.put("areaname", pduser.getString("areaname"));
					pdcl.put("name", pduser.getString("NAME"));
				}else{
					
					arrcl=pd_task.getString("ASSIGNEE_").split(",");
					if(arrcl.length>=2){
						pd_task.put("USERNAME", arrcl[0]);
						pduser=userService.getUserInfoByUsername(pd_task);
						if(pduser!=null){
							pdcl.put("areaname", pduser.getString("areaname"));
							pdcl.put("name", pduser.getString("NAME"));
						}else{
							pd_task.put("tsdept", pd_task.getString("ASSIGNEE_"));
							pdarea=areamanageService.findByDepts(pd_task);
							if(pdarea!=null){
								pdcl.put("areaname", pdarea.getString("tsdeptname"));
								pdcl.put("name",pdarea.getString("tsdept"));
							}
						}
					}else{
						pd_task.put("AREA_CODE", pd_task.getString("ASSIGNEE_"));
						pdarea=areamanageService.findByAreaCode(pd_task);
						if(pdarea!=null){
							pdcl.put("areaname", pdarea.getString("NAME"));
							pdcl.put("name",pdarea.getString("NAME"));
						}
					}	
				}
				listTemp.add(pdcl);
			}
		}
		
		for(int i=0;i<clList.size();i++){
			if(!listTemp.contains(clList.get(i))){
				listTemp.add(clList.get(i));
			}
		}
	
        if (listTemp.size() > 1) {
            //list 集合倒叙排序
        	Collections.sort(listTemp, new Comparator<PageData>() {
                 @Override
                 public int compare(PageData o1, PageData o2) {
                     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     try {
                         Date dt1 = format.parse(o1.getString("cldate"));
                         Date dt2 = format.parse(o2.getString("cldate"));
                         if (dt1.getTime() > dt2.getTime()) {
                             return 1;
                         } else if (dt1.getTime() < dt2.getTime()) {
                             return -1;
                         } else {
                             return 0;
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     return 0;
                 }
             });
        }
        
       
		return listTemp;
	}
	
	@Transactional
	public String deal(String id,String PROC_INST_ID_,String ID_,String dwbm,String userid,String cfbm,String doaction,String msg,String tsdept,String OPINION,String ksoffice,String kstime) throws Exception{
		
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		if(taskService==null){
			taskService=(TaskService) taskService1;
		}
		if(ruprocdefService==null){
			ruprocdefService=(RuprocdefService) ruprocdefService1;
		}
		if(userService==null){
			userService=(UserService) userService1;
		}
		
		if(operatelogService==null){
			operatelogService=(OperateLogService) operatelogService1;
		}
		String clyj=OPINION;
		
		PageData pd = new PageData();
		String taskId ="";
		pd.put("proc_id", PROC_INST_ID_);
		pd.put("id", id);
		pd.put("ZXID", userid);
		pd.put("code", "");
		PageData pd_s =workorderService.findById(pd);
		long min=System.currentTimeMillis();
		long min1=0,min2=0,min3 = 0,min4=0,min5=0;
		//System.out.println("pd_s:"+pd_s);
		if(doaction!=null&&doaction.equals("azb")){
			taskId = ruprocdefService.getTaskID(PROC_INST_ID_);	//任务ID
			min1=System.currentTimeMillis();
			//System.out.println("派发工单："+(min1-min)+"毫秒");
			pd.put("OPINION","派发工单");
		}else{
			taskId = ID_;	//任务ID
			if(cfbm!=null&&!cfbm.equals("")){
				pd_s.put("cfbm",cfbm);
				workorderService.editCfbm(pd_s);
			}
			min1=System.currentTimeMillis();
			//System.out.println("处理重复部门："+(min1-min)+"毫秒");
		}
		
		String sfrom = "";
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		OPINION = sfrom + userid + ",huangfege,"+OPINION;//审批人的姓名+审批意见
		
		String newtsdept=tsdept;
		pd_s.put("newtsdept", newtsdept);
		String[] ndept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
		////System.out.println("tsdept:"+pd_s.getString("tsdept"));
		
		////System.out.println(tasks);
		
		
		Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
		if(task==null){
			return "未获取流程";
		}
		
		min2=System.currentTimeMillis();
		//System.out.println("查询流程名称："+(min2-min1)+"毫秒");
		
		String taskname=task.getName()==null?"":task.getName();
		//System.out.println(taskname+"taskname");
		if(taskname.equals("多部门工单处理")&&ndept.length==1){
			taskname="单部门工单处理";
		}
		
		if(taskname.equals("部门领导审批")&&ndept.length==1){
			taskname="单部门领导审批";
		}
        // 如果是会签流程
		boolean boo=false;
		String sendcont="",userstr="";
		if(pd_s!=null){
			sendcont="您有一个投诉工单需要处理：投诉内容："+pd_s.getString("tscont");
		}
		String nextdept="";
		String iszx=pd_s.getString("iszx")==null?"":pd_s.getString("iszx");
		String source=pd_s.getString("source")==null?"":pd_s.getString("source");
        if (taskname.equals("工单提交"))
        {
        	
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	String[] dept=tsdept.split(",");
        	map.put("提交人",userid);		//审批结果
        	map.put("提交内容",OPINION);		//审批结果
        	if(dept.length==1){								//批准
        		map.put("BMZR","yes");
			}else{			
				map.put("BMZR","no");
			}
        	map.put("depts",pd_s.getString("tsdept"));
        	pd.put("tsdept", pd_s.getString("tsdept"));
        	nextdept= pd_s.getString("tsdept");
        	if(pd_s.getString("tsdept").equals("350101")){
        		pd.put("ROLE_NAME", "工单专员");
        	}else{
        		pd.put("ROLE_NAME", "部门处理人员");
        	}
			List<PageData> roleList=userService.getUserByRoleName(pd);
			
			for(PageData pd_gd:roleList){
				if(userstr!=""){
					userstr=userstr+",";
				}
				userstr=userstr+pd_gd.getString("USERNAME");
				
			}
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("单部门工单处理")){
        	String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");

        	map.put("单部门处理人",userid);		//审批结果
        	map.put(min+"单部门处理内容",OPINION);		//审批结果
        	pd.put("tsdept", tsdept);
        	nextdept=tsdept;
        	
        	
        	if(!"".equals(ksoffice)){
        		List<PageData> roleList=new ArrayList();
        		//保存部门处理进入下一步
        		pd.put("dept", tsdept);
        		pd.put("office", ksoffice);
        		pd.put("kstime", kstime);
        		pd.put("remark", OPINION);
				pd.put("czman", userid);
        		ruprocdefService.saveOffices(pd);
        		
            	map.put("officeDeal",ksoffice);
        		map.put("DBMDJ","sendOffice");
        		
        	}else{
	        	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")||!cfbm.equals("")||tslevel.equals("203e2311eaa54b6298a1e5a87018a5b2")){	
	        		
	        		List<PageData> roleList=new ArrayList();
	        		if(iszx.equals("1")||iszx.equals("2")||source.equals("1")){ //如果是投诉坐席的紧急就指定到投诉坐席审批
	        			pd.put("tsdept", "");
	        			pd.put("ROLE_NAME", "投诉坐席");
	        		}else{
	        			nextdept="350101";
	        			pd.put("ROLE_NAME", "工单专员");
	            		pd.put("tsdept", "350101");
	        		}
	        		roleList=userService.getUserByRoleName(pd);
	    			
	    			for(PageData pd_gd:roleList){
	    				if(userstr!=""){
	    					userstr=userstr+",";
	    				}
	    				userstr=userstr+pd_gd.getString("USERNAME");
	    			}
	            	map.put("GDZY",userstr);
	        		map.put("DBMDJ","low");
				}else{		
					pd.put("ROLE_NAME", "部门领导");
					List<PageData> roleList=userService.getUserByRoleName(pd);
					
					for(PageData pd_gd:roleList){
						if(userstr!=""){
							userstr=userstr+",";
						}
						userstr=userstr+pd_gd.getString("USERNAME");
						
					}
					map.put("LEADER",userstr);		//审批结果
					map.put("DBMDJ","high");
				}
        	}	
        	//String tkid =ruprocdefService.getTaskID(pd.getString("PROC_INST_ID_"));
			//setAssignee(tkid,pd_s.getString("tsdept"));	
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("科室处理")){
        	//bmclUser
        	//pd_s.put("dept",dwbm);
        	pd_s.put("office",dwbm);
			pd_s.put("proc_id",PROC_INST_ID_);
			pd_s.put("iscl", "1");
			pd_s.put("isdel", "0");
			pd_s.put("remark", OPINION);
			PageData pd_offices =ruprocdefService.getOffices(pd_s);
			
			PageData pd_deptdeal =ruprocdefService.getDealByOffice(pd_s);
			if(pd_deptdeal==null||pd_deptdeal.get("id")==null){			
        		pd_s.put("czman", userid);
        		pd_s.put("officeid", pd_offices.get("id"));
        		pd_s.put("dept",dwbm.substring(0, 6) );
				ruprocdefService.saveDealOffice(pd_s);
				tsdept=pd_offices.getString("office")==null?"":pd_offices.getString("office");
	        	String[] dept=tsdept.split(",");
	        	int num=0;
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i].substring(0, 6));
	        		pd_s.put("office", dept[i]);
					pd_s.put("proc_id", PROC_INST_ID_);
					pd_s.put("iscl", "1");
					pd_s.put("isdel", "0");
					PageData pd_deptdeal_s =ruprocdefService.getDealByOffice(pd_s);
					if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
						num=num+1;
						String remark=pd_deptdeal_s.getString("remark");
						String[] arr=remark.split(",");
						if(arr.length==3){
							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
						}else{
							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
						}
						map.put(min+"单部门科室处理"+num,OPINION);		//审批结果
					}
	        	}
	        	//所有科室处理后
	        	if(dept.length>=1&&dept.length==num){
	        		//驳回
	        		String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");

	        		map.put("bmclUser",dwbm.substring(0,6));
	            	map.put("DBMKSCL","officeDeal");
	            	//map.put(min+"部门领导-审批内容",OPINION);		//审批结果
	            	taskService.complete(taskId, map); //设置会签
	        	}
			}
			pd.put("type", "3");
        }else if (taskname.equals("部门处理人员处理")){
        	
        	
        	if(msg.equals("yes")){ //批准通过
	        	String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
	        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
	
	        	map.put("单部门处理人",userid);		//审批结果
	        	map.put(min+"单部门处理内容",OPINION);		//审批结果
	        	pd.put("tsdept", tsdept);
	        	nextdept=tsdept;
	        	
	        	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")||!cfbm.equals("")||tslevel.equals("203e2311eaa54b6298a1e5a87018a5b2")){	
	        		
	        		List<PageData> roleList=new ArrayList();
	        		if(iszx.equals("1")||iszx.equals("2")||source.equals("1")){ //如果是投诉坐席的紧急就指定到投诉坐席审批
	        			pd.put("tsdept", "");
	        			pd.put("ROLE_NAME", "投诉坐席");
	        		}else{
	        			nextdept="350101";
	        			pd.put("ROLE_NAME", "工单专员");
	            		pd.put("tsdept", "350101");
	        		}
	        		roleList=userService.getUserByRoleName(pd);
	    			
	    			for(PageData pd_gd:roleList){
	    				if(userstr!=""){
	    					userstr=userstr+",";
	    				}
	    				userstr=userstr+pd_gd.getString("USERNAME");
	    			}
	            	map.put("GDZY",userstr);
	        		map.put("DBMSH","officeLow");
				}else{		
					pd.put("ROLE_NAME", "部门领导");
					List<PageData> roleList=userService.getUserByRoleName(pd);
					
					for(PageData pd_gd:roleList){
						if(userstr!=""){
							userstr=userstr+",";
						}
						userstr=userstr+pd_gd.getString("USERNAME");
						
					}
					//${DBMSH=='officeHigh'}
					map.put("LEADER",userstr);		//审批结果
					map.put("DBMSH","officeHigh");
				}
        	}else{
        		pd_s.put("dept",dwbm);
    			pd_s.put("proc_id",PROC_INST_ID_);
    			pd_s.put("isdel", "0");
    			PageData pd_offices =ruprocdefService.getOffices(pd_s);
    			PageData pdDeal =new PageData();
    			//删除已处理的信息
    			if(pd_offices!=null&&pd_offices.get("id")!=null){
    				pdDeal.put("officeid", pd_offices.get("id"));
    				ruprocdefService.delDealOffice(pdDeal);
    				ruprocdefService.delOffices(pd_offices);
    				
    				//保存部门处理进入下一步
    				pd_s.put("remark", OPINION);
    				pd_s.put("czman", userid);
            		pd.put("dept", dwbm);
            		pd.put("office", ksoffice);
            		pd.put("officeid", pd_offices.get("id"));
            		pd.put("kstime", kstime);
            		ruprocdefService.saveOffices(pd);
    			}
    			map.put("部门处理人员",userid);		//审批结果
            	map.put(min+"部门处理人员处理内容",OPINION);		//审批结果
            	map.put("officeDeal",ksoffice);
        		map.put("DBMSH","officeChoice");
        	}
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("部门领导审批")){
        	if(msg.equals("yes")){ //批准
	        	pd_s.put("dept",dwbm);
				pd_s.put("proc_id",PROC_INST_ID_);
				pd_s.put("iscl", "2");
				pd_s.put("isdel", "0");
				pd_s.put("remark", OPINION);
				PageData pd_deptdeal =ruprocdefService.getDealByDept(pd_s);
				if(pd_deptdeal==null||pd_deptdeal.get("id")==null){				
            		map.put("MSG","yes");
            		pd_s.put("czman", userid);
    				ruprocdefService.saveDealDept(pd_s);
    				tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
    	        	String[] dept=tsdept.split(",");
    	        	int num=0;
    	        	for(int i=0;i<dept.length;i++){
    	        		pd_s.put("dept", dept[i]);
    					pd_s.put("proc_id", PROC_INST_ID_);
    					pd_s.put("isdeal", "2");
    					pd_s.put("isdel", "0");
    					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(pd_s);
    					if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
    						num=num+1;
    						String remark=pd_deptdeal_s.getString("remark");
    						String[] arr=remark.split(",");
    						if(arr.length==3){
    							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
    						}else{
    							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
    						}
    						map.put(min+"多部门领导审核"+num,OPINION);		//审批结果
    					}
    	        	}
    	        	
    	        	if(dept.length>=2&&dept.length==num){
    	        		//驳回
    	        		String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");

    	        		tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
    	            	pd.put("tsdept", "350101");
    	            	nextdept="350101";
    	            	pd.put("ROLE_NAME", "工单专员");
    	    			List<PageData> roleList=userService.getUserByRoleName(pd);
    	    			
    	    			for(PageData pd_gd:roleList){
    	    				if(userstr!=""){
    	    					userstr=userstr+",";
    	    				}
    	    				userstr=userstr+pd_gd.getString("USERNAME");
    	    				
    	    			}
    	            	map.put("GDZY",userstr);
    	            	map.put("部门领导姓名",userid);		//审批结果
    	            	//map.put(min+"部门领导-审批内容",OPINION);		//审批结果
    	            	taskService.complete(taskId, map); //设置会签
    	            	pd.put("type", "3");

    	        	}
    			}
			}else{	
	        	String[] dept=tsdept.split(",");
	        	//for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dwbm);
					pd_s.put("proc_id", PROC_INST_ID_);
					pd_s.put("iscl", "1");
					ruprocdefService.delDealDept(pd_s);
					pd_s.put("iscl", "2");
					ruprocdefService.delDealDept(pd_s);
	        	//}
				map.put("MSG","no");
				if(pd_s!=null&&pd_s.getString("tsdept")!=null&&!pd_s.getString("tsdept").equals(pd_s.getString("newtsdept"))){
					map.put("depts",newtsdept);
					nextdept=newtsdept;
					workorderService.editTsdept(pd_s);
					workorderService.saveTsdept(pd_s);
				}else{
					map.put("depts",pd_s.getString("tsdept"));
					nextdept=pd_s.getString("tsdept");
				}
				map.put("部门领导姓名",userid);		//审批结果
	        	map.put(min+"部门领导-审批内容",OPINION);		//审批结果
	        	taskService.complete(taskId, map); //设置会签
	        	
			}	
        	pd.put("type", "3");	
        	
        }else if (taskname.equals("单部门领导审批")){
        	
        	
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	
        	nextdept="350101";
        	pd.put("ROLE_NAME", "工单专员");
        	pd.put("tsdept", "350101");
			List<PageData> roleList=userService.getUserByRoleName(pd);
			
			for(PageData pd_gd:roleList){
				if(userstr!=""){
					userstr=userstr+",";
				}
				userstr=userstr+pd_gd.getString("USERNAME");
				
			}
			map.put("GDZY",userstr);
        	if(msg.equals("yes")){								//批准
        		map.put("DBMMSG","yes");
			}else{	
	        	String[] dept=tsdept.split(",");
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i]);
					pd_s.put("proc_id",PROC_INST_ID_);
					pd_s.put("iscl", "1");
					ruprocdefService.delDealDept(pd_s);
	        	}
	        	
	        	String[] newdept=newtsdept.split(",");
	        	if(newdept.length>=2){
	        		map.put("DBMMSG","no-duble");
	        	}else{
	        		map.put("DBMMSG","no");
	        	}
	        	if(pd_s!=null&&pd_s.getString("tsdept")!=null&&pd_s.getString("newtsdept")!=null&&!pd_s.getString("tsdept").equals(pd_s.getString("newtsdept"))){
					map.put("depts",newtsdept);
					nextdept=newtsdept;
					workorderService.editTsdept(pd_s);
					workorderService.saveTsdept(pd_s);
				}else{
					map.put("depts",pd_s.getString("tsdept"));
					nextdept=pd_s.getString("tsdept");
				}
	        	
	        	pd.put("tsdept", pd_s.getString("tsdept"));
	        	pd.put("ROLE_NAME", "部门处理人员");
				roleList=userService.getUserByRoleName(pd);
				for(PageData pd_gd:roleList){
					if(userstr!=""){
						userstr=userstr+",";
					}
					userstr=userstr+pd_gd.getString("USERNAME");
				}
	        	
				//map.put("depts","350101");
			}	
        	map.put("单部门-部门领导姓名",userid);		//审批结果
        	map.put(min+"单部门-部门领导-审批内容",OPINION);		//审批结果
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("工单专员审批")){
        	
        	
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	
        	String[] dept=tsdept.split(",");
        	if(msg.equals("yes")){
        		//批准
        		map.put("RESULT","yes");
        		pd.put("uid", pd_s.getString("uid"));
            	pd.put("dealman",  userid);
    			pd.put("cljd",  dwbm);
    			pd.put("type", "4");
    			workorderService.editCL(pd);
    			//taskService.complete(taskId, map); //设置会签
    			
    			boo=true;
			}else{		
				if(dept.length==1){
        			map.put("RESULT","no-single"); //单部门审批不通过
        		}else{
        			map.put("RESULT","no-mul"); //多部门审批不通过
        			for(int i=0;i<dept.length;i++){
    	        		pd_s.put("dept", dept[i]);
    					pd_s.put("proc_id", PROC_INST_ID_);
    					pd_s.put("iscl", "1");
    					ruprocdefService.delDealDept(pd_s);
    					pd_s.put("iscl", "2");
    					ruprocdefService.delDealDept(pd_s);
    	        	}
        			
        		}
				if(pd_s!=null&&pd_s.getString("tsdept")!=null&&!pd_s.getString("tsdept").equals(pd_s.getString("newtsdept"))){
					map.put("depts",newtsdept);
					nextdept=newtsdept;
					pd_s.put("msg", msg);
					workorderService.editTsdept(pd_s);
					workorderService.saveTsdept(pd_s);
				}else{
					map.put("depts",pd_s.getString("tsdept"));
					nextdept=pd_s.getString("tsdept");
				}
				
				
				pd.put("tsdept", pd_s.getString("tsdept"));
	        	pd.put("ROLE_NAME", "部门处理人员");
	        	List<PageData> roleList=userService.getUserByRoleName(pd);
				for(PageData pd_gd:roleList){
					if(userstr!=""){
						userstr=userstr+",";
					}
					userstr=userstr+pd_gd.getString("USERNAME");
				}
			}	
        	map.put("工单专员",userid);		//审批结果
        	map.put(min+"工单专员审批内容",OPINION);		//审批结果
        	taskService.complete(taskId, map); 
        	pd.put("type", "3");
        }else if (taskname.equals("多部门工单处理")&&dwbm.length()==8&&msg.equals("")){
        	pd_s.put("office",dwbm);
			pd_s.put("proc_id",PROC_INST_ID_);
			pd_s.put("iscl", "1");
			pd_s.put("isdel", "0");
			pd_s.put("remark", OPINION);
			PageData pd_offices =ruprocdefService.getOffices(pd_s);
			
			PageData pd_deptdeal =ruprocdefService.getDealByOffice(pd_s);
			pd.put("name_", "多部门工单处理");
			PageData pdTask =ruprocdefService.getActRuTask(pd);
			String assignee_="";
			
    		if(pdTask!=null){
    			assignee_=pdTask.getString("ASSIGNEE_");
    			assignee_=ruprocdefService.removeOne(assignee_, dwbm);
    			
    		}
			//处理历史信息
    		pd.put("name_", "多部门工单处理");
    		PageData pdTaskInst =ruprocdefService.getActRuTaskInst(pd);
    		if(pdTaskInst!=null){
    			String assigneeInst_=pdTaskInst.getString("ASSIGNEE_");
    			if(assigneeInst_!=null&&assigneeInst_.indexOf(dwbm)<0){
    				//assignee_=assigneeInst_+","+dwbm;
        			pd.put("assignee_", assigneeInst_+","+dwbm);
        			ruprocdefService.updateActRuTaskInst(pd);
    			}
    		}
    		
			if(pd_deptdeal==null||pd_deptdeal.get("id")==null){	
				
				
        		pd_s.put("czman", userid);
        		pd_s.put("officeid", pd_offices.get("id"));
        		pd_s.put("dept",dwbm.substring(0, 6) );
				ruprocdefService.saveDealOffice(pd_s);
				tsdept=pd_offices.getString("office")==null?"":pd_offices.getString("office");
	        	String[] dept=tsdept.split(",");
	        	int num=0;
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i].substring(0, 6));
	        		pd_s.put("office", dept[i]);
					pd_s.put("proc_id", PROC_INST_ID_);
					pd_s.put("iscl", "1");
					pd_s.put("isdel", "0");
					PageData pd_deptdeal_s =ruprocdefService.getDealByOffice(pd_s);
					if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
						num=num+1;
						String remark=pd_deptdeal_s.getString("remark");
						String[] arr=remark.split(",");
						if(arr.length==3){
							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
						}else{
							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
						}
						map.put(min+"单部门科室处理"+num,OPINION);		//审批结果
					}
	        	}
	        	//所有科室处理后
	        	if(dept.length>=1&&dept.length==num){
	        		//驳回
	        		assignee_=assignee_+","+dwbm.substring(0, 6);
	        	}
	        	
    			pd.put("assignee_", assignee_);
    			ruprocdefService.updateActRuTask(pd);
			}
			pd.put("type", "3");
        }else if (taskname.equals("多部门工单处理")&&dwbm.length()==6&&!msg.equals("")){
        	if(msg.equals("yes")){ //批准通过
	        	String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
	        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
	        	pd_s.put("dept",dwbm);
				pd_s.put("proc_id",PROC_INST_ID_);
				pd_s.put("iscl", "1");
				pd_s.put("isdel", "0");
				pd_s.put("remark", OPINION);
				PageData pd_deptdeal =ruprocdefService.getDealByDept(pd_s);
				if(pd_deptdeal==null||pd_deptdeal.get("id")==null){
					
					pd_s.put("czman", userid);
					ruprocdefService.saveDealDept(pd_s);
					
					pd.put("name_", "多部门工单处理");
	        		PageData pdTask =ruprocdefService.getActRuTask(pd);
	        		if(pdTask!=null){
	        			String assignee_=pdTask.getString("ASSIGNEE_");
	        			assignee_=ruprocdefService.removeOne(assignee_, dwbm);
	        			//assignee_=assignee_+","+ksoffice;
	        			pd.put("assignee_", assignee_);
	        			ruprocdefService.updateActRuTask(pd);
	        		}
					
					tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
		        	String[] dept=tsdept.split(",");
		        	int num=0;
		        	for(int i=0;i<dept.length;i++){
		        		pd_s.put("dept", dept[i]);
						pd_s.put("proc_id", PROC_INST_ID_);
						pd_s.put("isdeal", "1");
						pd_s.put("isdel", "0");
						PageData pd_deptdeal_s =ruprocdefService.getDealByDept(pd_s);
						if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
							num=num+1;
							String remark=pd_deptdeal_s.getString("remark");
							String[] arr=remark.split(",");
							if(arr.length==3){
								OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
							}else{
								OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
							}
							map.put(min+"多部门处理内容"+num,OPINION);		//审批结果
						}
		        	}
		        	
		        	if(dept.length>=2&&dept.length==num){
		            	map.put("多部门处理",tsdept);		//审批结果
		            	pd.put("tsdept", tsdept);
		            	
		            	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")||!cfbm.equals("")||tslevel.equals("203e2311eaa54b6298a1e5a87018a5b2")){	
		            		//批准
		            		List<PageData> roleList=new ArrayList();
		            		if(iszx.equals("1")||iszx.equals("2")||source.equals("1")){ //如果是投诉坐席的紧急就指定到投诉坐席审批
		            			pd.put("tsdept", "");
		            			pd.put("ROLE_NAME", "投诉坐席");
		            		}else{
		            			nextdept="350101";
		            			pd.put("ROLE_NAME", "工单专员");
		                		pd.put("tsdept", "350101");
		            		}
		            		roleList=userService.getUserByRoleName(pd);
		        			for(PageData pd_gd:roleList){
		        				if(userstr!=""){
		        					userstr=userstr+",";
		        				}
		        				userstr=userstr+pd_gd.getString("USERNAME");
		        			}
		                	map.put("GDZY",userstr);
		                	map.put("DJ","low");
		    			}else{	
		    				pd.put("ROLE_NAME", "部门领导");
			    			List<PageData> roleList=userService.getUserByRoleName(pd);
			    			
			    			for(PageData pd_gd:roleList){
			    				if(userstr!=""){
			    					userstr=userstr+",";
			    				}
			    				userstr=userstr+pd_gd.getString("USERNAME");
			    			}
			    			map.put("LEADER",userstr);		//审批结果果
			    			map.put("DJ","high");
		    			}	
		            	taskService.complete(taskId, map); //设置会签
		        	}
		        	
				}
				pd.put("type", "3");
        	}else{
        		pd_s.put("dept",dwbm);
    			pd_s.put("proc_id",PROC_INST_ID_);
    			pd_s.put("isdel", "0");
    			PageData pd_offices =ruprocdefService.getOffices(pd_s);
    			PageData pdDeal =new PageData();
    			//删除已处理的信息
    			if(pd_offices!=null&&pd_offices.get("id")!=null){
    				pdDeal.put("officeid", pd_offices.get("id"));
    				ruprocdefService.delDealOffice(pdDeal);
    				ruprocdefService.delOffices(pd_offices);
    				
    				//保存部门处理进入下一步
    				pd.put("remark", OPINION);
    				pd.put("czman", userid);
            		pd.put("dept", dwbm);
            		pd.put("office", ksoffice);
            		pd.put("officeid", pd_offices.get("id"));
            		pd.put("kstime", kstime);
            		ruprocdefService.saveOffices(pd);
    			}
    			pd.put("name_", "多部门工单处理");
        		PageData pdTask =ruprocdefService.getActRuTask(pd);
        		if(pdTask!=null){
        			String assignee_=pdTask.getString("ASSIGNEE_");
        			assignee_=ruprocdefService.removeOne(assignee_, dwbm);
        			assignee_=assignee_+","+ksoffice;
        			pd.put("assignee_", assignee_);
        			ruprocdefService.updateActRuTask(pd);
        		}
        	}
        	
        	pd.put("type", "3");
        }else if (taskname.equals("多部门工单处理")&&dwbm.length()==6&&msg.equals("")){
        	if(!"".equals(ksoffice)){
        		/*
        		 * 多部门可能存在部门还未处理就进入下一步
        		 */
        		pd.put("name_", "多部门工单处理");
        		PageData pdTask =ruprocdefService.getActRuTask(pd);
        		if(pdTask!=null){
        			String assignee_=pdTask.getString("ASSIGNEE_");
        			assignee_=ruprocdefService.removeOne(assignee_, dwbm);
        			assignee_=assignee_+","+ksoffice;
        			pd.put("assignee_", assignee_);
        			ruprocdefService.updateActRuTask(pd);
        		}
        		pd.put("dept", dwbm);
        		pd.put("office", ksoffice);
        		pd.put("remark", OPINION);
				pd.put("czman", userid);
				pd.put("kstime", kstime);
        		ruprocdefService.saveOffices(pd);
            	//map.put("officeDeals",ksoffice);
        		//map.put("DJ","sendOffice");
        		//taskService.complete(taskId, map); //设置会签
        		pd.put("type", "3");
        	}else{
        		
				pd_s.put("dept",dwbm);
				pd_s.put("proc_id",PROC_INST_ID_);
				pd_s.put("iscl", "1");
				pd_s.put("isdel", "0");
	
				pd_s.put("remark", OPINION);
				PageData pd_deptdeal =ruprocdefService.getDealByDept(pd_s);
				if(pd_deptdeal==null||pd_deptdeal.get("id")==null){
					pd_s.put("czman", userid);
					ruprocdefService.saveDealDept(pd_s);
					
					//去除移处理的编码
					/*PageData pdTask =ruprocdefService.getActRuTask(pd);
	        		if(pdTask!=null){
	        			String assignee_=pdTask.getString("ASSIGNEE_");
	        			assignee_=ruprocdefService.removeOne(assignee_,dwbm);
	        			pd.put("assignee_", assignee_);
	        			ruprocdefService.updateActRuTask(pd);
	        		}*/
					
					tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
		        	String[] dept=tsdept.split(",");
		        	int num=0;
		        	for(int i=0;i<dept.length;i++){
		        		pd_s.put("dept", dept[i]);
						pd_s.put("proc_id", PROC_INST_ID_);
						pd_s.put("isdeal", "1");
						pd_s.put("isdel", "0");
						PageData pd_deptdeal_s =ruprocdefService.getDealByDept(pd_s);
						if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
							num=num+1;
							String remark=pd_deptdeal_s.getString("remark");
							String[] arr=remark.split(",");
							if(arr.length==3){
								OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
							}else{
								OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
							}
							map.put(min+"多部门处理内容"+num,OPINION);		//审批结果
						}
		        	}
		        	
		        	if(dept.length>=2&&dept.length==num){
		        		//驳回
		        		String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
		            	
		            	map.put("多部门处理",tsdept);		//审批结果
		            	
		            	pd.put("tsdept", tsdept);
		            	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")||!cfbm.equals("")||tslevel.equals("203e2311eaa54b6298a1e5a87018a5b2")){	
		            		//批准
		            		List<PageData> roleList=new ArrayList();
		            		if(iszx.equals("1")||iszx.equals("2")||source.equals("1")){ //如果是投诉坐席的紧急就指定到投诉坐席审批
		            			pd.put("tsdept", "");
		            			pd.put("ROLE_NAME", "投诉坐席");
		            		}else{
		            			nextdept="350101";
		            			pd.put("ROLE_NAME", "工单专员");
		                		pd.put("tsdept", "350101");
		            		}
		            		roleList=userService.getUserByRoleName(pd);
		        			for(PageData pd_gd:roleList){
		        				if(userstr!=""){
		        					userstr=userstr+",";
		        				}
		        				userstr=userstr+pd_gd.getString("USERNAME");
		        			}
		                	map.put("GDZY",userstr);
		            		map.put("DJ","low");
		    			}else{	
		    				pd.put("ROLE_NAME", "部门领导");
			    			List<PageData> roleList=userService.getUserByRoleName(pd);
			    			
			    			for(PageData pd_gd:roleList){
			    				if(userstr!=""){
			    					userstr=userstr+",";
			    				}
			    				userstr=userstr+pd_gd.getString("USERNAME");
			    			}
			    			map.put("LEADER",userstr);		//审批结果果
		    				map.put("DJ","high");
		    			}	
		            	taskService.complete(taskId, map); //设置会签
		        	}
					
		        	pd.put("type", "3");
				}else{
					boo=true;
				}
        	}
		}	
        if(!boo){
        	pd.put("uid", pd_s.getString("uid"));
         	pd.put("dealman",  userid);
     		pd.put("cljd", nextdept);
     		pd.put("cljduser", userstr);
     		pd.put("ZXID", userid);
     		workorderService.editCLByuid(pd);
     		min4=System.currentTimeMillis();
    		//System.out.println("修改工单理流程名称："+(min4-min3)+"毫秒");
        }
        String msgstr="";
        if(msg.equals("yes")){msgstr="审核通过";}
        if(msg.equals("no")){msgstr="审核不通过";}
        String operate="{处理节点:"+taskname+",工单流程id:"+PROC_INST_ID_+",处理人:"+userid+",处理意见:"+clyj+",是否通过:"+msgstr+"}";
        pd.put("ID", UuidUtil.get32UUID());
        pd.put("MAPPERNAME","RuprocdefService.deal");
        pd.put("OPERATEMAN", userid);// 操作者
		pd.put("systype","1");
        pd.put("OPERATEDATE", new Date());// 时间
        pd.put("OPERATESTR", operate);// 请求参数
	    pd.put("TYPE", "1");// 正常结束
	    pd.put("IP", IPUtil.getLocalIpv4Address());// ip
	    operatelogService.save(pd);
        
        String[] arry=userstr.split(",");
        RuTaskController ruTaskController=new RuTaskController();
        PageData pd_workorder = workorderService.findById(pd_s);
		if(pd_workorder!=null){
			//投诉、表扬、建议），等级（高级、紧急、普通）信息；
			//sendcont="您有一条"+pd_workorder.getString("tslevelname")+"的投诉工单，请速处理";
			sendcont="您有一条"+pd_workorder.getString("tslevelname")+"的投诉工单，请速处理";
			
		}
        for(int i=0;i<arry.length;i++){
        	if(arry[i]!=null&&!arry[i].equals("")){
        		//sendcont=URLEncoder.encode("您有一条待处理工单，请<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe1de579339958660&redirect_uri=http://webchat.ada-robotics.com:8090/ccweb/appWeixin/getUserid&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\">登录操作！</a>","utf-8");
				ruTaskController.sendMsg(arry[i], sendcont);
			}
        }
        
        min5=System.currentTimeMillis();
		//System.out.println("从处理理时间工单时间："+(min5-min1)+"毫秒");
		return "";
	}
	
	
	/*
	 * dealWeb
	 * 座席端派发
	 */
	@Transactional
	public String dealWeb(String id,String PROC_INST_ID_,String ID_,String dwbm,String userid,String cfbm,String doaction,String msg,String tsdept,String OPINION) throws Exception{
		return "";
	}
	
	public PageData getActRuTask(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.getActRuTask", pd);
	}
	
	public void updateActRuTask(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.updateActRuTask", pd);
	}
	
	public PageData getActRuTaskInst(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.getActRuTaskInst", pd);
	}
	
	public void updateActRuTaskInst(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.updateActRuTaskInst", pd);
	}
	
	
	/*
     * @ClassName Test
     * @Desc TODO   移除指定用户 ID
     * @Date 2019/8/31 14:58
     * @Version 1.0
     */
    public String removeOne(String userIds, String dwbm) {
        // 返回结果
        String result = "";
        // 判断是否存在。如果存在，移除指定用户 ID；如果不存在，则直接返回空
        if(userIds.indexOf(",") != -1) {
            // 拆分成数组
            String[] userIdArray = userIds.split(",");
            // 数组转集合
            List<String> userIdList = new ArrayList<String>(Arrays.asList(userIdArray));
            // 移除指定用户 ID
            userIdList.remove(dwbm.toString());
            // 把剩下的用户 ID 再拼接起来
            result = StringUtils.join(userIdList, ",");
        }
        // 返回
        return result;
    }
    
    
    
 
	
}

