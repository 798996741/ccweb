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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.fh.entity.system.Role;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.MD5;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.xxgl.service.mng.ZxzManager;
import com.xxgl.service.role.RolefrontManager;


/** 
 * 说明：坐席人员
 * 创建人：351412933
 * 创建时间：2018-08-01
 */
@Controller
@RequestMapping(value="/zxlb")
public class ZxlbController extends BaseController {
	

	String menuUrl = "zxlb/list.do"; //菜单地址(权限用)
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;
	
	@Resource(name="zxzService")
	private ZxzManager zxzService;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="userService")
	private UserManager userService;
	

	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	
	@Resource(name="rolefrontService")
	private RolefrontManager rolefrontService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增zxlb");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_zxlb = new PageData();
		pd = this.getPageData();
		pd_zxlb=zxlbService.findByZxyh(pd);
		if(pd_zxlb==null){
			pd.put("ID", this.get32UUID());	//主键
			if(pd.getString("PWD")!=null&&!pd.getString("PWD").equals("")){
				pd.put("PWD", MD5.md5(pd.getString("PWD")));
				pd.put("PASSWORD",pd.getString("PWD"));
			}
			//pd.put("CZMAN",  Jurisdiction.getUsername());
			pd.put("CREATEMAN",  Jurisdiction.getUsername());
			zxlbService.save(pd);
			mv.addObject("msg","success_add");
		}else{
			mv.addObject("msg","error1");
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
		logBefore(logger, Jurisdiction.getUsername()+"删除zxlb");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		zxlbService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改zxlb");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_zxlb = new PageData();
		pd = this.getPageData();
		pd_zxlb=zxlbService.findByZxyh(pd);
		if(pd_zxlb==null){
			pd.put("CREATEMAN",  Jurisdiction.getUsername());
			if(pd.getString("PWD")!=null&&!pd.getString("PWD").equals("")){
				pd.put("PWD", MD5.md5(pd.getString("PWD")));
			}
			zxlbService.edit(pd);
			mv.addObject("msg","success_add");
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
		logBefore(logger, Jurisdiction.getUsername()+"列表zxlb");
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
		List<PageData>	varList = zxlbService.list(page);	//列出zxlb列表
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
		
		mv.setViewName("xxgl/zxlb/zxlb_list");
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
		List<Dictionaries> dictList=dictionariesService.listAllDict("11a4a7f462594ad19268ab564b98837a");
		pd.put("ROLE_ID", "1");
		List<Role> rolefrontList = rolefrontService.listAllRolesByPId(pd);	//列出所有系统用户角色
		mv.addObject("rolefrontList", rolefrontList);
		
	    mv.addObject("dictList", dictList);
	    List<Dictionaries> roleList=dictionariesService.listAllDict("ac76494cb469426f8ed6965a68f53a3a");
	    
	    mv.addObject("roleList", roleList);
	    pd.put("ZXLB", "1");
	    pd.put("ROLE_ID", "");
	    List<PageData> userList = userService.listAllUser(pd);
	    mv.addObject("userList", userList);
	    
	    List<PageData> fzList= zxzService.listAll(pd);
	    mv.addObject("fzList", fzList);
	    
	    
	    JSONArray arr = JSONArray.fromObject(areamanageService.listAll(pd));
		String json = arr.toString();
		json = json.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
		//System.out.println(json);
		mv.addObject("zTreeNodes", json);
		mv.setViewName("xxgl/zxlb/zxlb_edit");

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
		
		
		List<Dictionaries> dictList=dictionariesService.listAllDict("11a4a7f462594ad19268ab564b98837a");
	    
	    mv.addObject("dictList", dictList);
	    
	    pd.put("ROLE_ID", "1");
	    List<Role> rolefrontList = rolefrontService.listAllRolesByPId(pd);	//列出所有系统用户角色
		mv.addObject("rolefrontList", rolefrontList);
	    
		
	    List<Dictionaries> roleList=dictionariesService.listAllDict("ac76494cb469426f8ed6965a68f53a3a");
	    
	    mv.addObject("roleList", roleList);
	    pd.put("ZXLB", "1");
	    pd.put("ROLE_ID", "");
	    List<PageData> userList = userService.listAllUser(pd);
	    mv.addObject("userList", userList);
	    List<PageData> fzList= zxzService.listAll(pd);
	    mv.addObject("fzList", fzList);
		
	    JSONArray arr = JSONArray.fromObject(areamanageService.listAll(pd));
		String json = arr.toString();
		json = json.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
		//System.out.println(json);
		pd = zxlbService.findById(pd);	//根据ID读取
		mv.addObject("zTreeNodes", json);
	    
		
		mv.setViewName("xxgl/zxlb/zxlb_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除zxlb");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			zxlbService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出zxlb到excel");
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
		List<PageData> varOList = zxlbService.listAll(pd);
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
	
	/**判断用户名是否存在
	 * @return
	 */
	@RequestMapping(value="/hasU")
	@ResponseBody
	public Object hasU(){
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(zxlbService.findByZxid(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	
	@RequestMapping(value="/getUsers")
	@ResponseBody
	public Object getUsers() throws Exception{
		PageData pd = new PageData();	
		pd=this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd.put("ZXLB", "1");
	    pd.put("ROLE_ID", "");
	    
	    List<PageData> userList = userService.listAllUser(pd);
	  
		JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(PageData pd_obj:userList){
			object = new JSONObject();
			object.put("USERNAME", pd_obj.getString("USERNAME"));
			object.put("NAME", pd_obj.getString("NAME"));
			object.put("USER_ID", pd_obj.getString("USER_ID"));
			objlist.add(object);
		}
		
		map.put("list", objlist);
		return AppUtil.returnObject(pd, map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
