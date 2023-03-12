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
public class ParamWeb implements CommonIntefate {


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
				if(cmd.equals("ParamAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
			        List<PageData> list;
					pd.put("param_code", json.getString("param_code")); 
		            list =blacklistService.listSysparams(pd);
					if(list.size()>0){
						object.put("success","true");
						object.put("data",list);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("zxLogAdd")) {
				
					//ZXID  坐席id
				    //CZXM  操作项目
				    //UCID  通话标识
				    //NOTE  备注信息
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					pd.put("createman",pd_token.getString("ID"));
			        pd.put("ZXID",json.get("ZXID"));
			        pd.put("CZXM",json.get("CZXM"));
			        pd.put("UCID",json.get("UCID"));
			        pd.put("NOTE",json.get("NOTE")); 
			        pd.put("RQ", sdf.format(d)); 
			        blacklistService.saveZxlog(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
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

