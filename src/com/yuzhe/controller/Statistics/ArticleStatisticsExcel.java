package com.yuzhe.controller.Statistics;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yuzhe.service.StatisticsManager;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/api")
public class ArticleStatisticsExcel {

    @Resource(name = "articleStatisticsService")
    private StatisticsManager statisticsService;

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @ResponseBody()
    @RequestMapping(value = "/StatisticsExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    public Object exportStatistics(HttpServletResponse response,String tokenid, String StartTime,String EndTime){
        JSONObject result = new JSONObject();
        try{
            PageData token=new PageData();
            token.put("TOKENID",tokenid) ;
            System.out.println(tokenid);
            token=zxlbService.findByTokenId(token);
            if(token!=null){
                PageData pd_token=new PageData();
                pd_token.put("StartTime",StartTime);
                System.out.println("开始时间");
                System.out.println(StartTime);
                pd_token.put("EndTime",EndTime);
                List<PageData> pdlist=statisticsService.statistics(pd_token);
                List<PageData> pdlistLevel=statisticsService.FindLevel();
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("物品统计");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("物品类型");
                titleRow.createCell((short) 2).setCellValue("数量");
                System.out.println("---------");
                if(pdlistLevel.size()>0){
                    int count = 1;
                    for (PageData pd1 : pdlistLevel){
                        JSONObject jb=new JSONObject();
                        jb.put("level",pd1.get("name"));
                        jb.put("count",0);
                        for (PageData pd2:pdlist) {
                            if(pd1.get("name").equals(pd2.get("name"))){
                                jb.put("count",pd2.get("COUNT"));
                            }
                        }
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(jb.getString("level"));
                        dataRow.createCell((short) 2).setCellValue(jb.getString("count"));
                        System.out.println(count);
                    }
                    // 设置下载时客户端Excel的名称
                    String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-report.xls";
                    //设置下载的文件
                    System.out.println(filename);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", "attachment;filename=" + filename);
                    OutputStream ouputStream = response.getOutputStream();//打开流
                    wb.write(ouputStream); //在excel内写入流
                    ouputStream.flush();// 刷新流
                    ouputStream.close();// 关闭流
                    result.put("success","true");
                }

            }else{
                result.put("success", "false");
                result.put("msg", "登录超时，请重新登录");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("success","false");
            result.put("msg","操作异常");
        }
        return result;
    }
}
