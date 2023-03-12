package com.fh.service.system.operatelog;



import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;


/** 
 * 说明： 操作日志接口
 * 创建人：351412933
 * 创建时间：2018-12-05
 * @version
 */
public interface OperateLogManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;

	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;

	/**
	 * 根据mapper获取字段信息
	 * @param TABLENAME
	 * @throws Exception
	 */
	public List<PageData> getFieldsByName(String TABLENAME)throws Exception;

	/**
	 * 根据mapper获取模块名称
	 * @param MAPPERNAME
	 * @throws Exception
	 */
	public PageData getModuleName(String MAPPERNAME)throws Exception;

	/**
	 * 获取所有模块信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getModules(PageData pd)throws Exception;

	/**
	 * 获取所有模块信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> modulelistGroupByModulename(PageData pd)throws Exception;

	/**
	 * 根据表名、ID获取信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findModuleById(PageData pd)throws Exception;

	/**新增模块日志配置
	 * @param pd
	 * @throws Exception
	 */
	public void saveModuleMenu(PageData pd)throws Exception;


	/**编辑模块日志配置
	 * @param pd
	 * @throws Exception
	 */
	public void editModuleMenu(PageData pd)throws Exception;


	/**删除模块日志配置
	 * @param pd
	 * @throws Exception
	 */
	public void deleteModuleMenu(PageData pd)throws Exception;

	/**查询模块日志配置
	 * @param pd
	 * @throws Exception
	 */
	public PageData findModuleMenuById(PageData pd)throws Exception;

	/**模块日志配置列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> moduleMenulistPage(Page page)throws Exception;


    void lostandfoundSave(PageData pd) throws Exception;

	List<PageData> findOperateLog(Page page) throws Exception;
}

