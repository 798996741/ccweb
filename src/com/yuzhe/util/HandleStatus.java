package com.yuzhe.util;

import com.fh.util.PageData;
import com.yuzhe.service.DictionariesManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-11-16 16:44
 **/
public class HandleStatus {
    //用于转换物品状态
    public  static String handleStatus(String status) throws Exception {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        DictionariesManager dictionService = (DictionariesManager) wac.getBean("dictionService");
        //获取本地数据库中所有物品状态
        PageData pd = new PageData();
        pd.put("parentId","a5650d52-d879-11ea-bd5a-fa163e003af7");
        List<PageData> dictionaries = dictionService.findDictionaries(pd);
        for (PageData dictionary : dictionaries) {
            if(dictionary.containsValue(status)){
                return String.valueOf(dictionary.get("remark"));
            }
        }
        return "1";
    }
}