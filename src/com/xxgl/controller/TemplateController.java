package com.xxgl.controller;

import java.io.PrintWriter;
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
import com.xxgl.service.mng.CustomtemplateManager;
import com.xxgl.service.mng.NairetemplateManager;
import com.xxgl.service.mng.TemplateManager;


/** 
 * 说明：参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 */
@Controller
@RequestMapping(value="/template")
public class TemplateController extends BaseController {
	
	String menuUrl = "template/list.do"; //菜单地址(权限用)
	
	@Resource(name="templateService")
	private TemplateManager templateService;
	@Resource(name="caseService")
	private CaseManager caseService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="nairetemplateService")
	private NairetemplateManager nairetemplateService;
	@Resource(name="customtemplateService")
	private CustomtemplateManager customtemplateService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		
		//pd.put("CZMAN",  Jurisdiction.getUsername());
		pd.put("CREATEMAN",  Jurisdiction.getUsername());
		PageData pd_new = templateService.findByCode(pd);	//根据ID读取
		if(pd_new!=null&&pd_new.getString("ID")!=null){
			mv.addObject("msg","输入的编码已经存在");
			System.out.println("输入的编码已经存在");
		}else{
			pd.put("ID", this.get32UUID());	//主键
			templateService.save(pd);
			mv.addObject("msg","成功");
		}	
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		
		templateService.delete(pd);
		
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		PageData pd_new = templateService.findByCode(pd);	//根据ID读取
		if(pd_new!=null&&pd_new.getString("ID")!=null){
			mv.addObject("msg","输入的编码已经存在");
			System.out.println("输入的编码已经存在");
		}else{
			templateService.edit(pd);
			mv.addObject("msg","成功");
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
		logBefore(logger, Jurisdiction.getUsername()+"列表template");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		List<PageData>	varList = templateService.list(page);	//列出template列表
		mv.setViewName("xxgl/template/template_list");
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
		List<PageData> varCList = customtemplateService.listAll(pd);
		mv.addObject("varCList", varCList);
		List<PageData> varNList = nairetemplateService.listAll(pd);
		mv.addObject("varNList", varNList);
	    
		pd = this.getPageData();
		mv.setViewName("xxgl/template/template_edit");
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
	   
		List<PageData> varCList = customtemplateService.listAll(pd);
		mv.addObject("varCList", varCList);
		List<PageData> varNList = nairetemplateService.listAll(pd);
		mv.addObject("varNList", varNList);
	    
		pd = this.getPageData();
		pd = templateService.findById(pd);	//根据ID读取
		
		mv.setViewName("xxgl/template/template_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			templateService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出template到excel");
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
		List<PageData> varOList = templateService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STARTADDR"));	    //1
			vpd.put("var2", varOList.get(i).getString("NUM"));	    //2
			vpd.put("var3", varOList.get(i).getString("CASEID"));	    //3
			vpd.put("var4", varOList.get(i).getString("TYPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("template1"));	    //5
			vpd.put("var6", varOList.get(i).getString("template2"));	    //6
			vpd.put("var7", varOList.get(i).getString("template3"));	    //7
			vpd.put("var8", varOList.get(i).getString("template4"));	    //8
			vpd.put("var9", varOList.get(i).getString("template5"));	    //9
			vpd.put("var10", varOList.get(i).getString("template6"));	    //10
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
