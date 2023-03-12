package com.yuzhe.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.thoughtworks.xstream.mapper.ArrayMapper;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.MonitorManager;
import com.yuzhe.util.HandleTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Aliar
 * @Time: 2020-10-27 15:05
 **/
public class MonitorWeb implements CommonIntefate {

    String MSG = "msg";
    String SUCCESS = "success";
    String FALSE = "false";
    String TRUE = "true";
    String DATA = "data";
    String STARTTIME = "startTime";

    String SERVICEANALYSISBC = "serviceAnalysisBC";
    String SERVICEANALYSISSG = "serviceAnalysisSG";
    String SERVICEANALYSISLC = "serviceAnalysisLC";
    String RECEPTIONTIME = "receptionTime";
    String WORKORDERPROCESSING = "workOrderProcessing";


    @Resource(name = "monitorService")
    MonitorManager monitorService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        PageData timePd = new PageData();
        String cmd = data.getString("cmd")==null?"":data.getString("cmd");
        timePd.put("startTime", getTime());
        //错误返回
        result.put(MSG, "cmd错误");
        result.put(SUCCESS, FALSE);
        //第一层左边数据统计内容 应答数、示忙次数
        if(SERVICEANALYSISBC.equals(cmd)){
            List<PageData> serviceAnalysisBCList = monitorService.getServiceAnalysisBC(timePd);
            result.put(DATA, serviceAnalysisBCList);
            result.put(MSG, "获取成功");
            result.put(STARTTIME, timePd.getString("startTime"));
            result.put(SUCCESS, TRUE);
        }

        //第一层中间数据统计内容 应答率
        if(SERVICEANALYSISSG.equals(cmd)) {
            List<PageData> serviceAnalysisSGList = monitorService.getServiceAnalysisSG(timePd);
            result.put(DATA, serviceAnalysisSGList);
            result.put(MSG, "获取成功");
            result.put(STARTTIME, timePd.getString("startTime"));
            result.put(SUCCESS, TRUE);
        }

        //第一层右边数据统计内容 折线图
        if(SERVICEANALYSISLC.equals(cmd)) {
            List<PageData> serviceAnalysisLCList = monitorService.getServiceAnalysisLC(timePd);
            result.put(DATA, serviceAnalysisLCList);
            result.put(MSG, "获取成功");
            result.put(STARTTIME, timePd.getString("startTime"));
            result.put(SUCCESS, TRUE);
        }

        //接待时长统计
        if(RECEPTIONTIME.equals(cmd)) {
            List<PageData> receptionTimeList = monitorService.getReceptionTime(timePd);
            result.put(DATA, receptionTimeList);
            result.put(MSG, "获取成功");
            result.put(STARTTIME, timePd.getString("startTime"));
            result.put(SUCCESS, TRUE);
        }

        //工单处理概况
        if(WORKORDERPROCESSING.equals(cmd)) {
            //图形数据
            PageData workOrderProcessing = monitorService.getWorkOrderProcessing(timePd);
            //统计数据
            PageData workOrderCount = monitorService.getWorkOrderCount(timePd);
            //数据统一放入
            PageData workOrder = new PageData();
            workOrder.put("workOrder", workOrderProcessing);
            workOrder.put("workCount", workOrderCount);
            result.put(DATA, workOrder);
            result.put(MSG, "获取成功");
            result.put(STARTTIME, timePd.getString("startTime"));
            result.put(SUCCESS, TRUE);
        }
        return result;
    }


    public String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date time = cal.getTime();
        String weekhand = df.format(time);
        return weekhand + " 00:00:00";
    }
}