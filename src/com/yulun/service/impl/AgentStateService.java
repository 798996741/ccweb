package com.yulun.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yulun.service.AgentStateManager;

@Service("agentStateService")
public class AgentStateService implements AgentStateManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> getZXByState(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("ZXstateMapper.getNumberByState",pd);
	}
	@Override
	public List<PageData> getZXByStateLike(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("ZXstateMapper.getNumberByStateLike",pd);
	}

}
