package com.fh.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** 
 * 说明：
 * 创建人：huangjianling
 * 修改时间：2015年11月24日
 * @version
 */
public class Constants {
	public static String PICTURE_VISIT_FILE_PATH = "";//图片访问的路径
	
	public static String VISIT_FILE_PATH = "";//图片访问的路径
	
	
	public static String PICTURE_SAVE_FILE_PATH = "";//图片存放的路径
	/**
	 * 网站路径
	 */
	public static String VISIT_WEB_PATH=""; 
	
	public Constants(){
		Properties pros = getPprVue();
		PICTURE_VISIT_FILE_PATH= pros.getProperty("PICTURE_VISIT_FILE_PATH");	//地址
		VISIT_FILE_PATH= pros.getProperty("VISIT_FILE_PATH");
		//System.out.println(VISIT_FILE_PATH+"VISIT_FILE_PATH");
		PICTURE_SAVE_FILE_PATH= pros.getProperty("PICTURE_SAVE_FILE_PATH");
		VISIT_WEB_PATH= pros.getProperty("VISIT_WEB_PATH");
	}
	public Properties getPprVue(){
		InputStream inputStream = DbFH.class.getClassLoader().getResourceAsStream("dbconfig.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			//读取配置文件出错
			e.printStackTrace();
		}
		return p;
	}
	
	//public static String PICTURE_VISIT_FILE_PATH = "http://127.0.0.1:8080/ccweb/appupload/picReq?imgFileName=";//图片访问的路径
	
	public static String getPICTURE_VISIT_FILE_PATH() {
		return PICTURE_VISIT_FILE_PATH;
	}

	public static void setPICTURE_VISIT_FILE_PATH(String pICTURE_VISIT_FILE_PATH) {
		PICTURE_VISIT_FILE_PATH = pICTURE_VISIT_FILE_PATH;
	}

	public static String getPICTURE_SAVE_FILE_PATH() {
		return PICTURE_SAVE_FILE_PATH;
	}

	public static void setPICTURE_SAVE_FILE_PATH(String pICTURE_SAVE_FILE_PATH) {
		PICTURE_SAVE_FILE_PATH = pICTURE_SAVE_FILE_PATH;
	}

	public static void main(String[] args) {
		Constants.setPICTURE_SAVE_FILE_PATH("D:/Tomcat 6.0/webapps/FH/topic/");
		Constants.setPICTURE_VISIT_FILE_PATH("http://192.168.1.225:8888/FH/topic/");
	}
	
}
