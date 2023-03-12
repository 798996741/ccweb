package com.yuzhe.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface LostManager {
	 //新增报失
    int addReport(PageData pd) throws Exception;
    //新增报失附件
    void addReportFiles(PageData pd) throws Exception;
    //新增报失人信息
    void addLoster(PageData pd) throws Exception;
    //追加物品编号至文件表
    void UpdFileAddNumber(PageData pd) throws Exception;
    //根据文件id查寻文件信息
    PageData findFileById(PageData pd) throws Exception;
    //根据文件id删除文件
    void delFileById(PageData pd) throws Exception;
    //查询物品状态
    List<PageData> itemState() throws Exception;
    //物品报失分页查询
    List<PageData> reportListPage(Page page) throws Exception;
    //根据数据字典id查询名称
    PageData getDictionariesNameById(PageData pd) throws Exception;
    //报失详情/编辑数据回显
    PageData getReportById(PageData pd) throws Exception;
    //报失编辑修改
    void UpdReportById(PageData pd) throws Exception;
    //删除
    void DelReportById(PageData pd) throws Exception;
    //报失导出
    List<PageData> reportList(PageData pd) throws Exception;

    public Integer batchInsert(List<PageData> pd)  throws Exception;

    public Integer insertError(List<PageData> pd) throws Exception;

    List<PageData> findLostList(Page page) throws Exception;
}
