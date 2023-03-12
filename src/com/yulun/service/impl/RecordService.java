package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.RecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("recordService")
public class RecordService implements RecordManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RecordMapper.deleteAll", ArrayDATA_IDS);
	}

	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void pf(PageData pd)throws Exception{
		dao.update("RecordMapper.pf", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RecordMapper.datalistPage", page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listByids(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RecordMapper.listByids", pd);
	}

	@Override
	public PageData findRecordClearWarn() throws Exception {
		return (PageData) dao.findForObject("RecordMapper.findRecordClearWarn",new PageData());
	}

	@Override
	public void updRecordClearWarn(PageData pd) throws Exception {
		dao.update("RecordMapper.updRecordClearWarn",pd);
	}

}
