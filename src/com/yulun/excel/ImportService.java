//package com.yulun.excel;
//
//import java.awt.Color;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
////import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.multipart.MultipartFile;
//
//import sn.ylxt.common.JdbcUtil;
//import sn.ylxt.common.ResultInfo;
//import com.yulun.excel.CheckUtils;
//import com.yulun.excel.IdCardVerification;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fh.service.system.dictionaries.DictionariesManager;
//import com.fh.util.PageData;
//
//public class ImportService {
//
//	@Resource(name="dictionariesService")
//	private DictionariesManager dictionariesService;
//	//电话号码为空
//	private static final String IMPORT_NO_CONTENT = "电话号码为空";
//	//电话号已存在
//	private static final String IMPORT_EXISTED_DATA = "电话号码已存在";
//	//无效信息(手机号不满足11位、手机号不为阿拉伯数字)
//	private static final String IMPORT_INVALID_DATA = "电话号码必须为11位阿拉伯数字";
//
//	/**导入的定制用户的列*/
//	private static final String[] importCustUserColumns = {"电话", "姓名", "备注"};
//	/**导出的定制用户的列*/
//	private static final String[] exportCustUserColumns = {"电话", "姓名", "备注", "失败原因"};
//
//
//	@Override
//	public JSONObject ImportCustUser(MultipartFile excelFile, String brandCode, String loginName) throws Exception {
//	    //LOG.info("进入导入用户信息:brandCode={}", brandCode);
//	    // 获取解析excel的wb
//	    XSSFWorkbook wb = new XSSFWorkbook(excelFile.getInputStream());
//	    // 返回json
//	    JSONObject result = new JSONObject();
//
//	    // 校验文件头部信息是否匹配
//	    if (checkExcelTitles(wb,importCustUserColumns,result)) {
//
//	        // 校验文件内容是否有错误内容
//	        Map<String, Object> map = checkExcel(wb, brandCode, loginName);
//
//	        //分别获取正确和错误的的用户对象list
//	        @SuppressWarnings("unchecked")
//	        List<PageData> importCustUserList = (List<PageData>) map.get("importCustUserList");
//
//	        @SuppressWarnings("unchecked")
//	        List<PageData> errorUserList = (List<PageData>) map.get("errorUserList");
//
//	        // 不为空则批量导入
//	        if (!CollectionUtil.isEmpty(importCustUserList)) {
//	           // PageDataService.importUserInfo(importCustUserList);
//	            result.put("successCount", importCustUserList.size());    //成功导入的条数
//	        } else {
//	            result.put("successCount", 0);
//	        }
//
//	        // 不为空则创建导入失败的文件
//	        if (!CollectionUtil.isEmpty(errorUserList)) {
//	            //创建导入失败的excel对象
//	            XSSFWorkbook errorWb = createExportContent(errorUserList);
//	            //创建字节流
//	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	            //将excel对象写入字节流
//	            errorWb.write(bos);
//
//	            //设置文件后缀，将文件上传至服务器并返回链接（有待学习！！这里用的公司写好的工具类）
//	            String suffix = ".xlsx";
//	            String errorUrl = fileUploadService.toUpFile(bos.toByteArray(), FileUpLoadEnum.file, suffix);
//
//	            result.put("errorCount", errorUserList.size());    //导入失败的条数
//	            result.put("errorUrl", errorUrl);                  //下载链接
//
//	        } else {
//	            result.put("errorCount", 0);
//	        }
//	        result.put("result", "success");
//	    } else {
//	        result.put("result", "failed");
//	    }
//	    return result;
//	}
//
//	/*
//	 * importColumns 字段名称
//	 * importFields 字段
//	 * importYzColumns 字段参数验证规则
//	 *
//	 */
//	public JSONObject importExcel(String openFilename, HttpServletRequest request, String czman,String pcbh,String[] importColumns,String[] importFields,String[] importYzColumns) throws Exception{
//		Workbook workBook = null;
//		Connection conn = null;
//		File file = null;
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs1 = null;
//		PreparedStatement pstmt1 = null;
//		String strSql = "";
//		int k = 0;
//		boolean flag = false;
//		int tmpint = 0,count=0,allcount=0,punish_jls=0;
//		int tmpint2 = 0;
//		JSONObject result = new JSONObject();
//
//		String ycstr="";
//		CheckUtils checkUtils=new CheckUtils();
//		//System.out.println(checkUtils.isValidDate("1993-09-12"));
//		IdCardVerification idCardVerification=new IdCardVerification();
//
//		try
//		{
//			//Date d = new Date();
//			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			//String pcbh=sdf.format(d);
//
//			file = new File(openFilename);
//			workBook = Workbook.getWorkbook(file);
//			Sheet sheet = workBook.getSheet(0);
//			k = sheet.getRows();
//
//
//			//String[] columns=importColumns
//
//			boolean boo = false;
//			for (int i = 1; i < k; i++)
//			{
//				boo = false;
//
//				for(int m=0;m<importColumns.length;m++){
//					sheet.getCell(m, i).getContents();
//					if(importYzColumns[m].equals("notnull")){
//						if(sheet.getCell(m, i).getContents()==null||sheet.getCell(m, i).getContents().equals("")){
//							if(!ycstr.equals("")){ycstr=ycstr+"#";}
//							ycstr=ycstr+importFields[m];
//						}
//					}
//
//					if(importYzColumns[m].equals("IdCardVerification")){
//						if(sheet.getCell(m, i).getContents()!=null&&!sheet.getCell(m, i).getContents().equals("")&&!IdCardVerification.IDCardValidate(sheet.getCell(m, i).getContents()).equals("该身份证有效！")){
//							if(!ycstr.equals("")){ycstr=ycstr+"#";}
//							ycstr=ycstr+importFields[m];
//						}
//					}
//
//					if(importYzColumns[m].equals("DateVerification")){
//						if(sheet.getCell(m, i).getContents()!=null&&!sheet.getCell(m, i).getContents().equals("")&&!CheckUtils.isValidDate(sheet.getCell(m, i).getContents())){
//							if(!ycstr.equals("")){ycstr=ycstr+"#";}
//							ycstr=ycstr+importFields[m];
//						}
//					}
//
//					if(importYzColumns[m].equals("TelVerification")){//电话验证
//						if(sheet.getCell(m, i).getContents()!=null&&!sheet.getCell(m, i).getContents().equals("")&&!CheckUtils.isPhone(sheet.getCell(m, i).getContents())){
//							if(!ycstr.equals("")){ycstr=ycstr+"#";}
//							ycstr=ycstr+importFields[m];
//						}
//					}
//
//					if(importYzColumns[m].equals("DictVerification")){//字典验证
//						if(sheet.getCell(m, i).getContents()!=null&&!sheet.getCell(m, i).getContents().equals("")&&!CheckUtils.isPhone(sheet.getCell(m, i).getContents())){
//							dictionariesService.findByName(pd);
//						}
//					}
//
//				}
//
//                jxl.Cell Iarea = sheet.getCell(1, i);
//				jxl.Cell Iname =sheet.getCell(2, i);
//				jxl.Cell Isex =sheet.getCell(3, i);
//				jxl.Cell Icardid =sheet.getCell(4, i);
//				jxl.Cell Ibirthdate =sheet.getCell(5, i);
//				jxl.Cell Ibydate=sheet.getCell(9, i);
//				jxl.Cell Ixl=sheet.getCell(8, i);
//				jxl.Cell Ihkszd=sheet.getCell(6, i);
//				jxl.Cell Iaddr=sheet.getCell(7, i);
//				jxl.Cell Iiszs=sheet.getCell(10, i);
//				jxl.Cell Izsdate=sheet.getCell(11, i);
//				jxl.Cell Izslevel=sheet.getCell(12, i);
//				jxl.Cell Iisjob=sheet.getCell(13, i);
//				jxl.Cell Ijobtype=sheet.getCell(14, i);
//				jxl.Cell Irytype=sheet.getCell(15, i);
//				jxl.Cell Icbqk=sheet.getCell(16, i);
//				jxl.Cell Iisbx=sheet.getCell(17, i);
//				jxl.Cell Ibxnx=sheet.getCell(18, i);
//				jxl.Cell Icydate=sheet.getCell(19, i);
//				jxl.Cell Izyrs=sheet.getCell(20, i);
//				jxl.Cell Iqyaddr=sheet.getCell(21, i);
//				jxl.Cell Iisqyjnbx=sheet.getCell(22, i);
//				jxl.Cell Iqyname=sheet.getCell(23, i);
//				jxl.Cell Iqytel=sheet.getCell(24, i);
//				jxl.Cell Irzdate=sheet.getCell(25, i);
//				jxl.Cell Ijnbx=sheet.getCell(26, i);
//				jxl.Cell Icjtype=sheet.getCell(27, i);
//				jxl.Cell Iendpoint_id=sheet.getCell(28, i);
//				jxl.Cell Itel=sheet.getCell(29, i);
//
//
////				area = "";name = "";sex = "";cardid = "";birthdate = "";
////				bydate="";xl="";hkszd="";addr="";iszs="";zsdate="";zslevel="";isjob="";jobtype="";
////				rytype="";cbqk="";isbx="";bxnx="";cydate="";zyrs="";qyaddr="";isqyjnbx="";
////				qyname="";qytel="";rzdate="";jnbx="";cjtype="";endpoint_id="";cbqkname="";tel="";
//
//
//
//				String area = Iarea.getContents();
//				String name = Iname.getContents();
//				String sex = Isex.getContents();
//				String cardid = Icardid.getContents();
//				String birthdate = Ibirthdate.getContents();
//				String bydate=Ibydate.getContents();
//				String xl=Ixl.getContents();
//				String hkszd=Ihkszd.getContents();
//				String addr=Iaddr.getContents();
//				String iszs=Iiszs.getContents();
//				String zsdate=Izsdate.getContents();
//				String zslevel=Izslevel.getContents();
//				String isjob=Iisjob.getContents();
//				String jobtype=Ijobtype.getContents();
//				String rytype=Irytype.getContents();
//				String cbqk=Icbqk.getContents();
//				String isbx=Iisbx.getContents();
//				String bxnx=Ibxnx.getContents();
//				String cydate=Icydate.getContents();
//				String zyrs=Izyrs.getContents();
//				String qyaddr=Iqyaddr.getContents();
//				String isqyjnbx=Iisqyjnbx.getContents();
//				String qyname=Iqyname.getContents();
//				String qytel=Iqytel.getContents();
//				String rzdate=Irzdate.getContents();
//				String jnbx=Ijnbx.getContents();
//				String cjtype=Icjtype.getContents();
//				String endpoint_id=Iendpoint_id.getContents();
//				String tel=Itel.getContents();
//
//
//
//
//				if ((cardid != null) && (!cardid.equals("")))
//				{
//					boo = false;
//					strSql = "select * from t_sys_dict where dict_name=?  and dict_type='xl' ";
//					pstmt = conn.prepareStatement(strSql);
//					pstmt.setString(1, xl);
//					rs = pstmt.executeQuery();
//					if ((rs != null) && (rs.next())) {
//						xl=rs.getString("dict_id");
//					}else{
//						if(!xl.equals("")){
//							ycstr=ycstr+"#xl";
//						}
//					}
//
//
//
//					if (rs != null){rs.close();rs = null;}
//					if (pstmt != null){pstmt.close();pstmt = null;}
//
//					strSql = "select * from t_dwgl where dwname=? ";
//					pstmt = conn.prepareStatement(strSql);
//					pstmt.setString(1, area);
//					rs = pstmt.executeQuery();
//					if ((rs != null) && (rs.next())) {
//						area=rs.getString("dwbm");
//					}else{
//						if(!area.equals("")){
//							ycstr=ycstr+"#area";
//						}
//					}
//					if (rs != null){rs.close();rs = null;}
//					if (pstmt != null){pstmt.close();pstmt = null;}
//					strSql = "select * from t_sys_dict where dict_name=? and dict_type='zslevel' ";
//					pstmt = conn.prepareStatement(strSql);
//					pstmt.setString(1, zslevel);
//					rs = pstmt.executeQuery();
//					if ((rs != null) && (rs.next())) {
//						zslevel=rs.getString("dict_id");
//					}else{
//						if(!zslevel.equals("")){
//							ycstr=ycstr+"#zslevel";
//						}
//					}
//					if (rs != null){rs.close();rs = null;}
//					if (pstmt != null){pstmt.close();pstmt = null;}
//					strSql = "select * from t_sys_dict where dict_name=? and dict_type='jobtype' ";
//					pstmt = conn.prepareStatement(strSql);
//					pstmt.setString(1, jobtype);
//					rs = pstmt.executeQuery();
//					if ((rs != null) && (rs.next())) {
//						jobtype=rs.getString("dict_id");
//					}else{
//						if(!jobtype.equals("")){
//							ycstr=ycstr+"#jobtype";
//						}
//					}
//
//					if (rs != null){rs.close();rs = null;}
//					if (pstmt != null){pstmt.close();pstmt = null;}
//
//					strSql = "select * from t_sys_dict where dict_name=? and dict_type='rytype' ";
//					pstmt = conn.prepareStatement(strSql);
//					pstmt.setString(1, rytype);
//					rs = pstmt.executeQuery();
//					if ((rs != null) && (rs.next())) {
//						rytype=rs.getString("dict_id");
//					}else{
//						if(!rytype.equals("")){
//							ycstr=ycstr+"#rytype";
//						}
//					}
//					if (rs != null){rs.close();rs = null;}
//					if (pstmt != null){pstmt.close();pstmt = null;}
//
//					if(isjob!=null&&isjob.trim().equals("就业")){
//						isjob="1";
//					}else{
//						isjob="0";
//					}
//
//					if(isqyjnbx!=null&&isqyjnbx.trim().equals("是")){
//						isqyjnbx="1";
//					}else{
//						isqyjnbx="0";
//					}
//
//			          if(cjtype.equals("微信")){
//			        	  cjtype="1";
//			          }else if(cjtype.equals("设备")){
//			        	  cjtype="2";
//			          }
//
//					if(sex!=null&&sex.trim().equals("男")){
//						sex="1";
//					}else{
//						sex="2";
//					}
//
//					if(isbx.equals("是")){
//		        	  isbx="1";
//		            }else{
//		        	  isbx="0";
//		            }
//					if(iszs.equals("是")){
//						iszs="1";
//					}else{
//		        	  iszs="0";
//		          }
//
//					String cbqkname="";
//					String[] cbqk_arr=(cbqk==null?null:cbqk.split(","));
//					if(cbqk_arr!=null){
//						for(int m=0;m<cbqk_arr.length;m++){
//							strSql = "select * from  t_sys_dict where dict_name='"+cbqk_arr[m]+"' and dict_type='cbqk'";
//							pstmt1 = conn.prepareStatement(strSql);
//							rs1 = pstmt1.executeQuery();
//							if ((rs1 != null) && (rs1.next())){
//								if(!cbqkname.equals("")){cbqkname=cbqkname+",";}
//								cbqkname=cbqkname+rs1.getString("dict_id");
//							}
//							if (rs1 != null){rs1.close();rs1 = null;}
//							if (pstmt1 != null){pstmt1.close();pstmt1 = null;}
//						}
//
//					}
//
//                    String jnbxname="";
//					String[] jnbx_arr=(jnbx==null?null:jnbx.split(","));
//					if(jnbx_arr!=null){
//						for(int m=0;m<jnbx_arr.length;m++){
//							strSql = "select * from  t_sys_dict where dict_name='"+jnbx_arr[m]+"' and dict_type='jnbx'";
//							pstmt1 = conn.prepareStatement(strSql);
//							rs1 = pstmt1.executeQuery();
//							if ((rs1 != null) && (rs1.next())){
//								if(!jnbxname.equals("")){jnbxname=jnbxname+",";}
//								jnbxname=jnbxname+rs1.getString("dict_id");
//							}
//							if (rs1 != null){rs1.close();rs1 = null;}
//							if (pstmt1 != null){pstmt1.close();pstmt1 = null;}
//						}
//
//					}
//
//					if(ycstr.equals("")){
//						strSql = "delete from t_by_people where cardid=? and name=? and xl=?";
//						pstmt = conn.prepareStatement(strSql);
//						pstmt.setString(1, cardid.trim());
//						pstmt.setString(2, name.trim());
//						pstmt.setString(3, xl);
//						//System.out.println(xl+"xl:"+cardid+"cardid:"+name+"name:"+area+"");
//						pstmt.executeUpdate();
//						//if ((rs != null) && (rs.next())) {
//							//flag = true;
//					//	}
//						//if (rs != null){rs.close();rs = null;}
//						if (pstmt != null){pstmt.close();pstmt = null;}
//						//if (!flag)
//						//{
//						strSql = "insert into t_by_people(name,cardid,sex,birthday,tel, dwbm,addr,xl,bydate,iszs,"
//								+ "zsdate,zslevel,isjob,jobtype,rytype, cbqk,isbx,bxnx,cydate,zyrs,"
//								+ "qyaddr,isqyjnbx,qyname,qytel,rzdate ,jnbx,createdate,createman,area,cjtype,endpoint_id,issh,pcbh) "
//								+ "values(?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,now(),?,?,?,?,?,'"+pcbh+"')";
//						//System.out.println(strSql);
//						pstmt = conn.prepareStatement(strSql);
//
//
//						pstmt.setString(1, name.trim());
//						pstmt.setString(2, cardid.trim());
//						pstmt.setString(3, sex);
//						pstmt.setString(4, birthdate);
//						pstmt.setString(5, tel);
//
//						pstmt.setString(6, hkszd.trim());
//						pstmt.setString(7, addr);
//						pstmt.setString(8, xl);
//						pstmt.setString(9, bydate);
//						pstmt.setString(10,iszs);
//
//						pstmt.setString(11, zsdate);
//						pstmt.setString(12, zslevel);
//						pstmt.setString(13,isjob);
//						pstmt.setString(14, jobtype);
//						pstmt.setString(15, rytype);
//
//						pstmt.setString(16, cbqkname);
//						pstmt.setString(17,isbx);
//						pstmt.setString(18, bxnx);
//						pstmt.setString(19, cydate);
//						pstmt.setString(20, zyrs);
//
//						pstmt.setString(21,qyaddr);
//						pstmt.setString(22, isqyjnbx);
//						pstmt.setString(23,qyname);
//						pstmt.setString(24, qytel);
//						pstmt.setString(25,rzdate);
//						pstmt.setString(26, jnbxname);
//						pstmt.setString(27, czman);
//						pstmt.setString(28, area);
//						pstmt.setString(29, cjtype);
//						pstmt.setString(30, endpoint_id);
//						pstmt.setString(31, "1");
//						if (pstmt.executeUpdate() > 0)
//						{
//							count++;
//							result.setFlag(true);
//						}
//						else
//						{
//							result.setFlag(false);
//							result.setMessage("保存失败");
//						}
//						if (pstmt != null){pstmt.close();pstmt = null;}
//						allcount++;
//						result.setFlag(true);
//					}else{
//
//						strSql = "insert into t_by_people_yc(name,cardid,sex,birthday,tel, dwbm,addr,xl,bydate,iszs,"
//								+ "zsdate,zslevel,isjob,jobtype,rytype, cbqk,isbx,bxnx,cydate,zyrs,"
//								+ "qyaddr,isqyjnbx,qyname,qytel,rzdate ,jnbx,createdate,createman,area,cjtype,endpoint_id,issh,ycstr,pcbh) "
//								+ "values(?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,now(),?,?,?,?,?,?,'"+pcbh+"')";
//						//System.out.println(strSql);
//						pstmt = conn.prepareStatement(strSql);
//
//
//						pstmt.setString(1, name.trim());
//						pstmt.setString(2, cardid.trim());
//						pstmt.setString(3, sex);
//						pstmt.setString(4, birthdate);
//						pstmt.setString(5, tel);
//
//						pstmt.setString(6, hkszd.trim());
//						pstmt.setString(7, addr);
//						pstmt.setString(8, xl);
//						pstmt.setString(9, bydate);
//						pstmt.setString(10,iszs);
//
//						pstmt.setString(11, zsdate);
//						pstmt.setString(12, zslevel);
//						pstmt.setString(13,isjob);
//						pstmt.setString(14, jobtype);
//						pstmt.setString(15, rytype);
//
//						pstmt.setString(16, cbqkname);
//						pstmt.setString(17,isbx);
//						pstmt.setString(18, bxnx);
//						pstmt.setString(19, cydate);
//						pstmt.setString(20, zyrs);
//
//						pstmt.setString(21,qyaddr);
//						pstmt.setString(22, isqyjnbx);
//						pstmt.setString(23,qyname);
//						pstmt.setString(24, qytel);
//						pstmt.setString(25,rzdate);
//						pstmt.setString(26, jnbxname);
//						pstmt.setString(27, czman);
//						pstmt.setString(28, area);
//						pstmt.setString(29, cjtype);
//						pstmt.setString(30, endpoint_id);
//						pstmt.setString(31, "1");
//						pstmt.setString(32, ycstr);
//						if (pstmt.executeUpdate() > 0)
//						{
//							punish_jls++;
//							result.setFlag(true);
//						}
//						else
//						{
//							result.setFlag(false);
//							result.setMessage("保存失败");
//						}
//						if (pstmt != null){pstmt.close();pstmt = null;}
//						allcount++;
//						result.setFlag(true);
//					}
//
//
//				}
//			}
//			if (result.getFlag())
//			{
//				conn.commit();
//				result.setFlag(true);
//				result.setJls(count);
//				result.setMessage(pcbh);
//				result.setKjry_jls(allcount);
//				result.setPunish_jls(punish_jls);
//			}
//			else
//			{
//				conn.rollback();
//				result.setFlag(true);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			conn.rollback();
//			result.setFlag(false);
//		}
//		finally
//		{
//		}
//		return result;
//	}
//
//	//校验文件头部信息是否匹配
//	private boolean checkExcelTitles(XSSFWorkbook wb, String[] titles , JSONObject result) {
//
//	    if (null == wb.getSheetAt(0).getRow(0)) {
//	        result.put("failMsg", "请使用模版进行导入！");
//	        return false;
//	    } else {
//	        XSSFRow row = wb.getSheetAt(0).getRow(1);
//	        //判断第一行是否有数据
//	        if (row == null) {
//	            result.put("failMsg", "导入表格为空，请填写完信息后重新导入！");
//	            return false;
//	        }
//
//	        //大于5000条不允许导入
//	        int rowSum = wb.getSheetAt(0).getLastRowNum();
//	        if (rowSum > 5000) {     // 判断数据是否大于5000条
//	            result.put("failMsg", "电话信息条数超过5000条，请删减后重新导入！");
//	            return false;
//	        }
//	        return true;
//	    }
//	}
//
//	//检查文件内容是否有误
//	private Map<String, Object> checkExcel(XSSFWorkbook wb, String brandCode, String loginName) throws Exception {
//
//	    Map<String, Object> map = new HashMap<String, Object>();
//
//	    // 不可导入的List
//	    List<CustUserImportError> errorUserList = new ArrayList<CustUserImportError>();
//	    // 可成功导入的List
//	    List<PageData> importCustUserList = new ArrayList<PageData>();
//
//	    // 获得第一张表单
//	    XSSFSheet sheet = wb.getSheetAt(0);
//	    // 获得总行数
//	    int rowSum = sheet.getLastRowNum();
//	    // 获得该品牌下所有用户信息
//	    List<String> custUserList = PageDataService.getCustUserByBrandCode(brandCode);
//	    // 手机号List(用于判断本表单手机号是否重复)
//	    List<String> mobileList = new ArrayList<String>();
//
//	    for (int i = 1; i <= rowSum; i++) {
//	        if (null == sheet.getRow(i)) {
//	            continue;
//	        }
//	        // 获取电话
//	        String mobileNo = null;
//	        if (null != sheet.getRow(i).getCell((short) 0)) {
//	            mobileNo = ExcelUtil.getStringCellValue(sheet.getRow(i).getCell((short) 0));
//	            if (mobileNo.contains("E")) {    //当cell不是文本格式时，手机号会被会被转换成科学计数，需要用以下方法转回为手机号
//	                DecimalFormat df = new DecimalFormat("0");
//	                mobileNo = df.format(sheet.getRow(i).getCell((short) 0).getNumericCellValue());
//	            }
//	        }
//	        //获取姓名
//	        //获取备注
//	        // 校验数据
//	        String errorReason = "";
//	        if (StringUtils.isEmpty(mobileNo)) {    //判断手机号是否为空
//	            errorReason = IMPORT_NO_CONTENT + ";";
//	        } else if (mobileNo.length() != 11 || !isNotNumeric(mobileNo)) {    //判断手机号是否输入合法
//	            errorReason += IMPORT_INVALID_DATA + ";";
//	        }
//	        if (mobileList.contains(mobileNo) || custUserList.contains(mobileNo)) {    //判断是否与已存在的用户电话重复
//	            errorReason += IMPORT_EXISTED_DATA + ";";
//	        } else {
//	            if (null != mobileNo){
//	                mobileList.add(mobileNo);
//	            }
//	        }
//
//	        // 创建可导入的list和不可导入的list
//	        if (!StringUtils.isEmpty(errorReason)) {
//	            errorUserList.add(buildErrorUser(userName, mobileNo, remark, errorReason));
//	        } else {
//	            importCustUserList.add(buildUser(brandCode, userName, mobileNo, remark, loginName));
//	        }
//	    }
//	    map.put("errorUserList", errorUserList);
//	    map.put("importCustUserList", importCustUserList);
//	    return map;
//	}
//
//	//创建导入失败的excel文件
//	private XSSFWorkbook createExportContent(List<PageData> errorUserList) {
//	    // 创建XSSFWorkbook
//	    XSSFWorkbook wb = new XSSFWorkbook();
//
//	    // 创建表单并设置cell宽度
//	    XSSFSheet currentSheet = wb.createSheet("Sheet1");
//	    currentSheet.setDefaultColumnWidth(20);
//
//	    // 创建表头
//	    createTitle(currentSheet,exportCustUserColumns);
//
//	    // 创建cellStyle
//	    XSSFCellStyle style = wb.createCellStyle();
//	   // style.setFillPattern(XSSFCellStyle.);    //设置cell底色
//	    style.setFillForegroundColor(new XSSFColor(Color.red));
//
//	    // 插入表内容
//	    int currentRow = 1;
//	    Row row = currentSheet.getRow(0);
//	    Cell cell = null;
//
//	    for (PageData errorUser : errorUserList) {
//	        int cellIndex = 0;
//	        // 行
//	        row = currentSheet.createRow(currentRow);
//	        // 列
//	        cell = (Cell) row.createCell(cellIndex++);
//	        cell.setCellValue(errorUser.getMobileNo());
//
//	        cell = (Cell) row.createCell(cellIndex++);
//	        cell.setCellValue(errorUser.getUserName());
//
//	        cell = (Cell) row.createCell(cellIndex++);
//	        cell.setCellValue(errorUser.getRemark());
//
//	        cell = (Cell) row.createCell(cellIndex++);
//	        cell.setCellValue(errorUser.getErrorReason());
//	        // 最后一个单元格设置样式
//	        ((org.apache.poi.ss.usermodel.Cell) cell).setCellStyle(style);
//
//	        currentRow += 1;
//	    }
//	    return wb;
//	}
//
//	//创建导入失败的表头
//	private void createTitle(XSSFSheet sheet, String[] titles) {
//	    Row row = sheet.createRow(0);
//	    for (int i = 0; i < titles.length; i++) {
//	        //Cell cell = row.createCell(i);
//	        //cell.setCellValue(titles[i]);
//	    }
//	}
//}
//
//
//
