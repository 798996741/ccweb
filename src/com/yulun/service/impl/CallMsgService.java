package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.CallMsgManger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("callMsgService")
public class CallMsgService implements CallMsgManger {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findVipinfo(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CallMsgkMapper.findVipinfo",pd);
    }

    @Override
    public List<PageData> findCustinfo(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CallMsgkMapper.findCustinfo",pd);
    }

    @Override
    public PageData findnum(PageData pd) throws Exception {
        return (PageData) dao.findForObject("CallMsgkMapper.findnum",pd);
    }

    @Override
    public List<PageData> getCZSM(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CallMsgkMapper.getCZSM",pd);
    }

    @Override
    public List<PageData> findbyucid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CallMsgkMapper.findbyucid",pd);
    }
}
