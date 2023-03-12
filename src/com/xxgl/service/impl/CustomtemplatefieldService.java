package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.CustomtemplatefieldManager;

/** 
 * 说明： 参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
@Service("customtemplatefieldService")
public class CustomtemplatefieldService implements CustomtemplatefieldManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CustomtemplatefieldMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CustomtemplatefieldMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CustomtemplatefieldMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CustomtemplatefieldMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustomtemplatefieldMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomtemplatefieldMapper.findById", pd);
	}
	
	public PageData findByCusid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomtemplatefieldMapper.findByCusid", pd);
	}
	
	
	/**通过起始地址获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAddr(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomtemplatefieldMapper.findByAddr", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CustomtemplatefieldMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

