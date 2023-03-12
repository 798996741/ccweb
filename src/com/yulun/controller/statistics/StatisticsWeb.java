package com.yulun.controller.statistics;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.entity.Statistics;
import com.yulun.service.NoticeManager;
import com.yulun.service.StatisticsManager;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticsWeb implements CommonIntefate {

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name = "statisticsService")
    private StatisticsManager statisticsManager;
    @Resource(name = "noticeService")
    private NoticeManager noticeManager;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object = new JSONObject();
//        try {
            PageData pd = new PageData();
            Page page = new Page();
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData pd_stoken = new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                String zxid = pd_token.getString("ZXID");
                String zxz = pd_token.getString("ZXZ");
                if (cmd.equals("zxgrbb")) {
                    HashMap<String, Statistics> map = new HashMap<>();
                    PageData pageData1 = new PageData();
                    pageData1.put("zxid", json.getString("zxid"));
                    List<PageData> getxzid = statisticsManager.getxzid(pageData1);
                    for (PageData xzid : getxzid) {
                        map.put(xzid.getString("zxid"), new Statistics());
                        System.out.println(xzid.getString("zxid"));
                    }
                    pd.put("starttime", json.getString("starttime"));
                    pd.put("endtime", json.getString("endtime"));
                    pd.put("thfx", "0");
                    pd.put("zxid", json.getString("zxid"));

                    List<PageData> ldllist = statisticsManager.zxCallNumCount(pd);
                    List<PageData> hrzsclist = statisticsManager.zxCallNumSum(pd);

                    PageData pd2 = new PageData();
                    pd2.put("starttime", json.getString("starttime"));
                    pd2.put("endtime", json.getString("endtime"));
                    pd2.put("thfx", "0");
                    pd2.put("inmark", "0");
                    pd2.put("zxid", json.getString("zxid"));
                    List<PageData> nbhjsllist = statisticsManager.zxCallNumCount(pd2);
                    List<PageData> nbhjzsclist = statisticsManager.zxCallNumSum(pd2);

                    PageData pd3 = new PageData();
                    pd3.put("starttime", json.getString("starttime"));
                    pd3.put("endtime", json.getString("endtime"));
                    pd3.put("thfx", "1");
                    pd3.put("zxid", json.getString("zxid"));
                    List<PageData> wbhjzllist = statisticsManager.zxCallNumCount(pd3);
                    List<PageData> wbhjzsclist = statisticsManager.zxCallNumSum(pd3);

                    PageData pd4 = new PageData();
                    pd4.put("starttime", json.getString("starttime"));
                    pd4.put("endtime", json.getString("endtime"));
                    pd4.put("zxid", json.getString("zxid"));
                    List<PageData> thzsclist = statisticsManager.zxCallNumSum(pd4);

                    List<PageData> getcgjtl = statisticsManager.getcgjtl(pd4);
                    List<PageData> getxlfql = statisticsManager.getxlfql(pd4);

                    for (Map.Entry<String, Statistics> entry : map.entrySet()) {
                        if (ldllist.size() == 1 && null == ldllist.get(0) || ldllist.size() == 0) {
                            entry.getValue().setLdl("0");

                        } else {
                            for (PageData pageDatum : ldllist) {
                                if (entry.getKey().equals(pageDatum.get("zxid").toString())) {
                                    entry.getValue().setLdl(pageDatum.get("callnum").toString() == "" ? "0" : pageDatum.get("callnum").toString());
                                } else if (entry.getValue().getLdl() == null || entry.getValue().getLdl() == "") {
                                    entry.getValue().setLdl("0");
                                }
                            }
                        }

                        if (hrzsclist.size() == 1 && null == hrzsclist.get(0) || hrzsclist.size() == 0) {
                            entry.getValue().setHrzsc("0");

                        } else {
                            for (PageData pageData : hrzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setHrzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getHrzsc() == null || entry.getValue().getHrzsc() == "") {
                                    entry.getValue().setHrzsc("0");
                                }
                            }
                        }

                        if (nbhjsllist.size() == 1 && null == nbhjsllist.get(0) || nbhjsllist.size() == 0) {
                            entry.getValue().setNbhjsl("0");

                        } else {
                            for (PageData pageData : nbhjsllist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setNbhjsl(pageData.get("callnum").toString() == "" ? "0" : pageData.get("callnum").toString());
                                } else if (entry.getValue().getNbhjsl() == null || entry.getValue().getNbhjsl() == "") {
                                    entry.getValue().setNbhjsl("0");
                                }
                            }
                        }
                        if (nbhjzsclist.size() == 1 && null == nbhjzsclist.get(0) || nbhjzsclist.size() == 0) {
                            entry.getValue().setNbhjzsc("0");
                        } else {
                            for (PageData pageData : nbhjzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setNbhjzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getNbhjzsc() == null || entry.getValue().getNbhjzsc() == "") {
                                    entry.getValue().setNbhjzsc("0");
                                }
                            }
                        }

                        if (wbhjzllist.size() == 1 && null == wbhjzllist.get(0) || wbhjzllist.size() == 0) {
                            entry.getValue().setWbhjl("0");

                        } else {
                            for (PageData pageData : wbhjzllist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setWbhjl(pageData.get("callnum").toString() == "" ? "0" : pageData.get("callnum").toString());
                                } else if (entry.getValue().getWbhjl() == null || entry.getValue().getWbhjl() == "") {
                                    entry.getValue().setWbhjl("0");
                                }
                            }
                        }

                        if (wbhjzsclist.size() == 1 && null == wbhjzsclist.get(0) || wbhjzsclist.size() == 0) {
                            entry.getValue().setWbhjzsc("0");

                        } else {
                            for (PageData pageData : wbhjzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setWbhjzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getWbhjzsc() == null || entry.getValue().getWbhjzsc() == "") {
                                    entry.getValue().setWbhjzsc("0");
                                }
                            }
                        }

                        if (thzsclist.size() == 1 && null == thzsclist.get(0) || thzsclist.size() == 0) {
                            entry.getValue().setThzsc("0");

                        } else {
                            for (PageData pageData : thzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setThzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getThzsc() == null || entry.getValue().getThzsc() == "") {
                                    entry.getValue().setThzsc("0");
                                }
                            }
                        }

                        if (getcgjtl.size() == 1 && null == getcgjtl.get(0) || getcgjtl.size() == 0) {
                            entry.getValue().setChjtl("0");
                        } else {
                            for (PageData pageData : getcgjtl) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setChjtl(pageData.get("cgjtl").toString());
                                } else {
                                    entry.getValue().setChjtl("0");
                                }
                            }
                        }

                        if (getxlfql.size() == 1 && null == getxlfql.get(0) || getxlfql.size() == 0) {
                            entry.getValue().setXlfql("0");
                        } else {
                            for (PageData pageData : getcgjtl) {
                                for (PageData pageData2 : getxlfql) {
                                    if (entry.getKey().equals(pageData.get("zxid").equals(pageData2.get("zxid").toString()))) {
                                        int i = Integer.parseInt(pageData2.get("xlfql").toString()) - Integer.parseInt(pageData.get("cgjtl").toString());
                                        entry.getValue().setXlfql(i + "");
                                    } else {
                                        entry.getValue().setXlfql("0");
                                    }
                                }
                            }
                        }

                    }
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    object.put("time", starttime.substring(11, starttime.length()) + " " + endtime.substring(11, endtime.length()));
                    object.put("day", starttime.substring(0, 11) + " " + endtime.substring(0, 11));
                    object.put("data", map);
                    object.put("success", "true");
                    System.out.println(map.toString());
                    System.out.println(map.get("601"));
                } else if (cmd.equals("zxOpeLog")) {
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);
                    System.out.println(users);
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("keywords", json.getString("keywords"));
//                    pd.put("zxid", json.getString("zxid"));
                    page.setPd(pd);
                    List<PageData> zxopeloglistPage = statisticsManager.zxopeloglistPage(page);
                    object.put("data", zxopeloglistPage);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                } else if (cmd.equals("zxCallLog")) {
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);
                    Integer pageIndex = json.getInteger("pageIndex");
                        page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("zxid", json.getString("zxid"));
                    pd.put("zjhm", json.getString("zjhm"));
                    pd.put("thfx", json.getString("thfx"));
                    pd.put("thlx", json.getString("thlx"));
                    pd.put("keywords", json.getString("keywords"));
                    page.setPd(pd);
                    List<PageData> zxcallloglistPage = statisticsManager.zxcallloglistPage(page);
                    object.put("data", zxcallloglistPage);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                } else if (cmd.equals("MYDTJ")) {
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);
                    String way = json.getString("way");
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("keywords", json.getString("keywords"));
                    pd.put("way", way);
                    page.setPd(pd);
                    if (way.equals("zxid")) {
                        List<PageData> list = statisticsManager.MYDTJlistPage(page);

                        object.put("data", list);
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    } else if (way.equals("day") || way.equals("week") || way.equals("month") || way.equals("year")) {
                        List<PageData> list = statisticsManager.MYDTJlistPage(page);

                        object.put("data", changetime(starttime, endtime, way, list));
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    }
                } else if (cmd.equals("ZXHWTJ")) {
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);
                    String way = json.getString("way");
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);

                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("way", way);
                    pd.put("zxid", json.getString("zxid"));
                    pd.put("keywords", json.getString("keywords"));
                    page.setPd(pd);
                    System.out.println(way);
                    if (way.equals("zxid")) {
                        List<PageData> list = statisticsManager.ZXHWTJlistPage(page);
                        object.put("data", list);
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    } else if (way.equals("day") || way.equals("week") || way.equals("month") || way.equals("year")) {
                        List<PageData> list = statisticsManager.ZXHWTJlistPage(page);

                        object.put("data", changetime(starttime, endtime, way, list));
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    }

                } else if (cmd.equals("XTHWTJ")) {
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    String way = json.getString("way");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("way", json.getString("way"));
                    pd.put("keywords", json.getString("keywords"));
                    page.setPd(pd);
                    List<PageData> list = statisticsManager.XTHWTJlistPage(page);
                    System.out.println(list);
                    object.put("data", changetime(starttime, endtime, way, list));
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if (cmd.equals("TimeTelStat")){
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("starttime",starttime);
                    pd.put("endtime",endtime);
                    page.setPd(pd);
                    List<PageData> list = statisticsManager.TimeTelStatlistPage(page);
                    System.out.println(list);
                    object.put("data", list);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if (cmd.equals("getzxhwtj")){
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);

                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("starttime",starttime);
                    pd.put("endtime",endtime);
                    pd.put("userids",users);
                    pd.put("name",json.getString("name"));
                    pd.put("keywords",json.getString("keywords"));
                    page.setPd(pd);
                    List<PageData> list = statisticsManager.getzxhwtjlistPage(page);
                    object.put("data", list);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());

                }else if (cmd.equals("getzxkf")){
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("userids",users);
                    pd.put("starttime",starttime);
                    pd.put("endtime",endtime);
                    pd.put("zxid",json.getString("zxid"));
                    pd.put("name",json.getString("name"));
                    pd.put("keywords",json.getString("keywords"));
                    page.setPd(pd);

                    List<PageData> list = statisticsManager.zxkflistPage(page);
                    object.put("data", list);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if (cmd.equals("getzxhwtjchar")){
                    PageData pageData = new PageData();
                    pageData.put("zxz",zxz);
                    pageData.put("id",zxid);
                    List<PageData> except = statisticsManager.getUserByZxz(pageData);
                    ArrayList<String> list1 = new ArrayList<>();
                    for (PageData pageData1 : except) {
                        list1.add(pageData1.getString("zxid"));
                    }
                    String users=list1.toString().substring(1,list1.toString().length()-1);
                    pd.put("userids",users);

                    String starttime = json.getString("starttime");
                    String endtime = json.getString("endtime");
                    if (starttime==null || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime==null || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("userids",users);
                    pd.put("starttime",starttime);
                    pd.put("endtime",endtime);
                    pd.put("zxid",json.getString("zxid"));
                    pd.put("name",json.getString("name"));
                    List<PageData> getzxhwtj = statisticsManager.getzxhwtj(pd);
//                    data:[{"name”:"呼入量","data":"1,2,3"},{"name”:"呼出量","data":"1,2,3"},{"name":"坐席","data":"601,602"}]
                    System.out.println(getzxhwtj);

                    ArrayList<HashMap<String, String>> hashList = new ArrayList<>();
                    HashMap<String, String> mapname      = new HashMap<>();
                    HashMap<String, String> maphrl      = new HashMap<>();
                    HashMap<String, String> mapyds      = new HashMap<>();
                    HashMap<String, String> mapydl      = new HashMap<>();
                    HashMap<String, String> mapcsyds    = new HashMap<>();
                    HashMap<String, String> mapjsydl    = new HashMap<>();
                    HashMap<String, String> mapthzsc    = new HashMap<>();
                    HashMap<String, String> maphrzsc    = new HashMap<>();
                    HashMap<String, String> maphrpjsc   = new HashMap<>();
                    HashMap<String, String> mapsmsc     = new HashMap<>();
                    HashMap<String, String> mapsmcs     = new HashMap<>();
                    HashMap<String, String> mapkxsc     = new HashMap<>();
                    HashMap<String, String> mapbccs     = new HashMap<>();
                    HashMap<String, String> mapbcsc     = new HashMap<>();
                    HashMap<String, String> mapgzsc     = new HashMap<>();
                    HashMap<String, String> mapgzztsc   = new HashMap<>();
                    HashMap<String, String> mapgslyl    = new HashMap<>();
                    HashMap<String, String> maphccs     = new HashMap<>();
                    HashMap<String, String> maphccgcs   = new HashMap<>();
                    HashMap<String, String> maphccgl    = new HashMap<>();
                    HashMap<String, String> maphcthsc   = new HashMap<>();
                    HashMap<String, String> maphcpjthsc = new HashMap<>();
                    HashMap<String, String> mapzdhcthsc = new HashMap<>();
                    HashMap<String, String> maphjzys    = new HashMap<>();
                    HashMap<String, String> maphjzycs   = new HashMap<>();
                    HashMap<String, String> mapdlcs     = new HashMap<>();
                    HashMap<String, String> mapmyd      = new HashMap<>();
                    ArrayList<String> namelist       = new ArrayList<>();
                    ArrayList<String> hrllist       = new ArrayList<>();
                    ArrayList<String> ydslist       = new ArrayList<>();
                    ArrayList<String> ydllist       = new ArrayList<>();
                    ArrayList<String> csydslist    = new ArrayList<>();
                    ArrayList<String> jsydllist    = new ArrayList<>();
                    ArrayList<String> thzsclist    = new ArrayList<>();
                    ArrayList<String> hrzsclist    = new ArrayList<>();
                    ArrayList<String> hrpjslistc   = new ArrayList<>();
                    ArrayList<String> smsclist    = new ArrayList<>();
                    ArrayList<String> smcslist    = new ArrayList<>();
                    ArrayList<String> kxsclist    = new ArrayList<>();
                    ArrayList<String> bccslist    = new ArrayList<>();
                    ArrayList<String> bcsclist    = new ArrayList<>();
                    ArrayList<String> gzsclist    = new ArrayList<>();
                    ArrayList<String> gzztslistc   = new ArrayList<>();
                    ArrayList<String> gslyllist    = new ArrayList<>();
                    ArrayList<String> hccslist    = new ArrayList<>();
                    ArrayList<String> hccgclists   = new ArrayList<>();
                    ArrayList<String> hccgllist    = new ArrayList<>();
                    ArrayList<String> hcthslistc   = new ArrayList<>();
                    ArrayList<String> hcpjtlisthsc = new ArrayList<>();
                    ArrayList<String> zdhctlisthsc = new ArrayList<>();
                    ArrayList<String> hjzyslist    = new ArrayList<>();
                    ArrayList<String> hjzyclists   = new ArrayList<>();
                    ArrayList<String> dlcslist    = new ArrayList<>();
                    ArrayList<String> mydlist    = new ArrayList<>();




                    for (PageData pd1 : getzxhwtj) {
                        namelist    .add(pd1.get("name"      ).toString());
                        hrllist     .add(pd1.get("hrl"      ).toString());
                        ydslist     .add(pd1.get("yds"      ).toString());
                        ydllist     .add(pd1.get("ydl"      ).toString());
                        csydslist   .add(pd1.get("csyds"    ).toString());
                        jsydllist   .add(pd1.get("jsydl"    ).toString());
                        thzsclist   .add(pd1.get("thzsc"    ).toString());
                        hrzsclist   .add(pd1.get("hrzsc"    ).toString());
                        hrpjslistc  .add(pd1.get("hrpjsc"   ).toString());
                        smsclist    .add(pd1.get("smsc"     ).toString());
                        smcslist    .add(pd1.get("smcs"     ).toString());
                        kxsclist    .add(pd1.get("kxsc"     ).toString());
                        bccslist    .add(pd1.get("bccs"     ).toString());
                        bcsclist    .add(pd1.get("bcsc"     ).toString());
                        gzsclist    .add(pd1.get("gzsc"     ).toString());
                        gzztslistc  .add(pd1.get("gzztsc"   ).toString());
                        gslyllist   .add(pd1.get("gslyl"    ).toString());
                        hccslist    .add(pd1.get("hccs"     ).toString());
                        hccgclists  .add(pd1.get("hccgcs"   ).toString());
                        hccgllist   .add(pd1.get("hccgl"    ).toString());
                        hcthslistc  .add(pd1.get("hcthsc"   ).toString());
                        hcpjtlisthsc.add(pd1.get("hcpjthsc").toString());
                        zdhctlisthsc.add(pd1.get("zdhcthsc").toString());
                        hjzyslist   .add("0");
                        hjzyclists  .add(pd1.get("hjzycs"   ).toString());
                        dlcslist    .add(pd1.get("hjzycs"   ).toString());
                        mydlist     .add(pd1.get("hjzycs"   ).toString());

                    }
                    mapname    .put("name","坐席"      );
                    maphrl     .put("name","呼入量"      );
                    mapyds     .put("name","应答数"      );
                    mapydl     .put("name","应答率"      );
                    mapcsyds   .put("name","超时应答数"    );
                    mapjsydl   .put("name","应答及时率"    );
                    mapthzsc   .put("name","通话总时长"    );
                    maphrzsc   .put("name","呼入通话时长"   );
                    maphrpjsc  .put("name","呼入平均通话时长");
                    mapsmsc    .put("name","示忙时长"     );
                    mapsmcs    .put("name","示忙次数"     );
                    mapkxsc    .put("name","空闲时间"     );
                    mapbccs    .put("name","保持次数"     );
                    mapbcsc    .put("name","保持时长"     );
                    mapgzsc    .put("name","工作时长"     );
                    mapgzztsc  .put("name","工作状态时长");;
                    mapgslyl   .put("name","工时利用率"    );
                    maphccs    .put("name","呼出次数"     );
                    maphccgcs  .put("name","呼出成功次数"   );
                    maphccgl   .put("name","呼出成功率"    );
                    maphcthsc  .put("name","呼出通话时长"   );
                    maphcpjthsc.put("name","呼出平均通话时长");
                    mapzdhcthsc.put("name","最大通话时长"   );
                    maphjzys   .put("name","呼叫转移数"    );
                    maphjzycs  .put("name","呼叫转移时长"   );
                    mapdlcs    .put("name","登录次数"     );
                    mapmyd     .put("name","满意度"      );
                    mapname    .put("data", StringUtils.strip(namelist    .toString(),"[]"));
                    maphrl     .put("data", StringUtils.strip(hrllist     .toString(),"[]"));
                    mapyds     .put("data", StringUtils.strip(ydslist     .toString(),"[]"));
                    mapydl     .put("data", StringUtils.strip(ydllist     .toString(),"[]"));
                    mapcsyds   .put("data", StringUtils.strip(csydslist   .toString(),"[]"));
                    mapjsydl   .put("data", StringUtils.strip(jsydllist   .toString(),"[]"));
                    mapthzsc   .put("data", StringUtils.strip(thzsclist   .toString(),"[]"));
                    maphrzsc   .put("data", StringUtils.strip(hrzsclist   .toString(),"[]"));
                    maphrpjsc  .put("data", StringUtils.strip(hrpjslistc  .toString(),"[]"));
                    mapsmsc    .put("data", StringUtils.strip(smsclist    .toString(),"[]"));
                    mapsmcs    .put("data", StringUtils.strip(smcslist    .toString(),"[]"));
                    mapkxsc    .put("data", StringUtils.strip(kxsclist    .toString(),"[]"));
                    mapbccs    .put("data", StringUtils.strip(bccslist    .toString(),"[]"));
                    mapbcsc    .put("data", StringUtils.strip(bcsclist    .toString(),"[]"));
                    mapgzsc    .put("data", StringUtils.strip(gzsclist    .toString(),"[]"));
                    mapgzztsc  .put("data", StringUtils.strip(gzztslistc  .toString(),"[]"));
                    mapgslyl   .put("data", StringUtils.strip(gslyllist   .toString(),"[]"));
                    maphccs    .put("data", StringUtils.strip(hccslist    .toString(),"[]"));
                    maphccgcs  .put("data", StringUtils.strip(hccgclists  .toString(),"[]"));
                    maphccgl   .put("data", StringUtils.strip(hccgllist   .toString(),"[]"));
                    maphcthsc  .put("data", StringUtils.strip(hcthslistc  .toString(),"[]"));
                    maphcpjthsc.put("data", StringUtils.strip(hcpjtlisthsc.toString(),"[]"));
                    mapzdhcthsc.put("data", StringUtils.strip(zdhctlisthsc.toString(),"[]"));
                    maphjzys   .put("data", StringUtils.strip(hjzyslist   .toString(),"[]"));
                    maphjzycs  .put("data", StringUtils.strip(hjzyclists  .toString(),"[]"));
                    mapdlcs    .put("data", StringUtils.strip(dlcslist    .toString(),"[]"));
                    mapmyd     .put("data", StringUtils.strip(mydlist     .toString(),"[]"));

                    hashList.add(mapname    );
                    hashList.add(maphrl     );
                    hashList.add(mapyds     );
                    hashList.add(mapydl     );
                    hashList.add(mapcsyds   );
                    hashList.add(mapjsydl   );
                    hashList.add(mapthzsc   );
                    hashList.add(maphrzsc   );
                    hashList.add(maphrpjsc  );
                    hashList.add(mapsmsc    );
                    hashList.add(mapsmcs    );
                    hashList.add(mapkxsc    );
                    hashList.add(mapbccs    );
                    hashList.add(mapbcsc    );
                    hashList.add(mapgzsc    );
                    hashList.add(mapgzztsc  );
                    hashList.add(mapgslyl   );
                    hashList.add(maphccs    );
                    hashList.add(maphccgcs  );
                    hashList.add(maphccgl   );
                    hashList.add(maphcthsc  );
                    hashList.add(maphcpjthsc);
                    hashList.add(mapzdhcthsc);
                    hashList.add(maphjzys   );
                    hashList.add(maphjzycs  );
                    hashList.add(mapdlcs    );
                    hashList.add(mapmyd     );
                    object.put("data", hashList);
                    object.put("success", "true");
                }else if (cmd.equals("homeservice")){
                    List<PageData> list = statisticsManager.HomeService(pd);
                    object.put("data", list);
                    object.put("success", "true");
                }else if(cmd.equals("statechange")){
                    List<PageData> list = statisticsManager.StatueChange();
                    if(list.size()>0){
                        for (PageData pagedate: list
                        ) {
                            if("示闲".equals(pagedate.getString("note"))){
                                object.put("kxzsc",pagedate.get("ljsj"));
                                object.put("ksgzzcs",pagedate.get("COUNT"));
                            }
                            if("示忙".equals(pagedate.getString("note"))){
                                object.put("mlzsc",pagedate.get("ljsj"));
                                object.put("ztgzzcs",pagedate.get("COUNT"));
                            }
                            if("应答".equals(pagedate.getString("note"))){
                                object.put("thzsc",pagedate.get("ljsj"));
                                object.put("thzcs",pagedate.get("COUNT"));
                            }
                        }
                    }else{
                        object.put("msg", "暂无数据");
                    }

                    object.put("success", "true");
                }
            } else {
                object.put("success", "false");
                object.put("msg", "登录超时，请重新登录");
            }
//        } catch (Exception e) {
//            object.put("success", "false");
//            object.put("msg", "异常");
//        }
        return object;
    }

    public ArrayList<PageData> judgetime(String starttime, String endtime, String way) throws ParseException {

        Integer startmonth = Integer.parseInt(starttime.substring(5, 7));
        Integer endmonth = Integer.parseInt(endtime.substring(5, 7));
        Integer startyear = Integer.parseInt(starttime.substring(0, 4));
        Integer endyear = Integer.parseInt(endtime.substring(0, 4));
        Integer startday = Integer.parseInt(starttime.substring(8, 10));
        Integer endday = Integer.parseInt(endtime.substring(8, 10));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(starttime);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date1);
        Integer weekNumbe1 = calendar.get(Calendar.WEEK_OF_YEAR);
        Date date2 = format.parse(endtime);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);
        calendar1.setTime(date2);
        Integer weekNumbe2 = calendar1.get(Calendar.WEEK_OF_YEAR);
        ArrayList<PageData> strings = new ArrayList<>();
        if (way.equals("day")) {
            Integer day = endday - startday;
            for (int i = startday; i <= endday; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        } else if (way.equals("month")) {
            Integer month = endmonth - startmonth;
            for (int i = startmonth; i <= endmonth; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        } else if (way.equals("year")) {
            Integer year = endyear - startyear;
            for (int i = startyear; i <= endyear; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        } else if (way.equals("week")) {
            int week = weekNumbe2 - 1 - weekNumbe1 + 1;
            System.out.println(weekNumbe2);
            System.out.println(weekNumbe1);
            for (int i = weekNumbe1; i <= weekNumbe2; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        }
        return strings;
    }

    public List<PageData> changetime(String starttime, String endtime, String way, List<PageData> list) {

        if (way.equals("day")) {
            for (PageData pageData : list) {
                pageData.put("time", starttime.substring(0, 7) + "-" + Integer.parseInt(pageData.get("time").toString()));
            }
        } else if (way.equals("month")) {
            for (PageData pageData : list) {
                pageData.put("time", starttime.substring(8, 10) + "-" + Integer.parseInt(pageData.get("time").toString()));
            }
        } else if (way.equals("week")) {

        }
        return list;
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }

    public String getday() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
