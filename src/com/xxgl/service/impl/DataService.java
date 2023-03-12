package com.xxgl.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.DataManager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("dataService")
public class DataService
  implements DataManager
{
  @Resource(name="daoSupport")
  private DaoSupport dao;
  
  public PageData finddataById(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("DataMapper.findById", pd);
  }
  
  public void savedata(PageData pd)
    throws Exception
  {
    this.dao.save("DataMapper.save", pd);
  }
  
  public void updatedata(PageData pd)
    throws Exception
  {
    this.dao.save("DataMapper.update", pd);
  }
  
  public void deletedataById(String ID)
    throws Exception
  {
    this.dao.save("DataMapper.delete", ID);
  }
  
  public void updatedataById(PageData pd) throws Exception
  {
    this.dao.save("DataMapper.updateState", pd);
  }
  
  /**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
  
  public List<PageData> listdataByTypeId(Page page)throws Exception
  {
    return (List<PageData>)this.dao.findForList("DataMapper.datalist", page);
  }
  
  public PageData findCardid(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("DataMapper.findCardid", pd);
  }
  
  public PageData findCardidBySalary(PageData pd)
    throws Exception
  {
    return (PageData)this.dao.findForObject("DataMapper.findCardidBySalary", pd);
  }
  
}
