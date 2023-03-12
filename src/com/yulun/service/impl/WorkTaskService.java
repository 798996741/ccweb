package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.WorkTaskManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("workTaskService")
public class WorkTaskService implements WorkTaskManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public List<PageData> findAlllistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("WorkTaskMapper.findAlllistPage",pd);
    }

    @Override
    public PageData findWorkTaskByid(PageData pd) throws Exception {
        return (PageData) dao.findForList("WorkTaskMapper.findWorkTaskByid",pd);
    }

    @Override
    public void updateWorkTask(PageData pd) throws Exception {
        dao.update("WorkTaskMapper.updateWorkTask",pd);
    }

    @Override
    public void insertWorkTask(PageData pd) throws Exception {
        dao.save("WorkTaskMapper.insertWorkTask",pd);
    }

}
