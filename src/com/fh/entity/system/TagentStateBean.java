package com.fh.entity.system;

public class TagentStateBean {
	
	private String AGENTEID;//坐席id
	private String AGENTNAME;//坐席名字
	private String STATE;//当前状态
	private String UTIME;//持续时间（返回值是以秒为单位，需要转换为 00:02:22 格式）
	private String JTL;//电话接听量
	public String getAGENTEID() {
		return AGENTEID;
	}
	public void setAGENTEID(String aGENTEID) {
		AGENTEID = aGENTEID;
	}
	public String getAGENTNAME() {
		return AGENTNAME;
	}
	public void setAGENTNAME(String aGENTNAME) {
		AGENTNAME = aGENTNAME;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getUTIME() {
		return UTIME;
	}
	public void setUTIME(String uTIME) {
		UTIME = uTIME;
	}
	public String getJTL() {
		return JTL;
	}
	public void setJTL(String jTL) {
		JTL = jTL;
	}


}
