package com.yulun.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CommnoManager;
import com.yulun.service.ServerLogManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UpdateCommon implements CommonIntefate {
    @Resource(name = "commnoService")
    private CommnoManager commnoManager;
    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name="serverLogService")
   	private ServerLogManager serverLogService;
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
                pd.put("name",json.get("name"));
                pd.put("id",json.get("id"));
                pd.put("phone",json.get("phone"));
                pd.put("age",json.get("age"));
                pd.put("sex",json.get("sex"));
                pd.put("cardtype",json.get("cardtype"));
                pd.put("idcard",json.get("idcard"));
                pd.put("address",json.get("address"));
                pd.put("uid",json.get("uid"));
                if(json.get("uid")!=null&&json.get("ucid")!=null){
    				pd.put("ucid",json.get("ucid"));
    				serverLogService.saveRecord(pd);
    			}
                if (json.getString("id")!=null && !json.getString("id").equals("")){
                    List<PageData> commonByid = commnoManager.findCommonByid(pd);
                    if (commonByid.size()>0){
                        System.out.println("编辑");
                        commnoManager.updateCommon(pd);
                        data.put("msg", "修改成功");
                    }else {
                        System.out.println("新增");
                        commnoManager.insertCommon(pd);
                        data.put("msg", "新增成功");
                    }

                }
                data.put("success","true");
                data.put("msg","修改成功");
            }else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }

        }catch (Exception e){
            data.put("success","false");
            data.put("msg","异常");
        }

        return data;
    }
}
