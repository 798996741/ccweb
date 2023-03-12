package com.yulun.service;

import java.util.List;

import com.fh.util.PageData;

public interface AgentStateManager {

	List<PageData> getZXByState(PageData pd) throws Exception;
	
	List<PageData> getZXByStateLike(PageData pd) throws Exception;
}
