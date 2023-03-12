package com.yulun.controller.msgtemp;


import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.operatelog.OperateLogManager;
import com.fh.util.IPUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.entity.MsgTemp;
import com.yulun.service.MsgTempManager;


import com.yulun.service.NoticeManager;
import com.yulun.service.ServerLogManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MsgTempWeb implements CommonIntefate {
    @Resource(name = "msgTempService")
    private MsgTempManager msgTempManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name = "serverLogService")
    private ServerLogManager serverLogService;
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;
    @Resource(name ="operatelogService")
    private OperateLogManager operateLogManager;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object = new JSONObject();
        try {
            PageData pd = new PageData();
            Page page = new Page();
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData pd_stoken = new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
             String ZXID = pd_token.getString("ZXID");
            if (pd_token != null) {
                pd.put("ZXID",pd_stoken.getString("ZXID"));
                if (cmd.equals("insertMagTemp")) {
                    pd.put("ZXID",pd_token.getString("ZXID"));
                    pd.put("content", json.getString("content"));
                    pd.put("parentid", json.getString("parentid"));
                    pd.put("tempname", json.getString("tempname"));
                    pd.put("acceptman", json.getString("acceptman"));
                    pd.put("cate", json.getString("cate"));
                    pd.put("phones", json.getString("phones"));
                    pd.put("phonesid", json.getString("phonesid"));
                    pd.put("way", json.getString("way"));
                    pd.put("uid", json.get("uid"));
                    pd.put("czman", ZXID);
                    pd.put("czdate",getTime() );
                    if (json.get("uid") != null && json.get("ucid") != null) {

                        pd.put("ucid", json.get("ucid"));
                        serverLogService.saveRecord(pd);
                    }
                    msgTempManager.save(pd);
                    object.put("success", "true");
                } else if (cmd.equals("updateMagTemp")) {
                    pd.put("id", json.getString("id"));
                    pd.put("ZXID",pd_token.getString("ZXID"));
                    pd.put("content", json.getString("content"));
                    pd.put("parentid", json.getString("parentid"));
                    pd.put("tempname", json.getString("tempname"));
                    pd.put("acceptman", json.getString("acceptman"));
                    pd.put("cate", json.getString("cate"));
                    pd.put("phones", json.getString("phones"));
                    pd.put("way", json.getString("way"));
                    pd.put("phonesid", json.getString("phonesid"));
                    if (json.get("uid") != null && json.get("ucid") != null) {
                        pd.put("uid", json.get("uid"));
                        pd.put("ucid", json.get("ucid"));
                        serverLogService.saveRecord(pd);
                    }
                    msgTempManager.edit(pd);
                    object.put("success", "true");
                } else if (cmd.equals("deleteMagTemp")) {
                    pd.put("ZXID",pd_token.getString("ZXID"));

                    String[] ids = json.getString("id").split(",");
                    msgTempManager.deleteAll(ids);
                    String id = json.getString("id");
                    String zxid = pd_token.getString("ZXID");
                    String PROC_INST_ID_ = UuidUtil.get32UUID();
                    String clyj="无";
                    String msgstr="通过";
                    String taskname="执行批量删除";
                    String operate="{处理节点:"+taskname+",工单流程id:"+PROC_INST_ID_+",处理人:"+zxid+",处理意见:"+clyj+",是否通过:"+msgstr+"}";
                    pd.put("ID", PROC_INST_ID_);
                    pd.put("MAPPERNAME","MessageMapper.deleteMagTemp");
                    pd.put("OPERATEMAN", zxid);// 操作者
                    pd.put("systype","2");
                    pd.put("OPERATEDATE", new Date());// 时间
                    pd.put("OPERATESTR", operate);// 请求参数
                    pd.put("TYPE", "1");// 正常结束
                    pd.put("IP", IPUtil.getLocalIpv4Address());// ip
                    operateLogManager.save(pd);


                    object.put("success", "true");
                } else if (cmd.equals("listAllDict")) {

                    PageData pageData = new PageData();
                    pageData.put("id",ZXID);
                    List<PageData> except = noticeManager.getExcept(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);

                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("keywords", json.get("keywords"));
                    pd.put("way", json.get("way"));
                    pd.put("userids", users);
                    page.setPd(pd);
                    List<PageData> msgAlllistPage = msgTempManager.findMsgAlllistPage(page);
                    object.put("pageId", pageIndex);
                    object.put("pageCount", page.getTotalPage());
                    object.put("recordCount", page.getTotalResult());
                    object.put("data", msgAlllistPage);
                    object.put("success", "true");
                } else if (cmd.equals("listByParentId")) {
                    String parentid = json.getString("parentid");
                    List<MsgTemp> msgTemps = msgTempManager.listSubDictByParentId(parentid);
                    object.put("data", msgTemps);
                    object.put("success", "true");
                } else if (cmd.equals("findMsgLogAll")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);

                    PageData pageData = new PageData();
                    pageData.put("id",ZXID);
                    List<PageData> except = noticeManager.getExcept(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);

                    pd.put("userids", users);
                    pd.put("acceptman", json.getString("acceptman"));
                    pd.put("starttime", json.getString("starttime"));
                    pd.put("endtime", json.getString("endtime"));
                    pd.put("keywords", json.getString("keywords"));
                    pd.put("sendman", json.getString("sendman"));
                    pd.put("phone", json.getString("phone"));
                    pd.put("phoneid", json.getString("phoneid"));
                    pd.put("acceptmanname", json.getString("acceptmanname"));
                    page.setPd(pd);
                    List<PageData> msgLogAll = msgTempManager.findMsgLogAlllistPage(page);
                    object.put("pageId", pageIndex);
                    object.put("pageCount", page.getTotalPage());
                    object.put("recordCount", page.getTotalResult());
                    object.put("data", msgLogAll);
                    object.put("success", "true");

                } else if (cmd.equals("insertMsgLog")) {
                    pd.put("ZXID",pd_token.getString("ZXID"));
                    //phonesobject为空为单发，不为空为群发
                    String phones = json.getString("phones");
                    String phonesid = json.getString("phonesid");
                    pd.put("uid", json.get("uid"));
                    if (json.get("uid") != null && json.get("ucid") != null) {

                        pd.put("ucid", json.get("ucid"));
                        serverLogService.saveRecord(pd);
                    }
                    if (phones.contains(",")) {
                        String[] phonessplit = phones.split(",");
                        String[] phonesidsplit = phonesid.split(",");
                        for (int i = 0; i < phonessplit.length; i++) {
                            PageData pageData = new PageData();
                            pageData.put("id", phonesidsplit[i]);
                            List<PageData> getvipinfo = msgTempManager.getvipinfo(pageData);
                            List<PageData> getcustom = msgTempManager.getcustom(pageData);
                            List<PageData> getaddlist = msgTempManager.getaddlist(pageData);
                            String cate = "";
                            if (getvipinfo.size() > 0) {
                                cate = "0";
                            }
                            if (getcustom.size() > 0) {
                                cate = "1";
                            }
                            if (getaddlist.size() > 0) {
                                cate = "2";
                            }
                            pd.put("cate", cate);
                            pd.put("time", json.getString("time"));
                            pd.put("way", json.getString("way"));
                            pd.put("temp", json.getString("temp"));
                            pd.put("content", json.getString("content"));
                            pd.put("acceptman", json.getString("acceptman"));
                            pd.put("phone", phonessplit[i]);
                            pd.put("sendman", json.getString("sendman"));
                            pd.put("sendtime", json.getString("sendtime"));
                            pd.put("state", "0");
                            pd.put("returnmsg", json.getString("returnmsg"));
                            pd.put("phones", phones);
                            pd.put("phonesid", phonesid);
                            pd.put("phoneid", phonesidsplit[i]);
                            pd.put("czman", ZXID);
                            pd.put("czdate",getTime() );
                            pd.put("source","1");
                            msgTempManager.insertMsgLog(pd);
                        }
                    } else {
                        pd.put("uid", json.getString("uid"));
                        pd.put("ucid", json.getString("ucid"));
                        pd.put("time", json.getString("time"));
                        pd.put("way", json.getString("way"));
                        pd.put("temp", json.getString("temp"));
                        pd.put("content", json.getString("content"));
                        pd.put("acceptman", json.getString("acceptman"));
                        pd.put("phone", phones);
                        pd.put("sendman", json.getString("sendman"));
                        pd.put("sendtime", json.getString("sendtime"));
                        pd.put("state", "0");
                        pd.put("returnmsg", json.getString("returnmsg"));
                        pd.put("phones", phones);
                        pd.put("phonesid", phonesid);
                        pd.put("phoneid", phonesid);
                        pd.put("czman", ZXID);
                        pd.put("czdate",getTime() );
                        pd.put("source","1");
                        msgTempManager.insertMsgLog(pd);
                    }
                    object.put("success", "true");
                } else if (cmd.equals("updateMsgLog")) {
                    pd.put("ZXID",pd_token.getString("ZXID"));
                    pd.put("id", json.getString("id"));
                    pd.put("time", json.getString("time"));
                    pd.put("way", json.getString("way"));
                    pd.put("temp", json.getString("temp"));
                    pd.put("content", json.getString("content"));
                    pd.put("acceptman", json.getString("acceptman"));
                    pd.put("phone", json.getString("phone"));
                    pd.put("sendman", json.getString("sendman"));
                    pd.put("sendtime", json.getString("sendtime"));
                    pd.put("state", json.getString("state"));
                    pd.put("returnmsg", json.getString("returnmsg"));
                    pd.put("cate", json.getString("cate"));
                    pd.put("phones", json.getString("phones"));
                    pd.put("phonesid", json.getString("phonesid"));
                    pd.put("phoneid", json.getString("phoneid"));
                    pd.put("source","1");
                    if (json.get("uid") != null && json.get("ucid") != null) {
                        pd.put("uid", json.get("uid"));
                        pd.put("ucid", json.get("ucid"));
                        serverLogService.saveRecord(pd);
                    }
                    msgTempManager.updateMsgLog(pd);
                    object.put("success", "true");
                } else if (cmd.equals("findMsgLogById")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", json.getString("starttime"));
                    pd.put("endtime", json.getString("endtime"));
                    pd.put("keywords", json.getString("keywords"));
                    pd.put("sendman", json.getString("sendman"));
                    pd.put("phone", json.getString("phone"));

                    page.setPd(pd);
//                    List<PageData> findMsgcustByIdlistPage = msgTempManager.findMsgcustByIdlistPage(page);
//                    if (findMsgcustByIdlistPage!=null){
//                        object.put("data",findMsgcustByIdlistPage);
//                    }
                    List<PageData> findMsgvipByIdlistPage = msgTempManager.findMsgvipByIdlistPage(page);
//                    if (findMsgvipByIdlistPage!=null){
//                        object.put("data",findMsgvipByIdlistPage);
//                    }
                    object.put("data", findMsgvipByIdlistPage);
                    object.put("pageId", pageIndex);
                    object.put("pageCount", page.getTotalPage());
                    object.put("recordCount", page.getTotalResult());
                    object.put("success", "true");
                } else if (cmd.equals("deleteTempAll")) {

                    String id = json.getString("id");
                    String zxid = pd_token.getString("ZXID");
                    String PROC_INST_ID_ = UuidUtil.get32UUID();
                    String clyj="无";
                    String msgstr="通过";
                    String taskname="执行批量删除";
                    String operate="{处理节点:"+taskname+",工单流程id:"+PROC_INST_ID_+",处理人:"+zxid+",处理意见:"+clyj+",是否通过:"+msgstr+"}";
                    pd.put("ID", PROC_INST_ID_);
                    pd.put("MAPPERNAME","MessageMapper.deleteMagTemp");
                    pd.put("OPERATEMAN", zxid);// 操作者
                    pd.put("systype","2");
                    pd.put("OPERATEDATE", new Date());// 时间
                    pd.put("OPERATESTR", operate);// 请求参数
                    pd.put("TYPE", "1");// 正常结束
                    pd.put("IP", IPUtil.getLocalIpv4Address());// ip
                    operateLogManager.save(pd);
                    pd.put("ZXID",pd_token.getString("ZXID"));
                    String[] ids = json.getString("id").split(",");
                    msgTempManager.deleteAll(ids);
                    object.put("success", "true");
                } else if (cmd.equals("getacceptman")) {
                    String cate = json.getString("cate");
                    System.out.println(cate);
                    ArrayList<PageData> alist = new ArrayList<>();
                    pd.put("id", json.getString("id"));
                    if (cate.contains("0")) {
                        List<PageData> getvipinfo = msgTempManager.getvipinfo(pd);
                        for (PageData pageData : getvipinfo) {
                            ArrayList<PageData> list = new ArrayList<>();
                            String id = pageData.getString("id");
                            PageData pageData1 = new PageData();
                            pageData1.put("vipid", id);
                            List<PageData> getviptel = msgTempManager.getviptel(pageData1);
                            for (PageData pageData2 : getviptel) {
                                PageData pageData3 = new PageData();
                                pageData3.put("id", pageData2.getString("otel"));
                                pageData3.put("name", pageData2.getString("otel"));
                                list.add(pageData3);
                            }
                            pageData.put("children", list);
                            pageData.put("cate", "0");
                            alist.add(pageData);

                        }
                    }
                    if (cate.contains("1")) {
                        List<PageData> getcustom = msgTempManager.getcustom(pd);
                        for (PageData pageData : getcustom) {
                            ArrayList<PageData> list = new ArrayList<>();
                            String phone = pageData.getString("phone");
                            if (phone.contains(",")) {
                                String[] split = phone.split(",");
                                for (String s : split) {
                                    PageData pageData1 = new PageData();
                                    pageData1.put("id", s);
                                    pageData1.put("name", s);
                                    list.add(pageData1);
                                }
                            } else {
                                PageData pageData1 = new PageData();
                                pageData1.put("id", phone);
                                pageData1.put("name", phone);
                                list.add(pageData1);
                            }
                            pageData.put("children", list);
                            pageData.put("cate", "1");
                            alist.add(pageData);

                        }
                    }
                    if (cate.contains("2")) {
                        PageData data1 = new PageData();
                        data1.put("id",ZXID);
                        List<PageData> except = noticeManager.getExcept(data1);
                        ArrayList<String> list1 = new ArrayList<>();
                        for (PageData pageData1 : except) {
                            list1.add(pageData1.getString("dept"));
                        }
                        String users=list1.toString().substring(1,list1.toString().length()-1);
                        System.out.println(users);
                        pd.put("departmentid",users);
                        List<PageData> getaddlist = msgTempManager.getaddlist(pd);
                        for (PageData pageData : getaddlist) {
                            ArrayList<PageData> list = new ArrayList<>();
                            PageData pageData1 = new PageData();
                            pageData1.put("id", pageData.getString("tel1"));
                            pageData1.put("name", pageData.getString("tel1"));
                            list.add(pageData1);
                            System.out.println(list);
                            pageData.put("children", list);
                            pageData.put("cate", "2");
                            alist.add(pageData);
                        }
                    }
                    System.out.println(alist);
                    object.put("data", alist);
                    object.put("success", "true");
                } else if (cmd.equals("getphone")) {
                    String cate = json.getString("cate");
                    System.out.println(cate);
                    ArrayList<PageData> alist = new ArrayList<>();
                    String ids = json.getString("id");
                    if (ids.contains(",")) {
                        String[] splits = ids.split(",");
                        for (String str : splits) {
                            pd.put("id", str);
                            if (cate.contains("0")) {
                                List<PageData> getvipinfo = msgTempManager.getvipinfo(pd);
                                for (PageData pageData : getvipinfo) {
                                    String id = pageData.getString("id");
                                    PageData pageData1 = new PageData();
                                    pageData1.put("vipid", id);

                                    List<PageData> getviptel = msgTempManager.getviptel(pageData1);
                                    for (PageData pageData2 : getviptel) {
                                        String otel = pageData2.getString("otel");
                                        if (otel != null && !otel.equals("")) {
                                            PageData pageData3 = new PageData();
                                            pageData3.put("phoneid", pageData.get("id"));
                                            pageData3.put("phone", otel);
                                            alist.add(pageData3);
                                        }

                                    }
                                }
                            }
                            if (cate.contains("1")) {
                                List<PageData> getcustom = msgTempManager.getcustom(pd);
                                for (PageData pageData : getcustom) {
                                    ArrayList<String> list = new ArrayList<>();
                                    String phone = pageData.getString("phone");
                                    if (phone != null) {
                                        if (phone.contains(",")) {
                                            String[] split = phone.split(",");
                                            for (String s : split) {
                                                PageData pageData1 = new PageData();
                                                pageData1.put("phoneid", pageData.get("id"));
                                                pageData1.put("phone", s);
                                                alist.add(pageData1);
                                            }
                                        } else {
                                            PageData pageData1 = new PageData();
                                            pageData1.put("phoneid", pageData.get("id"));
                                            pageData1.put("phone", phone);
                                            alist.add(pageData1);
                                        }
                                    }

                                }
                            }
                            if (cate.contains("2")) {

                                List<PageData> getaddlist = msgTempManager.getaddlist(pd);
                                for (PageData pageData : getaddlist) {
                                    String tel1 = pageData.getString("tel1");
                                    if ((!tel1.equals("") && tel1 != null)) {
                                        PageData pageData1 = new PageData();
                                        pageData1.put("phoneid", pageData.get("id"));
                                        pageData1.put("phone", pageData.getString("tel1") != null ? pageData.getString("tel1") : "");
                                        alist.add(pageData1);
                                    }
                                }
                            }
                        }
                    } else {
                        pd.put("id", ids);
                        if (cate.contains("0")) {
                            List<PageData> getvipinfo = msgTempManager.getvipinfo(pd);
                            for (PageData pageData : getvipinfo) {
                                String id = pageData.getString("id");
                                PageData pageData1 = new PageData();
                                pageData1.put("vipid", id);

                                List<PageData> getviptel = msgTempManager.getviptel(pageData1);
                                for (PageData pageData2 : getviptel) {
                                    PageData pageData3 = new PageData();
                                    if (pageData2.getString("otel") != null && !pageData2.getString("otel").equals("")) {
                                        pageData3.put("phoneid", pageData.get("id"));
                                        pageData3.put("phone", pageData2.getString("otel") != null ? pageData2.getString("otel") : "");
                                        alist.add(pageData3);
                                    }
                                }
                            }
                        }
                        if (cate.contains("1")) {
                            List<PageData> getcustom = msgTempManager.getcustom(pd);
                            for (PageData pageData : getcustom) {
                                ArrayList<String> list = new ArrayList<>();
                                String phone = pageData.getString("phone");
                                if (phone != null && !phone.equals("")) {
                                    if (phone.contains(",")) {
                                        String[] split = phone.split(",");
                                        for (String s : split) {
                                            PageData pageData1 = new PageData();
                                            pageData1.put("phoneid", pageData.get("id"));
                                            pageData1.put("phone", s);
                                            alist.add(pageData1);
                                        }
                                    } else {
                                        PageData pageData1 = new PageData();
                                        pageData1.put("phoneid", pageData.get("id"));
                                        pageData1.put("phone", phone);
                                        alist.add(pageData1);
                                    }
                                }

                            }
                        }
                        if (cate.contains("2")) {
                            List<PageData> getaddlist = msgTempManager.getaddlist(pd);
                            for (PageData pageData : getaddlist) {
                                String tel1 = pageData.getString("tel1");
                                if ((!tel1.equals("") && tel1 != null)) {
                                    PageData pageData1 = new PageData();
                                    pageData1.put("phoneid", pageData.get("id"));
                                    pageData1.put("phone", pageData.getString("tel1") != null ? pageData.getString("tel1") : "");
                                    alist.add(pageData1);
                                }
                            }
                        }
                    }


                    object.put("data", alist);
                    object.put("success", "true");
                }
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

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
