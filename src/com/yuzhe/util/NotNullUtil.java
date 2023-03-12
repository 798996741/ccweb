package com.yuzhe.util;

import com.fh.util.PageData;

import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-12 19:40
 **/
public class NotNullUtil {
    public static void notNull(PageData pd, String[] strName){
        for (String str : strName) {
            if(String.valueOf(pd.get(str)) == null)
                pd.put(str,"");
        }
    }
}