package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface CommnoManager {
    public List<PageData> findAlllistPage(Page pd) throws Exception;
    public List<PageData> findCommonByid(PageData pd) throws Exception;
    public void insertCommon(PageData pd) throws Exception;
    public void updateCommon(PageData pd) throws Exception;
    public void deleteCommon(String[] ArrayDATA_IDS) throws Exception;
    public List<PageData> adviInfolistPage(Page pd) throws Exception;
    public List<PageData> complainInfolistPage(Page pd) throws Exception;
}
