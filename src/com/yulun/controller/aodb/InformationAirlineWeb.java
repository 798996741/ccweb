package com.yulun.controller.aodb;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.InformationAirlineManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InformationAirlineWeb implements CommonIntefate {

    @Resource(name = "informationAirlineService")
    private InformationAirlineManager informationAirlineManager;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        PageData param = new PageData();
        try{
            JSONObject jsondata = data.getJSONObject("data");
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            param.putAll(jsondata);
            if(cmd.equals("AirlineAll")){
                Page page = new Page();
                Integer pageIndex;
                Integer pageSize;
                List<PageData> list;
                pageIndex = jsondata.getInteger("pageIndex");
                page.setCurrentPage(pageIndex);
                pageSize = jsondata.getInteger("pageSize");
                page.setShowCount(pageSize);
                page.setPd(param);
                list=informationAirlineManager.cachelistPage(page);
                if(list.size()>0){
                        for (PageData pagetdata: list
                        ) {
                            if(param.getString("locale").equals("en")){
                                pagetdata.put("startAirportName",pagetdata.getString("startAirporEnglishtName"));
                                pagetdata.put("endAirportName",pagetdata.getString("endAirportEnglishName"));
                             }
                            pagetdata.remove("startAirporEnglishtName");
                            pagetdata.remove("endAirportEnglishName");
                    }

                    result.put("success","true");
                    result.put("data",list);
                    result.put("pageId",pageIndex);
                    result.put("pageCount",page.getTotalPage());
                    result.put("recordCount",page.getTotalResult());
                }else{
                    result.put("success","false");
                    result.put("msg","暂无数据");
                }
            }else if(cmd.equals("ConsultFindById")){
                PageData pdconsult=informationAirlineManager.findById(param);
                if(param.getString("locale").equals("en")){
                    pdconsult.put("startAirportName",pdconsult.getString("startAirporEnglishtName"));
                    pdconsult.put("endAirportName",pdconsult.getString("endAirportEnglishName"));
                }
                pdconsult.remove("startAirporEnglishtName");
                pdconsult.remove("endAirportEnglishName");
                result.put("success","true");
                result.put("data",pdconsult);
            }else{
                result.put("success","false");
                result.put("msg","访问异常");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("success", "false");
            result.put("msg","操作异常");
        }
        return result;
    }
}
