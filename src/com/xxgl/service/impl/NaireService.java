package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.NaireManager;

/** 
 * 说明： 参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
@Service("naireService")
public class NaireService implements NaireManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("NaireMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("NaireMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("NaireMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NaireMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NaireMapper.listAll", pd);
	}

	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllAnswer(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NaireMapper.listAllAnswer", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findByNext(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NaireMapper.findByNext", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NaireMapper.findById", pd);
	}
	
	
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NaireMapper.findByCode", pd);
	}
	
	/**通过起始地址获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAddr(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NaireMapper.findByAddr", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("NaireMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	public void saveNext(PageData pd)throws Exception{
		dao.save("NaireMapper.saveNext", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void deleteNext(PageData pd)throws Exception{
		dao.delete("NaireMapper.deleteNext", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editNext(PageData pd)throws Exception{
		dao.update("NaireMapper.editNext", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllNext(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NaireMapper.listAllNext", pd);
	}
	
	@SuppressWarnings("unchecked")
	public PageData findByNextAnswer(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NaireMapper.findByNextAnswer", pd);
	}
	
}

