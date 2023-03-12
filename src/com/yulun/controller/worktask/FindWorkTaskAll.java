package com.yulun.controller.worktask;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.WorkTaskManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindWorkTaskAll implements CommonIntefate {

    @Resource(name = "workTaskService")
    private WorkTaskManager workTaskManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        Page page = new Page();

        try {
            PageData pd_stoken = new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                Integer pageIndex = json.getInteger("pageIndex");
                page.setCurrentPage(pageIndex);
                Integer pageSize = json.getInteger("pageSize");
                page.setShowCount(pageSize);
                pd.put("starttime", json.get("starttime"));
                pd.put("endtime", json.get("endtime"));
                pd.put("class", json.get("class"));
                pd.put("userid", json.get("userid"));
                pd.put("telnum", json.get("telnum"));
                pd.put("type", json.get("type"));
                page.setPd(pd);
                List<PageData> list = workTaskManager.findAlllistPage(page);
                System.out.println(list);
                data.put("data", list);
                data.put("pageId",pageIndex);
                data.put("pageCount",page.getTotalPage());
                data.put("recordCount",page.getTotalResult());
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
