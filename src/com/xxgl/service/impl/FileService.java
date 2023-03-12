package com.xxgl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.HttpClientUtil;
import com.fh.util.PageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxgl.controller.WorkorderController;
import com.xxgl.service.mng.FileManager;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.utils.Constants;
import com.xxgl.utils.DateUtils;

/** 
 * 说明： 文件
 * 创建人：351412933
 * 创建时间：2019-11-24
 * @version
 */
@Service("fileService")
public class FileService implements FileManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	
	
	public PageData findFileById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("fileMapper.findFileById", pd);
	}
	
	public List<PageData> findFileByuid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("fileMapper.findFileByuid", pd);
	}
	
	public List<PageData> datalistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("fileMapper.datalistPage", page);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("fileMapper.deleteAll", ArrayDATA_IDS);
	}
	
	public void deleteFile(PageData pd)throws Exception{
		dao.delete("fileMapper.deleteFile", pd);
	}
	
	public void deleteAllFile(PageData pd)throws Exception{
		dao.delete("fileMapper.deleteAllFile", pd);
	}
	
	public void savefile(PageData pd)throws Exception{
		dao.save("fileMapper.saveFile", pd);
	}
	
	
}

