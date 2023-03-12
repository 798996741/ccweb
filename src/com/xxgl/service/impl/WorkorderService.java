package com.xxgl.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.HttpClientUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxgl.controller.WorkorderController;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.utils.Constants;
import com.xxgl.utils.DateUtils;

/** 
 * 说明： 工单管理
 * 创建人：351412933
 * 创建时间：2019-11-24
 * @version
 */
@Service("workorderService")
public class WorkorderService implements WorkorderManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveWorkProc(PageData pd)throws Exception{
		dao.save("workorderMapper.saveWorkProc", pd);
	}
	
	public void save(PageData pd)throws Exception{
		dao.save("workorderMapper.save", pd);
	}
	
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("workorderMapper.delete", pd);
	}
	
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("workorderMapper.edit", pd);
	}
	
	public void editWorkproc(PageData pd)throws Exception{
		dao.update("workorderMapper.editWorkproc", pd);
	}
	
	public void editCL(PageData pd)throws Exception{
		dao.update("workorderMapper.editCL", pd);
	}
	
	public void zxPf(PageData pd)throws Exception{
		dao.update("workorderMapper.zxPf", pd);
	}
	
	public void zxDpf(PageData pd)throws Exception{
		dao.update("workorderMapper.zxDpf", pd);
	}
	
	public void updateWorkReceive(PageData pd)throws Exception{
		dao.update("workorderMapper.updateWorkReceive", pd);
	}
	
	public void editTsdept(PageData pd)throws Exception{
		dao.update("workorderMapper.editTsdept", pd);
	}
	
	public void saveTsdept(PageData pd)throws Exception{
		dao.update("workorderMapper.saveTsdept", pd);
	}
	
	public void editCfbm(PageData pd)throws Exception{
		dao.update("workorderMapper.editCfbm", pd);
	}

	public void editCLByuid(PageData pd)throws Exception{
		dao.update("workorderMapper.editCLByuid", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		List<PageData> varList=(List<PageData>)dao.findForList("workorderMapper.datalistPage", page);
		for(PageData pd:varList){
			if(!pd.getString("type").equals("4")&&!pd.getString("type").equals("5")){
				pd.put("iscs", this.getCsnum(pd));
			}
			
		}
		return varList;
	}
	
	
	public List<PageData> overTimelist(String type)throws Exception{
		Page page=new Page();
		PageData pd_s=new PageData();
        page.setCurrentPage(0);
        page.setShowCount(1000);
        pd_s.put("overtime", "1");
        page.setPd(pd_s);
		List<PageData> varList=(List<PageData>)dao.findForList("workorderMapper.datalistPage", page);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date now = new Date();
	    java.util.Date date=null;
	    long l=0;
	    long hour=0;
	    int day=0;
	    long days=0;
	    long min = 0;  
        long sec = 0;  

		String czdate="",clsx="",cljduser="";
		String[] arry=null;
		RuTaskController ruTaskController=new RuTaskController();
		for(PageData pd:varList){
			cljduser=pd.getString("cljduser")==null?"0":pd.getString("cljduser");
			clsx=pd.getString("clsx")==null?"0":pd.getString("clsx");
			czdate=String.valueOf(pd.get("czdate")==null?"":pd.get("czdate")).substring(0, 19);

			day=0;
			if(clsx.equals("24H")){
				day=1;
			}else if(clsx.equals("48H")){
				day=2;
			}else if(clsx.equals("3D")){
				day=3;
			}else if(clsx.equals("7D")){
				day=7;
			}
			if(day>0){
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date_cz =formatDate.parse(czdate);
				Calendar c = Calendar.getInstance();
		        c.setTime(date_cz);
		        c.add(Calendar.DAY_OF_MONTH, day);// 处理时限天数
		        Date tsdate_sx = c.getTime();
		        
				l=now.getTime()-tsdate_sx.getTime();
				days=l/(24*60*60*1000);
				hour=(l/(60*60*1000)-days*24);
				min = ((l / (60 * 1000)) - days * 24 * 60 - hour * 60);  
		        sec = (l/1000-days*24*60*60-hour*60*60-min*60);
				//System.out.println("L:"+l+"days:"+days+"hour:"+hour+"min:"+min+"sec:"+sec);
				//System.out.println("cljduser:"+cljduser+"czdate:"+czdate);
				if(type.equals("1")){
					if(days==0&&hour==-1&&min==0&&sec==0){
						arry=cljduser.split(",");
						//System.out.println(arry);
						for(int i=0;i<arry.length;i++){
							ruTaskController.sendMsg(arry[i], "您有一条即将超时的工单，请及时处理");				
						}
						
					}
					
				}else if(type.equals("2")&& l>0){
					
					arry=cljduser.split(",");
					//System.out.println(cljduser+"arry");
					for(int i=0;i<arry.length;i++){
						ruTaskController.sendMsg(arry[i], "您有一条超时的工单，请及时处理");				
					}
				}
				//pd.put("iscs", this.getCsnum(pd));
			}
			
			
			
		}
		return varList;
	}
	
	
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("workorderMapper.listAll", pd);
	}

	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findByid", pd);
	}
	
	public PageData findFileById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findFileById", pd);
	}
	
	public List<PageData> findFileByuid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("workorderMapper.findFileByuid", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("workorderMapper.deleteAll", ArrayDATA_IDS);
	}
	
	public void deleteFile(PageData pd)throws Exception{
		dao.delete("workorderMapper.deleteFile", pd);
	}
	
	public void deleteAllFile(PageData pd)throws Exception{
		dao.delete("workorderMapper.deleteAllFile", pd);
	}
	
	public void savefile(PageData pd)throws Exception{
		dao.save("workorderMapper.saveFile", pd);
	}
	
	public PageData gdCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.gdCount", pd);
	}
	
	/*
	 * 超时数据
	 * (non-Javadoc)
	 * @see com.xxgl.service.mng.WorkorderManager#getCsnum(java.util.List)
	 */
	public int getCsnum(List<PageData> alllist)throws Exception{
		String clsx="",czdate="";
		int day=0;
		int csnum=0;
		for(PageData pddoc:alllist){
			clsx=pddoc.getString("clsx")==null?"0":pddoc.getString("clsx");
			czdate=String.valueOf(pddoc.get("czdate"));
			day=0;
			if(clsx.equals("24H")){
				day=1;
			}else if(clsx.equals("48H")){
				day=2;
			}else if(clsx.equals("3D")){
				day=3;
			}else if(clsx.equals("7D")){
				day=7;
			}
			if(day>0){
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date =formatDate.parse(czdate);
				Calendar c = Calendar.getInstance();
		        c.setTime(date);
		        c.add(Calendar.DAY_OF_MONTH, day);// 处理时限天数
		        
		        Date tsdate_sx = c.getTime();
		        
		        Date today = new Date();
		        c.setTime(today);
		       // c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
		        Date tomorrow = c.getTime();
		        
		        String dateString = formatDate.format(tsdate_sx);
		        String dateString2 = formatDate.format(tomorrow);
		        
		        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date dt1 = df.parse(dateString);
	            Date dt2 = df.parse(dateString2);
		        
		        if(dt1.getTime()<dt2.getTime()){
		        	csnum++;
		        }
			}
		}
		return csnum;
	}
	
	
	public int getCsnum(PageData pddoc)throws Exception{
		String clsx="",tsdate="";
		int day=0;
		int csnum=0;
		//for(PageData pddoc:alllist){
			clsx=pddoc.getString("clsx")==null?"0":pddoc.getString("clsx");
			tsdate=String.valueOf(pddoc.get("czdate"));
			day=0;
			if(clsx.equals("24H")){
				day=1;
			}else if(clsx.equals("48H")){
				day=2;
			}else if(clsx.equals("3D")){
				day=3;
			}else if(clsx.equals("7D")){
				day=7;
			}
			if(day>0){
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date =formatDate.parse(tsdate);
				Calendar c = Calendar.getInstance();
		        c.setTime(date);
		        c.add(Calendar.DAY_OF_MONTH, day);// 处理时限天数
		        
		        Date tsdate_sx = c.getTime();
		        
		        Date today = new Date();
		        c.setTime(today);
		        //c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
		        Date tomorrow = c.getTime();
		        
		        String dateString = formatDate.format(tsdate_sx);
		        String dateString2 = formatDate.format(tomorrow);
		        
		        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date dt1 = df.parse(dateString);
	            Date dt2 = df.parse(dateString2);
		       // System.out.println(dateString+"dt2"+dateString2);
		        if(dt1.getTime()<dt2.getTime()){
		        	 //System.out.println(csnum+"dt2"+dateString2);
		        	csnum++;
		        }
			}
		//}
			// System.out.println(csnum+"dt2");
		return csnum;
	}
	
	public PageData taskSend(PageData pd) throws Exception{
		RuTaskController ruTaskController=new RuTaskController();
		//查询所有人员信息
		pd.put("ROLE_NAMES", "工单专员,部门领导,部门处理人员,投诉坐席,科室处理人员");
		List<PageData> roleList=userService.getUserByRoleName(pd);
		PageData pd_stoken=new PageData();
		PageData pd_token=new PageData();
		for(PageData pduser:roleList){
			pd_stoken.put("USERNAME", pduser.getString("USERNAME"));
			pd_token=userService.findByUsername(pd_stoken); //获取用户信息
			pd_stoken.put("ROLE_ID", pd_token.getString("ROLE_ID"));
			PageData role=roleService.findObjectById(pd_stoken);//获取用户角色
			PageData pdAll=workorderService.getAllList(pd, pd_token, role, pduser.getString("USERNAME"));
			if(pdAll!=null&&pdAll.get("csnum")!=null&&Integer.parseInt(String.valueOf(pdAll.get("csnum")))>0){//超时记录大于0
				//System.out.println(pduser.getString("USERNAME")+"dddd");
				ruTaskController.sendMsg(pduser.getString("USERNAME"), "您有"+pdAll.get("csnum")+"条投诉工单需要处理");				
			}
			
		}
		return pd;
		//return (PageData)dao.findForObject("workorderMapper.findByYear", pd);
	}
	
	
	public PageData getAllList(PageData pd,PageData user,PageData role,String username) throws Exception{
		
		PageData pdtj=new PageData();
		Page page=new Page();
		int wcl=0;
		int clz=0;
		int dsh=0;
		int js=0;
		
		if(role!=null&&(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("部门处理人员")))){
			if(user.getString("dwbh")!=null){
				pd.put("DWBM", user.getString("dwbh")); 	
			}else{
				pd.put("DWBM", user.getString("DWBM")); 	
			}
		}else if(role!=null&&(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("科室处理人员")))){
			String bm="";
			if(user.getString("dwbh")!=null){
				bm=user.getString("dwbh");
			}else{
				bm=user.getString("DWBM"); 	
			}
			pd.put("DWBM_OFFICE", bm); 
			pd.put("DWBM_OFFICE1", bm); 
		}else{
			pd.put("USERNAME", username); 		//查询当前用户的任务(用户名查询)
		}
		String islx="cl";
		//System.out.println(role.getString("ROLE_NAME"));
		if(role!=null&&(user.getString("DWBM").equals("350101")&&"工单专员".equals(role.getString("ROLE_NAME")))){
			pd.put("DWBM", user.getString("DWBM")); 
			pd.put("USERNAME", username);
			pd.put("isgdzy","1");
		}else if(role!=null&&"投诉坐席".equals(role.getString("ROLE_NAME"))){
			//pd.put("DWBM", user.getString("DWBM")); 
			pd.put("USERNAME", username);
			pd.put("isgdzy","1");
			pd.put("iszx","1");
			islx="sh";
		}else{
			pd.put("isgdzy","0");
		}
		
		if(role!=null&&"部门领导".equals(role.getString("ROLE_NAME"))){ //判断觉得是否查询
			pd.put("DWBM1", user.getString("DWBM")); 
			islx="sh";
		}
		if(role!=null&&"部门处理人员".equals(role.getString("ROLE_NAME"))){ //判断是否处理人员
			pd.put("DWBM2", user.getString("DWBM")); 
		}
        List<PageData> list;
        List<PageData> dblist =new ArrayList();
        List<PageData> alllist=new ArrayList();
        pd.put("isreceive","2");//只查询需要接收的数据
        if(pd!=null&&pd.getString("isgdzy")!=null&&pd.getString("isgdzy").equals("1")){ //安质部的工单专员
        	pd.put("dpf", "1");
			if(pd.getString("iszx")!=null&&pd.getString("iszx").equals("1")){
				pd.put("dwbm", user.getString("DWBM")); 
	        	pd.put("dpf", "1");
				pd.put("iszxuser","1");
				page.setPd(pd);
	        	dblist=workorderService.list(page);	//列出doc列表
			}else{
				
				pd.put("dpf", "1");
				//pd.put("iszx","0");
				pd.put("ispf","0");
				page.setPd(pd);
	        	dblist=workorderService.list(page);	//列出doc列表
			}
			
        }else{
        	page.setPd(pd);
        }
       
		list =ruprocdefService.list(page);	//列出Rutask列表
    	PageData dbpd=new PageData();
		for(PageData pddoc:dblist){  
			pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 10));
			dbpd=new PageData();
			pddoc.put("czdate", String.valueOf(pddoc.get("czdate")).substring(0, 19));
			dbpd.put("czdate", pddoc.getString("czdate"));
			dbpd.put("tsdate", pddoc.getString("tsdate"));
			dbpd.put("order_by", pddoc.getString("order_by"));
			dbpd.put("tssourcename", pddoc.getString("tssourcename"));
			dbpd.put("tslevelname", pddoc.getString("tslevelname"));
			dbpd.put("clsx", pddoc.getString("clsx"));
			dbpd.put("proc_id", pddoc.getString("proc_id"));
			dbpd.put("id", pddoc.get("id"));
			dbpd.put("code", pddoc.get("code"));
			dbpd.put("uid", pddoc.getString("uid"));
			dbpd.put("isreceive", pddoc.getString("isreceive"));
			dbpd.put("depttype", pddoc.getString("depttype"));
			dbpd.put("islx", "dpf");
			dbpd.put("tsdate_format",pddoc.getString("tsdate"));
			dbpd.put("tsman", pddoc.getString("tsman"));
			dbpd.put("tstel", pddoc.getString("tstel"));
			dbpd.put("tsdeptname", pddoc.getString("tsdeptname"));
			dbpd.put("tsbigtype", pddoc.getString("tsbigtype"));
			dbpd.put("tstypename", pddoc.getString("tstypename"));
			dbpd.put("tsclassifyname", pddoc.getString("tsclassifyname"));
			dbpd.put("dealman", pddoc.getString("dealman"));
			dbpd.put("cljdname", pddoc.getString("cljdname"));
			dbpd.put("endreason", pddoc.getString("endreason"));
			dbpd.put("type", pddoc.getString("type"));
			dbpd.put("id", String.valueOf(pddoc.get("id")));
			if(!pddoc.getString("type").equals("4")&&!pddoc.getString("type").equals("5")){
				dbpd.put("iscs", this.getCsnum(pddoc));
			}else{
				dbpd.put("iscs", "0");
			}
			
			dbpd.put("cardid", pddoc.getString("cardid"));
			dbpd.put("tscont", pddoc.getString("tscont"));
			dbpd.put("tstype", pddoc.getString("tstype"));
			dbpd.put("tsclassify", pddoc.getString("tsclassify"));
			dbpd.put("tsdept", pddoc.getString("tsdept"));
			dbpd.put("tssource", pddoc.getString("tssource"));
			dbpd.put("tslevel", pddoc.getString("tslevel"));
			dbpd.put("ishf", pddoc.getString("ishf"));
			if(pddoc.get("endtime")!=null){
				dbpd.put("endtime", String.valueOf(pddoc.get("endtime")).substring(0, 19));
			}
			
			dbpd.put("cjdate", pddoc.getString("cjdate"));
			dbpd.put("hbh", pddoc.getString("hbh"));
			dbpd.put("kscl", pddoc.getString("kscl"));
			dbpd.put("czman", pddoc.getString("czman"));
			dbpd.put("proc_id", pddoc.getString("proc_id"));
			dbpd.put("cfbm", pddoc.getString("cfbm"));
			dbpd.put("iszx", pddoc.getString("iszx"));
			dbpd.put("source", pddoc.getString("source"));
		
			dbpd.put("email", pddoc.getString("email"));
			dbpd.put("tsqd", pddoc.getString("tsqd"));
			dbpd.put("tssq", pddoc.getString("tssq"));
			dbpd.put("cardtype", pddoc.getString("cardtype"));
			dbpd.put("deport", pddoc.getString("deport"));
			dbpd.put("arrport", pddoc.getString("arrport"));
			dbpd.put("location", pddoc.getString("location"));
			//dbpd.put("isreceive", "");
			wcl++;
			alllist.add(dbpd);
		}
		
		for(PageData pddoc:list){  //不是待派发数据 需要做接收
			//System.out.println(pddoc.getString("NAME_")+"name_cl");
			pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 10));
			dbpd=new PageData();
			pddoc.put("czdate", String.valueOf(pddoc.get("czdate")).substring(0, 19));
			dbpd.put("czdate", pddoc.getString("czdate"));
			dbpd.put("tsdate", pddoc.getString("tsdate"));
			dbpd.put("tssourcename", pddoc.getString("tssourcename"));
			dbpd.put("tslevelname", pddoc.getString("tslevelname"));
			dbpd.put("clsx", pddoc.getString("clsx"));
			dbpd.put("order_by", pddoc.getString("order_by"));
			dbpd.put("proc_id", pddoc.getString("proc_id"));
			dbpd.put("id", pddoc.get("id"));
			dbpd.put("uid", pddoc.getString("uid"));
			dbpd.put("ID_", pddoc.getString("ID_"));
			dbpd.put("PROC_INST_ID_", pddoc.getString("PROC_INST_ID_"));
			
			//dbpd.put("iscs", "0");
			
			
			dbpd.put("DGRM_RESOURCE_NAME_", pddoc.getString("DGRM_RESOURCE_NAME_"));
			dbpd.put("code", pddoc.get("code"));
			dbpd.put("tsdate_format",pddoc.getString("tsdate"));
			dbpd.put("tsman", pddoc.getString("tsman"));
			dbpd.put("tstel", pddoc.getString("tstel"));
			dbpd.put("tsdeptname", pddoc.getString("tsdeptname"));
			dbpd.put("tsbigtype", pddoc.getString("tsbigtype"));
			dbpd.put("tstypename", pddoc.getString("tstypename"));
			dbpd.put("tsclassifyname", pddoc.getString("tsclassifyname"));
			dbpd.put("dealman", pddoc.getString("dealman"));
			dbpd.put("cljdname", pddoc.getString("cljdname"));
			dbpd.put("endreason", pddoc.getString("endreason"));
			dbpd.put("type", pddoc.getString("type"));
			dbpd.put("id", String.valueOf(pddoc.get("id")));
			dbpd.put("iscs", this.getCsnum(pddoc));
			dbpd.put("cardid", pddoc.getString("cardid"));
			dbpd.put("tscont", pddoc.getString("tscont"));
			dbpd.put("tstype", pddoc.getString("tstype"));
			dbpd.put("tsclassify", pddoc.getString("tsclassify"));
			dbpd.put("tsdept", pddoc.getString("tsdept"));
			dbpd.put("tssource", pddoc.getString("tssource"));
			dbpd.put("tslevel", pddoc.getString("tslevel"));
			dbpd.put("ishf", pddoc.getString("ishf"));
			if(pddoc.get("endtime")!=null){
				dbpd.put("endtime", String.valueOf(pddoc.get("endtime")).substring(0, 19));
			}
			dbpd.put("cjdate", pddoc.getString("cjdate"));
			dbpd.put("hbh", pddoc.getString("hbh"));
			dbpd.put("kscl", pddoc.getString("kscl"));
			dbpd.put("czman", pddoc.getString("czman"));
			dbpd.put("proc_id", pddoc.getString("proc_id"));
			dbpd.put("cfbm", pddoc.getString("cfbm"));
			dbpd.put("iszx", pddoc.getString("iszx"));
			dbpd.put("source", pddoc.getString("source"));
		
			dbpd.put("email", pddoc.getString("email"));
			dbpd.put("tsqd", pddoc.getString("tsqd"));
			dbpd.put("tssq", pddoc.getString("tssq"));
			dbpd.put("cardtype", pddoc.getString("cardtype"));
			dbpd.put("deport", pddoc.getString("deport"));
			dbpd.put("arrport", pddoc.getString("arrport"));
			dbpd.put("location", pddoc.getString("location"));
			dbpd.put("isreceive", pddoc.getString("ISRECEIVES"));
			dbpd.put("receive", pddoc.getString("receive"));
			dbpd.put("depttype", pddoc.getString("depttype"));
			
			if((user.getString("DWBM").equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))){
				if(pddoc!=null&&pddoc.getString("NAME_").equals("多部门工单处理")){
					PageData db_deal=new PageData();
					db_deal.put("dept", "350101");
					db_deal.put("proc_id", pddoc.getString("proc_id"));
					db_deal.put("iscl", "1");
					db_deal.put("isdel", "0");
					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(db_deal);
					if(pd_deptdeal_s==null||pd_deptdeal_s.get("id")==null){
						dbpd.put("islx", islx);
						wcl++;
						alllist.add(dbpd);
					}
					pddoc.put("ISRECEIVES", pddoc.getString("ISRECEIVES")==null?"0":pddoc.getString("ISRECEIVES"));
				}else if(pddoc!=null&&pddoc.getString("NAME_").equals("单部门工单处理")){
					dbpd.put("islx", islx);
					wcl++;
					alllist.add(dbpd);
				}else{
					dbpd.put("islx", islx);
					dsh++;
					alllist.add(dbpd);
				}
			}else{
				if(pddoc!=null&&pddoc.getString("NAME_").equals("多部门工单处理")){
					PageData db_deal=new PageData();
					db_deal.put("dept", user.getString("DWBM"));
					db_deal.put("proc_id", pddoc.getString("proc_id"));
					db_deal.put("iscl", "1");
					db_deal.put("isdel", "0");
					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(db_deal);
					if(pd_deptdeal_s==null||pd_deptdeal_s.get("id")==null){
						dbpd.put("islx", islx);
						wcl++;
						alllist.add(dbpd);
					}
				}else if(pddoc!=null&&pddoc.getString("NAME_").equals("单部门工单处理")){
					dbpd.put("islx", islx);
					wcl++;
					alllist.add(dbpd);
				}else{
					dbpd.put("islx", islx);
					dsh++;
					alllist.add(dbpd);
				}
			}
			
		}
		
		Collections.sort(alllist,new Comparator<PageData>(){
			
			@Override
			public int compare(PageData o1, PageData o2) {
				// TODO Auto-generated method stub
				//System.out.println(o2);
				//System.out.println(o1);
				if(Integer.parseInt(String.valueOf(o2.get("iscs")))>Integer.parseInt(String.valueOf(o1.get("iscs")))){
					return 1;//降序
				}
				return -1;
				
			}
		});
		
		int csnum=workorderService.getCsnum(alllist);
		pdtj.put("csnum", csnum);
		pdtj.put("wclCount", wcl);
		pdtj.put("clzCount", wcl+dsh);
		pdtj.put("dshCount", dsh);
		pdtj.put("varList", alllist);
		return pdtj;
	}

	
	
	public PageData findByYear(PageData pd) throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findByYear", pd);
	}

	public void editCode(PageData pd) throws Exception{
		dao.update("workorderMapper.editCode", pd);
	}
	
	public void editReplayFile(PageData pd) throws Exception{
		dao.update("workorderMapper.editReplayFile", pd);
	}
	
	public void editReplayReceive(PageData pd) throws Exception{
		dao.update("workorderMapper.editReplayReceive", pd);
	}
	
	public void editReplay(PageData pd) throws Exception{
		dao.update("workorderMapper.editReplay", pd);
	}
	

	public void saveCode(PageData pd) throws Exception{
		dao.save("workorderMapper.saveCode", pd);
	}
	
	
	
	public String getWorkorderList(String USER_ID,String sendcont) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBURL);
		//设置请求方式
		String content="";
		Map<String, String> param=new HashMap();
		String[] USERID=Constants.USERID;
		String[] POSTID=Constants.POSTID;
		for(int i=0;i<USERID.length;i++){
			param.put("userId", USERID[i]);
			param.put("postId", POSTID[i]);
			param.put("orderCol", "");
			param.put("orderDir", "asc");
			param.put("pageNum", "1");
			param.put("pageSize", "10000");
			param.put("keyWord", "");
			param.put("questionType", "");
			param.put("deptId", "");
			util.setParameter(param);
			util.post();
			//获取返回内容
			content=util.getContent();
			//打印结果
			System.out.println("____________________________________");
			System.out.println(content+"content");
			System.out.println("__________________________________________");
			PageData pd=new PageData();
			PageData pd_dict=new PageData();
			org.json.JSONObject json = new org.json.JSONObject(content);   
			List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(json.getString("data"));
	        System.out.println("利用JSONArray中的parse方法来解析json数组字符串");
	        String value="";
	        PageData pddb=null;
	        
	        for(Map<String,String> mapList : listObjectFir){
	        	pd=new PageData();
	        	pddb=null;
	            for (Map.Entry entry : mapList.entrySet()){
	            	value=(entry.getValue()==null?"":entry.getValue()).toString();
	               if(entry.getKey()!=null&&(entry.getKey().equals("updatetime")||entry.getKey().equals("repTime")||entry.getKey().equals("dueTime")||entry.getKey().equals("dispatchDate")||entry.getKey().equals("replyTime"))){
	            	   if(!value.equals("")){
	            		   value=DateUtils.timeStamp2Date(value,"");
	            	   }
	               }
	              
	               if(value.equals("null")){
	            	   value="";
	               }
	               pd.put(entry.getKey(), value);
	            }
	            pd.put("userid", USERID[i]);
				pd.put("postid", POSTID[i]);
	            pddb= workorderService.findDbByid(pd);
	            if(pddb==null){
	            	workorderService.saveDb(pd);
	            	//发送接收信息
	            	this.sendNextPerson(pd.getString("caseCode"), POSTID[i], sendcont);
	            	BaseController baseController=new BaseController();
	            	WorkorderController workorderController=new WorkorderController();
	            	pd.put("workid", baseController.get32UUID());
	        		pd.put("tsdate",pd.getString("repTime"));
	        		pd.put("tssource","183f8c3ffd654a6aa307aa14c3fbe033");
	        		pd.put("tsman",pd.getString("repMan"));
	        		pd.put("tstel",pd.getString("repTel"));
	        		pd.put("tscont",pd.getString("matterDescription"));
	        		pd.put("tslevel",pd.getString("tslevel"));
	        		pd.put("NAME", pd.getString("matterType"));
	        		pd.put("source", "1");
	        		pd.put("tsdept","");
	        		pd_dict=dictionariesService.findByName(pd);
	        		if(pd_dict!=null){
	        			pd.put("tsclassify",pd_dict.getString("DICTIONARIES_ID"));
	        		}
	        		
	        		pd.put("tstype","");
	        		pd.put("ishf","");
	        		pd.put("type","0");
	        		pd.put("cljd","");
	        		pd.put("cardid","");
	        		pd.put("cjdate","");
	        		pd.put("czman","350111-zy");  //默认呼叫中心插入用户
	        		if(pd.getString("completeTimeAsk")!=null){
	        			pd.put("clsx",Integer.parseInt(pd.getString("completeTimeAsk"))/60);
	        		}
	        		
	        		pd.put("uid", baseController.get32UUID());	//随机生成
	        		pd.put("type", "0");
	        		
	        		Date currentTime = new Date();
	        		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        		String year = formatter.format(currentTime);
	        		PageData pd_year = new PageData();
	        		pd_year.put("year", year);
	        		pd_year = this.getNum(year);
	        		String code_num = String.valueOf(pd_year.get("code_num"));
	        		int length = code_num.length();
	        		String file_code = "";
	        		if(length == 1){
	        			file_code = "000" + code_num + "";
	        		}else if(length == 2){
	        			file_code = "00" + code_num + "";
	        		}else if(length == 3){
	        			file_code = "0" + code_num + "";
	        		}else{
	        			file_code = "" + code_num + "";
	        		}
        			pd.put("code", year+file_code);
        			workorderService.save(pd);	
        			if("1".equals(code_num)){
        				workorderService.saveCode(pd_year);
        			}else{
        				pd_year.put("code_num", Integer.parseInt(code_num));
        				workorderService.editCode(pd_year);
        			}
	            }
	            
	        }
		}
		
        return content;
		
	}
	
	public PageData getNum(String year) throws Exception{
		PageData pd_num = new PageData();
		pd_num.put("year", year);
		pd_num = workorderService.findByYear(pd_num);
		if(pd_num == null){
			pd_num = new PageData();
			pd_num.put("year", year);
			pd_num.put("code_num", "1");
		}else{
			int code_num = Integer.parseInt(String.valueOf(pd_num.get("code_num")))+1;
			pd_num.put("code_num", code_num);
		}
		return pd_num;
	}

	
	public PageData findDbByCasecode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findDbByCasecode", pd);
	}
	
	public PageData findDbByid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findDbByid", pd);
	}
	
	public void saveDb(PageData pd)throws Exception{
		dao.save("workorderMapper.saveDb", pd);
	}
	
	/*
	 * 设置纳入指标
	 * (non-Javadoc)
	 * @see com.xxgl.service.mng.WorkorderManager#saveDb(com.fh.util.PageData)
	 */
	public void target(PageData pd)throws Exception{
		dao.save("workorderMapper.target", pd);
	}
	
	/*
	 * 设置纳入指标
	 * (non-Javadoc)
	 * @see com.xxgl.service.mng.WorkorderManager#saveDb(com.fh.util.PageData)
	 */
	public int returnProc(PageData pd)throws Exception{
		int i=1;
		dao.update("workorderMapper.returnProc", pd);
		return i;
	}
	
	
	/*
	 * 坐席撤回派发
	 * (non-Javadoc)
	 * @see com.xxgl.service.mng.WorkorderManager#saveDb(com.fh.util.PageData)
	 */
	public void returnPf(PageData pd)throws Exception{
		dao.update("workorderMapper.returnPf", pd);
	}
	
	/*
	 * 申请退单接口
	 */
	public String sendReturn(String caseCode,String userId,String postId,String opinion,String deptId,String taskId,String unitType) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBRETURN);
		//设置请求方式
		
		Map<String, String> param=new HashMap();

		param.put("caseCode",caseCode);
		param.put("postId", postId);
		param.put("userId", userId);
		param.put("deptId", deptId);
		param.put("opinion", opinion);
		param.put("taskId", taskId);
		param.put("unitType", unitType);
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}

	/*
	 * 1.责任单位签收员或处理人发送下一处理人
	 */
	public String sendNextPerson(String caseCode,String postId,String sendcont) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBNEXTURL);
		//设置请求方式
		
		Map<String, String> param=new HashMap();
		
		param.put("caseCode",caseCode);
		param.put("postId", postId);
		param.put("recvDeptId", "");
		param.put("recvPosId", "");
		param.put("opinion", "");
		param.put("taskId", "");
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}
	
	
	/*
	 * 责任单位处理人申请审结
	 */
	public String sendAuditFinish(String caseCode,String replyPoint,String postId,String opinion,String replyPerson,String taskId,String replyPhoneNo,String replyTime,String solved) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBAuditFinish);
		//设置请求方式
	
		
		Map<String, String> param=new HashMap();

		param.put("caseCode",caseCode);
		param.put("postId", postId);
		param.put("replyPoint", replyPoint);
		
		param.put("opinion", opinion);
		param.put("taskId", taskId);
		param.put("replyPerson", replyPerson);
		param.put("replyPhoneNo", replyPhoneNo);
		
		param.put("replyTime", replyTime);
		param.put("solved", solved);
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}

	
	
	/*
	 * 责任单位分管领导审核审结
	 */
	public String sendFinishByLeader(String caseCode,String postId,String opinion,String doType,String taskId) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBFinishByLeader);
		//设置请求方式
		Map<String, String> param=new HashMap();

		param.put("caseCode",caseCode);
		param.put("postId", postId);
		
		param.put("opinion", opinion);
		param.put("taskId", taskId);
		param.put("doType", doType);
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}

	



	
	public static void main(String[] args) throws Exception {
		WorkorderService ruTaskController=new WorkorderService();
		System.out.println(ruTaskController.sendNextPerson("20200119033924", "159581", "内容"));
	}
	
	
	
	
}

