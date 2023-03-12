package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.ServerLogManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("serverLogService")
public class ServerLogService implements ServerLogManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ServerlogMapper.save", pd);
	}

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ServerlogMapper.delete", pd);
	}

	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ServerlogMapper.edit", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ServerlogMapper.datalistPage", page);
	}
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServerlogMapper.findByid", pd);
	}
	
	
	public void saveRecord(PageData pd)throws Exception{
		if(pd.getString("ucid")!=null&&!pd.getString("ucid").equals("")&&pd.getString("uid")!=null){
			PageData pd_record=this.findRecordByuid(pd);
			if(pd_record==null){
				dao.save("ServerlogMapper.saveRecord", pd);
			}	
		}
	}
	
	public PageData findRecordByuid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServerlogMapper.findRecordByuid", pd);
	}
	
}
