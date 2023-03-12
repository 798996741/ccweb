package com.yulun.controller.queueState;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.ArrayUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.mysql.fabric.xmlrpc.base.Array;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.QueueStateManager;

public class Statistics implements CommonIntefate {

	@Resource(name = "queueStateService")
	private QueueStateManager queueStateManager;
	
	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
//		int[] hours;
//		JSONObject json = data.getJSONObject("data");
//		System.out.println(json);
//		hours=new int[]{8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
		JSONObject json = data.getJSONObject("data");
		List x=new ArrayList<>();
		for (int i = 8; i <=22; i++) {
			x.add(i);
		}
		JSONObject job = new JSONObject();
		JSONArray lists = new JSONArray(x);
		JSONArray AnswerCounts = new JSONArray();
		JSONArray CallCounts = new JSONArray();
		for (Object list : lists) {
			PageData pd = new PageData();
			pd.put("zbh", json.get("zbh"));
			pd.put("hour", list);
			PageData quepd = queueStateManager.getCountByHour(pd);
			System.out.println(quepd.toString());
			AnswerCounts.add(quepd.get("AnswerCount"));
			CallCounts.add(quepd.get("CallCount"));
		}
		job.put("x", lists);
		job.put("y1", AnswerCounts);
		job.put("y2", CallCounts);
		return job;
	}

}
