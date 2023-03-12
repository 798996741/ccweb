package com.yulun.controller.msgtemp;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.yulun.service.MsgTempManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/sendmsg")
public class MsgController extends BaseController {
    @Resource(name = "msgTempService")
    private MsgTempManager msgTempManager;


    @RequestMapping(value = "/gomsglist.do")
    public ModelAndView gomsglist() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        Page page = new Page();
        pd = this.getPageData();
        page.setCurrentPage(1);
        page.setShowCount(9999);
        page.setPd(pd);
        List<PageData> list = msgTempManager.findMsgLogAlllistPage(page);

        mv.addObject("data", list);
        mv.addObject("pd", pd);
        mv.setViewName("msglog/msglog_list");
        return mv;
    }

    @RequestMapping(value = "/gomsgdatils.do")
    public ModelAndView gomsgdatils() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String ids = pd.getString("ids");
        String cate = "0,1";
//        List getphone = getphone(ids, cate);
//        List getacceptman = getacceptman("", cate);

        PageData obj = msgTempManager.gomsgdatils(pd);
        mv.addObject("pd", obj);
//        mv.addObject("phone", getphone);


        mv.setViewName("msglog/msglog_edit");
        return mv;
    }

    @RequestMapping(value = "/gosendmsg.do")
    public ModelAndView gosendmsg() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Page page = new Page();
        page.setCurrentPage(1);
        page.setShowCount(9999);
        page.setPd(pd);
        String ids = pd.getString("ids");
        String cate = "0,1";
//        List getacceptman = getacceptman("", cate);
        List getphone = getphone("", cate);
        System.out.println(getphone);
        List<PageData> msgtemp = msgTempManager.findMsgAlllistPage(page);
        mv.addObject("msgtemp", msgtemp);
        mv.addObject("getphone", getphone);

        mv.setViewName("msglog/sendmsg_list");
        return mv;
    }

    @RequestMapping(value = "/savemsg.do")
    public ModelAndView savemsg() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd =this.getPageData();
        System.out.println("pd:"+pd);
//        String phones = json.getString("phones");
//        String phonesid = json.getString("phonesid");
//        pd.put("uid", json.get("uid"));


//            String[] phonessplit = phones.split(",");
//            String[] phonesidsplit = phonesid.split(",");
//            for (int i = 0; i < phonessplit.length; i++) {
        String phones = pd.getString("phone");
        if (StringUtils.isNotEmpty(phones)){
            String[] split = phones.split(",");
            String phoneid = split[0];
            String phone = split[1];
            PageData pageData = new PageData();
            pageData.put("id", phoneid);
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
            pd.put("time", getTime());
            pd.put("way", "0");
//                pd.put("temp", json.getString("temp"));
//                pd.put("content", json.getString("content"));
//                pd.put("acceptman", json.getString("acceptman"));

            pd.put("phone", phone);
            pd.put("sendman", Jurisdiction.getUsername());
            pd.put("state", "0");
            pd.put("phones", phone);
            pd.put("phonesid", phoneid);
            pd.put("phoneid", phoneid);
            pd.put("source", "2");
            pd.put("czman", Jurisdiction.getUsername());
            pd.put("czdate", getTime());
            msgTempManager.insertMsgLog(pd);
        }
        mv.setViewName("msglog/sendmsg_list");
        return mv;
//            }
    }

    public List getphone(String ids, String cate) throws Exception {
        PageData pd = new PageData();
//        String cate = json.getString("cate");
        System.out.println(cate);

        ArrayList<PageData> alist = new ArrayList<>();
//        String ids = json.getString("id");
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
                            pageData3.put("name", pageData.get("name"));
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
                                pageData1.put("name", pageData.getString("name"));
                                alist.add(pageData1);
                            }
                        } else {
                            PageData pageData1 = new PageData();
                            pageData1.put("phoneid", pageData.get("id"));
                            pageData1.put("phone", phone);
                            pageData1.put("name", pageData.getString("name"));
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
                        pageData1.put("name", pageData.get("id"));
                        alist.add(pageData1);
                    }
                }
            }
        }
        return alist;
    }

    public List getacceptman(String ids, String cate) throws Exception {
        PageData pd = new PageData();
//        String cate = json.getString("cate");
        pd.put("cate", cate);
        System.out.println(cate);
        ArrayList<PageData> alist = new ArrayList<>();
        pd.put("id", ids);
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
        return alist;
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
