package com.xxgl.service.mng;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 文件上传
 * 创建人：351412933
 * 创建时间：2019-11-24
 * @version
 */
public interface FileManager{

	
	public PageData findFileById(PageData pd)throws Exception;
	
	public List<PageData> findFileByuid(PageData pd)throws Exception;
	
	public List<PageData> datalistPage(Page page)throws Exception;
	
	public void deleteFile(PageData pd)throws Exception;
	
	public void deleteAllFile(PageData pd)throws Exception;
	
	public void savefile(PageData pd)throws Exception;
	
}

