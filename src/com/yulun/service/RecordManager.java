package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface RecordManager {

	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void pf(PageData pd)throws Exception;
	/*
	 *查询所有列表信息
	 *
	 */
	public List<PageData> listPage(Page page)throws Exception;
	
	/**通过批量删除数据
	 * @param pd
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public List<PageData> listByids(PageData pd)throws Exception;

	PageData findRecordClearWarn() throws Exception;

	void updRecordClearWarn(PageData pd) throws  Exception;
	
}
