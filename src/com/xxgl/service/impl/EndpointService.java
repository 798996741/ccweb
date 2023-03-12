package com.xxgl.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.util.PageData;
import com.xxgl.service.mng.EndpointManager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("endpointService")
public class EndpointService implements EndpointManager
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public PageData findendpointById(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("EndpointMapper.findById", pd);
  }
  
  public void saveendpoint(PageData pd)
    throws Exception
  {
    this.dao.save("EndpointMapper.save", pd);
  }
  
  public void updateendpoint(PageData pd)
    throws Exception
  {
    this.dao.save("EndpointMapper.update", pd);
  }
  
  public void deleteendpointById(String endpoint_CODE)
    throws Exception
  {
    this.dao.save("EndpointMapper.delete", endpoint_CODE);
  }
  
  public void updateendpointById(PageData pd) throws Exception
  {
    this.dao.save("EndpointMapper.updateState", pd);
  }
  
  /**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
  
  public List<PageData> listendpointByTypeId(Page page)throws Exception
  {
    return (List<PageData>)this.dao.findForList("EndpointMapper.endpointlist", page);
  }
  
  public PageData findCardid(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("EndpointMapper.findCardid", pd);
  }
  
  public PageData findCardidBySalary(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("EndpointMapper.findCardidBySalary", pd);
  }
  
 
}
