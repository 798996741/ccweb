package com.yulun.controller.vipplan;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.VipPlanManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class UpdateServtype implements CommonIntefate {

    @Resource(name = "vipPlanService")
    private VipPlanManager vipPlanManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                pd.put("ZXID",pd_token.getString("ZXID"));
                pd.put("id", json.get("id"));
                pd.put("servtype", json.get("servtype"));
                vipPlanManager.updateServtype(pd);
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
