package com.xxgl.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.util.PageData;
import com.xxgl.service.mng.CaseManager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("caseService")
public class CaseService
  implements CaseManager
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public PageData findcaseById(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("CaseMapper.findById", pd);
  }
  
  public void savecase(PageData pd)
    throws Exception
  {
    this.dao.save("CaseMapper.save", pd);
  }
  
  public void updatecase(PageData pd)
    throws Exception
  {
    this.dao.save("CaseMapper.update", pd);
  }
  
  public void deletecaseById(String case_CODE)
    throws Exception
  {
    this.dao.save("CaseMapper.delete", case_CODE);
  }
  
  public void updatecaseById(PageData pd) throws Exception
  {
    this.dao.save("CaseMapper.updateState", pd);
  }
  
  /**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
  
  public List<PageData> listcaseByTypeId(Page page)throws Exception
  {
    return (List<PageData>)this.dao.findForList("CaseMapper.caselist1Page", page);
  }
  
  public PageData findCardid(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("CaseMapper.findCardid", pd);
  }
  
  public PageData findCardidBySalary(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("CaseMapper.findCardidBySalary", pd);
  }
  
  public List<CaseBean> listAllCasetoEndpoint(PageData pd) throws Exception{
	  return (List<CaseBean>)this.dao.findForList("CaseMapper.caselist", pd);
  }
}
