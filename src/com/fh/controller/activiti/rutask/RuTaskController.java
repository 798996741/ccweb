package com.fh.controller.activiti.rutask;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fh.controller.activiti.AcBusinessController;


import net.sf.json.JSONArray;

import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.http.client.ClientProtocolException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.HttpClientUtil;
import com.fh.util.Httpsend;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.fh.util.weixin.Weixin;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.activiti.ruprocdef.impl.RuprocdefService;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.fhsms.FhsmsManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.service.system.user.impl.UserService;
import com.xxgl.controller.AsynTask;
import com.xxgl.service.impl.WorkorderService;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.utils.PageUtil;
import com.xxgl.utils.ResponseUtils;
import com.xxgl.utils.SpringContextHolder;
import com.yulun.utils.Httpclient;

/**
 * 说明：待办任务
 */
@Controller
@RequestMapping(value="/rutask")
public class RuTaskController extends AcBusinessController {
	
	String menuUrl = "rutask/list.do"; 	//菜单地址(权限用)
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	@Resource(name="fhsmsService")
	private FhsmsManager fhsmsService;
	@Resource(name="workorderService")
	private WorkorderManager workorderService1;

	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	@Autowired
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
	@Resource(name="userService")
	private UserManager userService1;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	
	
	UserService userService = (UserService) SpringContextHolder.getSpringBean("userService");  
	WorkorderService workorderService = (WorkorderService) SpringContextHolder.getSpringBean("workorderService");  

	/**待办任务列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表待办任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		User user=Jurisdiction.getLoginUser();
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		if(null != pd.getString("codes") ){
			pd.put("code", pd.getString("codes"));
		}	
		if(null != pd.getString("tssources") ){
			pd.put("tssource", pd.getString("tssources"));
		}	
		if(null != pd.getString("tsmans") ){
			pd.put("tsman", pd.getString("tsmans"));
		}	
		if(null != pd.getString("tsdepts") ){
			pd.put("tsdept", pd.getString("tsdepts"));
		}	
		
		if(null != pd.getString("types") ){
			pd.put("type", pd.getString("types"));
		}	
		
		if(null != pd.getString("tstypes")&&"" != pd.getString("tstypes") ){
			pd.put("tstype", pd.getString("tstypes"));
		}
		if(null != pd.getString("bigtstypes")&&"" != pd.getString("bigtstypes") ){
			pd.put("bigtstype", pd.getString("bigtstypes"));
		}
			
		
		if(null != pd.getString("endreasons") ){
			pd.put("endreason", pd.getString("endreasons"));
		}	
		
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");			//开始时间
		String lastEnd = pd.getString("lastEnd");				//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		}
		//pd.put("USERNAME", Jurisdiction.getUsername()); 		//查询当前用户的任务(用户名查询)
		//pd.put("RNUMBERS", Jurisdiction.getRnumbers()); 		//查询当前用户的任务(角色编码查询)
		//pd.put("DWBM", user.getDWBM()); 
		
		String role_id=user.getROLE_ID();
		PageData pd_js = new PageData();
		pd_js.put("ROLE_ID", role_id);
		PageData role=roleService.findObjectById(pd_js);
		
		if(role!=null&&(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("部门处理人员")))){
			pd.put("DWBM", user.getDWBM()); 
		}else if(role!=null&&(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("科室处理人员")))){
			pd.put("DWBM_OFFICE", user.getDWBM()); 
			pd.put("DWBM_OFFICE1", user.getDWBM()); 
		}else{
			pd.put("USERNAME", Jurisdiction.getUsername()); 		//查询当前用户的任务(用户名查询)
		}
		
		
		if((user.getDWBM().equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))){
			pd.put("DWBM", user.getDWBM()); 
			pd.put("USERNAME", Jurisdiction.getUsername());
			pd.put("isgdzy","1");
		}else{
			pd.put("isgdzy","0");
		}
		if(role.getString("ROLE_NAME").equals("部门领导")){
			pd.put("DWBM1", user.getDWBM()); 
		}
		if(role.getString("ROLE_NAME").equals("部门处理人员")){
			pd.put("DWBM2", user.getDWBM()); 
		}
		pd.put("isreceive","2");
		page.setPd(pd);
		
		boolean boo=roleService.isRole(role_id);
		////System.out.println(boo+"name");
		List<PageData>	varList =new ArrayList();
		List<PageData>	alllist =new ArrayList();
		if(boo){
			varList = ruprocdefService.list(page);	//列出Rutask列表
			for(PageData pddoc:varList){  
				
				if((user.getDWBM().equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))){
					if(pddoc!=null&&pddoc.getString("NAME_").equals("多部门工单处理")){
						PageData db_deal=new PageData();
						db_deal.put("dept", "350101");
						db_deal.put("proc_id", pddoc.getString("proc_id"));
						db_deal.put("iscl", "1");
						db_deal.put("isdel", "0");
    					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(db_deal);
    					if(pd_deptdeal_s==null){
							alllist.add(pddoc);
    					}
    					pddoc.put("ISRECEIVE", pddoc.getString("ISRECEIVES")==null?"0":pddoc.getString("ISRECEIVES"));
    					
					}else{
						alllist.add(pddoc);
					}
				}else{
					alllist.add(pddoc);
				}
			}
		}
		Collections.sort(alllist,new Comparator<PageData>(){
			
			@Override
			public int compare(PageData o1, PageData o2) {
				// TODO Auto-generated method stub
				////System.out.println(o2);
				////System.out.println(o1);
				if(Integer.parseInt(String.valueOf(o2.get("iscs")))>Integer.parseInt(String.valueOf(o1.get("iscs")))){
					return 1;//降序
				}
				return -1;
				
			}
		});
		
		mv.setViewName("activiti/rutask/rutask_list");
		
		
		
		
	    if(user!=null&&user.getDWBM()!=null){
			String dwbm= user.getDWBM();
			if(dwbm.equals("350101")){
				dwbm="3501";
			}
			pd.put("dwbm", dwbm);
			
		}
	    pd.put("parentId", "9ed978b4a4674adb9c08f45d2e2d9813");
		List<PageData> tssourceList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tssourceList", tssourceList);
	    pd.put("AREA_LEVEL", "3");
	    List<PageData> tsbmList=areamanageService.listAll(pd);
	    mv.addObject("tsdeptList", tsbmList);
	    pd.put("parentId", "1b303f7026934f68a6bd1ea01433db19");
	    List<PageData> tstypeList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tstypeLists", tstypeList);
		mv.addObject("varList", alllist);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**待办任务列表(只显示5条,用于后台顶部小铃铛左边显示)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getList")
	@ResponseBody
	public Object getList(Page page) throws Exception{
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd.put("USERNAME", Jurisdiction.getUsername()); 		//查询当前用户的任务(用户名查询)
		pd.put("RNUMBERS", Jurisdiction.getRnumbers()); 		//查询当前用户的任务(角色编码查询)
		page.setPd(pd);
		page.setShowCount(5);
		List<PageData>	varList = ruprocdefService.list(page);	//列出Rutask列表
		List<PageData> pdList = new ArrayList<PageData>();
		for(int i=0;i<varList.size();i++){
			PageData tpd = new PageData();
			tpd.put("NAME_", varList.get(i).getString("NAME_"));	//任务名称
			tpd.put("PNAME_", varList.get(i).getString("PNAME_"));	//流程名称
			pdList.add(tpd);
		}
		map.put("list", pdList);
		map.put("taskCount", page.getTotalResult());
		return AppUtil.returnObject(pd, map);
	}
	
	/**去办理任务页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goHandle")
	public ModelAndView goHandle()throws Exception{
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		User user=Jurisdiction.getLoginUser();
		String dwbm=user.getDWBM();
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("proc_id", pd.getString("PROC_INST_ID_"));
		PageData pd_s =workorderService.findById(pd);
		List<PageData>	varList = ruprocdefService.varList(pd);			//列出流程变量列表
		List<PageData>	hitaskList = ruprocdefService.hiTaskList(pd);	//历史任务节点列表
		
		List<PageData>	clList=new ArrayList();

		PageData pdcl=new PageData();
		PageData pduser=new PageData();
		String[] arrcl=null;
		/*for(int i=0;i<hitaskList.size();i++){							//根据耗时的毫秒数计算天时分秒
			if(null != hitaskList.get(i).get("DURATION_")){
				Long ztime = Long.parseLong(hitaskList.get(i).get("DURATION_").toString());
				Long tian = ztime / (1000*60*60*24);
				Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
				Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
				Long miao = (ztime % (1000*60*60*24))%(1000*60*60)%(1000*60)/1000;
				hitaskList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分"+miao+"秒");
			}
			
		}*/
		String lasttime="";
		String taskId = pd.getString("ID_");	//任务ID
		String taskname="";
		if(taskId!=null){
			Task task=taskService.createTaskQuery() // 创建任务查询
	                .taskId(taskId) // 根据任务id查询
	                .singleResult();
			if(task!=null){
				taskname=task.getName()==null?"":task.getName();
			}
		}
		////System.out.println("taskname:"+taskname);
		
		List<PageData> listTemp=ruprocdefService.getClList(pd, varList, taskname);
		
		String clsx=pd_s.getString("clsx")==null?"0":pd_s.getString("clsx");
		String czdate=String.valueOf(pd_s.get("czdate"));
		int hour=0;
		if(clsx.equals("24H")){
			hour=24;
		}else if(clsx.equals("48H")){
			hour=48;
		}else if(clsx.equals("3D")){
			hour=72;
		}else if(clsx.equals("7D")){
			hour=168;
		}else if(clsx.equals("其他")){
			hour=0;
		}else if(!clsx.equals("0")){
			if(clsx.equals("")){
				clsx="0";
			}
			hour=Integer.parseInt(clsx);
		}
		if(hour>0){
			String xclsj=RuTaskController.addDateMinut(czdate, hour);
			String cltime=RuTaskController.timesBetween(lasttime, xclsj);
			mv.addObject("cltime", cltime);
			mv.addObject("xclsj", xclsj);
		}
		
	    pd.put("bianma", "014");
	    List<PageData> tstypeList=dictionariesService.listAllDictByBM(pd);
	    JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(PageData pd_obj:tstypeList){
			object = new JSONObject();
			object.put("id", pd_obj.get("DICTIONARIES_ID"));
			object.put("pId", pd_obj.getString("PARENT_ID"));
			object.put("name",  pd_obj.getString("NAME"));
			//object.put("name",  pd_obj.get("NAME"));
			objlist.add(object);
		}
	    String json = objlist.toString();
		json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked");
		////System.out.println(json);
		mv.addObject("zTreeNodes", json);
		pd.put("isuse", "1");
		JSONArray arr = JSONArray.fromObject(areamanageService.listAll(pd));
		String json_dept = arr.toString();
		json_dept = json_dept.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
		
		pd.put("isuse", "1");
		pd.put("dwbm", dwbm);
		pd.put("AREA_LEVEL", "4");
		List<PageData> ksList=areamanageService.listAll(pd);
		pd.put("userid", Jurisdiction.getUsername()); //用户id
		pd.put("isdel", "0");
		if(dwbm.length()>=8){
			pd.put("dept", dwbm.substring(0, 6));	
		}else{
			pd.put("dept", dwbm);
		}
		
		PageData  ksoffice=ruprocdefService.getOffices(pd);
		
		if(taskname.equals("多部门工单处理")){
			PageData pd_js = new PageData();
			pd_js.put("ROLE_ID", user.getROLE_ID());
			PageData role=roleService.findObjectById(pd_js);//获取用户角色
			if(role.getString("ROLE_NAME").equals("科室处理人员")){
				taskname="多部门多科室处理";
				if(ksoffice!=null){
					
					String offices=ksoffice.getString("office");
					String kstimes=ksoffice.getString("kstime");
					String[] office=offices.split(",");
					String[] kstime=kstimes.split(",");
					//判断科室是否有剩余时间
					for(int i=0;i<office.length;i++){
						if(office[i].equals(dwbm)){
							if(kstime[i]!=null&&!"none".equals(kstime[i])&&Integer.parseInt(kstime[i])>0&&Integer.parseInt(kstime[i])<=hour){
								String xclsj=RuTaskController.addDateMinut(czdate, Integer.parseInt(kstime[i]));
								String cltime=RuTaskController.timesBetween(lasttime, xclsj);
								mv.addObject("cltime", cltime);
								mv.addObject("xclsj", xclsj);
							}
						}
					}
					
					
				}
				
				
				
			}else if(role.getString("ROLE_NAME").equals("部门处理人员")){
				pd_js.put("dept", dwbm);
				pd_js.put("isdel", "0");
				pd_js.put("proc_id", pd.getString("PROC_INST_ID_"));
				List<PageData>  ksofficeDeal=ruprocdefService.getDealByOfficeDept(pd_js);
				if(ksofficeDeal.size()>0){
					taskname="多部门处理";
				}
				
			}
			
		}
		System.out.println(taskname+"taskname");
		mv.addObject("taskname", taskname);
		mv.addObject("zTreeNodes_dept", json_dept);
		mv.setViewName("activiti/rutask/rutask_handle");
		mv.addObject("varList", varList);
		mv.addObject("ksoffice", ksoffice);
		mv.addObject("ksList", ksList);
		mv.addObject("hitaskList", hitaskList);
		mv.addObject("clList", listTemp);
		mv.addObject("clCount", listTemp.size());
		mv.addObject("pd", pd);
		mv.addObject("pd_s", pd_s);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	

	public static String addDateMinut(String day, int hour){   
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;   
        try {   
            date = format.parse(day);   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
        if (date == null)   
            return "";   
        //System.out.println("front:" + format.format(date)); //显示输入的日期  
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        cal.add(Calendar.HOUR, hour);// 24小时制   
        date = cal.getTime();   
        //System.out.println("after:" + format.format(date));  //显示更新后的日期 
	    cal = null;   
	    return format.format(date);   
 
	}
	
	public static String timesBetween(String sdate,String bdate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		////System.out.println("sdate:"+sdate);
		////System.out.println("bdate:"+bdate);
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long diff = 0;
		boolean boo=true;
		try {
			Date startDate= new Date();
			Date bindDate= df.parse(bdate);
			long stime = startDate.getTime();
			long btime = bindDate.getTime();
			if(stime>=btime){
				diff = stime - btime;
				
			}else{
				boo=false;
				diff = btime - stime;
			}
			day = diff/(24*60*60*1000);
			hour = diff/(60*60*1000) - day*24;
			min = diff/(60*1000) - day*24*60 - hour*60;
			sec = diff/1000-day*24*60*60-hour*60*60-min*60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String hours="",mins="",secs="";
		if(hour<10){
			hours="0"+hour;
		}else{
			hours=hour+"";
		}
		if(min<10){
			mins="0"+min;
		}else{
			mins=min+"";
		}
		if(sec<10){
			secs="0"+sec;
		}else{
			secs=sec+"";
		}
		long hou=day*24+hour;
		if(boo){
			return  "-"+day+"天"+hours+"时"+mins+"分"+secs+"秒"+hou;
		}else{
			return  day+"天"+hours+"时"+mins+"分"+secs+"秒"+hou;
		}
	}
	
	/**办理任务
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/handle")
	@ResponseBody
	public String handle(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject object = new JSONObject();
		logBefore(logger, Jurisdiction.getUsername()+"办理任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		Session session = Jurisdiction.getSession();
		User user=Jurisdiction.getLoginUser();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String taskId ="";
		
		String PROC_INST_ID_=pd.getString("PROC_INST_ID_");
		String ksoffice=pd.getString("office")==null?"":pd.getString("office");
		String kstime=pd.getString("kstime")==null?"":pd.getString("kstime");
		
		String id=pd.getString("id");
		String ID_=pd.getString("ID_")==null?"":pd.getString("ID_");
		String dwbm=user.getDWBM();
		String userid=user.getUSERNAME();
		String cfbm=pd.getString("cfbm")==null?"":pd.getString("cfbm");
		if(cfbm.equals("undefined")){cfbm="";}
		if(ksoffice.equals("undefined")){ksoffice="";}
		
		String doaction=pd.getString("doaction")==null?"":pd.getString("doaction");
		String msg=pd.getString("msg")==null?"":pd.getString("msg");
		String tsdept=pd.getString("tsdept")==null?"":pd.getString("tsdept");
		////System.out.println("tsdept:"+tsdept);
		String OPINION=pd.getString("OPINION")==null?"":pd.getString("OPINION");
		long min1=System.currentTimeMillis();
		
		/*修改接收标记*/
		pd.put("ISRECEIVE", "1");
		pd.put("ID_", ID_);
		pd.put("username", userid);
		ruprocdefService.receive(pd);
			
		AsynTask myThread = new AsynTask();
	    myThread.setDwbm(dwbm);
	    myThread.setKsoffice(ksoffice);
	    myThread.setKstime(kstime);
	    myThread.setPid(PROC_INST_ID_);
	    myThread.setUserid(userid);
	    myThread.setOPINION(OPINION);
	    myThread.setCfbm(cfbm);
	    myThread.setDoaction(doaction);
	    myThread.setId(id);
	    myThread.setID_(ID_);
	    myThread.setMsg(msg);
	    myThread.setPROC_INST_ID_(PROC_INST_ID_);
	    myThread.setTsdept(tsdept);
	    Thread thread = new Thread(myThread);
        thread.start();
		
		/*修改前*/
		//ruprocdefService.deal(id,PROC_INST_ID_, ID_, dwbm, userid, cfbm, doaction, msg, tsdept, OPINION);
		long min5=System.currentTimeMillis();
		//System.out.println("派发工单总时间："+(min5-min1)+"毫秒");
		mv.addObject("msg","success");
		//mv.setViewName("redirect:/rutask/list.do");
		
		object.put("success","true");
		ResponseUtils.renderJson(response, object.toString());
		
		return null;
	}
	
	
	public String sendMsgByUser(String uid,String USER_ID,String dept,String ROLE_NAME) throws Exception{
		if(userService==null){
			userService=(UserService) userService1;
		}
		if(workorderService==null){
			workorderService=(WorkorderService) workorderService1;
		}
		PageData pd_workorder = new PageData();
		PageData pd_s = new PageData();
		pd_s.put("uid",uid);
		pd_workorder = workorderService.findById(pd_s);
		PageData pd_doc = new PageData();
		String sendcont="";
		if(pd_workorder!=null){
			//投诉、表扬、建议），等级（高级、紧急、普通）信息；
			sendcont="您有一条"+pd_workorder.getString("tslevelname")+"的投诉工单，请速处理";
		}
		pd_doc.put("ROLE_NAME",ROLE_NAME);
		pd_doc.put("dept",dept);
		List<PageData> roleList=userService.getUserByRoleName(pd_doc);
		String userstr="";
		RuTaskController ruTaskController=new RuTaskController();
		for(PageData pd_gd:roleList){
			if(userstr!=""){
				userstr=userstr+",";
			}
			userstr=userstr+pd_gd.getString("USERNAME");
			if(pd_gd.getString("USERNAME")!=null&&!pd_gd.getString("USERNAME").equals("")){
				ruTaskController.sendMsg(pd_gd.getString("USERNAME"), sendcont);
			}
		}
		
		return "";
	}
	
	
	
	public String sendMsg(String USER_ID,String sendcont) throws Exception{
		try{
			if(userService==null){
				userService=(UserService) userService1;
			}
			PageData pd=new PageData();
			pd.put("USERNAME", USER_ID);
			pd=userService.findByUsername(pd);
			if(pd!=null&&pd.getString("wxid")!=null&&!pd.getString("wxid").equals("")){
				//sendcont=URLEncoder.encode("请登录：http://webchat.ada-robotics.com:8090/ccweb/appWeixin/getUserid","utf-8");
				sendcont=URLEncoder.encode(sendcont+",<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe1de579339958660&redirect_uri=http://webchat.ada-robotics.com:8090/ccwebWx/appWeixin/getUserid&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\">点击前往！</a>","utf-8");
		
				//HttpClientUtil util=new HttpClientUtil("http://luyin.ada-robotics.com/weixin/app_message/sendtext?USER_ID="+USER_ID+"&content="+sendcont+"");
				String requestUrl="http://luyin.ada-robotics.com/weixin/app_message/sendtext?USER_ID="+pd.getString("wxid")+"&content="+sendcont+"";
				
				Httpclient httpclient=new Httpclient(); 
				String content=Httpclient.doGet2(requestUrl, "GBK");
				//JSONObject jsonobj ect=Weixin.httpRequst(requestUrl, "get", null);
				//设置请求方式
				//util.get();
				//获取返回内容
				//String content=util.getContent();
				//打印结果
				//System.out.println(content+"content11");
			}
			
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}	
		return "";
		
	}
	
	
	public String replay(String datajson) throws Exception{
		try{
			String requestUrl="http://luyin.ada-robotics.com/weixin/app_message/sendtext?data="+datajson+"";
			
			Httpclient httpclient=new Httpclient(); 
			String content=Httpclient.doGet2(requestUrl, "GBK");
			
		}catch(Exception e){
			e.getMessage();
		}	
		return "";
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		//RuTaskController ruTaskController=new RuTaskController();
		//System.out.println(ruTaskController.sendMsg("HuangJianLing", "内容"));
	}
	
	@RequestMapping(value="/getCltime")
	public void getCltime(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			String cltime=RuTaskController.timesBetween("", pd.getString("xclsj"));
			out.write(cltime.replace("天", "day").replace("时", "hour").replace("分", "min").replace("秒", "sec"));
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	
	
	
	
	
	/**办理任务
	 * @param
	 * @throws Exception
	 */
	/*@RequestMapping(value="/handle")
	public ModelAndView handle() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"办理任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Session session = Jurisdiction.getSession();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String taskId = pd.getString("ID_");	//任务ID
		
		pd.put("proc_id", pd.getString("PROC_INST_ID_"));
		PageData pd_s =workorderService.findById(pd);
		
		String sfrom = "";
		Object ofrom = getVariablesByTaskIdAsMap(taskId,"审批结果");
		if(null != ofrom){
			sfrom = ofrom.toString();
		}
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		String OPINION = sfrom + Jurisdiction.getUsername() + ",fh,"+pd.getString("OPINION");//审批人的姓名+审批意见
		String msg = pd.getString("msg");
		//String[] dept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
		////System.out.println("tsdept:"+pd_s.getString("tsdept"));
		List<Task> tasks = taskService.createTaskQuery().taskName("多部门工单处理")
                .processInstanceId(pd.getString("PROC_INST_ID_")).list();
		//System.out.println(tasks);
		
		Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
		//System.out.println(task+"tasks:::::");
        // 如果是会签流程
        if ( tasks != null && tasks.size() > 0 )
        {
            
            // 当前executionId
            String currentExecutionId = task.getId();
            // 当前签署总数
            String currentSignCount =String.valueOf(getVariablesByTaskIdAsMap(currentExecutionId , "nrOfInstances")) ;
            //System.out.println(currentSignCount+"currentSignCount");
            if (msg.equals( "yes" ))
            {
                // 签署数+1
                setVariablesByTaskId(currentExecutionId , "nrOfInstances" , String.valueOf((Integer.parseInt(currentSignCount ) + 1)) );
            }

        }else{
		
        	List<Task> tasks_d = taskService.createTaskQuery().taskName("单部门工单处理")
                    .processInstanceId(pd.getString("PROC_INST_ID_")).list();
        	Task task_d=taskService.createTaskQuery() // 创建任务查询
                    .taskId(taskId) // 根据任务id查询
                    .singleResult();
    		//System.out.println(task+"tasks:::::");
            // 如果是会签流程
            if ( tasks != null && tasks.size() > 0 ){
            	
            	
            }else{	
	
				if(msg.equals("yes")){								//批准
					map.put("单部门","单部门审批"+OPINION);		//审批结果
					setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
					setVariablesByTaskId(taskId,"RESULT","yes");
					completeMyPersonalTask(taskId);
				}else{												//驳回
					map.put("多部门","多部门审批");		//审批结果
					map.put("MULTIPLE",pd.getString("tsdept"));		//审批结果
					map.put("MULTIPLE", Jurisdiction.getUsername());
					setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
					setVariablesByTaskId(taskId,"RESULT","no");
					//completeMyPersonalTask(taskId);
					
					Map<String, Object> vMap = new HashMap<String, Object>();
					vMap.put("多部门","多部门审批");
					vMap.put("RESULT","no");
					
					List<String> assigneeList=new ArrayList<String>(); //分配任务的人员
					
					String[] dept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
					for(int i=0;i<dept.length;i++){
						assigneeList.add(dept[i]);
					}
					vMap.put("deptList", assigneeList);
					taskService.complete(taskId, vMap); //设置会签
					
					//setVariablesByTaskIdAsMap(taskId,vMap);
					//completeMyPersonalTask_Map(taskId,vMap);
					
					//map.put("审批结果", "【批准】" + OPINION);		//审批结果
					//setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
					//setVariablesByTaskId(taskId,"RESULT","批准");
					//completeMyPersonalTask(taskId);
				//}
            //}
				try{
					removeVariablesByPROC_INST_ID_(pd.getString("PROC_INST_ID_"),"RESULT");			//移除流程变量(从正在运行中)
				}catch(Exception e){
				}
				try{
					//System.out.println(pd.getString("tsdept")+"tsdept:::::");
					String ASSIGNEE_ =pd.getString("tsdept");							//下一待办对象
					if(Tools.notEmpty(ASSIGNEE_)){
						String tkid =ruprocdefService.getTaskID(pd.getString("PROC_INST_ID_"));
						setAssignee(tkid,ASSIGNEE_);	//指定下一任务待办对象
					}else{
						Object os = session.getAttribute("YAssignee");
						if(null != os && !"".equals(os.toString())){
							ASSIGNEE_ = os.toString();										//没有指定就是默认流程的待办人
						}else{
							//trySendSms(mv,pd); //没有任务监听时，默认流程结束，发送站内信给任务发起人
						}
					}
					//mv.addObject("ASSIGNEE_",ASSIGNEE_);	//用于给待办人发送新任务消息
				//}catch(Exception e){
					//trySendSms(mv,pd);
				}
		}	
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}*/
	
	
	public static void startTask(TaskService taskService, String proId){
		Task task = taskService.createTaskQuery().processInstanceId(proId).singleResult();
		//System.out.println("当前任务编码：" + task.getId() + "，当前任务名称：" + task.getName());
		//设置会签人员
		Map<String, Object> var = new HashMap<String, Object>();
		List<String> signList = new ArrayList<String>();
		signList.add("zhangSan");
		signList.add("liSi");
		var.put("signList", signList);
		taskService.complete(task.getId(), var);
	}

	

	public void Cl(String taskId,String tsdept) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"办理任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Session session = Jurisdiction.getSession();
		RuprocdefService  ruprocdefService=new RuprocdefService();
		Map<String,String> remap= ruprocdefService.getTaskMapByID(taskId);
   	
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		//String OPINION =  Jurisdiction.getU_name() + ","+pd.getString("OPINION");//审批人的姓名+审批意见
		//String msg = pd.getString("msg");
		String dept[]=tsdept.split(",");
		if(dept.length==1){								//批准
			//map.put("审批结果", "【批准】" + OPINION);		//审批结果
			setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
			setVariablesByTaskId(taskId,"RESULT","single");
			completeMyPersonalTask(taskId);
		}else{												//驳回
			//map.put("审批结果", "【驳回】" + OPINION);		//审批结果
			setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
			setVariablesByTaskId(taskId,"RESULT","multiple");
			completeMyPersonalTask(taskId);
		}
		try{
			removeVariablesByPROC_INST_ID_(remap.get("PROC_INST_ID_"),"RESULT");			//移除流程变量(从正在运行中)
		}catch(Exception e){
			/*此流程变量在历史中**/
		}
		try{
			String ASSIGNEE_ =tsdept;							//下一待办对象
			if(Tools.notEmpty(ASSIGNEE_)){
				String tkid = null;
				if(null!=session.getAttribute("TASKID")){
					tkid=session.getAttribute("TASKID").toString();
				}else{
					//tkid =ruprocdefService.getTaskID(remap.get("PROC_INST_ID_"));
				}
				setAssignee(tkid,ASSIGNEE_);	//指定下一任务待办对象
			}else{
				Object os = session.getAttribute("YAssignee");
				if(null != os && !"".equals(os.toString())){
					ASSIGNEE_ = os.toString();										//没有指定就是默认流程的待办人
				}else{
					//trySendSms(mv,pd); //没有任务监听时，默认流程结束，发送站内信给任务发起人
				}
			}
			//mv.addObject("ASSIGNEE_",ASSIGNEE_);	//用于给待办人发送新任务消息
		}catch(Exception e){
			/*手动指定下一待办人，才会触发此异常。
			 * 任务结束不需要指定下一步办理人了,发送站内信通知任务发起人**/
			//trySendSms(mv,pd);
		}
		
	}
	
	/**尝试站内信
	 * @param mv
	 * @param pd
	 * @throws Exception
	 */
	public void trySendSms(ModelAndView mv,PageData pd)throws Exception{
		List<PageData>	hivarList = hiprocdefService.hivarList(pd);			//列出历史流程变量列表
		for(int i=0;i<hivarList.size();i++){
			if("USERNAME".equals(hivarList.get(i).getString("NAME_"))){
				sendSms(hivarList.get(i).getString("TEXT_"));
				mv.addObject("FHSMS",hivarList.get(i).getString("TEXT_"));
				break;
			}
		}
	}
	
	/**发站内信通知审批结束
	 * @param USERNAME
	 * @throws Exception
	 */
	public void sendSms(String USERNAME) throws Exception{
		PageData pd = new PageData();
		pd.put("SANME_ID", this.get32UUID());			//ID
		pd.put("SEND_TIME", DateUtil.getTime());		//发送时间
		pd.put("FHSMS_ID", this.get32UUID());			//主键
		pd.put("TYPE", "1");							//类型1：收信
		pd.put("FROM_USERNAME", USERNAME);				//收信人
		pd.put("TO_USERNAME", "系统消息");
		pd.put("CONTENT", "您申请的任务已经审批完毕,请到已办任务列表查看");
		pd.put("STATUS", "2");
		fhsmsService.save(pd);
	}
	
	/**去审批详情页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/details")
	public ModelAndView details(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("activiti/rutask/handle_details");
		mv.addObject("pd", pd);
		return mv;
	}
	/*
	 * 设置接收
	 */
	@RequestMapping(value="/receive")
	@ResponseBody()
	public String receive(HttpServletResponse response,HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		com.alibaba.fastjson.JSONObject object=new com.alibaba.fastjson.JSONObject();
		try{
			String username=Jurisdiction.getUsername();
			PageData pd_token=ruprocdefService.findTaskById(pd);
			if(pd_token!=null){
				pd.put("username", username);
				ruprocdefService.receive(pd);
		        object.put("success","true");
			    object.put("msg","审核成功");
			}else{
				object.put("success","false");
		        object.put("msg","暂无数据");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","操作异常");
		}	
		ResponseUtils.renderJson(response, object.toString());
		return null;
	}
	
	
}
