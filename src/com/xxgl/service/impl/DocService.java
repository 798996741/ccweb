package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.DocManager;

/** 
 * 说明： 优惠券
 * 创建人：351412933
 * 创建时间：2018-08-01
 * @version
 */
@Service("docService")
public class DocService implements DocManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DocMapper.save", pd);
	}
	
	/**新增错误
	 * @param pd
	 * @throws Exception
	 */
	public void saveError(PageData pd)throws Exception{
		dao.save("DocMapper.saveError", pd);
	}
	
	public void saveAudit(PageData pd)throws Exception{
		dao.save("DocMapper.saveAudit", pd);
	}
	
	public void savefile(PageData pd)throws Exception{
		dao.save("DocMapper.saveFile", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DocMapper.delete", pd);
	}
	
	public void deleteFile(PageData pd)throws Exception{
		dao.delete("DocMapper.deleteFile", pd);
	}
	
	public void deleteAllFile(PageData pd)throws Exception{
		dao.delete("DocMapper.deleteAllFile", pd);
	}

	@Override
	public void delDocByTitle(PageData pd) throws Exception {
		dao.delete("DocMapper.delDocByTitle",pd);
	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DocMapper.edit", pd);
	}
	
	public void editAudit(PageData pd)throws Exception{
		dao.update("DocMapper.editAudit", pd);
	}
	public void sh(PageData pd)throws Exception{
		dao.update("DocMapper.sh", pd);
	}
	
	public void doccomment(PageData pd)throws Exception{
		dao.save("DocMapper.doccomment", pd);
	}
	
	public void clicknum(PageData pd)throws Exception{
		dao.update("DocMapper.clicknum", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.datalist", page);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.datalistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listErrorPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.datalistErrorPage", page);
	}


	@SuppressWarnings("unchecked")
	public List<PageData> listAuditPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.dataAuditlistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllComment(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.listAllComment", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.listAll", pd);
	}
	
	public List<PageData> findFileByuid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.findFileByuid", pd);
	}
	
	public List<PageData> findByIds(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DocMapper.findByIds", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DocMapper.findByid", pd);
	}
	
	
	public PageData findAuditById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DocMapper.findAuditByid", pd);
	}
	
	public PageData findAuditByUid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DocMapper.findAuditByUid", pd);
	}

	@Override
	public PageData findDocByTitle(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DocMapper.findDocByTitle",pd);
	}


	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DocMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

