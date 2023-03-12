package com.yulun.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class InsertNotice implements CommonIntefate {
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;
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
                pd.put("title", json.get("title"));
                pd.put("starttime", json.get("starttime"));
                pd.put("endtime", json.get("endtime"));
                pd.put("content", json.get("content"));
                pd.put("setname", json.get("setname"));

                String uuid32 = getUUID32();
                pd.put("id", uuid32);
                StringBuffer buffer = new StringBuffer();


                if (json.get("userid") != null && !"".equals(json.get("userid"))) {
                    String[] userids = json.get("userid").toString().split(",");

                    for (String userid : userids) {
                        PageData pd3 = new PageData();
                        pd3.put("id", userid);
                        PageData username = noticeManager.getUsername(pd3);
                        buffer.append(username.getString("zxxm")).append(",");
                        PageData pd2 = new PageData();
                        pd2.put("userid", userid);
                        pd2.put("readname", username.getString("zxxm"));
                        pd2.put("noticeid", uuid32);
                        pd2.put("type", 1);
                        noticeManager.insertNoticeRead(pd2);
                    }
                }
                System.out.println(buffer);
                if(!buffer.toString().equals("") && buffer.toString()!=null){
                    pd.put("username",buffer.deleteCharAt(buffer.length() - 1).toString() );
                }
                pd.put("userid", json.get("userid"));
                noticeManager.insertNotice(pd);
                data.put("success", "true");
                data.put("msg", "新增成功");
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

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
}
