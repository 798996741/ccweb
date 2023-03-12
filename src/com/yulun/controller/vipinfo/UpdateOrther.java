package com.yulun.controller.vipinfo;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.VipInfoManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class UpdateOrther implements CommonIntefate {
    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
        PageData pd = new PageData();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                pd.put("ZXID",pd_token.getString("ZXID"));
                pd.put("id", json.get("id"));
                pd.put("oname", json.get("oname"));
                pd.put("otel", json.get("otel"));
                pd.put("vipid", json.get("vipid"));
                vipInfoManager.updateOrther(pd);
                data.put("success", "true");
                data.put("msg", "修改成功");
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            data.put("success", "false");
            data.put("msg", "修改失败");
        }


        return data;
    }
}
