package com.xxgl.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.entity.system.ZthwltjBean;
import com.fh.util.PageData;
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.TjManager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("tjService")
public class TjService implements TjManager
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
 
  public ZthwltjBean listAllZthwl(PageData pd) throws Exception{
	  return (ZthwltjBean)this.dao.findForList("ZthwlMapper.zthwltjInfo", pd);
  }
  
  public List<PageData> listTagentState(Page page)throws Exception
  {
    return (List<PageData>)this.dao.findForList("TagentStateMapper.tagentStatelistPage", page);
  }
}
