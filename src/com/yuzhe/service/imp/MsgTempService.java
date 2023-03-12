package com.yuzhe.service.imp;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yuzhe.service.MsgTempManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("yuzhemsgtempservice")
public class MsgTempService implements MsgTempManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public void AddMsgTemp(PageData pd) throws Exception {
        dao.save("MsgTempMapper.AddMsgTemp",pd);
    }

    @Override
    public List<PageData> MsgTempListPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("MsgTempMapper.MsgTemplistPage",page);
    }

    @Override
    public void delMsgTemp(PageData pd) throws Exception {
        dao.delete("MsgTempMapper.delMsgTemp",pd);
    }

    @Override
    public PageData FindMsgById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("MsgTempMapper.FindMsgById",pd);
    }

    @Override
    public void UpdMsgById(PageData pd) throws Exception {
        dao.update("MsgTempMapper.UpdMsgById",pd);
    }

    @Override
    public void SendMsg(PageData pd) throws Exception {
        dao.save("MsgTempMapper.SendMsg",pd);
    }

    @Override
    public void UpdMsgStatus(PageData pd) throws Exception {
        dao.update("MsgTempMapper.UpdMsgStatus",pd);
    }

    @Override
    public List<PageData> MsgTempList() throws Exception {
        return (List<PageData>) dao.findForList("MsgTempMapper.MsgTempList",new PageData());
    }

    @Override
    public List<PageData> MsgLogListPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("MsgTempMapper.MsgLoglistPage",page);
    }

    @Override
    public List<PageData> MsgLogList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MsgTempMapper.MsgLoglist",pd);
    }

    @Override
    public PageData GetMsgLogById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("MsgTempMapper.GetMsgLogById",pd);
    }
}
