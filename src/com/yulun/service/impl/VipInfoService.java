package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.VipInfoManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("vipInfoService")
public class VipInfoService implements VipInfoManager{
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findAlllistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("VipInfoMapper.findAlllistPage",pd);
    }

    @Override
    public PageData findVipInfoByid(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VipInfoMapper.findVipInfoByid",pd);
    }

    @Override
    public void insertVipInfo(PageData pd) throws Exception {
        dao.save("VipInfoMapper.insertVipInfo",pd);
    }

    @Override
    public void updateVipInfo(PageData pd) throws Exception {
        dao.update("VipInfoMapper.updateVipInfo",pd);
    }

    @Override
    public void updateVipid(PageData pd) throws Exception {
        dao.update("VipInfoMapper.updateVipid",pd);
    }

    @Override
    public void updateVipresult(PageData pd) throws Exception {
        dao.update("VipInfoMapper.updateVipresult",pd);
    }

    @Override
    public void deleteVipInfo(PageData pd) throws Exception {
        dao.delete("VipInfoMapper.deleteVipInfo",pd);
    }

    @Override
    public PageData findOrtherByid(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VipInfoMapper.findOrtherByid",pd);
    }

    @Override
    public List<PageData> findAllByVipidlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("VipInfoMapper.findAllByVipidlistPage",pd);
    }

    @Override
    public void insertOrther(PageData pd) throws Exception {
        dao.save("VipInfoMapper.insertOrther",pd);
    }

    @Override
    public void updateOrther(PageData pd) throws Exception {
        dao.update("VipInfoMapper.updateOrther",pd);
    }

    @Override
    public void deleteOrther(PageData pd) throws Exception {
        dao.delete("VipInfoMapper.deleteOrther",pd);
    }

    @Override
    public void deleteOrtherAll(PageData pd) throws Exception {
        dao.delete("VipInfoMapper.deleteOrtherAll",pd);
    }


    @Override
    public void insertphoto(PageData pd) throws Exception {
        dao.save("VipInfoMapper.insertphoto",pd);
    }

    @Override
    public PageData getFileByid(PageData pd) throws Exception {
        return (PageData) dao.findForObject("VipInfoMapper.getFileByid",pd);
    }

    @Override
    public List<PageData> getFileByVipid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VipInfoMapper.getFileByVipid",pd);
    }

    @Override
    public void deleteFile(PageData pd) throws Exception {
        dao.delete("VipInfoMapper.deleteFile",pd);
    }

    @Override
    public void updateFile(PageData pd) throws Exception {
        dao.delete("VipInfoMapper.updateFile",pd);
    }

    @Override
    public List<PageData> checkvip(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("VipInfoMapper.checkvip",pd);
    }
}
