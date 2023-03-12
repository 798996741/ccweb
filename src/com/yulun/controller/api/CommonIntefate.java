package com.yulun.controller.api;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface CommonIntefate {
    /**
     * 通用请求接口
     * 每个业务操作都必须继承
     * @param data 业务操作所需要的参数
     * @param request
     * @return 返回JSON 结构体 返回后,外部直接返回到前台
     */
    JSONObject execute(JSONObject data, HttpServletRequest request ) throws Exception;
}
