package com.yuzhe.service;

import com.fh.util.PageData;

/**
 * @Author: Aliar
 * @Time: 2020-09-17 21:00
 **/
public interface UserManager {
    PageData findUserExists(PageData pd) throws Exception;

    int updateOpenId(PageData pd) throws Exception;

    PageData findUserByUP(PageData pd) throws Exception;
}