package com.yulun.controller.auditinfo;

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

public class UpdateAuditInfo implements CommonIntefate {
    @Resource(name = "auditInfoService")
    private AuditInfoManager auditInfoManager;
    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
//        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);

            if (pd_token != null) {
                PageData pd = new PageData();
                pd.put("ZXID",pd_token.getString("ZXID"));
                pd.put("id", json.get("id"));
                pd.put("name", json.get("name"));
                pd.put("sex", json.get("sex"));
                pd.put("idcard", json.get("idcard"));
                pd.put("recepdep", json.get("recepdep"));
                pd.put("clevel", json.get("clevel"));
                pd.put("birthday", json.get("birthday"));
                pd.put("position", json.get("position"));
                pd.put("place", json.get("place"));
                pd.put("waiter", json.get("waiter"));
                pd.put("isimport", json.get("isimport"));
                pd.put("isusecard", json.get("isusecard"));
                pd.put("ortherinfo", json.get("ortherinfo"));
                pd.put("favorite", json.get("favorite"));
                pd.put("auditor", json.get("auditor"));
                pd.put("audittime", getTime());
                pd.put("cztime", json.get("cztime"));
                pd.put("czman", json.get("czman"));
                pd.put("type", json.get("type"));
                pd.put("ctype", json.get("ctype"));
                pd.put("result", json.get("result"));
                pd.put("infoid", json.get("infoid"));
                pd.put("auditno", json.get("auditno"));
                if (json.get("type").equals("删除") && json.get("result").equals("1")) {
                    System.out.println("执行删除");
                    auditInfoManager.updateResult(pd);
                    pd.put("id", json.get("infoid"));
                    vipInfoManager.deleteVipInfo(pd);//审核通过删除要客信息
                    data.put("msg", "要客删除成功");
                    pd.put("vipid", json.get("infoid").toString());
                    vipInfoManager.deleteOrtherAll(pd);
                } else if (json.get("type").equals("修改") && json.get("result").equals("1")) {
                    System.out.println("执行修改");
                    auditInfoManager.updateAuditInfo(pd);
                    pd.put("id", json.get("infoid"));
                    vipInfoManager.updateVipInfo(pd);//审核通过修改要客信息
                    data.put("msg", "要客修改成功");
                } else if (json.get("type").equals("新增") && json.get("result").equals("1")) {
                    System.out.println("执行修改");
                    auditInfoManager.updateAuditInfo(pd);
                    pd.put("id", json.get("infoid"));
                    vipInfoManager.updateVipInfo(pd);//审核通过修改要客信息状态
                    data.put("msg", "要客修改成功");
                } else if (json.get("type").equals("新增") && json.get("result").equals("2")){
                    System.out.println("执行删除");
                    auditInfoManager.updateResult(pd);
                    pd.put("id", json.get("infoid"));
                    vipInfoManager.deleteVipInfo(pd);//新增审核不通过，删除要客信息
                    data.put("msg", "要客删除成功");
                    pd.put("vipid", json.get("id").toString());
                    vipInfoManager.deleteOrtherAll(pd);
                }else {
                    System.out.println("执行审核不通过");
                    auditInfoManager.updateResult(pd);
                    pd.put("id", json.get("infoid"));
                    vipInfoManager.updateVipresult(pd);//审核不通过修改要客信息状态
                    data.put("msg", "要客修改成功");
                }
                data.put("success", "true");
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }


//        } catch (Exception e) {
//            data.put("success", "false");
//            data.put("msg", "异常");
//        }


        return data;
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
