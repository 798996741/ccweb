package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ExetaskManager;

/** 
 * 说明： 参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
@Service("exetaskService")
public class ExetaskService implements ExetaskManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ExetaskMapper.save", pd);
	}
	
	
	public void saveAnswer(PageData pd)throws Exception{
		dao.save("ExetaskMapper.saveAnswer", pd);
	}
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ExetaskMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ExetaskMapper.edit", pd);
	}
	
	public void editAnswer(PageData pd)throws Exception{
		dao.update("ExetaskMapper.editAnswer", pd);
	}
	
	
	
	public void editTableName(PageData pd)throws Exception{
		dao.update("ExetaskMapper.editTableName", pd);
	}
	
	public void dropTable(PageData pd)throws Exception{
		dao.update("ExetaskMapper.dropTable", pd);
	}
	
	public void createNewTable(PageData pd)throws Exception{
		dao.update("ExetaskMapper.createNewTable", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ExetaskMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ExetaskMapper.listAll", pd);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllIsfp(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ExetaskMapper.listAllIsfp", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listCustom(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ExetaskMapper.listCustom", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ExetaskMapper.findById", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByField(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ExetaskMapper.findByField", pd);
	}
	
	public PageData findByAnswer(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ExetaskMapper.findByAnswer", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ExetaskMapper.findByCode", pd);
	}
	
	/**通过起始地址获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAddr(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ExetaskMapper.findByAddr", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ExetaskMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

