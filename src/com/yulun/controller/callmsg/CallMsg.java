package com.yulun.controller.callmsg;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CallMsgManger;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CallMsg implements CommonIntefate {
    @Resource(name = "callMsgService")
    private CallMsgManger callMsgManger;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
        JSONObject object = new JSONObject();
        try {
            String role = json.getString("role");
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                pd.put("ucid", json.getString("ucid"));
                pd.put("zjhm", json.getString("zjhm"));
                List<PageData> vipinfo = callMsgManger.findVipinfo(pd);
                List<PageData> findbyucid = callMsgManger.findbyucid(pd);
                PageData findnum = callMsgManger.findnum(pd);
                List<PageData> custinfo = callMsgManger.findCustinfo(pd);
                if (findbyucid.size()>0) {
                    pd.put("hwlsh",findbyucid.get(0).get("hwlsh").toString());
                }
                List<PageData> czsm = callMsgManger.getCZSM(pd);
                System.out.println("vipinfo:"+vipinfo);
                System.out.println("findbyucid:"+findbyucid);
                System.out.println("custinfo:"+custinfo);
                System.out.println("findnum:"+findnum);
                if ("905".equals(role)) {
                    if (vipinfo.size()>0){
                        if (findbyucid.size()>0){
                            for (PageData pageData : vipinfo) {
                                pageData.put("num",findnum.get("num").toString());
                                pageData.put("zjhm",json.getString("zjhm"));
                                pageData.put("kssj",findbyucid.get(0).get("kssj").toString());
                                if (czsm.size()>0){
                                    pageData.put("czsm",czsm.get(0).getString("CZSM"));
                                }else {
                                    pageData.put("czsm","暂无");
                                }
                            }
                        }else {
                            for (PageData pageData : vipinfo) {
                                pageData.put("num",findnum.get("num").toString());
                                pageData.put("zjhm",json.getString("zjhm"));
                                pageData.put("kssj","暂无");
                                if (czsm.size()>0){
                                    pageData.put("czsm",czsm.get(0).getString("CZSM"));
                                }else {
                                    pageData.put("czsm","暂无");
                                }
                            }
                        }
                        System.out.println("执行了1");
                        object.put("data", vipinfo);
                    }else{
                        PageData pageData = new PageData();
                        pageData.put("name","暂无");
                        pageData.put("position","暂无");
                        pageData.put("zjhm",pd.getString("zjhm"));
                        if (findbyucid.size()>0){
                            pageData.put("kssj",findbyucid.get(0).get("kssj").toString());
                        }else {
                            pageData.put("kssj","暂无");
                        }
                        if (czsm.size()>0){
                            pageData.put("czsm",czsm.get(0).getString("CZSM"));
                        }else {
                            pageData.put("czsm","暂无");
                        }
                        pageData.put("num",findnum.get("num").toString());
                        System.out.println("执行了2");
                        vipinfo.add(pageData);
                        object.put("data", vipinfo);
                    }
                    System.out.println(vipinfo);
                } else {
                    if (custinfo.size()>0){
                        if (findbyucid.size()>0){
                            for (PageData pageData : custinfo) {
                                pageData.put("num",findnum.get("num").toString());
                                pageData.put("zjhm",pd.getString("zjhm"));
                                pageData.put("kssj",findbyucid.get(0).get("kssj").toString());
                                pageData.put("position","暂无");
                                if (czsm.size()>0){
                                    pageData.put("czsm",czsm.get(0).getString("CZSM"));
                                }else {
                                    pageData.put("czsm","暂无");
                                }
                            }
                        }else {
                            for (PageData pageData : custinfo) {
                                pageData.put("num",findnum.get("num").toString());
                                pageData.put("zjhm",pd.getString("zjhm"));
                                pageData.put("kssj","暂无");
                                pageData.put("position","暂无");
                                if (czsm.size()>0){
                                    pageData.put("czsm",czsm.get(0).getString("CZSM"));
                                }else {
                                    pageData.put("czsm","暂无");
                                }
                            }
                        }
                        System.out.println("执行了3");
                        object.put("data", custinfo);
                    }else{
                        PageData pageData = new PageData();
                        pageData.put("name","暂无");
                        pageData.put("zjhm",pd.getString("zjhm"));
                        if (findbyucid.size()>0){
                            pageData.put("kssj",findbyucid.get(0).get("kssj").toString());
                        }else {
                            pageData.put("kssj","暂无");
                        }
                        pageData.put("num",findnum.get("num").toString());
                        pageData.put("position","暂无");
                        if (czsm.size()>0){
                            pageData.put("czsm",czsm.get(0).getString("CZSM"));
                        }else {
                            pageData.put("czsm","暂无");
                        }
                        System.out.println("执行了4");
                        custinfo.add(pageData);
                        object.put("data", custinfo);
                    }
                }
                object.put("success", "true");
            } else {
                object.put("success", "false");
                object.put("msg", "登录超时，请重新登录");
            }
        } catch (Exception e) {
            object.put("success", "false");
            object.put("msg", "异常");
        }
        return object;
    }
}
