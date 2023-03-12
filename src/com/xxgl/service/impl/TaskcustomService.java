package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.TaskcustomManager;

/** 
 * 说明： 参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
@Service("taskcustomService")
public class TaskcustomService implements TaskcustomManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TaskcustomMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TaskcustomMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.edit", pd);
	}

	public void editCustom(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.editCustom", pd);
	}
	
	public void editHF(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.editHF", pd);
	}
	
	
	public void editTableName(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.editTableName", pd);
	}
	
	public void editFptype(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.editFptype", pd);
	}
	
	public void dropTable(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.dropTable", pd);
	}
	
	public void createNewTable(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.createNewTable", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllBynotWj(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllBynotWj", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByWj(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllByWj", pd);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByzxman(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllByzxman", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllIsfp(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllIsfp", pd);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllIsnotfp(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllIsnotfp", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listCustom(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listCustom", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.findById", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByField(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.findByField", pd);
	}
	public PageData findFieldByID(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.findFieldByID", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.findByCode", pd);
	}
	
	/**通过起始地址获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAddr(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.findByAddr", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TaskcustomMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveGroupFp(PageData pd)throws Exception{
		dao.save("TaskcustomMapper.saveGroupFp", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void deleteGroupFp(PageData pd)throws Exception{
		dao.delete("TaskcustomMapper.deleteGroupFp", pd);
	}
	
	
	public PageData listByGroupname(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.listByGroupname", pd);
	}
	public PageData listByGroupField(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.listByGroupField", pd);
	}
	
	public List<PageData> listAllUseDe(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllUseDe", pd);
	}
	
	public List<PageData> listAllCusFp(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllCusFp", pd);
	}
	
	public List<PageData> listAllMsg(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskcustomMapper.listAllMsg", pd);
	}
	
	public void editCusFp(PageData pd)throws Exception{
		dao.update("TaskcustomMapper.editCusFp", pd);
	}
	
	public PageData getCusByrandom(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskcustomMapper.getCusByrandom", pd);
	}
}

