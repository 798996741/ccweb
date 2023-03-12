package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface AuditInfoManager {
    public List<PageData> findAlllistPage(Page pd) throws Exception;
    public PageData findAuditByid(PageData pd) throws Exception;
    public PageData getMaxAuditno(PageData pd) throws Exception;
    public void insertAuditInfo(PageData pd) throws Exception;
    public void updateAuditInfo(PageData pd) throws Exception;
    public void updateAuditByInfoid(PageData pd) throws Exception;
    public void updateResult(PageData pd) throws Exception;
    public void deleteAuditInfo(PageData pd) throws Exception;

}
