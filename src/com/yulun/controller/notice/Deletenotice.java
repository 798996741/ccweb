package com.yulun.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class Deletenotice implements CommonIntefate {
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
                String[] ids = json.getString("id").split(",");
                noticeManager.deleteAll(ids);
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }



        } catch (Exception e) {
            data.put("success", "false");
            data.put("msg", "异常");
        }

        data.put("success", "true");
        data.put("msg", "删除成功");

        return data;
    }
}
