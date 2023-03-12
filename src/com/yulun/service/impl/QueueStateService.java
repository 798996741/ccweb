package com.yulun.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yulun.service.QueueStateManager;

@Service("queueStateService")
public class QueueStateService implements QueueStateManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public PageData getQueueState(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("QueueState.getQueueState", pd);
	}

	@Override
	public PageData getCountByHour(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("QueueState.getCountByHour", pd);
	}

}
