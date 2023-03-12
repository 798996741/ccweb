package com.fh.service.activiti.querywork;

import com.fh.util.PageData;

import java.util.List;

public interface QueryWorkManage {
    public List<PageData> gettxbm(PageData pd)throws Exception;
    public List<PageData> gettxdl(PageData pd)throws Exception;
    public List<PageData> gettxxl(PageData pd)throws Exception;
    public List<PageData> findByid(PageData pd)throws Exception;
    public List<PageData> getbigtype(PageData pd)throws Exception;
    public List<PageData> getsmalltype(PageData pd)throws Exception;
    public List<PageData> getjybm(PageData pd)throws Exception;
    public List<PageData> getjydl(PageData pd)throws Exception;
    public List<PageData> getjyxl(PageData pd)throws Exception;
}
