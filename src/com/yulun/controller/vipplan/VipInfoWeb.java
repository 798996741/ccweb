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

public class VipInfoWeb implements CommonIntefate {
    @Resource(name = "vipPlanService")
    private VipPlanManager vipPlanManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        try {
            PageData pd = new PageData();
            Page page = new Page();
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData pd_stoken = new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                if (cmd.equals("FindVipPlanAll")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", json.get("starttime"));
                    pd.put("endtime", json.get("endtime"));
                    pd.put("linecate", json.get("linecate"));
                    pd.put("keywords", json.get("keywords"));
                    page.setPd(pd);
                    List<PageData> list = vipPlanManager.findVipPlanAlllistPage(page);
                    data.put("data", list);
                    data.put("pageId",pageIndex);
                    data.put("pageCount",page.getTotalPage());
                    data.put("recordCount",page.getTotalResult());
                    data.put("success", "true");
                }else if (cmd.equals("FindVipplanByid")){
                    pd.put("vipplanid", json.getString("vipplanid"));
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    page.setPd(pd);
                    List<PageData> list = vipPlanManager.findOrderinfoByVipplanidlistPage(page);
                    data.put("data", list);
                    data.put("pageId",pageIndex);
                    data.put("pageCount",page.getTotalPage());
                    data.put("recordCount",page.getTotalResult());
                    data.put("success", "true");
                }else if (cmd.equals("InsertOrtherInfo")){
                    pd.put("oordername", json.get("oordername"));
                    pd.put("oidcard", json.get("oidcard"));
                    pd.put("ounit", json.get("ounit"));
                    pd.put("oposition", json.get("oposition"));
                    pd.put("oisimport", json.get("oisimport"));
                    pd.put("vipplanid", json.get("vipplanid"));
                    vipPlanManager.insertOrtherInfo(pd);
                    data.put("success", "true");
                }else if (cmd.equals("InsertVipplan")){

                }else if (cmd.equals("UpdateOrtherInfo")){

                }else if (cmd.equals("UpdateVipplan")){

                }else if (cmd.equals("DeleteVipplan")){

                }else if (cmd.equals("DeleteOrtherInfo")){

                }

            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }
        } catch (Exception e) {
            data.put("success", "false");
            data.put("msg", "操作异常");
        }
        return data;

    }
}
