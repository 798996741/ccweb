package com.fh.controller.activiti.hitask;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.fh.controller.activiti.AcBusinessController;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.dictionaries.DictionariesManager;

/**
 * 说明：已办任务
 */
@Controller
@RequestMapping(value="/hitask")
public class HiTaskController extends AcBusinessController {
	
	String menuUrl = "hitask/list.do"; 	//菜单地址(权限用)
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表已办任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		User user=Jurisdiction.getLoginUser();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");						//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");					//开始时间
		String lastEnd = pd.getString("lastEnd");						//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		}
		if(null != pd.getString("codes") ){
			pd.put("code", pd.getString("codes"));
		}	
		if(null != pd.getString("tssources") ){
			pd.put("tssource", pd.getString("tssources"));
		}	
		if(null != pd.getString("tsmans") ){
			pd.put("tsman", pd.getString("tsmans"));
		}	
		if(null != pd.getString("tsdepts") ){
			pd.put("tsdept", pd.getString("tsdepts"));
		}	
		
		if(null != pd.getString("types") ){
			pd.put("type", pd.getString("types"));
		}	
		
		if(null != pd.getString("tstypes")&&"" != pd.getString("tstypes") ){
			pd.put("tstype", pd.getString("tstypes"));
		}
		if(null != pd.getString("bigtstypes")&&"" != pd.getString("bigtstypes") ){
			pd.put("bigtstype", pd.getString("bigtstypes"));
		}

		if(null != pd.getString("tsclassifys")&&"" != pd.getString("tsclassifys") ){
			pd.put("tsclassifys", pd.getString("tsclassifys"));
		}
			
		
		if(null != pd.getString("endreasons") ){
			pd.put("endreason", pd.getString("endreasons"));
		}	
		pd.put("USERNAME", Jurisdiction.getUsername()); 				//查询当前用户的任务(用户名查询)
		pd.put("DWBM", user.getDWBM()); 				//查询当前用户的任务(角色编码查询)
		/*if(user!=null&&user.getDWBM()!=null){
			String dwbm= user.getDWBM();
			if(dwbm.equals("350101")){
				dwbm="3501";
			}
			pd.put("DWBM", dwbm);
		}*/
		page.setPd(pd);
		List<PageData>	varList = ruprocdefService.hitasklist(page);	//列出历史任务列表
		PageData pdTask =new PageData();
		for(int i=0;i<varList.size();i++){
			/*Long ztime = Long.parseLong(varList.get(i).get("DURATION_").toString());
			Long tian = ztime / (1000*60*60*24);
			Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
			Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
			Long miao = (ztime % (1000*60*60*24))%(1000*60*60)%(1000*60)/1000;
			varList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分"+miao+"秒");*/
			//varList.get(i).put("INITATOR", getInitiator(varList.get(i).getString("PROC_INST_ID_")));//流程申请人
			//查找是否接受
			if(varList.get(i).get("tsdept").toString().indexOf(",")>=0){
				pd.put("name_", "多部门工单处理");
				pd.put("proc_id", varList.get(i).get("proc_id"));
				pdTask =ruprocdefService.getActRuTask(pd);
				if(pdTask!=null){
					varList.get(i).put("ISRECEIVE", pdTask.getString("ISRECEIVE"));
				}
				
			}else{
				pd.put("name_", "单部门工单处理");
				pd.put("proc_id", varList.get(i).get("proc_id"));
				pdTask =ruprocdefService.getActRuTask(pd);
				
				if(pdTask!=null){
					varList.get(i).put("ISRECEIVE", pdTask.getString("ISRECEIVE"));
				}
			}
			
		
		}
		mv.setViewName("activiti/hitask/hitask_list");
		mv.addObject("varList", varList);

	
	    if(user!=null&&user.getDWBM()!=null){
			String dwbm= user.getDWBM();
			if(dwbm.equals("350101")){
				dwbm="3501";
			}
			pd.put("dwbm", dwbm);
			
		}
	    pd.put("parentId", "9ed978b4a4674adb9c08f45d2e2d9813");
		List<PageData> tssourceList=dictionariesService.listAllDictByParentId(pd);
	    mv.addObject("tssourceList", tssourceList);
	    pd.put("AREA_LEVEL", "3");
	    List<PageData> tsbmList=areamanageService.listAll(pd);
	    mv.addObject("tsdeptList", tsbmList);
	    pd.put("parentId", "1b303f7026934f68a6bd1ea01433db19");
	    List<PageData> tstypeList=dictionariesService.listAllDictByParentId(pd);
		pd.put("parentId", "55b155b57041478c9c3c15848e8d0225");
		List<PageData> tsclassifys=dictionariesService.listAllDictByParentId(pd);
		mv.addObject("tsclassifyList", tsclassifys);
	 
		
		
	    
	    mv.addObject("tstypeLists", tstypeList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());						//按钮权限
		return mv;
	}
	
}
