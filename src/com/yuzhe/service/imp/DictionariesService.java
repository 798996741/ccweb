package com.yuzhe.service.imp;


import com.fh.dao.DAO;
import com.fh.util.PageData;
import com.yuzhe.service.DictionariesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-07 15:08
 **/

@Service(value = "dictionService")
public class DictionariesService implements DictionariesManager {

    @Resource(name = "daoSupport")
    DAO dao;

    @Override
    public List<PageData> findDictionaries(PageData dataPd) throws Exception {
        return (List<PageData>) dao.findForList("DictionMapper.findDictionaries", dataPd);
    }

    @Override
    public PageData findObjById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("DictionMapper.findObjById", pd);
    }

    public List<PageData> listAllDictBySql(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("DictionMapper.listAllDictBySql", pd);
    }

    public PageData findByDictName(PageData pd)throws Exception{
        return (PageData)dao.findForObject("DictionMapper.findByDictName", pd);
    }

    public PageData findObjByName(PageData pd)throws Exception{
        return (PageData)dao.findForObject("DictionMapper.findObjByName", pd);
    }


    @Override
    public List<PageData> findDictionariesByGetMethod(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("DictionMapper.findDictionariesByGetMethod",pd);
    }
}