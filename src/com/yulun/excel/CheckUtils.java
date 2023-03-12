package com.yulun.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {

	public static boolean isValidDate(String str) {
		boolean convertSuccess=true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess=false;
		} 
		return convertSuccess;
	}

	/**
     * 判断是否为空字符串最优代码
     * @param str
     * @return 如果为空，则返回true
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否非空
     * @param str 如果不为空，则返回true
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

	
    public static boolean isPhone(String phone){
    	String regex = "^((13[0-9])|(14[0-9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$";
    	boolean convertSuccess=false;
    	if(phone!=null&&!phone.equals("")&&phone.length()==11){
    		Pattern p = Pattern.compile(regex);
        	Matcher m = p.matcher(phone);  //registrant_phone  ====  电话号码字段
        	boolean isMatch = m.matches();
        	if (!isMatch) {
        		convertSuccess=false;
        	}else{
        		
        		convertSuccess=true;
        	}
    	}
    	
    	return convertSuccess;
    }
    

	public static void main(String[] args) throws ParseException {
		// 从控制端输入用户身份证

		CheckUtils checkUtils=new CheckUtils();
		System.out.println(CheckUtils.isValidDate("1993-09-12"));
	}

}
