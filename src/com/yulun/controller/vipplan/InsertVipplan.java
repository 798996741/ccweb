package com.yulun.controller.vipplan;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.VipPlanManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class InsertVipplan implements CommonIntefate {
    @Resource(name = "vipPlanService")
    private VipPlanManager vipPlanManager;
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
                pd.put("ZXID",pd_token.getString("ZXID"));
                String uuid32 = getUUID32();
                pd.put("id", uuid32);
                pd.put("no", getplanno());
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
                pd.put("servtype", "2");
                vipPlanManager.insertVipplan(pd);
                data.put("success", "true");
                data.put("id", uuid32);
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            data.put("success", "false");
        }

        return data;
    }

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    public String getplanno() throws Exception {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String day = dateFormat.format(date);
        PageData pd = new PageData();
        pd.put("keywords",day);
        PageData maxAuditno = vipPlanManager.getMaxno(pd);
        String maxno="";
        if (maxAuditno!=null){
            String maxauditno = maxAuditno.getString("maxno");
            int i = Integer.parseInt(maxauditno.substring(8, maxauditno.length())) + 1;
            String code = i < 999 ? (i < 10 ? ("00" + i) : (i < 100 ? "0" + i : "" + i)) : "001";
            maxno=day+code;
        }else {
            maxno=day+"001";
        }
        System.out.println(maxno);
        return maxno;
    }
}
