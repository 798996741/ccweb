package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface VipPlanManager {
    public List<PageData> findVipPlanAlllistPage(Page pd) throws Exception;
    public List<PageData> findOrderinfoByVipplanidlistPage(Page pd) throws Exception;
    public PageData findVipplanByid(PageData pd) throws Exception;
    public PageData getMaxno(PageData pd) throws Exception;
    public void insertVipplan(PageData pd) throws Exception;
    public void updateVipplan(PageData pd) throws Exception;
    public void updateServtype(PageData pd) throws Exception;
    public void deleteVipplan(PageData pd) throws Exception;
    public void insertOrtherInfo(PageData pd) throws Exception;
    public void updateOrtherInfo(PageData pd) throws Exception;
    public void updateVipplanid(PageData pd) throws Exception;
    public void deleteOrtherInfo(PageData pd) throws Exception;
}
