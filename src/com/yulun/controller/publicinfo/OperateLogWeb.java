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
import com.fh.service.system.operatelog.OperateLogManager;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.yulun.service.AddressManager;
import com.yulun.service.AddressManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：操作日志
 * 创建人：351412933
 * 创建时间：2020-06-06
 */
public class OperateLogWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name = "operatelogService")
	private OperateLogManager operatelogService;
	
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
				if(cmd.equals("OperateLogAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("zxid", json.getString("zxid")); 
					pd.put("mappername", json.getString("mappername")); 
					pd.put("modulename", json.getString("modulename")); 
					
					pd.put("keywords", json.getString("keywords")); 
					pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					pd.put("dwbm", pd_token.getString("dept")); 
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =operatelogService.list(page);
					if(list.size()>0){
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("ModulenameAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("isfront",json.getString("isfront")); 
					
		            list =operatelogService.modulelistGroupByModulename(pd);
		           // System.out.println(list);
					if(list.size()>0){
						object.put("success","true");
						object.put("data",list);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if("findOperateLog".equals(cmd)){  //根据提交的方法执行相应的操作，获取知识库分页信息
					Page page = new Page();
					Integer pageIndex;
					Integer pageSize;
					List<PageData> list;

					pd.put("zxid", json.getString("zxid"));
					pd.put("mappername", json.getString("mappername"));
					pd.put("modulename", json.getString("modulename"));

					pd.put("keywords", json.getString("keywords"));
					pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					pd.put("dwbm", pd_token.getString("dept"));
					pageIndex = json.getInteger("pageIndex");
					page.setCurrentPage(pageIndex);
					pageSize = json.getInteger("pageSize");
					page.setShowCount(pageSize);
					page.setPd(pd);
					list =operatelogService.findOperateLog(page);
					 System.out.println(list);
					if(list.size()>0){
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
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
	
	
	 public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

   
}

