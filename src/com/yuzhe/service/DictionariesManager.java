package com.yuzhe.service;


import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-07 15:06
 **/


public interface DictionariesManager {
    List<PageData> findDictionaries(PageData dataPd) throws Exception;

    PageData findObjById(PageData pd) throws Exception;

    List<PageData> listAllDictBySql(PageData pd) throws Exception;

    PageData findByDictName(PageData pd)throws Exception;

    PageData findObjByName(PageData pd)throws Exception;


    List<PageData> findDictionariesByGetMethod(PageData pd ) throws Exception;
}
