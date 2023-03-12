package com.yulun.controller.publicinfo;

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
import com.yulun.service.BlacklistManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：黑名单
 * 创建人：351412933
 * 创建时间：2019-12-27
 */
public class BlacklistWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

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
				if(cmd.equals("BlacklistAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("tel", json.getString("tel")); 
					pd.put("subremark", json.getString("subremark")); 
					pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					
					pd.put("czman", json.getString("czman")); 
					pd.put("issh", json.getString("issh")); 
					pd.put("isdel", "0"); 
					pd.put("zxid", json.getString("zxid")); 
					pd.put("dept", pd_token.getString("dept")); 
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =blacklistService.listPage(page);
		           // System.out.println(list);
					if(list.size()>0){
						pd_token.put("param_code", "recordsvr");
						PageData pdparam=blacklistService.findSysparam(pd_token);
						for(PageData pdlist:list){
							if(pdlist.get("start_time")!=null&&String.valueOf(pdlist.get("start_time")).length()>10){
								pdlist.put("start_time", String.valueOf(pdlist.get("start_time")).substring(0, 19));
							}
							if(pdlist.get("end_time")!=null&&String.valueOf(pdlist.get("end_time")).length()>10){
								pdlist.put("end_time", String.valueOf(pdlist.get("end_time")).substring(0, 19));
							}
							if(pdlist!=null&&pdlist.getString("file_name")!=null){
								pdlist.put("lywjurl", pdparam.getString("param_value")+String.valueOf(pdlist.get("start_time")).substring(0, 10)+"/"+String.valueOf(pdlist.get("ext_no"))+"/"+pdlist.getString("file_name"));
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
				}else if(cmd.equals("BlacklistSh")) {
					pd.put("shman",pd_token.getString("ID"));
			        pd.put("issh",json.get("issh"));
			        pd.put("shremark",json.get("shremark"));
			        pd.put("id",json.get("id"));
			        PageData pdBlacklist=blacklistService.findById(pd);
				    if(pdBlacklist!=null&&pdBlacklist.get("id")!=null){
				        blacklistService.sh(pd);
				        object.put("success","true");
					    object.put("msg","审核成功");
				    }else{
				    	object.put("success","false");
					    object.put("msg","审核失败，信息不存在");
				    }    
				}else if(cmd.equals("BlacklistDel")) {
					pd.put("remark",json.get("remark"));
				    pd.put("id",json.get("id"));
//				    PageData pdBlacklist=blacklistService.findById(pd);
//				    if(pdBlacklist!=null&&pdBlacklist.get("id")!=null){
//				    	pd.put("isdel","1");
//					    blacklistService.deleteAll(pd);
//
//			        	object.put("success","true");
//					    object.put("msg","删除成功");
//				    }else{
//				    	object.put("success","false");
//					    object.put("msg","删除失败，信息不存在");
//				    }
					blacklistService.deleteBlack(pd);
					object.put("success","true");
					object.put("msg","删除成功");
				    
				}else if(cmd.equals("BlacklistAdd")) {
				
					pd.put("czman",pd_token.getString("ID"));
			        pd.put("subremark",json.get("subremark"));
			        pd.put("type",json.get("type"));
			        pd.put("ucid",json.get("ucid"));
			        pd.put("tel",json.get("tel"));
			        pd.put("supremark",json.get("supremark")); 
			        PageData pdBlacklist=blacklistService.findByTel(pd);
			        if(pdBlacklist!=null&&pdBlacklist.get("id")!=null){
			        	object.put("success","false");
						object.put("msg","保存失败，手机号码已存在");
			        }else{
			        	 blacklistService.save(pd);
					     object.put("success","true");
						 object.put("msg","保存成功");
			        }
			       
			       
				}else if(cmd.equals("BlacklistEdit")) {
					pd.put("czman",pd_token.getString("ID"));
					pd.put("subremark",json.get("subremark"));
			        pd.put("tel",json.get("tel"));
			        pd.put("supremark",json.get("supremark")); 
			        pd.put("id",json.get("id"));
			        pd.put("type",json.get("type"));
			        pd.put("ucid",json.get("ucid"));
			        PageData pdBlacklist=blacklistService.findByTel(pd);
			        if(pdBlacklist!=null&&pdBlacklist.get("id")!=null){
			        	object.put("success","false");
						object.put("msg","修改失败，手机号码已存在");
			        }else{
				        blacklistService.edit(pd);
				        object.put("success","true");
					    object.put("msg","修改成功");
			        }
				}else if(cmd.equals("BlacklistFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdBlacklist=blacklistService.findById(pd);
			        if(pdBlacklist!=null&&pdBlacklist.get("id")!=null){
			        	
			        	pd_token.put("param_code", "recordsvr");
						PageData pdparam=blacklistService.findSysparam(pd_token);
						if(pdBlacklist!=null&&pdBlacklist.getString("lywj")!=null){
							pdBlacklist.put("lywjurl", pdparam.getString("param_value")+pdBlacklist.getString("lywj"));
						}
						object.put("success","true");
						
						object.put("data",pdBlacklist);
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
	
	
	
	
	
}

