package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface OftenWordManager {
    public List<PageData> findWord(PageData pd) throws Exception;
    public PageData findWordById(PageData pd) throws Exception;
    public void insertWord(PageData pd) throws Exception;
    public void updateWord(PageData pd) throws Exception;
    public void deleteWord(PageData pd) throws Exception;
    public List<PageData> findevaluate(PageData pd) throws Exception;
    public PageData findevaluateById(PageData pd) throws Exception;
    public void insertevaluate(PageData pd) throws Exception;
    public void updateevaluate(PageData pd) throws Exception;
    public void deleteevaluate(PageData pd) throws Exception;

    public void inserttthjl(PageData pd) throws Exception;
    public void updatetthjl(PageData pd) throws Exception;
    public PageData getID(PageData pd) throws Exception;
}
