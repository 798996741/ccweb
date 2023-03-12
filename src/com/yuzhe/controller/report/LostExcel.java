package com.yuzhe.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.*;
import com.xxgl.service.mng.ZxlbManager;
import com.yuzhe.service.ArticleManager;
import com.yuzhe.service.DictionariesManager;
import com.yuzhe.service.LostManager;
import com.yuzhe.service.imp.ArticleService;
import com.yuzhe.service.imp.DictionariesService;
import com.yuzhe.service.imp.ImportExcelService;
import com.yuzhe.service.imp.LostService;
import com.yuzhe.util.HandleTime;
import com.yuzhe.util.IdentifierUtil;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.naming.ldap.PagedResultsControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.fh.util.DateUtil.getTime;

/**
 * @Author: Aliar
 * @Time: 2020-09-01 11:09
 **/

@Controller
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/api")
public class LostExcel extends BaseController {


    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "lostService")
    private LostManager lostManager;

    @Resource(name = "dictionService")
    private DictionariesManager dictionariesService;

    @Resource(name = "articleService")
    private ArticleManager articleService;


    //获取UUID
    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }


    @RequestMapping(value = "/readLosterExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readLosterExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = null;
        JSONObject json = new JSONObject();
        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> saveErrorList = new ArrayList<PageData>();
        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            String createMan = pd_token.getString("zxxm");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "lostListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"物品名称", "物品等级", "航班号", "物品详情", "遗失日期", "遗失地点", "报失人姓名", "报失人电话"};//设置字段名称
                String[] importFields = {"article_name", "article_level", "finder_flightNumber", "article_detail", "article_losttime", "article_lostplace", "loster_name", "loster_tel"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "4", "", "", "2", "", "", "3"};
                int[] importFiledNums = {30, 150, 30, 500, 0, 30, 30, 0}; //0表示无限制
                int[] importFiledNull = {1, 1, 1, 0, 0, 1, 1, 1}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "", "", "", "", "", "isNumberic"};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "defcd730-d87a-11ea-bd5a-fa163e003af7", "", "", "", "", "", ""};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);
                //获取能够正确导入的数据
                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));
                //修改到期时间
                pd.put("parentId","efcd730-d87a-11ea-bd5a-fa163e003af7");
                List<PageData> dictionaries = dictionariesService.findDictionaries(pd);
                System.out.println(listPd);
                for (int i = 0; i < listPd.size(); i++) {
                    pd = new PageData();
                    pd_area = new PageData();
                    //计算到期时间
                    String dayStr = "";
                    String articleLevel = listPd.get(i).getString("article_level").trim();
                    for (PageData dictionary : dictionaries) {
                        String levelName = dictionary.getString("name");
                        if (levelName.equals(articleLevel)) {
                            dayStr = dictionary.getString("remark");
                            pd.put("articleLevel", dictionary.getString("dictionariesId"));
                        }
                    }
                    boolean flag = dayStr.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
                    if (flag == true && dayStr != null && dayStr.length() > 0) {
                        Integer day = Integer.valueOf(dayStr);
                        String articleDuedate = HandleTime.getFutureTime(day);
                        pd.put("articleDuedate", articleDuedate);
                    }
                    //物品相关信息
                    pd.put("createMan", createMan);
                    pd.put("articleName", listPd.get(i).getString("article_name").trim());
                    pd.put("articleDetail", listPd.get(i).getString("article_detail").trim());
                    pd.put("articlePlace", listPd.get(i).getString("article_lostplace").trim());

                    //报失人相关信息
                    pd.put("losterName", listPd.get(i).getString("loster_name").trim());
                    pd.put("losterTel", listPd.get(i).getString("loster_tel").trim());
                    pd.put("losterplace", listPd.get(i).getString("article_lostplace").trim());
                    pd.put("losterTime", listPd.get(i).getString("article_losttime").trim());
                    pd.put("losterFlightnumber", listPd.get(i).getString("finder_flightNumber").trim());
                    saveList.add(pd);
                }
                if (saveList.size() > 0) {
                    lostManager.batchInsert(saveList);
                    articleService.batchInsert(saveList);
                }
                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
                    for (PageData pageData : errorList) {
                        pageData.put("pcbh", pcbh);
                    }
                    lostManager.insertError(errorList);
                    articleService.insertError(errorList);
                }

                        json.put("success", "true");
                        if (errorList.size() > 0) {
                            json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                            json.put("href", "api/excelLoster_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                        } else {
                            json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                        }
                    }


                } else {
                    json.put("success", "false");
                    json.put("msg", "登录超时，请重新登录");
                }
        } catch (Exception e) {
            json.put("success", "异常");
        }finally {
            return json.toString();
        }

        }


    @RequestMapping(value = "/excelLoster_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String excelLoster_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = lostManager.findLostList(page);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);
            wsheet.addCell(new Label(0, 0, "物品名称"));
            wsheet.addCell(new Label(1, 0, "物品等级"));
            wsheet.addCell(new Label(2, 0, "航班号"));
            wsheet.addCell(new Label(3, 0, "物品详情"));
            wsheet.addCell(new Label(4, 0, "遗失日期"));
            wsheet.addCell(new Label(5, 0, "遗失地点"));
            wsheet.addCell(new Label(6, 0, "报失人姓名"));
            wsheet.addCell(new Label(7, 0, "报失人电话"));
            wsheet.addCell(new Label(8, 0, "失败原因"));
            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {

                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("article_name") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("article_name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("article_name")));
                }
                if (ycstr.indexOf("article_level") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("article_level"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("article_level")));
                }
                if (ycstr.indexOf("loster_flightnumber") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("loster_flightnumber"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("loster_flightnumber")));
                }
                if (ycstr.indexOf("article_detail") >= 0) {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("article_losttime"), wcfFC));
                } else {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("article_losttime")));
                }
                if (ycstr.indexOf("article_losttime") >= 0) {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("article_losttime"), wcfFC));
                } else {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("article_losttime")));
                }
                if (ycstr.indexOf("article_lostplace") >= 0) {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("article_lostplace"), wcfFC));
                } else {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("article_lostplace")));
                }
                if (ycstr.indexOf("loster_name") >= 0) {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("loster_name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("loster_name")));
                }
                if (ycstr.indexOf("loster_tel") >= 0) {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("loster_tel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("loster_tel")));
                }
                wsheet.addCell(new Label(8, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }


}


