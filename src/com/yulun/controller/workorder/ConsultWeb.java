package com.yulun.controller.workorder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.yulun.service.BlacklistManager;
import com.yulun.service.ConsultManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：咨询接口
 * 创建人：351412933
 * 创建时间：2019-12-27
 */
public class ConsultWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="consultService")
	private ConsultManager consultService;
	@Resource(name="blacklistService")
	private BlacklistManager blacklistService;
	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request)
			throws Exception {
		JSONObject object=new JSONObject();
		try{
			PageData pd = new PageData();
			String cmd = data.getString("cmd")==null?"":data.getString("cmd");
			PageData pd_stoken=new PageData();
			JSONObject json = data.getJSONObject("data");
			pd_stoken.put("TOKENID", json.get("tokenid"));
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			if(pd_token!=null){
				pd.put("ZXID", pd_token.getString("ZXID"));
				if(cmd.equals("ConsultAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        pd.put("ZXZ", pd_token.getString("ZXZ")); 
					pd.put("phone", json.getString("phone")); 
					pd.put("issh", json.getString("issh")); 
					pd.put("type", json.getString("type")); 
					pd.put("zxid", json.getString("zxid")); 
					pd.put("customid", json.getString("customid")); 
					pd.put("starttime", json.getString("starttime"));
					pd.put("keywords", json.getString("keywords"));
					pd.put("ucid",json.get("ucid"));
					pd.put("dept", pd_token.getString("dept")); 
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					pd.put("isdel", "0"); 
					
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =consultService.listPage(page);
		           // System.out.println(list);
					if(list.size()>0){
						pd_token.put("param_code", "recordsvr");
						PageData pdparam=blacklistService.findSysparam(pd_token);
					
						for(PageData pdServerLog:list){  
							if(pdServerLog!=null){
								
								if(pdServerLog.get("start_time")!=null&&String.valueOf(pdServerLog.get("start_time")).length()>10){
									pdServerLog.put("start_time", String.valueOf(pdServerLog.get("start_time")).substring(0, 19));
								}
								if(pdServerLog.get("end_time")!=null&&String.valueOf(pdServerLog.get("end_time")).length()>10){
									pdServerLog.put("end_time", String.valueOf(pdServerLog.get("end_time")).substring(0, 19));
								}
								if(pdServerLog!=null&&pdServerLog.getString("file_name")!=null){
									pdServerLog.put("lywjurl", pdparam.getString("param_value")+String.valueOf(pdServerLog.get("start_time")).substring(0, 10)+"/"+String.valueOf(pdServerLog.get("ext_no"))+"/"+pdServerLog.getString("file_name"));
								}
							}
							
						}
						
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("ConsultAdd")) {
					
					Date currentTime = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					String year = formatter.format(currentTime);
					PageData pd_year = new PageData();
					pd_year.put("year", year);
					
					pd_year = getNum(year);
					String code_num = String.valueOf(pd_year.get("code_num"));
					int length = code_num.length();
					String file_code = "";
					if(length == 1){
						file_code = "000" + code_num + "";
					}else if(length == 2){
						file_code = "00" + code_num + "";
					}else if(length == 3){
						file_code = "0" + code_num + "";
					}else{
						file_code = "" + code_num + "";
					}
					
				//	pd_year.put("code", Integer.parseInt(code_num)+1);
					
					pd.put("orderno", year+file_code);
				
					pd.put("czman",pd_token.getString("ID"));
			        pd.put("contype",json.get("contype"));
			        pd.put("content",json.get("content"));
			        pd.put("type", json.getString("type")); 
			        pd.put("receivetime",json.get("receivetime"));
			        pd.put("commonid",json.get("commonid"));
			        pd.put("phone",json.get("phone")); 
			        pd.put("title",json.get("title")); 
			        
			        pd.put("customid",json.get("customid")); 
			        pd.put("ucid",json.get("ucid")); 
			        consultService.save(pd);
			        
			        if("1".equals(code_num)){
			        	consultService.saveCode(pd_year);
					}else{
						pd_year.put("code_num", Integer.parseInt(code_num));
						consultService.editCode(pd_year);
					}
				    object.put("success","true");
					object.put("msg","保存成功");
					object.put("orderno", year+file_code+"");
			       
			       
				}else if(cmd.equals("ConsultEdit")) {
					pd.put("czman",pd_token.getString("ID"));
			        pd.put("contype",json.get("contype"));
			        pd.put("content",json.get("content"));
			        pd.put("receivetime",json.get("receivetime"));
			        pd.put("commonid",json.get("commonid"));
			        pd.put("phone",json.get("phone")); 
			        pd.put("title",json.get("title"));
			        pd.put("orderno",json.getString("orderno"));
			        pd.put("id",json.get("id"));
			        
			        PageData pdBlacklist=consultService.findById(pd);
				    if(pdBlacklist!=null&&pdBlacklist.get("orderno")!=null){
				    	consultService.edit(pd);
				        object.put("success","true");
					    object.put("msg","修改成功");
				    }else{
				    	object.put("success","false");
					    object.put("msg","修改失败，信息不存在");
				    }    
			        
				}else if(cmd.equals("ConsultFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdconsult=consultService.findById(pd);
			        if(pdconsult!=null&&pdconsult.get("id")!=null){
			        	
			        	
						object.put("success","true");
						
						object.put("data",pdconsult);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else{
					object.put("success","false");
			        object.put("msg","访问异常");
				}
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","操作异常");
		}	
        return object;
	}
	
	
	/*
	 * 获取编码
	 */
	public PageData getNum(String year) throws Exception{
		PageData pd_num = new PageData();
		pd_num.put("year", year);
		pd_num = consultService.findByYear(pd_num);
		if(pd_num == null){
			pd_num = new PageData();
			pd_num.put("year", year);
			pd_num.put("code_num", "1");
		}else{
			int code_num = Integer.parseInt(String.valueOf(pd_num.get("code_num")))+1;
			pd_num.put("code_num", code_num);
		}
		return pd_num;
	}
	
	
	
	
}

