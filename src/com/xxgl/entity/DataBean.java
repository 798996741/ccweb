package com.xxgl.entity;

import java.util.ArrayList;
import java.util.List;

public class DataBean {
	private String name;
	private String value;
	List<DataBean> datalist=new ArrayList();
	
	public List<DataBean> getDatalist() {
		return datalist;
	}
	public void setDatalist(List<DataBean> datalist) {
		this.datalist = datalist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
