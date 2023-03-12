package com.xxgl.controller;

import javax.annotation.Resource;

import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.activiti.ruprocdef.impl.RuprocdefService;

public class AsynTask implements Runnable{
	
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	String pid;
    public void setPid(String pid)
    {
        this.pid = pid;
    }
    String userid;
    public void setUserid(String userid)
    {
        this.userid = userid;
    }
    String dwbm;
    public void setDwbm(String dwbm)
    {
        this.dwbm = dwbm;
    }
    String oPINION;
    public void setOPINION(String oPINION)
    {
        this.oPINION = oPINION;
    }
    private String PROC_INST_ID_;
    public void setPROC_INST_ID_(String PROC_INST_ID_)
    {
        this.PROC_INST_ID_ = PROC_INST_ID_;
    }
    
   
    
    private String id;
    public void setId(String id)
    {
        this.id = id;
    }
    private String ID_;
    public void setID_(String ID_)
    {
        this.ID_ = ID_;
    }
    
    private String cfbm;
    public void setCfbm(String cfbm)
    {
        this.cfbm = cfbm;
    }
    
    private String doaction;
    public void setDoaction(String doaction)
    {
        this.doaction = doaction;
    }
    
    private String msg;
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    private String tsdept;
    public void setTsdept(String tsdept)
    {
        this.tsdept = tsdept;
    }
    
    String ksoffice;
    
    
    public String getKsoffice() {
		return ksoffice;
	}


	public void setKsoffice(String ksoffice) {
		this.ksoffice = ksoffice;
	}
	
    String kstime;


	public String getKstime() {
		return kstime;
	}


	public void setKstime(String kstime) {
		this.kstime = kstime;
	}


	@Override
    public void run()
    {
		try {
			RuprocdefService ruprocdefService=new RuprocdefService();
			//System.out.println(doaction+"dd");
			ruprocdefService.deal(id,PROC_INST_ID_, ID_, dwbm, userid, cfbm, doaction, msg, tsdept, oPINION,ksoffice,kstime);

			//ruprocdefService.deal("", pid, "", dwbm, userid, "", "azb", "yes", "", oPINION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public static void main(String[] args) {
        System.out.println("start....");
       // asynTask();
        System.out.println("返回前端成功");
    }
}