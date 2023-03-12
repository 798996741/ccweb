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

public class FindCommonAll implements CommonIntefate {
    @Resource(name = "commnoService")
    private CommnoManager commnoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        Page page = new Page();
        try {
            JSONObject json = data.getJSONObject("data");
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                Integer pageIndex = json.getInteger("pageIndex");
                page.setCurrentPage(pageIndex);
                Integer pageSize = json.getInteger("pageSize");
                page.setShowCount(pageSize);
                page.setPd(pd);
                pd.put("name", json.get("name"));
                pd.put("phone", json.get("phone"));
                pd.put("idcard", json.get("idcard"));
                pd.put("cardtype", json.get("cardtype"));
                pd.put("keywords", json.get("keywords"));
                List<PageData> list = commnoManager.findAlllistPage(page);
                data.put("data", list);
                data.put("pageId", pageIndex);
                data.put("pageCount", page.getTotalPage());
                data.put("recordCount", page.getTotalResult());
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
