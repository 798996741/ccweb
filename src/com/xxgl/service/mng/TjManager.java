package com.xxgl.service.mng;

import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.entity.system.User;
import com.fh.entity.system.ZthwltjBean;
import com.fh.util.PageData;

import java.util.List;

public abstract interface TjManager
{

  public ZthwltjBean listAllZthwl(PageData pd) throws Exception;
  
  public List<PageData> listTagentState(Page page)throws Exception;
	
}
