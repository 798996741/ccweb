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
import com.xxgl.utils.BitConverter;
import com.xxgl.utils.ResponseUtils;
import com.xxgl.utils.WDWUtil;
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.CustomtemplateManager;
import com.xxgl.service.mng.CustomtemplatefieldManager;
import com.xxgl.service.mng.FieldManager;
import com.xxgl.service.mng.ExetaskManager;
import com.xxgl.service.mng.NaireManager;
import com.xxgl.service.mng.NairetemplateManager;
import com.xxgl.service.mng.TaskcustomManager;
import com.xxgl.service.mng.TaskuserManager;
import com.xxgl.service.mng.TemplateManager;
import com.xxgl.service.mng.ZxlbManager;


/** 
 * 说明：参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 */
@Controller
@RequestMapping(value="/exetask")
public class ExetaskController extends BaseController {

	String menuUrl = "exetask/list.do"; //菜单地址(权限用)

	@Resource(name="taskuserService")
	private TaskuserManager taskuserService;
	
	@Resource(name="taskcustomService")
	private TaskcustomManager taskcustomService;
	
	
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="exetaskService")
	private ExetaskManager exetaskService;

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
	
	@Resource(name="naireService")
	private NaireManager naireService;
	
	@Resource(name="nairetemplateService")
	private NairetemplateManager nairetemplateService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	
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
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		pd = taskuserService.findById(pd);	//根据ID读取
		if(pd.getString("TABLENAME")!=null&&!pd.getString("TABLENAME").equals("")){
			taskuserService.dropTable(pd);
		}

		taskuserService.delete(pd);


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

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表task");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList =null;
		String action=pd.getString("action")==null?"":pd.getString("action");
		if(!action.equals("1")){
			//根据用户名或者任务
			PageData pd_zxyh = new PageData();
			String username=Jurisdiction.getUsername();
			pd.put("USERNAME", username);
			PageData pd_user=zxlbService.findByUsername(pd);
			if(pd_user==null){
				
				//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
				
				String keywords = pd.getString("keywords");				//关键词检索条件
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String dqdate=df.format(new Date());// new Date()为获取当前系统时间
				pd.put("COMPLETEDATE", dqdate);
				
				page.setPd(pd);
				varList = taskuserService.list(page);	//列出task列表
				float num=0;   
			//	DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
				String s = "";//返回的是String类型 
			//	DecimalFormat df =null;
				NumberFormat numberFormat = NumberFormat.getInstance();
				for(PageData pd_n:varList){
					String TABLENAME=pd_n.getString("TABLENAME");
					pd_n.put("FIELD", "ID");
					List<PageData> pd_table = exetaskService.listAll(pd_n);
					pd_n.put("NUM", pd_table.size());
					pd_n.put("HFWJ", "1");
					List<PageData> pd_table_iswj =exetaskService.listAllIsfp(pd_n);
					pd_n.put("HFWJ", "2");
					List<PageData> pd_table_issb =exetaskService.listAllIsfp(pd_n);
					num=(float)(pd_table_iswj.size()+pd_table_issb.size())/(float)pd_table.size()*100;
					
					numberFormat.setMaximumFractionDigits(2); 
					s=numberFormat.format(num);;
					pd_n.put("WCJD",s);
					pd_n.put("WCRW",pd_table_iswj.size()+pd_table_issb.size());
				}
				mv.addObject("varList", varList);
			}else{
				PageData pd_kh = new PageData();
				pd_zxyh.put("ZXYH", username);
				PageData pd_zxlb=zxlbService.findByZxyh(pd_zxyh);
				if(pd_zxlb!=null){
					String keywords = pd.getString("keywords");				//关键词检索条件
					if(null != keywords && !"".equals(keywords)){
						pd.put("keywords", keywords.trim());
					}
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
					String dqdate=df.format(new Date());// new Date()为获取当前系统时间
					pd.put("COMPLETEDATE", dqdate);
					page.setPd(pd);
					varList = taskuserService.list(page);	//列出task列表
					
					List<PageData>	zxList =new ArrayList();
					List<PageData>	taskList =new ArrayList();
					float num=0;      
					String s = "";//返回的是String类型 
					NumberFormat numberFormat = NumberFormat.getInstance();
					for(PageData pd_n:varList ){
						pd_kh.put("TABLENAME", pd_n.getString("TABLENAME"));
						pd_kh.put("FIELD", "a.ID,(@i:=@i+1) as ROWNO");
						pd_kh.put("ZXMAN", pd_zxlb.getString("ID"));
						zxList=taskcustomService.listAll(pd_kh);
						if(zxList.size()>0){
							
							pd_n.put("FIELD", "a.ID");
							pd_n.put("ZXMAN", pd_zxlb.getString("ID"));
							List<PageData> pd_table = exetaskService.listAll(pd_n);
							pd_n.put("NUM", pd_table.size());
							pd_n.put("HFWJ", "1");
							List<PageData> pd_table_iswj =exetaskService.listAllIsfp(pd_n);
							pd_n.put("HFWJ", "2");
							List<PageData> pd_table_issb =exetaskService.listAllIsfp(pd_n);
							num=(float)(pd_table_iswj.size()+pd_table_issb.size())/(float)pd_table.size()*100;
							////System.out.println(pd_table_issb.size()+"pd_table_issb"+pd_table_iswj.size()+"pd_table_iswj"+(pd_table_iswj.size()+pd_table_issb.size())+"dddd");
							numberFormat.setMaximumFractionDigits(2); 
							s=numberFormat.format(num);
							pd_n.put("WCJD",s);
							pd_n.put("WCRW",pd_table_iswj.size()+pd_table_issb.size());
							taskList.add(pd_n);
						}
					}
					mv.addObject("varList", taskList);
					
				}
			
				
			}
			
			
		}
		
		mv.setViewName("xxgl/exetask/exetask_list");
		
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/wclist")
	public ModelAndView wclist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表task");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	varList =null;
		//根据用户名或者任务
		PageData pd_zxyh = new PageData();
		String username=Jurisdiction.getUsername();
		pd.put("USERNAME", username);
		PageData pd_user=zxlbService.findByUsername(pd);
		if(pd_user==null){
			
			
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String dqdate=df.format(new Date());// new Date()为获取当前系统时间
			pd.put("STATE", "2");
			
			page.setPd(pd);
			varList = taskuserService.list(page);	//列出task列表
			
			
			float num=0;   
		//	DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
			String s = "";//返回的是String类型 
		//	DecimalFormat df =null;
			NumberFormat numberFormat = NumberFormat.getInstance();
			for(PageData pd_n:varList){
				String TABLENAME=pd_n.getString("TABLENAME");
				pd_n.put("FIELD", "a.ID");
				List<PageData> pd_table = exetaskService.listAll(pd_n);
				pd_n.put("NUM", pd_table.size());
				pd_n.put("HFWJ", "1");
				List<PageData> pd_table_iswj =exetaskService.listAllIsfp(pd_n);
				pd_n.put("HFWJ", "2");
				List<PageData> pd_table_issb =exetaskService.listAllIsfp(pd_n);
				num=(float)(pd_table_iswj.size()+pd_table_issb.size())/(float)pd_table.size()*100;
				
				numberFormat.setMaximumFractionDigits(2); 
				s=numberFormat.format(num);;
				pd_n.put("WCJD",s);
				pd_n.put("WCRW",pd_table_iswj.size()+pd_table_issb.size());
			}
			mv.addObject("varList", varList);
		}else{
			PageData pd_kh = new PageData();
			pd_zxyh.put("ZXYH", username);
			PageData pd_zxlb=zxlbService.findByZxyh(pd_zxyh);
			if(pd_zxlb!=null){
				String keywords = pd.getString("keywords");				//关键词检索条件
				if(null != keywords && !"".equals(keywords)){
					pd.put("keywords", keywords.trim());
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String dqdate=df.format(new Date());// new Date()为获取当前系统时间
				pd.put("STATE", "2");
				page.setPd(pd);
				varList = taskuserService.list(page);	//列出task列表
				
				List<PageData>	zxList =new ArrayList();
				List<PageData>	taskList =new ArrayList();
				float num=0;      
				String s = "";//返回的是String类型 
				NumberFormat numberFormat = NumberFormat.getInstance();
				for(PageData pd_n:varList ){
					pd_kh.put("TABLENAME", pd_n.getString("TABLENAME"));
					pd_kh.put("FIELD", "a.ID,(@i:=@i+1) as ROWNO");
					pd_kh.put("ZXMAN", pd_zxlb.getString("ID"));
					zxList=taskcustomService.listAll(pd_kh);
					if(zxList.size()>0){
						
						pd_n.put("FIELD", "a.ID");
						pd_n.put("ZXMAN", pd_zxlb.getString("ID"));
						List<PageData> pd_table = exetaskService.listAll(pd_n);
						pd_n.put("NUM", pd_table.size());
						pd_n.put("HFWJ", "1");
						List<PageData> pd_table_iswj =exetaskService.listAllIsfp(pd_n);
						pd_n.put("HFWJ", "2");
						List<PageData> pd_table_issb =exetaskService.listAllIsfp(pd_n);
						num=(float)(pd_table_iswj.size()+pd_table_issb.size())/(float)pd_table.size()*100;
						
						numberFormat.setMaximumFractionDigits(2); 
						s=numberFormat.format(num);
						pd_n.put("WCJD",s);
						pd_n.put("WCRW",pd_table_iswj.size()+pd_table_issb.size());
						taskList.add(pd_n);
					}
				}
				mv.addObject("varList", taskList);
				
			}	
		}
		mv.setViewName("xxgl/exetask/exetask_wclist");
		mv.addObject("STATE", "2");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	
	/**获取客户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getTask")
	@ResponseBody
	public void getTask(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		List<PageData>	varList =null;
		//根据用户名或者任务
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		String str=this.getMsg(pd);
		ResponseUtils.renderJson(response,str);
	}
	
	
	
	
	
	
	public String getMsg(PageData pd){
		JSONObject object_new = new JSONObject();
		try{
			PageData pd_kh = new PageData();
			PageData pd_zxyh = new PageData();
			List<PageData>	varList = null;
			List<PageData>	zxList = null;
			String username=Jurisdiction.getUsername();
			String rownum=pd.getString("ROWNO")==null?"1":pd.getString("ROWNO");
			int ROWNO=Integer.parseInt(rownum);
				
			boolean boo=false;
			PageData pd_new = new PageData();
			pd_new=taskuserService.findById(pd);
			System.out.println(pd_new.getString("FPTYPE")+":FPTYPE");
			PageData pd_new_cus = new PageData();
			String TEMPLATE_ID=pd.getString("TEMPLATE_ID");
			String tableName=pd.getString("TABLENAME");
			//pd_new.put("ID", TEMPLATE_ID);
			//pd_new=templateService.findById(pd_new);
			//page.setPd(pd);
			pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
			pd_new_cus.put("ISSHOW", "1");
			varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
			////System.out.println(pd.getString("ZXMAN")+":ZXMAN");
			//List<PageData>	customList =exetaskService.listCustom(pd);
	
			String tablestr="";
			String zdstr="",fieldnamestr="",istelstr="",fieldtypestr="";;
			String tbodystr="";
			boolean boo_notWj=false;
			//PageData pd_new_filed = new PageData();
			PageData cus_one = new PageData();
			int num=varList.size();
			//if(num>=7){
				//num=7;
			//}
			for(int i=0;i<num;i++){
				//pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
				//pd_new_filed=fieldService.findById(pd_new_filed);
				if(!zdstr.equals("")){
					zdstr=zdstr+",";
					fieldnamestr=fieldnamestr+",";
					istelstr=istelstr+",";
					fieldtypestr=fieldtypestr+",";
				}
				zdstr=zdstr+varList.get(i).getString("FIELD");
				fieldnamestr=fieldnamestr+varList.get(i).getString("FIELDNAME");
				istelstr=istelstr+varList.get(i).getString("ISTEL");
				fieldtypestr=fieldtypestr+varList.get(i).getString("FIELDTYPE");
			}
			if(!zdstr.equals("")){
				zdstr=zdstr+",(@i:=@i+1) as ROWNO";
			}
			pd_zxyh.put("ZXYH", username);
			PageData pd_zxlb=zxlbService.findByZxyh(pd_zxyh);
			if(pd_zxlb!=null){
				if(pd_new!=null&&pd_new.getString("FPTYPE")!=null&&pd_new.getString("FPTYPE").equals("2")){
					pd_kh.put("TABLENAME", pd.getString("TABLENAME"));
					pd_kh.put("FIELD", zdstr.toUpperCase()+",a.ID,HFWJ,HFRESULT,HFDATE");
					////System.out.println(zdstr.toUpperCase()+",ID,HFWJ,HFRESULT,HFDATE");
					if(!username.equals("admin")){
						pd_kh.put("ZXMAN", pd_zxlb.getString("ID"));
					}
					if(pd.getString("CUS_ID")!=null&&!pd.getString("CUS_ID").equals("")){
						zxList=taskcustomService.listAll(pd_kh);
						for(PageData pd_d:zxList){
							if(pd_d.getString("ID")!=null&&pd_d.getString("ID").equals(pd.getString("CUS_ID"))){
								cus_one=pd_d;
							}
						}
					}else{	
						zxList=taskcustomService.listAllBynotWj(pd_kh);
						if(zxList.size()<=0){
							cus_one=this.getCustom(pd, pd_zxlb.getString("ID"), username);
						}else{
							boo_notWj=true;
							zxList=taskcustomService.listAll(pd_kh);
							cus_one=zxList.get(ROWNO-1);
						}
					}	
				}else{
					pd_kh.put("TABLENAME", pd.getString("TABLENAME"));
					pd_kh.put("FIELD", zdstr.toUpperCase()+",a.ID,HFWJ,HFRESULT,HFDATE");
					System.out.println(zdstr.toUpperCase()+",ID,HFWJ,HFRESULT,HFDATE");
					if(!username.equals("admin")){
						pd_kh.put("ZXMAN", pd_zxlb.getString("ID"));
					}
					zxList=taskcustomService.listAll(pd_kh);
					
					if(zxList.size()>0){
						cus_one=zxList.get(ROWNO-1);
					}	
				}
				
			}else{
				pd_kh.put("TABLENAME", pd.getString("TABLENAME"));
				pd_kh.put("FIELD", zdstr.toUpperCase()+",a.ID,HFWJ,HFRESULT,HFDATE");
				zxList=taskcustomService.listAll(pd_kh);
				if(zxList.size()>0){
					cus_one=zxList.get(ROWNO-1);
				}	
			}	
			
			String userstr="",listr="",rwqdstr="";
			pd.put("FIELD", "*");
			String tablecusstr="";
			String theadstr="<thead><tr>";
			String tbodycusstr="";
			PageData pd_new_filed_cus = new PageData();
			
			//if(num>=5){
				//num=5;
		//	}
			for(int i=0;i<num;i++){
				//pd_new_filed_cus.put("ID", varList.get(i).getString("FIELD_ID"));
				//pd_new_filed_cus=fieldService.findById(pd_new_filed_cus);
				theadstr+="<th>"+varList.get(i).getString("FIELDNAME")+"</th>";
			}
			theadstr+="<th>是否完成</th>";
			theadstr+="<th>回访结果</th>";
			theadstr+="<th>回访时间</th>";
			theadstr=theadstr+"</tr></thead>";
			tbodycusstr="<tbody><tr>";
			////System.out.println("zxLis00t:"+zxList.get(ROWNO-1));
			String fieldvalue="";
			PageData pd_dictionaries=null;
			for(PageData pddata:zxList){
				for(int i=0;i<num;i++){
					boo=true;
					fieldvalue=(pddata.get(varList.get(i).getString("FIELD").toUpperCase())==null?"":pddata.get(varList.get(i).getString("FIELD").toUpperCase())).toString();
					//System.out.println(varList.get(i).getString("ISTEL"));
					if(fieldvalue.equals("null")){
						fieldvalue="";
					}
					//pd_new_filed_cus.put("ID", varList.get(i).getString("FIELD_ID"));
					//pd_new_filed_cus=fieldService.findById(pd_new_filed_cus);
					if(varList.get(i).getString("ISTEL")!=null&&varList.get(i).getString("ISTEL").equals("1")){
						tbodycusstr+="<td  onclick=\"zxrw("+pddata.get("ROWNO")+")\"><img src=\"static/login/images/icon_09.png\" style=\"width:20px;\"/>&nbsp;&nbsp;&nbsp;"+fieldvalue+"</td>";
					}else{
						tbodycusstr+="<td>"+fieldvalue+"</td>";
					}
					
				}
				if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("1")){
					tbodycusstr+="<td style=\"color:red\">回访成功</td>";
				}else if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("2")){
					tbodycusstr+="<td style=\"color:blue\">回访失败</td>";
				}else{
					tbodycusstr+="<td style=\"color:blue\">未回访</td>";
				}
				pd_dictionaries=new PageData(); 
				if(pddata.get("HFRESULT")!=null){
					pddata.put("DICTIONARIES_ID", pddata.get("HFRESULT"));
					pd_dictionaries=dictionariesService.findById(pddata);
				}
				if(pd_dictionaries!=null){
					tbodycusstr+="<td>"+(pd_dictionaries.getString("NAME")==null?"":pddata.get("NAME"))+"</td>";
				}else{
					tbodycusstr+="<td></td>";
				}
				
				tbodycusstr+="<td>"+(pddata.get("HFDATE")==null?"":pddata.get("HFDATE"))+"</td>";
				tbodycusstr=tbodycusstr+"</tr></tbody>";
			}
			tablecusstr=theadstr+tbodycusstr;
			
			String[] filename=fieldnamestr.split(",");
			String[] zd=zdstr.split(",");
			String[] istel=istelstr.split(",");
			String[] fieldtype=fieldtypestr.split(",");
			String value="";
			String str="";
			String last="0";
			if((ROWNO)==zxList.size()){
				if(boo_notWj){
					last="2";
				}else{
					last="1";
				}
			}
			userstr=userstr+"<div class=\"cs-bgx\" style=\"text-align:center\"><img class=\"profile-user-img img-responsive img-circle\" src=\"static/login/images/icon-touxiang.png\" alt=\"User profile picture\">";
			userstr=userstr+"<input type=\"hidden\" id=\"cusnum\" name=\"cusnum\" value=\""+ROWNO+"\">"
					+ "<input type=\"hidden\" id=\"last\" name=\"last\" value=\""+last+"\">"
							+ "<input type=\"hidden\" id=\"CUS_ID\" name=\"CUS_ID\" value=\""+cus_one.getString("ID")+"\">";
			String name="";
			String tel="";
			for(int i=0;i<filename.length;i++){
				if(zd[i].equals("name")){
					name=cus_one.getString(zd[i].toUpperCase());
					userstr=userstr+"<h3 class=\"profile-username text-center\">"+name+"&nbsp;&nbsp;&nbsp;&nbsp;<i onclick=\"editCus('"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+cus_one.getString("ID")+"','"+pd.getString("TABLENAME")+"');\" style=\"cursor:pointer\" class=\"ace-icon fa fa-pencil-square-o bigger-120\" title=\"编辑\"></i></h3>";
				}else{
					str="";
					if(istel[i].equals("1")){
						str="是电话";
						tel=cus_one.getString(zd[i].toUpperCase());
						if(tel!=null&&!tel.equals("null")){
							userstr=userstr+"<a class=\"btn\"><img src=\"static/login/images/icon_09.png\" style=\"width:20px;\"/>&nbsp;&nbsp;&nbsp;<b>"+tel+"</b>&nbsp;&nbsp;&nbsp;</a>";	
						}
						
					}else{
						value="";
						listr=listr+"<li  style=\"border-bottom:1px solid #ddd;min-height:30px;word-wrap:break-word;vertical-align:middle;line-height:30px;height:auto;\" onclick=\"dq('"+cus_one.getString("rowno")+"')\">";
						value=String.valueOf(cus_one.get(zd[i].toUpperCase()));
						if(value==null||value.equals("null")){
							value="";
						}
						if(fieldtype[i].indexOf("date")>=0&&value.length()>=10){
							value=value.substring(0, 10);
						}
						listr=listr+"<b style=\"width:30%\">"+filename[i]+":</b><a style=\"width:68%;word-wrap:break-word;\">"+value+str+"</a>";
						listr=listr+"</li>";
					}	
				}
				
			}
			
			
			
			userstr=userstr+"</div><ul class=\"list-group list-group-unbordered\" id=\"listr\">";
			userstr=userstr+listr;
			userstr=userstr+"</ul>";
			
			//获取问卷内容
			
			//List<PageData> pd_tm=naireService.listAll(pd);
			
			
			PageData pd_naire = new PageData();
			
			
			JSONObject object = new JSONObject();
			
			String customString="";
			if(boo){
				//customString=theadstr+tbodystr;
			}else{
				//customString="<thead><tr><td style=\"color:red\">暂无数据</td></tr></thead>";
			}
			object_new.put("userstr", userstr);
			
			object_new.put("customString", tablecusstr);
			
			//object_new.put("customString",customString);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return object_new.toString();
	}
	
	public PageData getCustom(PageData pd,String zxman,String fpman) throws Exception{
		PageData pd_cus_fp=new PageData();
		pd_cus_fp.put("TASKID",  pd.getString("ID"));
		PageData pd_cus=null;
		List<PageData> pd_cus_fplist=taskcustomService.listAllCusFp(pd_cus_fp);
		if(pd_cus_fplist.size()>0){
			String field=pd_cus_fplist.get(0).getString("FIELD");
			String groupname=pd_cus_fplist.get(0).getString("GROUPNAME");
			String tablename=pd_cus_fplist.get(0).getString("TABLENAME");
			String TJ="";
			if(groupname.equals("kongzhi")){
				TJ="and "+field+" is null  and zxman is null";
			}else{
				TJ="and "+field+"='"+groupname+"' and zxman is null";
			}
			
			pd_cus_fplist.get(0).put("TJ", TJ);
			pd_cus=taskcustomService.getCusByrandom(pd_cus_fplist.get(0));
			if(pd_cus!=null){
				pd_cus_fp.put("GROUPNAME",  groupname);
				taskcustomService.editCusFp(pd_cus_fp);
				pd_cus.put("ZXMAN", zxman);
				pd_cus.put("FPMAN", fpman);
				pd_cus.put("TABLENAME", tablename);
				taskcustomService.edit(pd_cus);
			}
		}
		return pd_cus;
	}
	
	
	/**获取客户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getCustomtable")
	@ResponseBody
	public void getCustomtable(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		List<PageData>	varList =null;
		//根据用户名或者任务
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		String str=this.getCustomMsg(pd);
		ResponseUtils.renderJson(response,str);
	}
	

	
	public String getCustomMsg(PageData pd){
		JSONObject object_new = new JSONObject();
		try{
			PageData pd_kh = new PageData();
			PageData pd_zxyh = new PageData();
			List<PageData>	varList = null;
			List<PageData>	zxList = null;
			String username=Jurisdiction.getUsername();
			String rownum=pd.getString("ROWNO")==null?"1":pd.getString("ROWNO");
			int ROWNO=Integer.parseInt(rownum);
			PageData pd_new = new PageData();
			PageData pd_new_cus = new PageData();
			String TEMPLATE_ID=pd.getString("TEMPLATE_ID");
			String tableName=pd.getString("TABLENAME");
			//pd_new.put("ID", TEMPLATE_ID);
			//pd_new=templateService.findById(pd_new);
			//page.setPd(pd);
			pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
			pd_new_cus.put("ISSHOW","1");
			varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
			////System.out.println(pd.getString("ZXMAN")+":ZXMAN");
			//List<PageData>	customList =exetaskService.listCustom(pd);
	
			String tablestr="";
			String zdstr="",fieldnamestr="",istelstr="",fieldtypestr="";;
			String tbodystr="";
			boolean boo=false;
			//PageData pd_new_filed = new PageData();
			PageData cus_one = new PageData();
			int num=varList.size();
			//if(num>=7){
			//	num=7;
			//}
			for(int i=0;i<num;i++){
				//pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
				//pd_new_filed=fieldService.findById(pd_new_filed);
				if(!zdstr.equals("")){
					zdstr=zdstr+",";
					fieldnamestr=fieldnamestr+",";
					istelstr=istelstr+",";
					fieldtypestr=fieldtypestr+",";
				}
				zdstr=zdstr+varList.get(i).getString("FIELD");
				fieldnamestr=fieldnamestr+varList.get(i).getString("FIELDNAME");
				istelstr=istelstr+varList.get(i).getString("ISTEL");
				fieldtypestr=fieldtypestr+varList.get(i).getString("FIELDTYPE");
			}
			if(!zdstr.equals("")){
				zdstr=zdstr+",(@i:=@i+1) as ROWNO";
			}
			pd_zxyh.put("ZXYH", username);
			PageData pd_zxlb=zxlbService.findByZxyh(pd_zxyh);
			if(pd_zxlb!=null){
				pd_kh.put("TABLENAME", pd.getString("TABLENAME"));
				pd_kh.put("FIELD", zdstr.toUpperCase()+",a.ID,HFWJ,HFRESULT,HFDATE");
				//System.out.println(zdstr.toUpperCase()+",ID,HFWJ,HFRESULT,HFDATE");
				if(!username.equals("admin")){
					pd_kh.put("ZXMAN", pd_zxlb.getString("ID"));
				}
				zxList=taskcustomService.listAll(pd_kh);
				
				if(zxList.size()>0){
					cus_one=zxList.get(ROWNO-1);
				}	
				////System.out.println(cus_one+"d"+zxList.size());
			}	else{
				pd_kh.put("TABLENAME", pd.getString("TABLENAME"));
				pd_kh.put("FIELD", zdstr.toUpperCase()+",a.ID,HFWJ,HFRESULT,HFDATE");
				zxList=taskcustomService.listAll(pd_kh);
				if(zxList.size()>0){
					cus_one=zxList.get(ROWNO-1);
				}	
			}
			
			String listr="",rwqdstr="";
			pd.put("FIELD", "*");
			String tablecusstr="";
			String theadstr="<thead><tr>";
			String tbodycusstr="";
			PageData pd_new_filed_cus = new PageData();
			
			//if(num>=5){
			//	num=5;
			//}
			for(int i=0;i<num;i++){
				//pd_new_filed_cus.put("ID", varList.get(i).getString("FIELD_ID"));
				//pd_new_filed_cus=fieldService.findById(pd_new_filed_cus);
				theadstr+="<th style=\"min-width:100px;\" title=\""+varList.get(i).getString("FIELDNAME")+"\">"+varList.get(i).getString("FIELDNAME")+"</th>";
			}
			theadstr+="<th>是否完成</th>";
			theadstr+="<th>回访结果</th>";
			theadstr+="<th>回访时间</th>";
			if(pd_zxlb==null){
				theadstr+="<th>坐席人员</th>";
			}
			theadstr=theadstr+"</tr></thead>";
			tbodycusstr="<tbody><tr>";
			////System.out.println("zxLis00t:"+zxList.get(ROWNO-1));
			String fieldvalue="";
			PageData pd_dictionaries=null;
			for(PageData pddata:zxList){
				for(int i=0;i<num;i++){
					boo=true;
					fieldvalue=(pddata.get(varList.get(i).getString("FIELD").toUpperCase())==null?"":pddata.get(varList.get(i).getString("FIELD").toUpperCase())).toString();
					//System.out.println(varList.get(i).getString("ISTEL")+"ISTEL");
					if(fieldvalue.equals("null")){
						fieldvalue="";
					}
					if(varList.get(i).getString("ISTEL")!=null&&varList.get(i).getString("ISTEL").equals("1")){
						tbodycusstr+="<td title=\""+fieldvalue+"\" style=\"cursor:pointer\" onclick=\"zxrw("+pddata.get("ROWNO")+",'"+pddata.get("ID")+"')\"><img src=\"static/login/images/icon_09.png\" style=\"width:20px;\"/>&nbsp;&nbsp;&nbsp;"+fieldvalue+"</td>";
					}else{
						tbodycusstr+="<td title=\""+fieldvalue+"\" >"+fieldvalue+"</td>";
					}
					
				}
				if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("1")){
					tbodycusstr+="<td style=\"color:red\">回访成功</td>";
				}else if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("2")){
					tbodycusstr+="<td style=\"color:blue\">回访失败</td>";
				}else if(pddata.get("HFWJ")!=null&&pddata.get("HFWJ").equals("3")){
					tbodycusstr+="<td style=\"color:blue\">下次回访</td>";
				}else{
					tbodycusstr+="<td style=\"color:green\">未回访</td>";
				}
				pd_dictionaries=new PageData(); 
				if(pddata.get("HFRESULT")!=null){
					pddata.put("DICTIONARIES_ID", pddata.get("HFRESULT"));
					pd_dictionaries=dictionariesService.findById(pddata);
				}
				if(pd_dictionaries!=null){
					tbodycusstr+="<td>"+(pd_dictionaries.getString("NAME")==null?"":pd_dictionaries.get("NAME"))+"</td>";
				}else{
					tbodycusstr+="<td></td>";
				}
				tbodycusstr+="<td>"+(pddata.get("HFDATE")==null?"":pddata.get("HFDATE"))+"</td>";
				if(pd_zxlb==null){
					tbodycusstr+="<td>"+(pddata.get("ZXXM")==null?"":pddata.get("ZXXM"))+"</td>";
				}
				tbodycusstr=tbodycusstr+"</tr></tbody>";
			}
			tablecusstr=theadstr+tbodycusstr;
		
			
			//获取问卷内容
			
			//List<PageData> pd_tm=naireService.listAll(pd);
			
			
			PageData pd_naire = new PageData();
			
			
			JSONObject object = new JSONObject();
			
			String customString="";
			if(boo){
				//customString=theadstr+tbodystr;
			}else{
				//customString="<thead><tr><td style=\"color:red\">暂无数据</td></tr></thead>";
			}
			
			object_new.put("customString", tablecusstr);
			
			//object_new.put("customString",customString);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return object_new.toString();
	}
	
	
	@RequestMapping(value="/getTmByNaire")
	public void getTmByNaire(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		pd = this.getPageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		
		List<PageData>	varList =null;
		List<PageData>	varList_field =null;		
		//获取客户模板信息
		PageData pd_new_cus = new PageData();
		pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
		//pd_new_cus.put("ISSHOW","1");
		varList_field = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
		
		//获取当前客户的信息
		pd_new_cus.put("FIELD", "*");
		pd_new_cus.put("ID", pd.getString("CUS_ID"));
		pd_new_cus.put("TABLENAME",  pd.getString("TABLENAME"));
		PageData pd_cus =taskcustomService.findFieldByID(pd_new_cus);
		
		
		
		//根据用户名或者任务
		Map<String,Object> map = new HashMap<String,Object>();
		
		PageData pd_task = new PageData();
		pd_task=taskuserService.findById(pd);
		String state="";
		if(pd_task!=null){
			state=pd_task.getString("STATE")==null?"":pd_task.getString("STATE");
		}
		
		varList = naireService.listAll(pd);	 //所有获取题目列表
		pd_new.put("ID", pd.getString("NAIRE_TEMPLATE_ID"));
		////System.out.println(pd.getString(""));
		PageData  pd_naire_template=nairetemplateService.findById(pd_new);
		
		
		
		//List<PageData> answerList=naireService.listAllAnswer(pd); //已回答题目列表
		String rownum=pd.getString("ROWNO")==null?"1":pd.getString("ROWNO");
		int ROWNO=Integer.parseInt(rownum);
		String tmstr="",listr="",nexttmstr="";
		
		//是否有下一题
		PageData  pd_naire_next= new PageData();
		String NEXT_ID="";
		String ANSWER="";
		if(pd.getString("ANSWER")!=null){
			pd_naire_next=naireService.findByNextAnswer(pd);
			if(pd_naire_next!=null){
				NEXT_ID=pd_naire_next.getString("NEXT_ID");
			}
		}
		
		tmstr="<style>li{line-height:10px;}</style><ul class=\"infomation-r\">";
		nexttmstr=nexttmstr+"<div class=\"xytfgx\"> </div>";
		nexttmstr=nexttmstr+"<ul class=\"infomation-r\">";
		if(ROWNO==1){
			String kcb=pd_naire_template.getString("OPENINGREMARKS")==null?"":pd_naire_template.getString("OPENINGREMARKS");
			for(PageData pddata:varList_field){
				////System.out.println("kcb:"+pddata.getString("FIELD").toLowerCase());
				if(kcb.indexOf(pddata.getString("FIELD").toUpperCase())>=0&&pd_cus!=null){
					kcb=kcb.replace(pddata.getString("FIELD").toUpperCase(), pd_cus.getString(pddata.getString("FIELD").toUpperCase())==null?"":pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
				}
				if(kcb.indexOf(pddata.getString("FIELD").toLowerCase())>=0&&pd_cus!=null){
					////System.out.println(pddata.getString("FIELD").toLowerCase()+"9999"+pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
					kcb=kcb.replace(pddata.getString("FIELD").toLowerCase(), pd_cus.getString(pddata.getString("FIELD").toUpperCase())==null?"":pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
				}
			}	
			tmstr=tmstr+"<li class=\"infomation-r-kcb\">开场白："+kcb+"</li>";
			//}
		}
		PageData  pd_naire=null;
		//System.out.println(varList+"ROWNO:"+ROWNO);
		String isjs="0";
		String selected1="",selected2="",selected3="",stylestr="style=\"display:none\"";
		List<Dictionaries> dictList=dictionariesService.listAllDict("ca1e395955174092bba6f80133d48242");
		PageData  pd_custom=null;
		PageData pd_answer_s = new PageData();
		//pd_answer_s.put("NAIRE_ID", pd_naire.getString("ID"));
		pd_answer_s.put("CUS_ID", pd.getString("CUS_ID"));
		pd_answer_s.put("FIELD","ID,FPMAN,ZXMAN,HFWJ,HFDATE,HFRESULT,CREATEDATE,CREATEMAN,HFREMARK,HFJL");
		pd_answer_s.put("TABLENAME", pd.getString("TABLENAME"));
		pd_answer_s.put("ID", pd.getString("CUS_ID"));
		pd_custom=taskcustomService.findFieldByID(pd_answer_s);
			
		if((ROWNO-1)==varList.size()){
			
			tmstr=tmstr+"<li>回访结果：";
			tmstr=tmstr+"<select onchange=\"changeHFWJ()\" id=\"HFWJ\" name=\"HFWJ\" style=\"width:40%;height:30px;\" >";
			if(pd_custom!=null&&pd_custom.getString("HFWJ")!=null&&pd_custom.getString("HFWJ").equals("1")){	
				selected1="selected";
				stylestr="style=\"display:none\"";
			}
			if(pd_custom.getString("HFWJ")!=null&&pd_custom.getString("HFWJ").equals("2")){	
				selected2="selected";
				stylestr="style=\"display:block\"";
			}
			if(pd_custom.getString("HFWJ")!=null&&pd_custom.getString("HFWJ").equals("3")){	
				selected3="selected";
				stylestr="style=\"display:none\"";
			}
			tmstr=tmstr+"<option value=\"1\" "+selected1+">成功</option>";
			tmstr=tmstr+"<option value=\"2\" "+selected2+">失败</option>";
			tmstr=tmstr+"<option value=\"3\" "+selected2+">下次回访</option>";
			tmstr=tmstr+"</select>";
			tmstr=tmstr+"</li>";
			
			tmstr=tmstr+"<li id=\"failid\" "+stylestr+">失败原因：<select id=\"HFRESULT\" name=\"HFRESULT\" style=\"width:40%;height:30px;\" >";
			for(int i=0;i<dictList.size();i++){
				
				if(pd_custom.getString("HFRESULT")!=null&&pd_custom.getString("HFRESULT").equals(dictList.get(i).getDICTIONARIES_ID())){	
					tmstr=tmstr+"<option value=\""+dictList.get(i).getDICTIONARIES_ID()+"\" selected>"+dictList.get(i).getNAME()+"</option>";
				}else{
					tmstr=tmstr+"<option value=\""+dictList.get(i).getDICTIONARIES_ID()+"\">"+dictList.get(i).getNAME()+"</option>";
				}
			}
			tmstr=tmstr+"</select>";
			tmstr=tmstr+"</li>";
			
			tmstr=tmstr+"<li style=\"height:100px;\"  >";
			tmstr=tmstr+"回访备注：";
			tmstr=tmstr+"<textarea style=\"width:40%;height:100px;\" id=\"HFREMARK\" name=\"HFREMARK\">"+(pd_custom.getString("HFREMARK")==null?"":pd_custom.getString("HFREMARK"))+"</textarea>";
			tmstr=tmstr+"</li>";
			//System.out.println(state+"state:");
			if(!state.equals("2")){
				tmstr=tmstr+"<li>";
				tmstr=tmstr+"<button type=\"button\" onclick=\"hfresult()\" class=\"btn btn-block btn-info\" style=\"width:100px;float:left\">回访结果保存</button>";
				tmstr=tmstr+"</li>";
			}
			
			
			if(pd_naire_template.getString("CONCLUDINGREMARKS")!=null){
				tmstr=tmstr+"<li  style=\"font-weight:bold;color:red;line-height:70px\">结束语："+pd_naire_template.getString("CONCLUDINGREMARKS")+"</li>";
			}
			isjs="1";
		}
		//PageData  pd_custom=null;
		if((ROWNO-1)!=varList.size()){
			if(NEXT_ID.equals("")){
				pd_naire=varList.get(ROWNO-1);
			}else{
				PageData pd_next = new PageData();
				PageData pd_naire_var = new PageData();
				pd_next.put("ID",NEXT_ID);
				pd_naire=naireService.findById(pd_next);
				if(pd_naire!=null){
					
				}
				for(int i=0;i<varList.size();i++){
					pd_naire_var=varList.get(i);
					if(pd_naire_var!=null&&pd_naire!=null&&pd_naire.getString("ID").equals(pd_naire_var.getString("ID"))){
						ROWNO=Integer.parseInt((String.valueOf(pd_naire_var.get("CODE"))));
					}
					
				}
			}
			
			PageData pd_answer_res =null;
			if(pd_naire!=null){
				
				pd_answer_s.put("NAIRE_ID", pd_naire.getString("ID"));
				pd_answer_s.put("CUS_ID", pd.getString("CUS_ID"));
				pd_answer_s.put("FIELD","ID,FPMAN,ZXMAN,HFWJ,HFDATE,HFRESULT,CREATEDATE,CREATEMAN,HFREMARK,HFJL");
				pd_answer_s.put("TABLENAME", pd.getString("TABLENAME"));
				pd_answer_s.put("ID", pd.getString("CUS_ID"));
				pd_answer_res =exetaskService.findByAnswer(pd_answer_s);
				//pd_custom=taskcustomService.findFieldByID(pd_answer_s);
			}
			
			if(pd_naire!=null){
			
				tmstr=tmstr+"<li class=\"ex-pd\" style=\"line-height:20px;\">"+pd_naire.getString("TYPENAME")+"</li>";
				//nexttmstr=nexttmstr+"<li class=\"ex-pd\">"+pd_naire.getString("TYPENAME")+"</li>";
				
				if(pd_answer_res!=null){
					ANSWER=pd_answer_res.getString("ANSWER")==null?"":pd_answer_res.getString("ANSWER");
				}
				String SUBJECT=pd_naire.getString("SUBJECT")==null?"":pd_naire.getString("SUBJECT");
				for(PageData pddata:varList_field){
					////System.out.println("kcb:"+pddata.getString("FIELD").toLowerCase());
					if(SUBJECT.indexOf(pddata.getString("FIELD").toUpperCase())>=0&&pd_cus!=null){
						SUBJECT=SUBJECT.replace(pddata.getString("FIELD").toUpperCase(), pd_cus.getString(pddata.getString("FIELD").toUpperCase())==null?"":pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
					}
					if(SUBJECT.indexOf(pddata.getString("FIELD").toLowerCase())>=0&&pd_cus!=null){
						////System.out.println(pddata.getString("FIELD").toLowerCase()+"9999"+pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
						SUBJECT=SUBJECT.replace(pddata.getString("FIELD").toLowerCase(), pd_cus.getString(pddata.getString("FIELD").toUpperCase())==null?"":pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
					}
				}	
				
				tmstr=tmstr+"<li style=\"line-height:20px;\">"+pd_naire.getString("CODE")+"、"+SUBJECT+"</li>";
				nexttmstr=nexttmstr+"<li style=\"line-height:20px;\">"+pd_naire.getString("CODE")+"、"+SUBJECT+"</li>";
				String answer=pd_naire.getString("ANSWER")==null?"":pd_naire.getString("ANSWER");
				String[] arr=answer.split(";");
				String[] arr1=null;
				PageData pd_answer=new PageData();
				boolean boo_answer=false;
				for(int i=0;i<arr.length;i++){
					pd_answer=new PageData();
					arr1=arr[i].split(":");
					if(arr1.length>=1){
						pd_answer.put("answer", arr1[0]);
					}
					if(pd_naire.getString("TYPENAME")!=null&&pd_naire.getString("TYPENAME").equals("多选题")){
						
						if(ANSWER.indexOf(arr1[0])>=0){
							boo_answer=true;
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;\">"
									+ "	<input type=\"checkbox\"  id=\"ANSWER\" onclick=\"nextNaire('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+(ROWNO+1)+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" name =\"ANSWER\" value=\""+arr1[0]+"\" checked>"+arr[i]+""
									+ "</li>";

							nexttmstr=nexttmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;\">"
									+ "	<input type=\"checkbox\" value=\""+arr1[0]+"\" checked>"+arr[i]+""
									+ "</li>";
						}else{
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;\">"
									+ "	<input type=\"checkbox\"  id=\"ANSWER\" onclick=\"nextNaire('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+(ROWNO+1)+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" name =\"ANSWER\" value=\""+arr1[0]+"\">"+arr[i]+""
									+ "</li>";
							
							nexttmstr=nexttmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;\">"
									+ "	<input type=\"checkbox\"   value=\""+arr1[0]+"\">"+arr[i]+""
									+ "</li>";
						}
					}else{
						if(ANSWER.equals(arr1[0])){
							boo_answer=true;
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;float:left\">"
									+ "	<input type=\"radio\"  id=\"ANSWER\"  onclick=\"nextNaire('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+(ROWNO+1)+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" name =\"ANSWER\" value=\""+arr1[0]+"\" checked>"+arr[i]+""
									+ "</li>";
							nexttmstr=nexttmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;float:left\">"
									+ "	<input type=\"radio\"   value=\""+arr1[0]+"\" checked>"+arr[i]+""
									+ "</li>";
							//tmstr=tmstr+"<option value="+arr1[0]+" selected>"+arr1[0]+"</li>";
						}else{
							//tmstr=tmstr+"<option value="+arr1[0]+">"+arr1[0]+"</li>";
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;float:left\">"
									+ "	<input type=\"radio\"  id=\"ANSWER\" onclick=\"nextNaire('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+(ROWNO+1)+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" name =\"ANSWER\" value=\""+arr1[0]+"\">"+arr[i]+""
									+ "</li>";
							
							nexttmstr=nexttmstr+"<li style=\"min-height:35px;height:auto;line-height:30px;float:left\">"
									+ "	<input type=\"radio\"   value=\""+arr1[0]+"\">"+arr[i]+""
									+ "</li>";
						}
					}	
				}
				
				//if(pd_naire.getString("REMARK")!=null){
					tmstr=tmstr+"<li style=\"color:red\"><div class=\"infomation-r-nr-one-bz\">备注:"+(pd_naire.getString("REMARK")==null?"":pd_naire.getString("REMARK"))+"</div></li>";
				//}
					nexttmstr=nexttmstr+"<li style=\"color:red\"><div class=\"infomation-r-nr-one-bz\">备注:"+(pd_naire.getString("REMARK")==null?"":pd_naire.getString("REMARK"))+"</div></li>";

			}else{
				nexttmstr=nexttmstr+"<li>无下一题</li>";
			}
			
			if(ROWNO!=1){
				tmstr=tmstr+"<li style=\"margin:0px\"><div class=\"xyt\"  type=\"button\" "
						+ "onclick=\"save_answer('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+(ROWNO-1)+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" "
						+ " style=\"width:100px;float:left;margin-top:-1px;cursor:pointer\">上一题</div>";
				tmstr=tmstr+"<div class=\"xyt\"  type=\"button\" "
						+ "onclick=\"save_answer('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+(ROWNO+1)+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" "
						+ " style=\"margin-left:50px;width:100px;float:left;margin-top:-1px;cursor:pointer\">下一题</div>";
				
			}else{
				tmstr=tmstr+"<li style=\"margin:0px\"><div class=\"xyt\" "
						+ "onclick=\"save_answer('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+(ROWNO+1)+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" "
						+ " style=\"margin-left:50px;width:100px;float:left;margin-top:-1px;cursor:pointer\">下一题</div>";
				
			}
			
			tmstr=tmstr+"<div class=\"jshf\" "
					+ "onclick=\"end_answer('"+pd.getString("NAIRE_TEMPLATE_ID")+"','"+varList.size()+"','"+pd.getString("type")+"','"+pd.getString("ID")+"','"+pd.getString("CUSTOM_TEMPLATE_ID")+"','"+pd.getString("TABLENAME")+"','"+pd.getString("ZXMAN")+"','"+pd_naire.getString("ID")+"','"+pd_naire.getString("CODE")+"','"+pd_naire.getString("TYPENAME")+"')\" "
					+ "class=\"btn btn-block btn-info\" style=\"margin-left:50px;margin-top:-1px;width:100px;float:left;background:#FFA07A;border:#FFA07A\">结束回访</div></li>";
		}
		tmstr=tmstr+"</ul>";
		
		nexttmstr=nexttmstr+"</ul>";
		JSONObject object_new = new JSONObject();
		object_new.put("tmstr", tmstr);
		object_new.put("nexttmstr", nexttmstr);
		if(isjs.equals("1")&&pd_custom!=null){
			////System.out.println("pd_custom.toString():"+pd_custom.toString());
			object_new.put("HFRESULT", pd_custom.getString("HFRESULT"));
			object_new.put("HFWJ", pd_custom.getString("HFWJ"));
		}
		object_new.put("isjs", isjs);
		ResponseUtils.renderJson(response,object_new.toString());
	}
	
	
	@RequestMapping(value="/getAnswer")
	public void getAnswer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		pd = this.getPageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		
		List<PageData>	varList =null;
		List<PageData>	varList_field =null;		
		//获取客户模板信息
		PageData pd_task = new PageData();
		String action=pd.getString("action")==null?"":pd.getString("action");
		if(action.equals("result")){
			pd_task.put("ID", pd.getString("TASKID"));
			pd_task=taskuserService.findById(pd_task);
			if(pd_task!=null){
				pd.put("CUSTOM_TEMPLATE_ID", pd_task.getString("CUSTOM_TEMPLATE_ID"));
				pd.put("TABLENAME", pd_task.getString("TABLENAME"));
				pd.put("CUS_ID",pd.getString("ID"));
				
			}
			
		}
		
		List<PageData> answerList=naireService.listAllAnswer(pd); //已回答题目列表
		PageData pd_new_cus = new PageData();
		pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
		//pd_new_cus.put("ISSHOW","1");
		varList_field = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
		//获取当前客户的信息
		pd_new.put("FIELD", "*");
		pd_new.put("ID", pd.getString("CUS_ID"));
		pd_new.put("TABLENAME",  pd.getString("TABLENAME"));
		PageData pd_cus =taskcustomService.findFieldByID(pd_new);
		
		String tmstr="",listr="";
		
		String ANSWER="";
		//PageData  pd_custom=null;
		for(PageData pd_naire:answerList){
			tmstr=tmstr+"<style>ul li{list-style-type:none}</style><ul style=\"margin-top:10px;border:1px #ccc dashed\">";
			if(pd_naire!=null){
				
				ANSWER=pd_naire.getString("ANSWER")==null?"":pd_naire.getString("ANSWER");
				String SUBJECT=pd_naire.getString("SUBJECT")==null?"":pd_naire.getString("SUBJECT");
				for(PageData pddata:varList_field){
					////System.out.println("kcb:"+pddata.getString("FIELD").toLowerCase());
					if(SUBJECT.indexOf(pddata.getString("FIELD").toUpperCase())>=0&&pd_cus!=null){
						SUBJECT=SUBJECT.replace(pddata.getString("FIELD").toUpperCase(), pd_cus.getString(pddata.getString("FIELD").toUpperCase())==null?"":pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
					}
					if(SUBJECT.indexOf(pddata.getString("FIELD").toLowerCase())>=0&&pd_cus!=null){
						////System.out.println(pddata.getString("FIELD").toLowerCase()+"9999"+pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
						SUBJECT=SUBJECT.replace(pddata.getString("FIELD").toLowerCase(), pd_cus.getString(pddata.getString("FIELD").toUpperCase())==null?"":pd_cus.getString(pddata.getString("FIELD").toUpperCase()));
					}
				}	
				
				tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px\">"+pd_naire.getString("NUM")+"、"+SUBJECT+"(<span style=\"font-weight:bold\">"+pd_naire.getString("TYPENAME")+"</span>)</li>";
				String answer=pd_naire.getString("ALLANSWER")==null?"":pd_naire.getString("ALLANSWER");
				String[] arr=answer.split(";");
				String[] arr1=null;
				PageData pd_answer=new PageData();
				//tmstr=tmstr+"<li><select id=\"ANSWER\" name =\"ANSWER\">";
				for(int i=0;i<arr.length;i++){
					pd_answer=new PageData();
					arr1=arr[i].split(":");
					if(arr1.length>=1){
						pd_answer.put("answer", arr1[0]);
					}
					if(pd_naire.getString("TYPENAME")!=null&&pd_naire.getString("TYPENAME").equals("多选题")){
						
						////System.out.println(ANSWER+"ANSWER"+arr1[0]);
						if(ANSWER.indexOf(arr1[0])>=0){
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px\">"
									+ "	<input type=\"checkbox\"  id=\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" name =\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" value=\""+arr1[0]+"\" checked>"+arr[i]+""
									+ "</li>";
							//tmstr=tmstr+"<option value="+arr1[0]+" selected>"+arr1[0]+"</li>";
						}else{
							//tmstr=tmstr+"<option value="+arr1[0]+">"+arr1[0]+"</li>";
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px\">"
									+ "	<input type=\"checkbox\"  id=\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" name =\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" value=\""+arr1[0]+"\">"+arr[i]+""
									+ "</li>";
						}
					}else{
					
						if(ANSWER.equals(arr1[0])){
							////System.out.println("ANSWER:"+ANSWER+"arr1[0]:"+arr1[0]);
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px\">"
									+ "	<input type=\"radio\"  id=\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" name =\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" value=\""+arr1[0]+"\" checked>"+arr[i]+""
									+ "</li>";
						}else{
							tmstr=tmstr+"<li style=\"min-height:35px;height:auto;line-height:30px\">"
									+ "	<input type=\"radio\"  id=\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" name =\"ANSWER"+pd_naire.getString("NAIRE_ID")+"\" value=\""+arr1[0]+"\">"+arr[i]+""
									+ "</li>";
						}
					}	
				}
				
			}
			tmstr=tmstr+"</ul>";
		}
		
		JSONObject object_new = new JSONObject();
		object_new.put("tmstr", tmstr);
		ResponseUtils.renderJson(response,object_new.toString());
	}
	
	
	
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/saveAnswer")
	public void saveAnswer(PrintWriter out) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"删除task");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		PageData pd_answer = new PageData();
		pd = this.getPageData();
		PageData pd_task = new PageData();
		pd_task=taskuserService.findById(pd);
		String state="";
		if(pd_task!=null){
			state=pd_task.getString("STATE")==null?"":pd_task.getString("STATE");
		}
		if(!state.equals("2")){
			pd_answer = exetaskService.findByAnswer(pd);	//根据ID读取
			if(pd_answer==null){
				pd.put("ID", this.get32UUID());	//主键
				exetaskService.saveAnswer(pd);
			}else{
				exetaskService.editAnswer(pd);
			}
		}
		
		out.write("success");
		out.close();
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
		pd.put("FIELD", "ID");
		List<PageData> pd_table = exetaskService.listAll(pd);	
		if(pd_table.size()<=0){
			str="还未添加客户";
			str="error1";
		}else{
			List<PageData> pd_table_fp = exetaskService.listAllIsfp(pd);	
			if(pd_table_fp.size()<=0){
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
		mv.setViewName("xxgl/exetask/exetask_imp");
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
					//System.out.println("rowNum"+rowNum);
					for(int r=1;r<=rowNum;r++){//读取每一行，第一行为标题，从第二行开始
						//System.out.println(r+":r");
						HSSFRow row = st.getRow(r); 
						HSSFCell cell = row.getCell((short) (0));
						if(cell == null||cell.equals("")||cell.getCellType()==Cell.CELL_TYPE_BLANK){
							break;
						}
						
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
						map.put("ID", "sys_guid()");
						map.put("createdate", "sysdate");
						map.put("createman", "'"+Jurisdiction.getUsername()+"'");
						
						if(!insertstr.equals("insert into "+TABLENAME+"(ID,")){
							insertstr=insertstr+") values(sys_guid(),sysdate,'"+Jurisdiction.getUsername()+"',";
						}

						for(int i=0;i<varList.size();i++){
							pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
							pd_new_filed=fieldService.findById(pd_new_filed);
							cell = row.getCell((short) (i));
							if(cell != null&&!cell.equals("")&&cell.getCellType()!=Cell.CELL_TYPE_BLANK){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(i!=0){
									insertstr=insertstr+",";
								}
								
								if(!selectStr.equals("")&&!cell.getStringCellValue().equals("")){
									selectStr=selectStr+" and ";
								}
								if(pd_new_filed.getString("FIELDTYPE")!=null&&pd_new_filed.getString("FIELDTYPE").equals("date")){
									insertstr=insertstr+ "to_date('"+cell.getStringCellValue()+"','YYYY-MM-DD')";
									map.put(pd_new_filed.getString("FIELD"),  "to_date('"+cell.getStringCellValue()+"','YYYY-MM-DD')");
									if(!cell.getStringCellValue().equals("")){
										selectStr+= pd_new_filed.getString("FIELD")+"=to_date('"+cell.getStringCellValue()+"','YYYY-MM-DD')";
									}
									
								}else{
									insertstr=insertstr+ "'"+cell.getStringCellValue()+"'";
									map.put(pd_new_filed.getString("FIELD"),"'"+cell.getStringCellValue()+"'");
									if(!cell.getStringCellValue().equals("")){
										selectStr+= pd_new_filed.getString("FIELD")+"='"+cell.getStringCellValue()+"'";
									}
								}
								temp=true;
							}
						} 
						
						if(temp){//Excel完全没有问题
							insertstr=insertstr+")";
							lineList.add(map);
							
							//TABLENAME
							// webService.saveOrUpdateAll(userInfoList);
							lineColumn="";
							////System.out.println(insertstr);	
							Map<String,String> lineMap = lineList.get(0); 
							for (String key : lineMap.keySet()) { 
								if(!lineColumn.equals("")){lineColumn=lineColumn+",";lineValues=lineValues+",";}
								lineColumn +=key; 
								lineValues+=lineMap.get(key);
							} 
							pd.put("selectStr",selectStr);
							pd.put("lineValues",lineValues);
							pd.put("lineColumn",lineColumn);
							pd.put("tableName",TABLENAME);
							pd.put("lineList",lineList);
							pd_search=exetaskService.findByField(pd);
							if(pd_search==null){
								count++;
								exetaskService.save(pd);
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
