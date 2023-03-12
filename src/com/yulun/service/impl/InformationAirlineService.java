package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.InformationAirlineManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("informationAirlineService")
public class InformationAirlineService implements InformationAirlineManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("InformationAirlineMapper.findById",pd);
    }

    @Override
    public List<PageData> cachelistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("InformationAirlineMapper.cacheDatalistPage",page);
    }
}
