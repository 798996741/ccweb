package com.yuzhe.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface MsgTempManager {

    /**
     *新增短信模板
     * @param pd
     * @throws Exception
     */
    void AddMsgTemp(PageData pd) throws Exception;

    /**
     * 短信模板分页查询
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> MsgTempListPage(Page page) throws Exception;

    /**
     * 短信模板删除
     * @param pd
     * @throws Exception
     */
    void delMsgTemp(PageData pd) throws Exception;

    /**
     * 根据id查询短信模板信息
     * @param pd
     * @return
     * @throws Exception
     */
    PageData FindMsgById(PageData pd) throws Exception;

    /**
     * 根据id修改短信模板内容
     * @param pd
     * @throws Exception
     */
    void UpdMsgById(PageData pd) throws Exception;

    /**
     * 发送短信
     * @param pd
     * @throws Exception
     */
    void SendMsg(PageData pd) throws Exception;

    /**
     * 修改短信状态
     * @param pd
     * @throws Exception
     */
    void UpdMsgStatus(PageData pd) throws Exception;

    /**
     * 短信发送模板下拉框查询
     * @return
     * @throws Exception
     */
    List<PageData> MsgTempList() throws Exception;

    /**
     * 短信历史记录分页查询
     * @param page
     * @return
     * @throws Exception
     */
    List<PageData> MsgLogListPage(Page page) throws Exception;

    /**
     * 短信历史记录查询,用于导出
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> MsgLogList(PageData pd) throws Exception;

    /**
     * 根据短信记录id查询短信记录
     * @param pd
     * @return
     * @throws Exception
     */
    PageData GetMsgLogById(PageData pd) throws Exception;
}
