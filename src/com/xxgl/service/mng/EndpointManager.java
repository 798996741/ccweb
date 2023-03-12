package com.xxgl.service.mng;

import com.fh.entity.Page;

import com.fh.util.PageData;

import java.util.List;

public abstract interface EndpointManager
{
  public abstract PageData findendpointById(PageData paramPageData)
    throws Exception;
  
  public abstract void saveendpoint(PageData paramPageData)
    throws Exception;
  
  public abstract void updateendpoint(PageData paramPageData)
    throws Exception;
  
  public abstract void deleteendpointById(String paramString)
    throws Exception;
  
  public abstract void updateendpointById(PageData paramPageData)
		    throws Exception;
  
  public abstract List<PageData> listendpointByTypeId(Page page)
    throws Exception;
  
  public abstract PageData findCardid(PageData paramPageData)
    throws Exception;
  
  public abstract PageData findCardidBySalary(PageData paramPageData)
    throws Exception;
}
