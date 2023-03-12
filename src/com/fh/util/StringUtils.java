package com.fh.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;


public class StringUtils {

	public static String getFormatDate()
			throws Exception{
		GregorianCalendar gCalendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strFormatDate;
		try{
			strFormatDate = formatter.format(gCalendar.getTime());
			strFormatDate = strFormatDate.substring(0, 10);
		}catch (Exception ex){
			System.out.println((new StringBuilder("errMsg:")).append(ex.toString()).toString());
			return null;
		}
		return strFormatDate;
	}


	public static String getFormatTime()
			throws Exception{
		GregorianCalendar gCalendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strFormatTime;
		try{
			strFormatTime = formatter.format(gCalendar.getTime());
			strFormatTime = strFormatTime.substring(11, 19);
		}catch (Exception ex){
			System.out.println((new StringBuilder("errMsg:")).append(ex.toString()).toString());
			return null;
		}
		return strFormatTime;
	}

	public static String getDateTimeMs()
			throws Exception{
		GregorianCalendar gCalendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDateTime;
		try{
			strDateTime = formatter.format(gCalendar.getTime());
		}catch (Exception ex){
			System.out.println((new StringBuilder("Error Message:")).append(ex.toString()).toString());
			return null;
		}
		return strDateTime;
	}

	public static String getStrDate(){
		String strDate = "";
		try{
			strDate = getFormatDate();
			strDate = (new StringBuilder(String.valueOf(strDate.substring(0, 4)))).append(strDate.substring(5, 7)).append(strDate.substring(8, 10)).toString();
		}catch (Exception e){
			System.out.println((new StringBuilder("Error Message of getStrDate():")).append(e.toString()).toString());
		}
		return strDate;
	}

	public static String getStrTime(){
		String strDate = "";
		try{
			strDate = getFormatTime();
			strDate = (new StringBuilder(String.valueOf(strDate.substring(0, 2)))).append(strDate.substring(3, 5)).append(strDate.substring(6, 8)).toString();
		}catch (Exception e){
			System.out.println((new StringBuilder("Error Message of getStrDate():")).append(e.toString()).toString());
		}
		return strDate;
	}

	public static String getDateTime(String strFormat){
		GregorianCalendar gCalendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
		String strDateTime;
		try{
			strDateTime = formatter.format(gCalendar.getTime());
		}catch (Exception ex){
			System.out.println((new StringBuilder("Error Message:")).append(ex.toString()).toString());
			return null;
		}
		return strDateTime;
	}

	public static String replace(String strSource, String strFrom, String strTo){
		String strDest = "";
		int intFromLen = strFrom.length();
		if (strSource.length() == 0)
			return strSource;
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) 
		{
			strDest = (new StringBuilder(String.valueOf(strDest))).append(strSource.substring(0, intPos)).toString();
			strDest = (new StringBuilder(String.valueOf(strDest))).append(strTo).toString();
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = (new StringBuilder(String.valueOf(strDest))).append(strSource).toString();
		return strDest;
	}

	public static String getUrl(String dz)
	{
		String url = "";
		Properties properties = new Properties();

		InputStream is = Tools.class.getClassLoader().getResourceAsStream("dbconfig.properties");
		try
		{
			properties.load(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		url = properties.getProperty(dz);
		return url;
	}
	

}