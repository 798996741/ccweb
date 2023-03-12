package com.yuzhe.service;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;

import java.util.List;
import java.util.Map;

/**
 * @Author: Aliar
 * @Time: 2020-10-27 15:14
 **/
public interface MonitorManager {
    /**
     * 第一层左边数据统计内容 应答数、示忙次数
     * @param timePd
     * @return
     * @throws Exception
     */
    List<PageData> getServiceAnalysisBC(PageData timePd) throws Exception;

    /**
     * 第一层中间数据统计内容 应答率
     * @param timePd
     * @return
     * @throws Exception
     */
    List<PageData> getServiceAnalysisSG(PageData timePd) throws Exception;

    /**
     * 第一层右边的折线图数据
     * @param timePd
     * @return
     * @throws Exception
     */
    List<PageData> getServiceAnalysisLC(PageData timePd) throws Exception;

    /**
     * 接待时长统计数据
     * @param timePd
     * @return
     * @throws Exception
     */
    List<PageData> getReceptionTime(PageData timePd) throws Exception;

    /**
     * 工单处理总览图表有关数据
     * @param timePd
     * @return
     * @throws Exception
     */
    PageData getWorkOrderProcessing(PageData timePd) throws Exception;

    /**
     * 工单处理的统计数据
     * @param timePd
     * @return
     * @throws Exception
     */
    PageData getWorkOrderCount(PageData timePd) throws Exception;;
}
