//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yuzhe.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.utils.SpringContextHolder;
import com.yulun.excelCheckUtils.CheckUtils;
import com.yulun.excelCheckUtils.ExcelConst;
import com.yulun.excelCheckUtils.IdCardVerification;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportExcelService {
   DictionariesService dictionariesService = (DictionariesService) SpringContextHolder.getSpringBean("dictionService");

    public ImportExcelService() {
    }

    public JSONObject importExcel(String openFilename, HttpServletRequest request, JSONObject object_verificate) throws Exception {
        Workbook workBook = null;
        Connection conn = null;
        File file = null;
        String strSql = "";
        JSONObject result = new JSONObject();
        PageData pd = new PageData();
        List<PageData> pd_right = new ArrayList();
        List<PageData> pd_error = new ArrayList();
        new ArrayList();
        new PageData();
        new PageData();
        String ycstr = "";
        new CheckUtils();
        new IdCardVerification();

        try {
            String[] importColumns = (String[])object_verificate.get("importColumns");
            String[] importFields = (String[])object_verificate.get("importFields");
            String[] importYzColumns = (String[])object_verificate.get("importYzColumns");
            int[] importFiledNums = (int[])object_verificate.get("importFiledNums");
            int[] importFiledNull = (int[])object_verificate.get("importFiledNull");
            String[] importValueColumns = (String[])object_verificate.get("importValueColumns");
            String[] importTjColumns = (String[])object_verificate.get("importTjColumns");
            file = new File(openFilename);
            workBook = Workbook.getWorkbook(file);
            Sheet sheet = workBook.getSheet(0);
            int k = sheet.getRows();
            String cont = "";
            String ycstrs = "";
            boolean boo = false;

            for(int i = 1; i < k; ++i) {
                boo = false;
                PageData pdExcel = new PageData();
                ycstr = "";
                ycstrs = "";

                for(int m = 0; m < importColumns.length; ++m) {
                    cont = "";
                    if (sheet.getCell(m, i) != null) {
                        boo = true;
                        cont = sheet.getCell(m, i).getContents() == null ? "" : sheet.getCell(m, i).getContents();
                        cont = cont.trim();
                    }

                    if (importFiledNull[m] == 1 && cont.equals("")) {
                        if (!ycstr.equals("")) {
                            ycstr = ycstr + "#";
                            ycstrs = ycstrs + ";";
                        }

                        ycstr = ycstr + importFields[m];
                        ycstrs = ycstrs + importColumns[m] + "不能为空";
                    }

                    if (ExcelConst.YzStr(importYzColumns[m]).equals("IdCardVerification") && !cont.equals("") && !IdCardVerification.IDCardValidate(cont).equals("该身份证有效！")) {
                        if (!ycstr.equals("")) {
                            ycstr = ycstr + "#";
                            ycstrs = ycstrs + ";";
                        }

                        ycstr = ycstr + importFields[m];
                        ycstrs = ycstrs + importColumns[m] + "格式无效";
                    }

                    if (ExcelConst.YzStr(importYzColumns[m]).equals("DateVerification") && !cont.equals("") && !CheckUtils.isValidDate(cont)) {
                        if (!ycstr.equals("")) {
                            ycstr = ycstr + "#";
                            ycstrs = ycstrs + ";";
                        }

                        ycstr = ycstr + importFields[m];
                        ycstrs = ycstrs + importColumns[m] + "格式无效";
                    }

                    if (ExcelConst.YzStr(importYzColumns[m]).equals("TelVerification") && !cont.equals("") && !CheckUtils.isPhone(cont)) {
                        if (!ycstr.equals("")) {
                            ycstr = ycstr + "#";
                            ycstrs = ycstrs + ";";
                        }

                        ycstr = ycstr + importFields[m];
                        ycstrs = ycstrs + importColumns[m] + "格式无效";
                    }

                    if (ExcelConst.YzStr(importYzColumns[m]).equals("DictVerification")) {
                        if (cont.equals("")) {
                            if (!ycstr.equals("")) {
                                ycstr = ycstr + "#";
                                ycstrs = ycstrs + ";";
                            }

                            ycstr = ycstr + importFields[m];
                            ycstrs = ycstrs + importColumns[m] + "不能为空";
                        } else {
                            pd.put("NAME", cont);
                            pd.put("parentId", importValueColumns[m]);
                            PageData pdDic = this.dictionariesService.findByDictName(pd);

                            if (pdDic == null) {
                                if (!ycstr.equals("")) {
                                    ycstr = ycstr + "#";
                                    ycstrs = ycstrs + ";";
                                }

                                ycstr = ycstr + importFields[m];
                                ycstrs = ycstrs + importColumns[m] + "字典不存在";
                            }
                        }
                    }

                    if (ExcelConst.YzStr(importYzColumns[m]).equals("ValueVerification") && importValueColumns[m].indexOf(cont) < 0) {
                        if (!ycstr.equals("")) {
                            ycstr = ycstr + "#";
                            ycstrs = ycstrs + ";";
                        }

                        ycstr = ycstr + importFields[m];
                        ycstrs = ycstrs + importColumns[m] + "值不正确";
                    }

                    if (ExcelConst.YzStr(importYzColumns[m]).equals("SqlVerification") && !cont.equals("")) {
                        new ArrayList();
                        pd.put("sql", importValueColumns[m]);
                        pd.put("name", cont);
                        List<PageData> pd_sql = this.dictionariesService.listAllDictBySql(pd);
                        if (pd_sql == null || pd_sql.size() <= 0) {
                            if (!ycstr.equals("")) {
                                ycstr = ycstr + "#";
                                ycstrs = ycstrs + "#";
                            }

                            ycstr = ycstr + importFields[m];
                            ycstrs = ycstrs + importColumns[m] + "不能为空";
                        }
                    }

                    if (cont.length() > 0 && !ExcelConst.YzNum(cont.length(), importFiledNums[m])) {
                        if (!ycstr.equals("")) {
                            ycstr = ycstr + "#";
                            ycstrs = ycstrs + ";";
                        }

                        ycstr = ycstr + importFields[m];
                        ycstrs = ycstrs + importColumns[m] + "长度超出" + importFiledNums[m];
                    }

                    if (!ExcelConst.YzTj(cont, importTjColumns[m])) {
                        if (!ycstr.equals("")) {
                            ycstr = ycstr + "#";
                            ycstrs = ycstrs + ";";
                        }

                        ycstr = ycstr + importFields[m];
                        if (importTjColumns[m].equals("isNumberic")) {
                            ycstrs = ycstrs + importColumns[m] + "格式不正确，需要数字";
                        } else if (importTjColumns[m].equals("isEnglish")) {
                            ycstrs = ycstrs + importColumns[m] + "格式不正确，需要英文";
                        } else if (importTjColumns[m].equals("isChinese")) {
                            ycstrs = ycstrs + importColumns[m] + "格式不正确，需要中文";
                        }
                    }

                    pdExcel.put(importFields[m], cont);
                }

                pdExcel.put("ycstr", ycstr);
                pdExcel.put("ycstrs", ycstrs);
                if (ycstr.equals("") && boo) {
                    pd_right.add(pdExcel);
                } else if (boo) {
                    pd_error.add(pdExcel);
                }
            }

            result.put("rightList", pd_right);
            result.put("errorList", pd_error);
        } catch (Exception var32) {
            var32.printStackTrace();
        }

        return result;
    }

    private boolean checkExcelTitles(XSSFWorkbook wb, String[] titles, JSONObject result) {
        if (wb.getSheetAt(0).getRow(0) == null) {
            result.put("failMsg", "请使用模版进行导入！");
            return false;
        } else {
            XSSFRow row = wb.getSheetAt(0).getRow(1);
            if (row == null) {
                result.put("failMsg", "导入表格为空，请填写完信息后重新导入！");
                return false;
            } else {
                int rowSum = wb.getSheetAt(0).getLastRowNum();
                if (rowSum > 5000) {
                    result.put("failMsg", "电话信息条数超过5000条，请删减后重新导入！");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
}
