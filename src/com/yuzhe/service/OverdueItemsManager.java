package com.yuzhe.service;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

public interface OverdueItemsManager {
	
	    //过期物品数量
		Integer OverdueCount() throws Exception;
	    //过期物品查询
	    List<PageData> listOverdue(Page page) throws Exception;
	    //到期清理修改物品状态
		void Overduedispose(PageData pd)throws Exception;
		//到期清理新增操作过程
		void SaveProcess(PageData pd)throws Exception;
		//重新入库修改物品状态
		void OverdueAgain(PageData pd)throws Exception;
		//根据物品等级查询保存时间
		Integer getDaysByLevel(PageData pd) throws Exception;
		//根据id查询过期物品信息
		PageData getOverdueById(PageData pd) throws Exception;
		//根据id删除过期物品信息
		PageData delOverdueById(PageData pd) throws Exception;
}
