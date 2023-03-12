package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface VipInfoManager {
    public List<PageData> findAlllistPage(Page pd) throws Exception;
    public PageData findVipInfoByid(PageData pd) throws Exception;
    public void insertVipInfo(PageData pd) throws Exception;
    public void updateVipInfo(PageData pd) throws Exception;
    public void updateVipid(PageData pd) throws Exception;
    public void updateVipresult(PageData pd) throws Exception;
    public void deleteVipInfo(PageData pd) throws Exception;
    public PageData findOrtherByid(PageData pd) throws Exception;
    public List<PageData> findAllByVipidlistPage(Page pd) throws Exception;
    public void insertOrther(PageData pd) throws Exception;
    public void updateOrther(PageData pd) throws Exception;
    public void deleteOrther(PageData pd) throws Exception;
    public void deleteOrtherAll(PageData pd) throws Exception;
    public void insertphoto(PageData pd) throws Exception;
    public PageData getFileByid(PageData pd) throws Exception;
    public List<PageData> getFileByVipid(PageData pd) throws Exception;
    public void deleteFile(PageData pd) throws Exception;
    public void updateFile(PageData pd) throws Exception;
    public List<PageData> checkvip(PageData pd) throws Exception;

}
