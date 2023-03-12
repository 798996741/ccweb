package com.yuzhe.service.imp;

import java.util.List;

import javax.annotation.Resource;

import com.fh.entity.Page;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yuzhe.service.OverdueItemsManager;

@Service
public class OverdueItemsService implements OverdueItemsManager{

	@Resource(name = "daoSupport")
    private DaoSupport dao;
	
    @Override
    public Integer OverdueCount() throws Exception {
        return (Integer)dao.findForObject("OverdueItemsMapper.getcount", new PageData());
    }


    @Override
    public List<PageData> listOverdue(Page page) throws Exception {
        return (List<PageData>) dao.findForList("OverdueItemsMapper.OverduelistPage",page);
    }

    @Override
    public void Overduedispose(PageData pd) throws Exception {
        dao.update("OverdueItemsMapper.Overduedispose",pd);
    }

    @Override
    public void SaveProcess(PageData pd) throws Exception {
        dao.save("OverdueItemsMapper.SaveProcess",pd);
    }

    @Override
    public void OverdueAgain(PageData pd) throws Exception {
        dao.update("OverdueItemsMapper.OverdueAgain",pd);
    }

    @Override
    public Integer getDaysByLevel(PageData pd) throws Exception {
        return (Integer) dao.findForObject("OverdueItemsMapper.getDaysByLevel",pd);
    }

    @Override
    public PageData getOverdueById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OverdueItemsMapper.getOverdueById",pd);
    }

    @Override
    public PageData delOverdueById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OverdueItemsMapper.delOverdueById",pd);
    }

}
