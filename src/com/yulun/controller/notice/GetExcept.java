package com.yulun.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetExcept implements CommonIntefate {
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = data.getJSONObject("data");
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", json.get("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        JSONObject object = new JSONObject();
        if (pd_token != null) {
            pd.put("id",json.getString("id"));
            List<PageData> except = noticeManager.getExcept(pd);
            object.put("data",except);
            object.put("success","true");
        }else {
            data.put("success", "false");
            data.put("msg", "登录超时，请重新登录");
        }

        return object;
    }
}
