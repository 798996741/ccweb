package com.yulun.controller.worktask;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.WorkTaskManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class UpdateWorkTask implements CommonIntefate {
    @Resource(name = "workTaskService")
    private WorkTaskManager workTaskManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = data.getJSONObject("data");
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_stoken.getString("ZXID");
            if (pd_token != null) {
                pd.put("ZXID",pd_token.getString("ZXID"));
                pd.put("telnum", json.getString("telnum"));
                pd.put("id", json.getString("id"));
                pd.put("teltime", json.getString("teltime"));
                pd.put("message", json.getString("message"));
                pd.put("type", json.getString("type"));
                pd.put("result", json.getString("result"));
                pd.put("class", json.getString("class"));
                pd.put("userid", json.getString("userid"));
                pd.put("hwlsh", json.getString("hwlsh"));
                pd.put("uid", json.getString("uid"));
                pd.put("ucid", json.getString("ucid"));
                pd.put("czman", json.getString("czman"));
                pd.put("czdate", zxid);
                workTaskManager.updateWorkTask(pd);
                data.put("statusCode", 200);
                data.put("message", "成功");
                data.put("success", "true");
            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            data.put("statusCode", 400);
            data.put("message", "失败");
        }
        return data;
    }
}
