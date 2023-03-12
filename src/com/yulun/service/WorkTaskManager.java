package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface WorkTaskManager {
    public List<PageData> findAlllistPage(Page pd) throws Exception;
    public PageData findWorkTaskByid(PageData pd) throws Exception;
    public void updateWorkTask(PageData pd) throws Exception;
    public void insertWorkTask(PageData pd) throws Exception;

}
