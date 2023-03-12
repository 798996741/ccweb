package com.xxgl.service.mng;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 坐席组人员
 * 创建人：351412933
 * 创建时间：2018-08-01
 * @version
 */
public interface ZxlbManager{

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
	
	public void editTokenid(PageData pd)throws Exception;
	
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
	public PageData findByTokenId(PageData pd)throws Exception;
	
	public PageData findByZxyh(PageData pd)throws Exception;
	public PageData findByZxid(PageData pd)throws Exception;
	public PageData findByZxz(PageData pd)throws Exception;
	public PageData findByUsername(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public PageData findByParamCode(PageData pd)throws Exception;
}
