package com.yuzhe.controller.MsgAdmin;

import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yuzhe.service.MsgTempManager;
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
public class MsgLogExcel  extends BaseController {

    @Resource(name = "yuzhemsgtempservice")
    private MsgTempManager msgTempService;

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    /**
     * 短信历史记录导出
     * @param response
     * @param request
     * @return
     * @throws Exception
     */

    @ResponseBody()
    @RequestMapping(value = "/MsgLogExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    public Object exportMsgLog(HttpServletResponse response, HttpServletRequest request) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject result = new JSONObject();
        try{
            PageData token=new PageData();
            token.put("TOKENID",request.getParameter("tokenid")) ;
            token=zxlbService.findByTokenId(token);
            if(token!=null){
                PageData pd_token=new PageData();
                pd_token.put("StartTime",request.getParameter("StartTime"));
                pd_token.put("EndTime",request.getParameter("EndTime"));
                pd_token.put("phone",request.getParameter("phone"));
                pd_token.put("keywords",request.getParameter("keywords"));
                List<PageData> listpd=msgTempService.MsgLogList(pd_token);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("短信历史记录");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("发送人");
                titleRow.createCell((short) 2).setCellValue("短信模板");
                titleRow.createCell((short) 3).setCellValue("联系电话");
                titleRow.createCell((short) 4).setCellValue("发送时间");
                titleRow.createCell((short) 5).setCellValue("发送状态");
                titleRow.createCell((short) 6).setCellValue("短信内容");
                if(listpd.size()>0){
                    int count = 1;
                    for (PageData pd1 : listpd){
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("tempname"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("phone"));
                        dataRow.createCell((short) 4).setCellValue(simpleDateFormat.format(pd1.get("sendtime")));
                        dataRow.createCell((short) 5).setCellValue("1".equals(pd1.getString("state"))?"发送成功":"发送失败");
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("content"));
                    }
                    // 设置下载时客户端Excel的名称
                    String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-msglog.xls";
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
