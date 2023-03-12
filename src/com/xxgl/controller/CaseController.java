package com.xxgl.controller;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.xxgl.service.mng.CaseManager;

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
@RequestMapping(value="/case")
public class CaseController
  extends BaseController
{
  String flUrl = "htfl.do";
  @Resource(name="caseService")
  private CaseManager caseService;
  @Resource(name="userService")
  private UserManager userService;
  
  @Resource(name="fhlogService")
  private FHlogManager FHLOG;
  
  @Resource(name="dictionariesService")
  private DictionariesManager dictionariesService;
   
  
  @RequestMapping(value="/listCases")
  public ModelAndView listcase(Page page) throws Exception
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
      //List<CaseBean> listcase = new ArrayList();
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
      List<User> listuser = new ArrayList();
      listuser=userService.listAllUsertoCase(pd_new);
      page.setPd(pd);
      List<PageData> listcase = this.caseService.listcaseByTypeId(page);
      //System.out.println(page.getPageStr()+"分页2");
      mv.addObject("pd", pd);
      mv.addObject("page", page);
      mv.addObject("listcase", listcase);
      mv.addObject("listuser", listuser);
      mv.setViewName("system/case/case_list");
      mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
 
  @RequestMapping(value="/tocaseAdd")
  public ModelAndView tocaseAdd()
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
      List<User> listuser = new ArrayList();
      listuser=userService.listAllUsertoCase(pd_new);
     
      if (!guid.equals("")) {
        pd = this.caseService.findcaseById(pd);
      } else {
        guid = get32UUID();
      }
      mv.addObject("pd", pd);
      mv.addObject("listuser", listuser);
     // mv.addObject("ID", guid);
      mv.addObject("action", action);
      
      mv.addObject("msg", "savecase");
      mv.setViewName("system/case/case_add");
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping(value="/tocaseSet")
  public ModelAndView tocaseSet()
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
      List<Dictionaries> dictList=dictionariesService.listAllDict("2f0472d2e0dd4c668704abd1940f2905");
      
      if (!guid.equals("")) {
        pd = this.caseService.findcaseById(pd);
      } else {
        guid = get32UUID();
      }
      mv.addObject("pd", pd);
      mv.addObject("dictList", dictList);
     // mv.addObject("ID", guid);
      mv.addObject("action", action);
      

      mv.setViewName("system/case/case_set");
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  
  @RequestMapping(value="/savecase")
  public ModelAndView savecase(Page page)
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
      this.caseService.savecase(pd);
    }
    if ((pd.getString("action") != null) && (pd.getString("action").equals("2"))) {
    	 pd.put("CZMAN", Jurisdiction.getUsername());
      this.caseService.updatecase(pd);
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
    List<User> listuser = new ArrayList();
    listuser=userService.listAllUsertoCase(pd_new);
    page.setPd(pd);
    List<PageData> listcase = this.caseService.listcaseByTypeId(page);
    System.out.println(page.getPageStr()+"分页2");
    mv.addObject("pd", pd);
    mv.addObject("page", page);
    mv.addObject("listcase", listcase);
    mv.addObject("listuser", listuser);
    mv.setViewName("system/case/case_list");
    return mv;
  }
  
  @RequestMapping(value="/saveset")
  public ModelAndView saveset(Page page)
    throws Exception
  {
    logBefore(this.logger, Jurisdiction.getUsername() + "新增实例设置");
    
    ModelAndView mv = getModelAndView();
    PageData pd = new PageData();
    PageData pd1 = new PageData();
    PageData pdadd = new PageData();
    pd = getPageData();
    if ((pd.getString("action") != null) && (pd.getString("action").equals("1")))
    {
      //pd.put("GUID", get32UUID());
      
      pd.put("CZMAN", Jurisdiction.getUsername());
      this.caseService.savecase(pd);
    }
    if ((pd.getString("action") != null) && (pd.getString("action").equals("2"))) {
    	 pd.put("CZMAN", Jurisdiction.getUsername());
    	 this.caseService.updatecase(pd);
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
    List<User> listuser = new ArrayList();
    listuser=userService.listAllUsertoCase(pd_new);
    page.setPd(pd);
    List<PageData> listcase = this.caseService.listcaseByTypeId(page);
    System.out.println(page.getPageStr()+"分页2");
    mv.addObject("pd", pd);
    mv.addObject("page", page);
    mv.addObject("listcase", listcase);
    mv.addObject("listuser", listuser);
    mv.setViewName("system/case/case_list");
    return mv;
  }
  
  
  @RequestMapping(value="/deletecase")
  @ResponseBody
  public Object deletecase(@RequestParam String ID)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    String errInfo = "";
    try
    {
      PageData pd = new PageData();
      
      pd = getPageData();
      
    
        this.caseService.deletecaseById(ID);
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
  
  @RequestMapping(value="/updateState")
  @ResponseBody
  public Object updatecase(@RequestParam String ID)throws Exception
  {
    Map<String, String> map = new HashMap();
    String errInfo = "";
    try
    {
      PageData pd = new PageData();
      pd = getPageData();
      this.caseService.updatecaseById(pd);
      this.FHLOG.save(Jurisdiction.getUsername(), "删除信息" + ID);
      errInfo = "success";
    }
    catch (Exception e)
    {
      errInfo = "false";
      this.logger.error(e.toString(), e);
    }
    map.put("result", errInfo);
    return AppUtil.returnObject(new PageData(), map);
  }
  
  
  @RequestMapping(value="/hasCardid")
  public String hasContractPlanCode(HttpServletRequest req, HttpServletResponse resp)
    throws Exception
  {
    PageData pd = new PageData();
    
    Map<String, String> map = new HashMap();
    String errInfo = "success";
    try
    {
      pd = getPageData();
      if (this.caseService.findCardid(pd) != null) {
        errInfo = "error";
      } else {
        errInfo = "success";
      }
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    map.put("result", errInfo);
    resp.getWriter().print(errInfo);
    return null;
  }
}
