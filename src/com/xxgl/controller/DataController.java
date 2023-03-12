package com.xxgl.controller;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.xxgl.service.mng.DataManager;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/data")
public class DataController
  extends BaseController
{
  String flUrl = "htfl.do";
  @Resource(name="dataService")
  private DataManager dataService;
  @Resource(name="userService")
  private UserManager userService;
  
  @Resource(name="fhlogService")
  private FHlogManager FHLOG;
  
  @RequestMapping(value="/listdatas")
  public ModelAndView listdata(Page page) throws Exception
  {
    ModelAndView mv = getModelAndView();
    try
    {
      Session session = Jurisdiction.getSession();
      User user=(User) session.getAttribute(Const.SESSION_USER);
      PageData pd = new PageData();
      PageData pd_new = new PageData();
      pd = this.getPageData();
      String lx = pd.getString("lx") == null ? "1" : pd.getString("lx");
      mv.addObject("lx", lx);
      //List<data> listdata = new ArrayList();
      String keywords = pd.getString("keywords");				//关键词检索条件
      if(null != keywords && !"".equals(keywords)){
    	  pd.put("keywords", keywords.trim());
      }
      String role_id=user.getROLE_ID();
      if(role_id!=null&&role_id.equals("1")){
    	  
      }else{
    	  pd_new.put("USERNAME", user.getUSERNAME()); 
    	  pd.put("USERNAME", user.getUSERNAME()); 
      }
     
      page.setPd(pd);
      List<PageData> listdata = this.dataService.listdataByTypeId(page);
      System.out.println(page.getPageStr()+"分页2");
      mv.addObject("pd", pd);
      mv.addObject("page", page);
      mv.addObject("listdata", listdata);
      mv.setViewName("system/data/data_list");
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
 
  @RequestMapping(value="/todataAdd")
  public ModelAndView todataAdd()
    throws Exception
  {
    ModelAndView mv = getModelAndView();
    try
    {
      Session session = Jurisdiction.getSession();
      User user=(User) session.getAttribute(Const.SESSION_USER);
      PageData pd = new PageData();
      PageData pd_new= new PageData();
      pd = getPageData();
      String guid = pd.getString("ID") == null ? "" : pd.getString("ID");
      String action = pd.getString("action") == null ? "" : pd.getString("action");
      String role_id=user.getROLE_ID();
      if(role_id!=null&&role_id.equals("1")){
    	  
      }else{
    	  pd_new.put("USERNAME", user.getUSERNAME()); 
      }
     
      if (!guid.equals("")) {
        pd = this.dataService.finddataById(pd);
      } else {
        guid = get32UUID();
      }
      mv.addObject("pd", pd);
     // mv.addObject("ID", guid);
      mv.addObject("action", action);
      

      mv.setViewName("system/data/data_add");
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping(value="/savedata")
  public ModelAndView savedata(Page page)
    throws Exception
  {
    logBefore(this.logger, Jurisdiction.getUsername() + "新增项目管理");
    
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    PageData pd1 = new PageData();
    PageData pdadd = new PageData();
    pd = getPageData();
    if ((pd.getString("action") != null) && (pd.getString("action").equals("1")))
    {
      //pd.put("GUID", get32UUID());
      
      pd.put("CZMAN", Jurisdiction.getUsername());
      this.dataService.savedata(pd);
    }
    if ((pd.getString("action") != null) && (pd.getString("action").equals("2"))) {
    	 pd.put("CZMAN", Jurisdiction.getUsername());
      this.dataService.updatedata(pd);
    }
    mv.addObject("msg", "success");
    Session session = Jurisdiction.getSession();
    User user=(User) session.getAttribute(Const.SESSION_USER);
    PageData pd_new = new PageData();
    String role_id=user.getROLE_ID();
    if(role_id!=null&&role_id.equals("1")){
  	  
    }else{
  	  pd_new.put("USERNAME", user.getUSERNAME()); 
  	  pd.put("USERNAME", user.getUSERNAME()); 
    }
    
    page.setPd(pd);
    List<PageData> listdata = this.dataService.listdataByTypeId(page);
    System.out.println(page.getPageStr()+"分页2");
    mv.addObject("pd", pd);
    mv.addObject("page", page);
    mv.addObject("listdata", listdata);
    mv.setViewName("system/data/data_list");
    return mv;
  }
  
  @RequestMapping(value="/deletedata")
  @ResponseBody
  public Object deletedata(@RequestParam String ID)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    String errInfo = "";
    try
    {
      PageData pd = new PageData();
      
      pd = getPageData();
      
    
        this.dataService.deletedataById(ID);
        this.FHLOG.save(Jurisdiction.getUsername(), "删除信息" + ID);
        errInfo = "success";
      //}
    }
    catch (Exception e)
    {
      errInfo = "false";
      this.logger.error(e.toString(), e);
    }
    map.put("result", errInfo);
    return AppUtil.returnObject(new PageData(), map);
  }
  
  
  
}
