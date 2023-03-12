package com.yulun.controller.doc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.xxgl.service.mng.DocManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：知识库相关功能
 * 创建人：351412933
 * 创建时间：2019-12-27
 */
public class DocWeb implements CommonIntefate {

	
	@Resource(name="DocService")
	private DocManager DocService;
	
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
				if(cmd.equals("DocAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        //System.out.println(json.getString("issh")+"issh");
					pd.put("type", json.getString("type"));
					pd.put("ishot", json.getString("ishot"));
					
					pd.put("keywords", json.getString("doctitle"));
					pd.put("doctype_id", json.getString("doctype_id"));
					if( json.getString("type")!=null&& (json.getString("type").equals("3")|| json.getString("type").equals("1"))){
						pd.put("dept", pd_token.getString("dept"));
					}
					if(json.getString("type")!=null&& json.getString("type").equals("2")){
						pd.put("userid", pd_token.getString("ID"));
					}
					
					if(json.getString("type")!=null&& json.getString("type").equals("all")){
						pd.put("userid", pd_token.getString("ID"));
						pd.put("dept", pd_token.getString("dept"));
					}
					
					
					
					pd.put("issh", json.getString("issh"));
					pd.put("zxid", json.getString("zxid")); 
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =docService.listPage(page);
					if(list.size()>0){
						for(PageData pddoc:list){  
							pddoc.put("createdate", String.valueOf(pddoc.get("createdate")).substring(0, 19));
							if(pddoc.get("shtime")!=null){
								pddoc.put("shtime", String.valueOf(pddoc.get("shtime")).substring(0, 19));
							}
							if(pddoc.get("lastmoddate")!=null){
								pddoc.put("lastmoddate", String.valueOf(pddoc.get("lastmoddate")).substring(0, 19));
							}
							if(pddoc.get("lastclickdate")!=null){
								pddoc.put("lastclickdate", String.valueOf(pddoc.get("lastclickdate")).substring(0, 19));
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
				}else if(cmd.equals("DocAuditAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        //System.out.println(json.getString("issh")+"issh");
					pd.put("type", json.getString("type"));
					pd.put("ishot", json.getString("ishot"));
					pd.put("issh", json.getString("issh"));
					pd.put("keywords", json.getString("doctitle"));
					pd.put("doctype_id", json.getString("doctype_id"));
					if( json.getString("type")!=null&& (json.getString("type").equals("3")|| json.getString("type").equals("1"))){
						pd.put("dept", pd_token.getString("dept"));
					}
					if( json.getString("type")!=null&& json.getString("type").equals("2")){
						pd.put("userid", pd_token.getString("ID"));
					}
					pd.put("dept", pd_token.getString("dept")); 
					
					//pd.put("issh", json.getString("issh"));
					//pd.put("type", json.getString("type"));
					pd.put("zxid", json.getString("zxid"));
					
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =docService.listAuditPage(page);
					if(list.size()>0){
						for(PageData pddoc:list){  
							pddoc.put("createdate", String.valueOf(pddoc.get("createdate")).substring(0, 19));
							if(pddoc.get("shtime")!=null){
								pddoc.put("shtime", String.valueOf(pddoc.get("shtime")).substring(0, 19));
							}
							if(pddoc.get("lastmoddate")!=null){
								pddoc.put("lastmoddate", String.valueOf(pddoc.get("lastmoddate")).substring(0, 19));
							}
							if(pddoc.get("lastclickdate")!=null){
								pddoc.put("lastclickdate", String.valueOf(pddoc.get("lastclickdate")).substring(0, 19));
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
				}else if(cmd.equals("DocAdd")) {
				
					String type=String.valueOf(json.get("type")==null?"":json.get("type"));
					String dept=String.valueOf(json.get("dept")==null?"":json.get("dept"));
					pd.put("ishot", json.getString("ishot"));
					pd.put("validate", json.getString("validate"));
					pd.put("createman",pd_token.getString("ID"));
			        pd.put("doctitle",json.get("doctitle"));
			        pd.put("doctype_id",json.get("doctype_id"));//父类id
			      //  pd.put("sort",json.get("sort"));  //排序
			        pd.put("doctype",json.get("doctype")); //状态 1、启用 0，禁用
			        pd.put("doccont",json.get("doccont"));
			        pd.put("type",type); //1公共接口，2用户，3部门
			        //System.out.println(pd_token);
			       
			        if((type.equals("1")||type.equals("3"))&&dept.equals("")){
			        	pd.put("dept",pd_token.getString("dept"));
			        }
			        //docService.save(pd);
			        pd.put("doaction", "0");
			        if(type.equals("2")){
			        	docService.save(pd);
			        }else{
			        	docService.saveAudit(pd);
			        }
		        	
			        object.put("success","true");
				    object.put("msg","保存成功,请到待审核列表进行审核");
				}else if(cmd.equals("DocEdit")) {
					String type=String.valueOf(json.get("type")==null?"":json.get("type"));
					String dept=String.valueOf(json.get("dept")==null?"":json.get("dept"));
					pd.put("createman",pd_token.getString("ID"));
			        pd.put("doctitle",json.get("doctitle"));
			        pd.put("doctype",json.get("doctype"));
			        pd.put("id",json.get("id"));
			        pd.put("ishot", json.getString("ishot"));
					pd.put("validate", json.getString("validate"));
			        pd.put("doctype_id",json.get("doctype_id"));//父类id
			      //  pd.put("sort",json.get("sort"));  //排序
			       // pd.put("doctype","1"); //状态 1、启用 0，禁用
			        pd.put("doccont",json.get("doccont"));
			        pd.put("type",type); //1公共接口，2用户，3部门
			        
			        if((type.equals("1")||type.equals("3"))&&dept.equals("")){
			        	pd.put("dept",pd_token.getString("dept"));
			        }else{
			        	if(!dept.equals("")){
			        		pd.put("dept",dept);
			        	}
			        }
			        
			        PageData pdDoc=docService.findById(pd);
			        if(pdDoc!=null){
			        	if(pdDoc.getString("type").equals("2")){ //个人
			        		docService.edit(pd);
			        	}else{
				        	pdDoc.put("doaction", "1");
				        	docService.saveAudit(pd);
				        	object.put("success","true");
						    object.put("msg","修改成功,请到待审核列表进行审核");
			        	}    
			        }else{
			        	object.put("success","true");
					    object.put("msg","修改失败，未找到该记录");
			        }
			        //docService.edit(pd);
			        //object.put("success","true");
			        //object.put("msg","修改成功");
				}else if(cmd.equals("DocDel")) {
					String ids=json.getString("id")==null?"":json.getString("id");
					String[] id=ids.split(",");
					boolean boo=false;
					pd.put("ids",ids);
					List<PageData> docList=docService.findByIds(pd);
			        if(docList.size()!=id.length){
			        	boo=true;
			        }
					
					if(!boo){
						for(int i=0;i<id.length;i++){
							pd.put("id",id[i]);
					        PageData pdDoc=docService.findById(pd);
					        if(pdDoc!=null){
					        	pdDoc.put("ZXID", pd_token.getString("ZXID"));
					        }
				        	if(pdDoc.getString("type").equals("2")){ //个人
				        		docService.delete(pdDoc);
				        		object.put("success","true");
							    object.put("msg","删除成功");
				        	}else{
					        	pd.put("uid",pdDoc.getString("uid"));
				        		pd.put("issh","0");
				        		PageData pdDocAudit=docService.findAuditByUid(pd);
				        		if(pdDocAudit!=null){
				        			object.put("success","false");
				    			    object.put("msg","该知识库还有一条未处理记录,请到待审核列表进行操作");
				        		}else{
				        			pdDoc.put("doaction", "2");
						        	docService.saveAudit(pdDoc);
						        	object.put("success","true");
								    object.put("msg","提交成功,请到待审核列表进行审核");
				        		}
				        	}
						}
						
					}else{
						object.put("success","false");
					    object.put("msg","删除失败,未查询到全部要删除的信息");
					}
					
					
			    	//String DATA_IDS =String.valueOf(json.get("id"));
					//String ArrayDATA_IDS[] = DATA_IDS.split(",");
					//docService.deleteAll(ArrayDATA_IDS);
		        	//docService.delete(pd);
			       
				}else if(cmd.equals("DocFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdDoc=docService.findById(pd);
			        if(pdDoc!=null&&pdDoc.get("id")!=null){
						object.put("success","true");
						pdDoc.put("createdate", String.valueOf(pdDoc.get("createdate")).substring(0, 19));
						if(pdDoc.get("shtime")!=null){
							pdDoc.put("shtime", String.valueOf(pdDoc.get("shtime")).substring(0, 19));
							
						}
						object.put("data",pdDoc);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("DocSh")) {
				
					String type=String.valueOf(json.get("type")==null?"":json.get("type"));
					String dept=String.valueOf(json.get("dept")==null?"":json.get("dept"));
					pd.put("shman",pd_token.getString("ID"));
			        pd.put("issh",json.get("issh")); //1、审核，2、审核不通过
			        pd.put("shyj",json.get("shyj"));//父类id
			      //  pd.put("sort",json.get("sort"));  //排序
			        pd.put("id",json.get("id")); 
			        pd.put("cxissh","0"); //1、审核，2、审核不通过
			        PageData pdDoc=docService.findAuditById(pd);
			        if(pdDoc!=null&&pdDoc.get("id")!=null){
			        	pdDoc.put("ZXID", pd_token.getString("ZXID"));
			        	if(pdDoc.get("doaction")!=null&&pdDoc.get("doaction").equals("0")&&json.get("issh").equals("1")){
			        		docService.save(pdDoc);
			        	}else if(pdDoc.get("doaction")!=null&&pdDoc.get("doaction").equals("1")&&json.get("issh").equals("1")){
			        		docService.edit(pdDoc);
			        	}else if(pdDoc.get("doaction")!=null&&pdDoc.get("doaction").equals("2")&&json.get("issh").equals("1")){
			        		docService.delete(pdDoc);
			        	}
			        	docService.sh(pd);
					    object.put("success","true");
						object.put("msg","审核成功");
			        }else{
			        	object.put("success","false");
						object.put("msg","审核失败，该记录已审核或不存在");
			        }
			        
			       
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
