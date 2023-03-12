package com.yuzhe.service.imp;

import com.fh.dao.DAO;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yuzhe.service.RecycleManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("recycleService")
public class RecycleService implements RecycleManager {

    @Resource(name = "daoSupport")
    DAO dao;

    /**
     * 回收箱列表
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> recycleList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("RecycleMapper.RecyclelistPage",page);
    }

    /**
     * 回收箱还原
     * @param id
     * @throws Exception
     */
    @Override
    public void restore(String[] id) throws Exception {
        dao.update("RecycleMapper.restore",id);
    }
}
