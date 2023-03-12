package com.yulun.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.*;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/api")
public class ExcelController extends BaseController {
    @Resource(name = "commnoService")
    private CommnoManager commnoManager;
    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name = "msgTempService")
    private MsgTempManager msgTempManager;
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;
    @Resource(name = "addressService")
    private AddressManager addressService;
    @Resource(name = "statisticsService")
    private StatisticsManager statisticsManager;

    /**
     * 导入用户信息到EXCEL
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app_readComExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readComExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        pd.put("ZXID",pd_stoken.getString("ZXID"));
        if (pd_token != null) {
            if (null != file && !file.isEmpty()) {
                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "ComExcel");                            //执行上传
                List<PageData> listPd = (List) ObjectExcelRead2.readExcel(filePath, fileName, 1, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
                System.out.println(listPd);
                /*存入数据库操作======================================*/
                for (int i = 0; i < listPd.size(); i++) {
                    pd.put("id", getUUID32());
                    pd.put("name", listPd.get(i).getString("var0"));
                    pd.put("phone", listPd.get(i).getString("var1"));
                    pd.put("age", listPd.get(i).getString("var2"));
                    String var3 = listPd.get(i).getString("var3");
                    if (var3.equals("男")) {
                        var3 = "0";
                    } else if (var3.equals("女")) {
                        var3 = "1";
                    } else {
                        var3 = "";
                    }
                    pd.put("sex", var3);
                    String var4 = listPd.get(i).getString("var4");
                    if (var4.equals("身份证")) {
                        var4 = "0";
                    } else if (var4.equals("护照")) {
                        var4 = "1";
                    } else if (var4.equals("港澳台通行证")) {
                        var4 = "2";
                    } else {
                        var4 = "";
                    }
                    pd.put("cardtype", var4);
                    pd.put("idcard", listPd.get(i).getString("var5"));
                    pd.put("address", listPd.get(i).getString("var6"));
                    commnoManager.insertCommon(pd);

                }
                json.put("success", "true");
            }

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    /**
     * 导入用户信息到EXCEL
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app_readVipExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readVipExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            pd.put("ZXID",pd_stoken.getString("ZXID"));
            if (pd_token != null) {

                if (null != file && !file.isEmpty()) {
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                    String fileName = FileUpload.fileUp(file, filePath, "VipExcel");                            //执行上传
                    List<PageData> listPd = (List) ObjectExcelRead2.readExcel(filePath, fileName, 1, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
                    /*存入数据库操作======================================*/
                    for (int i = 0; i < listPd.size(); i++) {
                        pd.put("id", getUUID32());
                        pd.put("name", listPd.get(i).getString("var1"));
                        pd.put("sex", listPd.get(i).getString("var2"));
                        pd.put("idcard", listPd.get(i).getString("var3"));
                        pd.put("recepdep", listPd.get(i).getString("var4"));
                        pd.put("clevel", listPd.get(i).getString("var5"));
                        pd.put("birthday", listPd.get(i).getString("var6"));
                        pd.put("position", listPd.get(i).getString("var7"));
                        pd.put("place", listPd.get(i).getString("var8"));
                        pd.put("waiter", listPd.get(i).getString("var9"));
                        pd.put("isimport", listPd.get(i).getString("var10"));
                        pd.put("isusecard", listPd.get(i).getString("var11"));
                        pd.put("ortherinfo", listPd.get(i).getString("var12"));
                        pd.put("ctype", listPd.get(i).getString("var13"));
                        pd.put("favorite", listPd.get(i).getString("var14"));
                        pd.put("result", listPd.get(i).getString("var15"));
                        vipInfoManager.insertVipInfo(pd);

                    }
                    json.put("success", "true");
                }
            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    @RequestMapping(value = "/downComExcel")
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void downComExcel(HttpServletResponse response) throws Exception {
        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "ComExcel.xls", "ComExcel.xls");
    }

    @RequestMapping(value = "/downVipExcel")
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void downVipExcel(HttpServletResponse response) throws Exception {
        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "VipExcel.xls", "VipExcel.xls");
    }

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }


    /**
     * 导出到Excel
     */
    @RequestMapping(value = "/app_exportComExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String name = pd.getString("name");
                String phone = pd.getString("phone");
                String idcard = pd.getString("idcard");
                String type = pd.getString("type");
                String keywords = pd.getString("keywords");
                String pageIndex = pd.getString("pageIndex");
                String pageSize = pd.getString("pageSize");
                String name1 = name == null ? "" : urlDecoder.decode(name, "utf-8");
                String phone1 = name == null ? "" : urlDecoder.decode(phone, "utf-8");
                String idcard1 = name == null ? "" : urlDecoder.decode(idcard, "utf-8");
                String type1 = name == null ? "" : urlDecoder.decode(type, "utf-8");
                String keywords1 = name == null ? "" : urlDecoder.decode(keywords, "utf-8");
                String pageIndex1 = name == null ? "" : urlDecoder.decode(pageIndex, "utf-8");
                String pageSize1 = name == null ? "" : urlDecoder.decode(pageSize, "utf-8");
                pd.put("name", name1);
                pd.put("phone", phone1);
                pd.put("idcard", idcard1);
                pd.put("type", type1);
                pd.put("keywords", keywords1);
                pd.put("pageIndex", pageIndex1);
                pd.put("pageSize", pageSize1);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = commnoManager.findAlllistPage(page);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("普客信息记录");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("客户姓名");
                titleRow.createCell((short) 1).setCellValue("客户电话");
                titleRow.createCell((short) 2).setCellValue("年龄");
                titleRow.createCell((short) 3).setCellValue("性别");
                titleRow.createCell((short) 4).setCellValue("证件类型");
                titleRow.createCell((short) 5).setCellValue("证件号码");
                titleRow.createCell((short) 6).setCellValue("所在区域");
                if (clist.size() > 0) {

                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("phone"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("age"));
                        String sex = pd1.getString("sex");
                        if (sex.equals("0")) {
                            sex = "男";
                        } else if (sex.equals("1")) {
                            sex = "女";
                        }
                        dataRow.createCell((short) 3).setCellValue(sex);
                        String cardtype = pd1.getString("cardtype");
                        if (cardtype.equals("0")) {
                            cardtype = "身份证";
                        } else if (cardtype.equals("1")) {
                            cardtype = "护照";
                        } else if (cardtype.equals("2")) {
                            cardtype = "港澳台通行证";
                        }
                        dataRow.createCell((short) 4).setCellValue(cardtype);
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("idcard"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("address"));
                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-CUSTINFO.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    //短息记录导出
    @RequestMapping(value = "/exportmsglogExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportmsglogExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                URLDecoder urlDecoder = new URLDecoder();
                String starttime = pd.getString("starttime");
                String endtime = pd.getString("endtime");
                String sendman = pd.getString("sendman");
                String phone = pd.getString("phone");
                String acceptman = pd.getString("acceptman");
                String keywords = pd.getString("keywords");
                String starttime1 = starttime == null ? "" : urlDecoder.decode(starttime, "utf-8");
                String endtime1 = endtime == null ? "" : urlDecoder.decode(endtime, "utf-8");
                String sendman1 = sendman == null ? "" : urlDecoder.decode(sendman, "utf-8");
                String phone1 = phone == null ? "" : urlDecoder.decode(phone, "utf-8");
                String acceptman1 = acceptman == null ? "" : urlDecoder.decode(acceptman, "utf-8");
                String keywords1 = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");
                pd.put("starttime", starttime1);
                pd.put("endtime", endtime1);
                pd.put("sendman", sendman1);
                pd.put("phone", phone1);
                pd.put("acceptman", acceptman1);
                pd.put("keywords", keywords1);


                PageData pageData = new PageData();
                pageData.put("id", zxid);
                List<PageData> except = noticeManager.getExcept(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users = list1.toString().substring(1, list1.toString().length() - 1);
                pd.put("userids", users);

                System.out.println(pd);

                List<PageData> clist = msgTempManager.findMsgLogAll(pd);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("短信发送记录");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("发送人");
                titleRow.createCell((short) 2).setCellValue("短信模板");
                titleRow.createCell((short) 3).setCellValue("联系方式");
                titleRow.createCell((short) 4).setCellValue("发送时间");
                titleRow.createCell((short) 5).setCellValue("发送结果");
                titleRow.createCell((short) 6).setCellValue("短信内容");
//                titleRow.createCell((short) 5).setCellValue("客户名称");
//                titleRow.createCell((short) 6).setCellValue("客户类别");

                if (clist.size() > 0) {


                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("sendman"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("tempname"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("phone"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("sendtime"));
                        String state = StringUtils.isNotEmpty(pd1.getString("state")) ? pd1.getString("state") : "";
                        if ("1".equals(state)) {
                            state = "发送成功";
                        } else if ("0".equals(state)) {
                            state = "待发送";
                        } else if ("-1".equals(state)) {
                            state = "发送失败";
                        }
                        dataRow.createCell((short) 5).setCellValue(state);
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("content"));
                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-Messagelog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }

    }

    //通讯录导出
    @RequestMapping(value = "/exportaddrlistExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportaddrlistExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();


                pd.put("name", pd.getString("name") == null ? "" : urlDecoder.decode(pd.getString("name"), "utf-8"));
                pd.put("tel1", pd.getString("tel1") == null ? "" : urlDecoder.decode(pd.getString("tel1"), "utf-8"));
                pd.put("issh", pd.getString("issh") == null ? "" : urlDecoder.decode(pd.getString("issh"), "utf-8"));

                page.setPd(pd);

                List<PageData> clist = addressService.listPage(page);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("通讯录");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("姓名");
                titleRow.createCell((short) 2).setCellValue("审核状态");
                titleRow.createCell((short) 3).setCellValue("联系电话");

                if (clist.size() > 0) {


                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("name"));
                        String sh = StringUtils.isNotEmpty(pd1.getString("issh")) ? pd1.getString("issh") : "";
                        if ("0".equals(sh)) {
                            sh = "未审核";
                        } else if ("1".equals(sh)) {
                            sh = "审核通过";
                        } else if ("2".equals(sh)) {
                            sh = "审核不通过";
                        }
                        dataRow.createCell((short) 2).setCellValue(sh);
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("tel1"));

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-AddressList.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }

    }



//操作明细
    @RequestMapping(value = "/exportzxOpeLogExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportzxOpeLogExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                PageData pageData = new PageData();
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);
                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                if (starttime==null || starttime.equals("") || "undefined".equals(starttime)) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("") || "undefined".equals(endtime)) {
                    endtime = getTime() + "-31";
                }
                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("keywords", request.getParameter("keywords"));
//                    pd.put("zxid", json.getString("zxid"));


                List<PageData> clist = statisticsManager.zxopelog(pd);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("操作明细");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("坐席工号");
                titleRow.createCell((short) 2).setCellValue("坐席姓名");
                titleRow.createCell((short) 3).setCellValue("操作类型");
                titleRow.createCell((short) 4).setCellValue("操作时间");

                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.get("zx").toString());
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("note"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("fssj"));

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxOpeLog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
    }

    //通话明细
    @RequestMapping(value = "/exportzxcalllogExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportzxcalllogExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                PageData pageData = new PageData();
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);

                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                if (starttime==null || starttime.equals("")) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("")) {
                    endtime = getTime() + "-31";
                }
                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("zxid", request.getParameter("zxid"));
                pd.put("zjhm", request.getParameter("zjhm"));
                pd.put("keywords", request.getParameter("keywords"));
                pd.put("thlx", '1');


                List<PageData> clist = statisticsManager.zxcalllog(pd);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("通话明细");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short)0).setCellValue("序号");
                titleRow.createCell((short)1).setCellValue("坐席工号");
                titleRow.createCell((short)2).setCellValue("坐席姓名");
                titleRow.createCell((short)3).setCellValue("通话方向");
                titleRow.createCell((short)4).setCellValue("开始时间");
                titleRow.createCell((short)5).setCellValue("结束时间");
                titleRow.createCell((short)6).setCellValue("主叫号码");
                titleRow.createCell((short)7).setCellValue("被叫号码");
                titleRow.createCell((short)8).setCellValue("排队时长");
                titleRow.createCell((short)9).setCellValue("振铃时长");
                titleRow.createCell((short)10).setCellValue("通话时长");
                titleRow.createCell((short)11).setCellValue("结束类型");
                titleRow.createCell((short)12).setCellValue("满意度");
                titleRow.createCell((short)13).setCellValue("事后整理时长");
                titleRow.createCell((short)14).setCellValue("通话类型");

                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.get("zxid").toString());
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 3).setCellValue(pd1.get("thfx").toString());
                        dataRow.createCell((short) 4).setCellValue(pd1.get("kssj").toString());
                        dataRow.createCell((short) 5).setCellValue(pd1.get("jssj").toString());
                        dataRow.createCell((short) 6).setCellValue(pd1.get("zjhm").toString());
                        dataRow.createCell((short) 7).setCellValue(pd1.get("bjhm").toString());
                        dataRow.createCell((short) 8).setCellValue(pd1.get("bcsj").toString());
                        dataRow.createCell((short) 9).setCellValue(pd1.get("ddsj").toString());
                        dataRow.createCell((short) 10).setCellValue(pd1.get("thsj").toString());
                        dataRow.createCell((short) 11).setCellValue(pd1.get("gjyy").toString());
                        dataRow.createCell((short) 12).setCellValue(pd1.get("khpj").toString());
                        dataRow.createCell((short) 13).setCellValue(pd1.get("shclsj").toString());
                        dataRow.createCell((short) 14).setCellValue(pd1.get("thlx").toString());

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxcalllog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
    }

    //在线客服明细
    @RequestMapping(value = "/exportserviceExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportserviceExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                PageData pageData = new PageData();
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);

                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                if (starttime==null || starttime.equals("")) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("")) {
                    endtime = getTime() + "-31";
                }
                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("zxid", request.getParameter("zxid"));
                pd.put("zjhm", request.getParameter("zjhm"));
                pd.put("thfx", request.getParameter("thfx"));
                pd.put("thlx", '2');


                List<PageData> clist = statisticsManager.zxcalllog(pd);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("通话明细");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("坐席工号");
                titleRow.createCell((short) 2).setCellValue("坐席姓名");
                titleRow.createCell((short) 3).setCellValue("通话方向");
                titleRow.createCell((short) 4).setCellValue("开始时间");
                titleRow.createCell((short) 5).setCellValue("结束时间");
                titleRow.createCell((short) 6).setCellValue("主叫号码");
                titleRow.createCell((short) 7).setCellValue("被叫号码");
                titleRow.createCell((short) 8).setCellValue("通话时长");

                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.get("zxid").toString());
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 3).setCellValue(pd1.get("thfx").toString());
                        dataRow.createCell((short) 4).setCellValue(pd1.get("kssj").toString());
                        dataRow.createCell((short) 5).setCellValue(pd1.get("jssj").toString());
                        dataRow.createCell((short) 6).setCellValue(pd1.get("zjhm").toString());
                        dataRow.createCell((short) 7).setCellValue(pd1.get("bjhm").toString());
                        dataRow.createCell((short) 8).setCellValue(pd1.get("thsj").toString());

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxcalllog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
    }

    //视频明细
    @RequestMapping(value = "/exportvideoExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportvideoExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                PageData pageData = new PageData();
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);

                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                if (starttime==null || starttime.equals("")) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("")) {
                    endtime = getTime() + "-31";
                }
                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("zxid", request.getParameter("zxid"));
                pd.put("zjhm", request.getParameter("zjhm"));
                pd.put("thfx", request.getParameter("thfx"));
                pd.put("thlx", '3');


                List<PageData> clist = statisticsManager.zxcalllog(pd);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("通话明细");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("坐席工号");
                titleRow.createCell((short) 2).setCellValue("坐席姓名");
                titleRow.createCell((short) 3).setCellValue("通话方向");
                titleRow.createCell((short) 4).setCellValue("开始时间");
                titleRow.createCell((short) 5).setCellValue("结束时间");
                titleRow.createCell((short) 6).setCellValue("主叫号码");
                titleRow.createCell((short) 7).setCellValue("被叫号码");
                titleRow.createCell((short) 8).setCellValue("通话时长");

                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.get("zxid").toString());
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 3).setCellValue(pd1.get("thfx").toString());
                        dataRow.createCell((short) 4).setCellValue(pd1.get("kssj").toString());
                        dataRow.createCell((short) 5).setCellValue(pd1.get("jssj").toString());
                        dataRow.createCell((short) 6).setCellValue(pd1.get("zjhm").toString());
                        dataRow.createCell((short) 7).setCellValue(pd1.get("bjhm").toString());
                        dataRow.createCell((short) 8).setCellValue(pd1.get("thsj").toString());

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxcalllog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
    }

    //话务量统计
    @RequestMapping(value = "/exportZXHWTJExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportZXHWTJExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                PageData pageData = new PageData();
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);
                String way = request.getParameter("way");
                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                if (starttime==null || starttime.equals("")) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("")) {
                    endtime = getTime() + "-31";
                }

                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("way", way);
                pd.put("zxid", zxid);
                page.setPd(pd);


                List<PageData> clist = statisticsManager.ZXHWTJlistPage(page);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("话务量统计");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("坐席工号");
                titleRow.createCell((short) 2).setCellValue("呼入量（次）");
                titleRow.createCell((short) 3).setCellValue("呼入接听量（次）");
                titleRow.createCell((short) 4).setCellValue("5秒短话量");
                titleRow.createCell((short) 5).setCellValue("呼入通话时长");
                titleRow.createCell((short) 6).setCellValue("外呼量（次）");
                titleRow.createCell((short) 7).setCellValue("外呼接听量（次）");
                titleRow.createCell((short) 8).setCellValue("外呼通话时长");
                titleRow.createCell((short) 9).setCellValue("平均通话时间");
                titleRow.createCell((short) 10).setCellValue("事后处理量");
                titleRow.createCell((short) 11).setCellValue("平均事后工作时间");
                titleRow.createCell((short) 12).setCellValue("平均振铃时长");

                titleRow.createCell((short) 13).setCellValue("平均保持时长");
                titleRow.createCell((short) 14).setCellValue("转移量（次）");
                titleRow.createCell((short) 15).setCellValue("咨询量（次）");
                titleRow.createCell((short) 16).setCellValue("咨询时长");

                titleRow.createCell((short) 17).setCellValue("保持量（次）");
                titleRow.createCell((short) 18).setCellValue("保持时长");
                titleRow.createCell((short) 19).setCellValue("工作总时长");
                titleRow.createCell((short) 20).setCellValue("置忙总时长");
                titleRow.createCell((short) 21).setCellValue("置忙量");
                titleRow.createCell((short) 22).setCellValue("振铃时长");
                titleRow.createCell((short) 23).setCellValue("登录时长");
                titleRow.createCell((short) 24).setCellValue("事后处理时长");

                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.get("zxid").toString());
                        dataRow.createCell((short) 2).setCellValue(pd1.get("zxhrl").toString());
                        dataRow.createCell((short) 3).setCellValue(pd1.get("zxhrjts").toString());
                        dataRow.createCell((short) 4).setCellValue(pd1.get("zxwmdhs").toString());
                        dataRow.createCell((short) 5).setCellValue(pd1.get("zxhrzsc").toString());
                        dataRow.createCell((short) 6).setCellValue(pd1.get("zxwhs").toString());
                        dataRow.createCell((short) 7).setCellValue(pd1.get("zxwhjts").toString());
                        dataRow.createCell((short) 8).setCellValue(pd1.get("zxthsc").toString());
                        dataRow.createCell((short) 9).setCellValue(pd1.get("zxpjthsc").toString());
                        dataRow.createCell((short) 10).setCellValue(pd1.get("zxshcls").toString());
                        dataRow.createCell((short) 11).setCellValue(pd1.get("zxpjshsc").toString());
                        dataRow.createCell((short) 12).setCellValue(pd1.get("pjzlsc").toString());

                        dataRow.createCell((short) 13).setCellValue(pd1.get("zxpjbcsc").toString());
                        dataRow.createCell((short) 14).setCellValue(pd1.get("zyl").toString());
                        dataRow.createCell((short) 15).setCellValue(pd1.get("zxl").toString());
                        dataRow.createCell((short) 16).setCellValue(pd1.get("zxsc").toString());
                        dataRow.createCell((short) 17).setCellValue(pd1.get("zxbcs").toString());
                        dataRow.createCell((short) 18).setCellValue(pd1.get("zxhbcsc").toString());
                        dataRow.createCell((short) 19).setCellValue(pd1.get("zxgzzsc").toString());
                        dataRow.createCell((short) 20).setCellValue(pd1.get("zmzsc").toString());
                        dataRow.createCell((short) 21).setCellValue(pd1.get("zmzb").toString());
                        dataRow.createCell((short) 22).setCellValue(pd1.get("zlsc").toString());
                        dataRow.createCell((short) 23).setCellValue(pd1.get("dlsc").toString());
                        dataRow.createCell((short) 24).setCellValue(pd1.get("zxshsc").toString());

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxcalllog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
    }

    //满意度统计
    @RequestMapping(value = "/exportMYDTJExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportMYDTJExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                PageData pageData = new PageData();
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);
                String way = request.getParameter("way");
                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                if (starttime==null || starttime.equals("")) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("")) {
                    endtime = getTime() + "-31";
                }

                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("keywords", request.getParameter("keywords"));
                pd.put("way", way);
                page.setPd(pd);


                List<PageData> clist = statisticsManager.MYDTJlistPage(page);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("满意度统计");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("坐席工号");
                titleRow.createCell((short) 2).setCellValue("坐席姓名");
                titleRow.createCell((short) 3).setCellValue("参评率");
                titleRow.createCell((short) 4).setCellValue("非常满意");
                titleRow.createCell((short) 5).setCellValue("参评非常满意率");
                titleRow.createCell((short) 6).setCellValue("整体非常满意率");
                titleRow.createCell((short) 7).setCellValue("不满意");
                titleRow.createCell((short) 8).setCellValue("无评价");
                titleRow.createCell((short) 9).setCellValue("总计");


                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.get("zxid").toString());
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 3).setCellValue(pd1.get("cpl").toString());
                        dataRow.createCell((short) 4).setCellValue(pd1.get("fcmy").toString());
                        dataRow.createCell((short) 5).setCellValue(pd1.get("cpfcmyl").toString());
                        dataRow.createCell((short) 6).setCellValue(pd1.get("ztfcmyl").toString());
                        dataRow.createCell((short) 7).setCellValue(pd1.get("bmy").toString());
                        dataRow.createCell((short) 8).setCellValue(pd1.get("wpj").toString());
                        dataRow.createCell((short) 9).setCellValue(pd1.get("zj").toString());


                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxcalllog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
    }

    //坐席话务统计
    @RequestMapping(value = "/exportXTHWTJExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportXTHWTJExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);

            if (pd_token != null) {
                String zxid = pd_token.getString("ZXID");
                PageData pd = new PageData();
                Page page = new Page();
                PageData pageData = new PageData();
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);
                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                String way = request.getParameter("way");
                if (starttime==null || starttime.equals("")) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("")) {
                    endtime = getTime() + "-31";
                }
                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("way", request.getParameter("way"));
                pd.put("keywords", request.getParameter("keywords"));
                page.setPd(pd);


                List<PageData> list = statisticsManager.XTHWTJlistPage(page);
                List<PageData> clist = changetime(starttime, endtime, way, list);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("话务统计");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("开始时间");
                titleRow.createCell((short) 2).setCellValue("IVR呼入总");
                titleRow.createCell((short) 3).setCellValue("人工呼入总计");
                titleRow.createCell((short) 4).setCellValue("人工呼入占比");
                titleRow.createCell((short) 5).setCellValue("放弃量");
                titleRow.createCell((short) 6).setCellValue("放弃率");
                titleRow.createCell((short) 7).setCellValue("接通量");
                titleRow.createCell((short) 8 ).setCellValue("接通率");
                titleRow.createCell((short) 9 ).setCellValue("15秒接通量");
                titleRow.createCell((short) 10).setCellValue("10秒放弃量");
                titleRow.createCell((short) 11).setCellValue("服务水平15秒");
                titleRow.createCell((short) 12).setCellValue("人均话务处理");
                titleRow.createCell((short) 13).setCellValue("排队时长");
                titleRow.createCell((short) 14).setCellValue("排队量");
                titleRow.createCell((short) 15).setCellValue("平均排队时长");
                titleRow.createCell((short) 16).setCellValue("振铃时长");
                titleRow.createCell((short) 17).setCellValue("振铃量");
                titleRow.createCell((short) 18).setCellValue("平均振铃时长");
                titleRow.createCell((short) 19).setCellValue("通话时长");
                titleRow.createCell((short) 20).setCellValue("平均通话时长");
                titleRow.createCell((short) 21).setCellValue("事后时间");
                titleRow.createCell((short) 22).setCellValue("平均事后时间");
                titleRow.createCell((short) 23).setCellValue("平均话务时间");
                titleRow.createCell((short) 24).setCellValue("坐席登陆时长");



                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.get("time").toString());
                        dataRow.createCell((short) 2).setCellValue(pd1.get("hrzj").toString());
                        dataRow.createCell((short) 3).setCellValue(pd1.get("rghrzj").toString());
                        dataRow.createCell((short) 4).setCellValue(pd1.get("rghjzb").toString());
                        dataRow.createCell((short) 5).setCellValue(pd1.get("fql").toString());
                        dataRow.createCell((short) 6).setCellValue(pd1.get("fqhjzb").toString());
                        dataRow.createCell((short) 7).setCellValue(pd1.get("jtl").toString());
                        dataRow.createCell((short) 8 ).setCellValue(pd1.get("jtzb").toString());
                        dataRow.createCell((short) 9 ).setCellValue(pd1.get("fifteenjtl").toString());
                        dataRow.createCell((short) 10).setCellValue(pd1.get("tenfql").toString());
                        dataRow.createCell((short) 11).setCellValue(pd1.get("fifteenjtzb").toString());
                        dataRow.createCell((short) 12).setCellValue(pd1.get("rjhwcl").toString());
                        dataRow.createCell((short) 13).setCellValue(pd1.get("pdsc").toString());
                        dataRow.createCell((short) 14).setCellValue(pd1.get("pdl").toString());
                        dataRow.createCell((short) 15).setCellValue(pd1.get("pjpdsc").toString());
                        dataRow.createCell((short) 16).setCellValue(pd1.get("zlsc").toString());
                        dataRow.createCell((short) 17).setCellValue(pd1.get("zll").toString());
                        dataRow.createCell((short) 18).setCellValue(pd1.get("pjzlsc").toString());
                        dataRow.createCell((short) 19).setCellValue(pd1.get("thsc").toString());
                        dataRow.createCell((short) 20).setCellValue(pd1.get("pjthsc").toString());
                        dataRow.createCell((short) 21).setCellValue(pd1.get("shclsj").toString());
                        dataRow.createCell((short) 22).setCellValue(pd1.get("pjshclsc").toString());
                        dataRow.createCell((short) 23).setCellValue(pd1.get("pjhwsc").toString());
                        dataRow.createCell((short) 24).setCellValue(pd1.get("zxdlsc").toString());



                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxcalllog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }
    }

    //工作量统计导出
    @RequestMapping(value = "/exportgetzxhwtjExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportgetzxhwtjExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd = new PageData();
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);

            Page page = new Page();
            if (pd_token != null) {
                PageData pageData = new PageData();
                String zxid = pd_token.getString("ZXID");
                String zxz = pd_token.getString("ZXZ");
                pageData.put("zxz",zxz);
                pageData.put("id",zxid);
                List<PageData> except = statisticsManager.getUserByZxz(pageData);
                ArrayList<String> list1 = new ArrayList<>();
                for (PageData pageData1 : except) {
                    list1.add(pageData1.getString("zxid"));
                }
                String users=list1.toString().substring(1,list1.toString().length()-1);
                pd.put("userids",users);
                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                if (starttime==null || starttime.equals("")) {
                    starttime = getTime() + "-01";
                }
                if (endtime==null || endtime.equals("")) {
                    endtime = getTime() + "-31";
                }
                pd.put("starttime",starttime);
                pd.put("endtime",endtime);
                pd.put("zxid",request.getParameter("zxid"));
                pd.put("name",request.getParameter("name"));
                String fields = request.getParameter("fields");
                System.out.println(fields);

                boolean hrl      = StringUtils.contains(fields,"hrl");
                boolean yds      = StringUtils.contains(fields,"yds");
                boolean ydl      = StringUtils.contains(fields,"ydl");
                boolean csyds    = StringUtils.contains(fields,"csyds");
                boolean jsydl    = StringUtils.contains(fields,"jsydl");
                boolean thzsc    = StringUtils.contains(fields,"thzsc");
                boolean hrzsc    = StringUtils.contains(fields,"hrzsc");
                boolean hrpjsc   = StringUtils.contains(fields,"hrpjsc");
                boolean smsc     = StringUtils.contains(fields,"smsc");
                boolean smcs     = StringUtils.contains(fields,"smcs");
                boolean kxsc     = StringUtils.contains(fields,"kxsc");
                boolean bccs     = StringUtils.contains(fields,"bccs");
                boolean bcsc     = StringUtils.contains(fields,"bcsc");
                boolean gzsc     = StringUtils.contains(fields,"gzsc");
                boolean gzztsc   = StringUtils.contains(fields,"gzztsc");
                boolean gslyl    = StringUtils.contains(fields,"gslyl");
                boolean hccs     = StringUtils.contains(fields,"hccs");
                boolean hccgcs   = StringUtils.contains(fields,"hccgcs");
                boolean hccgl    = StringUtils.contains(fields,"hccgl");
                boolean hcthsc   = StringUtils.contains(fields,"hcthsc");
                boolean hcpjthsc = StringUtils.contains(fields,"hcpjthsc");
                boolean zdhcthsc = StringUtils.contains(fields,"zdhcthsc");
                boolean hjzys    = StringUtils.contains(fields,"hjzys");
                boolean hjzycs   = StringUtils.contains(fields,"hjzycs");
                boolean dlcs     = StringUtils.contains(fields,"dlcs");
                boolean myd      = StringUtils.contains(fields,"myd");
                List<PageData> clist = statisticsManager.getzxhwtj(pd);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("工作量统计");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                int num=0;
                titleRow.createCell((short) num).setCellValue("序号");
                titleRow.createCell((short) (num+=1)).setCellValue("姓名");

                if (    !hrl &&
                        !yds &&
                        !ydl &&
                        !csyds &&
                        !jsydl &&
                        !thzsc &&
                        !hrzsc &&
                        !hrpjsc &&
                        !smsc &&
                        !smcs &&
                        !kxsc &&
                        !bccs &&
                        !bcsc &&
                        !gzsc &&
                        !gzztsc &&
                        !gslyl &&
                        !hccs &&
                        !hccgcs &&
                        !hccgl &&
                        !hcthsc &&
                        !hcpjthsc &&
                        !zdhcthsc &&
                        !hjzys &&
                        !hjzycs &&
                        !dlcs &&
                        !myd) {
                    System.out.println("全空导出");
                    titleRow.createCell((short) (num += 1)).setCellValue("呼叫数"      );
                    titleRow.createCell((short) (num += 1)).setCellValue("应答数"      );
                    titleRow.createCell((short) (num += 1)).setCellValue("应答率"      );
                    titleRow.createCell((short) (num += 1)).setCellValue("超时应答数"    );
                    titleRow.createCell((short) (num += 1)).setCellValue("应答及时率"    );
                    titleRow.createCell((short) (num += 1)).setCellValue("总通话时长"    );
                    titleRow.createCell((short) (num += 1)).setCellValue("呼入通话时长"   );
                    titleRow.createCell((short) (num += 1)).setCellValue("呼入平均通话时长");
                    titleRow.createCell((short) (num += 1)).setCellValue("示忙时长"     );
                    titleRow.createCell((short) (num += 1)).setCellValue("示忙次数"     );
                    titleRow.createCell((short) (num += 1)).setCellValue("空闲时长"     );
                    titleRow.createCell((short) (num += 1)).setCellValue("保持次数"     );
                    titleRow.createCell((short) (num += 1)).setCellValue("保持时长"     );
                    titleRow.createCell((short) (num += 1)).setCellValue("工作时长"     );
                    titleRow.createCell((short) (num += 1)).setCellValue("工作状态时长");
                    titleRow.createCell((short) (num += 1)).setCellValue("工时利用率"    );
                    titleRow.createCell((short) (num += 1)).setCellValue("呼出次数"     );
                    titleRow.createCell((short) (num += 1)).setCellValue("呼出成功数"   );
                    titleRow.createCell((short) (num += 1)).setCellValue("呼出成功率"    );
                    titleRow.createCell((short) (num += 1)).setCellValue("呼出通话时长"   );
                    titleRow.createCell((short) (num += 1)).setCellValue("呼出平均通话时长");
                    titleRow.createCell((short) (num += 1)).setCellValue("最大呼出通话时长"   );
//                    titleRow.createCell((short) (num += 1)).setCellValue("内部呼叫次数"    );
                    titleRow.createCell((short) (num += 1)).setCellValue("转移呼叫数"   );
                    titleRow.createCell((short) (num += 1)).setCellValue("登录次数"      );
                    titleRow.createCell((short) (num += 1)).setCellValue("满意度"      );
                    if (clist.size() > 0) {
                        int count = 1;
                        for (PageData pd1 : clist) {
                            int num1 = 0;
                            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                            dataRow.createCell((short) num1).setCellValue(count);
                            count++;
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("name").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hrl").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("yds").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("ydl").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("csyds").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("jsydl").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("thzsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hrzsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hrpjsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("smsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("smcs").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("kxsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("bccs").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("bcsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("gzsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("gzztsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("gslyl").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hccs").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hccgcs").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hccgl").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hcthsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hcpjthsc").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("zdhcthsc").toString());
//                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hjzys").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hjzycs").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("dlcs").toString());
                            dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("myd").toString());
                        }
                    }
                }else {
                        System.out.println("部分导出");
                        if (hrl) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼入量");
                        }
                        if (yds) {
                            titleRow.createCell((short) (num += 1)).setCellValue("应答数");
                        }
                        if (ydl) {
                            titleRow.createCell((short) (num += 1)).setCellValue("应答率");
                        }
                        if (csyds) {
                            titleRow.createCell((short) (num += 1)).setCellValue("超时应答数");
                        }
                        if (jsydl) {
                            titleRow.createCell((short) (num += 1)).setCellValue("应答及时率");
                        }
                        if (thzsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("通话总时长");
                        }
                        if (hrzsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼入通话时长");
                        }
                        if (hrpjsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼入平均通话时长");
                        }
                        if (smsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("示忙时长");
                        }
                        if (smcs) {
                            titleRow.createCell((short) (num += 1)).setCellValue("示忙次数");
                        }
                        if (kxsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("空闲时间");
                        }
                        if (bccs) {
                            titleRow.createCell((short) (num += 1)).setCellValue("保持次数");
                        }
                        if (bcsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("保持时长");
                        }
                        if (gzsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("工作时长");
                        }
                        if (gzztsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("工作状态时长");
                        }
                        if (gslyl) {
                            titleRow.createCell((short) (num += 1)).setCellValue("工时利用率");
                        }
                        if (hccs) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼出次数");
                        }
                        if (hccgcs) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼出成功数");
                        }
                        if (hccgl) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼出成功率");
                        }
                        if (hcthsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼出通话时长");
                        }
                        if (hcpjthsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼出平均通话时长");
                        }
                        if (zdhcthsc) {
                            titleRow.createCell((short) (num += 1)).setCellValue("最大通话时长");
                        }
                        if (hjzys) {
                            titleRow.createCell((short) (num += 1)).setCellValue("呼叫转移数");
                        }
//                        if (hjzycs) {
//                            titleRow.createCell((short) (num += 1)).setCellValue("内部呼叫次数");
//                        }
                        if (dlcs) {
                            titleRow.createCell((short) (num += 1)).setCellValue("登录次数");
                        }
                        if (myd) {
                            titleRow.createCell((short) (num += 1)).setCellValue("满意度");
                        }
                        if (clist.size() > 0) {
                            int count = 1;
                            for (PageData pd1 : clist) {
                                int num1 = 0;
                                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                                dataRow.createCell((short) num1).setCellValue(count);
                                count++;
                                dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.getString("name"));
                                if (hrl) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hrl").toString());
                                }
                                if (yds) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("yds").toString());
                                }
                                if (ydl) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("ydl").toString());
                                }
                                if (csyds) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("csyds").toString());
                                }
                                if (jsydl) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("jsydl").toString());
                                }
                                if (thzsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("thzsc").toString());
                                }
                                if (hrzsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hrzsc").toString());
                                }
                                if (hrpjsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hrpjsc").toString());
                                }
                                if (smsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("smsc").toString());
                                }
                                if (smcs) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("smcs").toString());
                                }
                                if (kxsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("kxsc").toString());
                                }
                                if (bccs) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("bccs").toString());
                                }
                                if (bcsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("bcsc").toString());
                                }
                                if (gzsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("gzsc").toString());
                                }
                                if (gzztsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("gzztsc").toString());
                                }
                                if (gslyl) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("gslyl").toString());
                                }
                                if (hccs) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hccs").toString());
                                }
                                if (hccgcs) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hccgcs").toString());
                                }
                                if (hccgl) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hccgl").toString());
                                }
                                if (hcthsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hcthsc").toString());
                                }
                                if (hcpjthsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hcpjthsc").toString());
                                }
                                if (zdhcthsc) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("zdhcthsc").toString());
                                }
//                                if (hjzys) {
//                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hjzys").toString());
//                                }
                                if (hjzycs) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("hjzycs").toString());
                                }
                                if (dlcs) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("dlcs").toString());
                                }
                                if (myd) {
                                    dataRow.createCell((short) (num1 += 1)).setCellValue(pd1.get("myd").toString());
                                }
                            }
                        }
                    }



                    // 设置下载时客户端Excel的名称
                    String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-gzlList.xls";
                    //设置下载的文件
                    System.out.println(filename);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", "attachment;filename=" + filename);
                    OutputStream ouputStream = response.getOutputStream();//打开流
                    wb.write(ouputStream); //在excel内写入流
                    ouputStream.flush();// 刷新流
                    ouputStream.close();// 关闭流


            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }


    }


//在线客服统计
@RequestMapping(value = "/exportzxkfExcel", produces = {"application/json;charset=UTF-8"})
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@ResponseBody()
public void exportzxkfExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
    JSONObject json = new JSONObject();
    try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        String zxid = pd_token.getString("ZXID");
        if (pd_token != null) {
            PageData pd = new PageData();
            Page page = new Page();
            PageData pageData = new PageData();
            String zxz = pd_token.getString("ZXZ");
            pageData.put("zxz",zxz);
            pageData.put("id",zxid);
            List<PageData> except = statisticsManager.getUserByZxz(pageData);
            ArrayList<String> list1 = new ArrayList<>();
            for (PageData pageData1 : except) {
                list1.add(pageData1.getString("zxid"));
            }
            String users=list1.toString().substring(1,list1.toString().length()-1);
            pd.put("userids",users);
            String starttime = request.getParameter("starttime");
            String endtime = request.getParameter("endtime");
            if (starttime==null || starttime.equals("")) {
                starttime = getTime() + "-01";
            }
            if (endtime==null || endtime.equals("")) {
                endtime = getTime() + "-31";
            }
            pd.put("userids",users);
            pd.put("starttime",starttime);
            pd.put("endtime",endtime);
            pd.put("zxid",request.getParameter("zxid"));
            pd.put("name",request.getParameter("name"));
            page.setPd(pd);


            List<PageData> clist = statisticsManager.zxkflistPage(page);
            System.out.println(clist);
            //创建excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("话务统计");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell((short) 0).setCellValue("序号");
            titleRow.createCell((short) 1).setCellValue("姓名");
            titleRow.createCell((short) 2).setCellValue("服务量");
            titleRow.createCell((short) 3).setCellValue("满意");
            titleRow.createCell((short) 4).setCellValue("服务时长");



            if (clist.size() > 0) {

                int count = 1;
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count);
                    count++;
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("name"));
                    dataRow.createCell((short) 2).setCellValue(pd1.get("fwl").toString());
                    dataRow.createCell((short) 3).setCellValue(pd1.get("myd").toString());
                    dataRow.createCell((short) 4).setCellValue(pd1.get("fwsc").toString());



                }

            }
            // 设置下载时客户端Excel的名称
            String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-zxcalllog.xls";
            //设置下载的文件
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//打开流
            wb.write(ouputStream); //在excel内写入流
            ouputStream.flush();// 刷新流
            ouputStream.close();// 关闭流

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }


    } catch (Exception e) {
        json.put("success", "false");
    }
}
        public String getTime() {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            return dateFormat.format(date);
        }

    public List<PageData> changetime(String starttime, String endtime, String way, List<PageData> list) {

        if (way.equals("day")) {
            for (PageData pageData : list) {
                pageData.put("time", starttime.substring(0, 7) + "-" + Integer.parseInt(pageData.get("time").toString()));
            }
        } else if (way.equals("month")) {
            for (PageData pageData : list) {
                pageData.put("time", starttime.substring(8, 10) + "-" + Integer.parseInt(pageData.get("time").toString()));
            }
        } else if (way.equals("week")) {

        }
        return list;
    }
}
