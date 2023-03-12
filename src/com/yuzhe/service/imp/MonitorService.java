package com.yuzhe.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yuzhe.service.MonitorManager;
import com.yuzhe.util.HandleTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.*;

/**
 * @Author: Aliar
 * @Time: 2020-10-27 15:14
 **/

@Service("monitorService")
public class MonitorService implements MonitorManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public List<PageData> getServiceAnalysisBC(PageData timePd) throws Exception {
        List<PageData> serviceAnalysisBC = (List<PageData>) dao.findForList("MonitorMapper.getServiceAnalysisBC",timePd);
        return serviceAnalysisBC;
    }

    @Override
    public List<PageData> getServiceAnalysisSG(PageData timePd) throws Exception {
        List<PageData> serviceAnalysisSG = (List<PageData>) dao.findForList("MonitorMapper.getServiceAnalysisSG",timePd);
        return serviceAnalysisSG;
    }

    @Override
    public List<PageData> getServiceAnalysisLC(PageData timePd) throws Exception {
        //获取周一到下周一的时间 00:00:00
        String Mon = timePd.getString("startTime");
        String Tues = HandleTime.getFutureTime(Mon, 1);
        String Wed = HandleTime.getFutureTime(Tues, 1);
        String Thur = HandleTime.getFutureTime(Wed, 1);
        String Fri = HandleTime.getFutureTime(Thur, 1);
        String Sat = HandleTime.getFutureTime(Fri, 1);
        String Sun = HandleTime.getFutureTime(Sat, 1);
        String NextMon = HandleTime.getFutureTime(Sun, 1);
        timePd.put("Mon", Mon);
        timePd.put("Tues", Tues);
        timePd.put("Wed", Wed);
        timePd.put("Thur", Thur);
        timePd.put("Fri", Fri);
        timePd.put("Sat", Sat);
        timePd.put("Sun", Sun);
        timePd.put("NextMon", NextMon);
        //获取数据
        List<PageData> serviceAnalysisLC = (List<PageData>) dao.findForList("MonitorMapper.getServiceAnalysisLC",timePd);
        return serviceAnalysisLC;
    }

    @Override
    public List<PageData> getReceptionTime(PageData timePd) throws Exception {
        List<PageData> receptionTime = (List<PageData>) dao.findForList("MonitorMapper.getReceptionTime",timePd);
        return receptionTime;
    }

    @Override
    public PageData getWorkOrderProcessing(PageData timePd) throws Exception {
        PageData workOrderProcessing = (PageData) dao.findForObject("MonitorMapper.getWorkOrderProcessing",timePd);
        return workOrderProcessing;
    }

    @Override
    public PageData getWorkOrderCount(PageData timePd) throws Exception {
        PageData workOrderCount = (PageData) dao.findForObject("MonitorMapper.getWorkOrderProcessingCount",timePd);
        return workOrderCount;
    }
}