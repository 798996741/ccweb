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
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.FieldDetailManager;
import com.xxgl.service.mng.FieldManager;


/** 
 * 说明：参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 */
@Controller
@RequestMapping(value="/field")
public class FieldController extends BaseController {
	
	String menuUrl = "field/list.do"; //菜单地址(权限用)
	@Resource(name="fielddetailService")
	private FieldDetailManager fieldDetailService;
	
	@Resource(name="fieldService")
	private FieldManager fieldService;
	@Resource(name="caseService")
	private CaseManager caseService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Field");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		
		
		List<PageData> varOList = fieldService.listAllExit(pd);
		if(varOList.size()<=0){
			pd.put("ID", this.get32UUID());	//主键
			if(pd.getString("FIELD")!=null){
				pd.put("FIELD", pd.getString("FIELD").toLowerCase().trim());
			}
			fieldService.save(pd);
			mv.addObject("msg","success_save");
		}else{
			mv.addObject("msg","error1");
		}
		mv.setViewName("save_result");
		//mv.setViewName("xxgl/field/field_list");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Field");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		
		fieldService.delete(pd);
		
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Field");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		List<PageData> varOList = fieldService.listAllExit(pd);
		if(varOList.size()<=0){
			if(pd.getString("FIELD")!=null){
				pd.put("FIELD", pd.getString("FIELD").toLowerCase().trim());
			}
			fieldService.edit(pd);
			mv.addObject("msg","success_save");
		}else{
			mv.addObject("msg","error1");
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Field");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		URLDecoder urlDecoder = new URLDecoder();  
		String keywords = pd.getString("keywords")==null?"":URLDecoder.decode( pd.getString("keywords"),"utf-8");;				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}			//关键词检索条件
		
		page.setPd(pd);
		
		List<PageData>	varList = fieldService.list(page);	//列出Field列表
		mv.setViewName("xxgl/field/field_list");
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
	    List<Dictionaries> dictList=dictionariesService.listAllDict("e7196a14b22d4d928cb02e23ef9bbbc3");
	    mv.addObject("dictList", dictList);
	   
	    String ID=this.get32UUID();
	
	    mv.addObject("ID", ID);
	    
		pd = this.getPageData();
		mv.setViewName("xxgl/field/field_edit");
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
	   
		List<Dictionaries> dictList=dictionariesService.listAllDict("e7196a14b22d4d928cb02e23ef9bbbc3");
		mv.addObject("dictList", dictList);
	    
		pd = this.getPageData();
		pd = fieldService.findById(pd);	//根据ID读取
		
		mv.setViewName("xxgl/field/field_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Field");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			fieldService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Field到excel");
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
		List<PageData> varOList = fieldService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STARTADDR"));	    //1
			vpd.put("var2", varOList.get(i).getString("NUM"));	    //2
			vpd.put("var3", varOList.get(i).getString("CASEID"));	    //3
			vpd.put("var4", varOList.get(i).getString("TYPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("FIELD1"));	    //5
			vpd.put("var6", varOList.get(i).getString("FIELD2"));	    //6
			vpd.put("var7", varOList.get(i).getString("FIELD3"));	    //7
			vpd.put("var8", varOList.get(i).getString("FIELD4"));	    //8
			vpd.put("var9", varOList.get(i).getString("FIELD5"));	    //9
			vpd.put("var10", varOList.get(i).getString("FIELD6"));	    //10
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
