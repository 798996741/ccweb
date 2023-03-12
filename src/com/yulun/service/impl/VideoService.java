package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.VideoManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoService implements VideoManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> videolist(Page page) throws Exception {
        return (List<PageData>) dao.findForList("VideoMapper.videolistPage",page);
    }

    @Override
    public void delVideoAll(String[] ids) throws Exception {
        dao.delete("VideoMapper.delVideoAll",ids);
    }
}
