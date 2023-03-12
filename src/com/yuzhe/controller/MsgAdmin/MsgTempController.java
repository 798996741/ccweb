package com.yuzhe.controller.MsgAdmin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.MsgTempManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MsgTempController implements CommonIntefate {

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "yuzhemsgtempservice")
    private MsgTempManager msgTempService;

    @Resource(name="userService")
    private UserManager userService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsondata = data.getJSONObject("data");
        JSONObject result = new JSONObject();
        System.out.println("++++++++进来了");
        try {
            PageData pd_stoken=new PageData();
            PageData pdall=new PageData();
            pd_stoken.put("TOKENID",jsondata.get("tokenid"));
            PageData pdtoken=zxlbService.findByTokenId(pd_stoken);
            String username=(jsondata.getString("username")==null?"":jsondata.getString("username"));
            if(!"".equals(username)){
                pd_stoken.put("USERNAME", username);
                pdtoken=userService.findByUsername(pd_stoken);
                if(pdtoken!=null){
                    pdtoken.put("dept", pdtoken.getString("DWBM"));
                    pdtoken.put("ZXYH", username);
                }
                pdall.put("ZXID", username);
                pdall.put("systype", "1");
            }else{
                if(pdtoken!=null){
                    pdall.put("ZXID", pdtoken.getString("ZXID"));
                }
            }
            if(pdtoken!=null){
                String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
                if("AddMsgTemp".equals(cmd)){
                    pdall.put("tempname",jsondata.get("tempname"));
                    pdall.put("content",jsondata.get("content"));
                    pdall.put("czman",pdtoken.get("ZXID"));
                    pdall.put("czdate",new Date());
                    msgTempService.AddMsgTemp(pdall);
                    result.put("success","true");
                }else if("MsgTempListPage".equals(cmd)){
                    Page page=new Page();
                    Integer pageIndex=jsondata.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize=jsondata.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pdall.put("keywords",jsondata.getString("keywords"));
                    page.setPd(pdall);
                    List<PageData> pdlist=msgTempService.MsgTempListPage(page);
                    JSONArray ja=new JSONArray();
                    for (PageData pd:pdlist){
                        JSONObject jb=new JSONObject();
                        jb.put("id",pd.get("id"));
                        jb.put("content",pd.get("content")==null?"":pd.get("content"));
                        jb.put("tempname",pd.get("tempname")==null?"":pd.get("tempname"));
                        jb.put("czman",pd.get("czman")==null?"":pd.get("czman"));
                        jb.put("czdate",pd.get("czdate")==null?"":simpleDateFormat.format(pd.get("czdate")));
                        ja.add(jb);
                    }
                    result.put("data",ja);
                    result.put("success","true");
                    result.put("pageId",pageIndex);
                    result.put("pageCount",page.getTotalPage());
                    result.put("recordCount",page.getTotalResult());
                }else if("delMsgTemp".equals(cmd)){
                    pdall.put("id",jsondata.get("id"));
                    msgTempService.delMsgTemp(pdall);
                    result.put("success","true");
                }else if("FindMsgById".equals(cmd)){
                    JSONObject jb=new JSONObject();
                    pdall.put("id",jsondata.get("id"));
                    pdall=msgTempService.FindMsgById(pdall);
                    jb.put("id",pdall.get("id")==null?"":pdall.get("id"));
                    jb.put("tempname",pdall.get("tempname")==null?"":pdall.get("tempname"));
                    jb.put("czman",pdall.get("czman")==null?"":pdall.get("czman"));
                    jb.put("content",pdall.get("content")==null?"":pdall.get("content"));
                    jb.put("czdate",pdall.get("czdate")==null?"":simpleDateFormat.format(pdall.get("czdate")));
                    result.put("data",jb);
                    result.put("success","true");
                }else if("UpdMsgById".equals(cmd)){
                    pdall.put("id",jsondata.get("id"));
                    pdall.put("tempname",jsondata.get("tempname"));
                    pdall.put("content",jsondata.get("content"));
                    pdall.put("czman",pdtoken.get("ZXID"));
                    pdall.put("czdate",new Date());
                    msgTempService.UpdMsgById(pdall);
                    result.put("success","true");
                }else if("SendMsg".equals(cmd)){
                    String phone=jsondata.getString("phone");
                    String content=jsondata.getString("content");
                    pdall.put("phone",phone);
                    pdall.put("content",content);
                    pdall.put("temp",jsondata.get("temp"));
                    pdall.put("state",0);
                    pdall.put("sendman",pdtoken.get("ZXID"));
                    pdall.put("sendtime",new Date());
                    pdall.put("czman",pdtoken.get("ZXID"));
                    pdall.put("czdate",new Date());
                    msgTempService.SendMsg(pdall);
					/*
					 * SendMsgUtil sm=new SendMsgUtil();
					 * System.out.println("Msg====="+pdall.get("id"));
					 * sm.sendMsg(msgTempService,phone,content,pdall.get("id"));
					 */
                    result.put("success","true");
                }else if("MsgTempList".equals(cmd)){
                    List<PageData> listpd=msgTempService.MsgTempList();
                    JSONArray ja=new JSONArray();
                    for (PageData pd:listpd){
                        JSONObject jb=new JSONObject();
                        jb.put("id",pd.get("id"));
                        jb.put("content",pd.get("content")==null?"":pd.get("content"));
                        jb.put("tempname",pd.get("tempname")==null?"":pd.get("tempname"));
                        jb.put("czman",pd.get("czman")==null?"":pd.get("czman"));
                        jb.put("czdate",pd.get("czdate")==null?"":simpleDateFormat.format(pd.get("czdate")));
                        ja.add(jb);
                    }
                    result.put("data",ja);
                    result.put("success","true");
                }else if("MsgLogListPage".equals(cmd)){
                    Page page=new Page();
                    page.setCurrentPage(jsondata.getInteger("pageIndex"));
                    page.setShowCount(jsondata.getInteger("pageSize"));
                    pdall.put("StartTime",jsondata.getString("StartTime"));
                    pdall.put("EndTime",jsondata.getString("EndTime"));
                    pdall.put("phone",jsondata.getString("phone"));
                    pdall.put("keywords",jsondata.getString("keywords"));
                    page.setPd(pdall);
                    List<PageData> listpd=msgTempService.MsgLogListPage(page);
                    JSONArray ja=new JSONArray();
                    for (PageData pd:listpd){
                        JSONObject jb=new JSONObject();
                        jb.put("id",pd.get("id"));
                        jb.put("sendman",pd.get("zxxm")==null?"":pd.get("zxxm"));
                        jb.put("tempname",pd.get("tempname")==null?"":pd.get("tempname"));
                        jb.put("phone",pd.get("phone")==null?"":pd.get("phone"));
                        jb.put("sendtime",pd.get("sendtime")==null?"":simpleDateFormat.format(pd.get("sendtime")));
                        jb.put("state","1".equals(pd.get("state"))?"发送成功":"发送失败");
                        jb.put("content",pd.get("content")==null?"":pd.get("content"));
                        ja.add(jb);
                    }
                    result.put("data",ja);
                    result.put("success","true");
                    result.put("pageId",jsondata.getInteger("pageIndex"));
                    result.put("pageCount",page.getTotalPage());
                    result.put("recordCount",page.getTotalResult());
                }else if("GetMsgLogById".equals(cmd)){
                    JSONObject jb=new JSONObject();
                    pdall.put("id",jsondata.get("id"));
                    pdall=msgTempService.GetMsgLogById(pdall);
                    jb.put("id",pdall.get("id"));
                    jb.put("sendman",pdall.get("zxxm")==null?"":pdall.get("zxxm"));
                    jb.put("tempname",pdall.get("tempname")==null?"":pdall.get("tempname"));
                    jb.put("phone",pdall.get("phone")==null?"":pdall.get("phone"));
                    jb.put("sendtime",pdall.get("sendtime")==null?"":simpleDateFormat.format(pdall.get("sendtime")));
                    jb.put("state","1".equals(pdall.get("state"))?"发送成功":"发送失败");
                    jb.put("content",pdall.get("content")==null?"":pdall.get("content"));
                    result.put("data",jb);
                    result.put("success","true");
                }else{
                    result.put("success","false");
                    result.put("msg","访问异常");
                }

            }else{
                result.put("success","false");
                result.put("msg","登录超时，请重新登录");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("success","false");
            result.put("msg","操作异常");
        }
        return result;
    }
}
