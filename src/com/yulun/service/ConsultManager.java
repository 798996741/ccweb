package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface ConsultManager {

	

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	public PageData findByYear(PageData pd) throws Exception;
	
	public void editCode(PageData pd) throws Exception;

	public void saveCode(PageData pd) throws Exception;
}
