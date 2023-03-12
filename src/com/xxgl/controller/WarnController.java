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
import com.xxgl.service.mng.FieldDetailManager;


/** 
 * 说明：内容详细
 * 创建人：351412933
 * 创建时间：2018-07-25
 */
@Controller
@RequestMapping(value="/warn")
public class WarnController extends BaseController {
	
	String menuUrl = "warn/list.do"; //菜单地址(权限用)
	@Resource(name="fielddetailService")
	private FieldDetailManager fielddetailService;
	
	@Resource(name="caseService")
	private CaseManager caseService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Warn");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession();
		User user=(User) session.getAttribute(Const.SESSION_USER);
		pd = this.getPageData();
		
		List<PageData>	varList = fielddetailService.warnlist(pd);	//列出FieldDetail列表
		PageData pd_new = new PageData();
		String role_id=user.getROLE_ID();
	    if(role_id!=null&&role_id.equals("1")){
	    	  
	    }else{
	    	pd_new.put("USERNAME", user.getUSERNAME()); 
	    	pd.put("USERNAME", user.getUSERNAME()); 
	    }
		
		List<CaseBean> listcase = new ArrayList();
	    listcase=caseService.listAllCasetoEndpoint(pd_new);
	    mv.addObject("listcase_s", listcase);
		
	    List<Dictionaries> dictList_zd=dictionariesService.listAllDict("6b272abc46a34105ac98bb03dd71a549");
	    mv.addObject("dictList_zd", dictList_zd); 
		
		mv.setViewName("xxgl/warn/warn_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
}
