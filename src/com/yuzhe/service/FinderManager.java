package com.yuzhe.service;

import com.fh.util.PageData;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 10:43
 **/
public interface FinderManager {

    int deleteFinderById(PageData pd) throws Exception;

    int saveFinder(PageData pd) throws Exception;

}
