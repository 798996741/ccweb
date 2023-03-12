package com.yuzhe.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yuzhe.service.LostManager;
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
public class ReportExcel {

    @Resource(name="lostService")
    private LostManager lostManager;

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @ResponseBody()
    @RequestMapping(value = "/ReportExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    public Object exportReport(HttpServletResponse response, HttpServletRequest request){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject result = new JSONObject();
        try{
            PageData token=new PageData();
            token.put("TOKENID",request.getParameter("tokenid")) ;
            token=zxlbService.findByTokenId(token);
            if(token!=null){
                PageData pd_token=new PageData();
                pd_token.put("losterName",request.getParameter("losterName"));
                pd_token.put("lostPlace",request.getParameter("lostPlace"));
                pd_token.put("StartTime",request.getParameter("StartTime"));
                pd_token.put("EndTime",request.getParameter("EndTime"));
                pd_token.put("articleCliam",request.getParameter("articleCliam"));
                pd_token.put("keywords",request.getParameter("keywords"));
                List<PageData> listpd=lostManager.reportList(pd_token);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("物品报失");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("报失单号");
                titleRow.createCell((short) 2).setCellValue("物品名称");
                titleRow.createCell((short) 3).setCellValue("物品详情");
                titleRow.createCell((short) 4).setCellValue("丢失地点");
                titleRow.createCell((short) 5).setCellValue("丢失时间");
                titleRow.createCell((short) 6).setCellValue("物品等级");
                titleRow.createCell((short) 7).setCellValue("物品状态");
                titleRow.createCell((short) 8).setCellValue("报失人姓名");
                titleRow.createCell((short) 9).setCellValue("报失人电话");
                titleRow.createCell((short) 10).setCellValue("航班号");
                titleRow.createCell((short) 11).setCellValue("记录人");
                titleRow.createCell((short) 12).setCellValue("创建时间");
                titleRow.createCell((short) 13).setCellValue("修改时间");
                if(listpd.size()>0){
                    int count = 1;
                    for (PageData pd1 : listpd){
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("article_identifier"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("article_name"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("article_detail"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("article_lostplace"));
                        dataRow.createCell((short) 5).setCellValue(simpleDateFormat.format(pd1.get("article_losttime")));
                        PageData p=new PageData();
                        p.put("dictionariesId",pd1.get("article_level"));
                        p=lostManager.getDictionariesNameById(p);
                        dataRow.createCell((short) 6).setCellValue(p.getString("name"));
                        p.put("dictionariesId",pd1.get("article_cliam"));
                        p=lostManager.getDictionariesNameById(p);
                        dataRow.createCell((short) 7).setCellValue(p.getString("name"));
                        dataRow.createCell((short) 8).setCellValue(pd1.getString("loster_name"));
                        dataRow.createCell((short) 9).setCellValue(pd1.getString("loster_tel"));
                        dataRow.createCell((short) 10).setCellValue(pd1.getString("loster_flightnumber"));
                        dataRow.createCell((short) 11).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 12).setCellValue(simpleDateFormat.format(pd1.get("create_time")));
                        dataRow.createCell((short) 13).setCellValue(pd1.get("update_time")==null?"":simpleDateFormat.format(pd1.get("update_time")));
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
