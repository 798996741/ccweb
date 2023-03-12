package com.yuzhe.controller.File;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.FileManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

public class fileCRUDController implements CommonIntefate {

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "lsFileService")
    private FileManager fileServie;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject jsondata = data.getJSONObject("data");
        JSONObject result=new JSONObject();
        try{
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            String tokenid=jsondata.getString("tokenid");
            PageData param=new PageData();
            param.put("TOKENID",tokenid);
            PageData pdtoken=zxlbService.findByTokenId(param);
            if(pdtoken!=null){
                if("FileList".equals(cmd)){
                    PageData pd_tonken=new PageData();
                    pd_tonken.put("articlId",jsondata.get("number"));
                    pd_tonken.put("type",1);
                    List<PageData> listpd=fileServie.FileList(pd_tonken);
                    JSONArray ja=new JSONArray();
                    for (PageData pd:listpd ) {
                        JSONObject jb=new JSONObject();
                        jb.put("id",pd.get("id"));
                        jb.put("fileName",pd.get("file_name"));
                        jb.put("createMan",pdtoken.get("ZXXM"));
                        jb.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pd.get("create_time")));
                        jb.put("isImg",pd.getString("file_type").startsWith("image"));
                        jb.put("type",pd.get("type"));
                        ja.add(jb);
                    }
                    result.put("data",ja);
                    result.put("success","true");
                }else if("photo".equals(cmd)) {
                	PageData pd_tonken=new PageData();
                    pd_tonken.put("articlId",jsondata.get("number"));
                    pd_tonken.put("type",0);
                    List<PageData> listpd=fileServie.FileList(pd_tonken);
                    JSONArray ja=new JSONArray();
                    for (PageData pd:listpd ) {
                        JSONObject jb=new JSONObject();
                        jb.put("id",pd.get("id"));
                        jb.put("fileName",pd.get("file_name"));
                        jb.put("createMan",pdtoken.get("ZXXM"));
                        jb.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pd.get("create_time")));
                        jb.put("isImg",pd.getString("file_type").startsWith("image"));
                        jb.put("type",pd.get("type"));
                        ja.add(jb);
                    }
                    result.put("data",ja);
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
}
