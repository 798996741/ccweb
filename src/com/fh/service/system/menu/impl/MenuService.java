package com.fh.service.system.menu.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Menu;
import com.fh.service.system.menu.MenuManager;
import com.fh.util.PageData;

/** 
 * 类名称：MenuService 菜单处理
 * 修改时间：2015年10月27日
 * @version v2
 */
@Service("menuService")
public class MenuService implements MenuManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 通过ID获取其子一级菜单
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenufrontByParentId(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenufrontByParentId", parentId);
	}
	
	public List<Menu> listSubMenuByParentIdcd(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentIdcd", parentId);
	}
	
	/**
	 * 通过菜单ID获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
	}
	
	/**
	 * 新增菜单
	 * @param menu
	 * @throws Exception
	 */
	public void saveMenu(Menu menu) throws Exception {
		dao.save("MenuMapper.insertMenu", menu);
	}
	
	/**
	 * 取最大ID
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
	}
	
	/**
	 * 删除菜单
	 * @param MENU_ID
	 * @throws Exception
	 */
	public void deleteMenuById(String MENU_ID) throws Exception {
		dao.save("MenuMapper.deleteMenuById", MENU_ID);
	}
	
	/**
	 * 编辑
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	public void edit(Menu menu) throws Exception {
		 dao.update("MenuMapper.updateMenu", menu);
	}
	
	/**
	 * 保存菜单图标 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MenuMapper.editicon", pd);
	}
	
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
		for(Menu menu : menuList){
			menu.setMENU_URL("menu/toEdit.do?MENU_ID="+menu.getMENU_ID());
			menu.setSubMenu(this.listAllMenu(menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}

	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuQx(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentId(MENU_ID);
		for(Menu menu : menuList){
			menu.setSubMenu(this.listAllMenuQx(menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}
	
	
	public List<Menu> listAllMenufrontQx(String MENU_ID) throws Exception {
		List<Menu> menuList = this.listSubMenufrontByParentId(MENU_ID);
		for(Menu menu : menuList){
			menu.setSubMenu(this.listAllMenufrontQx(menu.getMENU_ID()));
			menu.setTarget("treeFrame");
		}
		return menuList;
	}
	
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(系统菜单列表)(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenuQxcd(String MENU_ID,String iscd) throws Exception {
		List<Menu> menuList = this.listSubMenuByParentIdcd(MENU_ID);
		for(Menu menu : menuList){
			if(menu.getMENU_TYPE()!=null&&!menu.getMENU_TYPE().equals("3")){
				menu.setSubMenu(this.listAllMenuQxcd(menu.getMENU_ID(),iscd));
				menu.setTarget("treeFrame");
			}
		}
		return menuList;
	}
	
	public List<Menu> listSubMenuByParent(PageData pd)throws Exception{
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParent", pd);
	}
	
	
	public List<PageData> listMenufrontByMenuids(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MenuMapper.listMenufrontByMenuids", pd);
	}
	
	public PageData getMenufrontById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenufrontById", pd);
	}
	
}
