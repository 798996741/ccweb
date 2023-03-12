package com.yulun.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoticeWeb implements CommonIntefate {
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object=new JSONObject();
        try{
            PageData pd = new PageData();
            Page page = new Page();
            String cmd = data.getString("cmd")==null?"":data.getString("cmd");
            PageData pd_stoken=new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token=zxlbService.findByTokenId(pd_stoken);
            if(pd_token!=null){
                if(cmd.equals("noticeByUserId")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    page.setPd(pd);
                    pd.put("userid",json.getString("userid"));
                    List<PageData> list = noticeManager.noticeByUserIdlistPage(page);
                    System.out.println(list);
                    object.put("success","true");
                    object.put("data",list);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if(cmd.equals("changeReadById")){
                    pd.put("id",json.getString("id"));
                    pd.put("type","0");
                    pd.put("time",getTime());
                    noticeManager.changeReadById(pd);
                    object.put("success", "true");
                    object.put("msg", "修改成功");
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
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
