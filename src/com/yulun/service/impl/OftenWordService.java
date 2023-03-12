package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yulun.service.OftenWordManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("oftenWordService")
public class OftenWordService implements OftenWordManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public List<PageData> findWord(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OftenWordMapper.findWord",pd);
    }

    @Override
    public PageData findWordById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OftenWordMapper.findWordById",pd);
    }

    @Override
    public void insertWord(PageData pd) throws Exception {
        dao.save("OftenWordMapper.insertWord",pd);
    }

    @Override
    public void updateWord(PageData pd) throws Exception {
        dao.update("OftenWordMapper.updateWord",pd);
    }

    @Override
    public void deleteWord(PageData pd) throws Exception {
        dao.delete("OftenWordMapper.deleteWord",pd);
    }

    @Override
    public List<PageData> findevaluate(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OftenWordMapper.findevaluate",pd);
    }

    @Override
    public PageData findevaluateById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OftenWordMapper.findevaluateById",pd);
    }

    @Override
    public void insertevaluate(PageData pd) throws Exception {
        dao.save("OftenWordMapper.insertevaluate",pd);
    }

    @Override
    public void updateevaluate(PageData pd) throws Exception {
        dao.update("OftenWordMapper.updateevaluate",pd);
    }

    @Override
    public void deleteevaluate(PageData pd) throws Exception {
        dao.delete("OftenWordMapper.deleteevaluate",pd);
    }

    @Override
    public void inserttthjl(PageData pd) throws Exception {
        dao.save("OftenWordMapper.inserttthjl",pd);
    }

    @Override
    public void updatetthjl(PageData pd) throws Exception {
        dao.update("OftenWordMapper.updatetthjl",pd);
    }

    @Override
    public PageData getID(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OftenWordMapper.getID",pd);
    }
}
