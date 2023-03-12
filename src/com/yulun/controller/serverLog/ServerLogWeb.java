package com.yulun.controller.serverLog;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.yulun.service.ServerLogManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：我的服务记录
 * 创建人：351412933
 * 创建时间：2019-12-27
 */
public class ServerLogWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="serverLogService")
	private ServerLogManager serverLogService;
	
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
				if(cmd.equals("ServerLogAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
			        if(json.getString("usertype")!=null&&json.getString("usertype").equals("1")){
			        	pd.put("usertype", json.getString("usertype")); //1查询我的服务记录；其他查询所有的	
			        	 //System.out.println(pd_token.getString("ID")+"issh");
			        	pd.put("createman", pd_token.getString("ID"));
			        	
			        }
			        pd.put("ZXZ", pd_token.getString("ZXZ")); 
			        pd.put("dept", pd_token.getString("dept")); 
					pd.put("keywords", json.getString("keywords")); //综合查询字段
					pd.put("starttime", json.getString("starttime"));
					pd.put("zxid", json.getString("zxid"));
					pd.put("phone", json.getString("phone"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =serverLogService.listPage(page);
					if(list.size()>0){
						for(PageData pdServerLog:list){  
							pdServerLog.put("createdate", String.valueOf(pdServerLog.get("createdate")).substring(0, 19));
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
				}else if(cmd.equals("ServerLogAdd")) {
				
					pd.put("createman",pd_token.getString("ID"));
			        pd.put("type",json.get("type"));
			        pd.put("phone",json.get("phone"));
			        pd.put("name",json.get("name"));
			        pd.put("receivetime",json.get("receivetime")); 
			        pd.put("content",json.get("content")); 
			        pd.put("customid",json.get("customid")); 
			        pd.put("ucid",json.get("ucid"));
			        if (json.getString("type")!=null){
						String type = json.getString("type");
						if (type.equals("1")){
							pd.put("cate","0");
						}else if (type.equals("0")){
							pd.put("cate","1");
						}
					}
			        serverLogService.save(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("ServerLogEdit")) {
					pd.put("createman",pd_token.getString("ID"));
			        pd.put("type",json.get("type"));
			        pd.put("phone",json.get("phone"));
			        pd.put("name",json.get("name"));
			        pd.put("receivetime",json.get("receivetime")); 
			        pd.put("content",json.get("content")); 
			        pd.put("customid",json.get("customid")); 
			        pd.put("ucid",json.get("ucid"));
			        pd.put("id",json.get("id"));
					if (json.getString("type")!=null){
						String type = json.getString("type");
						if (type.equals("1")){
							pd.put("cate","0");
						}else if (type.equals("0")){
							pd.put("cate","1");
						}
					}
			        serverLogService.edit(pd);
			        object.put("success","true");
				    object.put("msg","修改成功");
				}else if(cmd.equals("ServerLogDel")) {
			        pd.put("id",json.get("id"));
		        	serverLogService.delete(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
			       
				}else if(cmd.equals("ServerLogFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdServerLog=serverLogService.findById(pd);
			        if(pdServerLog!=null&&pdServerLog.get("id")!=null){
						object.put("success","true");
						pdServerLog.put("createdate", String.valueOf(pdServerLog.get("createdate")).substring(0, 19));
						if(pdServerLog.get("shtime")!=null){
							pdServerLog.put("shtime", String.valueOf(pdServerLog.get("shtime")).substring(0, 19));
							
						}
						object.put("data",pdServerLog);
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
