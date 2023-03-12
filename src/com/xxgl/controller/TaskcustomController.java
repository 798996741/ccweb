package com.xxgl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.xxgl.utils.BitConverter;
import com.xxgl.utils.ResponseUtils;
import com.xxgl.utils.WDWUtil;
import com.xxgl.entity.DataBean;
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.CustomtemplateManager;
import com.xxgl.service.mng.CustomtemplatefieldManager;
import com.xxgl.service.mng.ExetaskManager;
import com.xxgl.service.mng.FieldManager;
import com.xxgl.service.mng.TaskcustomManager;
import com.xxgl.service.mng.TaskuserManager;
import com.xxgl.service.mng.TemplateManager;
import com.xxgl.service.mng.ZxlbManager;
import com.xxgl.service.mng.ZxzManager;


/** 
 * 说明：参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 */
@Controller
@RequestMapping(value="/taskcustom")
public class TaskcustomController extends BaseController {

	String menuUrl = "taskcustom/list.do"; //菜单地址(权限用)

	@Resource(name="taskuserService")
	private TaskuserManager taskuserService;
	
	@Resource(name="exetaskService")
	private ExetaskManager exetaskService;
	
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;
	
	@Resource(name="zxzService")
	private ZxzManager zxzService;

	@Resource(name="taskcustomService")
	private TaskcustomManager taskcustomService;

	@Resource(name="caseService")
	private CaseManager caseService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="templateService")
	private TemplateManager templateService;
	@Resource(name="customtemplateService")
	private CustomtemplateManager customtemplateService;

	@Resource(name="customtemplatefieldService")
	private CustomtemplatefieldManager customtemplatefieldService;
	@Resource(name="fieldService")
	private FieldManager fieldService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增task");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();

		pd = this.getPageData();
		String TEMPLATE_ID=pd.getString("TEMPLATE_ID");
		String id=this.get32UUID();
		pd.put("ID", id);	//主键
		//pd.put("CZMAN",  Jurisdiction.getUsername());
		pd.put("CREATEMAN",  Jurisdiction.getUsername());
		taskuserService.save(pd);
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		pd_new.put("ID", TEMPLATE_ID);
		pd_new=templateService.findById(pd_new);
		//page.setPd(pd);
		pd_new_cus.put("CUS_TEMP_ID", pd_new.getString("CUSTOM_TEMPLATE_ID"));
		List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表

		//动态创建表
		String tableName="t_"+TaskcustomController.GuidToLongID(id);

		String tablestr="";
		tablestr="CREATE TABLE "+tableName+" (";
		tablestr=tablestr+"id varchar2(50) NOT NULL,";
		PageData pd_new_filed = new PageData();
		for(int i=0;i<varList.size();i++){
			pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
			pd_new_filed=fieldService.findById(pd_new_filed);
			if(pd_new_filed.getString("FIELDTYPE")!=null&&pd_new_filed.getString("FIELDTYPE").equals("date")){
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+" null ,";
			}else if(pd_new_filed.getString("FIELDNUM")==null||pd_new_filed.getString("FIELDNUM").equals("")){
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+"  null ,";
			}else{
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+"("+pd_new_filed.getString("FIELDNUM")+")  null ,";
			}
		}
		tablestr=tablestr+"fpman varchar2(50) NOT NULL,";
		tablestr=tablestr+"zxman varchar2(50) NOT NULL,";
		tablestr=tablestr+"hfwj varchar2(50), ";
		tablestr=tablestr+"hfdate date,";
		tablestr=tablestr+"hfresult varchar2(50), ";
		tablestr=tablestr+"createdate date,";
		tablestr=tablestr+"createman varchar2(50), ";
		tablestr=tablestr+"hfremark varchar2(50), ";
		tablestr=tablestr+"hfjl varchar2(50), ";
		tablestr=tablestr+"PRIMARY KEY (id)) ";
		//System.out.println("tablestr:"+tablestr);
		pd.put("ID", id);
		pd.put("TABLENAME", tableName);
		pd.put("TABLESTR", tablestr);

		taskuserService.createNewTable(pd);//创建表
		taskuserService.editTableName(pd);//修改表

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	
	@RequestMapping(value="/customtablesave")
	public ModelAndView customtablesave() throws Exception{
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();

		pd = this.getPageData();
		//pd.put("CZMAN",  Jurisdiction.getUsername());
		pd.put("CREATEMAN",  Jurisdiction.getUsername());
		
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		//page.setPd(pd);
		pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
		List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表

		
		String tablestr="";
		PageData pd_new_filed = new PageData();
		for(int i=0;i<varList.size();i++){
			pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
			pd_new_filed=fieldService.findById(pd_new_filed);
			if(!tablestr.equals("")){
				tablestr=tablestr+",";
			}
			if(pd_new_filed.getString("FIELDTYPE")!=null&&pd_new_filed.getString("FIELDTYPE").equals("date")){
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+"='"+pd.getString(pd_new_filed.getString("FIELD").toUpperCase())+"'";
			}else{
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+"='"+pd.getString(pd_new_filed.getString("FIELD").toUpperCase())+"'";
			}
		}
		
		//System.out.println("tablestr:"+pd.getString(pd_new_filed.getString("FIELDTYPE").toUpperCase()));
		//pd.put("ID", id);
		pd.put("UPDATESTR", tablestr);

		taskcustomService.editCustom(pd);;//创建表

		mv.addObject("msg","success_");
		mv.setViewName("save_result");
		return mv;
	}
	
	public static void main(String[] args){  

		byte[] buffer = "0ba9f2d267814d2b95e0a4fab874bcfc".getBytes();
		//System.out.println(BitConverter.ToInt64(buffer, 0));
		//return BitConverter.ToInt64(buffer, 0);
	}

	public static long GuidToLongID(String guid)
	{
		byte[] buffer = guid.getBytes();
		return BitConverter.ToInt64(buffer, 0);
	}



	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除task");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		/*pd.put("CREATEMAN", Jurisdiction.getUsername());
		pd = taskuserService.findById(pd);	//根据ID读取
		if(pd.getString("TABLENAME")!=null&&!pd.getString("TABLENAME").equals("")){
			taskuserService.dropTable(pd);
		}*/

		taskcustomService.delete(pd);


		out.write("success");
		out.close();
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改task");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());


		taskuserService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/editHF")
	public void editHF(HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"修改task");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PrintWriter out =response.getWriter();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		//System.out.println(pd.getString("HFRESULT")+"d");
		pd.put("HFRESULT",URLDecoder.decode(pd.getString("HFRESULT"), "utf-8"));
		pd.put("HFREMARK",URLDecoder.decode(pd.getString("HFREMARK"), "utf-8"));
		taskcustomService.editHF(pd);
		
		float num=0;   
		//	DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
		String s = "";//返回的是String类型 
		//	DecimalFormat df =null;
		NumberFormat numberFormat = NumberFormat.getInstance();
		pd.put("FIELD", "a.ID");
		List<PageData> pd_table = exetaskService.listAll(pd);
		pd.put("NUM", pd_table.size());
		pd.put("HFWJ", "1");
		List<PageData> pd_table_iswj =exetaskService.listAllIsfp(pd);
		pd.put("HFWJ", "2");
		List<PageData> pd_table_issb =exetaskService.listAllIsfp(pd);
		num=(float)(pd_table_iswj.size()+pd_table_issb.size())/(float)pd_table.size()*100;
		
		numberFormat.setMaximumFractionDigits(2); 
		s=numberFormat.format(num);
		pd.put("WCJD",s);
		out.write("success:"+s);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表task");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		URLDecoder urlDecoder = new URLDecoder();  
		//System.out.println( pd.getString("keywords"));
		String keywords = pd.getString("keywords")==null?"":URLDecoder.decode( pd.getString("keywords"),"utf-8");;				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}			//关键词检索条件
		
		page.setPd(pd);

		List<PageData>	varList = taskuserService.list(page);	//列出task列表
		float num=0;   
	//	DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
		String s = "";//返回的是String类型 
	//	DecimalFormat df =null;
		NumberFormat numberFormat = NumberFormat.getInstance();
		int NUM=0;
		for(PageData pd_n:varList){
			NUM=0;
			String TABLENAME=pd_n.getString("TABLENAME");
			String FPTYPE=pd_n.getString("FPTYPE")==null?"":pd_n.getString("FPTYPE");
			pd_n.put("FIELD", "a.ID,(@i:=@i+1) as ROWNO");
			List<PageData> pd_table =new ArrayList(); 
			List<PageData> pd_table_all =new ArrayList(); 
			pd_table_all =taskcustomService.listAll(pd_n);
			int numfp=0;
			if(FPTYPE.equals("2")){
				pd_n.put("TASKID",pd_n.getString("ID"));
				pd_table =taskcustomService.listAllMsg(pd_n);
				for(PageData pd_cus_fp:pd_table){
					NUM=NUM+Integer.parseInt(pd_cus_fp.getString("NUM")==null?"0":pd_cus_fp.getString("NUM"));
				}
				
				//NUM= pd_table.size();
			}else if(FPTYPE.equals("3")||FPTYPE.equals("1")){
				pd_table=pd_table_all;
				NUM= pd_table.size();
			}else{
				pd_table=pd_table_all;
				NUM=0;
			}
			numfp=taskcustomService.listAllByWj(pd_n).size();
			pd_n.put("ALLNUM",pd_table_all.size());	
			if(numfp==0){
				if(NUM!=0){
					pd_n.put("ZXQK","已分配:"+NUM+";执行:0");
					pd_n.put("ZXQKNUM",NUM);
				}
				pd_n.put("NUMFP","0");
			}else{
				pd_n.put("ZXQK","配额数:"+NUM+";执行:"+numfp+"");
				pd_n.put("NUMFP",numfp);
			}
			
			pd_n.put("NUM",NUM);
			pd_n.put("HFWJ", "1");
			List<PageData> pd_table_iswj =taskcustomService.listAllIsfp(pd_n);
			pd_n.put("HFWJ", "2");
			List<PageData> pd_table_issb =exetaskService.listAllIsfp(pd_n);
			num=(float)(pd_table_iswj.size()+pd_table_issb.size())/(float)NUM*100;
			
			numberFormat.setMaximumFractionDigits(2); 
			s=numberFormat.format(num);;
			pd_n.put("WCJD",s);
		}
		mv.setViewName("xxgl/taskcustom/taskcustom_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	
	/**回访结果查询列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list_result")
	public ModelAndView list_result(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表task");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User) session.getAttribute("sessionUser");
		String ZXZ="";
		if(user!=null){
			ZXZ=user.getZXZ();
			String ZXZSTR="";
			if(ZXZ!=null&&!ZXZ.equals("")){
				String[] arr=ZXZ.split(",");
				for(int i=0;i<arr.length;i++){
					if(!ZXZSTR.equals("")){
						ZXZSTR=ZXZSTR+",";
					}
					ZXZSTR=ZXZSTR+"'"+arr[i]+"'";
				}
				pd.put("ZXZSTR", ZXZSTR);
			}
		}
		
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData> varList = taskuserService.list(page);	//列出task列表
		String username=Jurisdiction.getUsername();
		pd.put("USERNAME", username);
		PageData pd_user=zxlbService.findByUsername(pd);
		List<PageData> varnewList =new ArrayList();
		if(pd_user!=null){
			PageData pd_kh = new PageData();
			List<PageData> zxList=null;
			for(PageData pd_n:varList ){
				pd_kh.put("TABLENAME", pd_n.getString("TABLENAME"));
				pd_kh.put("FIELD", "a.ID,(@i:=@i+1) as ROWNO");
				pd_kh.put("ZXMAN", pd_user.getString("ID"));
				zxList=taskcustomService.listAll(pd_kh);
				if(zxList.size()>0){
					varnewList.add(pd_n);
				}
				
			}
		}else{
			varnewList=varList;
		}
		
		List<PageData> zxzList=zxzService.listAll(pd);
		mv.setViewName("xxgl/taskcustom/taskcustom_result");
		mv.addObject("varList", varnewList);
		//System.out.println(zxzList);
		mv.addObject("zxzList", zxzList);
		mv.addObject("ZXZ", ZXZ);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();

		PageData pd = new PageData();
		List<Dictionaries> dictList=dictionariesService.listAllDict("704a5b0aa17449d4b32a4f9a5ccee656");
		mv.addObject("dictList", dictList);
		List<PageData> varNList = templateService.listAll(pd);
		mv.addObject("varNList", varNList);

		String ID=this.get32UUID();

		mv.addObject("ID", ID);

		pd = this.getPageData();
		mv.setViewName("xxgl/task/task_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	

	/**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		User user=(User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();

		List<Dictionaries> dictList=dictionariesService.listAllDict("704a5b0aa17449d4b32a4f9a5ccee656");
		mv.addObject("dictList", dictList);
		List<PageData> varNList = templateService.listAll(pd);
		mv.addObject("varNList", varNList);
		pd = this.getPageData();
		////System.out.println(pd.getString("action"));
		mv.addObject("action", pd.getString("action"));
		pd = taskuserService.findById(pd);	//根据ID读取

		////System.out.println(pd.getString("action"));
		mv.setViewName("xxgl/task/task_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goSearch")
	public ModelAndView goSearch()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		User user=(User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();

		pd = this.getPageData();
		////System.out.println(pd.getString("action"));
		PageData pd_new_cus = new PageData();
		
		String TEMPLATE_ID=pd.getString("TEMPLATE_ID");
		////System.out.println(TEMPLATE_ID+"TEMPLATE_ID");
		String tableName=pd.getString("TABLENAME");
		//pd_new.put("ID", TEMPLATE_ID);
		//pd_new=templateService.findById(pd_new);
		//page.setPd(pd);
		PageData pd_task = new PageData();
		String action=pd.getString("action")==null?"":pd.getString("action");
		if(action.equals("result")){
			pd_task.put("ID", pd.getString("TASKID"));
			pd_task=taskuserService.findById(pd_task);
			if(pd_task!=null){
				pd.put("CUSTOM_TEMPLATE_ID", pd_task.getString("CUSTOM_TEMPLATE_ID"));
				pd.put("TABLENAME", pd_task.getString("TABLENAME"));
			}
			
		}
		
		
		pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
		////System.out.println(pd.getString("CUSTOM_TEMPLATE_ID")+"pd.getString(\"CUSTOM_TEMPLATE_ID\")");
		List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
		String fieldstr="";
		for(int i=0;i<varList.size();i++){
			//pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
			//pd_new_filed=fieldService.findById(pd_new_filed);
			if(!fieldstr.equals("")){
				fieldstr=fieldstr+",";
			}
			fieldstr=fieldstr+varList.get(i).getString("FIELD").toUpperCase();
		}
		pd.put("FIELD", fieldstr);
		String str="";
		PageData pageData_search=taskcustomService.findFieldByID(pd);
		String value="";
		String req="";
		for(int i=0;i<varList.size();i++){
			if(varList.get(i).get("FIELD")!=null&&pageData_search.get(varList.get(i).get("FIELD").toString().toUpperCase())!=null){
				req="";
				System.out.println(varList.get(i).get("ISBASIC")+"fieldtype:");
				if(varList.get(i).get("ISBASIC")!=null&&varList.get(i).get("ISBASIC").equals("1")){
					req="required";
				}
				
				
				value=pageData_search.get(varList.get(i).get("FIELD").toString().toUpperCase()).toString();
				if(value==null){
					value="";
				}
					
				str=str+"<tr>";
				str=str+"<td style=\"width:205px;text-align: right;padding-top: 13px;\">"+varList.get(i).getString("FIELDNAME")+":</td>";
				//System.out.println(varList.get(i).get("FIELDTYPE")+"fieldtype:");
				if(varList.get(i).get("FIELDTYPE")!=null&&varList.get(i).get("FIELDTYPE").equals("date")){
					if(value.length()>10){
						value=value.substring(0, 10);
					}
					str=str+"<td><input  class=\"span10 date-picker\"  data-date-format=\"yyyy-mm-dd\" readonly=\"readonly\"  type=\"text\" name=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" id=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" value=\""+value+"\" maxlength=\"30\" placeholder=\"这里输入"+varList.get(i).getString("FIELDNAME")+"\" style=\"width:60%;\" "+req+"/>";
					str=str+"</td>";
				}else{
					str=str+"<td><input type=\"text\" name=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" id=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" value=\""+value+"\" maxlength=\"30\" placeholder=\"这里输入"+varList.get(i).getString("FIELDNAME")+"\" style=\"width:60%;\" "+req+"/>";
					str=str+"</td>";
				}
				str=str+"</tr>";
				
			}else{
				req="";
				if(varList.get(i).get("ISBASIC")!=null&&varList.get(i).get("ISBASIC").equals("1")){
					req="required";
				}
				value="";
				str=str+"<tr>";
				str=str+"<td style=\"width:205px;text-align: right;padding-top: 13px;\">"+varList.get(i).getString("FIELDNAME")+":</td>";
				if(varList.get(i).get("FIELDTYPE")!=null&&varList.get(i).get("FIELDTYPE").equals("date")){
					if(value.length()>10){
						value=value.substring(0, 10);
					}
					str=str+"<td><input  class=\"span10 date-picker\"  data-date-format=\"yyyy-mm-dd\" readonly=\"readonly\"  type=\"text\" name=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" id=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" value=\""+value+"\" maxlength=\"30\" placeholder=\"这里输入"+varList.get(i).getString("FIELDNAME")+"\" style=\"width:60%;\" "+req+"/>";
					str=str+"</td>";
				}else{
					str=str+"<td><input type=\"text\" name=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" id=\""+varList.get(i).getString("FIELD").toUpperCase()+"\" value=\""+value+"\" maxlength=\"30\" style=\"width:60%;\" readonly  "+req+"/>";
					str=str+"</td>";
				}
				str=str+"</tr>";
			}
			
		}
		
		
		
		
		////System.out.println(pd.getString("action"));
		mv.setViewName("xxgl/taskcustom/taskcustom_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("str", str);
		return mv;
	}	
	
	/**获取客户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getCustomList")
	@ResponseBody
	public void getCustomList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		String str=this.getMsg(pd);
		ResponseUtils.renderJson(response,str);
	}
	
	
	/**获取客户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/CustomSave")
	@ResponseBody
	public void CustomSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		pd = this.getPageData();
		
		String TEMPLATE_ID=pd.getString("TEMPLATE_ID");
		String type=pd.getString("type")==null?"":pd.getString("type");
		String zxid=pd.getString("zxid");
		String cid=pd.getString("cid");
		String[] arr=cid.split(",");
		if(type.equals("1")){
			for(int i=0;i<arr.length;i++){
				pd_new.put("ID", arr[i]);
				pd_new.put("ZXMAN", zxid);
				pd_new.put("TABLENAME", pd.getString("TABLENAME"));
				pd_new.put("FPMAN", Jurisdiction.getUsername());
				taskcustomService.edit(pd_new);
			}
		}
		String str=this.getMsg(pd);
		ResponseUtils.renderJson(response,str);
	}
	
	
	
	@RequestMapping(value="/tj")
	public void tj(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//将人员集合顺序随机打乱 因为第一人分配到的案件总金额是最大的
		//uid：用于存放人员数组的下标
		//System.out.println(pd.getString("TABLENAME")+"tablename");
		pd.put("TABLENAME", pd.getString("TABLENAME"));
		pd.put("FIELD","count(*) as COUNTER,ZXMAN,sum(case when hfwj=1 then 1 end) as HFWC,sum(case when hfwj=1 or hfwj=2  then 1 end) as YHFRW,sum(case when  hfwj=3 then 1 end) as XCHFRW,sum(case when hfwj=2 then 1 end) as HFSB,sum(case when hfwj is null then 1 end) as WWC");
		//System.out.println(pd.getString("FIELD")+"FIELD");
		List<PageData> varList=taskcustomService.listAllByzxman(pd);
		
		float num=0;
		String s = "";//返回的是String类型 
		//	DecimalFormat df =null;
		NumberFormat numberFormat = NumberFormat.getInstance();
		PageData pd_s=new PageData();
		PageData pd_zx=new PageData();
		

		String tablestr="";
		String theadstr="<thead><tr>";
		String tbodystr="";
		boolean boo=false;
		PageData pd_new_filed = new PageData();
		theadstr+="<th class=\"center\" style=\"width:30px;\">序号</th>";
		theadstr+="<th class=\"center\">坐席人姓名</th>";
		theadstr+="<th class=\"center\">总任务数</th>";
		theadstr+="<th class=\"center\">已回访</th>";
		theadstr+="<th class=\"center\">回访成功</th>";
		theadstr+="<th class=\"center\">下次回访</th>";
		theadstr+="<th class=\"center\">回访失败</th>";
		theadstr+="<th class=\"center\">未回访任务</th>";
		theadstr+="<th class=\"center\">进度</th>";
		theadstr=theadstr+"</tr></thead>";
		tbodystr="<tbody>";
		int i=0;
		PageData pd_task=taskuserService.findById(pd);
		String FPTYPE="";
		if(pd_task!=null){
			 FPTYPE=pd_task.getString("FPTYPE")==null?"":pd_task.getString("FPTYPE");
		}
		
		int NUM=0;
		for(PageData pd_n:varList){
			NUM=0;
			i++;
			if( pd_n.getString("ZXMAN")!=null&& !pd_n.getString("ZXMAN").equals("")){
				
				pd_s.put("ID", pd_n.getString("ZXMAN"));
				pd_zx=zxlbService.findById(pd_s);
				pd_n.put("ZXXM", pd_zx.getString("ZXXM"));
			}	
			/*if(FPTYPE.equals("2")){
				pd.put("TASKID",pd.getString("ID"));
				List<PageData> pd_table =taskcustomService.listAllMsg(pd);
				for(PageData pd_cus_fp:pd_table){
					NUM=NUM+Integer.parseInt(pd_cus_fp.getString("NUM")==null?"0":pd_cus_fp.getString("NUM"));
				}
				
			}else{*/
				NUM=Integer.parseInt((pd_n.get("COUNTER")==null?"0":pd_n.get("COUNTER")).toString());
			//}
			
			num=Float.parseFloat((pd_n.get("YHFRW")==null?"0":pd_n.get("YHFRW")).toString())/(float) NUM*100;
			numberFormat.setMaximumFractionDigits(2); 
			s=numberFormat.format(num);
			pd_n.put("WCJD",s);
			
			tbodystr=tbodystr+"<tr>";
			tbodystr=tbodystr+"<td class='center' style=\"width: 55px;\">"+i+"</td>";
			tbodystr=tbodystr+"<td class='center'>"+(pd_n.get("ZXXM")==null?"未分配人员":pd_n.get("ZXXM"))+"</td>";
			tbodystr=tbodystr+"<td class='center'>"+this.isnull(pd_n.get("COUNTER"))+"</td>";
			
			tbodystr=tbodystr+"<td class='center'>"+this.isnull(pd_n.get("YHFRW"))+"</td>";
			
			tbodystr=tbodystr+"<td class='center'>"+this.isnull(pd_n.get("HFWC"))+"</td>";
			tbodystr=tbodystr+"<td class='center'>"+this.isnull(pd_n.get("XCHFRW"))+"</td>";
			tbodystr=tbodystr+"<td class='center'>"+this.isnull(pd_n.get("HFSB"))+"</td>";
			tbodystr=tbodystr+"<td class='center'>"+this.isnull(pd_n.get("WWC"))+"</td>";
			tbodystr=tbodystr+"<td class='center'>";
			tbodystr=tbodystr+"<div class=\"clearfix\">";
			tbodystr=tbodystr+"<span class=\"pull-left\"></span>";
			tbodystr=tbodystr+"<small class=\"pull-right\">"+s+"%</small>";
			tbodystr=tbodystr+"</div>";
			tbodystr=tbodystr+"<div class=\"progress xs\">";
			tbodystr=tbodystr+"<div class=\"progress-bar progress-bar-green\" style=\"width: "+s+";\"></div>";
			tbodystr=tbodystr+"</div>";
			tbodystr=tbodystr+"</td>";
			tbodystr=tbodystr+"</tr>";
			boo=true;
		}
		tbodystr=tbodystr+"</tbody>";
		String str="";
		if(boo){
			str=theadstr+tbodystr;
		}else{
			str=theadstr+"<tbody><tr><td style=\"color:red\">暂无数据</td></tr></tbody>";
		}
		JSONObject object_new = new JSONObject();
		//System.out.println(str);
		object_new.put("tjString",str);
		//mv.setViewName("xxgl/taskcustom/tj_list");
		///mv.addObject("varList", varList);
		//mv.addObject("pd", pd);
		//mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		ResponseUtils.renderJson(response,object_new.toString());
	}
	
	public String isnull(Object var){
		String v="";
		if(var==null){
			v="";
		}
		
		if(var!=null){
			v=var.toString();
		}
		
		return v;
	}

	/**获取客户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/CustomRandomSave")
	@ResponseBody
	public void CustomRandomSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		pd = this.getPageData();
		
		//需要分配的人员集合（list）
		 
		//将人员集合顺序随机打乱 因为第一人分配到的案件总金额是最大的
		
		//uid：用于存放人员数组的下标
		int uid=0;
		String zxnumstr="";//坐席分配人员数量
		String[] zxnumarry=null;
		String FPTYPE=pd.getString("FPTYPE")==null?"":pd.getString("FPTYPE");
		int ROWNUM=0;
		if(FPTYPE.equals("3")){
			zxnumstr=pd.getString("zxnumstr")==null?"":pd.getString("zxnumstr");
			zxnumarry=zxnumstr.split(",");
			for(int i=0;i<zxnumarry.length;i++){
				ROWNUM=ROWNUM+Integer.parseInt(zxnumarry[i]);
			}
			pd.put("ROWNUM",ROWNUM);
		}
		pd.put("TABLENAME", pd.getString("TABLENAME"));
		pd.put("FIELD","ID,ZXMAN");
		List<PageData> pdlist=taskcustomService.listAllIsnotfp(pd);
		String str="";
		if(pdlist.size()>0){
			List<String>  clist=new ArrayList();
			List<String>  zxlist=new ArrayList();
			String TEMPLATE_ID=pd.getString("TEMPLATE_ID");
			
			String zxid=pd.getString("ZXIDSTR");
			String cid=pd.getString("cid");
			
			
			//String[] arr=cid.split(",");
			String[] zxarr=zxid.split(",");
			
			
			for(int i=0;i<zxarr.length;i++){
				zxlist.add(zxarr[i]);
			}
			for(int i=0;i<pdlist.size();i++){
				clist.add(pdlist.get(i).getString("ID"));
			}
			int k = 0;  
			
	        Map<String,String> map = new HashMap(); 
	        int personSize = zxlist.size(); 
	        List<DataBean> result = new ArrayList<DataBean>();  
	        
	        if(FPTYPE.equals("1")){
		        for (int i = 0; i < clist.size(); i++) {   
		        	DataBean p = new DataBean();  
		            p.setName(clist.get(i));
		            p.setValue(zxarr[i % personSize]);
		            result.add(p);  
		        }   
	        }
	        
	        if(FPTYPE.equals("3")){
	        	int num=0;
	        	int n=0;
	        	int allnum=0;
	        	for(int m=0;m<zxarr.length;m++){
					num=Integer.parseInt(zxnumarry[m]);
					allnum=allnum+num;
			        for (int i = n; i < allnum;i++) {   
			        	DataBean p = new DataBean();  
			            p.setName(clist.get(i));
			            p.setValue(zxarr[m]);
			            result.add(p);  
			        }  
			        n=allnum;
				} 
	        }
	        System.out.println("result:"+ result);
	        for(DataBean p:result){
	        	System.out.println(p.getName()+"fptype:"+ p.getValue());
				pd_new.put("ID", p.getName());
				pd_new.put("ZXMAN", p.getValue());
				pd_new.put("TABLENAME", pd.getString("TABLENAME"));
				pd_new.put("FPMAN", Jurisdiction.getUsername());
				taskcustomService.edit(pd_new);
			}
	       // System.out.println(pd.getString("FPTYPE")+"fptype:"+pd.getString("ID"));
	        taskuserService.editFptype(pd);
			//分配结束
			str=this.getMsg(pd);
		}else{
			str=this.getMsg(pd);
		}
		
		ResponseUtils.renderJson(response,str);
	}
	
	public String getMsg(PageData pd){
		JSONObject object_new = new JSONObject();
		try{
			PageData pd_task = new PageData();
			PageData pd_new_cus = new PageData();
			
			////System.out.println(TEMPLATE_ID+"TEMPLATE_ID");
			String tableName=pd.getString("TABLENAME");
			//pd_new.put("ID", TEMPLATE_ID);
			//pd_new=templateService.findById(pd_new);
			//page.setPd(pd);
			String action=pd.getString("action")==null?"":pd.getString("action");
			if(action.equals("result")){
				pd_task=taskuserService.findById(pd);
				if(pd_task!=null){
					pd.put("CUSTOM_TEMPLATE_ID", pd_task.getString("CUSTOM_TEMPLATE_ID"));
					pd.put("TABLENAME", pd_task.getString("TABLENAME"));
				}
				List<PageData>	zxmanList =zxlbService.listAll(pd);
				String ZXMANSTR="";
				for(PageData pd_zxman:zxmanList){
					if(!ZXMANSTR.equals("")){
						ZXMANSTR=ZXMANSTR+",";
					}
					ZXMANSTR=ZXMANSTR+"'"+pd_zxman.getString("ID")+"'";
				}
				pd.put("ZXMANSTR", ZXMANSTR);
			}
			
			pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
			pd_new_cus.put("ISSHOW","1");
			List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
	
			
			List<PageData>	customList =taskcustomService.listCustom(pd);
	
			String tablestr="";
			
			String theadstr="<table id=\"customtable\" class=\"table table-bordered table-hover\"><thead><tr>";
			String tbodystr="";
			boolean boo=false;
			PageData pd_new_filed = new PageData();
/*			theadstr+="<th style=\"width:50px;\"><input type=\"checkbox\" onclick=\"checkall(this)\" name=\"checkall\" id=\"checkall\"></th>";
*/			//for(int i=0;i<varList.size();i++){
			int num=varList.size();
			//if(num>=5){
				//num=5;
			//}
			for(int i=0;i<num;i++){
				//pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
				//pd_new_filed=fieldService.findById(pd_new_filed);
				theadstr+="<th title=\""+varList.get(i).getString("FIELDNAME")+"\">"+varList.get(i).getString("FIELDNAME")+"</th>";
			}
			theadstr+="<th>坐席人员</th>";
			theadstr+="<th>回访情况</th>";
			theadstr+="<th>回访结果</th>";
			theadstr+="<th>操作</th>";
			theadstr=theadstr+"</tr></thead>";
			tbodystr="<tbody>";
			String value="";
			PageData pd_dictionaries=null;
			for(PageData pddata:customList){
/*				tbodystr+="<tr><td><input type=\"checkbox\" name=\"customcheck\" id=\""+pddata.getString("ID")+"\"  value=\""+pddata.getString("ID")+"\"></td>";
*/				for(int i=0;i<num;i++){
				//for(int i=0;i<varList.size();i++){	
					boo=true;
					//pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
					//pd_new_filed=fieldService.findById(pd_new_filed);
					value=(pddata.get(varList.get(i).getString("FIELD").toUpperCase())==null?"":pddata.get(varList.get(i).getString("FIELD").toUpperCase())).toString();
					tbodystr+="<td title=\""+value+"\">"+value+"</td>";
				}
				tbodystr+="<td>"+(pddata.getString("ZXXM")==null?"":pddata.getString("ZXXM"))+"</td>";
				String hfwj=(pddata.getString("HFWJ")==null?"":pddata.getString("HFWJ"));
				if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("1")){
					tbodystr+="<td style=\"color:red\">回访成功</td>";
				}else if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("2")){
					tbodystr+="<td style=\"color:blue\">回访失败</td>";
				}else if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("3")){
					tbodystr+="<td style=\"color:blue\">下次回访</td>";
				}else{
					tbodystr+="<td style=\"color:green\">未回访</td>";
				}
				pd_dictionaries=new PageData(); 
				if(pddata.get("HFRESULT")!=null){
					pddata.put("DICTIONARIES_ID", pddata.get("HFRESULT"));
					pd_dictionaries=dictionariesService.findById(pddata);
				}
				if(pd_dictionaries!=null){
					tbodystr+="<td>"+(pd_dictionaries.getString("NAME")==null?"":pd_dictionaries.get("NAME"))+"</td>";
				}else{
					tbodystr+="<td></td>";
				}
				
				tbodystr+="<td style='width:110px;'><a  class=\"cy_bj\" onclick=\"search_cus('"+pddata.getString("ID")+"','"+pd.getString("TASKID")+"');\"><i title=\"查看\"></i>  </a>";
				if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("1")){
					tbodystr+="<a  class=\"cy_bj\" onclick=\"search_answer('"+pddata.getString("ID")+"','"+pd.getString("TASKID")+"');\"><i  title=\"查看\"></i>  </a>";
				}
				if(!action.equals("result")){
					tbodystr+= "<a class=\"cy_sc\" onclick=\"del_cus('"+pddata.getString("ID")+"');\"> <i title=\"删除\"></i> </a></td>";
				}
				tbodystr=tbodystr+"</tr>";
			}
			tbodystr=tbodystr+"</tbody></table>";
			
			//获取坐席人员信息
			String zxlbstr="";
			List<PageData>	zxzList = zxzService.listAll(pd_new_filed);	//列出zxlb列表
			List<PageData>	cusList =null; 
			String cusstr="";
			
			pd.put("TABLENAME", pd.getString("TABLENAME"));
			pd.put("FIELD","ID,ZXMAN");
			List<PageData> pdfllist=taskcustomService.listAllIsfp(pd);
			String zxman="";
			for(PageData pddata:pdfllist){
				zxman=pddata.getString("ZXMAN");
			}
			PageData pdzxm_search = new PageData();
			pdzxm_search.put("ID", zxman);
			PageData pdzxm=zxlbService.findById(pdzxm_search);
			
//			/System.out.println(zxman+"zxman");
			pd.put("TABLENAME", pd.getString("TABLENAME"));
			pd.put("FIELD","ID,ZXMAN");
			List<PageData> pdlist=taskcustomService.listAllIsnotfp(pd);
			
			zxlbstr=zxlbstr+"<div style=\"font-weight:bold;height:30px;margin-left:15px;\">可分配人数：<span style=\"color:red\">"+pdlist.size()+"</span>人<input type=\"hidden\" id=\"kfprs\" name=\"kfprs\" value=\""+pdlist.size()+"\" ></div>";
			zxlbstr=zxlbstr+"&nbsp;&nbsp;&nbsp;&nbsp;坐席组：<select name=\"ZXZ\" id=\"ZXZ\" style=\"width:180px;height:35px;\"><option value=\"\">==请选择坐席组==</option>";
			
			for(PageData pddata:zxzList){
				//System.out.println(pdzxm.getString("ZXZ")+"zxman:"+pddata.getString("ID"));
				if(pdzxm!=null&&pdzxm.getString("ZXZ").equals(pddata.getString("ID"))){
					zxlbstr=zxlbstr+"<option value=\""+pddata.getString("ID")+"\" selected>"+pddata.getString("ZMC")+"</option>";
					//object.put("isField","1");
				}else{
					zxlbstr=zxlbstr+"<option value=\""+pddata.getString("ID")+"\">"+pddata.getString("ZMC")+"</option>";
				}
			}
			zxlbstr=zxlbstr+"<select>";
			zxlbstr=zxlbstr+"<button type=\"button\" style=\"margin-left:10px;\" class=\"btn btn-default\" onclick=\"getZxman()\"> 选择坐席人员</button>";
			JSONObject object = new JSONObject();
			
			String customString="";
			if(boo){
				customString=theadstr+tbodystr;
			}else{
				customString=theadstr+"<tbody><tr><td style=\"color:red\">暂无数据</td></tr></tbody>";
			}
			
			System.out.println(zxlbstr);
			object_new.put("zxlbString", zxlbstr);
			object_new.put("customString",customString);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return object_new.toString();
	}
	
	@RequestMapping(value="/getZxlb")
	@ResponseBody
	public void getZxlb(HttpServletRequest request,HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		String zxlbstr="";
		List<PageData>	zxlbList = zxlbService.listAll(pd);	//列出zxlb列表
		List<PageData>	cusList =null; 
		String cusstr="";
		String action= pd.getString("action")==null?"":pd.getString("action");
		//pd.put("TABLENAME", pd.getString("TABLENAME"));
		//pd.put("FIELD","ID,ZXMAN");
		//List<PageData> pdlist=taskcustomService.listAllIsnotfp(pd);
		//zxlbstr=zxlbstr+"<li style=\"font-weight:bold\">可分配人数："+pdlist.size()+"人<input type=\"hidden\" id=\"kfprs\" name=\"kfprs\" value=\""+pdlist.size()+"\" ></li>";
		JSONObject object = new JSONObject();
		//是否存在坐席人员
		String username=Jurisdiction.getUsername();
		pd.put("USERNAME", username);
		PageData pd_user=zxlbService.findByUsername(pd);
		String rwstr="";
		
		
		for(PageData pddata:zxlbList){
			
			
			if(action.equals("result")){
				if(pd_user!=null){
					if(pd_user.getString("ID").equals(pddata.getString("ID"))){
						zxlbstr=zxlbstr+"<option value=\""+pddata.getString("ID")+"\" selected>"+pddata.getString("ZXXM")+"</option>";
					}
				}else{
					zxlbstr=zxlbstr+"<option value=\""+pddata.getString("ID")+"\">"+pddata.getString("ZXXM")+"</option>";
				}
				
			}else{
				pddata.put("ZXMAN", pddata.getString("ID"));
				pddata.put("TABLENAME", pd.getString("TABLENAME"));
				pddata.put("FIELD", "(@i:=@i+1) as ROWNO");
				cusList=taskcustomService.listAll(pddata);
				
				cusstr="";
				for(PageData pd_cus:cusList){
					if(!cusstr.equals("")){cusstr=cusstr+",";}
					cusstr=cusstr+pd_cus.getString("ID");
				}
				zxlbstr=zxlbstr+"<li style=\"float:left;margin-left:5px;width:150px;\">";
				zxlbstr=zxlbstr+"<input type=\"checkbox\" name=\"zxlbcheck\" id=\""+pddata.getString("ID")+"\"  value=\""+pddata.getString("ID")+"\">";
				zxlbstr=zxlbstr+"<span class=\"text\">("+pddata.getString("ZMC")+")"+pddata.getString("ZXXM")+"</span>";
				zxlbstr=zxlbstr+"<input type=\"hidden\" id=\"ZXXM"+pddata.getString("ID")+"\" value=\""+pddata.getString("ZXXM")+"\">";
				zxlbstr=zxlbstr+"</li>";
			}
		
		}
		object.put("zxlbstr",zxlbstr);
		ResponseUtils.renderJson(response,object.toString());
	}
	
	
	/**获取定额分配字段
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getFpfield")
	@ResponseBody
	public void getFpfield(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		pd=this.getPageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		String keywords = pd.getString("keywords");				//关键词检索条件
		String str="";
		pd.put("ISPE", "1");
		//System.out.println();
		List<PageData>	varList = customtemplatefieldService.listAll(pd);	//列出customtemplatefield列表
		JSONObject object = new JSONObject();
		//object_new.put("zxlbString", zxlbstr);
		//获取分配字段
		PageData pd_groupfile=taskcustomService.listByGroupField(pd);
		
		
		List<PageData>	deuseList = taskcustomService.listAllUseDe(pd);
	    
		if(deuseList.size()>0){
			str="<div style=\"height:30px;margin-left:10px;\">配额字段：<select name=\"FIELD\" id=\"FIELD\" style=\"width:180px;height:30px\">";
			for(PageData pdfield:varList){
				if(pd_groupfile!=null&&pd_groupfile.getString("FIELD").equals(pdfield.getString("FIELD"))){
					str=str+"<option value=\""+pdfield.getString("FIELD")+"\" selected>"+pdfield.getString("FIELDNAME")+"</option>";
					object.put("isField","1");
				}
			}
		}else{
			str="<div style=\"height:30px;margin-left:10px;\">配额字段：<select onChange=\"changefield()\" name=\"FIELD\" id=\"FIELD\" style=\"width:180px;height:30px\"><option value=\"\">==请选择定额分配字段==</option>";
			for(PageData pdfield:varList){
				//System.out.println(pd_groupfile.getString("FIELD")+"kkk"+pdfield.getString("FIELD"));
				if(pd_groupfile!=null&&pd_groupfile.getString("FIELD").equals(pdfield.getString("FIELD"))){
					str=str+"<option value=\""+pdfield.getString("FIELD")+"\" selected>"+pdfield.getString("FIELDNAME")+"</option>";
					object.put("isField","1");
				}else{
					str=str+"<option value=\""+pdfield.getString("FIELD")+"\">"+pdfield.getString("FIELDNAME")+"</option>";
				}
			}
		}
	   
		str=str+"</select>";
		str=str+"&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"btn btn-xs btn-success\" onclick=\"toExcelField('"+pd.getString("TABLENAME")+"');\">下载</a>"
				+ "<a style=\"margin-left:5px\" class=\"btn btn-xs btn-success\" onclick=\"impField('${var.CUSTOM_TEMPLATE_ID}','${var.TABLENAME}');\">导入</a></div>";
		
		
		//获取坐席人员信息
		String zxlbstr="";
		List<PageData>	zxzList = zxzService.listAll(pd);	//列出zxlb列表
		List<PageData>	cusList =null; 
		String cusstr="";
		
		
		List<PageData> pdfllist=taskcustomService.listAllMsg(pd);
		String zxz="";
		for(PageData pddata:pdfllist){
			zxz=pddata.getString("ZXZ");
		}
		//System.out.println(zxz+"zxz:");
		//zxlbstr=zxlbstr+"<li style=\"font-weight:bold\">可分配人数："+pdlist.size()+"人<input type=\"hidden\" id=\"kfprs\" name=\"kfprs\" value=\""+pdlist.size()+"\" ></li>";
		zxlbstr="<select class=\"form-control\"  name=\"ZXZDE\" id=\"ZXZDE\" style=\"width:180px;\"><option value=\"\">==请选择坐席组==</option>";
		
		for(PageData pddata:zxzList){
			//System.out.println(pdzxm.getString("ZXZ")+"zxman:"+pddata.getString("ID"));
			if(zxz!=null&&zxz.equals(pddata.getString("ID"))){
				zxlbstr=zxlbstr+"<option value=\""+pddata.getString("ID")+"\" selected>"+pddata.getString("ZMC")+"</option>";
				//object.put("isField","1");
			}else{
				zxlbstr=zxlbstr+"<option value=\""+pddata.getString("ID")+"\">"+pddata.getString("ZMC")+"</option>";
			}
		}
		object.put("selString",str);
		object.put("zxlbstr",zxlbstr);
		ResponseUtils.renderJson(response,object.toString());
	}
	
	/**根据配置字段获取相应的信息
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getMsgFpfield")
	@ResponseBody
	public void getMsgFpfield(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		pd=this.getPageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();			//关键词检索条件
		String str="";
		
		//System.out.println();
		List<PageData>	varList = taskuserService.listAllGroupByField(pd);	//列出customtemplatefield列表
		JSONObject object = new JSONObject();
		//object_new.put("zxlbString", zxlbstr);
		
		PageData pd_group = new PageData(); 
		//str="<ul class=\"products-list product-list-in-box\" style=\"margin-top:10px;\">";
		String groupstr="";
		String value="";
		int i=0;
		String num="";
	    for(PageData pdfield:varList){
	    	
	    	
	    	if(pdfield.get(pd.getString("FIELD").toUpperCase())!=null){
	    		value=pdfield.get(pd.getString("FIELD").toUpperCase()).toString();
				if(value.equals("")){
					value="kongzhi";
				}
		    	if(!groupstr.equals("")){
					groupstr=groupstr+",";
				}
		    	pd.put("GROUPNAME", value);
		    	pd_group=taskcustomService.listByGroupname(pd);
		    	if(pd_group!=null){
		    		num=pd_group.get("NUM").toString();
		    	}
		    	
				groupstr=groupstr+value;
				str=str+"<li style=\"margin-left:5px;width:30%;float:left;\">";
				str=str+"<table style=\"width:100%;\">";
				str=str+"<tr style=\"height:30px;\">";
				str=str+"<td style=\"text-align:right;font-size:14px;\" title=\""+pdfield.getString(pd.getString("FIELD").toUpperCase())+"\">"+pdfield.getString(pd.getString("FIELD").toUpperCase())+":</td>"
						+ "<td style=\"font-size:14px;width:80px;\"><input type=\"number\" style=\"width:60px;\" id=\"group"+i+"\" max=\""+pdfield.get("COUNTER")+"\" value=\""+num+"\" min=\"1\">共<span style=\"color:red\">"+pdfield.get("COUNTER")+"</span>人</td>";
				str=str+"</tr>";
				str=str+"</table>";
				str=str+"</li>";
				i++;
	    	}
	    	
		}
		str=str+"<input type=\"hidden\" value=\""+groupstr+"\" id=\"groupstr\">";
		object.put("groupString",str);
		ResponseUtils.renderJson(response,object.toString());
	}
	
	
	/*
	 * 获取已经分配的信息
	 */
	@RequestMapping(value="/getFpCont")
	@ResponseBody
	public void getFpCont(HttpServletRequest request,HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		String zxlbstr="";
		//List<PageData>	zxlbList = zxlbService.listAll(pd);	//列出zxlb列表
		List<PageData>	cusList =null; 
		String cusstr="";
		pd.put("TABLENAME", pd.getString("TABLENAME"));
		pd.put("FIELD","ID");
		List<PageData> pdlist=taskcustomService.listAllIsfp(pd);
		/*
		 * 获取任务信息
		 */
		PageData pd_task=taskuserService.findById(pd);
		String ZXIDSTR=pd_task.getString("ZXIDSTR");
		String FPTYPE = pd.getString("FPTYPE")==null?"":pd.getString("FPTYPE");	
		zxlbstr=zxlbstr+"<div style=\"font-weight:bold;height:30px;margin-left:15px;\">已执行人数：<span style=\"color:red\">"+pdlist.size()+"</span>人<input type=\"hidden\" id=\"kfprs\" name=\"kfprs\" value=\""+pdlist.size()+"\" ></div>";
		JSONObject object = new JSONObject();
		if(ZXIDSTR!=null&&!ZXIDSTR.equals("")){
			String[] zxid=ZXIDSTR.split(",");
			for(int i=0;i<zxid.length;i++){
				
				PageData pddata=new PageData();
				pddata.put("ZXMAN",zxid[i]);
				pddata.put("TABLENAME", pd.getString("TABLENAME"));
				pddata.put("FIELD", "(@i:=@i+1) as ROWNO");
				cusList=taskcustomService.listAll(pddata);
				
				cusstr="";
				for(PageData pd_cus:cusList){
					if(!cusstr.equals("")){cusstr=cusstr+",";}
					cusstr=cusstr+pd_cus.getString("ID");
				}
				pddata.put("ID",zxid[i]);
				pddata=zxlbService.findById(pddata);
				if(FPTYPE.equals("1")||FPTYPE.equals("2")){
					zxlbstr=zxlbstr+"<li style=\"float:left;margin-left:5px;width:150px;\">";
					zxlbstr=zxlbstr+"<span class=\"text\">"+pddata.getString("ZXXM")+":<span style=\"color:red\">"+cusList.size()+"</span>人</span>";
					zxlbstr=zxlbstr+"</li>";
				}
				if(FPTYPE.equals("3")){
			    	zxlbstr=zxlbstr+"<li style=\"margin-left:5px;width:24%;float:left;\">";
			    	zxlbstr=zxlbstr+"<table style=\"width:100%;\">";
			    	zxlbstr=zxlbstr+"<tr style=\"height:30px;\">";
					zxlbstr=zxlbstr+"<td style=\"width:30%;text-align:right;font-size:14px;\">"+pddata.getString("ZXXM")+":</td>"
							+ "<td style=\"font-size:14px;\"><input type=\"number\" min=\""+cusList.size()+"\" style=\"width:60px;font-size:14px\" value=\""+cusList.size()+"\" id=\"NUM"+zxid[i]+"\">人</td>";
					zxlbstr=zxlbstr+"</tr>";
					zxlbstr=zxlbstr+"</table>";
					zxlbstr=zxlbstr+"</li>";
		    	}	
			}
		}
		
		object.put("zxlbstr",zxlbstr);
		object.put("ZXIDSTR",ZXIDSTR);
		ResponseUtils.renderJson(response,object.toString());
	}
	

	/**获取客户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/CustomGroupSave")
	@ResponseBody
	public void CustomGroupSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		pd = this.getPageData();
		taskcustomService.deleteGroupFp(pd);
		String groupid=pd.getString("groupid");
		String cid=URLDecoder.decode(pd.getString("groupstr"), "utf-8");
		String[] arr=cid.split(",");
		//System.out.println(cid+"cid");
		String[] group=groupid.split(",");
		for(int i=0;i<arr.length;i++){
			String id=this.get32UUID();
			pd.put("GUID", id);	//主键
			pd.put("GROUPNAME", arr[i]);
			pd.put("NUM", group[i]);
			pd.put("TABLENAME", pd.getString("TABLENAME"));
			pd.put("CREATEMAN", Jurisdiction.getUsername());
			pd.put("ZXLB", pd.getString("ZXZ"));
			taskcustomService.saveGroupFp(pd);
		}
		taskuserService.editFptype(pd);
		String str=this.getMsg(pd);
		ResponseUtils.renderJson(response,str);
	}
	
	
	/**任务启动
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/qidong")
	@ResponseBody
	public void qidong(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		pd = this.getPageData();
		String str="";
		String TEMPLATE_ID=pd.getString("TEMPLATE_ID");
		String type=pd.getString("type");
		String TABLENAME=pd.getString("TABLENAME");
		pd.put("FIELD", "(@i:=@i+1) as ROWNO");
		List<PageData> pd_table = taskcustomService.listAll(pd);	
		if(pd_table.size()<=0){
			str="还未添加客户";
			str="error1";
		}else{
			
			List<PageData> pd_table_fp = taskcustomService.listAllIsfp(pd);	
			if(pd_table_fp.size()<=0&&pd.getString("FPTYPE")!=null&&pd.getString("FPTYPE").equals("1")){
				str="客户还未分配坐席人员";
				str="error2";
			}else{
				pd.put("STATE", type);
				pd.put("TASKMAN", Jurisdiction.getUsername());
				taskuserService.editStateqd(pd);;
				str="success_";
			}
		}
		ResponseUtils.renderJson(response,str);
	}
	

	/**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			taskuserService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	/**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出task到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		//System.out.println( pd.getString("TEMPLATE_ID")+"TEMPLATE_ID");
		//pd_new.put("ID", pd.getString("TEMPLATE_ID"));
		//pd_new=templateService.findById(pd_new);
		//page.setPd(pd);
		pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
		List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表

		PageData pd_new_filed = new PageData();
		List<String> titles = new ArrayList<String>();
		for(int i=0;i<varList.size();i++){
			pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
			pd_new_filed=fieldService.findById(pd_new_filed);
			titles.add(pd_new_filed.getString("FIELDNAME"));	//1
		}
		dataMap.put("titles", titles);


		/*List<PageData> varOList = taskuserService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STARTADDR"));	    //1
			vpd.put("var2", varOList.get(i).getString("NUM"));	    //2
			vpd.put("var3", varOList.get(i).getString("CASEID"));	    //3
			vpd.put("var4", varOList.get(i).getString("TYPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("task1"));	    //5
			vpd.put("var6", varOList.get(i).getString("task2"));	    //6
			vpd.put("var7", varOList.get(i).getString("task3"));	    //7
			vpd.put("var8", varOList.get(i).getString("task4"));	    //8
			vpd.put("var9", varOList.get(i).getString("task5"));	    //9
			vpd.put("var10", varOList.get(i).getString("task6"));	    //10
			varList.add(vpd);
		}
		dataMap.put("varList", varList);*/
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	
	/**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excelField")
	public ModelAndView excelField() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出task到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("信息内容");	//2
		titles.add("总数量");	//3
		titles.add("配额数量");	//4
		dataMap.put("titles", titles);
		
		List<PageData>	varOList = taskuserService.listAllGroupByField(pd);	//列出customtemplatefield列表
		List<PageData> varList = new ArrayList<PageData>();
		
		PageData pd_group = new PageData(); 
		//str="<ul class=\"products-list product-list-in-box\" style=\"margin-top:10px;\">";
		String groupstr="";
		String value="";
		int i=0;
		String num="";
	    for(PageData pdfield:varOList){
	    	
	    	
	    	if(pdfield.get(pd.getString("FIELD").toUpperCase())!=null){
	    		value=pdfield.get(pd.getString("FIELD").toUpperCase()).toString();
	    		PageData vpd = new PageData();
	    		if(value==null||value.equals("")){
	    			value="kongzhi";
	    		}
				vpd.put("var1", value);	    //1
				vpd.put("var2", pdfield.get("COUNTER").toString());	    //2
				vpd.put("var3", "");	    //3
				varList.add(vpd);
				/*if(value.equals("")){
					value="kongzhi";
				}
		    	if(!groupstr.equals("")){
					groupstr=groupstr+",";
				}
		    	pd.put("GROUPNAME", value);
		    	pd_group=taskcustomService.listByGroupname(pd);
		    	if(pd_group!=null){
		    		//num=pd_group.get("NUM").toString();
		    	}
		    	
				groupstr=groupstr+value;*/
				
	    	}
	    	
		}
	    dataMap.put("varList", varList);

		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	
	/**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/importexcelField")
	public void importexcelField(String TEMPLATE_ID,String TABLENAME,HttpServletRequest request,HttpServletResponse response){
	
		try {
			String msg = "";
			Integer state = 0;
			// String fileUrl = "/files/excel/";

			PageData pd = new PageData();
			pd=this.getPageData();
			PageData pd_new = new PageData();
			PageData pd_new_cus = new PageData();
			PageData pd_new_filed = new PageData();
			PageData pd_search = new PageData();
			//pd_new.put("ID", TEMPLATE_ID);
			//pd_new=templateService.findById(pd_new);
			//page.setPd(pd);


			String insertstr="";
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("fileField");
			
			String fileName=multipartFile.getOriginalFilename();
			 //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
	       CommonsMultipartFile cf= (CommonsMultipartFile) multipartFile; //获取本地存储路径
	       File file = new  File("D:\\fileupload");
	       //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
	       if (!file.exists()) file.mkdirs();
	       //新建一个文件
	       File file1 = new File("D:\\fileupload\\" + new Date().getTime() + ".xlsx"); 
	       //将上传的文件写入新建的文件中
	       try {
	           cf.getFileItem().write(file1); 
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       int count=0;//导入条数
	       int countall=0;//导入条数
	       InputStream is = null; 
	       boolean isExcel2003 = true; 
	       try{
	          
	          
	          if(WDWUtil.isExcel2007(fileName)){
	              isExcel2003 = false;  
	          }
	          //根据新建的文件实例化输入流
	          is = new FileInputStream(file1);
	          //根据excel里面的内容读取客户信息 
	          //is.close();
	      }catch(Exception e){
	          e.printStackTrace();
	      }
	       List<Map> lineList = new ArrayList();     
			if(is!=null){
				HSSFWorkbook wb = null;
		           //当excel是2003时
	           if(isExcel2003){
	               wb = new HSSFWorkbook(is); 
	           }
	           //else{//当excel是2007时
	           //   wb = new XSSFWorkbook(is); 
	          // }
				int rowCount = 0;
				int fieldCount=0;
				boolean temp = false;
				try {
					HSSFSheet st = wb.getSheetAt(0); 
					int rowNum = st.getLastRowNum(); //获取Excel最后一行索引，从零开始，所以获取到的是表中最后一行行数减一
					int colNum = st.getRow(0).getLastCellNum();//获取Excel列数
					
					String lineColumn="",lineValues="",selectStr="";
					
					Map map=new HashMap();
					String value="",title;
					//System.out.println("rowNum"+rowNum);
					for(int r=1;r<=rowNum;r++){//读取每一行，第一行为标题，从第二行开始
						//System.out.println(r+":r");
						HSSFRow row0 = st.getRow(0); 
						HSSFRow row = st.getRow(r); 
						HSSFCell cell = row.getCell((short) (0));
						System.out.println(cell+"cell");
						title="";
						if(cell != null&&!cell.equals("")&&cell.getCellType()!=Cell.CELL_TYPE_BLANK){
							if(cell.getCellType()==0){
								title=String.valueOf(cell.getNumericCellValue());
							}else{
								title=cell.getStringCellValue();
							}
						}
						
						
						cell = row.getCell((short) (2));
						value="";
						if(cell != null&&!cell.equals("")&&cell.getCellType()!=Cell.CELL_TYPE_BLANK){
							if(cell.getCellType()==0){
								value=String.valueOf(cell.getNumericCellValue());
							}else{
								value=cell.getStringCellValue();
							}
						}
						if(value!=null&&!value.equals("")){
							map.put(title, value);
							lineList.add(map);
						}
						
						
					}	
				}catch (Exception e) {
					//System.out.println("第"+rowCount+"行出错");
					msg = "第"+rowCount+"行出错";
					e.printStackTrace();
				}
			}
			is.close();
			is=null;
			List<PageData>	varList = taskuserService.listAllGroupByField(pd);	//列出customtemplatefield列表
			JSONObject object = new JSONObject();
			//object_new.put("zxlbString", zxlbstr);
			
			PageData pd_group = new PageData(); 
			//str="<ul class=\"products-list product-list-in-box\" style=\"margin-top:10px;\">";
			String groupstr="",str="";
			String value="";
			int i=0;
			String num="0";
		    for(PageData pdfield:varList){
		    	if(pdfield.get(pd.getString("FIELD").toUpperCase())!=null){
		    		value=pdfield.get(pd.getString("FIELD").toUpperCase()).toString();
					if(value.equals("")){
						value="kongzhi";
					}
			    	if(!groupstr.equals("")){
						groupstr=groupstr+",";
					}
			    	pd.put("GROUPNAME", value);
			    	pd_group=taskcustomService.listByGroupname(pd);
			    	if(pd_group!=null){
			    		//num=pd_group.get("NUM").toString();
			    	}
			    	num="0";
			    	for(Map map:lineList){
			    		if(map.get(value)!=null){
				    		num=map.get(value).toString();
				    	}
			    	}
			    	
					groupstr=groupstr+value;
					str=str+"<li style=\"margin-left:5px;width:30%;float:left;\">";
					str=str+"<table style=\"width:100%;\">";
					str=str+"<tr style=\"height:30px;\">";
					str=str+"<td style=\"text-align:right;font-size:14px;\" title=\""+pdfield.getString(pd.getString("FIELD").toUpperCase())+"\">"+pdfield.getString(pd.getString("FIELD").toUpperCase())+":</td>"
							+ "<td style=\"font-size:14px;width:80px;\"><input type=\"number\" style=\"width:60px;\" id=\"group"+i+"\" max=\""+pdfield.get("COUNTER")+"\" value=\""+num+"\" min=\"1\">共<span style=\"color:red\">"+pdfield.get("COUNTER")+"</span>人</td>";
					str=str+"</tr>";
					str=str+"</table>";
					str=str+"</li>";
					i++;
		    	}
		    	
			}
			str=str+"<input type=\"hidden\" value=\""+groupstr+"\" id=\"groupstr\">";
			object.put("groupString",str);
			//ResponseUtils.renderJson(response,object.toString());
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject result = new JSONObject();
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				response.setCharacterEncoding("GBK");
				response.setContentType("text/html;charset=UTF-8");
				out.write("xcel数据格式有问题，导入失败");
				out.flush();
				out.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}    
	}
	
	
	
	/**导入数据
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goImp")
	public ModelAndView goImp()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		User user=(User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd=this.getPageData();
		////System.out.println(pd.getString("action"));
		mv.setViewName("xxgl/taskcustom/taskcustom_imp");
		mv.addObject("msg", "importexcel");
		mv.addObject("pd", pd);
		
		return mv;
	}	

	/**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/importexcel")
	public void insertUserInfo(String TEMPLATE_ID,String TABLENAME,HttpServletRequest request,HttpServletResponse response){
		logBefore(logger, Jurisdiction.getUsername()+"导出task到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha"));
		try {
			String msg = "";
			Integer state = 0;
			// String fileUrl = "/files/excel/";

			PageData pd = new PageData();
			pd=this.getPageData();
			PageData pd_new = new PageData();
			PageData pd_new_cus = new PageData();
			PageData pd_new_filed = new PageData();
			PageData pd_search = new PageData();
			//pd_new.put("ID", TEMPLATE_ID);
			//pd_new=templateService.findById(pd_new);
			//page.setPd(pd);
			pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
			List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//获取客户的所有字段
			String insertstr="";
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("files");
			
			 String fileName=multipartFile.getOriginalFilename();
			 //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
	       CommonsMultipartFile cf= (CommonsMultipartFile) multipartFile; //获取本地存储路径
	       File file = new  File("D:\\fileupload");
	       //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
	       if (!file.exists()) file.mkdirs();
	       //新建一个文件
	       File file1 = new File("D:\\fileupload\\" + new Date().getTime() + ".xlsx"); 
	       //将上传的文件写入新建的文件中
	       try {
	           cf.getFileItem().write(file1); 
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       int count=0;//导入条数
	       int countall=0;//导入条数
	       InputStream is = null; 
	       boolean isExcel2003 = true; 
	       try{
	          
	          
	          if(WDWUtil.isExcel2007(fileName)){
	              isExcel2003 = false;  
	          }
	          //根据新建的文件实例化输入流
	          is = new FileInputStream(file1);
	          //根据excel里面的内容读取客户信息 
	          //is.close();
	      }catch(Exception e){
	          e.printStackTrace();
	      }
		       
			if(is!=null){
				HSSFWorkbook wb = null;
		           //当excel是2003时
	           if(isExcel2003){
	               wb = new HSSFWorkbook(is); 
	           }
	           //else{//当excel是2007时
	           //   wb = new XSSFWorkbook(is); 
	          // }
				int rowCount = 0;
				int fieldCount=0;
				boolean temp = false;
				try {
					HSSFSheet st = wb.getSheetAt(0); 
					int rowNum = st.getLastRowNum(); //获取Excel最后一行索引，从零开始，所以获取到的是表中最后一行行数减一
					int colNum = st.getRow(0).getLastCellNum();//获取Excel列数
					
					String lineColumn="",lineValues="",selectStr="";
					List<Map> lineList = new ArrayList();
					Map map=new HashMap();
					String guid="";
					//System.out.println("rowNum"+rowNum);
					for(int r=1;r<=rowNum;r++){//读取每一行，第一行为标题，从第二行开始
						//System.out.println(r+":r");
						guid=UuidUtil.get32UUID();
						HSSFRow row0 = st.getRow(0); 
						HSSFRow row = st.getRow(r); 
						HSSFCell cell = row.getCell((short) (0));
						HSSFCell cell0 = row0.getCell((short) (0));
						System.out.println(cell+"cell");
						//if(cell == null||cell.equals("")||cell.getCellType()==Cell.CELL_TYPE_BLANK){
							//break;
						//}
						
						countall++;
						temp=false;
						rowCount = r;
						
						lineColumn="";
						lineValues="";
						selectStr="";
						lineList = new ArrayList();
						map=new HashMap();
						lineColumn="ID,createdate,createman,";
						insertstr="insert into "+TABLENAME+"(ID,createdate,createman,";
						for(int i=0;i<varList.size();i++){
							pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
							pd_new_filed=fieldService.findById(pd_new_filed);
							if(!insertstr.equals("insert into "+TABLENAME+"(ID,createdate,createman,")){insertstr=insertstr+",";}
							if(!lineColumn.equals("ID,createdate,createman,")){lineColumn=lineColumn+",";}
							insertstr=insertstr+pd_new_filed.getString("FIELD");
							lineColumn=lineColumn+pd_new_filed.getString("FIELD");
						}
						map.put("ID", "'"+guid+"'");
						map.put("createdate", "now()");
						map.put("createman", "'"+Jurisdiction.getUsername()+"'");
						
						if(!insertstr.equals("insert into "+TABLENAME+"(ID,")){
							insertstr=insertstr+") values('"+guid+"',now(),'"+Jurisdiction.getUsername()+"',";
						}
						int num=0;
						
						String titlename="";
						boolean boo=false;
						for(int i=0;i<varList.size();i++){
							boo=false;
							pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
							pd_new_filed=fieldService.findById(pd_new_filed);
							num=i;
							for(int k=0;k<varList.size();k++){
								cell0 = row0.getCell((short) (k));
								//System.out.println(cell0+"cell0");
								if(cell0!=null){
									titlename=cell0.getStringCellValue()==null?"":cell0.getStringCellValue();
									if(pd_new_filed.getString("FIELDNAME").equals(titlename.trim())){
										num=k;	
										boo=true;
									}
								}
								
							}
							String value="";
							if(boo){
								//if(cell!=null){
									
								//}
								cell = row.getCell((short) (num));
								if(cell != null&&!cell.equals("")&&cell.getCellType()!=Cell.CELL_TYPE_BLANK){
									
									//cell.setCellType(Cell.CELL_TYPE_STRING);
									//System.out.println(cell.getCellType());
									if(cell.getCellType()==0){
										value=String.valueOf(cell.getNumericCellValue());
									}else{
										value=cell.getStringCellValue();
									}
									//System.out.println(value);
									//cell.setCellType(Cell.CELL_TYPE_STRING);
									if(i!=0){
										insertstr=insertstr+",";
									}
									
									if(!selectStr.equals("")&&!value.equals("")){
										selectStr=selectStr+" and ";
									}
									
									if(pd_new_filed.getString("FIELDTYPE")!=null&&pd_new_filed.getString("FIELDTYPE").equals("date")){
										System.out.println(value+"field-"+pd_new_filed.getString("FIELD"));
										insertstr=insertstr+ "'"+value+"'";
										
										if(!value.equals("")){
											map.put(pd_new_filed.getString("FIELD"),  "'"+value+"'");
											selectStr+= pd_new_filed.getString("FIELD")+"='"+value+"'";
										}else{
											map.put(pd_new_filed.getString("FIELD"),  "NULL");
											selectStr+= pd_new_filed.getString("FIELD")+"=NULL";
										}
										
									}else{
										System.out.println(value+"field:"+pd_new_filed.getString("FIELD"));

										insertstr=insertstr+ "'"+value+"'";
										map.put(pd_new_filed.getString("FIELD"),"'"+value+"'");
										if(!value.equals("")){
											selectStr+= pd_new_filed.getString("FIELD")+"='"+value+"'";
										}
									}
									
								}
							}else{
								if(i!=0){
									insertstr=insertstr+",";
								}
								if(pd_new_filed.getString("FIELDTYPE")!=null&&pd_new_filed.getString("FIELDTYPE").equals("date")){
									System.out.println(value+"field-"+pd_new_filed.getString("FIELD"));
									insertstr=insertstr+ "''";
									map.put(pd_new_filed.getString("FIELD"),"null");
									
								}else{
									insertstr=insertstr+ "''";
									map.put(pd_new_filed.getString("FIELD"),"");
								}
								//selectStr+= pd_new_filed.getString("FIELD")+"=''";
							}
							temp=true;
							
						} 
						
						if(temp){//Excel完全没有问题
							insertstr=insertstr+")";
							lineList.add(map);
							
							//TABLENAME
							// webService.saveOrUpdateAll(userInfoList);
							lineColumn="";
							////System.out.println("insertstr"+insertstr);	
							Map<String,String> lineMap = lineList.get(0); 
							for (String key : lineMap.keySet()) { 
								if(!lineColumn.equals("")){lineColumn=lineColumn+",";
								lineValues=lineValues+",";}
								lineColumn +=key; 
								////System.out.println(lineMap.get(key)+"lineMap.get(key)");
								if(lineMap.get(key).equals("")){
									lineValues+="'' as "+key;
								}else{
									lineValues+=lineMap.get(key)+" as "+key;
								}
								
							} 
							////System.out.println(lineValues+"lineValues");
							pd.put("selectStr",selectStr);
							pd.put("lineValues",lineValues);
							pd.put("lineColumn",lineColumn);
							pd.put("tableName",TABLENAME);
							pd.put("lineList",lineList);
							pd_search=taskcustomService.findByField(pd);
							if(pd_search==null){
								count++;
								taskcustomService.save(pd);
							}
							state = 1;
							msg = "导入成功:总数据"+countall+"条,导入成功"+count+"条";
						}else{//Excel存在必填项为空的情况
							state = 2;
							msg = "Excel数据格式有问题，请下载表格";
						}
					}	
				}catch (Exception e) {
					//System.out.println("第"+rowCount+"行出错");
					msg = "第"+rowCount+"行出错";
					e.printStackTrace();
				}
			}
			is.close();
			is=null;
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if(count>0){//大于0
				
			}
			out.write(msg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONObject result = new JSONObject();
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				response.setCharacterEncoding("GBK");
				response.setContentType("text/html;charset=UTF-8");
				out.write("xcel数据格式有问题，导入失败");
				out.flush();
				out.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}    
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
