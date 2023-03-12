package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.CommnoManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("commnoService")
public class CommnoService implements CommnoManager{
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findAlllistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("CommonMapper.findAlllistPage",pd);
    }

    @Override
    public List<PageData> findCommonByid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CommonMapper.findCommonByid",pd);
    }

    @Override
    public void insertCommon(PageData pd) throws Exception {
        dao.save("CommonMapper.insertCommon",pd);
    }

    @Override
    public void updateCommon(PageData pd) throws Exception {
        dao.update("CommonMapper.updateCommon",pd);
    }

    @Override
    public void deleteCommon(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("CommonMapper.deleteCommon",ArrayDATA_IDS);
    }

    @Override
    public List<PageData> adviInfolistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("CommonMapper.adviInfolistPage",pd);
    }

    @Override
    public List<PageData> complainInfolistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("CommonMapper.complainInfolistPage",pd);
    }
}
