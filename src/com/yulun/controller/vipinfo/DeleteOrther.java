package com.yulun.controller.vipinfo;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.VipInfoManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class DeleteOrther implements CommonIntefate {
    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");

        try {
            PageData pd = new PageData();
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                pd.put("id", json.get("id"));
                pd.put("ZXID",pd_token.getString("ZXID"));
                vipInfoManager.deleteOrther(pd);
                data.put("success", "true");
                data.put("msg", "删除成功");
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            data.put("success", "false");
            data.put("msg", "异常");
        }


        return data;
    }
}
