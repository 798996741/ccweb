package com.xxgl.utils;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
/**
 * 操作日志
* @author zx 
* @date  下午4:25:40
 */
public class OperateLogUtil {
	
	//用户端类型
	public  static final String BACK_END="后端";
	
	public static final String NURSE_APP="护士端App";
	
	public static final String XCX_APP="用户端App";
	
	public static final String CLIENT="客户端";
	
	public static final String NURSE_MINI_PROGRAM="护士端小程序";
	
	public static final String XCX_MINI_PROGRAM="用户端小程序";
	
	public static final String MINI_PROGRAM="小程序";
	
	public static final String MANAGEMENT="管理端";
	
	public static final String MANAGEMENT_LOGIN="管理端登录";
	
	public static final String MANAGEMENT_EDIT_PASSWORD="管理端修改密码";
	
	public static final String MANAGEMENT_FEEDBACK="管理端问题反馈";
	
	public static final String NURSE_LOGIN="护士端小程序登录";
	
	public static final String XCX_LOGIN="用户端小程序登录";
	
	public static final String MINI_PROGRAM_REFUND_BOND="小程序退押金";
	
	public static final String XCX="用户端";
	
	public static final String XCX_LOGOUT="用户端登出";
	
	public static final String XCX_BUY_MONTH_CARD="用户端购买月卡";
	
	public static final String XCX_BALANCE_BUY_MONTH_CARD="用户端余额购买月卡";
	
	public static final String XCX_REFUND_BOND="用户端退押金";
	
	public static final String XCX_PAY="用户端充值";
	
	public static final String NURSE_MINI_PROGRAM_PAY="护士端小程序充值";
	
	public static final String XCX_MINI_PROGRAM_PAY="用户端小程序充值";
	
	public static final String MY_INFO="我的资料";
	
	public static final String BED_MAINTAINED_REPLY="床位信息回复";
	
	public static final String CODE_MENU_TEMPLATE="下载模板";
	
	public static final String DOWNLOAD_EXCEL="导出excel";
	
	public static final String BATCH_PAYMENTS="批量付款";
	
	public static final String AOP_ABNORMAL="aop异常";
	
	/**
	 * 
	* @Title: OperateLogEntiy   
	* @Description: 返回OperateLog表保存的数据
	* @param @param sideType 护士端、管理端、web端
	* @param @param menuType 菜单类型
	* @param @param operateStr 内容
	* @param @param operateMan 操作者
	* @param @return      
	* @return PageData    返回类型
	 */
	public static PageData OperateLogEntiy(String sideType,String menuType,String operateStr){
		PageData pd = new PageData();
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(currentTimeMillis);
		pd.put("MENU_TYPE", menuType);//菜单类型
		pd.put("SIDE_TYPE", sideType);//护士端、管理端、web端
		pd.put("OPERATESTR", "导出："+operateStr);//内容
		pd.put("OPERATEMAN", Jurisdiction.getUsername());//操作者
		pd.put("IP", IpUtil.getIp());//操作者
		pd.put("OPERATEDATE", timestamp);//时间
		return pd;
		
	}
	
	/**
	 * 
	* @Title: packageName   
	* @Description: 获取包名  
	* @param @param className class路径
	* @param @return    设定文件   
	* @return String    返回类型
	 */
	public static String packageName(String className,String methodName){
		if(StringUtils.isNotBlank(className)){
			if(methodName.contains("ApkType")){
				return "apkType";
			}
			if("saveCallLog".equals(methodName)){
				return "callLog";
			}
			if("saveRelation".equals(methodName)){
				return "appUserApk";
			}
			String[] list=className.split("\\.");
			int length=list.length;
			String	packageName=list[length-1];
			packageName=packageName.replace("Service", "").toLowerCase();
			return packageName;
		}
		return null;
	}
	
	/**
	 * 
	* @Title: methodName   
	* @Description: 获取方法名    
	* @param @param className class路径
	* @param @return      
	* @return String    返回类型
	 */
	public static String methodName(String className){
		if(StringUtils.isNotBlank(className)){
			String[] list=className.split("\\.");
			int length=list.length;
			className=list[length-1];
			String methodName=className.replace("Service", "Mapper.findById");
			return methodName;
		}
		return null;
		
	}
	
	

}
