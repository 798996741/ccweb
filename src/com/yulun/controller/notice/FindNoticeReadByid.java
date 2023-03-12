package com.yulun.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindNoticeReadByid implements CommonIntefate {
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {

        JSONObject json = data.getJSONObject("data");
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);

            Integer pageIndex = null;
            Integer pageSize = null;
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                pageIndex = json.getInteger("pageIndex");
                page.setCurrentPage(pageIndex);
                pageSize = json.getInteger("pageSize");
                page.setShowCount(pageSize);
                System.out.println(json.getString("noticeid"));
                pd.put("noticeid", json.getString("noticeid"));
                page.setPd(pd);
                List<PageData> list = noticeManager.findNoticeReadByidlistPage(page);

                data.put("data", list);
//                data.put("pageId", page.getCurrentPage());//当前页码
////                data.put("pageCount", page.getShowCount());//当前页的条数
////                data.put("recordCount", page.getTotalResult());//总数
                data.put("pageId",pageIndex);
                data.put("pageCount",page.getTotalPage());
                data.put("recordCount",page.getTotalResult());
                data.put("success", "true");
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }
        } catch (Exception e) {
            data.put("success", "false");
        }

        return data;
    }
}
