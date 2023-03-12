package com.yulun.controller.vipinfo;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.VipInfoManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindVipInfoAll implements CommonIntefate {
    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
        PageData pd = new PageData();
        Page page = new Page();

        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", json.get("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            Integer pageIndex = Integer.parseInt(json.get("pageIndex").toString());
            page.setCurrentPage(pageIndex);
            Integer pageSize = Integer.parseInt(json.get("pageSize").toString());
            page.setShowCount(pageSize);
            pd.put("name", json.get("name"));
            pd.put("keywords", json.get("keywords"));
            pd.put("tel", json.get("tel"));
            pd.put("result", json.get("result"));
            page.setPd(pd);
            List<PageData> list = vipInfoManager.findAlllistPage(page);
            data.put("data", list);
            data.put("pageId",page.getCurrentPage());//当前页码
            data.put("pageCount",page.getShowCount());//当前页的条数
            data.put("recordCount",page.getTotalResult());//总数
            data.put("success", "true");
        } else {
            data.put("success", "false");
            data.put("msg", "登录超时，请重新登录");
        }
        try {


        } catch (Exception e) {
            data.put("success", "false");
        }

        return data;
    }
}
