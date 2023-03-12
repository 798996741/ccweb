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
public interface NaireManager{

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
	
	
	public List<PageData> findByNext(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**判断code是否重复
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception;
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	
	
	
	
	public PageData findByAddr(PageData pd)throws Exception;
	
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveNext(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void deleteNext(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editNext(PageData pd)throws Exception;
	

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllNext(PageData pd)throws Exception;
	
	public List<PageData> listAllAnswer(PageData pd)throws Exception;
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNextAnswer(PageData pd)throws Exception;
	
	
}

