/*
package com.yulun.controller.aodb;

import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.service.AirlineManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@EnableScheduling
public class AirlineTask {

    @Resource(name="airlineService")
    private AirlineManager airlineService;

    @Scheduled(cron = "0/10 * * * * ? ") // 间隔10分钟执行
    public void execute() throws Exception {
        PageData param=new PageData();
        List<PageData> pdlist=airlineService.listPageCache();
        System.out.println("航班数据条数-------"+pdlist.size());
        if(pdlist.size()>0){
           int n= airlineService.saveCache(pdlist);
            System.out.println("插入"+n+"条数据");
            airlineService.updIsCreate(pdlist);
        }
    }


    public static void main(String[] args) {
        System.out.println("你好呀");
    }
}
*/
