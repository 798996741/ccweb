package com.yuzhe.controller.report;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.fh.controller.base.BaseController;
import com.xxgl.service.mng.ZxlbManager;
import com.yuzhe.util.HttpPostAndHeader;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.yuzhe.service.LostManager;

@Controller
@RequestMapping("/api")
public class LostReport extends BaseController {
	@Resource(name="lostService")
	private LostManager lostManager;

	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;


	    /**文件上传保存
	     * @param
	     * @throws Exception
	     */
		@ResponseBody
		@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	    @RequestMapping(value="/uploadfile", produces = {"application/json;charset=UTF-8"})
	    public Object upload(@RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request) throws Exception {

	    	JSONObject result=new JSONObject();
	    	String tokenid=request.getParameter("tokenid");
	    	try{
				PageData pd_stoken=new PageData();
				String fileUpUrl = "";
				pd_stoken.put("TOKENID",tokenid);
				PageData pdtoken=zxlbService.findByTokenId(pd_stoken);
				String cmd = request.getParameter("cmd") == null ? "" : request.getParameter("cmd");
				if(pdtoken!=null){
					JSONArray ja=new JSONArray();
					JSONObject jb=new JSONObject();
					//获取原始文件名称
					String name=file.getOriginalFilename();
					//获取文件新名称
					String newname=request.getParameter("newname");
					//获取文件后缀
					String ext=name.substring(name.lastIndexOf("."));
					if(!newname.contains(ext)){
						newname+=ext;
					}
					//文件保存名
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					String filename = formatter.format(new Date())+"_"+System.currentTimeMillis()  + ext;
					//文件保存路径
					String path=request.getServletContext().getRealPath("uploadFiles/uploadFile");
					boolean isImg=file.getContentType().startsWith("image")?true:false;

					//文件流
					File fi = new File(path,filename);
					//文件上传
					file.transferTo(fi);
					//---------------------上传图片至局方接口，文件流 fi
					int photoType=Integer.parseInt(request.getParameter("type"));
					if(photoType==2){
						if(request.getParameter("isLost") == null || Integer.parseInt(request.getParameter("isLost")) != 1 ){
							JSONObject jsonObject = HttpPostAndHeader.upFile(fi);
							String code = String.valueOf(jsonObject.get("code"));
							if("0".equals(code)){
								fileUpUrl = String.valueOf(jsonObject.get("data"));
								result.put("fileUpUrl", fileUpUrl);
							}
						}
					}
					//---------------------
					if("addupload".equals(cmd)){
						PageData pd=new PageData();
						pd.put("fileDefinename",name);
						pd.put("fileName",newname);
						pd.put("filePath",filename);
						pd.put("fileType",file.getContentType());
						pd.put("createMan",pdtoken.getString("ZXID"));
						pd.put("createTime",new Date());
						pd.put("type",request.getParameter("type"));
						lostManager.addReportFiles(pd);
						System.out.println(file.getContentType());
						jb.put("id",pd.get("id"));
						jb.put("fileName",newname);
						jb.put("createMan",pdtoken.get("ZXXM"));
						jb.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						jb.put("isImg",isImg);
						jb.put("type",pd.get("type"));
						ja.add(jb);
						System.out.println("this is pd" + pd);
						System.out.println("this is jb" + jb);
						result.put("data",ja);
						result.put("success","true");
					}else if("updupload".equals(cmd)){
						PageData pd=new PageData();
						pd.put("fileDefinename",name);
						pd.put("fileName",newname);
						pd.put("filePath",filename);
						pd.put("fileType",file.getContentType());
						pd.put("createMan",pdtoken.getString("ZXID"));
						pd.put("createTime",new Date());
						pd.put("articleId",request.getParameter("number"));
						pd.put("type",request.getParameter("type"));
						lostManager.addReportFiles(pd);
						result.put("success","true");
					}else{
						result.put("success","false");
						result.put("msg","访问异常");
					}

				}else{
					result.put("success","false");
					result.put("msg","登录超时，请重新登录");
				}
			}catch (Exception e){
				e.printStackTrace();
				result.put("success","false");
				result.put("msg","操作异常");
			}

			return result;

	    }

	@ResponseBody()
	@RequestMapping(value = "/downloadfile", produces = {"application/json;charset=UTF-8"})
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	public Object downloadAndCat(HttpServletResponse response, HttpServletRequest request){
		JSONObject result=new JSONObject();
		String tokenid=request.getParameter("tokenid");
		try{
			PageData pd_stoken=new PageData();
			pd_stoken.put("TOKENID",tokenid);
			PageData pdtoken=zxlbService.findByTokenId(pd_stoken);
			if(pdtoken!=null){
				String cmd=request.getParameter("cmd")==null?"":request.getParameter("cmd");
				if("download".equals(cmd)){
					PageData pd=new PageData();
					pd.put("id",request.getParameter("id"));
					pd=lostManager.findFileById(pd);
					String relpath=request.getServletContext().getRealPath("uploadFiles/uploadFile");
					String oldfilename=pd.getString("file_path");
					String newfilename=pd.getString("file_name");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-disposition","attachment;filename="+new String(newfilename.getBytes("utf-8"),"iso8859-1"));
					FileInputStream fi=new FileInputStream(new File(relpath,oldfilename));
					ServletOutputStream fo=response.getOutputStream();
					IOUtils.copy(fi,fo);
					IOUtils.closeQuietly(fi);
					IOUtils.closeQuietly(fo);
					result.put("success","true");
				}else if("cat".equals(cmd)){
					PageData pd=new PageData();
					pd.put("id",request.getParameter("id"));
					pd=lostManager.findFileById(pd);
					String relpath=request.getServletContext().getRealPath("uploadFiles/uploadFile");
					String oldfilename=pd.getString("file_path");
					String newfilename=pd.getString("file_name");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-disposition","inline;filename="+newfilename);
					FileInputStream fi=new FileInputStream(new File(relpath,oldfilename));
					ServletOutputStream fo=response.getOutputStream();
					IOUtils.copy(fi,fo);
					IOUtils.closeQuietly(fi);
					IOUtils.closeQuietly(fo);
					result.put("success","true");
				}else{
					result.put("success","false");
					result.put("msg","访问异常");
				}
			}else{
				result.put("success","false");
				result.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("success","false");
			result.put("msg","操作异常");
		}
			return result;
	}

}
