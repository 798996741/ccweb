package com.yulun.controller.worktask;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.WorkTaskManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class FindWorkTaskByid implements CommonIntefate {
    @Resource(name = "workTaskService")
    private WorkTaskManager workTaskManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = data.getJSONObject("data");
        pd.put("id", json.getString("id"));
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", json.get("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData workTaskByid = workTaskManager.findWorkTaskByid(pd);
            data.put("data", workTaskByid);
            data.put("success", "true");
        } else {
            data.put("success", "false");
            data.put("msg", "登录超时，请重新登录");
        }

        return data;
    }
}
