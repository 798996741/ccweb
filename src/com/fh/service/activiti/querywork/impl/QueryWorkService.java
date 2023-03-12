package com.fh.service.activiti.querywork.impl;

import com.fh.dao.DaoSupport;
import com.fh.service.activiti.querywork.QueryWorkManage;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("queryWorkService")
public class QueryWorkService implements QueryWorkManage {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> gettxbm(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("queryWorkMapper.gettxbm",pd);
    }

    @Override
    public List<PageData> gettxdl(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("queryWorkMapper.gettxdl",pd);
    }

    @Override
    public List<PageData> gettxxl(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("queryWorkMapper.gettxxl",pd);
    }

    @Override
    public List<PageData> findByid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("queryWorkMapper.findByid",pd);
    }

    @Override
    public List<PageData> getbigtype(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("queryWorkMapper.getbigtype",pd);
    }

    @Override
    public List<PageData> getsmalltype(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("queryWorkMapper.getsmalltype",pd);
    }

    @Override
    public List<PageData> getjybm(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("queryWorkMapper.getjybm",pd);
    }

    @Override
    public List<PageData> getjydl(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("queryWorkMapper.getjydl",pd);
    }

    @Override
    public List<PageData> getjyxl(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("queryWorkMapper.getjyxl",pd);
    }


}
