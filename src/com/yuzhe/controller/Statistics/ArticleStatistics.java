package com.yuzhe.controller.Statistics;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.StatisticsManager;
import com.yuzhe.service.imp.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ArticleStatistics implements CommonIntefate {

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "articleStatisticsService")
    private StatisticsManager statisticsService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject jsonData=data.getJSONObject("data");
        JSONObject result = new JSONObject();
        System.out.println("++++++++进来了");
        try{
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData pd_stoken=new PageData();
            pd_stoken.put("TOKENID",jsonData.get("tokenid"));
            PageData pdtoken=zxlbService.findByTokenId(pd_stoken);
            if(pdtoken!=null){
                if("statistics".equals(cmd)){
                    PageData pd_token=new PageData();
                    pd_token.put("StartTime",jsonData.getString("StartTime"));
                    pd_token.put("EndTime",jsonData.getString("EndTime"));
                    List<PageData> pdlist=statisticsService.statistics(pd_token);
                    List<PageData> pdlistLevel=statisticsService.FindLevel();
                    System.out.println(pdlistLevel.toString());
                    JSONArray ja=new JSONArray();
                    for (PageData pd:pdlistLevel) {
                        JSONObject jb=new JSONObject();
                        jb.put("level",pd.get("name"));
                        jb.put("count",0);
                        for (PageData pd2:pdlist) {
                            if(pd.get("name").equals(pd2.get("name"))){
                                jb.put("count",pd2.get("COUNT"));
                            }
                        }
                        ja.add(jb);
                        System.out.println(jb.toJSONString());
                    }
                    result.put("data",ja);
                    result.put("success","true");
                }else{
                    result.put("success","false");
                    result.put("msg","访问异常");
                }
            }else{
                result.put("success","false");
                result.put("msg","登录超时，请重新登录");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.put("success","false");
            result.put("msg","操作异常");
        }
        return result;
    }
}
