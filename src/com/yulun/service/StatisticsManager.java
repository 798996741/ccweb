package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface StatisticsManager {
    public List<PageData> zxCallNumCount(PageData pd) throws Exception;
    public List<PageData> zxCallNumSum(PageData pd) throws Exception;
    public List<PageData> getxzid(PageData pd) throws Exception;
    public List<PageData> getcgjtl(PageData pd) throws Exception;
    public List<PageData> getxlfql(PageData pd) throws Exception;

    public List<PageData> getuser(PageData pd) throws Exception;
    public List<PageData> zxopeloglistPage(Page pd) throws Exception;
    public List<PageData> zxopelog(PageData pd) throws Exception;
    public List<PageData> zxcallloglistPage(Page pd) throws Exception;
    public List<PageData> zxcalllog(PageData pd) throws Exception;

    public List<PageData> ZXHWTJlistPage(Page pd) throws Exception;
    public List<PageData> timeZXHWTJlistPage(Page pd) throws Exception;

    public List<PageData> MYDTJlistPage(Page pd) throws Exception;


    public List<PageData> XTHWTJlistPage(Page pd) throws Exception;

    public List<PageData> TimeTelStatlistPage(Page pd) throws Exception;
    public List<PageData> getzxhwtjlistPage(Page pd) throws Exception;
    public List<PageData> getzxhwtj(PageData pd) throws Exception;
    public List<PageData> zxkflistPage(Page pd) throws Exception;
    public List<PageData> getUserByZxz(PageData pd) throws Exception;

    public List<PageData> videolistPage(PageData pd) throws Exception;
    public List<PageData> ServicelistPage(PageData pd) throws Exception;

    public List<PageData> HomeService(PageData pd) throws Exception;

    public List<PageData> StatueChange()  throws Exception;

}
