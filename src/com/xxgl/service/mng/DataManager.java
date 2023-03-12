package com.xxgl.service.mng;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public abstract interface DataManager
{
  public abstract PageData finddataById(PageData paramPageData)
    throws Exception;
  
  public abstract void savedata(PageData paramPageData)
    throws Exception;
  
  public abstract void updatedata(PageData paramPageData)
    throws Exception;
  
  public abstract void deletedataById(String paramString)
    throws Exception;
  
  public abstract void updatedataById(PageData paramPageData)
		    throws Exception;
  
  public abstract List<PageData> listdataByTypeId(Page page)
    throws Exception;
  
  
  public abstract PageData findCardid(PageData paramPageData)
    throws Exception;
  
  public abstract PageData findCardidBySalary(PageData paramPageData)
    throws Exception;
  
}
