package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.user.UserManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;

/** 
 * 说明： 优惠券
 * 创建人：351412933
 * 创建时间：2018-08-01
 * @version
 */
@Service("zxlbService")
public class ZxlbService implements ZxlbManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="userService")
	private UserManager userService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		if(pd.getString("ZXYH")==null||pd.getString("ZXYH").equals("")){
			PageData pd_user=new PageData();
			pd_user.put("USER_ID", pd.get("ID"));	//ID 主键
			pd_user.put("LAST_LOGIN", "");				//最后登录时间
			pd_user.put("IP", "");						//IP
			pd_user.put("STATUS", "0");					//状态
			pd_user.put("SKIN", "default");
			pd_user.put("RIGHTS", "");
			pd_user.put("isshow", "0");
			pd_user.put("USERNAME", pd.getString("ZXID"));
			pd_user.put("NAME", pd.getString("ZXXM"));
			pd_user.put("CREATEMAN", pd.getString("CREATMAN"));
			pd_user.put("ROLE_ID", "");
			pd_user.put("DWBM", pd.getString("dept"));
			pd_user.put("PASSWORD", new SimpleHash("SHA-1", pd_user.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
			if(null == userService.findByUsername(pd_user)){	//判断用户名是否存在
				userService.saveU(pd_user); 					//执行保存
			}
			pd.put("ZXYH",pd.getString("ZXID"));
		}
		dao.save("ZxlbMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("ZxlbMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd)throws Exception{
		if(pd.getString("ZXYH")==null||pd.getString("ZXYH").equals("")){
			PageData pd_user=new PageData();
			pd_user.put("USER_ID", pd.get("ID"));	//ID 主键
			pd_user.put("LAST_LOGIN", "");				//最后登录时间
			pd_user.put("IP", "");						//IP
			pd_user.put("STATUS", "0");					//状态
			pd_user.put("SKIN", "default");
			pd_user.put("RIGHTS", "");
			pd_user.put("isshow", "0");
			pd_user.put("USERNAME", pd.getString("ZXID"));
			pd_user.put("NAME", pd.getString("ZXXM"));
			pd_user.put("CREATEMAN", pd.getString("CREATMAN"));
			pd_user.put("ROLE_ID", "");
			pd_user.put("DWBM", pd.getString("dept"));
			pd_user.put("PASSWORD", new SimpleHash("SHA-1", pd_user.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
			if(null == userService.findByUsername(pd_user)){	//判断用户名是否存在
				userService.saveU(pd_user); 					//执行保存
			}
			pd.put("ZXYH",pd.getString("ZXID"));
		}
		pd.put("ZXYH",pd.getString("ZXID"));
		dao.update("ZxlbMapper.edit", pd);
	}
	
	
	@Override
	public void editTokenid(PageData pd)throws Exception{
		dao.update("ZxlbMapper.editTokenid", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ZxlbMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ZxlbMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findById", pd);
	}
	
	@Override
    public PageData findByTokenId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByTokenId", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByZxyh(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByZxyh", pd);
	}
	
	@Override
	public PageData findByZxid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByZxid", pd);
	}
	
	@Override
	public PageData findByParamCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByParamCode", pd);
	}
	
	@Override
	public PageData findByZxz(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByZxz", pd);
	}
	
	/**通过usernmad获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByUsername(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByUsername", pd);
	}
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ZxlbMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

