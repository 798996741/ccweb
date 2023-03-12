package com.xxgl.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.CustomtemplateManager;
import com.xxgl.service.mng.CustomtemplatefieldManager;
import com.xxgl.service.mng.FieldManager;
import com.xxgl.service.mng.NairetemplateManager;
import com.xxgl.service.mng.TaskcustomManager;
import com.xxgl.service.mng.TaskuserManager;
import com.xxgl.service.mng.TemplateManager;


/** 
 * 说明：参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 */
@Controller
@RequestMapping(value="/taskuser")
public class TaskuserController extends BaseController {
	
	String menuUrl = "taskuser/list.do"; //菜单地址(权限用)
	
	@Resource(name="taskuserService")
	private TaskuserManager taskuserService;

	@Resource(name="taskcustomService")
	private TaskcustomManager taskcustomService;
	@Resource(name="caseService")
	private CaseManager caseService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="templateService")
	private TemplateManager templateService;
	
	@Resource(name="nairetemplateService")
	private NairetemplateManager nairetemplateService;
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
		//System.out.println(pd.getString("TYPE")+"TYPE");
		//System.out.println(pd.getString("TYPE")+"TYPE");
		pd.put("CREATEMAN",  Jurisdiction.getUsername());
		taskuserService.save(pd);
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		//pd_new.put("ID", TEMPLATE_ID);
		//pd_new=templateService.findById(pd_new);
		//page.setPd(pd);
		//根据客户模板id 查询所有字段
		///pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
		//List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
		
		//动态创建表
		
		/*String tableName="t_"+TaskuserController.GuidToLongID(id);
		
		String tablestr="";
		tablestr="CREATE TABLE "+tableName+" (";
		tablestr=tablestr+"id varchar(50) NOT NULL,";
		PageData pd_new_filed = new PageData();
		for(int i=0;i<varList.size();i++){
			pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
			pd_new_filed=fieldService.findById(pd_new_filed);
			if(pd_new_filed.getString("FIELDTYPE")!=null&&pd_new_filed.getString("FIELDTYPE").equals("date")){
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+" NULL,";
			}else if(pd_new_filed.getString("FIELDNUM")==null||pd_new_filed.getString("FIELDNUM").equals("")){
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+"  NULL,";
			}else{
				tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+"("+pd_new_filed.getString("FIELDNUM")+") NULL,";
			}
		}
		tablestr=tablestr+"fpman varchar(50),";
		tablestr=tablestr+"zxman varchar(50),";
		tablestr=tablestr+"hfwj varchar(50), ";
		tablestr=tablestr+"hfdate date,";
		tablestr=tablestr+"hfresult varchar(50), ";
		tablestr=tablestr+"createdate date,";
		tablestr=tablestr+"createman varchar(50), ";
		tablestr=tablestr+"hfremark varchar(50), ";
		tablestr=tablestr+"hfjl varchar(50), ";
		tablestr=tablestr+"PRIMARY KEY (id)) ";
		//System.out.println(tablestr);
		pd.put("ID", id);
		pd.put("TABLENAME", tableName);
		pd.put("TABLESTR", tablestr);
		
		taskuserService.createNewTable(pd);//创建表
		taskuserService.editTableName(pd);//修改表
*/		
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
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		pd = taskuserService.findById(pd);	//根据ID读取
		if(pd!=null&&pd.getString("STATE").equals("0")){
			if(pd.getString("TABLENAME")!=null&&!pd.getString("TABLENAME").equals("")){
				//taskuserService.dropTable(pd);
			}
			taskuserService.delete(pd);
			out.write("success");
			out.close();
		}else{
			out.write("error");
			out.close();
		}
		
		
		
		
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
		PageData pd_task = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		pd_task=taskuserService.findById(pd);
		boolean boo=false;
		if(pd_task!=null){
			//判断表格是否有数据
			String STATE=pd_task.getString("STATE")==null?"0":pd_task.getString("STATE");
			PageData pd_table = new PageData();
			if(pd!=null&&pd_task.getString("TABLENAME")!=null){
				pd_table.put("tableName", pd_task.getString("TABLENAME"));
				pd_table.put("selectStr","1=1 limit 0,1");
				pd_table=taskcustomService.findByField(pd_table);
				if(pd_table!=null&&pd_table.getString("ID")!=null){
					boo=true;
				}
			}
			if(!boo){
				PageData pd_new_cus = new PageData();
				if(pd_task.getString("TABLENAME")!=null&&STATE.equals("0")){
					
					pd_new_cus.put("CUS_TEMP_ID", pd.getString("CUSTOM_TEMPLATE_ID"));
					List<PageData>	varList = customtemplatefieldService.listAll(pd_new_cus);	//列出customtemplatefield列表
					
					//动态创建表
					String tableName="t_"+TaskuserController.GuidToLongID(pd.getString("ID"));
					
					String tablestr="";
					tablestr="CREATE TABLE "+tableName+" (";
					tablestr=tablestr+"id varchar(50) NOT NULL,";
					PageData pd_new_filed = new PageData();
					for(int i=0;i<varList.size();i++){
						pd_new_filed.put("ID", varList.get(i).getString("FIELD_ID"));
						pd_new_filed=fieldService.findById(pd_new_filed);
						if(pd_new_filed.getString("FIELDTYPE")!=null&&pd_new_filed.getString("FIELDTYPE").equals("date")){
							tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+" NOT NULL,";
						}else if(pd_new_filed.getString("FIELDNUM")==null||pd_new_filed.getString("FIELDNUM").equals("")){
							tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+" NOT NULL,";
						}else{
							tablestr=tablestr+""+pd_new_filed.getString("FIELD")+" "+pd_new_filed.getString("FIELDTYPE")+"("+pd_new_filed.getString("FIELDNUM")+") NOT NULL,";
						}
					}
					tablestr=tablestr+"fpman varchar(50),";
					tablestr=tablestr+"zxman varchar(50),";
					tablestr=tablestr+"hfwj varchar(50), ";
					tablestr=tablestr+"hfdate date,";
					tablestr=tablestr+"hfresult varchar(50), ";
					tablestr=tablestr+"createdate date,";
					tablestr=tablestr+"createman varchar(50), ";
					tablestr=tablestr+"hfremark varchar(50), ";
					tablestr=tablestr+"hfjl varchar(50), ";
					tablestr=tablestr+"PRIMARY KEY (id)) ";
					//System.out.println(tablestr);
					pd.put("ID", pd.getString("ID"));
					pd.put("TABLENAME", tableName);
					pd.put("TABLESTR", tablestr);
					taskuserService.dropTable(pd_task); //删除表
					taskuserService.createNewTable(pd);//创建表
					taskuserService.editTableName(pd);//修改表
				}else{
					pd.put("TABLENAME", pd_task.getString("TABLENAME"));
				}
				taskuserService.edit(pd);
				mv.addObject("msg","success_");
			}else{
				taskuserService.edit(pd);
				mv.addObject("msg","success_");
			}
		}
		
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
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		URLDecoder urlDecoder = new URLDecoder(); 
		String keywords = pd.getString("keywords")==null?"":URLDecoder.decode( pd.getString("keywords"),"utf-8");;				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}	
		page.setPd(pd);
		
		List<PageData>	varList = taskuserService.list(page);	//列出task列表
		mv.setViewName("xxgl/task/task_list");
		mv.addObject("varList", varList);
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
		
		List<PageData> varCList = customtemplateService.listAll(pd);
		mv.addObject("varCList", varCList);
		List<PageData> varNList = nairetemplateService.listAll(pd);
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
		
		pd = this.getPageData();
		//System.out.println(pd.getString("action"));
		mv.addObject("action", pd.getString("action"));
		
		
		List<PageData> varCList = customtemplateService.listAll(pd);
		mv.addObject("varCList", varCList);
		List<PageData> varNList = nairetemplateService.listAll(pd);
		mv.addObject("varNList", varNList);
	   
		pd = taskuserService.findById(pd);	//根据ID读取
		PageData pd_table = new PageData();
		if(pd!=null&&pd.getString("TABLENAME")!=null){
			pd_table.put("tableName", pd.getString("TABLENAME"));
			pd_table.put("selectStr"," 1=1 limit 0,1");
			pd_table=taskcustomService.findByField(pd_table);
			if(pd_table!=null&&pd_table.getString("ID")!=null){
				mv.addObject("isexist", "1");
			}
		}
		
		//System.out.println(pd.getString("action"));
		mv.setViewName("xxgl/task/task_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
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
		List<String> titles = new ArrayList<String>();
		titles.add("起始地址");	//1
		titles.add("字段数量");	//2
		titles.add("实例id");	//3
		titles.add("类型");	//4
		titles.add("字段1");	//5
		titles.add("字段2");	//6
		titles.add("字段3");	//7
		titles.add("字段4");	//8
		titles.add("字段5");	//9
		titles.add("字段6");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = taskuserService.listAll(pd);
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
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
