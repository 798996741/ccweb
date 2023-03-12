package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.AuditInfoManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("auditInfoService")
public class AuditInfoService implements AuditInfoManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findAlllistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("AuditInfoMapper.findAlllistPage",pd);
    }

    @Override
    public PageData findAuditByid(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AuditInfoMapper.findAuditByid",pd);
    }

    @Override
    public PageData getMaxAuditno(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AuditInfoMapper.getMaxAuditno",pd);
    }

    @Override
    public void insertAuditInfo(PageData pd) throws Exception {
        dao.save("AuditInfoMapper.insertAuditInfo",pd);
    }

    @Override
    public void updateAuditInfo(PageData pd) throws Exception {
        dao.update("AuditInfoMapper.updateAuditInfo",pd);
    }

    @Override
    public void updateAuditByInfoid(PageData pd) throws Exception {
        dao.update("AuditInfoMapper.updateAuditByInfoid",pd);
    }

    @Override
    public void updateResult(PageData pd) throws Exception {
        dao.update("AuditInfoMapper.updateResult",pd);
    }

    @Override
    public void deleteAuditInfo(PageData pd) throws Exception {
        dao.delete("AuditInfoMapper.deleteAuditInfo",pd);
    }
}
