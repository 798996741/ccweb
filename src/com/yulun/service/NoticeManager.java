package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;


public interface NoticeManager {
    public List<PageData> findAlllistPage(Page pd) throws Exception;
    public List<PageData> getExcept(PageData pd) throws Exception;
    public List<PageData> noticeByUserIdlistPage(Page pd) throws Exception;
    public PageData findNoticeByid(PageData pd) throws Exception;
    public List<PageData> findNoticeReadByidlistPage(Page pd) throws Exception;
    public void insertNotice(PageData pd) throws Exception;
    public void insertNoticeRead(PageData pd) throws Exception;
    public void updateNotice(PageData pd) throws Exception;
    public void updateNoticeRead(PageData pd) throws Exception;
    public void changeReadById(PageData pd) throws Exception;
    public void deleteOne(PageData pd) throws Exception;
    public void deleteNoticeRead(PageData pd) throws Exception;
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception;
    public PageData getUsername(PageData pd) throws Exception;
}
