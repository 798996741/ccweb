package com.xxgl.service.mng;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;


/** 
 * 说明： 参数配置表接口
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
public interface TaskcustomManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	public void editHF(PageData pd)throws Exception;
	
	public void editTableName(PageData pd)throws Exception;
	
	public void dropTable(PageData pd)throws Exception;
	
	public void createNewTable(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listCustom(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	public List<PageData> listAllBynotWj(PageData pd)throws Exception;
	
	
	public List<PageData> listAllByWj(PageData pd)throws Exception;
	
	public List<PageData> listAllIsfp(PageData pd)throws Exception;
	
	public List<PageData> listAllByzxman(PageData pd)throws Exception;
	
	
	public List<PageData> listAllIsnotfp(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	public PageData findByField(PageData pd)throws Exception;
	
	public PageData findFieldByID(PageData pd)throws Exception;
	
	public PageData findByCode(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public PageData findByAddr(PageData pd)throws Exception;
	
	public void saveGroupFp(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void deleteGroupFp(PageData pd)throws Exception;
	
	/**通过taskid和groupname获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData listByGroupname(PageData pd)throws Exception;
	
	public PageData listByGroupField(PageData pd)throws Exception;
	
	/*定额分配已使用中*/
	public List<PageData> listAllUseDe(PageData pd)throws Exception;
	
	public List<PageData> listAllCusFp(PageData pd)throws Exception;
	
	public List<PageData> listAllMsg(PageData pd)throws Exception;
	
	public void editCustom(PageData pd)throws Exception;

	public void editCusFp(PageData pd)throws Exception;
	
	public PageData getCusByrandom(PageData pd)throws Exception;
}

