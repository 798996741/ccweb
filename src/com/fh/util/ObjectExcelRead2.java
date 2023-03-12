package com.fh.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 从EXCEL导入到数据库
 * 创建人：huangjianling
 * 创建时间：2014年12月23日
 * @version
 */
public class ObjectExcelRead2 {

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			HSSFWorkbook wb = new HSSFWorkbook(fi);
			HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			for (int i = startrow; i < rowNum; i++) {					//行循环开始

				PageData varpd = new PageData();
				HSSFRow row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				for (int j = startcol; j < cellNum; j++) {				//列循环开始

					HSSFCell cell = row.getCell(Short.parseShort(j + ""));
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
							case 0:
								Double value = cell.getNumericCellValue();
								cellValue = String.valueOf(value);
								if(cellValue.indexOf("E") != -1 || cellValue.indexOf("e") != -1 ||
										cellValue.indexOf("+") != -1) {
									BigDecimal big = new BigDecimal(value);
									cellValue = big.toString();
								}else {
									cellValue = String.valueOf(value.intValue());
								}
								break;
						case 1:
							cellValue = cell.getStringCellValue();
							break;
						case 2:
							cellValue = cell.getNumericCellValue() + "";
							// cellValue = String.valueOf(cell.getDateCellValue());
							break;
						case 3:
							cellValue = "";
							break;
						case 4:
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;

						}

//						else if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {// excel
//							NumberCell nc = (NumberCell) cell;
//							// 判断是否为科学计数法（包含E、e、+等符号）
//							if (("" + nc.getValue()).indexOf("E") != -1 || ("" + nc.getValue()).indexOf("e") != -1 || ("" + nc.getValue()).indexOf("+") != -1) {
//								BigDecimal bd = new BigDecimal("" + nc.getValue());
//								if (("" + nc.getValue()).indexOf("E") != -1 || ("" + nc.getValue()).indexOf("e") != -1 || ("" + nc.getValue()).indexOf("+") != -1) {
//									bd = new BigDecimal(Double.parseDouble(bd.toString()));
//									row[i] = bd.toString();
//								} else {
//									row[i] = "" + nc.getValue();
//								}
//							} else {
//								row[i] = "" + nc.getValue();
//							}
//						}


					} else {
						cellValue = "";
					}
					
					varpd.put("var"+j, cellValue);
					
				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return varList;
	}
}
