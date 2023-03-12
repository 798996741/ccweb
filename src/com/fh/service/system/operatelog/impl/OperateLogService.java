package com.fh.service.system.operatelog.impl;



import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.operatelog.OperateLogManager;
import com.fh.util.IPUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;


/** 
 * 说明： 操作日志
 * 创建人：351412933
 * 创建时间：2018-12-05
 * @version
 */
@Service("operatelogService")
public class OperateLogService implements OperateLogManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
			dao.save("OperateLogMapper.save", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OperateLogMapper.datalistPage", page);
	}

	public List<PageData> getFieldsByName(String TABLENAME)throws Exception{
		return (List<PageData>)dao.findForList("OperateLogMapper.getFieldsByName", TABLENAME);
	}

	public PageData getModuleName(String MAPPERNAME)throws Exception{
		return (PageData)dao.findForObject("OperateLogMapper.getModuleName", MAPPERNAME);
	}

	public List<PageData> getModules(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OperateLogMapper.getModules", pd);
	}
	public List<PageData> modulelistGroupByModulename(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OperateLogMapper.modulelistGroupByModulename", pd);
	}

	public PageData findModuleById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OperateLogMapper.findModuleById", pd);
	}

	public void saveModuleMenu(PageData pd) throws Exception {
		dao.save("OperateLogMapper.saveModuleMenu", pd);
	}

	public void editModuleMenu(PageData pd) throws Exception {
		dao.update("OperateLogMapper.editModuleMenu", pd);
	}

	public void deleteModuleMenu(PageData pd) throws Exception {
		dao.delete("OperateLogMapper.deleteModuleMenu", pd);
	}

	public PageData findModuleMenuById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OperateLogMapper.findModuleMenuById", pd);
	}

	public List<PageData> moduleMenulistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("OperateLogMapper.moduleMenulistPage", page);
	}

	@Override
	public void lostandfoundSave(PageData pd) throws Exception {
		dao.save("OperateLogMapper.lostandfoundSave", pd);
	}

	@Override
	public List<PageData> findOperateLog(Page page) throws Exception {
		return (List<PageData>)dao.findForList("OperateLogMapper.operateLoglistPage", page);
	}

}

