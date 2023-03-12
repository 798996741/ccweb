package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.NoticeManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("noticeService")
public class NoticeService implements NoticeManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public List<PageData> findAlllistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("NoticeMapper.findAlllistPage",pd);
    }

    @Override
    public List<PageData> getExcept(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("NoticeMapper.getExcept",pd);
    }

    @Override
    public List<PageData> noticeByUserIdlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("NoticeMapper.noticeByUserIdlistPage",pd);
    }

    @Override
    public PageData findNoticeByid(PageData pd) throws Exception {
        return (PageData) dao.findForObject("NoticeMapper.findNoticeByid",pd);
    }

    @Override
    public List<PageData> findNoticeReadByidlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("NoticeMapper.findNoticeReadByidlistPage",pd);
    }

    @Override
    public void insertNotice(PageData pd) throws Exception {
        dao.save("NoticeMapper.insertNotice",pd);
    }

    @Override
    public void insertNoticeRead(PageData pd) throws Exception {
        dao.save("NoticeMapper.insertNoticeRead",pd);
    }

    @Override
    public void updateNotice(PageData pd) throws Exception {
        dao.update("NoticeMapper.updateNotice",pd);
    }

    @Override
    public void updateNoticeRead(PageData pd) throws Exception {
        dao.update("NoticeMapper.updateNoticeRead",pd);
    }

    @Override
    public void changeReadById(PageData pd) throws Exception {
        dao.update("NoticeMapper.changeReadById",pd);
    }

    @Override
    public void deleteOne(PageData pd) throws Exception {
        dao.delete("NoticeMapper.deleteOne",pd);
    }

    @Override
    public void deleteNoticeRead(PageData pd) throws Exception {
        dao.delete("NoticeMapper.deleteNoticeRead",pd);
    }

    @Override
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("NoticeMapper.deleteAll",ArrayDATA_IDS);
    }

    @Override
    public PageData getUsername(PageData pd) throws Exception {
        return (PageData) dao.findForObject("NoticeMapper.getUsername",pd);
    }
}
