package com.yulun.controller.queueState;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.QueueStateManager;

public class ListQueueState implements CommonIntefate {

	@Resource(name = "queueStateService")
	private QueueStateManager queueStateManager;

	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
		JSONObject json = data.getJSONObject("data");
		PageData pd = new PageData();
		pd.put("zbh", json.get("zbh"));
		PageData quepd = queueStateManager.getQueueState(pd);
		System.out.println(quepd.toString());
		System.out.println(quepd.get("AnswerRate"));
		JSONObject job = new JSONObject();
		job.put("NowLine", quepd.get("NowLine"));
		job.put("TodayCall", quepd.get("TodayCall"));
		job.put("AnswerCount", quepd.get("AnswerCount"));
		job.put("LoginAgent", quepd.get("LoginAgent"));
		job.put("IdleAgent", quepd.get("IdleAgent"));
		job.put("BusyAgent", quepd.get("BusyAgent"));
		job.put("WorkAgent", quepd.get("WorkAgent"));
		job.put("AnswerRate", quepd.get("AnswerRate"));
		
		return job;
	}
}
