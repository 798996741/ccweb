package com.yulun.controller.vipplan;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.VipPlanManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindVipPlanAll implements CommonIntefate {
    @Resource(name = "vipPlanService")
    private VipPlanManager vipPlanManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
        JSONObject object=new JSONObject();
        try {
            PageData pd = new PageData();
            Page page = new Page();
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                Integer pageIndex = json.getInteger("pageIndex");
                page.setCurrentPage(pageIndex);
                Integer pageSize = json.getInteger("pageSize");
                page.setShowCount(pageSize);
                page.setPd(pd);
                pd.put("starttime", json.get("starttime"));
                pd.put("endtime", json.get("endtime"));
                pd.put("linecate", json.get("linecate"));
                pd.put("servtype", json.get("servtype"));
                pd.put("keywords", json.get("keywords"));
                pd.put("name", json.get("name"));

                List<PageData> list = vipPlanManager.findVipPlanAlllistPage(page);
                System.out.println(pageIndex);
                System.out.println(page.getTotalPage());
                System.out.println(page.getTotalResult());
                System.out.println(page.getCurrentResult());
                object.put("data", list);
                object.put("pageId",pageIndex);
                object.put("pageCount",page.getTotalPage());
                object.put("recordCount",page.getTotalResult());
                object.put("success", "true");
            } else {
                object.put("success", "false");
                object.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            object.put("success", "false");
        }


        return object;
    }
}
