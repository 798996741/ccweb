package com.yulun.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FindNoticeAll implements CommonIntefate {
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");

        try {
            PageData pd = new PageData();
            Page page = new Page();
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                Integer pageIndex = json.getInteger("pageIndex");
                page.setCurrentPage(pageIndex);
                Integer pageSize = json.getInteger("pageSize");
                page.setShowCount(pageSize);

                PageData pageData = new PageData();
                pageData.put("id",json.getString("userid"));
                List<PageData> except = noticeManager.getExcept(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);

                pd.put("keywords", json.getString("keywords"));
                pd.put("userid", json.getString("userid"));
                pd.put("userids", users);
                page.setPd(pd);
                List<PageData> list = noticeManager.findAlllistPage(page);
                data.put("data", list);
                data.put("pageId",pageIndex);
                data.put("pageCount",page.getTotalPage());
                data.put("recordCount",page.getTotalResult());
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            data.put("success", "false");
        }
        data.put("success", "true");
        return data;

    }

}
