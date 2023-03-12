package com.yulun.service;

import java.util.List;

import com.fh.util.PageData;

public interface QueueStateManager {

	PageData getQueueState(PageData pd) throws Exception;
	
	PageData getCountByHour(PageData pd) throws Exception;
}
