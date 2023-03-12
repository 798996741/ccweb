package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.VipPlanManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("vipPlanService")
public class VipPlanService implements VipPlanManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public List<PageData> findVipPlanAlllistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("VIPPlanMapper.findVipPlanAlllistPage",pd);
    }

    @Override
    public List<PageData> findOrderinfoByVipplanidlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("VIPPlanMapper.findOrderinfoByVipplanidlistPage",pd);
    }

    @Override
    public PageData findVipplanByid(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VIPPlanMapper.FindVipplanByid",pd);
    }

    @Override
    public PageData getMaxno(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VIPPlanMapper.getMaxno",pd);
    }

    @Override
    public void insertVipplan(PageData pd) throws Exception {
        dao.save("VIPPlanMapper.insertVipplan",pd);
    }

    @Override
    public void updateVipplan(PageData pd) throws Exception {
        dao.update("VIPPlanMapper.updateVipplan",pd);
    }

    @Override
    public void updateServtype(PageData pd) throws Exception {
        dao.update("VIPPlanMapper.updateServtype",pd);
    }

    @Override
    public void deleteVipplan(PageData pd) throws Exception {
        dao.delete("VIPPlanMapper.deleteVipplan",pd);
    }

    @Override
    public void insertOrtherInfo(PageData pd) throws Exception {
        dao.save("VIPPlanMapper.insertOrtherInfo",pd);
    }

    @Override
    public void updateOrtherInfo(PageData pd) throws Exception {
        dao.save("VIPPlanMapper.updateOrtherInfo",pd);
    }

    @Override
    public void updateVipplanid(PageData pd) throws Exception {
        dao.update("VIPPlanMapper.updateVipplanid",pd);
    }

    @Override
    public void deleteOrtherInfo(PageData pd) throws Exception {
        dao.save("VIPPlanMapper.deleteOrtherInfo",pd);
    }
}
