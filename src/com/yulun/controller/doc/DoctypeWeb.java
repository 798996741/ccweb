package com.yulun.controller.doc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.PageData;
import com.xxgl.service.doctype.DoctypeManager;
import com.xxgl.service.mng.DocManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：知识库类型相关功能
 * 创建人：351412933
 * 创建时间：2019-12-26
 */
public class DoctypeWeb implements CommonIntefate {

	
	@Resource(name="doctypeService")
	private DoctypeManager doctypeService;
	
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="docService")
	private DocManager docService;
	
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
				if(cmd.equals("DoctypeAll")){  //根据提交的方法执行相应的操作，获取知识库分类分类 
					pd.put("type", json.getString("type"));
					pd.put("ishot", json.getString("ishot"));
					pd.put("state", json.getString("state"));
					if(json.getString("type")!=null&&json.getString("type").equals("3")){
			        	pd.put("dept",pd_token.getString("dept"));
			        }
					if(json.getString("type")!=null&&json.getString("type").equals("2")){
						pd.put("userid", pd_token.getString("ID"));
			        }

					pd.put("userdept", pd_token.getString("dept")); 
					pd.put("parentid",  json.getString("parentid"));
					List<PageData> pdlist=doctypeService.listAllDoctype(pd);
					if(pdlist.size()>0){
						for(PageData pddoc:pdlist){
							pddoc.put("createdate", String.valueOf(pddoc.get("createdate")).substring(0, 19));	
						}
						object.put("success","true");
						object.put("data",pdlist);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("DoctypeAdd")) {
				
					String type=String.valueOf(json.get("type")==null?"":json.get("type"));
					String dept=String.valueOf(json.get("dept")==null?"":json.get("dept"));
					pd.put("ishot", json.getString("ishot"));
					pd.put("createman",pd_token.getString("ID"));
			        pd.put("name",json.get("name"));
			        pd.put("parentid",json.get("parentid"));//父类id
			        pd.put("sort",json.get("sort"));  //排序
			        pd.put("state",json.get("state")); //状态 1、启用 0，禁用
			        pd.put("cont",json.get("cont"));
			        pd.put("type",type); //1公共接口，2用户，3部门
			        if(type.equals("3")&&dept.equals("")){
			        	pd.put("dept",pd_token.getString("dept"));
			        }
			        doctypeService.save(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("DoctypeEdit")) {
					String type=String.valueOf(json.get("type")==null?"":json.get("type"));
					String dept=String.valueOf(json.get("dept")==null?"":json.get("dept"));
					pd.put("createman",pd_token.getString("ID"));
			        pd.put("name",json.get("name"));
			        pd.put("id",json.get("id"));
			        pd.put("parentid",json.get("parentid"));//父类id
			        pd.put("sort",json.get("sort"));  //排序
			        pd.put("state",json.get("state")); //状态 1、启用 0，禁用
			        pd.put("cont",json.get("cont"));
			        pd.put("ishot", json.getString("ishot"));
			        pd.put("type",type); //1公共接口，2用户，3部门
			        if(type.equals("3")&&dept.equals("")){
			        	pd.put("dept",pd_token.getString("dept"));
			        }else{
			        	if(!dept.equals("")){
			        		pd.put("dept",dept);
			        	}
			        }
			        doctypeService.edit(pd);
			        object.put("success","true");
				    object.put("msg","修改成功");
				}else if(cmd.equals("DoctypeDel")) {
			        pd.put("id",json.get("id"));
			        pd.put("doctype_id",json.get("id"));
			        List<PageData> pdlist=docService.listAll(pd);
			        if(pdlist.size()==0){
			        	doctypeService.delete(pd);
			        	object.put("success","true");
					    object.put("msg","删除成功");
			        }else{
			        	object.put("success","false");
				        object.put("msg","删除失败，该分类有相应的知识库信息");
			        }
			       
				}else if(cmd.equals("DoctypeFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdDoc=doctypeService.findById(pd);
			        if(pdDoc!=null&&pdDoc.get("id")!=null){
						object.put("success","true");
						pdDoc.put("createdate", String.valueOf(pdDoc.get("createdate")).substring(0, 19));
						object.put("data",pdDoc);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			object.put("success","false");
			object.put("msg","操作异常");
		}	
        return object;
	}
	
}
