package com.yulun.service;

import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;
import com.yulun.entity.MsgTemp;

import java.util.List;

public interface MsgTempManager {
    /**新增
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd)throws Exception;

    /**删除
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd)throws Exception;

    /**修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd)throws Exception;
    /**
     * 通过ID获取其子级列表
     * @param parentId
     * @return
     * @throws Exception
     */
    public List<MsgTemp> listSubDictByParentId(String parentId) throws Exception;

    public List<MsgTemp> listAllDict(String parentId) throws Exception;

    public List<PageData> findMsgLogAlllistPage(Page page) throws Exception;

    public List<PageData> findMsgvipByIdlistPage(Page page) throws Exception;
    public List<PageData> findMsgcustByIdlistPage(Page page) throws Exception;

    public List<PageData> findMsgAlllistPage(Page page) throws Exception;

    public void insertMsgLog(PageData pd) throws Exception;

    public void updateMsgLog(PageData pd) throws Exception;


    public void updateState(PageData pd) throws Exception;
    /*
     * 查询短信未发送的内容
     */
    public List<PageData> findMsgByState(PageData pd) throws Exception ;

    public void deleteAll(String[] ArrayDATA_IDS) throws Exception;

    public List<PageData> getvipinfo(PageData pd) throws Exception;
    public List<PageData> getviptel(PageData pd) throws Exception;
    public List<PageData> getcustom(PageData pd) throws Exception;
    public List<PageData> getaddlist(PageData pd) throws Exception;
    public List<PageData> findMsgLogAll(PageData pd) throws Exception;

    public PageData gomsgdatils(PageData pd) throws Exception;
}
