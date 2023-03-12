package com.xxgl.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.util.PageData;
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.TdmMobileManager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("tdmMobileService")
public class TdmMobileService
  implements TdmMobileManager
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public PageData findtdmMobileByMobile(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("TdmMobileMapper.findByMobile", pd);
  }
  
}
