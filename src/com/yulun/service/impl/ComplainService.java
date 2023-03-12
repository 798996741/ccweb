package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yulun.service.ComplainManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("complainService")
public class ComplainService implements ComplainManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> getData(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getData",pd);
    }

    @Override
    public List<PageData> getArea(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getArea",pd);
    }

    @Override
    public List<PageData> getDictionaries(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getDictionaries",pd);
    }

    @Override
    public List<PageData> getDictionaries2(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getDictionaries2",pd);
    }

    @Override
    public List<PageData> getgdzlmonth(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getgdzlmonth",pd);
    }

    @Override
    public List<PageData> getgdzlday(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getgdzlday",pd);
    }

    @Override
    public List<PageData> getgdzlyear(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getgdzlyear",pd);
    }

    @Override
    public PageData getcfts(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getcfts",pd);
    }

    @Override
    public PageData getzgds(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getzgds",pd);
    }

    @Override
    public List<PageData> getkscl(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getkscl",pd);
    }

    @Override
    public PageData getkscltsman(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getkscltsman",pd);
    }

    @Override
    public PageData getkscltsclassify(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getkscltsclassify",pd);
    }


    @Override
    public List<PageData> getpjsx(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getpjsx",pd);
    }

    @Override
    public List<PageData> getmaxclsj(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getmaxclsj",pd);
    }

    @Override
    public List<PageData> getminclsj(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getminclsj",pd);
    }

    @Override
    public List<PageData> getzcll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getzcll",pd);
    }

    @Override
    public List<PageData> getcsgd(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getcsgd",pd);
    }

    @Override
    public List<PageData> getdcll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getdcll",pd);
    }

    @Override
    public List<PageData> getcftsmonth(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getcftsmonth",pd);
    }

    @Override
    public List<PageData> getcftsday(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getcftsday",pd);
    }

    @Override
    public List<PageData> getcftsyear(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getcftsyear",pd);
    }

    @Override
    public List<PageData> getksclmonth(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getksclmonth",pd);
    }

    @Override
    public List<PageData> getksclday(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getksclday",pd);
    }

    @Override
    public List<PageData> getksclyear(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getksclyear",pd);
    }

    @Override
    public List<PageData> getsmallbybig(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getsmallbybig",pd);
    }

    @Override
    public List<PageData> getsywyear(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getsywyear",pd);
    }

    @Override
    public List<PageData> getsywday(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getsywday",pd);
    }

    @Override
    public List<PageData> getsywmonth(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getsywmonth",pd);
    }

    @Override
    public List<PageData> getmhzjyear(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getmhzjyear",pd);
    }

    @Override
    public List<PageData> getmhzjday(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getmhzjday",pd);
    }

    @Override
    public List<PageData> getmhzjmonth(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getmhzjmonth",pd);
    }

    @Override
    public List<PageData> getrxyear(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getrxyear",pd);
    }

    @Override
    public List<PageData> getrxday(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getrxday",pd);
    }

    @Override
    public List<PageData> getrxmonth(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getrxmonth",pd);
    }

    @Override
    public PageData getsyw(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getsyw",pd);
    }

    @Override
    public PageData getmhzj(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getmhzj",pd);
    }

    @Override
    public PageData getrx(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getrx",pd);
    }

    @Override
    public List<PageData> getsource(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getsource",pd);
    }

    @Override
    public List<PageData> getOfficeData(PageData pd) throws Exception {
        return  (List<PageData>) dao.findForList("ComplainMapper.getOfficeData",pd);
    }

    @Override
    public PageData getAreaByName(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ComplainMapper.getAreaByName",pd);
    }

    @Override
    public List<PageData> getpjsxoffice(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getpjsxoffice",pd);
    }

    @Override
    public List<PageData> getmaxclsjoffice(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getmaxclsjoffice",pd);
    }

    @Override
    public List<PageData> getminclsjoffice(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getminclsjoffice",pd);
    }

    @Override
    public List<PageData> getcsgdoffice(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ComplainMapper.getcsgdoffice",pd);
    }


}
