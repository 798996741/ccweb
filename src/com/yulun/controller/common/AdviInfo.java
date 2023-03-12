package com.yulun.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CommnoManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdviInfo implements CommonIntefate {
    @Resource(name = "commnoService")
    private CommnoManager commnoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = data.getJSONObject("data");
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", json.get("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            Page page = new Page();
            Integer pageIndex;
            Integer pageSize;
            pd.put("phone", json.get("phone"));

            pd.put("starttime", json.get("starttime"));
            pd.put("endtime", json.get("endtime"));

            pageIndex = json.getInteger("pageIndex");
            page.setCurrentPage(pageIndex);
            pageSize = json.getInteger("pageSize");
            page.setShowCount(pageSize);
            page.setPd(pd);
            List<PageData> list = commnoManager.adviInfolistPage(page);
            data.put("data", list);
            data.put("pageId", pageIndex);
            data.put("pageCount", page.getTotalPage());
            data.put("recordCount", page.getTotalResult());
            data.put("success", "true");
        } else {
            data.put("success", "false");
            data.put("msg", "登录超时，请重新登录");
        }

        return data;
    }
}
