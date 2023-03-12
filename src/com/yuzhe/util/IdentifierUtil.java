package com.yuzhe.util;


import com.fh.util.PageData;
import com.yuzhe.service.ArticleManager;
import com.yuzhe.service.DictionariesManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: Aliar
 * @Time: 2020-08-12 17:17
 **/


public class IdentifierUtil {
    public  String getIdentifier() throws Exception {
        return "HAK" + System.currentTimeMillis();
    }

    public  String getIdentifier(PageData pd) throws Exception {

        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        ArticleManager articleService = (ArticleManager) wac.getBean("articleService");
        DictionariesManager dictionService = (DictionariesManager) wac.getBean("dictionService");
        int articleTodayCount = articleService.articleTodayCount(pd);
        String articleTodayCountStr = "";
        articleTodayCount++;
        if(articleTodayCount < 10){
            articleTodayCountStr = "00" + articleTodayCount;
        }else if(articleTodayCount < 100){
            articleTodayCountStr = "0" + articleTodayCount;
        }else{
            articleTodayCountStr = "" + articleTodayCount;
        }
        PageData npd = new PageData();
        npd.put("articleLevel", String.valueOf(pd.get("receivePlace")));
        String counterNumberStr = "00";
        PageData counterNumber = dictionService.findObjByName(npd);
        if(counterNumber != null) {
            counterNumberStr = counterNumber.getString("remark");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String day = sdf.format(new Date());
        day = day.substring(1);
        return counterNumberStr + day + articleTodayCountStr;
    }
}
