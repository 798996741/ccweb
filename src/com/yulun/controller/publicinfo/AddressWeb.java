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
import com.yulun.service.AddressManager;
import com.yulun.service.AddressManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：通讯录
 * 创建人：351412933
 * 创建时间：2019-12-27
 */
public class AddressWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="addressService")
	private AddressManager addressService;
	
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
				if(cmd.equals("AddressAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("name", json.getString("name")); 
					pd.put("remark", json.getString("remark")); 
					pd.put("tel", json.getString("tel")); 
					pd.put("zxid", json.getString("zxid")); 
					pd.put("issh", json.getString("issh")); 
					pd.put("departmentid", json.getString("departmentid")); 
					pd.put("isdel", "0"); 
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
		            list =addressService.listPage(page);
		           // System.out.println(list);
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
				}else if(cmd.equals("AddressSh")) {
					pd.put("shman",pd_token.getString("ID"));
			        pd.put("issh",json.get("issh"));
			        pd.put("shremark",json.get("shremark"));
			        pd.put("id",json.get("id"));
			        pd.put("type",json.get("type"));
			        
			        PageData pdAddress=addressService.findById(pd);
			        if(pdAddress!=null&&pdAddress.get("id")!=null){
			        	addressService.sh(pd);
					    if(json.get("issh")!=null&&json.getString("issh").equals("1")){
					    	pd.put("czman",pd_token.getString("ID"));
					    	pd.put("type",json.get("type"));
					         pd.put("tel1",json.get("tel1"));
					         pd.put("name",json.get("name"));
					         pd.put("tel2",json.get("tel2")); 
					         pd.put("issh",json.get("issh"));
					         pd.put("remark", json.getString("remark")); 
					         pd.put("departmentid",json.get("departmentid")); 
					         pd.put("id",json.get("id"));
					         addressService.edit(pd);
					    }    
				        object.put("success","true");
					    object.put("msg","审核成功");
					}else{
						object.put("success","false");
				        object.put("msg","要审核的数据不存在");
					}
			        
			       
				}else if(cmd.equals("AddressDel")) {
					pd.put("remark",json.get("remark"));
				    pd.put("id",json.get("id"));
				    pd.put("type",json.get("type"));
				    pd.put("isdel","1");
					addressService.deleteAll(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
				}else if(cmd.equals("AddressAdd")) {
				
					pd.put("czman",pd_token.getString("ID"));
			        pd.put("type",json.get("type"));
			        pd.put("tel1",json.get("tel1"));
			        pd.put("name",json.get("name"));
			        pd.put("tel2",json.get("tel2"));
			        pd.put("remark", json.getString("remark")); 
			        pd.put("issh","0");
			        pd.put("addressno",this.getaddressno());
			        pd.put("departmentid",json.get("departmentid")); 
			        addressService.save(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("AddressEdit")) {
					pd.put("czman",pd_token.getString("ID"));
			        pd.put("type",json.get("type"));
			        pd.put("tel1",json.get("tel1"));
			        pd.put("name",json.get("name"));
			        pd.put("tel2",json.get("tel2")); 
			        pd.put("issh",json.get("issh"));
			        pd.put("remark", json.getString("remark")); 
			        pd.put("departmentid",json.get("departmentid")); 
			        pd.put("id",json.get("id"));
			        
			        addressService.edit(pd);
			        object.put("success","true");
				    object.put("msg","修改成功");
				}else if(cmd.equals("AddressFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdAddress=addressService.findById(pd);
			        if(pdAddress!=null&&pdAddress.get("id")!=null){
						object.put("success","true");
						object.put("data",pdAddress);
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

    public String getaddressno() throws Exception {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String day = dateFormat.format(date);
        PageData pd = new PageData();
        pd.put("keywords",day);
        PageData maxAddressno = addressService.getMaxAddressno(pd);
        String maxno="";
        if (maxAddressno!=null){
            String maxaddressno = maxAddressno.getString("maxaddressno");
            int i = Integer.parseInt(maxaddressno.substring(11, maxaddressno.length())) + 1;
            String code = i < 999 ? (i < 10 ? ("00" + i) : (i < 100 ? "0" + i : "" + i)) : "001";
            maxno=day+"-"+code;
        }else {
            maxno=day+"-"+"001";
        }
        return maxno;
    }
	
}

