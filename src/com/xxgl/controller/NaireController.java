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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.xxgl.utils.ResponseUtils;
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.NaireManager;


/** 
 * 说明：参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 */
@Controller
@RequestMapping(value="/naire")
public class NaireController extends BaseController {
	
	String menuUrl = "naire/list.do"; //菜单地址(权限用)
	
	@Resource(name="naireService")
	private NaireManager naireService;
	@Resource(name="caseService")
	private CaseManager caseService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增naire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		PageData pd_c = new PageData();
		pd_c=naireService.findByCode(pd);
		if(pd_c==null||pd_c.getString("ID")==null){
			pd.put("ID", this.get32UUID());	//主键
			//pd.put("CZMAN",  Jurisdiction.getUsername());
			
			//A：答案1；B：答案2；C：答案3；D：答案4；
			String answer=pd.getString("ANSWER")==null?"":pd.getString("ANSWER");
			answer=answer.replace("：", ":");
			answer=answer.replace("；", ";");
			pd.put("ANSWER", answer);
			pd.put("CREATEMAN",  Jurisdiction.getUsername());
			naireService.save(pd);
			mv.addObject("msg","success_");
		}else{
			mv.addObject("msg","error1");
		}
		
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveNext")
	public ModelAndView saveNext() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增naire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		PageData pd_c = new PageData();
		pd_c=naireService.findByNextAnswer(pd);
		if(pd!=null&&!pd.getString("ID").equals("")){
			if(pd_c==null||pd_c.getString("ID")==null){
				
				pd.put("CREATEMAN",  Jurisdiction.getUsername());
				naireService.editNext(pd);
				mv.addObject("msg","success_");
			}else{
				mv.addObject("msg","error1");
			}
		}else{
			if(pd_c==null||pd_c.getString("ID")==null){
				pd.put("ID", this.get32UUID());	//主键
				pd.put("CREATEMAN",  Jurisdiction.getUsername());
				naireService.saveNext(pd);
				mv.addObject("msg","success_");
			}else{
				mv.addObject("msg","error1");
			}
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
		logBefore(logger, Jurisdiction.getUsername()+"删除naire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		
		naireService.delete(pd);
		
		out.write("success");
		out.close();
	}
	
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteNext")
	public void deleteNext(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除naire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		naireService.deleteNext(pd);
		out.write("success");
		out.close();
	}
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改naire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		PageData pd_c = new PageData();
		pd_c=naireService.findByCode(pd);
		if(pd_c==null||pd_c.getString("ID")==null){
			String answer=pd.getString("ANSWER")==null?"":pd.getString("ANSWER");
			answer=answer.replace("：", ":");
			answer=answer.replace("；", ";");
			pd.put("ANSWER", answer);
			naireService.edit(pd);
			mv.addObject("msg","success_");
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
		logBefore(logger, Jurisdiction.getUsername()+"列表naire");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		List<PageData>	varList = naireService.list(page);	//列出naire列表
		mv.setViewName("xxgl/naire/naire_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	/**获取客户
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getNaireList")
	@ResponseBody
	public void getNaireList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		String str=this.getMsg(pd);
		ResponseUtils.renderJson(response,str);
	}
	
	
	@RequestMapping(value="/getNaireNext")
	@ResponseBody
	public void getNaireNext(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"批量删除task");
		PageData pd = new PageData();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		PageData pd_new = new PageData();
		PageData pd_new_cus = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		String str=this.getMsgNext(pd);
		ResponseUtils.renderJson(response,str);
	}
	
	
	
	public String getMsg(PageData pd){
		JSONObject object_new = new JSONObject();
		try{
			Page page=new Page();
			pd = this.getPageData();
			String action=pd.getString("action")==null?"":pd.getString("action");
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			page.setPd(pd);
			List<PageData>	varList = naireService.list(page);	//列出naire列表
	
			String tablestr="";
			String theadstr="<table id=\"nairetable\"  class=\"table table-bordered table-hover\">";
			 theadstr=theadstr+"<thead><tr>";
			String tbodystr="";
			boolean boo=false;
			PageData pd_new_filed = new PageData();
			theadstr+="<th class=\"center cy_th\" style=\"min-width: 30px;\">编号</th>";
			theadstr+="<th id=\"cy_thk\"></th>";
			theadstr+="<th class=\"center\" style=\"min-width: 60px;\">题目类型</th>";
			theadstr+="<th class=\"center\">题目</th>";
			theadstr+="<th class=\"center\">答案</th>";
			theadstr+="<th class=\"center\" style=\"min-width: 80px;\">备注信息</th>";
			theadstr+="<th class=\"center\" style=\"min-width: 60px;\">创建人</th>";
			theadstr+="<th class=\"center\" style=\"min-width: 80px;\">创建时间</th>";
			theadstr+="<th class=\"center\" style=\"min-width: 100px;\">操作</th>";
			
			theadstr=theadstr+"</tr></thead>";
			
			if(varList.size()>0){
				tbodystr="<tbody><tr>";
				for(PageData pddata:varList){
					
					tbodystr+="<td class='center cy_td'>"+(pddata.getString("CODE")==null?"":pddata.getString("CODE"))+"</td>";
					tbodystr+="<td id='cy_thk'></td>";
					tbodystr+="<td class='center'>"+(pddata.getString("TYPENAME")==null?"":pddata.getString("TYPENAME"))+"</td>";
					
					tbodystr+="<td class='center'  style=\"max-width:250px;\">"+(pddata.getString("SUBJECT")==null?"":pddata.getString("SUBJECT"))+"</td>";
					
					tbodystr+="<td class='center'  style=\"max-width:200px;\">"+(pddata.getString("ANSWER")==null?"":pddata.getString("ANSWER"))+"</td>";
					tbodystr+="<td class='center' style=\"max-width:150px;\">"+(pddata.getString("REMARK")==null?"":pddata.getString("REMARK"))+"</td>";
					
					tbodystr+="<td class='center'>"+(pddata.getString("CREATEMAN")==null?"":pddata.getString("CREATEMAN"))+"</td>";
					tbodystr+="<td class='center'>"+(pddata.get("CREATEDATE")==null?"":pddata.get("CREATEDATE"))+"</td>";
					
					tbodystr+="<td>";
					if(action.equals("")){
						tbodystr+= "<a class=\"cy_bj\" title=\"编辑\" onclick=\"edit('"+pddata.getString("ID")+"');\"> <i title=\"编辑\"></i></a>";
						//if(pddata.getString("TYPENAME").indexOf("单")>=0||pddata.getString("TYPENAME").indexOf("判断")>=0){
							tbodystr+= "<a  class=\"cy-pzd\" onclick=\"setnext('"+pddata.getString("ID")+"','"+pddata.getString("CODE")+"');\"> <i title=\"设置下一题\"></i></a>";			
						//}
						tbodystr+= "<a class=\"cy_sc\" onclick=\"del('"+pddata.getString("ID")+"');\"> <i  title=\"删除\"></i></a>";			
					}
					tbodystr+= "</td>";
					
					tbodystr=tbodystr+"</tr>";
				}
				tbodystr=tbodystr+"</tbody>";
			}else{
				tbodystr=tbodystr+"<tbody><tr ><td style=\"color:red;text-align:center\" colspan=\"8\">暂无数据</td></tr></tbody>";
				
			
			}
			tbodystr=tbodystr+"</table>";
			tbodystr=tbodystr+"<script type=\"text/javascript\">$(function() {$('#nairetable').DataTable({";
			tbodystr=tbodystr+"'paging'      : true,";
			tbodystr=tbodystr+"'lengthChange': false,";
			tbodystr=tbodystr+"'searching'   : false,";
			tbodystr=tbodystr+"'ordering'    : false,";
			tbodystr=tbodystr+"'info'        : true,";
			tbodystr=tbodystr+"'autoWidth'   : true";
			tbodystr=tbodystr+"});});</script>";
			JSONObject object = new JSONObject();
			
			String naireString=theadstr+tbodystr;;
			
			object_new.put("naireString",naireString);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return object_new.toString();
	}
	
	public String getMsgNext(PageData pd){
		JSONObject object_new = new JSONObject();
		try{
			Page page=new Page();
			pd = this.getPageData();
			
			List<PageData>	varList = naireService.listAllNext(pd);	//列出naire列表
	
			String tablestr="";
			String theadstr="<thead><tr>";
			String tbodystr="";
			boolean boo=false;
			PageData pd_new_filed = new PageData();
			theadstr+="<th class=\"center\">答案</th>";
			theadstr+="<th class=\"center\">下一题</th>";
			theadstr+="<th class=\"center\">创建人</th>";
			theadstr+="<th class=\"center\">创建时间</th>";
			theadstr+="<th class=\"center\">操作</th>";
			
			theadstr=theadstr+"</tr></thead>";
			
			if(varList.size()>0){
				tbodystr="<tbody><tr>";
				for(PageData pddata:varList){
					
					tbodystr+="<td class='center'>"+(pddata.getString("ANSWER")==null?"":pddata.getString("ANSWER"))+"</td>";
					tbodystr+="<td class='center'><pre style=\"border:0px\">"+(pddata.getString("SUBJECT")==null?"":pddata.getString("SUBJECT"))+"</pre></td>";
					
					tbodystr+="<td class='center'>"+(pddata.getString("CREATEMAN")==null?"":pddata.getString("CREATEMAN"))+"</td>";
					tbodystr+="<td class='center'>"+(pddata.get("CREATEDATE")==null?"":pddata.get("CREATEDATE"))+"</td>";
					
					tbodystr+="<td>";
					if(pd.getString("action")==null||pd.getString("action").equals("")){
						tbodystr+= "<a class=\"btn btn-xs btn-success\" title=\"编辑\" onclick=\"edit_next('"+pddata.getString("ANSWER")+"','"+pddata.getString("ID")+"','"+pddata.getString("NEXT_ID")+"');\"> <i class=\"ace-icon fa fa-pencil-square-o bigger-120\" title=\"编辑\"></i></a>";
						tbodystr+= "<a style=\"margin-left:10px;\" class=\"btn btn-xs btn-danger\" onclick=\"del_next('"+pddata.getString("ID")+"');\"> <i class=\"ace-icon fa fa-trash-o bigger-120\" title=\"删除\"></i></a>";			
					}
					
					tbodystr+= "</td>";
					
					tbodystr=tbodystr+"</tr></tbody>";
				}
			}else{
				tbodystr=tbodystr+"<tbody><tr ><td style=\"color:red;text-align:center\" colspan=\"8\">暂无数据</td></tr></tbody>";
			}
			JSONObject object = new JSONObject();
			
			String naireString=theadstr+tbodystr;;
			
			object_new.put("naireString",naireString);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return object_new.toString();
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
	    List<Dictionaries> dictList=dictionariesService.listAllDict("2b46f220e3ba4cb5adb98637a64dcf94");
	    mv.addObject("dictList", dictList);
	   
	    String ID=this.get32UUID();
	    mv.addObject("ID", ID);
		pd = this.getPageData();
		PageData pd_c = new PageData();
		pd_c=naireService.findByCode(pd);
		if(pd_c!=null&&pd_c.getString("CODE")!=null){
			String CODE=String.valueOf(Integer.parseInt(pd_c.getString("CODE")==null?"1":pd_c.getString("CODE"))+1);
			pd.put("CODE", CODE);
		}else{
			pd.put("CODE","1" );
		}
		
		mv.setViewName("xxgl/naire/naire_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goNext")
	public ModelAndView goNext()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		PageData pd = new PageData();
		PageData pd_naire = new PageData();
		pd = this.getPageData();
		
		List<PageData> varOList = naireService.findByNext(pd);
		pd_naire = naireService.findById(pd);	//根据ID读取
		List<PageData> pdList=new ArrayList();
		if(pd_naire!=null){
			String answer=pd_naire.getString("ANSWER")==null?"":pd_naire.getString("ANSWER");
			//System.out.println("answer:"+answer);
			String[] arr=answer.split(";");
			String[] arr1=null;
			PageData pd_answer=new PageData();
			for(int i=0;i<arr.length;i++){
				pd_answer=new PageData();
				arr1=arr[i].split(":");
				if(arr1.length>=1){
					pd_answer.put("answer", arr1[0]);
				}
				pdList.add(pd_answer);
			}
		}
		System.out.println("pdList:"+pdList);
		mv.setViewName("xxgl/naire/naire_next");
		mv.addObject("msg", "save");
		mv.addObject("varOList", varOList);
		mv.addObject("pdList", pdList);
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
	   
		List<Dictionaries> dictList=dictionariesService.listAllDict("2b46f220e3ba4cb5adb98637a64dcf94");
		mv.addObject("dictList", dictList);
	    
		pd = this.getPageData();
		//System.out.println(pd.getString("action"));
		mv.addObject("action", pd.getString("action"));
		pd = naireService.findById(pd);	//根据ID读取
		//System.out.println(pd.getString("action"));
		mv.setViewName("xxgl/naire/naire_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除naire");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			naireService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出naire到excel");
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
		List<PageData> varOList = naireService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STARTADDR"));	    //1
			vpd.put("var2", varOList.get(i).getString("NUM"));	    //2
			vpd.put("var3", varOList.get(i).getString("CASEID"));	    //3
			vpd.put("var4", varOList.get(i).getString("TYPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("naire1"));	    //5
			vpd.put("var6", varOList.get(i).getString("naire2"));	    //6
			vpd.put("var7", varOList.get(i).getString("naire3"));	    //7
			vpd.put("var8", varOList.get(i).getString("naire4"));	    //8
			vpd.put("var9", varOList.get(i).getString("naire5"));	    //9
			vpd.put("var10", varOList.get(i).getString("naire6"));	    //10
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
