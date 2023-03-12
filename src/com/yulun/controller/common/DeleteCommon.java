package com.yulun.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.fh.service.system.operatelog.OperateLogManager;
import com.fh.util.IPUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CommnoManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

public class DeleteCommon implements CommonIntefate {
    @Resource(name = "commnoService")
    private CommnoManager commnoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name ="operatelogService")
    private OperateLogManager operateLogManager;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = data.getJSONObject("data");
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", json.get("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {

            try {
                String id = json.getString("id");

                pd.put("ZXID",pd_token.getString("ZXID"));
                if (null!=id && !"".equals(id)){



                    String zxid = pd_token.getString("ZXID");
                    String PROC_INST_ID_ = UuidUtil.get32UUID();
                    String clyj="无";
                    String msgstr="通过";
                    String taskname="执行批量删除";
                    String operate="{处理节点:"+taskname+",工单流程id:"+PROC_INST_ID_+",处理人:"+zxid+",处理意见:"+clyj+",是否通过:"+msgstr+"}";
                    pd.put("ID", PROC_INST_ID_);
                    pd.put("MAPPERNAME","CommonMapper.deleteCommon");
                    pd.put("OPERATEMAN", zxid);// 操作者
                    pd.put("systype","2");
                    pd.put("OPERATEDATE", new Date());// 时间
                    pd.put("OPERATESTR", operate);// 请求参数
                    pd.put("TYPE", "1");// 正常结束
                    pd.put("IP", IPUtil.getLocalIpv4Address());// ip
                    operateLogManager.save(pd);
                    if (id.contains(",")) {
                        String[] split = id.split(",");
                        commnoManager.deleteCommon(split);
                    }else {
                        String[] split = new String[]{id};
                        commnoManager.deleteCommon(split);
                    }

                    data.put("success", "true");
                    data.put("msg", "删除成功");
                }else {
                    data.put("success", "false");
                    data.put("msg", "删除失败");
                }
            } catch (Exception e) {
                data.put("success", "false");
                data.put("msg", "异常");
            }
            data.put("success", "true");
            data.put("msg", "删除成功");
        } else {
            data.put("success", "false");
            data.put("msg", "登录超时，请重新登录");
        }

        return data;
    }
}
