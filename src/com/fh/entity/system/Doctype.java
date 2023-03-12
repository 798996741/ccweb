package com.fh.entity.system;

import java.util.List;

/**
 * 
* 类名称：文档分类
* 类描述： 
* @author QQ huangjianling
* 作者单位： 
* 联系方式：
* 修改时间：2015年12月16日
* @version 2.0
 */
public class Doctype {


	
	private String name;			//名称
	private String sort;			//编码
	private String type;		//排序	
	private String parentid;		//上级ID
	private String id;				//备注
	private String createdate;			//关联表
	private String createman;	//主键
	private String cont;
	private String state;
	private Doctype dict;
	private List<Doctype> subDict;
	private boolean hasDict = false;
	private String treeurl;
	private String target;
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCreateman() {
		return createman;
	}
	public void setCreateman(String createman) {
		this.createman = createman;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Doctype getDict() {
		return dict;
	}
	public void setDict(Doctype dict) {
		this.dict = dict;
	}
	public List<Doctype> getSubDict() {
		return subDict;
	}
	public void setSubDict(List<Doctype> subDict) {
		this.subDict = subDict;
	}
	public boolean isHasDict() {
		return hasDict;
	}
	public void setHasDict(boolean hasDict) {
		this.hasDict = hasDict;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
	
}
