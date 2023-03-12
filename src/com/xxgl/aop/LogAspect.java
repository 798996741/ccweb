package com.xxgl.aop;

import com.fh.service.system.operatelog.OperateLogManager;
import com.fh.util.*;
import com.xxgl.utils.OperateLogUtil;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Aspect
public class LogAspect {

    protected Logger logger = Logger.getLogger(this.getClass());

    @Resource(name = "operatelogService")
    private OperateLogManager operatelogService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Pointcut("execution(* *org.mybatis.spring.SqlSessionTemplate.insert(..))")
    public void save() {
    }

    @Pointcut("execution(* *org.mybatis.spring.SqlSessionTemplate.update(..)) ")
    public void update() {
    }

    @Pointcut("execution(* *org.mybatis.spring.SqlSessionTemplate.delete(..))")
    public void delete() {
    }

    @AfterReturning(pointcut = "save()")
    public void saveAfterReturning(JoinPoint point) throws Exception {
       /* System.out.println("save");
        String sql = SqlUtils.getMybatisSql(point, sqlSessionFactory);
        System.out.println("mapper=\n" + args[0]);
        System.out.println("sql\n" + sql);
        Object[] params;
        String paramStr = SqlUtils.arrayToString(args);//参数解析
        System.out.println("paramStr\n" + paramStr);
        System.out.println("paramObject\n" + SqlUtils.wrapCollection(args[1]));*/
    	try{
			Object[] args = point.getArgs();
	    	int number = 0;
			boolean boo=true;
	        PageData menu = operatelogService.getModuleName(args[0].toString().trim());
			if (menu != null) {
	            if (!args[0].equals("OperateLogMapper.save")) {
	                PageData pd = new PageData();
	                pd.put("ID", UuidUtil.get32UUID());
	                pd.put("MAPPERNAME",args[0].toString().trim());
	                pd.put("SQL", SqlUtils.getMybatisSql(point, sqlSessionFactory));
	                JSONObject jsonObj = JSONObject.fromObject(args[1]);
	                if (args[0].equals("UserMapper.updateLastLogin")) {
	                    PageData user = (PageData) args[1];
	                    pd.put("OPERATEMAN", user.getString("USERNAME"));// 操作者
	                    boo=false;
	                } else {
	                	if(Jurisdiction.getUsername()!=null){
	                		pd.put("OPERATEMAN", Jurisdiction.getUsername());// 操作者
	                		pd.put("systype","1");
	                	}else{
	                		if(jsonObj.get("systype")!=null){
	                			pd.put("OPERATEMAN", jsonObj.get("ZXID"));// 操作者
		                		pd.put("systype","3");
	                		}else{
	                			pd.put("OPERATEMAN", jsonObj.get("ZXID"));// 操作者
		                		pd.put("systype","2");
	                		}
	                		
	                	}
	                	
	                	pd.put("OPERATEDATE", new Date());// 时间
	 	                // String paramStr = SqlUtils.arrayToString(args);//参数解析
	 	                //  String paramStr ="";
	 	                String fieldAndAnnotation = null;// 字段加注解
	 	                String columnName = "";// 字段名
	 	                String columnComment = "";// 字段注释
	 	                String editBeforeOperate = null;
	 	                List<PageData> fieldList = operatelogService.getFieldsByName(menu.getString("TABLENAME"));// 获取表字段注解
	 	               
	 	
	 	                String operate = SqlUtils.getParameterValue(args[1]);
	 	                if (fieldList.size() > 0) {
	 	                    for (PageData pdata : fieldList) {
	 	                        fieldAndAnnotation = "";// 字段加注释
	 	                        columnName = pdata.get("COLUMN_NAME") != null ? pdata
	 	                                .get("COLUMN_NAME").toString() : "";
	 	                        columnComment = pdata.get("COMMENTS") != null ? pdata
	 	                                .get("COMMENTS").toString() : "";
	 	                       
	 	                            
	 	                        fieldAndAnnotation =columnComment ;
	 	                        if (operate.toUpperCase().contains(
	 	                                columnName.toUpperCase() + "=")) {
	 	                            operate = operate.toUpperCase().replaceAll(
	 	                                    columnName.toUpperCase() + "=",
	 	                                    fieldAndAnnotation + "=");
	 	                        }
	 	                        if (pdata.get("TYPE").equals("TIMESTAMP")) {//包含时间
	 	                            int start = operate.indexOf(fieldAndAnnotation) + 1;
	 	                            int index = 0;
	 	                            String tempStr = operate.substring(start + columnComment.length() + 2);
	 	                            if (tempStr.indexOf(",") != -1) {//不是最后一个参数
	 	                                index = tempStr.indexOf(",");
	 	                            } else {//最后一个参数
	 	                                index = tempStr.length() - 1;
	 	                            }
	 	                            String dateStr = tempStr.substring(0, index);
	 	                            if(dateStr.contains("GMT+")){
	 	                                SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
	 	                                Date date = sf.parse(dateStr);
	 	                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 	                                String result = "";
	 	                             //   System.out.println("时间格式化" + sdf.format(date).toString());
	 	                                String dateResult = sdf.format(date).toString();
	 	                                // String res  = tempStr.toUpperCase().replaceAll(dateStr.toUpperCase(), dateResult);
	 	                                StringBuilder res = new StringBuilder(tempStr);
	 	                                res.replace(0,index,dateResult);
	 	                                operate = operate.toUpperCase().replace(tempStr.toUpperCase(), res.toString());
	 	                                // System.out.println(operate);
	 	                            }
	 	                        }
	 	                    }
	 	                }
	 	                pd.put("OPERATESTR", operate);// 请求参数
	 	                pd.put("TYPE", "0");// 正常结束
	 	                pd.put("IP", IPUtil.getLocalIpv4Address());// ip
						//判断是否为失物招领系统
						boolean flag = false;
						java.lang.StackTraceElement[] classArray= new Exception().getStackTrace() ;
						for(int i=0;i<classArray.length;i++) {
							String classname = classArray[i].getClassName();
							if ("com.yuzhe".equals(classname.substring(0,9))) {
								flag = true;
							}
						}
						if(flag){
							operatelogService.lostandfoundSave(pd);
						}else{
							operatelogService.save(pd);
						}
	                    
	                }
	
	               
	            }
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
   
    }

    @Before("update()")
    public void updatBefore(JoinPoint point) throws Exception {
      /*  System.out.println("update");

        String sql = SqlUtils.getMybatisSql(point, sqlSessionFactory);
        System.out.println("mapper=\n" + args[0]);
        System.out.println("sql\n" + sql);
        Object[] params;
        String paramStr = SqlUtils.arrayToString(args);//参数解析
        System.out.println("paramStr\n" + paramStr);
        System.out.println("paramObject\n" + SqlUtils.wrapCollection(args[1]));*/
    	try{
	        Object[] args = point.getArgs();
	        PageData menu = operatelogService.getModuleName(args[0].toString().trim());
	        if (menu != null) {
	            if (!args[0].equals("OperateLogMapper.save")) {
	                PageData pd = new PageData();
	                pd.put("ID", UuidUtil.get32UUID());
	                pd.put("MAPPERNAME", args[0].toString().trim());
	                pd.put("SQL", SqlUtils.getMybatisSql(point, sqlSessionFactory));
	                JSONObject jsonObj = null;
	                List<PageData> fieldList = operatelogService.getFieldsByName(menu.getString("TABLENAME"));// 获取表字段注解
	                jsonObj = JSONObject.fromObject(args[1]);



	                if (args[0].equals("UserMapper.updateLastLogin")) {
	                    PageData user = (PageData) args[1];
	                    pd.put("OPERATEMAN", user.getString("USERNAME"));// 操作者
	                } else {
	                	if(Jurisdiction.getUsername()!=null){
	                		pd.put("OPERATEMAN", Jurisdiction.getUsername());// 操作者
	                		pd.put("systype","1");
	                	}else{
	                		if(jsonObj.get("systype")!=null){
	                			pd.put("OPERATEMAN", jsonObj.get("ZXID"));// 操作者
		                		pd.put("systype","3");
	                		}else{
	                			pd.put("OPERATEMAN", jsonObj.get("ZXID"));// 操作者
		                		pd.put("systype","2");
	                		}
	                		
	                	}
	                	pd.put("OPERATEDATE", new Date());// 时间
		                // String paramStr = SqlUtils.arrayToString(args);//参数解析
		                //  String paramStr ="";
		                String fieldAndAnnotation = null;// 字段加注解
		                String columnName = "";// 字段名
		                String columnComment = "";// 字段注释
		                String editBeforeOperate = null;
		               
		                String operate = SqlUtils.getParameterValue(SqlUtils.wrapCollection(args[1]));
		                String COLUMN = "";
		                String COLUMN_NAME="";
		                String str="";
		                PageData before = new PageData();
		                if (fieldList.size() > 0) {
		                    for (PageData pdata : fieldList) {
		                        fieldAndAnnotation = "";// 字段加注释
		                        if (pdata.get("COMMENTS") != null && (operate.contains("id") || operate.contains("主键")) && (pdata.getString("COLUMN_NAME").equals("id") || pdata.getString("COLUMN_NAME").equals("ID"))) {
		                            COLUMN = pdata.getString("COLUMN_NAME");//ID字段
		                            COLUMN_NAME=pdata.getString("COMMENTS");
		                        }
		                        columnName = pdata.get("COLUMN_NAME") != null ? pdata
		                                .get("COLUMN_NAME").toString() : "";
		                        columnComment = pdata.get("COMMENTS") != null ? pdata
		                                .get("COMMENTS").toString() : pdata.get("COLUMN_NAME") != null ? pdata
		                                .get("COLUMN_NAME").toString() : "";
		                       /* if(columnComment.equals("")&&columnName.equals("id")){
		                        	columnComment="主键";
		                        }        
		                        if(columnComment.equals("")){
		                        	columnComment=columnName;
		                        }*/
		                        fieldAndAnnotation =columnComment ;
		                        
		                        if(operate.toUpperCase().contains("{"+columnName.toUpperCase() + "=")){
		                        	 operate = operate.toUpperCase().replaceAll("\\{"+columnName.toUpperCase() + "=","{"+fieldAndAnnotation + "=");
		                        }else if(operate.toUpperCase().contains(","+columnName.toUpperCase() + "=")){
		                        	 operate = operate.toUpperCase().replaceAll(","+columnName.toUpperCase() + "=",","+fieldAndAnnotation + "=");
		                        }else  if (operate.toUpperCase().contains(columnName.toUpperCase() + "=")) {
		                            operate = operate.toUpperCase().replaceAll(columnName.toUpperCase() + "=",fieldAndAnnotation + "=");
		                        }
		                        if (pdata.get("TYPE").equals("TIMESTAMP")) {//包含时间
		                            int start = operate.indexOf(fieldAndAnnotation) + 1;
		                            int index = 0;
		                            String tempStr = operate.substring(start + columnComment.length() + 2);
		                            if (tempStr.indexOf(",") != -1) {//不是最后一个参数
		                                index = tempStr.indexOf(",");
		                            } else {//最后一个参数
		                                index = tempStr.length() - 1;
		                            }
		                            String dateStr = tempStr.substring(0, index);
		                            if(dateStr.contains("GMT+")){
		                                SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
		                                Date date = sf.parse(dateStr);
		                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                                String result = "";
		                            //    System.out.println("时间格式化" + sdf.format(date).toString());
		                                String dateResult = sdf.format(date).toString();
		                                // String res  = tempStr.toUpperCase().replaceAll(dateStr.toUpperCase(), dateResult);
		                                StringBuilder res = new StringBuilder(tempStr);
		                                res.replace(0,index,dateResult);
		                                operate = operate.toUpperCase().replace(tempStr.toUpperCase(), res.toString());
		                                // System.out.println(operate);
		                            }
		                        }
		                    }
		                    if (!COLUMN.equals("")) {
		                        int start = operate.indexOf("("+COLUMN_NAME+")") + 1;
		                        int index = 0;
		                        String tempStr = operate.substring(start + COLUMN_NAME.length() + 2);
		                        if (tempStr.indexOf(",") != -1) {//不是最后一个参数
		                            index = tempStr.indexOf(",");
		                        } else {//最后一个参数
		                            index = tempStr.length() - 1;
		                        }
		                        String ID = tempStr.substring(0, index).toLowerCase();
		                        PageData module = new PageData();
		                        module.put("TABLENAME", menu.getString("TABLENAME"));
		                        module.put("COLUMN", COLUMN);
		                        module.put("ID", ID);
		                        before = operatelogService.findModuleById(module);
		                        //System.out.println("ID=" + ID);
		                    }
		                }
		                
		                if (!COLUMN.equals("")) {
		                    pd.put("OPERATESTR", "修改前数据:" + SqlUtils.getParameterComments(before, fieldList) + ",修改后数据：" + operate);// 请求参数
		                } else {
		                    pd.put("OPERATESTR", "执行修改:" + operate.toString());
		                }
		                pd.put("TYPE", "0");// 正常结束
		                pd.put("IP", IPUtil.getLocalIpv4Address());// ip
						//判断是否为失物招领系统
						boolean flag = false;
						java.lang.StackTraceElement[] classArray= new Exception().getStackTrace() ;
						for(int i=0;i<classArray.length;i++) {
							String classname = classArray[i].getClassName();
							if ("com.yuzhe".equals(classname.substring(0,9))) {
								flag = true;
							}
						}
						if(flag){
							operatelogService.lostandfoundSave(pd);
						}else{
							operatelogService.save(pd);
						}
	                }
	
	                
	            }
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }


    @Before("delete()")
    public void deleteBefore(JoinPoint point) throws Exception {
       /* System.out.println("delete");
        Object[] args = point.getArgs();
        String sql = SqlUtils.getMybatisSql(point, sqlSessionFactory);
        System.out.println("mapper=\n" + args[0]);
        System.out.println("sql\n" + sql);
        Object[] params;
        String paramStr = SqlUtils.arrayToString(args);//参数解析
        System.out.println("paramStr\n" + paramStr);
        System.out.println("paramObject\n" + SqlUtils.wrapCollection(args[1]));*/

    	try{
	        Object[] args = point.getArgs();
	        PageData menu = operatelogService.getModuleName(args[0].toString().trim());
	        if (menu != null) {
	            if (!args[0].equals("OperateLogMapper.save")) {
	                PageData pd = new PageData();
	                pd.put("ID", UuidUtil.get32UUID());
	                pd.put("MAPPERNAME", args[0].toString().trim());
	                pd.put("SQL", SqlUtils.getMybatisSql(point, sqlSessionFactory));
	                JSONObject jsonObj = null;
	                List<PageData> fieldList = operatelogService.getFieldsByName(menu.getString("TABLENAME"));// 获取表字段注解
	                jsonObj = JSONObject.fromObject(args[1]);

	                if (args[0].equals("UserMapper.updateLastLogin")) {
	                    PageData user = (PageData) args[1];
	                    pd.put("OPERATEMAN", user.getString("USERNAME"));// 操作者
	                } else {
	                	if(Jurisdiction.getUsername()!=null){
	                		pd.put("OPERATEMAN", Jurisdiction.getUsername());// 操作者
	                		pd.put("systype","1");
	                	}else{
	                		if(jsonObj.get("systype")!=null){
	                			pd.put("OPERATEMAN", jsonObj.get("ZXID"));// 操作者
		                		pd.put("systype","3");
	                		}else{
	                			pd.put("OPERATEMAN", jsonObj.get("ZXID"));// 操作者
		                		pd.put("systype","2");
	                		}
	                		
	                	}
	                	pd.put("OPERATEDATE", new Date());// 时间
		                // String paramStr = SqlUtils.arrayToString(args);//参数解析
		                //  String paramStr ="";
		                String fieldAndAnnotation = null;// 字段加注解
		                String columnName = "";// 字段名
		                String columnComment = "";// 字段注释
		                String editBeforeOperate = null;
		                
		                String operate = SqlUtils.getParameterValue(args[1]);
		                String COLUMN="";
		                String COLUMN_NAME="";
		                PageData before = new PageData();
		                if (fieldList.size() > 0) {
		                    for (PageData pdata : fieldList) {
		                        fieldAndAnnotation = "";// 字段加注释
		                        //if (pdata.get("COMMENTS") != null && (operate.contains("id") || operate.contains("主键")) && (pdata.getString("COLUMN_NAME").equals("id") ||pdata.getString("COMMENTS").equals("id") || pdata.getString("COMMENTS").equals("主键"))) {
		                           // COLUMN = "id";//ID字段
		                           // COLUMN_NAME="主键";
		                       // }
		                        columnName = pdata.get("COLUMN_NAME") != null ? pdata
		                                .get("COLUMN_NAME").toString() : "";
		                        columnComment = pdata.get("COMMENTS") != null ? pdata
		                                .get("COMMENTS").toString() : "";
		                        fieldAndAnnotation =columnComment ;
		                        if (operate.toUpperCase().contains(
		                                columnName.toUpperCase() + "=")) {
		                            operate = operate.toUpperCase().replaceAll(
		                                    columnName.toUpperCase() + "=",
		                                    fieldAndAnnotation + "=");
		                        }
		                    }
		                    if (!COLUMN.equals("")) {
		                        int start = operate.indexOf("("+COLUMN_NAME+")") + 1;
		                        int index = 0;
		                        String tempStr = operate.substring(start + COLUMN_NAME.length() + 2);
		                        if (tempStr.indexOf(",") != -1) {//不是最后一个参数
		                            index = tempStr.indexOf(",");
		                        } else {//最后一个参数
		                            index = tempStr.length() - 1;
		                        }
		                        String ID = tempStr.substring(0, index).toLowerCase();
		                        PageData module = new PageData();
		                        module.put("TABLENAME", menu.getString("TABLENAME"));
		                        module.put("COLUMN", COLUMN);
		                        module.put("ID", ID);
		                        before = operatelogService.findModuleById(module);
		                        System.out.println("ID=" + ID);
		                    }
		                }
		                if (!COLUMN.equals("")) {
		                    System.out.println("删除数据:" + SqlUtils.getParameterComments(before, fieldList));
		                    pd.put("OPERATESTR", "删除数据:" + SqlUtils.getParameterComments(before, fieldList));// 请求参数
		                } else {
		                    pd.put("OPERATESTR", "执行删除:" + operate.toString());// 请求参数
		                }
		                pd.put("TYPE", "0");// 正常结束
		                pd.put("IP", IPUtil.getLocalIpv4Address());// ip
						//判断是否为失物招领系统
						boolean flag = false;
						java.lang.StackTraceElement[] classArray= new Exception().getStackTrace() ;
						for(int i=0;i<classArray.length;i++) {
							String classname = classArray[i].getClassName();
							if ("com.yuzhe".equals(classname.substring(0,9))) {
								flag = true;
							}
						}
						if(flag){
							operatelogService.lostandfoundSave(pd);
						}else{
							operatelogService.save(pd);
						}
	                }
	
	                
	            }
	        }
	    }catch(Exception e){
			e.printStackTrace();
		}

    }

    /**
     * 异常
     *
     * @param point
     * @param e
     */
//    @AfterThrowing(pointcut = "save()", throwing = "e")
    public void saveAfterThrowing(JoinPoint point, Throwable e) throws Exception {
    	try{
    		Object[] args = point.getArgs();
	        String exMsg = e.getCause().toString();
	        if (exMsg != null) {
	            if (!args[0].equals("OperateLogMapper.save")) {
	                PageData pd = new PageData();
	                pd.put("ID", UuidUtil.get32UUID());
	                pd.put("MAPPERNAME", args[0]);
	                pd.put("SQL", SqlUtils.getMybatisSql(point, sqlSessionFactory));
	                if (args[0].equals("UserMapper.updateLastLogin")) {
	                    PageData user = (PageData) args[1];
	                    pd.put("OPERATEMAN", user.getString("USERNAME"));// 操作者
	                } else {
	                    pd.put("OPERATEMAN", Jurisdiction.getUsername());// 操作者
	                }
	                pd.put("OPERATEDATE", new Date());// 时间
	                pd.put("OPERATESTR", exMsg);// 请求参数
	                pd.put("TYPE", "1");//  异常结束
	                pd.put("IP", IPUtil.getLocalIpv4Address());// ip
	
	            }
	        }
	    }catch(Exception e1){
			e.printStackTrace();
		}

    }


    /**
     * 异常
     *
     * @param point
     * @param e
     */
 //   @AfterThrowing(pointcut = "update()", throwing = "e")
    public void updateAfterThrowing(JoinPoint point, Throwable e) throws Exception {
        Object[] args = point.getArgs();
        String exMsg = e.getCause().toString();
        if (exMsg != null) {
            if (!args[0].equals("OperateLogMapper.save")) {
                PageData pd = new PageData();
                pd.put("ID", UuidUtil.get32UUID());
                pd.put("MAPPERNAME", args[0]);
                pd.put("SQL", SqlUtils.getMybatisSql(point, sqlSessionFactory));
                if (args[0].equals("UserMapper.updateLastLogin")) {
                    PageData user = (PageData) args[1];
                    pd.put("OPERATEMAN", user.getString("USERNAME"));// 操作者
                } else {
                    pd.put("OPERATEMAN", Jurisdiction.getUsername());// 操作者
                }
                pd.put("OPERATEDATE", new Date());// 时间
                pd.put("OPERATESTR", exMsg);// 请求参数
                pd.put("TYPE", "1");// 异常结束
                pd.put("IP", IPUtil.getLocalIpv4Address());// ip

            }
        }
    }


    /**
     * 异常
     *
     * @param point
     * @param e
     */
  //  @AfterThrowing(pointcut = "delete()", throwing = "e")
    public void deleteAfterThrowing(JoinPoint point, Throwable e) throws Exception {
        Object[] args = point.getArgs();
        String exMsg = e.getCause().toString();
        if (exMsg != null) {
            if (!args[0].equals("OperateLogMapper.save")) {
                PageData pd = new PageData();
                pd.put("ID", UuidUtil.get32UUID());
                pd.put("MAPPERNAME", args[0]);
                pd.put("SQL", SqlUtils.getMybatisSql(point, sqlSessionFactory));
                if (args[0].equals("UserMapper.updateLastLogin")) {
                    PageData user = (PageData) args[1];
                    pd.put("OPERATEMAN", user.getString("USERNAME"));// 操作者
                } else {
                    pd.put("OPERATEMAN", Jurisdiction.getUsername());// 操作者
                }
                pd.put("OPERATEDATE", new Date());// 时间
                pd.put("OPERATESTR", exMsg);// 请求参数
                pd.put("TYPE", "1");//  异常结束
                pd.put("IP", IPUtil.getLocalIpv4Address());// ip

            }
        }
    }

}