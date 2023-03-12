package com.yuzhe.service.imp;

import com.fh.dao.DAO;
import com.fh.util.PageData;
import com.yuzhe.service.CliamManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 11:21
 **/
@Service("cliamService")
public class CliamService implements CliamManager {

    @Resource(name = "daoSupport")
    DAO dao;

    @Override
    public int deleteCliamById(PageData pd) throws Exception {
        return (int) dao.delete("CliamMapper.deleteCliamById", pd);
    }
}