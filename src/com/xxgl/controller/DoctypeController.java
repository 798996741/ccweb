package com.xxgl.controller;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.xxgl.service.doctype.DoctypeManager;

/** 
 * 说明：数据字典
 * 创建人：huangjianling
 * 创建时间：2015-12-16
 */
@Controller
@RequestMapping(value="/doctype")
public class DoctypeController extends BaseController {
	
	String menuUrl = "doctype/list.do"; //菜单地址(权限用)
	@Resource(name="doctypeService")
	private DoctypeManager doctypeService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增doctype");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("id", this.get32UUID());	//主键
		pd.put("createman", Jurisdiction.getUsername());
		doctypeService.save(pd);
		mv.addObject("msg","success");
		//mv.setViewName("save_result");
		mv.setViewName("redirect:/doctype/list.do");
		return mv;
	}
	
	/**
	 * 删除
	 * @param id
	 * @param
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String id) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除doctype");
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("id", id);
		String errInfo = "success";
		if(doctypeService.listSubDictByParentId(id).size() > 0){//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			pd = doctypeService.findById(pd);//根据ID读取
			if(null != pd.get("TBSNAME") && !"".equals(pd.getString("TBSNAME"))){
				String[] table = pd.getString("TBSNAME").split(",");
				for(int i=0;i<table.length;i++){
					pd.put("thisTable", table[i]);
					try {
						if(Integer.parseInt(doctypeService.findFromTbs(pd).get("zs").toString())>0){//判断是否被占用，是：不允许删除(去排查表检查字典表中的编码字段)
							errInfo = "false";
							break;
						}
					} catch (Exception e) {
							errInfo = "false2";
							break;
					}
				}
			}
		}
		if("success".equals(errInfo)){
			doctypeService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改doctype");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("createman", Jurisdiction.getUsername());
		doctypeService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("redirect:/doctype/list.do");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表doctype");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords")==null?"":URLDecoder.decode(pd.getString("keywords"), "utf-8");					//检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
			mv.addObject("keywords", keywords);
		}
		String id = null == pd.get("id")?"":pd.get("id").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			id = pd.get("id").toString();
		}
		pd.put("id", id);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = doctypeService.list(page);	//列出doctype列表
		mv.addObject("pd", doctypeService.findById(pd));		//传入上级所有信息
		mv.addObject("id", id);			//上级ID
		mv.setViewName("xxgl/doctype/doctype_list");
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());					//按钮权限
		return mv;
	}
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDict")
	public ModelAndView listAllDict(Model model,String id)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(doctypeService.listAllDict("0",""));
			String json = arr.toString();
			json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("id",id);
			mv.addObject("pd", pd);	
			mv.setViewName("xxgl/doctype/doctype_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listAllDoc")
	public ModelAndView listAllDoc(Model model,String id)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(doctypeService.listAllDict("0","doc"));
			String json = arr.toString();
			json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("id",id);
			mv.addObject("action","search");
			mv.addObject("pd", pd);	
			mv.setViewName("xxgl/doctype/doctype_ztree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		String id = null == pd.get("id")?"":pd.get("id").toString();
		pd.put("id", id);					//上级ID
		mv.addObject("pds",doctypeService.findById(pd));		//传入上级所有信息
		if(id==null||id.equals("")){
			id="0";
		}
		mv.addObject("id", id);			//传入ID，作为子级ID用
		mv.setViewName("xxgl/doctype/doctype_edit");
		mv.addObject("msg", "save");
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
		String id = pd.getString("id");
		pd = doctypeService.findById(pd);	//根据ID读取
		mv.addObject("pd", pd);					//放入视图容器
		pd.put("id",pd.get("parentid").toString());			//用作上级信息
		mv.addObject("pds",doctypeService.findById(pd));				//传入上级所有信息
		mv.addObject("id", pd.get("parentid").toString());	//传入上级ID，作为子ID用
		pd.put("id",id);							//复原本ID
		mv.setViewName("xxgl/doctype/doctype_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	

	/**判断编码是否存在
	 * @return
	 */
	@RequestMapping(value="/hasBianma")
	@ResponseBody
	public Object hasBianma(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(doctypeService.findByBianma(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
