package com.yuzhe.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.*;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.UserManager;
import com.yuzhe.util.OAuthInfo;
import com.yuzhe.util.Signwx;
import org.apache.batik.css.engine.value.StringValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: Aliar
 * @Time: 2020-09-17 16:14
 **/

@RestController
public class UserWeb implements CommonIntefate {

    @Resource( name = "lfUserService")
    UserManager userService;

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    private final String SUCCESS = "success";
    private final String MSG = "msg";
    private final String TRUE = "true";
    private final String FALSE = "false";


    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        Log log = LogFactory.getLog(this.getClass());
        JSONObject result=new JSONObject();
        PageData pd = new PageData();
        String cmd = data.getString("cmd")==null?"":data.getString("cmd");
        JSONObject json = data.getJSONObject("data");
        pd.putAll(json);
        try {
            if ("alreadyExisit".equals(cmd)) {
                String code = pd.getString("code");
                //获取openid
                OAuthInfo oa = Signwx.getOAuthOpenId(Signwx.APP_ID, Signwx.APPSECRET, json.getString("code"));
                String openid = oa.getOpenId();
                pd.put("openId", openid);
                PageData user = userService.findUserExists(pd);
                if (user == null) {
                    result.put(SUCCESS, FALSE);
                    result.put(MSG, "微信未绑定账户");
                    result.put("openId", openid);
                } else {
                    result.put(SUCCESS, TRUE);
                    result.put(MSG, "查询到已绑定用户");
                    result.put("data", user);
                }
            }

            if ("login".equals(cmd)) {
                if (pd.get("userName") == null || "".equals(String.valueOf(pd.get("userName"))) || pd.get("password") == null || "".equals(String.valueOf(pd.get("password")))) {
                    result.put(SUCCESS, FALSE);
                    result.put(MSG, "账号或密码不能为空");
                    return result;
                }
                String passwrod = MD5.md5(String.valueOf(pd.get("password")));
                pd.put("password", passwrod);
                PageData user = userService.findUserByUP(pd);
                if (user != null) {
                    String openid = String.valueOf(pd.get("openId"));
                    if("".equals(openid) || openid == null || "null".equals(openid)){
                            result.put(SUCCESS, FALSE);
                            result.put(MSG, "openIdIsNull");
                            return result;
                    }
                    if(user.get("tokenid") == null || "null".equals(String.valueOf(user.get("tokenid")))|| "".equals(String.valueOf(user.get("tokenid")))){
                        String tokenId = IDUtils.createUUID();
                        user.put("tokenid",tokenId);
                        pd.put("tokenid", tokenId);
                    }
                    pd.put("id", String.valueOf(user.get("id")));
                    int number = userService.updateOpenId(pd);
                    if (number > 0) {
                        result.put(MSG, "更新openId成功");
                    } else {
                        result.put(MSG, "更新openId失败");
                    }
                    result.put(SUCCESS, TRUE);
                    result.put("data", user);
                } else {
                    result.put(SUCCESS, FALSE);
                    result.put(MSG, "账号或密码错误");
                }
            }

            if ("quit".equals(cmd)) {
                if (String.valueOf(pd.get("id")) != "") {
                    pd.put("openId", "null");
                    int number = userService.updateOpenId(pd);
                    if (number > 0) {
                        result.put(SUCCESS, TRUE);
                        result.put(MSG, "已重置OpenId");
                    } else {
                        result.put(SUCCESS, FALSE);
                        result.put(MSG, "重置OpenId失败");
                    }

                }
            }
        }catch (Exception e){}
        finally {
            return result;
        }
    }
}