package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.TaskuserManager;

/** 
 * 说明： 参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
@Service("taskuserService")
public class TaskuserService implements TaskuserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TaskuserMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TaskuserMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TaskuserMapper.edit", pd);
	}
	
	public void editStateqd(PageData pd)throws Exception{
		dao.update("TaskuserMapper.editStateqd", pd);
	}
	
	public void editStateCom(PageData pd)throws Exception{
		dao.update("TaskuserMapper.editStateCom", pd);
	}
	
	
	public void editTableName(PageData pd)throws Exception{
		dao.update("TaskuserMapper.editTableName", pd);
	}
	
	public void dropTable(PageData pd)throws Exception{
		dao.update("TaskuserMapper.dropTable", pd);
	}
	
	public void createNewTable(PageData pd)throws Exception{
		dao.update("TaskuserMapper.createNewTable", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TaskuserMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskuserMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllGroupByField(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskuserMapper.listAllGroupByField", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findById", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByTablename(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findByTablename", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findByCode", pd);
	}
	
	/**通过起始地址获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAddr(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findByAddr", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TaskuserMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public PageData findByField(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("TaskuserMapper.findByField", pd);
	}

	@Override
	public void editFptype(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("TaskuserMapper.editFptype", pd);
	}

	
	
}

