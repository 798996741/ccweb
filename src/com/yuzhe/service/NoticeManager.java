package com.yuzhe.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface NoticeManager {
    /**
     * 新增公告
     * @param pd
     * @throws Exception
     */
    void saveNotice(PageData pd) throws Exception;

    /**
     * 通知公告列表
     * @return
     * @throws Exception
     */
    List<PageData> noticeList(Page page) throws Exception;

    /**
     * 查询所有失物招领人员
     * @return
     * @throws Exception
     */
    List<PageData> getAllUser() throws Exception;

    /**
     * 根据id查找公告信息
     * @param pd
     * @return
     * @throws Exception
     */
    PageData findNoticeById(PageData pd ) throws Exception;

    /**
     * 根据id修改公告信息
     * @param pd
     * @throws Exception
     */
    void updNotice(PageData pd) throws Exception;

    /**
     * 根据id删除公告信息
     * @param id
     * @throws Exception
     */
    void delNotice(String[] id) throws Exception;
    //==========================================================================
    /**
     * 未阅读公告列表
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> myNoticeList(Page page) throws Exception;

    /**
     * 根据id修改阅读状态
     * @param pd
     * @throws Exception
     */
    void readNotice(PageData pd) throws Exception;

    /**
     * 已阅读公告列表
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> readNoticeList(Page page) throws Exception;

    /**
     * 已未阅读公告id查询
     * @param pd
     * @return
     * @throws Exception
     */
    PageData findMyNoticeById(PageData pd) throws Exception;
}
