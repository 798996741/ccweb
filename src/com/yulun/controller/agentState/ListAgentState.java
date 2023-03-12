package com.yulun.controller.agentState;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.AgentStateManager;

public class ListAgentState implements CommonIntefate {

	@Resource(name = "agentStateService")
	private AgentStateManager agentStateManager;

	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
		JSONObject json = data.getJSONObject("data");
		PageData pd = new PageData();
		JSONArray zxStateList = new JSONArray();
		JSONArray zxStateTotal = new JSONArray();
		JSONObject job = new JSONObject();
		List<PageData> listpage = new ArrayList<>();
		int lx = 0, sx = 0, sm = 0, hjhgz = 0, zl = 0, thz = 0, sf = 0;
		try {
			pd.put("zbh", json.get("zbh"));
			pd.put("keywords", "%"+json.get("keywords")+"%");
			System.out.println(json);
			listpage = agentStateManager.getZXByStateLike(pd);
			for (PageData pageData : listpage) {
				JSONObject jo = new JSONObject();
				jo.put("zxid", pageData.get("zxid"));
				jo.put("zxxm", pageData.get("zxxm"));
				jo.put("state", pageData.get("state"));
				jo.put("phone",pageData.get("phone"));
				System.out.println(pageData.get("phone").toString());
				switch ((int) pageData.get("state")) {
				case 0:
					jo.put("statename", "离线");
					lx++;
					break;
				case 1:
					jo.put("statename", "示闲");
					sx++;
					break;
				case 2:
					jo.put("statename", "示忙");
					sm++;
					break;
				case 3:
					jo.put("statename", "呼叫后工作");
					hjhgz++;
					break;
				case 4:
					jo.put("statename", "振铃");
					zl++;
					break;
				case 5:
					jo.put("statename", "通话中");
					thz++;
					break;
				case 6:
					jo.put("statename", "三方");
					sf++;
					break;
				default:
					break;
				}
				jo.put("updatetime", pageData.get("updatetime"));
				zxStateList.add(jo);
			}
			job.put("zxStateList", zxStateList);
			JSONObject Total1 = new JSONObject();
			Total1.put("state", 0);
			Total1.put("total", lx);
			JSONObject Total2 = new JSONObject();
			Total2.put("state", 1);
			Total2.put("total", sx);
			JSONObject Total3 = new JSONObject();
			Total3.put("state", 2);
			Total3.put("total", sm);
			JSONObject Total4 = new JSONObject();
			Total4.put("state", 3);
			Total4.put("total", hjhgz);
			JSONObject Total5 = new JSONObject();
			Total5.put("state", 4);
			Total5.put("total", zl);
			JSONObject Total6 = new JSONObject();
			Total6.put("state", 5);
			Total6.put("total", thz);
			JSONObject Total7 = new JSONObject();
			Total7.put("state", 6);
			Total7.put("total", sf);
			zxStateTotal.add(Total1);
			zxStateTotal.add(Total2);
			zxStateTotal.add(Total3);
			zxStateTotal.add(Total4);
			zxStateTotal.add(Total5);
			zxStateTotal.add(Total6);
			zxStateTotal.add(Total7);
			job.put("zxStateTotal", zxStateTotal);
			job.put("success", "true");
			System.out.println(zxStateTotal.toJSONString());
		} catch (Exception e) {
			job.put("success", "false");
			job.put("msg", "异常");
		}

		return job;
	}

}
