package com.yuzhe.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.DictionariesManager;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-07 15:01
 **/

@RestController
public class DictionariesWeb implements CommonIntefate {

    private final String SUCCESS = "success";
    private final String MSG = "msg";
    private final String TRUE = "true";
    private final String FALSE = "false";


    @Resource(name = "dictionService")
    DictionariesManager dictionariesService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject result=new JSONObject();
        PageData dataPd = new PageData();
        dataPd.putAll(data.getJSONObject("data"));
        String cmd = data.getString("cmd")==null?"":data.getString("cmd");
        try {
        if("findDict".equals(cmd)){
            List<PageData> dictionaries = dictionariesService.findDictionaries(dataPd);
            result.put(SUCCESS, TRUE);
            result.put("data" ,dictionaries);
        }else if("GetMethod".equals(cmd)){
            List<PageData> dictionaries =dictionariesService.findDictionariesByGetMethod(dataPd);
            result.put("data" ,dictionaries);
            result.put(SUCCESS, TRUE);
        }
        }catch (Exception e){
            result.put(MSG, "异常");
            result.put(SUCCESS, FALSE);
        }finally {
            return result;
        }
    }

}