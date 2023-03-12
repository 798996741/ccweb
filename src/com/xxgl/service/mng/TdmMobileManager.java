package com.xxgl.service.mng;


import com.fh.util.PageData;

import java.util.List;

public abstract interface TdmMobileManager
{
  public abstract PageData findtdmMobileByMobile(PageData paramPageData)
    throws Exception;
 
	
}
