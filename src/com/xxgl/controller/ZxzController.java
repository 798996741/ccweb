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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxzManager;

/** 
 * 说明：优惠券
 * 创建人：351412933
 * 创建时间：2018-08-01
 */
@Controller
@RequestMapping(value="/zxz")
public class ZxzController extends BaseController {
	
	String menuUrl = "zxz/list.do"; //菜单地址(权限用)
	@Resource(name="zxzService")
	private ZxzManager zxzService;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();logBefore(logger, Jurisdiction.getUsername()+"新增zxz");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		pd = this.getPageData();
		pd.put("ID", this.get32UUID());	//主键
		//pd.put("CZMAN",  Jurisdiction.getUsername());
		pd.put("CREATEMAN",  Jurisdiction.getUsername());
		zxzService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除zxz");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		zxzService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改zxz");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN",  Jurisdiction.getUsername());
		zxzService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表zxz");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		URLDecoder urlDecoder = new URLDecoder();  
		//System.out.println( pd.getString("keywords"));
		String keywords = pd.getString("keywords")==null?"":URLDecoder.decode( pd.getString("keywords"),"utf-8");;				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}	
		page.setPd(pd);
		List<PageData>	varList = zxzService.list(page);	//列出zxz列表
		String zjn="";
		String[] array=null;
		String zjnname="";
		for(PageData pageData:varList){
			zjn=pageData.getString("ZJN");
			if(zjn!=null&&!zjn.equals("")){
				zjnname="";
				array=zjn.split(",");
				List<PageData> dict= dictionariesService.listAllDictByDict(array);
				for(PageData pageData1:dict){
					if(!zjnname.equals("")){
						zjnname=zjnname+",";
					}
					zjnname=zjnname+pageData1.getString("NAME");
				}
			}
			pageData.put("ZJNNAME", zjnname);
		}
		
		mv.setViewName("xxgl/zxz/zxz_list");
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
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pd_s = new PageData();
		pd_s.put("parentId", "d3ab9183f453443b9f38ffa1899b4d04");
		List<PageData> dictList=dictionariesService.listAllDictByParentId(pd_s);
	    
	    mv.addObject("dictList", dictList);
		mv.setViewName("xxgl/zxz/zxz_edit");
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
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = zxzService.findById(pd);	//根据ID读取
		PageData pd_s = new PageData();
		pd_s.put("parentId", "d3ab9183f453443b9f38ffa1899b4d04");
		List<PageData> dictList=dictionariesService.listAllDictByParentId(pd_s);
	    
		for(PageData pd_zxz:dictList){
			
			if(pd.getString("ZJN")!=null&&pd.getString("ZJN").indexOf(pd_zxz.getString("DICTIONARIES_ID"))>=0){
				pd_zxz.put("checked", "checked");
			}
			
		}
		
		
	    mv.addObject("dictList", dictList);
		
		mv.setViewName("xxgl/zxz/zxz_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除zxz");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			zxzService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出zxz到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("优惠卷标题");	//1
		titles.add("有效期");	//2
		titles.add("详情");	//3
		titles.add("使用范围");	//4
		titles.add("费用");	//5
		titles.add("优惠前费用");	//6
		titles.add("创建人");	//7
		titles.add("时间");	//8
		dataMap.put("titles", titles);
		List<PageData> varOList = zxzService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("TITLE"));	    //1
			vpd.put("var2", varOList.get(i).getString("TERM_VALIDITY"));	    //2
			vpd.put("var3", varOList.get(i).getString("DETAIL"));	    //3
			vpd.put("var4", varOList.get(i).getString("USESCOPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("MONEY"));	    //5
			vpd.put("var6", varOList.get(i).getString("PRE_MONEY"));	    //6
			vpd.put("var7", varOList.get(i).getString("CREATEMAN"));	    //7
			vpd.put("var8", varOList.get(i).getString("CREATEDATE"));	    //8
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
