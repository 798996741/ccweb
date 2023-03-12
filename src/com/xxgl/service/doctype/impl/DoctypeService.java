package com.xxgl.service.doctype.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Doctype;
import com.fh.util.PageData;
import com.xxgl.service.doctype.DoctypeManager;

/** 
 * 说明： 数据字典
 * 创建人：huangjianling
 * 创建时间：2015-12-16
 * @version
 */
@Service("doctypeService")
public class DoctypeService implements DoctypeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DoctypeMapper.save", pd);
	}
	
	
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DoctypeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DoctypeMapper.edit", pd);
	}
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DoctypeMapper.datalistPage", page);
	}
	
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DoctypeMapper.findById", pd);
	}
	
	/**通过类型名称获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DoctypeMapper.findByName", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DoctypeMapper.findByBianma", pd);
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Doctype> listSubDictByParentId(String parentId) throws Exception {
		return (List<Doctype>) dao.findForList("DoctypeMapper.listSubDictByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Doctype> listAllDict(String parentId,String action) throws Exception {
		List<Doctype> dictList = this.listSubDictByParentId(parentId);
		for(Doctype dict : dictList){
			if(action.equals("doc")){
				dict.setTreeurl("doc/list.do?action=search&doctype_id="+dict.getId());
			}else{
				dict.setTreeurl("doctype/list.do?id="+dict.getId());
			}
			
			dict.setSubDict(this.listAllDict(dict.getId(),action));
			dict.setTarget("treeFrame");
		}
		return dictList;
	}
	
	
	public List<PageData> listAllDoctype(PageData pd) throws Exception {
		List<PageData> dictList = (List<PageData>) dao.findForList("DoctypeMapper.listAllDoctype",pd);
		return dictList;
	}
	
	/**排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	public PageData findFromTbs(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DoctypeMapper.findFromTbs", pd);
	}

	
	public List<PageData> listAllDictByDict(String[] dict_id)
			throws Exception {
		
		return (List<PageData>) dao.findForList("DoctypeMapper.listAllDictByDict", dict_id);
	}
	
}

