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
import com.xxgl.service.mng.EndpointManager;

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
@RequestMapping(value="/endpoint")
public class EndpointController
  extends BaseController
{
  String flUrl = "htfl.do";
  @Resource(name="endpointService")
  private EndpointManager endpointService;
  @Resource(name="userService")
  private UserManager userService;
  @Resource(name="caseService")
  private CaseManager caseService;
  
  @Resource(name="fhlogService")
  private FHlogManager FHLOG;
  @Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
  
  @RequestMapping(value="/listendpoints")
  public ModelAndView listendpoint(Page page) throws Exception
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
      //List<endpointBean> listendpoint = new ArrayList();
      String keywords = pd.getString("keywords");				//关键词检索条件
      if(null != keywords && !"".equals(keywords)){
    	  pd.put("keywords", keywords.trim());
      }
      
      String CASEID_SEARCH = pd.getString("CASEID_SEARCH");				//关键词检索条件
      if(null != CASEID_SEARCH && !"".equals(CASEID_SEARCH)){
    	  pd.put("CASEID", CASEID_SEARCH.trim());
      }
      
      
      
      String role_id=user.getROLE_ID();
      if(role_id!=null&&role_id.equals("1")){
    	  
      }else{
    	  pd_new.put("USERNAME", user.getUSERNAME()); 
    	  pd.put("USERNAME", user.getUSERNAME()); 
      }
      List<CaseBean> listcase = new ArrayList();
      listcase=caseService.listAllCasetoEndpoint(pd_new);
      page.setPd(pd);
      List<PageData> listendpoint = this.endpointService.listendpointByTypeId(page);
     // System.out.println(page.getPageStr()+"分页2");
      mv.addObject("pd", pd);
      mv.addObject("page", page);
      mv.addObject("listendpoint", listendpoint);
      mv.addObject("listcase_s", listcase);
      mv.setViewName("system/endpoint/endpoint_list");
      mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
 
  @RequestMapping(value="/toendpointAdd")
  public ModelAndView toendpointAdd()
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
      List<CaseBean> listcase = new ArrayList();
      listcase=caseService.listAllCasetoEndpoint(pd_new);
      List<Dictionaries> dictList=dictionariesService.listAllDict("52c74f09b9904ab5a93eb0169e097465");
      
      if (!guid.equals("")) {
        pd = this.endpointService.findendpointById(pd);
      } else {
        guid = get32UUID();
      }
      mv.addObject("pd", pd);
      mv.addObject("dictList", dictList);
      mv.addObject("listcase", listcase);
     // mv.addObject("ID", guid);
      mv.addObject("action", action);
      mv.addObject("msg", "saveendpoint");
      mv.setViewName("system/endpoint/endpoint_add");
    }
    catch (Exception e)
    {
      this.logger.error(e.toString(), e);
    }
    return mv;
  }
  
  @RequestMapping(value="/saveendpoint")
  public ModelAndView saveendpoint(Page page)
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
      this.endpointService.saveendpoint(pd);
    }
    if ((pd.getString("action") != null) && (pd.getString("action").equals("2"))) {
    	 pd.put("CZMAN", Jurisdiction.getUsername());
      this.endpointService.updateendpoint(pd);
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
    List<CaseBean> listcase = new ArrayList();
    listcase=caseService.listAllCasetoEndpoint(pd_new);
    page.setPd(pd);
    List<PageData> listendpoint = this.endpointService.listendpointByTypeId(page);
    System.out.println(page.getPageStr()+"分页2");
    mv.addObject("pd", pd);
    mv.addObject("page", page);
    mv.addObject("listendpoint", listendpoint);
    mv.addObject("listcase", listcase);
    mv.setViewName("system/endpoint/endpoint_list");
    return mv;
  }
  
  @RequestMapping(value="/deleteendpoint")
  @ResponseBody
  public Object deleteendpoint(@RequestParam String ID)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    String errInfo = "";
    try
    {
      PageData pd = new PageData();
      
      pd = getPageData();
      
    
        this.endpointService.deleteendpointById(ID);
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
