package com.xxgl.service.mng;

import com.fh.entity.Page;
import com.fh.entity.system.Run;
import com.fh.entity.system.User;
import com.fh.util.PageData;

import java.util.List;

public abstract interface RunManager
{
  public abstract PageData findrunById(PageData paramPageData)
    throws Exception;
  
  public abstract void saverun(PageData paramPageData)
    throws Exception;
  
  public abstract void updaterun(PageData paramPageData)
    throws Exception;
  
  public abstract void deleterunById(String paramString)
    throws Exception;
  
  public abstract void updaterunById(PageData paramPageData)
		    throws Exception;
  
  public abstract List<PageData> listrunByTypeId(Page page)
    throws Exception;
  
  
  public abstract PageData findCardid(PageData paramPageData)
    throws Exception;
  
  public abstract PageData findCardidBySalary(PageData paramPageData)
    throws Exception;
  
  public List<Run> listAllruntoEndpoint(PageData pd) throws Exception;
	
}
