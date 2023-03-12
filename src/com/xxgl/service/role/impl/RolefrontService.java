package com.xxgl.service.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Role;
import com.fh.util.PageData;
import com.xxgl.service.role.RolefrontManager;

/**	角色
 * @author huangjianling
 * 修改日期：2020-02-19
 */
@Service("rolefrontService")
public class RolefrontService implements RolefrontManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列出此组下级角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RolefrontMapper.listAllRolesByPId", pd);
	}
	
	/**通过id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RolefrontMapper.findObjectById", pd);
	}
	
	/**添加
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception {
		dao.save("RolefrontMapper.insert", pd);
	}
	
	/**保存修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("RolefrontMapper.edit", pd);
	}
	
	/**删除角色
	 * @param ROLE_ID
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RolefrontMapper.deleteRoleById", ROLE_ID);
	}
	
	/**给当前角色附加菜单权限
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception {
		dao.update("RolefrontMapper.updateRoleRights", role);
	}
	
	/**通过id查找
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Role getRoleById(String ROLE_ID) throws Exception {
		return (Role) dao.findForObject("RolefrontMapper.getRoleById", ROLE_ID);
	}
	
	/**给全部子角色加菜单权限
	 * @param pd
	 * @throws Exception
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RolefrontMapper.setAllRights", pd);
	}
	
	/**权限(增删改查)
	 * @param msg 区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	public void saveB4Button(String msg,PageData pd) throws Exception {
		dao.update("RolefrontMapper."+msg, pd);
	}
	
	
	public boolean isRole(String ROLE_ID) throws Exception {
		boolean boo=false;
		PageData pd_js = new PageData();
		pd_js.put("ROLE_ID", ROLE_ID);
		PageData role=this.findObjectById(pd_js);
		if(role!=null&&role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("工单专员")||role.getString("ROLE_NAME").equals("部门领导")||role.getString("ROLE_NAME").equals("部门处理人员")||role.getString("ROLE_NAME").equals("回复人员")||role.getString("ROLE_NAME").equals("工单审批专员"))||role.getString("ROLE_NAME").equals("工单专员")){
			boo=true;
		}
		return boo;
	}

}
