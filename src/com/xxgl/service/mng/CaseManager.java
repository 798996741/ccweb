package com.xxgl.service.mng;

import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.entity.system.User;
import com.fh.util.PageData;

import java.util.List;

public abstract interface CaseManager
{
  public abstract PageData findcaseById(PageData paramPageData)
    throws Exception;
  
  public abstract void savecase(PageData paramPageData)
    throws Exception;
  
  public abstract void updatecase(PageData paramPageData)
    throws Exception;
  
  public abstract void deletecaseById(String paramString)
    throws Exception;
  
  public abstract void updatecaseById(PageData paramPageData)
		    throws Exception;
  
  public abstract List<PageData> listcaseByTypeId(Page page)
    throws Exception;
  
  
  public abstract PageData findCardid(PageData paramPageData)
    throws Exception;
  
  public abstract PageData findCardidBySalary(PageData paramPageData)
    throws Exception;
  
  public List<CaseBean> listAllCasetoEndpoint(PageData pd) throws Exception;
	
}
