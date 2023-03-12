package com.xxgl.controller;

import java.io.File;
import java.io.IOException;
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
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.MD5;
import com.fh.util.MyUtils;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.xxgl.service.doctype.DoctypeManager;
import com.xxgl.service.mng.DocManager;


/** 
 * 说明：知识库管理
 * 创建人：351412933
 * 创建时间：2018-08-01
 */
@Controller
@RequestMapping(value="/doc")
public class DocController extends BaseController {
	

	String menuUrl = "doc/list.do"; //菜单地址(权限用)
	@Resource(name="docService")
	private DocManager docService;
	
	@Resource(name="doctypeService")
	private DoctypeManager doctypeService;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(@RequestParam(value = "file",required = false) MultipartFile[] files,HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增doc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_doc = new PageData();
		pd = this.getPageData();
	
		
		pd.put("doctype_id", request.getParameter("doctype_id"));
		pd.put("doctitle", request.getParameter("doctitle"));
		pd.put("docsource", request.getParameter("docsource"));
		pd.put("docauthor", request.getParameter("docauthor"));
		pd.put("doctype", request.getParameter("doctype"));
		pd.put("doccont", request.getParameter("doccont"));
		pd.put("id", request.getParameter("id"));
		pd.put("createman",  Jurisdiction.getUsername());
		String uid=this.get32UUID();
		pd.put("uid", uid);	//主键
		
		//pd.put("CZMAN",  Jurisdiction.getUsername());
		pd.put("createman",  Jurisdiction.getUsername());
		docService.save(pd);
		mv.addObject("msg","success_add");
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		for (MultipartFile i:files) {
			if(i.getSize()>0) {
				String filename = i.getOriginalFilename();
				//System.out.println(filename+"filename");
				String ext = MyUtils.getFileNameExt(filename);
				String newFileName = dateString+"_"+System.currentTimeMillis() + "." + ext;
				
				String path=request.getServletContext().getRealPath("uploadFiles/uploadFile");
				//String newFilePath = MyUtils.transferToWithHash(i, path, newFileName);
				pd.put("doc_file", newFileName);
				pd.put("doc_name", filename);
				pd.put("ext", ext);
				File file = new File(path,newFileName);
				try {
					i.transferTo(file);
					docService.savefile(pd);
					
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		
		mv.setViewName("redirect:/doc/list.do");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除doc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		docService.delete(pd);
		out.write("success");
		out.close();
	}
	
	
	@RequestMapping(value="/deleteFile")
	public void deleteFile(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		docService.deleteFile(pd);
		out.write("success");
		out.close();
	}
	
	
	@RequestMapping(value="/clicknum")
	public void clicknum(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		docService.clicknum(pd);
		out.write("success");
		out.close();
	}
	
	@RequestMapping(value="/doccomment")
	public void doccomment(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("createman",  Jurisdiction.getUsername());
		docService.doccomment(pd);
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
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_doc = new PageData();
		pd = this.getPageData();
	
		
		pd.put("doctype_id", request.getParameter("doctype_id"));
		pd.put("doctitle", request.getParameter("doctitle"));
		pd.put("docsource", request.getParameter("docsource"));
		pd.put("docauthor", request.getParameter("docauthor"));
		pd.put("doctype", request.getParameter("doctype"));
		pd.put("doccont", request.getParameter("doccont"));
		pd.put("id", request.getParameter("id"));
		pd.put("uid", request.getParameter("uid"));
		pd.put("createman",  Jurisdiction.getUsername());
		//String uid=this.get32UUID();
		//pd.put("uid", uid);	//主键
		
		//pd.put("CZMAN",  Jurisdiction.getUsername());
		pd.put("createman",  Jurisdiction.getUsername());
		
		mv.addObject("msg","success_add");
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		for (MultipartFile i:files) {
			if(i.getSize()>0) {
				String filename = i.getOriginalFilename();
				//System.out.println(filename+"filename");
				String ext = MyUtils.getFileNameExt(filename);
				String newFileName = dateString+"_"+System.currentTimeMillis() + "." + ext;
				
				String path=request.getServletContext().getRealPath("uploadFiles/uploadFile");
				//String newFilePath = MyUtils.transferToWithHash(i, path, newFileName);
				pd.put("doc_file", newFileName);
				pd.put("doc_name", filename);
				pd.put("ext", ext);
				File file = new File(path,newFileName);
				try {
					i.transferTo(file);
					docService.savefile(pd);
					
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		docService.edit(pd);
		
		mv.setViewName("redirect:/doc/list.do");
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
		
		if(pd.getString("action")!=null&&pd.getString("action").equals("search")){
			pd.put("doctype","1");
		}
		List<PageData>	varList = docService.list(page);	//列出doc列表
		for(PageData pds:varList){
			List<PageData> pd_pl = docService.listAllComment(pds);
			//pd_pl.put("id", value);
			pds.put("plnum", pd_pl.size());
		}
		
		String zjn="";
		String[] array=null;
		
		
		mv.setViewName("xxgl/doc/doc_list");
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
		
		//JSONArray arr = JSONArray.fromObject(doctypeService.listAllDict("0",""));
		
		List<PageData> doctypeList=doctypeService.listAllDoctype(pd);
		JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(PageData pd_obj:doctypeList){
			object = new JSONObject();
			object.put("id", pd_obj.get("id"));
			object.put("parentid", pd_obj.getString("parentid"));
			object.put("name",  pd_obj.getString("name"));
			//object.put("name",  pd_obj.get("NAME"));
			objlist.add(object);
		}
		
		
		String json = objlist.toString();
		json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked");
		System.out.println(json);
		mv.addObject("zTreeNodes", json);
	 
		mv.setViewName("xxgl/doc/doc_edit");

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
		String action=pd.getString("action");
		pd = docService.findById(pd);	//根据ID读取
		//JSONArray arr = JSONArray.fromObject(doctypeService.listAllDict("0",""));
		
				List<PageData> doctypeList=doctypeService.listAllDoctype(pd);
				JSONObject object = new JSONObject();
				List<JSONObject> objlist=new ArrayList();
				for(PageData pd_obj:doctypeList){
					object = new JSONObject();
					object.put("id", pd_obj.get("id"));
					object.put("parentid", pd_obj.getString("parentid"));
					object.put("name",  pd_obj.getString("name"));
					//object.put("name",  pd_obj.get("NAME"));
					objlist.add(object);
				}
				
				
				String json = objlist.toString();
		json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked");
		mv.addObject("zTreeNodes", json);
		
	
		if(action!=null&&action.equals("search")){
			mv.setViewName("xxgl/doc/doc_search");
		}else{
			mv.setViewName("xxgl/doc/doc_edit");
		}
		
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	

	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getFile")
	@ResponseBody
	public Object getFile() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();	
		pd=this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> fileList=docService.findFileByuid(pd);
		
		JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(PageData pd_obj:fileList){
			object = new JSONObject();
			object.put("doc_name", pd_obj.getString("doc_name"));
			object.put("doc_file", pd_obj.getString("doc_file"));
			object.put("id",  pd_obj.get("id"));
			objlist.add(object);
		}
		//System.out.println(objlist);
		map.put("list", objlist);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除doc");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			docService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出doc到excel");
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
		List<PageData> varOList = docService.listAll(pd);
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
