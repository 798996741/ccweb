package com.fh.entity;

import java.util.List;

/** 
 * 说明：地区管理 实体类
 * 创建人：351412933
 * 创建时间：2018-10-09
 */
public class AreaManage{ 
	
	private String AREA_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private AreaManage areamanage;
	private List<AreaManage> subAreaManage;
	private boolean hasAreaManage = false;
	private String treeurl;
	
	private String AREA_CODE;			//地区编码
	public String getAREA_CODE() {
		return AREA_CODE;
	}
	public void setAREA_CODE(String AREA_CODE) {
		this.AREA_CODE = AREA_CODE;
	}
	private String AREA_LEVEL;			//地区级别
	public String getAREA_LEVEL() {
		return AREA_LEVEL;
	}
	public void setFAREA_LEVEL(String AREA_LEVEL) {
		this.AREA_LEVEL = AREA_LEVEL;
	}

	public String getAREA_ID() {
		return AREA_ID;
	}
	public void setAREAMANAGE_ID(String AREA_ID) {
		this.AREA_ID = AREA_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public AreaManage getAreaManage() {
		return areamanage;
	}
	public void setAreaManage(AreaManage areamanage) {
		this.areamanage = areamanage;
	}
	public List<AreaManage> getSubAreaManage() {
		return subAreaManage;
	}
	public void setSubAreaManage(List<AreaManage> subAreaManage) {
		this.subAreaManage = subAreaManage;
	}
	public boolean isHasAreaManage() {
		return hasAreaManage;
	}
	public void setHasAreaManage(boolean hasAreaManage) {
		this.hasAreaManage = hasAreaManage;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
