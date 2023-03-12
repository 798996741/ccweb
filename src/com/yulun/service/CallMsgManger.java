package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface CallMsgManger {
    public List<PageData> findVipinfo(PageData pd) throws Exception;
    public List<PageData> findCustinfo(PageData pd) throws Exception;
    public PageData findnum(PageData pd) throws Exception;
    public List<PageData> getCZSM(PageData pd) throws Exception;
    public List<PageData> findbyucid(PageData pd) throws Exception;
}
