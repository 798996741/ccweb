package com.yuzhe.service.imp;

import javax.annotation.Resource;

import com.fh.entity.Page;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yuzhe.service.LostManager;

import java.util.List;

@Service("lostService")
public class LostService implements LostManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int addReport(PageData pd) throws Exception {
        int report = (int) dao.save("LostMapper.addReport", pd);
        int saveProcess = (int) dao.save("ProcessMapper.saveRegister", pd);
        return report + saveProcess;
    }

    @Override
    public void addReportFiles(PageData pd) throws Exception {
        dao.save("LostMapper.addReportFiles", pd);
    }

    @Override
    public void addLoster(PageData pd) throws Exception {
        dao.save("LostMapper.addLoster", pd);
    }

    @Override
    public void UpdFileAddNumber(PageData pd) throws Exception {
        dao.update("LostMapper.UpdFileAddNumber", pd);
    }

    @Override
    public PageData findFileById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("LostMapper.findFileById", pd);
    }

    @Override
    public void delFileById(PageData pd) throws Exception {
        dao.delete("LostMapper.delFileById", pd);
    }

    @Override
    public List<PageData> itemState() throws Exception {
        return (List<PageData>) dao.findForList("LostMapper.itemState", new PageData());
    }

    @Override
    public List<PageData> reportListPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("LostMapper.reportlistPage", page);
    }

    @Override
    public PageData getDictionariesNameById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("LostMapper.getDictionariesNameById", pd);
    }

    @Override
    public PageData getReportById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("LostMapper.getReportById", pd);
    }

    @Override
    public void UpdReportById(PageData pd) throws Exception {
        dao.update("LostMapper.UpdReportById", pd);
    }

    @Override
    public void DelReportById(PageData pd) throws Exception {
        dao.delete("LostMapper.DelReportById", pd);
    }

    @Override
    public List<PageData> reportList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("LostMapper.reportlist", pd);
    }

    @Override
    public Integer batchInsert(List<PageData> pd) throws Exception {
        return (Integer) dao.save("LostMapper.batchInsert", pd);
    }

    @Override
    public Integer insertError(List<PageData> pd) throws Exception {
        return (Integer) dao.save("LostMapper.insertError", pd);
    }

    @Override
    public List<PageData> findLostList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("LostMapper.findErrorlistPage", page);
    }
}
