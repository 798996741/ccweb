package com.fh.util;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyUtils {
	// 配置文件
	private static String DEFAULT_PROPERTIES_FILENAME = "snkjsnew.properties";

	public static String getKeyValue(String key) {
		Properties property=null;
		property = new Properties();
		InputStream inputStream = MyUtils.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_FILENAME);
		try {
			property.load(inputStream);
			inputStream.close();
			inputStream = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return property.getProperty(key);
	}
	
	/** 
     * 根据年 月 获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(int year, int month) {  
          
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }  
    
    /**
     * 判断当前日期是星期几
     * 
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) throws Exception {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	c.setTime(format.parse(pTime));
    	int dayForWeek = 0;
    	if(c.get(Calendar.DAY_OF_WEEK) == 1){
    		dayForWeek = 7;
    	}else{
    		dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
    	}
    	return dayForWeek;
    }
    
	/**
	 * 处理null
	 * @param str
	 * @return
	 */
    
	/**
	 * 字符串非空检验
	 * @param str 待校验的字符串
	 * @return true 不为空   false 空
	 */
	public static boolean isEmpty(Object[] str) {
		return ( str==null || str.length<=0 );
	}
	 
	public static String trimNull(String str){
		return str==null?"":str;
	}
	
	public static String trimNull1(String str){
		return str==null?"-1":str;
	}
	
	public static String trim(String str){
		return str==null?"":str.replaceAll("\\s*", "");
	}
	
	/**
	 * 验证日期格式
	 * @param date
	 * @return
	 */
	public static boolean checkDate(String date) {
		String eL = "^((//d{2}(([02468][048])|([13579][26]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|([1-2][0-9])))))|(//d{2}(([02468][1235679])|([13579][01345789]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(//s(((0?[0-9])|([1][0-9])|([2][0-3]))//:([0-5]?[0-9])((//s)|(//:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean b = m.matches();
		if (b) {
			System.out.println("格式正确");
		} else {
			System.out.println("格式错误");
		}
		return b;
	}
	
	/**
	 * 按时间格式验证时间
	 * @param strDateTime
	 * @param formatStr
	 * @return
	 */
	public static boolean checkDateFormatAndValite(String strDateTime, String formatStr) {
        //update it according to your requirement.
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            Date ndate = format.parse(strDateTime);
            String str = format.format(ndate);
//            System.out.println(ndate);
//            System.out.println(str);
//            System.out.println("strDateTime=" + strDateTime);
            //success
            if (str.equals(strDateTime))
                return true;
            //datetime is not validate
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * 日期比较
	 * @param DATE1
	 * @param DATE2
	 * @param formatStr  格式化 yyyy-MM-dd
	 * @return  1为DATE1大于DATE2  -1为DATE1小于DATE2  0为相等
	 */
	public static int compare_date(String DATE1, String DATE2, String formatStr) {
		DateFormat df = new SimpleDateFormat(formatStr);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	/**
	  * 验证是不是数字(验证到小数点后一位)
	  * 
	  * @param number
	  * @return
	  */
	public static boolean isDecimalNumber(String number) {
		String regExp = "^[0-9]+(.[0-9]{0,1})?$";
		return match(regExp, number);
	}
	
	/**
	  * 执行正则表达式
	  * @param pattern表达式
	  * @param str待验证字符串
	  * @return 返回 <b>true </b>,否则为 <b>false </b>
	  */
	private static boolean match(String pattern, String str) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	/**
	 * 字符串自动左补0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
	    int strLen = str.length();
	    if (strLen < strLength) {
	        while (strLen < strLength) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(str);// 左补0
	            // sb.append(str).append("0");//右补0
	            str = sb.toString();
	            strLen = str.length();
	        }
	    }

	    return str;
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
	
	public static String frontCompWithZore(int sourceDate,int formatLength) {  
		  /* 
		   * 0 指前面补充零 
		   * formatLength 字符总长度为 formatLength 
		   * d 代表为正数。 
		   */  
		String newString = String.format("%0"+formatLength+"d", sourceDate);  
		return  newString;  
	}  
	
	/**  
     * 生成任意位数随机数  
     * @param code_len(位数)  
     * @return  
     */  
    public static String validatePwdCode(int code_len) {   
    	int count = 0;   
    	char str[] = { 
    		   			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'a', 'B', 'b', 'C', 'c',
    		   			'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k',
    		   			'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's',
    		   			'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z'
       				 };   
    	StringBuffer pwd = new StringBuffer("");   
        Random r = new Random();   
        while (count < code_len) {   
        	int i = Math.abs(r.nextInt(62));   
        	if (i >= 0 && i < str.length) {   
        		pwd.append(str[i]);   
                count++;   
            }   
        }   
        //System.out.println(str[61]);
        return pwd.toString();   
    }
	
	/**
	 * 判断字符串是否存在数组中
	 * @param stringArray
	 * @param source
	 * @return
	 */
	public static boolean contains(String[] stringArray,String source){
		//转换为list
		List<String> tempList = Arrays.asList(stringArray);
	
		//利用list的包含方法，进行判断
		if(tempList.contains(source)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String getIpAddr(HttpServletRequest request) {      
		String ip = request.getHeader("x-forwarded-for");      
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
			ip = request.getHeader("Proxy-Client-IP");      
		}      
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
			ip = request.getHeader("WL-Proxy-Client-IP");      
		}      
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
			ip = request.getRemoteAddr();      
		}      
		return ip;      
	}
	
	public static boolean purjudje(String paramString1, String paramString2){
	    if ((paramString1 == null) || (paramString2 == null)) return false;
	    String[] array = paramString1.split("#");
	    boolean flag = false;
	    for(int i=0; i<array.length; i++){
	    	if(array[i].equals(paramString2)){
	    		flag = true;
	    		break;
	    	}
	    }
	    return flag;
	}
	
	/** 
	*字符串的日期格式的计算 
	*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));     
    } 
	
	/**
	 * 长整型秒转成小时分钟秒
	 * @param lt
	 * @return
	 */
	public static String covertTime(long lt){
		String time = "";
		if(lt>0){
			long hour = lt/3600;
			lt = lt%3600;
			long min = lt/60;
			lt = lt%60;
			long second = lt;
			
			time = hour+"小时"+min+"分钟"+second+"秒";
		}
		return time;
	}
	
	/**
	 * 计算时间差
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static String getTimeDiff(String startTime, String endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
        Date d1 = null;
        Date d2 = null;
        
        if(startTime!=null&&!startTime.equals("")
        		&&endTime!=null&&!endTime.equals("")){
	        try {
				d1 = format.parse(startTime);
				d2 = format.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
				return "";
			}
	        
	        //毫秒ms
	        long diff = d2.getTime() - d1.getTime();
	
	        long diffSeconds = diff / 1000 % 60;
	        long diffMinutes = diff / (60 * 1000) % 60;
	        long diffHours = diff / (60 * 60 * 1000);
	        
	        String timeStr = "";
	        if(diffHours!=0){
	        	timeStr += diffHours + ":";
	        }
	        if(diffMinutes!=0){
	        	timeStr += diffMinutes + "'";
	        }
	        if(diffSeconds!=0){
	        	timeStr += diffSeconds + "''";
	        }
	        
	        return timeStr;
        }
        return "";
	}
	
	/**
	 * 去掉字符串中重复的串
	 */
	public static String clearDuplicateWithString(String str){
		String newStr = "";
		if(str!=null&&!str.equals("")){
			String[] arr = str.split(",");
			Set set = new TreeSet();
			for (int i = 0; i < arr.length; i++) {
				set.add(arr[i]);
			}
			arr = (String[]) set.toArray(new String[0]);
			for (int i = 0; i < arr.length; i++) {
				if(!newStr.equals("")) newStr += ",";
				newStr += arr[i];
			}
		}
		return newStr;
	}
	
	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String str) {
		str = (str == null ? "" : str); 
		String tmp; 
		StringBuffer sb = new StringBuffer(1000); 
		char c; 
		int i, j; 
		sb.setLength(0); 
		for (i = 0; i < str.length(); i++) 
		{ 
		c = str.charAt(i); 
		sb.append("\\u"); 
		j = (c >>>8); //取出高8位 
		tmp = Integer.toHexString(j); 
		if (tmp.length() == 1) 
		sb.append("0"); 
		sb.append(tmp); 
		j = (c & 0xFF); //取出低8位 
		tmp = Integer.toHexString(j); 
		if (tmp.length() == 1) 
		sb.append("0"); 
		sb.append(tmp); 

		} 
		return (new String(sb)); 
	}
	
	/**
	 * unicode转换字符串
	 */
	public static String unicode2String(String str) {
		str = (str == null ? "" : str); 
		if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回 
		return str; 

		StringBuffer sb = new StringBuffer(1000); 

		for (int i = 0; i < str.length() - 6;) 
		{ 
		String strTemp = str.substring(i, i + 6); 
		String value = strTemp.substring(2); 
		int c = 0; 
		for (int j = 0; j < value.length(); j++) 
		{ 
		char tempChar = value.charAt(j); 
		int t = 0; 
		switch (tempChar) 
		{ 
		case 'a': 
		t = 10; 
		break; 
		case 'b': 
		t = 11; 
		break; 
		case 'c': 
		t = 12; 
		break; 
		case 'd': 
		t = 13; 
		break; 
		case 'e': 
		t = 14; 
		break; 
		case 'f': 
		t = 15; 
		break; 
		default: 
		t = tempChar - 48; 
		break; 
		} 

		c += t * ((int) Math.pow(16, (value.length() - j - 1))); 
		} 
		sb.append((char) c); 
		i = i + 6; 
		} 
		return sb.toString(); 
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}    
    
    /** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
    
    public static void main(String[] args){
    
//    	String [] array1 = {"[借][短期借款]20","[借][应付股利]13","[贷][预收账款]33"};  
//        String [] array2 = {"[贷][预收账款]33","[借][短期借款]20","[借][应付股利]13"};  
//        List<String> a = Arrays.asList(array1);
//        List<String> b = Arrays.asList(array2);
//        Arrays.sort(array1);  
//        Arrays.sort(array2);  
//        if (Arrays.equals(array1, array2)) {  
//                System.out.println("两个数组中的元素值相同");  
//        } else {  
//                System.out.println("两个数组中的元素值不相同");  
//        }  
//    	System.out.println(MyUtils.string2Unicode("134999392923"));
//    	
//    	System.out.println(MyUtils.unicode2String("123456"));
    	
    	try {
    		System.out.println(MyUtils.getDaysByYearMonth(2016, 3));
			System.out.println(MyUtils.dayForWeek("2016-03-01"));
			
			int num = MyUtils.getDaysByYearMonth(2016, 3);
			int tag = MyUtils.dayForWeek("2016-03-01");
			
			if(tag==7){
				tag = 0;
			}
			
			for(int i=0; i<tag; i++){
				System.out.print("  ");
			}
			
			for(int i=1; i<=num; i++){
				tag++;
				System.out.print(String.valueOf(i)+"  ");
				if(tag%7==0){
					System.out.println("");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    
    public static String cal(int second) {
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}

		return addZeroForNum(String.valueOf(h),2) + ":" + addZeroForNum(String.valueOf(d),2) + ":" + addZeroForNum(String.valueOf(s),2) ;
	}
	
	public static String cal2(long second) {
		long h = 0;
		long d = 0;
		long s = 0;
		long temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}

		return addZeroForNum(String.valueOf(h),2) + "小时" + addZeroForNum(String.valueOf(d),2) + "分钟";
	}
	
	
	/**
	 * 将数组转为 IN(,,,,)
	 * 能处理字符串和数字
	 * @param ids
	 * @return
	 */
	public static String toInCondition(Object[] ids) {
		if(ids==null || ids.length<=0) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		buf.append("(");
		for(Object id : ids) {
			if(id instanceof Number) {
				buf.append(id).append(",");
			} else {
				buf.append("'").append(id).append("',");
			}
		}
		buf.deleteCharAt(buf.length()-1);
		buf.append(")");
		return buf.toString();
	}
	public static String getFileNameExt(String filename) {
		if(filename==null || "".equals(filename)) {
			return "";
		}
		return filename.substring(filename.lastIndexOf(".")+1);
	}
	
	public static String transferToWithHash(File source,String rootdir,String newFileName) {
    	String fileName = source.getName();
    	int hashcode = fileName.hashCode();
		int dir1 = hashcode&0xf;
		int dir2 = (hashcode&0xf0)>>4;

        
        String newDir = "";
        rootdir = rootdir.replaceAll("\\\\", "/");
        if(rootdir.endsWith("/") ) {
        	newDir = rootdir + dir1 + "/" + dir2;
        } else {
        	newDir = rootdir + "/" + dir1 + "/" + dir2;
        }
        
    	File dir = new File(newDir);
    	if(!dir.exists()) {
    		dir.mkdirs();
    	}
    	
    	newFileName = (newFileName==null||"".equals(newFileName) ) ? fileName : newFileName;
    	
    	File dest = new File(dir,newFileName);
    	source.renameTo(dest);
    	return dest.getAbsolutePath().replaceAll("\\\\", "/");
    }
	    
}
