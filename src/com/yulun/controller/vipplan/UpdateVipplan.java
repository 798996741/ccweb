package com.yulun.controller.vipplan;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.VipPlanManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class UpdateVipplan implements CommonIntefate {
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
                pd.put("no", json.get("no"));
                pd.put("date", json.get("date"));
                pd.put("type", json.get("type"));
                pd.put("name", json.get("name"));
                pd.put("position", json.get("position"));
                pd.put("isimport", json.get("isimport"));
                pd.put("recman", json.get("recman"));
                pd.put("recchara", json.get("recchara"));
                pd.put("cardno", json.get("cardno"));
                pd.put("money", json.get("money"));
                pd.put("contact", json.get("contact"));
                pd.put("phone", json.get("phone"));
                pd.put("ordername", json.get("ordername"));
                pd.put("ordertel", json.get("ordertel"));
                pd.put("meetnum", json.get("meetnum"));
                pd.put("follownum", json.get("follownum"));
                pd.put("guestnum", json.get("guestnum"));
                pd.put("paymet", json.get("paymet"));
                pd.put("boxtype", json.get("boxtype"));
                pd.put("isseat", json.get("isseat"));
                pd.put("iseat", json.get("iseat"));
                pd.put("eatnum", json.get("eatnum"));
                pd.put("viproom", json.get("viproom"));
                pd.put("carno", json.get("carno"));
                pd.put("lineno", json.get("lineno"));
                pd.put("linecate", json.get("linecate"));
                pd.put("line", json.get("line"));
                pd.put("isvipcar", json.get("isvipcar"));
                pd.put("vipcarin", json.get("vipcarin"));
                pd.put("need", json.get("need"));
                pd.put("waiter", json.get("waiter"));
                pd.put("consumemon", json.get("consumemon"));
                pd.put("reseat", json.get("reseat"));
                pd.put("vipcar", json.get("vipcar"));
                pd.put("userid", json.get("userid"));
                pd.put("username", json.get("username"));
                vipPlanManager.updateVipplan(pd);
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
