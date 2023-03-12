package com.fh.controller.app.appuser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.dom4j.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;






import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import utils.FileStrUtil;

import com.fh.controller.activiti.AcStartController;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.controller.base.BaseController;
import com.fh.entity.OAuthInfo;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.Constants;
import com.fh.util.FileDownloadFromUrl;
import com.fh.util.FileUpload;
import com.fh.util.FilecopyHtml;
import com.fh.util.IDUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.MD5;
import com.fh.util.MyUtils;
import com.fh.util.ObjectExcelRead;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Signwx;
import com.fh.util.StringUtils;
import com.fh.util.Tools;
import com.fh.util.ZipTools;
import com.xxgl.utils.ResponseUtils;
import com.xxgl.service.mng.DocManager;
import com.xxgl.service.mng.FileManager;
import com.xxgl.service.mng.TdmMobileManager;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.service.AddressManager;
import com.yulun.service.BlacklistManager;
import com.yulun.service.RecordManager;
import com.yulun.service.ServerLogManager;



/**@author
  * 会员-接口类 
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appupload")
public class UploadController extends AcStartController{
    private Constants constants=new Constants();
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="fileService")
	private FileManager fileService;
	
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;
	
	
	
	/**
     * 获取本地图片
     * @param pictureName //文件名
     * @return
     */
    @RequestMapping("/picReq")
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
    public void ShowImg(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //文件名
        String imgFileName = request.getParameter("imgFileName")==null?"":request.getParameter("imgFileName");
        
        //这里是存放图片的文件夹地址
        String filepath=imgFileName.substring(0, imgFileName.lastIndexOf("/"));
        String filename=imgFileName.substring(imgFileName.lastIndexOf("/")+1,imgFileName.length());
        String path= Constants.PICTURE_SAVE_FILE_PATH;
        FileInputStream fileIs=null;
        OutputStream outStream = null;
        try {
            fileIs = new FileInputStream(path+"/"+imgFileName);
            //得到文件大小
            int i=fileIs.available();
            //准备一个字节数组存放二进制图片
            byte data[]=new byte[i];
            //读字节数组的数据
            fileIs.read(data);
            //设置返回的文件类型
            response.setContentType("image/*");
            //得到向客户端输出二进制数据的对象
            outStream=response.getOutputStream();
            //输出数据
            outStream.write(data);
            outStream.flush();
        } catch (Exception e) {
            logger.info("系统找不到图像文件："+path+"/"+imgFileName);
            return;
        }finally {
            //关闭输出流
            outStream.close();
            //关闭输入流
            fileIs.close();
        }
    }
	
	/**文件上传保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/FileUpload",produces = "text/html;charset=utf-8")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String editFile(@RequestParam(value = "file",required = false) MultipartFile[] files,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"新增doc");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject object=new JSONObject();
		JSONObject object_out=new JSONObject();
		PageData pd = new PageData();
		pd.put("TOKENID", request.getParameter("tokenid"));
		
		String path= Constants.PICTURE_SAVE_FILE_PATH;
		//System.out.println(path+"path");
		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){ 
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			//return object.toString();
			pd_token=new PageData();
			pd_token.put("ID", "tokenid:"+request.getParameter("tokenid"));
			pd_token.put("ZXYH", "tokenid:"+request.getParameter("tokenid"));

		}
		PageData pd_out = new PageData();
		pd = this.getPageData();
		String uuid=IDUtils.createUUID();
		pd.put("uuid", uuid);
		pd.put("uid", request.getParameter("uid"));
		pd.put("type", request.getParameter("type"));
		pd.put("filename", request.getParameter("filename"));
		pd.put("createman",pd_token.getString("ID"));
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		if (null != files) {
			for (MultipartFile i:files) {
				if(i.getSize()>0) {
					String filename = i.getOriginalFilename();
					FileStrUtil  fileStrUtil=new FileStrUtil();
					if (!fileStrUtil.checkFile(filename)) {
		            	object.put("success","false");
		    			object.put("data","上传的文件格式不正确");
		    			return object.toString();
		            }
				}	
			}	
			
			for (MultipartFile i:files) {
				//System.out.println(i.getOriginalFilename()+"files"+i.getSize());
				if(i.getSize()>0) {
					String filename = i.getOriginalFilename();
					//System.out.println(filename+"filename");
					String ext = MyUtils.getFileNameExt(filename);
					String newFileName = dateString+"_"+System.currentTimeMillis() + "." + ext;
					
					
					//String newFilePath = MyUtils.transferToWithHash(i, path, newFileName);
					pd.put("file", dateString+"/"+newFileName);
					pd.put("name", filename);
					pd.put("ext", ext);
					pd.put("createman",  pd_token.getString("ZXYH"));
					File file_c = new File(path+dateString+"/");
					if(!file_c.exists()){
						file_c.mkdir();
					}
					
					File file = new File(path+dateString+"/",newFileName);
					try {
						i.transferTo(file);
						fileService.savefile(pd);
						object_out.put("file", dateString+"/"+newFileName);
						object_out.put("uuid", uuid);
						object_out.put("name", filename);
						object_out.put("fileurl", Constants.VISIT_FILE_PATH+uuid);
						object.put("data",object_out.toString());
						object.put("success","true");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}	
		}else{
			object.put("success","false");
			object.put("data","保存失败");
		}	
		
		return object.toString();
	}
	
	
	
	
	@RequestMapping(value="/fileReq",produces = "text/html;charset=utf-8")
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public void fileReq(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		PageData pd = new PageData();
		PageData pd_file = new PageData();
		pd = this.getPageData();
		
		FileInputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		try {
			String uuid=pd.getString("uuid")==null?"":pd.getString("uuid");
			pd.put("uuid", uuid);
			pd_file=fileService.findFileById(pd);
			
			String downloadFileName = pd_file.getString("name");
			String path=Constants.PICTURE_SAVE_FILE_PATH+pd_file.getString("file");
			//System.out.println(path);
			file = new File(path);
			if (!file.exists()) {
				
				response.getWriter().print("文件不存在，请重新导出数据"+path);
			} else {
				inputStream = new FileInputStream(file);
				response.setContentType("application/txt;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;Filename=" + new String(downloadFileName.getBytes("GB2312"), "ISO8859-1"));// 设置下载后文件的名字、防止中文乱码

				outputStream = response.getOutputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(bytes)) > 0) {
					outputStream.write(bytes, 0, len);
				}
			}
		} catch (Exception e) {
			//log.error("文件下载异常：", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				//log.error("下载日报中关闭流异常 ：{} ", e);
			}
		}
	}
	
	
	
	/**文件上传保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/fileList",produces = "text/html;charset=utf-8")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String fileList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"新增doc");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject object=new JSONObject();
		PageData pd = new PageData();
		
		String cmd=request.getParameter("cmd")==null?"":request.getParameter("cmd");
		pd.put("TOKENID", request.getParameter("tokenid"));
		
		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){ 
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			return object.toString();
		}
		if(cmd.equals("fileAll")){
			pd.put("uid",request.getParameter("uid"));
			pd.put("type",request.getParameter("type"));
	        List<PageData> fileList=fileService.findFileByuid(pd);
	        for(PageData pdServerLog:fileList){  
				pdServerLog.put("createdate", String.valueOf(pdServerLog.get("createdate")).substring(0, 19));
				pdServerLog.put("file",Constants.VISIT_FILE_PATH+pdServerLog.getString("uuid"));
	        }
	        List<JSONObject> objlist=new ArrayList();
			if(fileList.size()>0){
	        	object.put("success","true");
			    object.put("data",fileList);
			}else{
				object.put("success","false");
		        object.put("msg","暂无数据");
			}
		}else if(cmd.equals("fileAllList")){
			pd.put("uid",request.getParameter("uid"));
			pd.put("type",request.getParameter("type"));
			Page page = new Page();
	        Integer pageIndex;
	        Integer pageSize;
	        
	        pageIndex = Integer.parseInt(request.getParameter("pageIndex")==null?"0":request.getParameter("pageIndex"));
            page.setCurrentPage(pageIndex);
            pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"10":request.getParameter("pageSize"));
            page.setShowCount(pageSize);
            page.setPd(pd);
      
	        List<PageData> fileList=fileService.datalistPage(page);
	        for(PageData pdServerLog:fileList){  
				pdServerLog.put("createdate", String.valueOf(pdServerLog.get("createdate")).substring(0, 19));
				pdServerLog.put("file",Constants.VISIT_FILE_PATH+pdServerLog.getString("uuid"));
	        }
	        List<JSONObject> objlist=new ArrayList();
			if(fileList.size()>0){
	        	object.put("success","true");
			    object.put("data",fileList);
			    object.put("pageId",pageIndex);
				object.put("pageCount",page.getTotalPage());
				object.put("recordCount",page.getTotalResult());
			}else{
				object.put("success","false");
		        object.put("msg","暂无数据");
			}
		}else  if(cmd.equals("fileByid")){
			pd.put("uuid",request.getParameter("uuid"));
	        PageData pdServerLog=fileService.findFileById(pd);
	        pdServerLog.put("file",Constants.VISIT_FILE_PATH+pdServerLog.getString("uuid"));			if(pdServerLog!=null){
	        	object.put("success","true");
			    object.put("data",pdServerLog);
			}else{
				object.put("success","false");
		        object.put("msg","暂无数据");
			}
		}else  if(cmd.equals("fileDel")){
			pd.put("id",request.getParameter("uuid"));
	        fileService.deleteAllFile(pd);
        	object.put("success","true");
		}
        
		
		return object.toString();
	}
	
	 
	
	

}
	
 