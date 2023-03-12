package com.yuzhe.service;

import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 11:26
 **/
public interface ProcessManager {
    public int deleteProcessById(PageData pd) throws Exception;

    List<PageData> findProcessById(PageData pd) throws Exception;

    int saveProcessByUpdate(PageData pd_token) throws Exception;

    void saveProcessByGet(PageData pd) throws Exception;

    void saveProcessByReceive(PageData pd) throws Exception;

    void saveProcessByAgain(PageData pd) throws Exception;

    /**
     *批量设置接受和转移
     * @param pd
     * @return 接受和转移成功的数量
     * @throws Exception
     */
    int batchSaveProcessByReceive(PageData pd) throws Exception;
}
