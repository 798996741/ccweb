package com.xxgl.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Run;
import com.fh.util.PageData;
import com.xxgl.service.mng.RunManager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("runService")
public class RunService
  implements RunManager
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public PageData findrunById(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("RunMapper.findById", pd);
  }
  
  public void saverun(PageData pd)
    throws Exception
  {
    this.dao.save("RunMapper.save", pd);
  }
  
  public void updaterun(PageData pd)
    throws Exception
  {
    this.dao.save("RunMapper.update", pd);
  }
  
  public void deleterunById(String ID)
    throws Exception
  {
    this.dao.save("RunMapper.delete", ID);
  }
  
  public void updaterunById(PageData pd) throws Exception
  {
    this.dao.save("RunMapper.updateState", pd);
  }
  
  /**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
  
  public List<PageData> listrunByTypeId(Page page)throws Exception
  {
    return (List<PageData>)this.dao.findForList("RunMapper.runlist", page);
  }
  
  public PageData findCardid(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("RunMapper.findid", pd);
  }
  
  public PageData findCardidBySalary(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("RunMapper.findCardidBySalary", pd);
  }
  
  public List<Run> listAllruntoEndpoint(PageData pd) throws Exception{
	  return (List<Run>)this.dao.findForList("RunMapper.runlist", pd);
  }
}
