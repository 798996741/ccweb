package com.yuzhe.util;

import com.fh.entity.system.Data;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Aliar
 * @Time: 2020-08-12 20:15
 **/
public class HandleTime {

    public static String getFutureTime(int day){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar1.add(Calendar.DATE, day);
        String result_days = sdf1.format(calendar1.getTime());
        return result_days;
    }



    public static String getFutureTime(String date, int day) throws ParseException {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf1.parse(date);
        calendar1.setTime(parse);
        calendar1.add(Calendar.DATE, day);
        String result_days = sdf1.format(calendar1.getTime());
        return result_days;
    }

    public static String getDate(){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.format(new Date());
        String result_days = sdf1.format(calendar1.getTime());
        return result_days;
    }


    public static void main(String[] args) {

        String str = "hb-liu3,wang-meng,hd-li,yaoyao-ren,sun-wei";
        String[] split = str.split(",");
        for (String s : split) {
            System.out.println(new SimpleHash("SHA-1", s,"MLccweb@2020" ).toString());
        }


    }
}