package com.yuzhe.service.imp;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yuzhe.service.StatisticsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("articleStatisticsService")
public class StatisticsService implements StatisticsManager {


    @Resource(name = "daoSupport")
    private DaoSupport dao;



    @Override
    public List<PageData> statistics(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.statistics",pd);
    }

    @Override
    public List<PageData> FindLevel() throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.FindLevel",new PageData());
    }
}
