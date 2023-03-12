package com.yuzhe.service;

import com.fh.util.PageData;

import java.util.List;

public interface StatisticsManager {

    //物品统计
    List<PageData> statistics(PageData pd ) throws Exception;
    //查询物品等级
    List<PageData> FindLevel() throws Exception;
    //导出

}
