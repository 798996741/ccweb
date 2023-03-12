package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.BlacklistManager;
import com.yulun.service.RecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("blacklistService")
public class BlacklistService implements BlacklistManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception{
		dao.update("AddressMapper.deleteAll", pd);
	}

	
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void sh(PageData pd)throws Exception{
		dao.update("BlacklistMapper.sh", pd);
	}
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BlacklistMapper.edit", pd);
	}
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BlacklistMapper.save", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BlacklistMapper.datalistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listByids(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BlacklistMapper.listByids", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BlacklistMapper.findByid", pd);
	}
	
	public PageData findByTel(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BlacklistMapper.findByTel", pd);
	}
	
	public PageData findSysparam(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BlacklistMapper.findSysparam", pd);
	}
	
	public List<PageData> listSysparams(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BlacklistMapper.listSysparams", pd);
	}
	
	
	public void saveZxlog(PageData pd)throws Exception{
		dao.save("BlacklistMapper.saveZxlog", pd);
	}

	@Override
	public void deleteBlack(PageData pd) throws Exception {
		dao.delete("BlacklistMapper.deleteBlack", pd);
	}
}
