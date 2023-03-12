package com.xxgl.service.mng;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 工单管理
 * 创建人：351412933
 * 创建时间：2019-11-24
 * @version
 */
public interface WorkorderManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	
	public void delete(PageData pd)throws Exception;
	

	public void edit(PageData pd)throws Exception;
	
	public void saveWorkProc(PageData pd)throws Exception;
	
	public void editWorkproc(PageData pd)throws Exception;
	
	
	public void editCL(PageData pd)throws Exception;
	
	public void zxPf(PageData pd)throws Exception;
	
	public void zxDpf(PageData pd)throws Exception;
	
	public void editTsdept(PageData pd)throws Exception;
	
	public void saveTsdept(PageData pd)throws Exception;
	public void editCfbm(PageData pd)throws Exception;
	
	public void editCLByuid(PageData pd)throws Exception;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	public PageData findDbByid(PageData pd)throws Exception;
	
	public void saveDb(PageData pd)throws Exception;
	
	public void updateWorkReceive(PageData pd)throws Exception;
	
	
	public PageData findFileById(PageData pd)throws Exception;
	
	public List<PageData> findFileByuid(PageData pd)throws Exception;
	
	public void deleteFile(PageData pd)throws Exception;
	
	public void deleteAllFile(PageData pd)throws Exception;
	
	public void savefile(PageData pd)throws Exception;
	
	public PageData gdCount(PageData pd)throws Exception;
	
	public PageData taskSend(PageData pd) throws Exception;
	
	public PageData getAllList(PageData pd,PageData user,PageData role,String username) throws Exception;
	
	public int getCsnum(List<PageData> pdList)throws Exception;
	
	public int getCsnum(PageData pddoc)throws Exception;
	
	public PageData findByYear(PageData pd) throws Exception;

	public void editCode(PageData pd) throws Exception;
	
	public void editReplayFile(PageData pd) throws Exception;
	
	public void editReplay(PageData pd) throws Exception;
	
	public void editReplayReceive(PageData pd) throws Exception;
	
	public void saveCode(PageData pd) throws Exception;

	
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public String getWorkorderList(String USER_ID,String sendcont) throws Exception;
	
	public PageData findDbByCasecode(PageData pd)throws Exception;
	/*
	 * 责任单位处理人申请审结
	 */
	public String sendAuditFinish(String caseCode,String replyPoint,String postId,String opinion,String replyPerson,String taskId,String replyPhoneNo,String replyTime,String solved) throws Exception;
	
	/*
	 * 责任单位处理人申请审结
	 */
	public String sendFinishByLeader(String caseCode,String postId,String opinion,String doType,String taskId) throws Exception;
	/*
	 * 1.责任单位签收员或处理人发送下一处理人
	 */
	public String sendNextPerson(String caseCode,String postId,String sendcont) throws Exception;

	/*
	 * 申请退单接口
	 */
	public String sendReturn(String caseCode,String userId,String postId,String opinion,String deptId,String taskId,String unitType) throws Exception;
	
	public void target(PageData pd)throws Exception;
	
	public int returnProc(PageData pd)throws Exception;
	
	public void returnPf(PageData pd)throws Exception;
	
	public List<PageData> overTimelist(String type)throws Exception;
}

