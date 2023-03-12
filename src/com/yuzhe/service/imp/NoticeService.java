package com.yuzhe.service.imp;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yuzhe.service.NoticeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("lostAndFoundNoticeService")
public class NoticeService implements NoticeManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public void saveNotice(PageData pd) throws Exception {
        dao.save("PublicMapper.saveNotice",pd);
        String[] users=pd.getString("userid").split(",");
        for (String s: users ) {
            PageData pageData=new PageData();
            pageData.put("type",0);
            pageData.put("noticeid",pd.get("id"));
            pageData.put("userid",s);
            dao.save("PublicMapper.saveNoticeread",pageData);
        }
    }

    @Override
    public List<PageData> noticeList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PublicMapper.noticelistPage",page);
    }

    @Override
    public List<PageData> getAllUser() throws Exception {
        return (List<PageData>) dao.findForList("PublicMapper.getAllUser",new PageData());
    }

    @Override
    public PageData findNoticeById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("PublicMapper.findNoticeById",pd);
    }

    @Override
    public void updNotice(PageData pd) throws Exception {
        dao.update("PublicMapper.updNotice",pd);
    }

    @Override
    public void delNotice(String[] id) throws Exception {
        dao.delete("PublicMapper.delNotice",id);
        dao.delete("PublicMapper.delMyNotice",id);
    }

    @Override
    public List<PageData> myNoticeList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PublicMapper.MyNoticelistPage",page);
    }

    @Override
    public void readNotice(PageData pd) throws Exception {
        dao.update("PublicMapper.readNotice",pd);
    }

    @Override
    public List<PageData> readNoticeList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PublicMapper.readNoticelistPage",page);
    }

    @Override
    public PageData findMyNoticeById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("PublicMapper.findMyNoticeById",pd);
    }
}
