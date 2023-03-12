package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.AirlineManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("airlineService")
public class AirlineService implements AirlineManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	 *查询所有列表信息
	 *
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AirlineMapper.datalistPage", page);
	}

	@Override
	public List<PageData> listPageCache() throws Exception {
		return (List<PageData>)dao.findForList("AirlineMapper.datalist", new PageData());
	}


	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AirlineMapper.findByid", pd);
	}

	@Override
	public int saveCache(List<PageData> pd) throws Exception {
		dao.save("AirlineMapper.saveCache",pd);
        return 0;
    }

	@Override
	public void updIsCreate(List<PageData> pd) throws Exception {
		dao.update("AirlineMapper.updIsCreate",pd);
	}

	@Override
	public List<PageData> cachelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("AirlineMapper.cacheDatalistPage", page);
	}

}
