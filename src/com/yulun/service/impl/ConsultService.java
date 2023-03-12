package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.BlacklistManager;
import com.yulun.service.ConsultManager;
import com.yulun.service.RecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("consultService")
public class ConsultService implements ConsultManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ConsultMapper.edit", pd);
	}
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ConsultMapper.save", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ConsultMapper.datalistPage", page);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ConsultMapper.findByid", pd);
	}
	
	public PageData findByYear(PageData pd) throws Exception{
		return (PageData)dao.findForObject("ConsultMapper.findByYear", pd);
	}
	
	public void editCode(PageData pd) throws Exception{
		dao.update("ConsultMapper.editCode", pd);
	}

	public void saveCode(PageData pd) throws Exception{
		dao.save("ConsultMapper.saveCode", pd);
	}
	
	
}
