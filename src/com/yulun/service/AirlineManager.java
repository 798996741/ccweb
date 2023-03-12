package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface AirlineManager {

	
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception;

	/*
	 *查询所有列表信息不分页
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPageCache()throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**
	 * 批量插入缓存表
	 * @param pd
	 * @throws Exception
     * @return
	 */
	public int saveCache(List<PageData> pd)throws Exception;
	/**
	 * 修改查询状态
	 * @param pd
	 * @throws Exception
	 */
	public void updIsCreate(List<PageData> pd)throws Exception;

	/*
	 *从缓存表查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> cachelistPage(Page page)throws Exception;
	
}
