package com.yuzhe.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author LEN0V0
 */
public class MyNoticeWeb implements CommonIntefate {

    @Resource(name = "lostAndFoundNoticeService")
    private NoticeManager lostAndFoundNoticeService;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        PageData param = new PageData();
        try{
            JSONObject jsondata = data.getJSONObject("data");
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData stoken = new PageData();
            stoken.put("TOKENID", jsondata.get("tokenid"));
            PageData pdtoken = zxlbService.findByTokenId(stoken);
            param.putAll(jsondata);
            if(pdtoken!=null){
                if ("MyNoticeList".equals(cmd)){
                    Page page = new Page();
                    Integer pageIndex = jsondata.getInteger("pageIndex");
                    Integer pageSize = jsondata.getInteger("pageSize");
                    page.setCurrentPage(pageIndex);
                    page.setShowCount(pageSize);
                    param.put("zxid",pdtoken.get("ZXID"));
                    page.setPd(param);
                    List<PageData> listpd =lostAndFoundNoticeService.myNoticeList(page);
                    if (listpd.size() > 0){
                        result.put("data", listpd);
                        result.put("pageId", pageIndex);
                        result.put("pageCount", page.getTotalPage());
                        result.put("recordCount", page.getTotalResult());
                        result.put("success", "true");
                    }else {
                        result.put("success", "false");
                        result.put("msg", "暂无数据");
                    }
                }else if("readNotice".equals(cmd)){
                    param.put("readtime",new Date());
                    lostAndFoundNoticeService.readNotice(param);
                    result.put("success", "true");
                    result.put("msg","修改成功");
                }else if("readNoticeList".equals(cmd)){
                    Page page = new Page();
                    Integer pageIndex = jsondata.getInteger("pageIndex");
                    Integer pageSize = jsondata.getInteger("pageSize");
                    page.setCurrentPage(pageIndex);
                    page.setShowCount(pageSize);
                    param.put("zxid",pdtoken.get("ZXID"));
                    page.setPd(param);
                    List<PageData> listpd =lostAndFoundNoticeService.readNoticeList(page);
                    if (listpd.size() > 0){
                        result.put("data", listpd);
                        result.put("pageId", pageIndex);
                        result.put("pageCount", page.getTotalPage());
                        result.put("recordCount", page.getTotalResult());
                        result.put("success", "true");
                    }else {
                        result.put("success", "false");
                        result.put("msg", "暂无数据");
                    }
                }else if("findMyNoticeById".equals(cmd)){
                    param.put("zxid",pdtoken.get("ZXID"));
                    PageData pd=lostAndFoundNoticeService.findMyNoticeById(param);
                    result.put("data", pd);
                    result.put("success", "true");
                }else{
                    result.put("success","false");
                    result.put("msg","访问异常");
                }
            }else {
                result.put("success", "false");
                result.put("msg", "登录超时，请重新登录");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("success", "false");
            result.put("msg", "操作异常");
        }
        return result;
    }
}
