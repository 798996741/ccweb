package com.yulun.controller.vipinfo;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.service.VipInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/api")
public class PhotoController {
    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @RequestMapping(value = "/app_upload", method = RequestMethod.POST)
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String upload(@RequestParam("files") MultipartFile[] files, MultipartHttpServletRequest request, HttpServletRequest request1) throws Exception, IOException {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                //获取文件夹的名字
                List<String[]> filess = new ArrayList<String[]>();
                String path = request.getSession().getServletContext().getRealPath("/uploadFiles/vipfile");

                StringBuffer stringbuffer = new StringBuffer();
                //对传进来的文件数组，进行 循环复制
                for (MultipartFile multipartFile : files) {
                    //判断文件是否为空
                    if (!multipartFile.isEmpty()) {
                        long l = System.currentTimeMillis();
                        //将多个文件名拼接在一个字符串中，用;分隔
                        stringbuffer.append(l + multipartFile.getOriginalFilename());
                        stringbuffer.append(";");
                        File dir = new File(path, l + multipartFile.getOriginalFilename());
                        //将文件名和对应的路径存放在数组中
                        String[] files1 = {l + multipartFile.getOriginalFilename(), dir.toPath().toString()};
                        //将一个文件的标识信息存入集合中
                        filess.add(files1);
                        //System.out.println(dir.toPath());
                        //文件不存则在创建
                        if (!dir.exists() && !dir.isDirectory()) {
                            dir.mkdirs();
                        }
                        //文件进行复制
                        multipartFile.transferTo(dir);
                    }
                }
                System.out.println(stringbuffer.length());
                String s = stringbuffer.substring(0, stringbuffer.length() - 1);
                //将文件信息集合存入数据库中
                PageData pd = new PageData();
                String vipid = request1.getParameter("vipid");
                System.out.println(vipid);
                pd.put("vipid", vipid);
                String uuid32 = getUUID32();
                pd.put("id", uuid32);
                for (String[] strings : filess) {
                    for (int i = 0; i < strings.length; i++) {
                        pd.put("name", strings[0]);
                        pd.put("url", strings[1]);

                    }
                    vipInfoManager.insertphoto(pd);
                }
                json.put("success", "true");
                json.put("pid", uuid32);
                return json.toString();

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }

        return json.toString();
    }

    @RequestMapping(value = "/app_getFile", produces = {
            "application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String getFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                String id = request.getParameter("id");
                PageData pd = new PageData();
                pd.put("id", id);
                PageData fileByid = vipInfoManager.getFileByid(pd);
                String url = fileByid.getString("url");
                System.out.println(url);
                File file = new File(url);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                response.setHeader("Content-Type", "image/jpeg");
                byte b[] = new byte[1024];
                int read;
                try {
                    while ((read = bis.read(b)) != -1) {
                        bos.write(b, 0, read);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    if (bos != null) {
                        bos.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
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

    @RequestMapping(value = "/app_Filename")
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String getname(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject data = new JSONObject();
        PageData pd_stoken = new PageData();
        System.out.println(request.getParameter("tokenid"));
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            System.out.println(request.getParameter("vipid"));
            pd.put("vipid", request.getParameter("vipid"));
            System.out.println(pd);
            List<PageData> fileByVipid = vipInfoManager.getFileByVipid(pd);
            System.out.println(fileByVipid);
            data.put("data", fileByVipid);
            data.put("success", "true");
        } else {
            data.put("success", "false");
            data.put("msg", "登录超时，请重新登录");
        }

        return data.toString();
    }

    @RequestMapping(value = "/app_delFile", method = RequestMethod.POST)
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String delFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject data = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                String id = request.getParameter("id");
                pd.put("id", id);
                PageData fileByid = vipInfoManager.getFileByid(pd);
                String url = fileByid.getString("url");
                String name = fileByid.getString("name");
                File file = new File(url);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                vipInfoManager.deleteFile(pd);
                data.put("success", "true");

            } else {
                data.put("success", "false");
                data.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            data.put("success", "false");
        }
        return data.toString();
    }

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

}
