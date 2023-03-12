package com.yuzhe.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface RecycleManager {

    /**
     * 回收箱列表
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> recycleList(Page page) throws Exception;

    /**
     * 回收箱还原
     * @param id
     * @throws Exception
     */
    void restore(String[] id) throws Exception;
}
