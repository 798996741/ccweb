package com.xxgl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;






























import utils.FileStrUtil;

import com.alibaba.fastjson.JSON;
import com.fh.controller.activiti.AcStartController;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.MD5;
import com.fh.util.MyUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.xxgl.service.doctype.DoctypeManager;
import com.xxgl.service.mng.DocManager;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.utils.ResponseUtils;


/** 
 * 说明：工单管理
 * 创建人：351412933
 * 创建时间：2019-11-24
 */
@Controller
@RequestMapping(value="/workorder")
public class WorkorderController extends AcStartController {
	

	String menuUrl = "workorder/list.do"; //菜单地址(权限用)
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	
	
	//@Autowired
	@Resource(name="taskService")
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
	
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value="/save")
	public String save(@RequestParam(value = "file",required = false) MultipartFile[] files,HttpServletResponse response,HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增doc");
		User user=Jurisdiction.getLoginUser();
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_doc = new PageData();
		pd = this.getPageData();
		JSONObject object = new JSONObject();
		pd.put("workid", this.get32UUID());
		pd.put("id", request.getParameter("id"));
		pd.put("code", request.getParameter("code"));
		pd.put("tsdate", request.getParameter("tsdate"));
		pd.put("tssource", request.getParameter("tssource"));
		pd.put("tsman", request.getParameter("tsman"));
		pd.put("tstel", request.getParameter("tstel"));
		pd.put("tscont", request.getParameter("tscont"));
		pd.put("tslevel", request.getParameter("tslevel"));
		pd.put("tsdept", request.getParameter("tsdept"));
		pd.put("tstype", request.getParameter("tstype"));
		
	
		pd.put("tsclassify", request.getParameter("tsclassify"));
		pd.put("ishf", request.getParameter("ishf"));
		pd.put("endreason", request.getParameter("endreason"));
		pd.put("type", request.getParameter("type"));
		pd.put("cljd", request.getParameter("cljd"));
		pd.put("cardid", request.getParameter("cardid"));
		pd.put("cjdate", request.getParameter("cjdate"));
		pd.put("hbh", request.getParameter("hbh"));
		pd.put("clsx", request.getParameter("clsx"));
		pd.put("source",  request.getParameter("source")==null?"0":request.getParameter("source"));
		
		pd.put("type", "0");
		pd.put("depttype",request.getParameter("depttype"));
		pd.put("cardtype",request.getParameter("cardtype"));
		pd.put("tsqd",request.getParameter("tsqd"));
		pd.put("email",request.getParameter("email"));
		pd.put("tssq",request.getParameter("tssq"));
		pd.put("arrport",request.getParameter("arrport"));
		pd.put("deport",request.getParameter("deport"));
		
		PageData pd_workorder =null;
		if(pd!=null&&pd.getString("id")!=null&&!pd.getString("id").equals("")){
			pd_workorder = workorderService.findById(pd);
			pd.put("iszx", pd_workorder.getString("iszx"));	//是否坐席
		}else{
			pd.put("iszx", "0");	//是否坐席
		}
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String year = formatter.format(currentTime);
		PageData pd_year = new PageData();
		pd_year.put("year", year);
		pd_year = getNum(year);
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
		pd.put("czman",  Jurisdiction.getUsername());
		
		String pid ="";
		long min1=System.currentTimeMillis();
		if(request.getParameter("id")!=null&&!request.getParameter("id").equals("")){
			workorderService.edit(pd);
		}else{
			workorderService.save(pd);	
			if("1".equals(code_num)){
				workorderService.saveCode(pd_year);
			}else{
				pd_year.put("code_num", Integer.parseInt(code_num));
				workorderService.editCode(pd_year);
			}
			if(!pid.equals("")){
				workorderService.saveWorkProc(pd);
			}
		}
		
		
		if(request.getParameter("doaction")!=null&&request.getParameter("doaction").equals("0")){
			
			if (request.getParameter("endreason")==null||request.getParameter("endreason").equals("")) {
                pd.put("endreason",  "快速处理");
            }
            if (request.getParameter("cfbm")!=null&&!request.getParameter("cfbm").equals("")&&(request.getParameter("endreason")==null||request.getParameter("endreason").equals(""))) { //选择重复投诉
            	pd.put("endreason",  "重复投诉");
            }
			pd.put("dealman",  Jurisdiction.getUsername());
			pd.put("cljd",  user.getDWBM());
			pd.put("type", "4");
			workorderService.editCL(pd);
			if(pd_workorder!=null&&pd_workorder.getString("caseCode")!=null&&!pd_workorder.getString("caseCode").equals("")){
				PageData pd_casecode = workorderService.findDbByCasecode(pd_workorder);
				if(pd_casecode!=null){
					Date date=new Date();
					long timestamp=date.getTime(); 
					workorderService.sendAuditFinish(pd_workorder.getString("caseCode"), pd.getString("endreason"), pd_casecode.getString("postid"), pd.getString("endreason"), user.getNAME(), "", user.getPHONE(), String.valueOf(timestamp), "1");	
				}
			}
		}else if(request.getParameter("doaction")!=null&&request.getParameter("doaction").equals("5")){
			pd.put("dealman",  Jurisdiction.getUsername());
			pd.put("cljd",  user.getDWBM());
			pd.put("type", "5");
			workorderService.editCL(pd);
			if(pd_workorder!=null&&pd_workorder.getString("caseCode")!=null&&!pd_workorder.getString("caseCode").equals("")){
				PageData pd_casecode = workorderService.findDbByCasecode(pd_workorder);
				if(pd_casecode!=null){
					Date date=new Date();
					long timestamp=date.getTime(); 
					workorderService.sendReturn(pd_workorder.getString("caseCode"), pd_casecode.getString("userid"), pd_casecode.getString("postid"), pd.getString("endreason"), user.getDWBM(),"", "1");	
				}
			}	
			
		}else{
			
			
			if(request.getParameter("doaction")!=null&&request.getParameter("doaction").equals("1")){
				if(pd_workorder==null||(pd_workorder!=null&&pd_workorder.getString("pro_id")==null)){
					Map<String,Object> map = new LinkedHashMap<String, Object>();
					map.put("发起流程人", Jurisdiction.getUsername());			//当前用户的姓名
					map.put("投诉人", request.getParameter("tsman"));
					map.put("投诉人身份证号码", pd.getString("cardid"));
					map.put("投诉内容", pd.getString("tscont"));
					map.put("创建时间", Tools.date2Str(new Date()));
					//
					pd_doc.put("ROLE_NAME", "工单专员");
					List<PageData> roleList=userService.getUserByRoleName(pd_doc);
					String userstr="";
					String sendcont="";
					if(pd!=null){
						sendcont="您有一个投诉工单需要处理：投诉内容："+pd.getString("tscont");
					}
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
					map.put("USERNAME", userstr);
					String key="gdsp";
					
					pid = startProcessInstanceByKeyHasVariables(key,map);	//启动流程实例(公文-收文流程)通过KEY
					pd.put("proc_id", pid);
					//if(user.getDWBM()!=null&&user.getDWBM().equals("350101")){
						object.put("data",pid);
					//}
					pd.put("proc_id", pid);
					if(pd_workorder==null){
						pd.put("workid", pd.getString("workid"));
					}else{
						pd.put("workid", pd_workorder.getString("workid"));
					}
					pd.put("dealman", Jurisdiction.getUsername());
					pd.put("cljd", user.getDWBM());
					pd.put("type", "2");
					pd.put("ispf", "1");
					workorderService.editCL(pd);
					workorderService.editWorkproc(pd);
				}
			}
		}
		
		long min5=System.currentTimeMillis();
		//System.out.println("流程发起时间："+(min5-min1)+"毫秒");
		if(!pid.equals("")){
			String dwbm=user.getDWBM();
			String userid=user.getUSERNAME();
			String OPINION=pd.getString("OPINION")==null?"派发工单":pd.getString("OPINION");
			
			AsynTask myThread = new AsynTask();
		    myThread.setDwbm(dwbm);
		    myThread.setPid(pid);
		    myThread.setUserid(userid);
		    myThread.setOPINION(OPINION);
		    myThread.setCfbm("");
		    myThread.setDoaction("azb");
		    myThread.setId("");
		    myThread.setID_("");
		    myThread.setMsg("yes");
		    myThread.setPROC_INST_ID_(pid);
		    myThread.setTsdept("");
		    Thread thread = new Thread(myThread);
	        thread.start();
			//ruprocdefService.deal("", pid, "", dwbm, userid, "", "azb", "yes", "", OPINION);
		}
		
		object.put("success","true");
		ResponseUtils.renderJson(response, object.toString());
		
		//mv.addObject("msg","success_add");
		//mv.setViewName("redirect:/workorder/list.do?type=all");
		return null;
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
	
	
	


	@RequestMapping(value="/getFile")
	@ResponseBody
	public Object getFile() throws Exception{
		PageData pd = new PageData();	
		pd=this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> fileList=workorderService.findFileByuid(pd);
		JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(PageData pd_obj:fileList){
			object = new JSONObject();
			object.put("name", pd_obj.getString("name"));
			object.put("file", pd_obj.getString("file"));
			object.put("id",  pd_obj.get("id"));
			objlist.add(object);
		}
		//System.out.println(objlist);
		map.put("list", objlist);
		map.put("pd", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**文件查询列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/filelist")
	public ModelAndView filelist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表doc");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		User user=Jurisdiction.getLoginUser();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		URLDecoder urlDecoder = new URLDecoder();  
		
		List<PageData> fileList=workorderService.findFileByuid(pd);
		JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(PageData pd_obj:fileList){
			object = new JSONObject();
			object.put("name", pd_obj.getString("name"));
			object.put("file", pd_obj.getString("file"));
			object.put("id",  pd_obj.get("id"));
			objlist.add(object);
		}
		mv.setViewName("xxgl/workorder/file_list");
		mv.addObject("varList", fileList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	/**文件上传保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editFile")
	public ModelAndView editFile(@RequestParam(value = "file",required = false) MultipartFile[] files,HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增doc");
		User user=Jurisdiction.getLoginUser();
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_workorder = new PageData();
		pd = this.getPageData();
		pd.put("uid", request.getParameter("uid"));
		pd.put("filename", request.getParameter("filenames"));
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		for (MultipartFile i:files) {
			if(i.getSize()>0) {
				String filename = i.getOriginalFilename();
				//System.out.println(filename+"filename");
				String ext = MyUtils.getFileNameExt(filename);
				FileStrUtil  fileStrUtil=new FileStrUtil();
				if (!fileStrUtil.checkFile(filename)) {
                    //限制文件类型，请求转发到原始请求页面，并携带错误提示信息
					mv.addObject("msg","filetypeError");
                }else{ 
					String newFileName = dateString+"_"+System.currentTimeMillis() + "." + ext;
					
					String path=request.getServletContext().getRealPath("uploadFiles/uploadFile");
					//String newFilePath = MyUtils.transferToWithHash(i, path, newFileName);
					pd.put("file", newFileName);
					pd.put("name", filename);
					pd.put("ext", ext);
					pd.put("createman",  Jurisdiction.getUsername());
					File file = new File(path,newFileName);
					try {
						i.transferTo(file);
						workorderService.savefile(pd);
						mv.addObject("msg","success_add");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }	
			}
		}	
		
		
		mv.setViewName("redirect:/workorder/filelist.do?uid="+request.getParameter("uid")+"");
		return mv;
	}
	
	//下载文件
	@RequestMapping(value="/downLoadFile")
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response){
		
		PageData pd = new PageData();
		PageData pd_file = new PageData();
		pd = this.getPageData();
		
		
		FileInputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		try {
			pd_file=workorderService.findFileById(pd);
			String downloadFileName = pd_file.getString("name");
			String path=request.getServletContext().getRealPath("uploadFiles/uploadFile/")+"/"+pd_file.getString("file");
			System.out.println(path);
			file = new File(path);
			if (!file.exists()) {
				
				System.out.println("日报文件不存在，请重新导出数据");
			} else {
				inputStream = new FileInputStream(file);
				response.setContentType("application/txt;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;Filename=" + new String(downloadFileName.getBytes("GB2312"), "ISO8859-1"));// 设置下载后文件的名字、防止中文乱码

				outputStream = response.getOutputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(bytes)) > 0) {
					outputStream.write(bytes, 0, len);
				}
			}
		} catch (Exception e) {
			//log.error("文件下载异常：", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				//log.error("下载日报中关闭流异常 ：{} ", e);
			}
		}
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除workorder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		workorderService.delete(pd);
		out.write("success");
		out.close();
	}
	
	@RequestMapping(value="/deleteFile")
	public void deleteFile(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		workorderService.deleteFile(pd);
		out.write("success");
		out.close();
	}
	
	
	
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(@RequestParam(value = "file",required = false) MultipartFile[] files,HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增doc");
		User user=Jurisdiction.getLoginUser();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_workorder = new PageData();
		pd = this.getPageData();
		
	

		pd.put("id", request.getParameter("id"));
		pd.put("code", request.getParameter("code"));
		pd.put("uid", request.getParameter("uid"));
		
		pd.put("tsdate", request.getParameter("tsdate"));
		pd.put("tssource", request.getParameter("tssource"));
		pd.put("tsman", request.getParameter("tsman"));
		pd.put("tstel", request.getParameter("tstel"));
		pd.put("tscont", request.getParameter("tscont"));
		pd.put("tslevel", request.getParameter("tslevel"));
		pd.put("tsdept", request.getParameter("tsdept"));
		pd.put("tstype", request.getParameter("tstype"));
		pd.put("type", request.getParameter("type"));
		
	
		pd.put("tsclassify", request.getParameter("tsclassify"));
		pd.put("ishf", request.getParameter("ishf"));
		pd.put("endreason", request.getParameter("endreason"));
		pd.put("type", request.getParameter("type"));
		pd.put("cljd", request.getParameter("cljd"));
		pd.put("cardid", request.getParameter("cardid"));
		pd.put("cjdate", request.getParameter("cjdate"));
		pd.put("hbh", request.getParameter("hbh"));
		pd.put("clsx", request.getParameter("clsx"));
		pd.put("czman",  Jurisdiction.getUsername());
		
		mv.addObject("msg","success_add");
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
/*		for (MultipartFile i:files) {
			if(i.getSize()>0) {
				String filename = i.getOriginalFilename();
				//System.out.println(filename+"filename");
				String ext = MyUtils.getFileNameExt(filename);
				String newFileName = dateString+"_"+System.currentTimeMillis() + "." + ext;
				
				String path=request.getServletContext().getRealPath("uploadFiles/uploadFile");
				//String newFilePath = MyUtils.transferToWithHash(i, path, newFileName);
				pd.put("file", newFileName);
				pd.put("name", filename);
				pd.put("ext", ext);
				File file = new File(path,newFileName);
				try {
					i.transferTo(file);
					workorderService.savefile(pd);
					
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	*/
		workorderService.edit(pd);
		pd_workorder = workorderService.findById(pd);
		if(pd.getString("endreason")!=null&&!pd.getString("endreason").equals("")){
			pd.put("dealman",  Jurisdiction.getUsername());
			pd.put("cljd",  user.getDWBM());
			pd.put("type", "4");
			//System.out.println( user.getDWBM()+"bm");
			workorderService.editCL(pd);
			if(pd_workorder!=null&&pd_workorder.getString("caseCode")!=null&&!pd_workorder.getString("caseCode").equals("")){
				PageData pd_casecode = workorderService.findDbByCasecode(pd_workorder);
				if(pd_casecode!=null){
					Date date=new Date();
					long timestamp=date.getTime(); 
					workorderService.sendAuditFinish(pd_workorder.getString("caseCode"), pd.getString("endreason"), pd_casecode.getString("postid"), pd.getString("endreason"), user.getNAME(), "", user.getPHONE(), String.valueOf(timestamp), "1");	
				}
			}
		}else{
			
			if(pd_workorder!=null&&pd_workorder.getString("pro_id")==null&&request.getParameter("doaction").equals("1")){
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				map.put("发起流程人", Jurisdiction.getUsername());			//当前用户的姓名
				map.put("投诉人", request.getParameter("tsman"));
				map.put("投诉人身份证号码", pd.getString("cardid"));
				map.put("投诉内容", pd.getString("tscont"));
				map.put("创建时间", Tools.date2Str(new Date()));
				map.put("USERNAME", Jurisdiction.getUsername());
				String key="gdsp";
				String pid = startProcessInstanceByKeyHasVariables(key,map);	//启动流程实例(公文-收文流程)通过KEY
				//System.out.println(pid+"pid");
				pd.put("proc_id", pid);
				pd.put("workid", pd_workorder.getString("workid"));
				workorderService.editWorkproc(pd);
			}
			
			
			//RuTaskController  ruTaskController=new RuTaskController();
			//ruTaskController.Cl(pid, request.getParameter("tsdept"));

		}
		mv.addObject("msg","success_add");
		mv.setViewName("save_result");
		//mv.setViewName("redirect:/workorder/list.do?type=all");
		return mv;
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表doc");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		User user=Jurisdiction.getLoginUser();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		URLDecoder urlDecoder = new URLDecoder();  
		//System.out.println( pd.getString("keywords"));
		String keywords = pd.getString("keywords")==null?"":URLDecoder.decode( pd.getString("keywords"),"utf-8");;				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}	
		
		if(null != pd.getString("codes") ){
			pd.put("code", pd.getString("codes"));
		}	
		if(null != pd.getString("istargets") ){
			pd.put("istarget", pd.getString("istargets"));
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
		
		if(null != pd.getString("types")&&! "".equals(pd.getString("types"))){
			pd.put("types", pd.getString("types"));
		}	
		
		if(null != pd.getString("tstypes")&&! "".equals(pd.getString("tstypes"))){
			pd.put("tstype", pd.getString("tstypes"));
		}
		if(null != pd.getString("bigtstypes")&&!"".equals(pd.getString("bigtstypes")) ){
			pd.put("bigtstype", pd.getString("bigtstypes"));
		}
		
		if(null != pd.getString("tsclassifys")&&!"".equals(pd.getString("tsclassifys")) ){
			pd.put("tsclassify", pd.getString("tsclassifys"));
		}
		
		if(null != pd.getString("endreasons") &&!"".equals(pd.getString("endreasons")) ){
			pd.put("endreason", pd.getString("endreasons"));
		}
		
		if(pd.getString("dpf")!=null &&!"".equals(pd.getString("dpf")) ){
			pd.put("dpf", pd.getString("dpf"));
			pd.put("ispf","0");
		}
		//System.out.println(pd.getString("dpf")+"pf");
		if(user!=null&&user.getDWBM()!=null){
			String dwbm= user.getDWBM();
			if(dwbm.equals("350101")){
				dwbm="3501";
			}
			pd.put("dwbm", dwbm);
		}
		page.setShowCount(1000000);
		page.setPd(pd);
	
		List<PageData>	varList = workorderService.list(page);	//列出doc列表
	
		pd.put("parentId", "9ed978b4a4674adb9c08f45d2e2d9813");
		List<PageData> tssourceList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tssourceList", tssourceList);
	    pd.put("AREA_LEVEL", "3");
	    pd.put("isuse", "1");
	    List<PageData> tsbmList=areamanageService.listAll(pd);
	    mv.addObject("tsdeptList", tsbmList);
	    pd.put("parentId", "1b303f7026934f68a6bd1ea01433db19");
	    List<PageData> tstypeList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tstypeLists", tstypeList);
	    
	    pd.put("parentId", "55b155b57041478c9c3c15848e8d0225");
	    List<PageData> tsclassifys=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tsclassifyList", tsclassifys);
	
		mv.setViewName("xxgl/workorder/workorder_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/gdcenter")
	public ModelAndView gdcenter(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"工单中心");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		URLDecoder urlDecoder = new URLDecoder();  
		User loginuser=Jurisdiction.getLoginUser();
		PageData pd_token = new PageData();
		PageData pd_stoken = new PageData();
		String username=loginuser.getUSERNAME();
		pd_stoken.put("USERNAME", username);
		pd_token=userService.findByUsername(pd_stoken);
		if(loginuser!=null){
			String dwbm=loginuser.getDWBM();
			String ZXYH= loginuser.getUSERNAME();
			if(dwbm.equals("350101")){
				dwbm="3501";
			}
			pd.put("dwbm", dwbm);
		}
		pd_token.put("USERNAME", pd_token.getString("ZXYH"));
		PageData user=new PageData();
		user=pd_token;
		String role_id=user.getString("ROLE_ID");
		PageData pd_js = new PageData();
		pd_js.put("ROLE_ID", role_id);
		PageData role=roleService.findObjectById(pd_js);//获取用户角色
		boolean boo=false;
		
		if(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("工单专员")||role.getString("ROLE_NAME").equals("部门领导")||role.getString("ROLE_NAME").equals("部门处理人员")||role.getString("ROLE_NAME").equals("部门领导"))||role.getString("ROLE_NAME").equals("投诉坐席")||role.getString("ROLE_NAME").equals("科室处理人员")){
			boo=true; //判断是否有处理、审核、派发的权限
		}
		
		
		Calendar cale = null;
	    cale = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    String firstday, lastday;
	    // 获取本月的第一天
	    cale = Calendar.getInstance();
	    cale.add(Calendar.MONTH, 0);
	    cale.set(Calendar.DAY_OF_MONTH, 1);
	    firstday = format.format(cale.getTime());
	    
		if(boo){
			PageData pdAll=workorderService.getAllList(pd, user, role, username);
			
			pd.put("USERNAME", Jurisdiction.getUsername()); 				//查询当前用户的任务(用户名查询)
			pd.put("DWBM",pd_token.getString("DWBM")); 				//查询当前用户的任务(角色编码查询)
			if(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("工单专员")||role.getString("ROLE_NAME").equals("部门领导")||role.getString("ROLE_NAME").equals("部门处理人员")||role.getString("ROLE_NAME").equals("部门领导"))||role.getString("ROLE_NAME").equals("投诉坐席")){
				pd.put("lastStart", firstday);
			}
			
			page.setShowCount(1000000);
			page.setPd(pd);
			List<PageData>	varList = ruprocdefService.hitasklist(page);	//列出doc列表
			
			mv.addObject("csnum", pdAll.get("csnum"));
			mv.addObject("wclCount", pdAll.get("wclCount"));
			mv.addObject("clzCount",pdAll.get("clzCount"));
			mv.addObject("dshCount", pdAll.get("dshCount"));
			mv.addObject("yclCount", varList.size());
			mv.addObject("varList", pdAll.get("varList"));
		}else{
			
			PageData pd_jl=workorderService.gdCount(pd);
			int wcl=Integer.parseInt(String.valueOf(pd_jl.get("wcl")==null?0:pd_jl.get("wcl")))+Integer.parseInt(String.valueOf(pd_jl.get("clz")==null?0:pd_jl.get("clz")));
			mv.addObject("wclCount", wcl);
			int clz=wcl+Integer.parseInt(String.valueOf(pd_jl.get("dsh")==null?0:pd_jl.get("dsh")));
			mv.addObject("clzCount", clz);
			mv.addObject("dshCount", pd_jl.get("dsh")==null?0:pd_jl.get("dsh"));
			mv.addObject("yclCount", pd_jl.get("js")==null?0:pd_jl.get("js"));
			mv.addObject("csnum", "0");
			page.setShowCount(1000000);
			page.setPd(pd);
			List<PageData>	varList = workorderService.list(page);	//
			String type="",endtime="";
			List<PageData> alllist=new ArrayList();
			for(PageData pdgd:varList){
				type=pdgd.getString("type")==null?"":pdgd.getString("type");
				if(type.equals("4")){
					endtime=String.valueOf(pdgd.get("endtime")==null?"":pdgd.get("endtime")).substring(0, 10);
					if(endtime.compareTo(firstday)>=0){
						alllist.add(pdgd);
					}	
				}else{
					alllist.add(pdgd);
				}
			}
			
			
			mv.addObject("varList", alllist);
		}
		mv.setViewName("xxgl/workorder/workorder_center");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	@RequestMapping(value="/getTstype")
	@ResponseBody
	public Object getTstype() throws Exception{
		PageData pd = new PageData();	
		pd=this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Dictionaries> tstypeList=dictionariesService.listAllDict(pd.getString("bigtstype"));
	    //mv.addObject("tssourceList", tssourceList);
		JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(Dictionaries pd_obj:tstypeList){
			object = new JSONObject();
			object.put("DICTIONARIES_ID", pd_obj.getDICTIONARIES_ID());
			object.put("NAME", pd_obj.getNAME());
			objlist.add(object);
		}
		
		map.put("list", objlist);
		return AppUtil.returnObject(pd, map);
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String uid=this.get32UUID();
		pd.put("uid", uid);	//主键
		
		
		pd.put("parentId", "2b0ee94d0db04ba6b244cedf436ab57b");
		List<PageData> tslevelList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tslevelList", tslevelList);
	    
	    pd.put("parentId", "9ed978b4a4674adb9c08f45d2e2d9813");
		List<PageData> tssourceList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tssourceList", tssourceList);
	    pd.put("parentId", "1b303f7026934f68a6bd1ea01433db19");
	    List<PageData> tstypeList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tstypeList", tstypeList);
	  
	    pd.put("isuse", "1");
	    pd.put("NOAREA_LEAVEL", "4");
		JSONArray arr = JSONArray.fromObject(areamanageService.listAll(pd));
		String json_dept = arr.toString();
		json_dept = json_dept.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
		//System.out.println(json);
		mv.addObject("zTreeNodes_dept", json_dept);
		
		
		pd.put("parentId", "55b155b57041478c9c3c15848e8d0225");
		List<PageData> tsclassifyList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tsclassifyList", tsclassifyList);
	    
	    pd.put("parentId", "02a79c10a8194fe89336c41eafc8cc32");
  		List<PageData> cardtypeList =dictionariesService.listAllDictByParentId(pd);
  		mv.addObject("cardtypeList", cardtypeList);    
  		
	    pd.put("parentId", "13efaad6a5694b9eb7d3fd84593c3666");
  		List<PageData> tsqdList =dictionariesService.listAllDictByParentId(pd);
  		mv.addObject("tsqdList", tsqdList);    	
	    
	    
		mv.setViewName("xxgl/workorder/workorder_edit");

		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("types",pd.getString("type"));
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("types",pd.getString("type"));
		mv.addObject("search",pd.getString("search"));
		String doaction=pd.getString("doaction");
		pd = workorderService.findById(pd);	//根据ID读取
		if(pd==null){
			pd = this.getPageData();
		}
		//JSONArray arr = JSONArray.fromObject(doctypeService.listAllDict("0",""));
		if(pd!=null&&pd.get("tsdate")!=null){
			pd.put("tsdate", String.valueOf(pd.get("tsdate")).substring(0, 10));
		}
		if(pd!=null&&pd.get("cardid")!=null&&pd.getString("cardid").length()==18){
			String cardid=pd.getString("cardid");
			pd.put("cardid", cardid.replace(cardid.substring(6, 14), "********"));
		}
		
		pd.put("parentId", "2b0ee94d0db04ba6b244cedf436ab57b");
		List<PageData> tslevelList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tslevelList", tslevelList);
	    
	    pd.put("parentId", "9ed978b4a4674adb9c08f45d2e2d9813");
		List<PageData> tssourceList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tssourceList", tssourceList);
	    pd.put("parentId", "1b303f7026934f68a6bd1ea01433db19");
	    List<PageData> tstypeList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tstypeList", tstypeList);
	  
	    pd.put("isuse", "1");
	    pd.put("NOAREA_LEAVEL", "4");
		JSONArray arr = JSONArray.fromObject(areamanageService.listAll(pd));
		String json_dept = arr.toString();
		json_dept = json_dept.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
		//System.out.println(json);
		mv.addObject("zTreeNodes_dept", json_dept);
		
		
		pd.put("parentId", "55b155b57041478c9c3c15848e8d0225");
		List<PageData> tsclassifyList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tsclassifyList", tsclassifyList);
	    
	    pd.put("parentId", "02a79c10a8194fe89336c41eafc8cc32");
  		List<PageData> cardtypeList =dictionariesService.listAllDictByParentId(pd);
  		mv.addObject("cardtypeList", cardtypeList);    
  		
	    pd.put("parentId", "13efaad6a5694b9eb7d3fd84593c3666");
  		List<PageData> tsqdList =dictionariesService.listAllDictByParentId(pd);
  		mv.addObject("tsqdList", tsqdList);  
		
	    List<Dictionaries> tsbmList=dictionariesService.listAllDict("7a78242921d34b5bbc0e24e6f6b309c0");
	   
	    
	    for(Dictionaries pd_zxz:tsbmList){
	    	//System.out.println(pd.getString("tsbm"));
			if(pd.getString("tsdept")!=null&&pd.getString("tsdept").indexOf(pd_zxz.getDICTIONARIES_ID())>=0){
				pd_zxz.setChecked("checked");
			}
			
		}
	    mv.addObject("tsdeptList", tsbmList);
	    List<PageData>	clList=new ArrayList();
		if(pd!=null&&pd.getString("proc_id")!=null&&!pd.getString("proc_id").equals("")){
			/*
			 * 获取审批信息
			 */
			
		    String[] arrcl=null;
			PageData pdcl=new PageData();
			PageData pduser=new PageData();
			pd.put("PROC_INST_ID_", pd.getString("proc_id"));
			List<PageData>	varList = ruprocdefService.varList(pd);	
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
			
			clList=ruprocdefService.getClList(pd, varList, taskname);
			
			
		}
		mv.addObject("clList", clList);
		mv.addObject("clCount", clList.size());
	   // System.out.println(clList+"clList");
	    
	    mv.setViewName("xxgl/workorder/workorder_edit");
	    pd.put("doaction", doaction);
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public String exportExcel(HttpServletResponse response) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出doc到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		User user=Jurisdiction.getLoginUser();
		
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		URLDecoder urlDecoder = new URLDecoder();  
		//System.out.println( pd.getString("keywords"));
		String keywords = pd.getString("keywords")==null?"":URLDecoder.decode( pd.getString("keywords"),"utf-8");;				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}	
		
		if(null != pd.getString("codes") ){
			pd.put("code", pd.getString("codes"));
		}	
		if(null != pd.getString("istargets") ){
			pd.put("istarget", pd.getString("istargets"));
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
		
		if(null != pd.getString("types")&&!pd.getString("types").equals("") ){
			pd.put("types", pd.getString("types"));
		}	
		
		if(null != pd.getString("tstypes")&&!pd.getString("tstypes").equals("") ){
			pd.put("tstype", pd.getString("tstypes"));
		}
		if(null != pd.getString("bigtstypes")&&!pd.getString("bigtstypes").equals("") ){
			pd.put("bigtstype", pd.getString("bigtstypes"));
		}
		
		if(null != pd.getString("tsclassifys")&&!pd.getString("tsclassifys").equals("") ){
			pd.put("tsclassify", pd.getString("tsclassifys"));
		}
		
		if(null != pd.getString("endreasons") &&!pd.getString("endreasons").equals("") ){
			pd.put("endreason", pd.getString("endreasons"));
		}
		
		if(pd.getString("dpf")!=null &&!pd.getString("dpf").equals("") ){
			pd.put("dpf", pd.getString("dpf"));
			pd.put("ispf","0");
		}
		//System.out.println(pd.getString("dpf")+"pf");
		if(user!=null&&user.getDWBM()!=null){
			String dwbm= user.getDWBM();
			if(dwbm.equals("350101")){
				dwbm="3501";
			}
			pd.put("dwbm", dwbm);
			
		}
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		
		dataMap.put("titles", titles);
		List<PageData> varOList = workorderService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		String ishf="",type="";
		
		try
	    {
			Date date = new Date();
	      String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
	      OutputStream os = response.getOutputStream();
	      response.reset();
	      response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
	      response.setContentType("application/msexcel");
	    
	      WritableWorkbook wbook = Workbook.createWorkbook(os);
	      WritableSheet wsheet = wbook.createSheet(formatFileName, 0);
	      

	      WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
	      WritableCellFormat wcfFC = new WritableCellFormat(wfont);
	      wcfFC.setBackground(Colour.AQUA);
	      wfont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
	      wcfFC = new WritableCellFormat(wfont);
	    
	      wsheet.addCell(new Label(0, 0, "投诉编号"));
	      wsheet.addCell(new Label(1, 0, "日期"));
	      wsheet.addCell(new Label(2, 0, "投诉来源"));
	      wsheet.addCell(new Label(3, 0, "投诉人"));
	      wsheet.addCell(new Label(4, 0, "联系方式"));
	      wsheet.addCell(new Label(5, 0, "投诉内容"));
	      wsheet.addCell(new Label(6, 0, "投诉等级"));
	      wsheet.addCell(new Label(7, 0, "投诉部门"));
	      wsheet.addCell(new Label(8, 0, "部门分类"));
	      wsheet.addCell(new Label(9, 0, "投诉类别（细项）"));
	      wsheet.addCell(new Label(10, 0, "投诉分类"));
	      wsheet.addCell(new Label(11, 0, "受理人"));
	      wsheet.addCell(new Label(12, 0, "顾客回访情况"));
	      wsheet.addCell(new Label(13, 0, "结束时间"));
	      wsheet.addCell(new Label(14, 0, "处理节点"));
	      wsheet.addCell(new Label(15, 0, "工单状态"));
	      wsheet.addCell(new Label(16, 0, "结束原因"));
      
	 
	      int num = 1;
	      
	      for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				
				wsheet.addCell(new Label(0, num, varOList.get(i).getString("code")));	    //1
				wsheet.addCell(new Label(1, num, String.valueOf(varOList.get(i).get("tsdate"))));	    //2
				wsheet.addCell(new Label(2, num, varOList.get(i).getString("tssourcename")));	    //3
				wsheet.addCell(new Label(3, num, varOList.get(i).getString("tsman")));	    //4
				wsheet.addCell(new Label(4, num, varOList.get(i).getString("tstel")));	    //5
				wsheet.addCell(new Label(5, num, varOList.get(i).getString("tscont")));	    //6
				wsheet.addCell(new Label(6, num, varOList.get(i).getString("tslevelname")));	    //7
				wsheet.addCell(new Label(7, num, varOList.get(i).getString("tsdeptname")));	    //8
				wsheet.addCell(new Label(8, num, varOList.get(i).getString("depttype")));	 
				wsheet.addCell(new Label(9, num, varOList.get(i).getString("tstypename")));	 
				wsheet.addCell(new Label(10, num, varOList.get(i).getString("tsclassifyname")));	   
				wsheet.addCell(new Label(11, num, varOList.get(i).getString("dealman")));	
				ishf=varOList.get(i).getString("ishf")==null?"":varOList.get(i).getString("ishf");
				if(ishf.equals("1")){
					ishf="是";
				}else{
					ishf="否";
				}
				wsheet.addCell(new Label(12, num, ishf));	  
				wsheet.addCell(new Label(13, num, String.valueOf(varOList.get(i).get("endtime")==null?"":varOList.get(i).get("endtime"))));	  
				wsheet.addCell(new Label(14, num, varOList.get(i).getString("cljdname")));
				type=varOList.get(i).getString("type");
				if(type.equals("0")){
					type="待反馈";
				}else if(type.equals("1")){
					type="工单已分派";
				}else if(type.equals("2")){
					type="正常为您处理";
				}else if(type.equals("3")){
					type="正常为您处理";
				}else if(type.equals("4")){
					type="工单已关闭";
				}else if(type.equals("5")){
					type="已退回";
				}
				wsheet.addCell(new Label(15, num,type));	  
				wsheet.addCell(new Label(16, num, varOList.get(i).getString("endreason")));
				num++;
			}
	      wbook.write();
	      wbook.close();
	      os.close();
	      
	      return null;
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	      System.out.println(ex);
	    }
	    return null;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	 /**
	    * @author: kaka
	    * @date:2017/4/26 17:24
	    * @params: [dkey 流程定义的key]
	    * @return:java.util.List<org.activiti.engine.impl.pvm.process.ActivityImpl>
	    * @描述: 根据流程定义的key获取流程定义所有的activity
	    *
	    * 释义：在设计流程时每一个组件在Activiti中都可以称之为——Activity，部署流程时引擎把XML文件保存到数据库；
	    *      当启动流程、完成任务时会从数据库读取XML并转换为Java对象，如果想在处理任务时获取任务的一些配置，例如某个任务配置了哪些监听器或者条件Flow配置了什么条件表达式，可以获取具体的Activity。
	    */
	//存放流程对应的所有activity
	 // 创建流程引擎 processEngine
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
 // 创建仓库服务对象
 	private RepositoryService repositoryService=processEngine.getRepositoryService();
	
	public void QueryProcessDefine(){
	     // 使用服务对象创建需要查询的对象
	   ProcessDefinitionQuery definitionQuery=repositoryService.createProcessDefinitionQuery();
	    // 添加查看参数
	   definitionQuery.processDefinitionKey("gdsp").orderByProcessDefinitionVersion().desc();
	    List<ProcessDefinition> pds=definitionQuery.list();
	    for (ProcessDefinition processDefinition : pds) {
	      
	      System.out.println("id:"+processDefinition.getId()+"name:"+processDefinition.getName()+"key:"+processDefinition.getKey()+"versioin:"+processDefinition.getVersion());
	      ProcessDefinitionImpl pdImpl=(ProcessDefinitionImpl) repositoryService.getProcessDefinition(processDefinition.getId());
	      System.out.println(pdImpl.getActivities());
	      //id:myProcess:1:4name:My processkey:myProcessversioin:1
	   //   [Activity(usertask1), Activity(usertask2), Activity(startevent1), Activity(endevent1)]
	    }
	}
	/*
	 * 设置是否纳入指标
	 */
	@RequestMapping(value="/target")
	@ResponseBody()
	public String receive(HttpServletResponse response,HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		com.alibaba.fastjson.JSONObject object=new com.alibaba.fastjson.JSONObject();
		try{
			String username=Jurisdiction.getUsername();
			PageData pd_token=workorderService.findById(pd);
			if(pd_token!=null){
				pd.put("username", username);
				workorderService.target(pd);
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
	
	
	/**
	 * 待派发数据接收
	 */
	@RequestMapping(value="/workReceive")
	@ResponseBody()
	public String updateWorkReceive(HttpServletResponse response,HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		com.alibaba.fastjson.JSONObject object=new com.alibaba.fastjson.JSONObject();
		try{
			String username=Jurisdiction.getUsername();
			PageData pd_token=workorderService.findById(pd);
			if(pd_token!=null){
				pd.put("username", username);
				workorderService.updateWorkReceive(pd);
		        object.put("success","true");
			    object.put("msg","接收成功");
			}else{
				object.put("success","false");
		        object.put("msg","暂无数据");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","操作异常"+e.getMessage());
		}	
		ResponseUtils.renderJson(response, object.toString());
		return null;
	}
	
	
	/**
	 * 撤回上一级
	 * @date 2020-11-27
	 * @author huangjianling
	 */
	@RequestMapping(value="/returnProc")
	@ResponseBody()
	public String returnProc(HttpServletResponse response,HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		com.alibaba.fastjson.JSONObject object=new com.alibaba.fastjson.JSONObject();
		try{
			String username=Jurisdiction.getUsername();
			PageData pd_token=workorderService.findById(pd);
			if(pd_token!=null){
				workorderService.returnProc(pd);
				
				if(pd.getString("proc_id")!=null){
					this.stopRunProcessInstance(pd.getString("proc_id"));
				}
		        object.put("success","true");
			    object.put("msg","撤回成功");
			}else{
				object.put("success","false");
		        object.put("msg","id不存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","撤回异常");
		}	
		ResponseUtils.renderJson(response, object.toString());
		return null;
	}
	
	/**
	 * 通过任务id跳转到上一步任务
	 * @date 2020-11-27
	 * @author huangjianling
	 */
	@RequestMapping(value="/backProc")
	@ResponseBody()
	public String backProc(HttpServletResponse response,HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		com.alibaba.fastjson.JSONObject object=new com.alibaba.fastjson.JSONObject();
		try{
			PageData pd_token=workorderService.findById(pd);
			if(pd_token!=null){
				workorderService.returnProc(pd);
				if(pd.getString("proc_id")!=null){
					this.stopRunProcessInstance(pd.getString("proc_id"));
				}
		        object.put("success","true");
			    object.put("msg","撤回成功");
			}else{
				object.put("success","false");
		        object.put("msg","id不存在");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","撤回异常");
		}	
		ResponseUtils.renderJson(response, object.toString());
		return null;
	}
	
	
	

	@RequestMapping(value="/getCardId")
	@ResponseBody()
	public String getCardId(HttpServletResponse response,HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData result = workorderService.findById(pd);
		String cardid = result.getString("cardid");
		if(cardid == null || "".equals(cardid)){
			return "";
		}
		JSONObject json = new JSONObject();
		json.put("cardid", cardid);
		return json.toString();
	}



	
	public static void main(String[] args){
		WorkorderController workorderController=new WorkorderController();
		workorderController.QueryProcessDefine();
	}
}
