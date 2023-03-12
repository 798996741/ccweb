package com.yuzhe.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.ProcessManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 17:07
 **/

@RestController
public class ProcessWeb implements CommonIntefate {

    private final String SUCCESS = "success";
    private final String MSG = "msg";
    private final String TRUE = "true";
    private final String FALSE = "false";
    private Integer  pageIndex;
    private Integer  pageSize;

    @Resource(name = "processService")
    ProcessManager processService;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name="userService")
    private UserManager userService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        Log log = LogFactory.getLog(this.getClass());
        JSONObject result=new JSONObject();
        PageData pd = new PageData();
        String cmd = data.getString("cmd")==null?"":data.getString("cmd");
        PageData pd_stoken=new PageData();
        JSONObject json = data.getJSONObject("data");
        pd_stoken.put("TOKENID", json.get("tokenid"));
        try {
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String username=(json.getString("username")==null?"":json.getString("username"));
            if(!"".equals(username)){
                pd_stoken.put("USERNAME", username);
                pd_token=userService.findByUsername(pd_stoken);
                if(pd_token!=null){
                    pd_token.put("dept", pd_token.getString("DWBM"));
                    pd_token.put("ZXYH", username);
                }
                pd.put("ZXID", username);
                pd.put("systype", "1");
            }else{
                if(pd_token!=null){
                    pd.put("ZXID", pd_token.getString("ZXID"));
                }
            }
            if (null == pd_token) {
                result.put(SUCCESS, FALSE);
                result.put(MSG, "请重新登陆");
                return result;
            }
            pd.putAll(json);

            if("findProcessById".equals(cmd)){
                String id = String.valueOf(pd.get("id"));
                if(id == null || "".equals(id) ){
                    result.put(SUCCESS,FALSE);
                    result.put(MSG,"idIsNull");
                    return result;
                }
                List<PageData> processList = processService.findProcessById(pd);
                if(processList.size() > 0){
                    result.put("data", processList);
                    result.put(SUCCESS,TRUE);
                }else{
                    result.put(SUCCESS,FALSE);
                    result.put(MSG,"暂无数据");
                }
            }


        } catch (Exception e) {
            result.put(MSG, "异常");
            result.put(SUCCESS, FALSE);
        } finally {
            return result;
        }

    }
}