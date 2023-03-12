package com.xxgl.task;

import java.util.Date;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.yulun.service.MsgTempManager;
import com.yulun.utils.msg.httpclient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.PageData;
import com.xxgl.service.mng.WorkorderManager;
@Component
public class AutoRunTask {
    
	@Resource(name="workorderService")
	private WorkorderManager workorderService;

	@Resource(name = "msgTempService")
	private MsgTempManager msgTempManager;
	
    @Scheduled(cron = "0/5 * * * * ? ") // 间隔5秒执行
    public void execute() throws Exception {
//    	taskCycle();
	}
    
    public void taskCycle() {
    	try {
//    		sendMsg();
//			workorderService.getWorkorderList("","");
    		
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println("使用SpringMVC框架配置定时任务");
    }
    
    @Scheduled(cron = "0 0 8-18/2 * * *") // 每天8点-18点 2个小时发送一次，超时信息提醒
    public void timer() throws Exception {
    	try {
			//workorderService.getWorkorderList("","");
    		PageData pd=new PageData();
    		//workorderService.taskSend(pd);
    		System.out.println("执行一次"+new Date());
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    
    @Scheduled(cron = "0/1 * * * * ? ") // 间隔1秒执行,1小时前提醒
    public void overTimeOrder() throws Exception {
    	
    	try {
			//workorderService.getWorkorderList("","");
    		//workorderService.overTimelist("1");
    		//System.out.println("执行一次"+new Date());
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
    
    
    @Scheduled(cron = "0 0 10 ? * * ") // 每天10点执行
    public void overTimeOrder_Cs_Ten() throws Exception {
    	
    	try {
    		workorderService.overTimelist("2");
    		System.out.println("执行一次"+new Date());
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
    
    @Scheduled(cron = "0 0 15 ? * * ") // 间隔1秒执行,每天下午3点执行
    public void overTimeOrder_Cs_Three() throws Exception {
    	
    	try {
    		workorderService.overTimelist("2");
    		System.out.println("执行一次"+new Date());
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
    
    
}