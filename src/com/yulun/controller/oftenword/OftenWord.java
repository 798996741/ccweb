package com.yulun.controller.oftenword;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.OftenWordManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OftenWord  implements CommonIntefate {
    @Resource(name="oftenWordService")
    private OftenWordManager oftenWordManage;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object = new JSONObject();
        try {
            PageData pd = new PageData();
            String cmd = data.getString("cmd")==null?"":data.getString("cmd");
            PageData pd_stoken=new PageData();
            JSONObject json = data.getJSONObject("data");
//            pd_stoken.put("TOKENID", json.get("tokenid"));
//            PageData pd_token=zxlbService.findByTokenId(pd_stoken);
//            if (pd_token != null) {
                if(cmd.equals("oftenwordlist")){
                    List<PageData> word = oftenWordManage.findWord(pd);
                    object.put("success","true");
                    object.put("data",word);
                }else if(cmd.equals("insertevaluate")) {
                    pd.put("id",json.get("id"));
                    pd.put("start",json.get("start"));
                    pd.put("issolve",json.get("issolve"));
                    pd.put("suggest",json.get("suggest"));
                    pd.put("zxid",json.get("zxid"));
                    pd.put("time",getTime());
                    oftenWordManage.insertevaluate(pd);
                    object.put("success","true");
                    object.put("msg","保存成功");
                }else if(cmd.equals("inserttthjl")){
                    PageData getID = oftenWordManage.getID(pd);
                    int id = Integer.parseInt(getID.get("func_nextid('tTHJL')").toString());
                    System.out.println(getID.get("func_nextid('tTHJL')"));
                    pd.put("id",id);
                    pd.put("zjhm",json.get("zjhm"));
                    pd.put("bjhm",json.get("bjhm"));
                    pd.put("ucid",json.get("ucid"));
                    pd.put("kssj",json.get("kssj"));
                    pd.put("jssj",json.get("jssj"));
                    pd.put("zxid",json.get("zxid"));
                    pd.put("thlx",json.get("thlx"));
                    pd.put("url",json.get("url"));
                    pd.put("sendurl",json.get("sendurl"));
                    oftenWordManage.inserttthjl(pd);
                    object.put("success","true");
                    object.put("data",pd);
                    object.put("msg","保存成功");
                }else if(cmd.equals("updatetthjl")){

                    pd.put("id",json.get("id"));
                    pd.put("zjhm",json.get("zjhm"));
                    pd.put("bjhm",json.get("bjhm"));
                    pd.put("ucid",json.get("ucid"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date kssj = sdf.parse(json.getString("kssj"));
                    Date jssj = sdf.parse(json.getString("jssj"));
                    pd.put("kssj",json.get("kssj"));
                    pd.put("jssj",json.get("jssj"));
                    pd.put("zxid",json.get("zxid"));
                    pd.put("thlx",json.get("thlx"));
                    pd.put("url",json.get("url"));
                    pd.put("sendurl",json.get("sendurl"));
                    pd.put("thsj",(jssj.getTime()/1000)-(kssj.getTime()/1000));
                    oftenWordManage.updatetthjl(pd);
                    object.put("success","true");
                    object.put("msg","编辑成功");
                }
//            } else {
//                object.put("success", "false");
//                object.put("msg", "登录超时，请重新登录");
//            }
        } catch (Exception e) {
            object.put("success", "false");
            object.put("msg", "异常");
        }
        return object;
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
