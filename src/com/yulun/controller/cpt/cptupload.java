package com.yulun.controller.cpt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.yulun.controller.api.CommonIntefate;

//import complaintapi.Api;

@Controller
@RequestMapping("api")
public class cptupload implements CommonIntefate{


	
//	@Value("${aircode}")
//	private String aircode;
//	@Value("${checkcode}")
//	private String checkcode;
	
	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
		Properties prop = new Properties();
		//需要外部属性配置文件的路径
		InputStream inputStream;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("cptupload.properties");
			prop.load(inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String aircode=prop.getProperty("aircode");
		String checkcode=prop.getProperty("checkcode");
		System.out.println("===zhj===");
		JSONArray ja= data.getJSONArray("data");
		System.out.println(ja);
		List<Map<String, String>> cptMapList = new ArrayList<>();
		
		for (int i = 0; i < ja.size(); i++) {

			Map<String, String> map = new HashMap<>();
			PageData pd = new PageData();
			map.put("cptid", ja.getJSONObject(i).get("cptid").toString());
			map.put("cptemphasis", ja.getJSONObject(i).get("cptemphasis").toString());
			map.put("surveyprocess", ja.getJSONObject(i).get("surveyprocess").toString());
			map.put("isreconciliation", ja.getJSONObject(i).get("isreconciliation").toString());
			map.put("disposeending", ja.getJSONObject(i).get("disposeending").toString());
			cptMapList.add(map);
		}
		System.out.println(checkcode+"------"+aircode);
//		Map<String,Object> param=Api.ApiUtil(cptMapList, checkcode, aircode);
//		System.out.println("========="+param.toString());
		JSONObject JSON=new JSONObject();
//		JSON.put("ZT", param.toString());
//		return (JSONObject) Api.ApiUtil(cptMapList, checkcode, aircode);
		return JSON;
	}
//	@ResponseBody
//	@RequestMapping(value="/getcptdata")
//	public Map<String, Object> getcptdata(@RequestParam String data) throws Exception {
//		Properties prop = new Properties();
//		//需要外部属性配置文件的路径
//		InputStream inputStream;
//		try {
//			inputStream = this.getClass().getClassLoader().getResourceAsStream("cptupload.properties");
//			prop.load(inputStream);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String aircode=prop.getProperty("aircode");
//		String checkcode=prop.getProperty("checkcode");
//		System.out.println(data);
//		List<Map<String, String>> cptMapList = new ArrayList<>();
//		com.alibaba.fastjson.JSONObject j=com.alibaba.fastjson.JSONObject.parseObject(data);
//		
//		JSONArray ja=j.getJSONArray("cptMapList");
//		for (int i = 0; i < ja.size(); i++) {
//			Map<String, String> map = new HashMap<>();
//			map.put("cptid", ja.getJSONObject(i).get("cptid").toString());
//			map.put("cptemphasis", ja.getJSONObject(i).get("cptemphasis").toString());
//			map.put("surveyprocess", ja.getJSONObject(i).get("surveyprocess").toString());
//			map.put("isreconciliation", ja.getJSONObject(i).get("isreconciliation").toString());
//			map.put("disposeending", ja.getJSONObject(i).get("disposeending").toString());
//			System.out.println(ja.getJSONObject(i).get("cptid").toString()+ja.getJSONObject(i).get("cptemphasis").toString()+ja.getJSONObject(i).get("surveyprocess").toString()+ja.getJSONObject(i).get("isreconciliation").toString()+ja.getJSONObject(i).get("disposeending").toString());
//			cptMapList.add(map);
//		}
//		return Api.ApiUtil(cptMapList, checkcode, aircode);
//		
//	}
}
