package com.xxgl.controller;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.CaseBean;
import com.fh.entity.system.User;
import com.fh.entity.system.ZthwltjBean;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.xxgl.entity.DataBean;
import com.xxgl.service.mng.CaseManager;
import com.xxgl.service.mng.FieldDetailManager;
import com.xxgl.service.mng.FieldManager;
import com.xxgl.service.mng.TjManager;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Controller
@RequestMapping(value="/tj")
public class TjController
  extends BaseController
{
  String flUrl = "tjhr.do";
  @Resource(name="tjService")
  private TjManager tjService;
  @Resource(name="userService")
  private UserManager userService;
  
  @Resource(name="fhlogService")
  private FHlogManager FHLOG;
  @Resource(name="fieldService")
  private FieldManager fieldService;
  @Resource(name="fielddetailService")
  private FieldDetailManager fielddetailService;
	
  
  @RequestMapping(value="/listTjs")
  public ModelAndView listTjs(Page page) throws Exception
  {
	  ModelAndView mv = getModelAndView();
	  
	  mv.setViewName("system/tj/datatj"); 
	  return mv;
  }
  

  @RequestMapping(value="/getdata")
  @ResponseBody
  public Object getdata(Page page)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    String errInfo = " ";
    ArrayList arry_return=new ArrayList();
    List<DataBean> data=new ArrayList();
    PageData pd = new PageData();
    pd = this.getPageData();
    try
    {
    	String time=pd.getString("time")==null?"30":pd.getString("time");
    	String id=pd.getString("choiceid")==null?"1":pd.getString("choiceid");
    	System.out.println(id+"id");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(50);//最大空闲数
        poolConfig.setMaxIdle(100);//最大连接数
        poolConfig.setMaxWaitMillis(20000);//最大等待毫秒数
        JedisPool pool = new JedisPool(poolConfig,"127.0.0.1");//使用配置创建连接池
        Jedis jedis = pool.getResource();//获取连接
        String CASEID=pd.getString("CASEID")==null?"15":pd.getString("15");
        pd.put("CASEID", CASEID);
		page.setPd(pd);
        List<PageData>	varList = fieldService.list(page);	//列出Field列表
        
        try {
        	
        	if(id.equals("3")){
        		
        	
	        	Set<String> results = new HashSet<String>();
	        	  
	        	results=jedis.zrangeByScore("yliot/dtu/xdl/dtu20180414",System.currentTimeMillis()-1000*60*(Integer.parseInt(time)),System.currentTimeMillis());
	        	String str="";
	        	String[] arry=null;
	        	
	        	DataBean dataBean=null;
	        	DataBean childBean=null;
	        	
	        	List<DataBean> childlist=new ArrayList();
	        	dataBean=new DataBean();
	        	dataBean.setName("温度");
	        	for (String result:results) {
	                System.out.println(result);
	                arry=result.toString().split("#");
	                System.out.println(arry[0]);
	               
	                if(arry.length>=3){
	                	childBean=new DataBean();
	                	childBean.setName(arry[6].substring(11, 20));
	                	if(arry[2].equals("01")){
	                		childBean.setValue(String.valueOf(Float.parseFloat(arry[7])/100));
	                	}else{
	                		childBean.setValue(arry[7]);
	                	}
	                	
	                    childlist.add(childBean);
	                }
	                
	            }
	        	dataBean.setDatalist(childlist);
	        	data.add(dataBean);
	        	
	        	
	        	dataBean=new DataBean();
	        	dataBean.setName("压强");
	        	childlist=new ArrayList();
	        	for (String result:results) {
	                System.out.println(result);
	                arry=result.toString().split("#");
	                System.out.println(arry[0]);
	               
	                if(arry.length>=3){
	                	childBean=new DataBean();
	                	childBean.setName(arry[6].substring(11, 20));
	                	if(arry[2].equals("01")){
	                		childBean.setValue(String.valueOf(Float.parseFloat(arry[8])/100));
	                	}else{
	                		childBean.setValue(arry[8]);
	                	}
	                	
	                    childlist.add(childBean);
	                }
	                
	            }
	        	dataBean.setDatalist(childlist);
	        	data.add(dataBean);
	        	map.put("result", JSON.toJSONString(data));
        	}else if(id.equals("1")){
        		System.out.println(jedis.zrevrange("yliot/dtu/xdl/dtu20180414",0,0));
        		String  a=String.valueOf(jedis.zrevrange("yliot/dtu/xdl/dtu20180414",0,0));
        		a=a.replace("[", "");
        		a=a.replace("]", "");
        		String[] arry=a.split("#");
        		String str="";
        		if(arry.length>9){
        			for(int i=0;i<arry.length;i++){
        				if(!str.equals("")){
        					str=str+"#";
        				}
        				if(i==7){
        					str=str+"温度："+String.valueOf(Float.parseFloat(arry[7])/100);
        				}
        				if(i==8){
        					str=str+"压强："+String.valueOf(Float.parseFloat(arry[8])/100);
        				}if(i==9){
        					str=str+"湿度："+String.valueOf(Float.parseFloat(arry[9])/100);
        				}if(i==10){
        					str=str+"沸点："+String.valueOf(Float.parseFloat(arry[10])/100);
        				}if(i==11){
        					str=str+"风速："+String.valueOf(Float.parseFloat(arry[11])/100);
        				}
        			}
        		}
        		System.out.println(a+"a");
        		map.put("result",str);
        	}else if(id.equals("2")){
        		System.out.println("ddddd");
        		List<PageData>	warnList = fielddetailService.warnlist(pd);	//列出FieldDetail列表
        		System.out.println(JSON.toJSONString(warnList)+"a");
        		map.put("result", JSON.toJSONString(warnList));
        		
        	}
        }finally {
            
            if(jedis!=null) {
                jedis.close();
            }
        }
       
      //}
    }
    catch (Exception e)
    {
      errInfo = "false";
      this.logger.error(e.toString(), e);
    }
    return AppUtil.returnObject(new PageData(), map);
  }
 
}
