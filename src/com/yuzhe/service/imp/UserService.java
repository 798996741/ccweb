package com.yuzhe.service.imp;

import com.fh.dao.DAO;
import com.fh.util.PageData;
import com.yuzhe.service.UserManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Aliar
 * @Time: 2020-09-17 21:00
 **/

@Service("lfUserService")
public class UserService implements UserManager {

    @Resource(name = "daoSupport")
    DAO dao;

    @Override
    public PageData findUserExists(PageData pd) throws Exception {
        return (PageData) dao.findForObject("lfUserMapper.findUserByOpenId",pd);
    }

    @Override
    public int updateOpenId(PageData pd) throws Exception {
        return (int) dao.update("lfUserMapper.updateOpenId",pd);
    }

    @Override
    public PageData findUserByUP(PageData pd) throws Exception {
        return (PageData) dao.findForObject("lfUserMapper.findUserByup",pd);
    }
}