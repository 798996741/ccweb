package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @author LEN0V0
 */
public interface InformationAirlineManager {

    /**通过id获取数据
     * @param pd
     * @throws Exception
     */
    PageData findById(PageData pd)throws Exception;


    /*
     *从缓存表查询所有列表信息
     *
     */
    List<PageData> cachelistPage(Page page)throws Exception;
}
