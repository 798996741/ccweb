package com.yuzhe.service.imp;

import com.fh.dao.DAO;
import com.fh.util.PageData;
import com.yuzhe.service.FinderManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 10:43
 **/
@Service("finderService")
public class FinderService implements FinderManager {
    @Resource(name = "daoSupport")
    DAO dao;

    @Override
    public int deleteFinderById(PageData pd) throws Exception {
        return  (int) dao.delete("FinderMapper.deleteFinderById", pd);
    }

    @Override
    public int saveFinder(PageData pd) throws Exception {
        return  (int) dao.save("FinderMapper.saveFinder", pd);
    }
}