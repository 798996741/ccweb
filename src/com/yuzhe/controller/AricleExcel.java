package com.yuzhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yuzhe.service.ArticleManager;
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
public class AricleExcel {

    @Resource(name = "articleService")
    private ArticleManager articleService;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @ResponseBody()
    @RequestMapping(value = "/AricleExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    public Object exportAricle(HttpServletResponse response, HttpServletRequest request){
        JSONObject result = new JSONObject();
        try{
            JSONObject json= JSON.parseObject(request.getParameter("data"));
            System.out.println("数据："+json);
            PageData pd_token=new PageData();
            pd_token.putAll(json);
            PageData token=zxlbService.findByTokenId(pd_token);
            if(token!=null){

                List<PageData> listpd=articleService.findArticleList(pd_token);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("失物列表");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("物品编号");
                titleRow.createCell((short) 2).setCellValue("物品名称");
                titleRow.createCell((short) 3).setCellValue("物品详情");
                titleRow.createCell((short) 4).setCellValue("公示状态");
                titleRow.createCell((short) 5).setCellValue("大分类");
                titleRow.createCell((short) 6).setCellValue("小分类");
                titleRow.createCell((short) 7).setCellValue("拾取地点");
                titleRow.createCell((short) 8).setCellValue("拾取时间");
                titleRow.createCell((short) 9).setCellValue("物品等级");
                titleRow.createCell((short) 10).setCellValue("物品状态");
                titleRow.createCell((short) 11).setCellValue("拾取人姓名");
                titleRow.createCell((short) 12).setCellValue("拾取人电话");
                titleRow.createCell((short) 13).setCellValue("到期时间");
                titleRow.createCell((short) 14).setCellValue("航班号");
                titleRow.createCell((short) 15).setCellValue("记录人");
                titleRow.createCell((short) 16).setCellValue("创建时间");
                titleRow.createCell((short) 17).setCellValue("修改时间");
                if(listpd.size()>0){
                    int count = 1;
                    for (PageData pd1 : listpd){
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("articleIdentifier"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("articleName"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("articleDetail"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("isPublicName"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("typeName"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("subTypeName"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("finderPlace"));
                        dataRow.createCell((short) 8).setCellValue(pd1.getString("finderTime"));
                        dataRow.createCell((short) 9).setCellValue(pd1.getString("articleLevelName"));
                        dataRow.createCell((short) 10).setCellValue(pd1.getString("articleCliamName"));
                        dataRow.createCell((short) 11).setCellValue(pd1.getString("finderName"));
                        dataRow.createCell((short) 12).setCellValue(pd1.getString("finderTel"));
                        dataRow.createCell((short) 13).setCellValue(pd1.getString("articleDuedate"));
                        dataRow.createCell((short) 14).setCellValue(pd1.getString("finderFlightNumber"));
                        dataRow.createCell((short) 15).setCellValue(pd1.getString("createMan"));
                        dataRow.createCell((short) 16).setCellValue(pd1.getString("createTime"));
                        dataRow.createCell((short) 17).setCellValue(pd1.getString("updateTime"));
                    }
                    // 设置下载时客户端Excel的名称
                    String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-aricle.xls";
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
