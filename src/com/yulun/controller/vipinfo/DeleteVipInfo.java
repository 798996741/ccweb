package com.yulun.controller.vipinfo;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.AuditInfoManager;
import com.yulun.service.VipInfoManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeleteVipInfo implements CommonIntefate {
    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;
    @Resource(name = "auditInfoService")
    private AuditInfoManager auditInfoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
        PageData pd = new PageData();
        PageData pd2 = new PageData();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                pd.put("ZXID",pd_token.getString("ZXID"));
                pd.put("id", json.get("id"));
                pd.put("auditno", getauditno());
                pd.put("name", json.get("name"));
                pd.put("sex", json.get("sex"));
                pd.put("idcard", json.get("idcard"));
                pd.put("recepdep", json.get("recepdep"));
                pd.put("clevel", json.get("clevel"));
                pd.put("birthday", json.get("birthday"));
                pd.put("position", json.get("position"));
                pd.put("place", json.get("place"));
                pd.put("isimport", json.get("isimport"));
                pd.put("isusecard", json.get("isusecard"));
                pd.put("ortherinfo", json.get("ortherinfo"));
                pd.put("favorite", json.get("favorite"));
                pd.put("waiter", json.get("waiter"));
                pd.put("infoid", json.get("id"));
                pd.put("type", "删除");
                pd.put("ctype", json.get("ctype"));
                pd.put("reason", json.get("reason"));
                pd.put("result", json.get("result"));
                pd.put("czman", json.get("czman"));
                pd.put("cztime", getTime());
                auditInfoManager.insertAuditInfo(pd);
                vipInfoManager.updateVipresult(pd);
                data.put("success", "true");
                data.put("msg", "进入审核");
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

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    public String getauditno() throws Exception {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String day = dateFormat.format(date);
        PageData pd = new PageData();
        pd.put("keywords",day);
        PageData maxAuditno = auditInfoManager.getMaxAuditno(pd);
        String maxno="";
        if (maxAuditno!=null){
            String maxauditno = maxAuditno.getString("maxauditno");
            int i = Integer.parseInt(maxauditno.substring(11, maxauditno.length())) + 1;
            String code = i < 999 ? (i < 10 ? ("00" + i) : (i < 100 ? "0" + i : "" + i)) : "001";
            maxno=day+"-"+code;
        }else {
            maxno=day+"-"+"001";
        }
        return maxno;
    }
}
