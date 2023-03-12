package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.AddressManager;
import com.yulun.service.RecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("addressService")
public class AddressService implements AddressManager{
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
		dao.update("AddressMapper.sh", pd);
	}
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AddressMapper.edit", pd);
	}
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AddressMapper.save", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AddressMapper.datalistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listByids(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AddressMapper.listByids", pd);
	}
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AddressMapper.findByid", pd);
	}
	
	
	public PageData getMaxAddressno(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AddressMapper.getMaxAddressno", pd);
	}
	
}
