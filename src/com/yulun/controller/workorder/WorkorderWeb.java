package com.yulun.controller.workorder;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import utils.FileStrUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.activiti.AcStartController;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.system.operatelog.OperateLogManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.Httpsend;
import com.fh.util.IPUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;
import com.xxgl.controller.AsynTask;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.service.mng.ZxlbManager;
import com.xxgl.utils.PageUtil;
import com.yulun.controller.api.CommonIntefate;


/**
 * 说明：工单接口开发
 * 创建人：351412933，hjl
 * 创建时间：2019-01-03
 */
public class WorkorderWeb extends AcStartController  implements CommonIntefate {
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	
	@Resource(name = "operatelogService")
	private OperateLogManager operatelogService;
	//@Autowired
	@Resource(name="taskService")
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
	
	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request)
			throws Exception {
		JSONObject object=new JSONObject();
		try{
			PageData pd = new PageData();
			String cmd = data.getString("cmd")==null?"":data.getString("cmd");
			PageData pd_stoken=new PageData();
			JSONObject json = data.getJSONObject("data");
			pd_stoken.put("TOKENID", json.get("tokenid"));
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			String username=(json.getString("username")==null?"":json.getString("username"));
			if(!username.equals("")){
				pd_stoken.put("USERNAME", username);
				pd_token=userService.findByUsername(pd_stoken);
				if(pd_token!=null){
					pd_token.put("dept", pd_token.getString("DWBM"));
					pd_token.put("ZXYH", username);
				}
				pd.put("ZXID", username);
				pd.put("systype", "1");
			}else{
				if(pd_token!=null){
					pd.put("ZXID", pd_token.getString("ZXID"));
				}
			}
			if(pd_token!=null){
				pd.put("ZXID", pd_token.getString("ZXID"));
				if(cmd.equals("WorkorderAll")){  //根据提交的方法执行相应的操作，获取工单分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
		            String keywords = json.getString("keywords")==null?"":URLDecoder.decode( json.getString("keywords"),"utf-8");				//关键词检索条件
		    		if(null != keywords && !"".equals(keywords)){
		    			pd.put("keywords", keywords.trim());
		    		}	
		    		String doaction = json.getString("doaction")==null?"":json.getString("doaction"); //1.显示自己的操作
		    		if(doaction.equals("1")){
		    			pd.put("czman",pd_token.getString("ZXYH"));
		    		}
		    		
		    		if(null != json.getString("cardid") ){
		    			pd.put("cardid", json.getString("cardid"));
		    		}
		    		if(null != json.getString("tscont") ){
		    			pd.put("tscont", json.getString("tscont"));
		    		}
		    		
		    		if(null != json.getString("code") ){
		    			pd.put("code", json.getString("code"));
		    		}
		    		pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
		    		if(null != json.getString("tssource") ){
		    			pd.put("tssource", json.getString("tssource"));
		    		}	
		    		if(null != json.getString("tsman") ){
		    			pd.put("tsman", json.getString("tsman"));
		    		}	
		    		if(null != json.getString("tsdept") ){
		    			pd.put("tsdept", json.getString("tsdept"));
		    		}
		    		if(null != json.getString("uid") ){
		    			pd.put("workid", json.getString("uid"));
		    		}
		    		
		    		if(null != json.getString("type") ){
		    			pd.put("types", json.getString("type"));
		    		}	
		    		
		    		if(null != json.getString("tstype") ){
		    			pd.put("tstype", json.getString("tstype"));
		    		}
		    		if(null != json.getString("bigtstype") ){
		    			pd.put("bigtstype", json.getString("bigtstype"));
		    		}
		    		
		    		if(null != json.getString("endreason") ){
		    			pd.put("endreason", json.getString("endreason"));
		    		}	
		    		
		    		if(null != json.getString("zxid") ){
		    			pd.put("zxid", json.getString("zxid"));
		    		}
		    		
		    		if(null != json.getString("iszx") ){
		    			pd.put("iszx", json.getString("iszx"));
		    		}
		    		
		    		
		    		if(pd_token!=null){
		    			String dwbm= pd_token.getString("dept");
		    			if(dwbm.equals("350101")){
		    				dwbm="3501";
		    			}
		    			pd.put("dwbm", dwbm);
		    			
		    		}
		    		pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		    		list = workorderService.list(page);	//列出doc列表
		    		String[] arrcl=null;
					JSONObject pdcl=new JSONObject();
					PageData pduser=new PageData();
					List<PageData>	varList =null;
					List<JSONObject> clList=new ArrayList();
					if(list.size()>0){
						for(PageData pddoc:list){  
							clList=new ArrayList();
							//System.out.println(list);
							if(pddoc.get("tsdate")!=null){
								pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 19));
							}
							if(pddoc.get("czdate")!=null){
								pddoc.put("czdate", String.valueOf(pddoc.get("czdate")).substring(0, 19));
							}
							if(pddoc.get("cldate")!=null){
								pddoc.put("cldate", String.valueOf(pddoc.get("cldate")).substring(0, 19));
							}
							if(pddoc.get("cjdate")!=null){
								//pddoc.put("cjdate", String.valueOf(pddoc.get("cjdate")).substring(0, 10));
							}
							if(pddoc.get("endtime")!=null){
								pddoc.put("endtime", String.valueOf(pddoc.get("endtime")).substring(0, 19));
							}
							
							if(pddoc!=null&&pddoc.getString("proc_id")!=null){
								
								/*
								 * 获取审批信息
								 */
							    pdcl=new JSONObject();
								pduser=new PageData();
								pd.put("PROC_INST_ID_", pddoc.getString("proc_id"));
								varList = ruprocdefService.varList(pd);	
								String lasttime="";
								for(int i=0;i<varList.size();i++){	
									pduser=new PageData();
									String clcont="";
									if(varList.get(i).getString("TEXT_")!=null&&varList.get(i).getString("TEXT_").indexOf(",huangfege,")>=0){
										pdcl=new JSONObject();
										arrcl=varList.get(i).getString("TEXT_").split(",huangfege,");
										pdcl.put("clman", arrcl[0]);
										pdcl.put("USERNAME", arrcl[0]);
										
										clcont=(arrcl[1]==null?"":arrcl[1]);
										if(clcont.equals("null")){
											clcont="";
										}
										pdcl.put("clcont", clcont);
										pdcl.put("cldate", String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_")==null?"":varList.get(i).get("LAST_UPDATED_TIME_")).substring(0, 10));
										pduser.put("clman", arrcl[0]);
										pduser.put("USERNAME", arrcl[0]);
										pduser.put("USER_ID", "");
										if(arrcl[0]!=null&&!"".equals(arrcl[0])){
											pduser=userService.getUserInfoByUsername(pduser);
											if(pduser!=null){
												pdcl.put("areaname", pduser.getString("areaname"));
												pdcl.put("name", pduser.getString("NAME"));
												
											}
										}
										
										clList.add(pdcl);
										
									}
								}
								pddoc.put("clList", clList.toString());
							}	
						}
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("WorkorderDel")) {
			        pd.put("id",json.get("id"));
			    	String DATA_IDS =String.valueOf(json.get("id"));
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					workorderService.deleteAll(ArrayDATA_IDS);
					
					String operate="{工单删除:"+DATA_IDS+"}";
					
			        pd.put("ID", UuidUtil.get32UUID());
			        pd.put("MAPPERNAME","workorderMapper.delete");
			        pd.put("OPERATEMAN", pd.put("ZXID", pd_token.getString("ZXID")));// 操作者
			        pd.put("systype","1");
			        pd.put("OPERATEDATE", new Date());// 时间
			        pd.put("OPERATESTR", operate);// 请求参数
			        pd.put("TYPE", "1");// 正常结束
			        pd.put("IP", IPUtil.getLocalIpv4Address());// ip
			        operatelogService.save(pd);
					
		        	//docService.delete(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
			       
				}else if(cmd.equals("WorkorderFindById")) {
			        pd.put("id",json.get("id"));
			        pd.put("proc_id",json.get("proc_id"));
			        PageData pdDoc=workorderService.findById(pd);
			      //  System.out.println(pdDoc.get("proc_id")+"proc_idd");
			       // String taskId = ruprocdefService.getTaskID(String.valueOf(pdDoc.get("proc_id")));	//任务ID	//任务ID
			        String taskname="";
			       /* if(taskId!=null){
			        	Task task=taskService.createTaskQuery() // 创建任务查询
				                .taskId(taskId) // 根据任务id查询
				                .singleResult();
						taskname=task.getName()==null?"":task.getName();	
			        }*/
			        
			        List<JSONObject> clList=new ArrayList();
			        List<PageData> clListPage=new ArrayList();
			        if(pdDoc!=null&&pdDoc.get("id")!=null){
						object.put("success","true");
						pdDoc.put("tsdate", String.valueOf(pdDoc.get("tsdate")).substring(0, 10));
						if(pdDoc.get("czdate")!=null){
							pdDoc.put("czdate", String.valueOf(pdDoc.get("czdate")).substring(0, 19));
							
						}
						if(pdDoc.get("cjdate")!=null){
							//pdDoc.put("cjdate", String.valueOf(pdDoc.get("cjdate")).substring(0, 10));
						}
						if(pdDoc!=null&&pdDoc.getString("proc_id")!=null){
							/*
							 * 获取审批信息
							 */
						    String[] arrcl=null;
						    JSONObject pdcl=new JSONObject();
							PageData pduser=new PageData();
							pd.put("PROC_INST_ID_", pdDoc.getString("proc_id"));
							List<PageData>	varList = ruprocdefService.varList(pd);	
							String lasttime="";
							String taskId = json.getString("ID_");	//任务ID
							if(taskId!=null){
								Task task=taskService.createTaskQuery() // 创建任务查询
						                .taskId(taskId) // 根据任务id查询
						                .singleResult();
								if(task!=null){
									taskname=task.getName()==null?"":task.getName();
								}
								
							}
							pd.put("proc_id", pdDoc.getString("proc_id"));
							clListPage=ruprocdefService.getClList(pd, varList, taskname);
								
							for(int i=0;i<clListPage.size();i++){	
								pdcl=new JSONObject();
								pdcl.put("clman", clListPage.get(i).getString("clman"));
								pdcl.put("USERNAME",clListPage.get(i).getString("USERNAME"));
								
								pdcl.put("clcont", clListPage.get(i).getString("clcont"));
								pdcl.put("cldate", clListPage.get(i).getString("cldate"));
								pdcl.put("areaname", clListPage.get(i).getString("areaname"));
								pdcl.put("name", clListPage.get(i).getString("name"));
								
								clList.add(pdcl);
							}
							
							pdDoc.put("clList", clList.toString());
						}
						//System.out.println(pdDoc.toString());
						object.put("data",pdDoc);
						object.put("taskname",taskname);
					}
				}else if(cmd.equals("WorkorderDb")){  //待办根据提交的方法执行相应的操作，获取工单分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        List<PageData> dblist =new ArrayList();
			        List<PageData> alllist=new ArrayList();
		            String keywords = json.getString("keywords")==null?"":URLDecoder.decode( json.getString("keywords"),"utf-8");;				//关键词检索条件
		    		if(null != keywords && !"".equals(keywords)){
		    			pd.put("keywords", keywords.trim());
		    		}	
		    		
		    		if(null != json.getString("code") ){
		    			pd.put("code", json.getString("code"));
		    		}
		    		pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
		    		if(null != json.getString("tssource") ){
		    			pd.put("tssource", json.getString("tssource"));
		    		}	
		    		if(null != json.getString("tsman") ){
		    			pd.put("tsman", json.getString("tsman"));
		    		}	
		    		if(null != json.getString("cardid") ){
		    			pd.put("cardid", json.getString("cardid"));
		    		}
		    		if(null != json.getString("tscont") ){
		    			pd.put("tscont", json.getString("tscont"));
		    		}
		    		if(null != json.getString("tsdept") ){
		    			pd.put("tsdept", json.getString("tsdept"));
		    		}	
		    		
		    		if(null != json.getString("type") ){
		    			pd.put("types", json.getString("type"));
		    		}	
		    		
		    		if(null != json.getString("tstype") ){
		    			pd.put("tstype", json.getString("tstype"));
		    		}
		    		if(null != json.getString("bigtstype") ){
		    			pd.put("bigtstype", json.getString("bigtstype"));
		    		}
		    		
		    		if(null != json.getString("endreason") ){
		    			pd.put("endreason", json.getString("endreason"));
		    		}	
		    		
		    		if(pd_token!=null){
		    			String dwbm= pd_token.getString("dept");
		    			String ZXYH= pd_token.getString("ZXYH");
		    			if(dwbm.equals("350101")){
		    				dwbm="3501";
		    			}
		    			pd.put("dwbm", dwbm);
		    			
		    			
		    		}
		    		pd_token.put("USERNAME", pd_token.getString("ZXYH"));
		    		PageData user=new PageData();
		    		if(!username.equals("")){
		    			user=pd_token;
		    		}else{
		    			user=userService.getUserInfoByUsername(pd_token);
		    		}
		    		String role_id="";
		    		if(user!=null){
		    			role_id=user.getString("ROLE_ID");
		    		}
		    		PageData pd_js = new PageData();
		    		pd_js.put("ROLE_ID", role_id);
		    		PageData role=roleService.findObjectById(pd_js);
		    		
		    		PageData pdAll=workorderService.getAllList(pd_js, user, role,  pd_token.getString("ZXYH"));
		    		List<PageData>	varList =(List<PageData>) pdAll.get("varList");	//列出doc列表
					//System.out.println(varList);
		    		if(varList!=null&&varList.size()>0){
						pageIndex = json.getInteger("pageIndex");
			            pageSize = json.getInteger("pageSize");
						
						PageData pdPage=PageUtil.startPage(varList, pageIndex, pageSize);
						
						object.put("success","true");
						object.put("data",pdPage.get("pageList"));
						object.put("pageId",pdPage.get("pageNum"));
						object.put("pageCount",pdPage.get("pageCount"));
						object.put("recordCount",varList.size());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("WorkorderYb")){  //待办根据提交的方法执行相应的操作，获取工单分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        List<PageData> dblist =new ArrayList();
			        List<PageData> alllist=new ArrayList();
		            String keywords = json.getString("keywords")==null?"":URLDecoder.decode( json.getString("keywords"),"utf-8");;				//关键词检索条件
		    		if(null != keywords && !"".equals(keywords)){
		    			pd.put("keywords", keywords.trim());
		    		}	
		    		
		    		if(null != json.getString("code") ){
		    			pd.put("code", json.getString("code"));
		    		}
		    		pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
		    		if(null != json.getString("tssource") ){
		    			pd.put("tssource", json.getString("tssource"));
		    		}	
		    		if(null != json.getString("tsman") ){
		    			pd.put("tsman", json.getString("tsman"));
		    		}	
		    		if(null != json.getString("tsdept") ){
		    			pd.put("tsdept", json.getString("tsdept"));
		    		}	
		    		
		    		if(null != json.getString("type") ){
		    			pd.put("types", json.getString("type"));
		    		}	
		    		
		    		if(null != json.getString("tstype") ){
		    			pd.put("tstype", json.getString("tstype"));
		    		}
		    		if(null != json.getString("bigtstype") ){
		    			pd.put("bigtstype", json.getString("bigtstype"));
		    		}
		    		
		    		if(null != json.getString("endreason") ){
		    			pd.put("endreason", json.getString("endreason"));
		    		}	
		    		
		    		if(pd_token!=null){
		    			String dwbm= pd_token.getString("dept");
		    			String ZXYH= pd_token.getString("ZXYH");
		    			if(dwbm.equals("350101")){
		    				dwbm="3501";
		    			}
		    			pd.put("dwbm", dwbm);
		    			
		    		}
		    		pd_token.put("USERNAME", pd_token.getString("ZXYH"));
		    		PageData user=new PageData();
		    		if(!username.equals("")){
		    			user=pd_token;
		    		}else{
		    			user=userService.getUserInfoByUsername(pd_token);
		    		}
		    		pd.put("DWBM", user.getString("DWBM"));
		    		pd.put("USERNAME",username); 		//查询当前用户的任务(用户名查询)
		    		page.setPd(pd);
		    		pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		           
		    		list =ruprocdefService.hitasklist(page);	//列出历史任务列表;	//列出Rutask列表
		            
                	PageData dbpd=new PageData();
					if(list.size()>0){
						for(PageData pddoc:list){  
							pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 10));
							dbpd=new PageData();
							dbpd.put("tsdate", pddoc.getString("tsdate"));
							dbpd.put("tssourcename", pddoc.getString("tssourcename"));
							dbpd.put("tslevelname", pddoc.getString("tslevelname"));
							dbpd.put("clsx", pddoc.getString("clsx"));
							dbpd.put("proc_id", pddoc.getString("proc_id"));
							dbpd.put("id", pddoc.get("id"));
							dbpd.put("uid", pddoc.getString("uid"));
							dbpd.put("ID_", pddoc.getString("ID_"));
							alllist.add(dbpd);
						}
						object.put("success","true");
						object.put("data",alllist);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("WorkorderAzbDb")){  //待办根据提交的方法执行相应的操作，获取工单分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
		            String keywords = json.getString("keywords")==null?"":URLDecoder.decode( json.getString("keywords"),"utf-8");;				//关键词检索条件
		    		if(null != keywords && !"".equals(keywords)){
		    			pd.put("keywords", keywords.trim());
		    		}	
		    		
		    		if(null != json.getString("code") ){
		    			pd.put("code", json.getString("code"));
		    		}
		    		pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
		    		if(null != json.getString("tssource") ){
		    			pd.put("tssource", json.getString("tssource"));
		    		}	
		    		if(null != json.getString("tsman") ){
		    			pd.put("tsman", json.getString("tsman"));
		    		}	
		    		if(null != json.getString("tsdept") ){
		    			pd.put("tsdept", json.getString("tsdept"));
		    		}	
		    		
		    		if(null != json.getString("type") ){
		    			pd.put("types", json.getString("type"));
		    		}	
		    		
		    		if(null != json.getString("tstype") ){
		    			pd.put("tstype", json.getString("tstype"));
		    		}
		    		if(null != json.getString("bigtstype") ){
		    			pd.put("bigtstype", json.getString("bigtstype"));
		    		}
		    		
		    		if(null != json.getString("endreason") ){
		    			pd.put("endreason", json.getString("endreason"));
		    		}	
		    		if(null != json.getString("iszx") ){
		    			pd.put("iszx", json.getString("iszx"));
		    		}
		    		if(pd_token!=null){
		    			String dwbm= pd_token.getString("dept");
		    			String ZXYH= pd_token.getString("ZXYH");
		    			if(dwbm.equals("350101")){
		    				dwbm="3501";
		    			}
		    			pd.put("userdwbm", dwbm);
		    			
		    			
		    		}
		    		pd.put("type", "all");
		    		pd.put("dpf", "1");
		    		pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		    		list = workorderService.list(page);	//所有待办
		            
					if(list.size()>0){
						for(PageData pddoc:list){  
							pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 19));
							if(pddoc.get("czdate")!=null){
								pddoc.put("czdate", String.valueOf(pddoc.get("czdate")).substring(0, 19));
							}
							
						}
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("WorkorderSh")){  //根据提交的方法执行相应的操作，获取工单分页信息 
					String sfrom="";
					
					
					Map<String,Object> map = new LinkedHashMap<String, Object>();
				
					String newtsdept=json.getString("tsdept")==null?"":json.getString("tsdept");
				
					
					String PROC_INST_ID_=json.getString("PROC_INST_ID_");
					//String id=pd.getString("id");
					String ID_=json.getString("ID_")==null?"":json.getString("ID_");
					String dwbm=pd_token.getString("dept");
					String userid=pd_token.getString("ZXYH");
					String cfbm=json.getString("cfbm")==null?"":json.getString("cfbm");
					String doaction=json.getString("doaction")==null?"":json.getString("doaction");
					String msg=json.getString("msg")==null?"":json.getString("msg");
					String OPINION=json.getString("OPINION")==null?"派发工单":json.getString("OPINION");
					
					/*修改接收标记*/
					pd.put("ISRECEIVE", "2");
					pd.put("ID_", ID_);
					pd.put("username", userid);
					ruprocdefService.receive(pd);
						
					AsynTask myThread = new AsynTask();
				    myThread.setDwbm(dwbm);
				    myThread.setPid(PROC_INST_ID_);
				    myThread.setUserid(userid);
				    myThread.setOPINION(OPINION);
				    myThread.setCfbm(cfbm);
				    myThread.setDoaction(doaction);
				    myThread.setId("");
				    myThread.setID_(ID_);
				    myThread.setMsg(msg);
				    myThread.setPROC_INST_ID_(PROC_INST_ID_);
				    myThread.setTsdept(newtsdept);
				    Thread thread = new Thread(myThread);
			        thread.start();
					
					//旧的处理方法
					//ruprocdefService.deal("",PROC_INST_ID_, ID_, dwbm, userid, cfbm, doaction, msg, newtsdept, OPINION);
					
					
			        object.put("success","true");
			        object.put("msg","审核成功");
				}else if(cmd.equals("WorkorderFilelist")) {
			        pd.put("uid",json.get("uid"));
			        List<PageData> fileList=workorderService.findFileByuid(pd);
			        for(PageData pdServerLog:fileList){  
						pdServerLog.put("createdate", String.valueOf(pdServerLog.get("createdate")).substring(0, 19));
					}
					List<JSONObject> objlist=new ArrayList();
					PageData pdDoc=workorderService.findById(pd);
					
					if(fileList.size()>0){
			        	object.put("success","true");
					    object.put("data",fileList);
					    object.put("work",pdDoc);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
			       
				}else if(cmd.equals("WorkorderFileDel")) {
					pd.put("id",json.get("id"));
					workorderService.deleteFile(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
				}else if(cmd.equals("returnPf")) {
					pd.put("id",json.get("id"));
					PageData pdDoc=workorderService.findById(pd);
					if(pdDoc!=null&&"0".equals(pdDoc.getString("isreceive"))){
						workorderService.returnPf(pd);	
					}else if(pdDoc!=null&&"2".equals(pdDoc.getString("isreceive"))){
						workorderService.returnProc(pd);
						this.stopRunProcessInstance(pdDoc.getString("proc_id"));
					}
		        	object.put("success","true");
				    object.put("msg","删除成功");
				}else if(cmd.equals("WorkorderReceive")) {
					pd.put("ID_",json.get("ID_"));
					pd.put("ISRECEIVE",json.get("ISRECEIVE"));
					pd.put("ISRECEIVE",json.get("ISRECEIVE"));
					PageData pd_task=ruprocdefService.findTaskById(pd);
					if(pd_task!=null){
						pd.put("username", pd_token.getString("ZXYH"));
						ruprocdefService.receive(pd);
				        object.put("success","true");
					}else{
						object.put("success","false");
				        object.put("msg","无数据");
					}
				}else if(cmd.equals("WorkorderReplay")) {
					pd.put("ids",json.get("fileIds"));
					pd.put("cptemphasis",json.get("cptemphasis"));
					pd.put("surveyprocess",json.get("surveyprocess"));
					pd.put("isreconciliation",json.get("isreconciliation"));
					pd.put("disposeending",json.get("disposeending"));
					pd.put("uid",json.get("uid"));
					pd.put("code",json.get("code"));
					workorderService.editReplayFile(pd);
					workorderService.editReplay(pd);
					List<PageData> fileList=workorderService.findFileByuid(pd);
					String path="";
					JSONObject object_r=new JSONObject();
					JSONObject object_data=new JSONObject();
					JSONObject object_f=new JSONObject();
					JSONObject object_f_List=new JSONObject();
					
					JSONArray replayjson =new JSONArray();
					object_r.put("cptemphasis",json.get("cptemphasis"));
					object_r.put("surveyprocess",json.get("surveyprocess"));
					object_r.put("isreconciliation",json.get("isreconciliation"));
					object_r.put("disposeending",json.get("disposeending"));
					//pd.put("uid",json.get("uid"));
					object_r.put("cptid",json.get("code"));
					replayjson.add(object_r);
					
			        /*JSONArray attachmentjson =new JSONArray();
			          for(PageData pdServerLog:fileList){  
			        	path=request.getServletContext().getRealPath("uploadFiles/uploadFile/")+"/"+pdServerLog.getString("file");
			        	pdServerLog.put("path", path);
			        	pdServerLog.put("createdate", String.valueOf(pdServerLog.get("createdate")).substring(0, 19));
			        	object_f.put("fileName", pdServerLog.get("filename"));
			        	object_f.put("fileToString",FileStrUtil.fileToString(path));
			        	object_f.put("fileType", pdServerLog.get("ext"));
			        	attachmentjson.add(object_f);
			        }
			        object_r.put("attachmentList",attachmentjson.toString());*/
			       
			        Map<String, String> param = new HashMap<>();
			        //{"cptMapList":[{"cptid":"JD3A200409266","cptemphasis":"1","surveyprocess":"1","isreconciliation":"1","disposeending":"1"}]}
			        object_data.put("cptMapList",  replayjson.toString());
			        param.put("data", object_data.toString());
			        //Httpsend.doPostJson(url, json)
			        //System.out.println(object_data.toString());
			        String url="http://114.115.151.239:8090/cpt/getcptdata";
			        //提交请求
			        String result = Httpsend.doPost(url, param);
			        com.alibaba.fastjson.JSONObject data_result =  com.alibaba.fastjson.JSONObject.parseObject(result);
					pd.put("replayman",pd_token.getString("ZXYH")); 
					//System.out.println(result);
					if(data_result.getString("code").equals("1000")){ //返回值处理
						pd.put("replaysuccess","1");
						object.put("success","true");
					    object.put("msg","回复成功");
					}else{
						//com.alibaba.fastjson.JSONObject feedbackInfoMap =  com.alibaba.fastjson.JSONObject.parseObject(data_result.getString("feedbackInfoMap"));
						pd.put("replaymsg",data_result.getString("msg"));
						pd.put("replaysuccess","0");
						object.put("success","false");
					    object.put("msg",data_result.getString("msg"));
					}
					workorderService.editReplayReceive(pd);
			    			
		        	
				}else if(cmd.equals("WorkorderDeallist")){
					pd.put("proc_id", json.getString("proc_id"));
					pd.put("ID_", json.getString("ID_"));
					pd.put("PROC_INST_ID_", json.getString("proc_id"));
					if(json.getString("proc_id")!=null&&!json.getString("proc_id").equals("")){
						List<PageData>	varList = ruprocdefService.varList(pd);			
						String taskname="";
						if(json.getString("ID_")!=null){
							String taskId = pd.getString("ID_");	//任务ID
							
							if(taskId!=null){
								Task task=taskService.createTaskQuery() // 创建任务查询
						                .taskId(taskId) // 根据任务id查询
						                .singleResult();
								if(task!=null){
									taskname=task.getName()==null?"":task.getName();
								}
							}
						}
						
						List<PageData> listTemp=ruprocdefService.getClList(pd, varList, taskname);
						object.put("success","true");
					    object.put("data",listTemp);
					    object.put("taskname",taskname);
					}else{
						object.put("success","false");
				        object.put("msg","参数不能为空");
					}
					
					
				}else{
					object.put("success","false");
			        object.put("msg","暂无数据");
				}
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","操作异常");
		}	
        return object;
	}
	


	
	/*
	 * 获取编码
	 */
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
	
	
}
