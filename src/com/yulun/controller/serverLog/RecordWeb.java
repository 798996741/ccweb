package com.yulun.controller.serverLog;

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
import com.yulun.service.RecordManager;
import com.yulun.service.ServerLogManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：我的服务记录-录音
 * 创建人：351412933
 * 创建时间：2019-12-27
 */
public class RecordWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="recordService")
	private RecordManager recordService;
	
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
				if(cmd.equals("RecordAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("phone", json.getString("phone")); 
					pd.put("keywords", json.getString("keywords")); 
					pd.put("ZXZ", pd_token.getString("ZXZ")); 
					pd.put("zxid", json.getString("zxid")); 
					pd.put("thfx", json.getString("thfx")); 
					pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					pd.put("dept", pd_token.getString("dept")); 
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =recordService.listPage(page);
		           // System.out.println(list);
					if(list.size()>0){
						pd_token.put("param_code", "recordsvr");
						PageData pdparam=blacklistService.findSysparam(pd_token);
					
						for(PageData pdServerLog:list){  
							if(pdServerLog!=null){
								if(pdServerLog.get("lysj")!=null&&String.valueOf(pdServerLog.get("lysj")).length()>10){
									pdServerLog.put("lysj", String.valueOf(pdServerLog.get("lysj")).substring(0, 19));
								}
								if(pdServerLog!=null&&pdServerLog.getString("lywj")!=null){
									pdServerLog.put("lywjurl", pdparam.getString("param_value")+String.valueOf(pdServerLog.get("lysj")).substring(0, 10)+"/"+String.valueOf(pdServerLog.get("ext_no"))+"/"+pdServerLog.getString("lywj"));
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
				}else if(cmd.equals("RecordPf")) {
					pd.put("pfman",pd_token.getString("ID"));
			        pd.put("score",json.get("score"));
			        pd.put("pfremark",json.get("pfremark"));
			        pd.put("id",json.get("id"));
			        recordService.pf(pd);
			        object.put("success","true");
				    object.put("msg","评分成功");
				}else if(cmd.equals("RecordDel")) {
					String DATA_IDS =json.getString("id")==null?"":json.getString("id");
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					recordService.deleteAll(ArrayDATA_IDS);
		        	object.put("success","true");
				    object.put("msg","删除成功");
				}else if(cmd.equals("RecordDelWarn")){
					if("861d1018f88a47b592f9e59dbb5032e9".equals(pd_token.get("ROLE_ID"))){
						Calendar calendar=Calendar.getInstance();
						int month=calendar.get(Calendar.MONTH)+1;
						int day=calendar.get(Calendar.DAY_OF_MONTH);
						System.out.println("月"+month+"日"+day);
						if(month==11&&day==25){
							PageData pa=new PageData();
							System.out.println("---------");
							pa.put("paramValue","1");
							recordService.updRecordClearWarn(pa);
						}
					}
					PageData pageData=recordService.findRecordClearWarn();
					if("0".equals(pageData.get("param_value"))){
						object.put("isClear",false);
					}else if("1".equals(pageData.get("param_value"))){
						object.put("isClear",true);
					}
					object.put("success","true");
				}else if(cmd.equals("clearRecord")){

					PageData pageData=new PageData();
					pageData.put("paramValue","0");
					recordService.updRecordClearWarn(pageData);
					object.put("success","true");
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

	public static void main(String[] args) {
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("月"+month+"日"+day);
	}
	
}

