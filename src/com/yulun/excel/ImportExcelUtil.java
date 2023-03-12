//package com.yulun.excel;
//
//import java.awt.Color;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//
//
//
//
//
//
//
//
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.shenqz.common.Contents;
//import com.shenqz.entity.InfoVoError;
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
//
//
//public class ImportExcelUtil {
//
//    private final static String excel2003L =".xls";    //2003- 版本的excel
//    private final static String excel2007U =".xlsx";   //2007+ 版本的excel
//
//    /**
//     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
//     * @param in,fileName
//     * @return
//     * @throws IOException
//     */
//    public  List<List<Object>> getBankListByExcel(Workbook work,String fileName,Integer num,
//            HttpServletResponse response,HttpServletRequest request) throws Exception{
//        Contents.errorDownLoad = "0";
//        /** 有问题的信息列出来 */
//        List<HashMap<String, String>> errorlist = Lists.newArrayList();
//
//        List<List<Object>> list = null;
//
//        if(null == work){
//            throw new Exception("创建Excel工作薄为空！");
//        }
//        Sheet sheet = null;
//        Row row = null;
//        Cell cell = null;
//
//        list = new ArrayList<List<Object>>();
//        //遍历Excel中所有的sheet
//        for (int i = 0; i < work.getNumberOfSheets(); i++) {
//            sheet = work.getSheetAt(i);
//            if(sheet==null){continue;}
//
//            //遍历当前sheet中的所有行
//            for (int j = num; j <= sheet.getLastRowNum(); j++) {
//                row = sheet.getRow(j);
//                if(row==null||row.getFirstCellNum()==j){continue;}
//
//                //遍历所有的列
//                List<Object> li = new ArrayList<Object>();
//                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
//                    // 取出一个单元格
//                    cell = row.getCell((int) y);
//                    // 重置保存单元格内容的变量为空白
//                    String strValue = "";
//                    int v = 0;
//                    // 取出一个单元格内容
//                    if(cell != null){
//                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//                            strValue = cell.getStringCellValue().trim();
//                        }
//                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
//                            Date d=cell.getDateCellValue();
//                            DateFormat formater=new SimpleDateFormat("yyyy/MM/dd");
//                            if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)){
//                                strValue=formater.format(d);
//                            }
//                            else{
//                                strValue = String.valueOf(((Double) cell
//                                        .getNumericCellValue()).longValue());
//                                if(y==5){
//                                    try {
//                                        Date date = formater.parse("1900/01/01");
//                                        Calendar a = Calendar.getInstance();
//                                        a.setTime(date);
//                                        a.add(Calendar.DATE, (Integer.valueOf(strValue)-2));
//                                        strValue=formater.format(a.getTime());
//                                    } catch (NumberFormatException e) {
//                                        e.printStackTrace();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//                    if(y == 0){
//                        if(strValue == null){
//                            errorlist.add(setCheckErrorMap(fileName,"第",String.valueOf(j+1),"行的A列不能为空"));
//                        }else{
//                            li.add(strValue);
//                            v++;
//                        }
//                        continue;
//                    }
//                    if(y == 1){
//                        if(strValue == null){
//                            errorlist.add(setCheckErrorMap(fileName,"第",String.valueOf(j+1),"行的B列不能为空"));
//                        }else{
//                            li.add(strValue);
//                            v++;
//                        }
//                        continue;
//                    }
//                    if(y == 2){
//                        if(strValue == null){
//                            errorlist.add(setCheckErrorMap(fileName,"第",String.valueOf(j+1),"行的C列不能为空"));
//                        }else{
//                            li.add(strValue);
//                            v++;
//                        }
//                        continue;
//                    }
//                    if(y == 3){
//                        if(strValue == null){
//                            errorlist.add(setCheckErrorMap(fileName,"第",String.valueOf(j+1),"行的D列不能为空"));
//                        }else{
//                            String regex="1[0-9]{10}";
//                            Pattern p=Pattern.compile(regex);
//                            Matcher m=p.matcher(strValue);
//                            if(!m.matches()){
//                                errorlist.add(setCheckErrorMap(fileName, "第",String.valueOf(j+1),"行的D列手机号不是11位"));
//
//                            }else{
//                                li.add(strValue);
//                                v++;
//                            }
//                        }
//                        continue;
//                    }
//                    if(y == 4){
//                        li.add(strValue);
//                        continue;
//                    }
//                    if(y == 5){
//                        li.add(strValue);
//                        continue;
//                    }
//                    if(y == 6){
//                        li.add(strValue);
//                    }
//                    if(v == 4){
//                        list.add(li);
//                    }
//                }
//            }
//        }
//        if(errorlist.size() > 0){
//            Contents.errorDownLoad = "1";
//            OutputStream os = null;
//            Workbook wb = createExportContent(errorlist);
//
//            try {
//                /*wb.write(response.getOutputStream());*/
//                //设置Excel表名
//                String path = request.getServletContext().getRealPath("/error/");
//                String filename = Contents.DOWNLOAD_FILENAME + ".xlsx";
//                File filepath = new File(path,filename);
//                if (!filepath.getParentFile().exists()) {
//                    filepath.getParentFile().mkdirs();
//                  }
//                  //将上传文件保存到一个目标文件当中
//                // 设置输入流
//                FileOutputStream fOut = new FileOutputStream(path+File.separator+filename);
//                // 将模板的内容写到输出文件上
//                wb.write(fOut);
//                fOut.flush();
//                wb.close();
//                // 操作结束，关闭文件
//                fOut.close();
//                /*os = response.getOutputStream();
//                response.reset();
//
//                response.setHeader("Content-disposition", "attachment; filename = " + URLEncoder.encode(filename, "UTF-8"));
//                response.setContentType("application/octet-streem");*/
//
//                /*ByteArrayOutputStream os = new ByteArrayOutputStream();
//                try {
//                    wb.write(os);
//                ByteArray bytes = os.toByteArray();
//                response.reset();
//                response.setContentType("application/msexcel;charset=utf-8");
//                response.setHeader("Content-disposition", "attachment;filename= "+ fileName);
//
//                response.getOutputStream().write(bytes.getRawBytes());*/
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } /*finally{
//                if (null != os) {
//
//                try {
//                    os.flush();
//                    os.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                }
//                if (null != wb) {
//                    try {
//                        wb.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }*/
//        }
//        return list;
//    }
//
//    /**
//     * 描述：根据文件后缀，自适应上传文件的版本
//     * @param inStr,fileName
//     * @return
//     * @throws Exception
//     */
//    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
//        Workbook wb = null;
//        String fileType = fileName.substring(fileName.lastIndexOf("."));
//        if(excel2003L.equals(fileType)){
//            wb = (Workbook) new HSSFWorkbook(inStr);  //2003-
//        }else if(excel2007U.equals(fileType)){
//            wb = new XSSFWorkbook(inStr);  //2007+
//        }else{
//            throw new Exception("解析的文件格式有误！");
//        }
//        return wb;
//    }
//
//    /**
//     * 描述：对表格中数值进行格式化
//     * @param cell
//     * @return
//     */
//    @SuppressWarnings("deprecation")
//    public  Object getCellValue(Cell cell){
//        Object value = null;
//        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
//        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
//        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
//
//        switch (cell.getCellType()) {
//        case Cell.CELL_TYPE_STRING:
//            value = cell.getRichStringCellValue().getString();
//            break;
//        case Cell.CELL_TYPE_NUMERIC:
//            if("General".equals(cell.getCellStyle().getDataFormatString())){
//                value = df.format(cell.getNumericCellValue());
//            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
//                value = sdf.format(cell.getDateCellValue());
//            }else{
//                value = df2.format(cell.getNumericCellValue());
//            }
//            break;
//        case Cell.CELL_TYPE_BOOLEAN:
//            value = cell.getBooleanCellValue();
//            break;
//        case Cell.CELL_TYPE_BLANK:
//            value = "";
//            break;
//        default:
//            break;
//        }
//        return value;
//    }
//
//    /**
//     * 判断导入的表格标题行是否正确
//     * @return
//     * @throws Exception
//     */
//    public boolean isExistTitles(XSSFWorkbook wb, String[] titles) throws Exception{
//        //创建Excel工作薄
//        if(null == wb){
//            return false;
//        }
//        Sheet sheet = null;
//        Row row = null;
//        Cell cell = null;
//
//        //遍历Excel中所有的sheet
//        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//            sheet = wb.getSheetAt(i);
//            if(sheet==null){continue;}
//            //遍历第二行
//            row = sheet.getRow(1);
//            for (int y = 0; y < titles.length; y++) {
//                cell = row.getCell(y);
//                if(!cell.getStringCellValue().equals(titles[y])){
//                    return false;
//                }
//            }
//
//        }
//        wb.close();
//        return true;
//
//    }
//    /**
//     * 存放错误信息的map
//     * @param organname
//     * @param item
//     * @param row
//     * @param cell
//     * @param errorInfo
//     * @return
//     */
//    public static HashMap setCheckErrorMap(String filename,String item,
//              String row,String errorInfo){
//           HashMap checkmap = new HashMap();
//           checkmap.put("filename", filename);
//           checkmap.put("ITEM", item);
//           checkmap.put("ROWS", row);
//           checkmap.put("ERROR", errorInfo);
//           return checkmap;
//      }
//
//  //创建导入失败的excel文件
//      private XSSFWorkbook createExportContent(List<HashMap<String, String>> errorlist ) {
//          // 创建XSSFWorkbook
//          XSSFWorkbook wb = new XSSFWorkbook();
//
//          // 创建表单并设置cell宽度
//          XSSFSheet currentSheet = wb.createSheet("Sheet1");
//          currentSheet.setDefaultColumnWidth(20);
//
//          // 创建表头
//          createTitle(currentSheet,Contents.errorCoumns);
//
//          // 创建cellStyle
//          XSSFCellStyle style = wb.createCellStyle();
//          style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);    //设置cell底色
//          style.setFillForegroundColor(new XSSFColor(Color.red));
//
//          // 插入表内容
//          int currentRow = 1;
//          Row row = currentSheet.getRow(0);
//          Cell cell = null;
//            for(int i=0;i<errorlist.size();i++){
//              HashMap<String,String> map = errorlist.get(i);
//              int cellIndex = 0;
//                  // 行
//                  row = currentSheet.createRow(currentRow);
//                  // 列
//
//                  cell = row.createCell(cellIndex++);
//                  cell.setCellValue(map.get("filename"));
//                  cell = row.createCell(cellIndex++);
//                  String str = map.get("ITEM")+map.get("ROWS")+map.get("ERROR");
//                  cell.setCellValue(str);
//
//                  // 最后一个单元格设置样式
//                  cell.setCellStyle(style);
//                  currentRow += 1;
//          }
//          return wb;
//      }
//
//      //创建导入失败的表头
//      private void createTitle(XSSFSheet sheet, String[] titles) {
//          Row row = sheet.createRow(0);
//          for (int i = 0; i < titles.length; i++) {
//              Cell cell = row.createCell(i);
//              cell.setCellValue(titles[i]);
//          }
//      }
//
//    //校验文件头部信息是否匹配
//    private boolean checkExcelTitles(XSSFWorkbook wb, String[] titles , JSONObject result) throws Exception {
//        String title=wb.getSheetAt(0).getRow(0).getCell(0).getStringCellValue().trim();
//        if(!"机构信息表".equals(title)){
//            result.put("failMsg", "导入的不是机构信息表!");
//            return false;
//        }
//        if (!new ImportExcelUtil().isExistTitles(wb, titles)) {
//            result.put("failMsg", "请使用模版进行导入！");
//            return false;
//        } else {
//            XSSFRow row = wb.getSheetAt(0).getRow(2);
//            //判断第一行是否有数据
//            if (row == null) {
//                result.put("failMsg", "导入表格为空，请填写完信息后重新导入！");
//                return false;
//            }
//
//            //有空格的空白行也视为表格为空
//            boolean flag = false;
//            for (int i = 0; i < Contents.importInfoVoColumns.length; i++) {
//                if (row.getCell(i) != null && row.getCell(i).getStringCellValue().trim() != null) {
//                    flag = true;
//                    break;
//                }
//            }
//            if (!flag) {    // 判断第一行是否有数据
//                result.put("failMsg", "导入表格为空，请填写完信息后重新导入！");
//                return false;
//            }
//
//            //大于5000条不允许导入
//            int rowSum = wb.getSheetAt(0).getLastRowNum();
//            if (rowSum > 5000) {     // 判断数据是否大于5000条
//                result.put("failMsg", "电话信息条数超过5000条，请删减后重新导入！");
//                return false;
//            }
//            return true;
//        }
//    }
//}
