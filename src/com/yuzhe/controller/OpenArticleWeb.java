package com.yuzhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xxgl.service.impl.WorkorderService;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.DictionariesManager;
import com.yuzhe.util.HttpPostAndHeader;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-09-15 11:07
 **/

@RestController
public class OpenArticleWeb implements CommonIntefate {


    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "dictionService")
    private DictionariesManager dictionariesService;

    private final String SUCCESS = "success";
    private final String MSG = "msg";
    private final String TRUE = "true";
    private final String FALSE = "false";

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        Log log = LogFactory.getLog(this.getClass());
        JSONObject result = new JSONObject();
        PageData pd = new PageData();
        String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
        PageData pd_stoken = new PageData();
        JSONObject json = data.getJSONObject("data");
        pd_stoken.put("TOKENID", json.get("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (null == pd_token) {
            result.put(SUCCESS, FALSE);
            result.put(MSG, "请重新登陆");
            return result;
        }
        pd.putAll(json);
        if ("getAcessToken".equals(cmd)) {
            JSONObject accessToken = HttpPostAndHeader.getAccessToken();
            System.out.println(accessToken);
        }

        if ("findOpenArticlePage".equals(cmd)) {
            String pageIndex = String.valueOf(json.getInteger("pageNum"));
            String pageSize = String.valueOf(json.getInteger("pageSize"));
            if (pageSize == null || "".equals(pageSize) || pageIndex == null || "".equals(pageIndex)) {
                result.put(SUCCESS, FALSE);
                result.put(MSG, "pageSizeOrPageIndexNotFind");
                return result;
            }


            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/queryLostPropertyOfPublicity");
            url.append("?pageNum=" + pageIndex);
            url.append("&pageSize=" + pageSize);
            pd.remove("tokenid");
            pd.remove("pageNum");
            pd.remove("pageSize");
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pd, "post");
            Object dataParse = jsonObject.get("data");
            JSONObject jsonResult = JSONObject.parseObject(dataParse.toString());
            result.put("pageCount", Integer.valueOf(jsonResult.getString("totalPage")));
            result.put("recordCount", Integer.valueOf(jsonResult.getString("totalCount")));
            result.put("pageId", Integer.valueOf(jsonResult.getString("currentPage")));
            JSONArray dataArray = JSONArray.parseArray(jsonResult.getString("objects"));
            PageData dicPd = new PageData();
            dicPd.put("parentId", "0");
            List<PageData> dictionaries = dictionariesService.findDictionaries(dicPd);
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject jsonObj = dataArray.getJSONObject(i);
                if(jsonObj.containsKey("lostPropertyType")){
                    for (PageData dictionary : dictionaries) {
                        String dictionariesId = String.valueOf(dictionary.get("dictionariesId"));
                        if(dictionariesId.equals(jsonObj.getString("lostPropertyType"))){
                            String name = dictionary.getString("name");
                            jsonObj.put("lostPropertyTypeName", name);
                            break;
                        }
                    }
                }
                //时间去掉时分秒
                if(jsonObj.containsKey("pickupTime")){
                    String pickupTime = String.valueOf(jsonObj.get("pickupTime"));
                    String subTime = pickupTime.substring(0, 10);
                    jsonObj.put("pickupTime", subTime);
                }
                if(jsonObj.containsKey("createTime")){
                    String createTime = String.valueOf(jsonObj.get("createTime"));
                    String subTime = createTime.substring(0, 10);
                    jsonObj.put("createTime", subTime);
                }
            }
            result.put("data", dataArray);
            String code = String.valueOf(jsonObject.get("code"));
            if ("0".equals(code)) {
                result.put(SUCCESS, TRUE);
                result.put(MSG, "获取成功");
            } else {
                result.put(SUCCESS, FALSE);
            }

        }
        if ("detail".equals(cmd)) {
            String id = String.valueOf(json.get("id"));
            if (id == null || "".equals(id)) {
                result.put(SUCCESS, FALSE);
                result.put(MSG, "idNotFind");
                return result;
            }

            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/descriptLostProperty/");
            url.append(id);
            pd.remove("tokenid");
            pd.remove("id");
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pd, "get");
            result.putAll(jsonObject);
            String code = String.valueOf(jsonObject.get("code"));
            if ("0".equals(code)) {
                result.put(SUCCESS, TRUE);
                result.put(MSG, "详情获取成功");
            } else {
                result.put(SUCCESS, FALSE);
            }
        }

        if ("findAllArticlePage".equals(cmd)) {
            String pageIndex = String.valueOf(json.getInteger("pageNum"));
            String pageSize = String.valueOf(json.getInteger("pageSize"));
            if (pageSize == null || "".equals(pageSize) || pageIndex == null || "".equals(pageIndex)) {
                result.put(SUCCESS, FALSE);
                result.put(MSG, "pageSizeOrPageIndexNotFind");
                return result;
            }

            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/queryLostPropertyPage");
            url.append("?pageNum=").append(pageIndex);
            url.append("&pageSize=").append(pageSize);
            pd.remove("tokenid");
            pd.remove("pageNum");
            pd.remove("pageSize");
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pd, "post");
            Object dataParse = jsonObject.get("data");
            JSONObject jsonResult = JSONObject.parseObject(dataParse.toString());
            result.put("pageCount", Integer.valueOf(jsonResult.getString("totalPage")));
            result.put("recordCount", Integer.valueOf(jsonResult.getString("totalCount")));
            result.put("pageId", Integer.valueOf(jsonResult.getString("currentPage")));
            JSONArray dataArray = JSONArray.parseArray(jsonResult.getString("objects"));
            //遍历转换类型
            //获取分类
            PageData dicPd = new PageData();
            dicPd.put("parentId", "0");
            List<PageData> dictionaries = dictionariesService.findDictionaries(dicPd);
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject jsonObj = dataArray.getJSONObject(i);
                if(jsonObj.containsKey("lostPropertyType")){
                    for (PageData dictionary : dictionaries) {
                        String dictionariesId = String.valueOf(dictionary.get("dictionariesId"));
                        if(dictionariesId.equals(jsonObj.getString("lostPropertyType"))){
                            String name = dictionary.getString("name");
                            jsonObj.put("lostPropertyTypeName", name);
                            break;
                        }
                    }
                    //去掉时分秒
                    if(jsonObj.containsKey("pickupTime")){
                        String pickupTime = String.valueOf(jsonObj.get("pickupTime"));
                        String subTime = pickupTime.substring(0, 10);
                        jsonObj.put("pickupTime", subTime);
                    }
                    if(jsonObj.containsKey("createTime")){
                        String createTime = String.valueOf(jsonObj.get("createTime"));
                        String subTime = createTime.substring(0, 10);
                        jsonObj.put("createTime", createTime);
                    }
                }
            }


            result.put("data", dataArray);
            String code = String.valueOf(jsonObject.get("code"));
            if ("0".equals(code)) {
                result.put(SUCCESS, TRUE);
                result.put(MSG, "列表获取成功");
            } else {
                result.put(SUCCESS, FALSE);
            }

        }

        if ("getImage".equals(cmd)) {
            StringBuffer url = new StringBuffer();
            List<String> urls = Arrays.asList(String.valueOf(pd.get("urls")).split(","));
            JSONArray jsonUrls = JSONArray.parseArray(JSON.toJSONString(urls));
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/getImage");
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), jsonUrls, "post");
            result.putAll(jsonObject);
            String code = String.valueOf(jsonObject.get("code"));
            if ("0".equals(code)) {
                result.put(SUCCESS, TRUE);
                result.put(MSG, "图片获取成功");
            } else {
                result.put(SUCCESS, FALSE);
            }
        }

        return result;

    }


}

