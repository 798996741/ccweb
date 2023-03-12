package com.yulun.controller.complain;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.yulun.service.ComplainManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value="/complain")
public class ComplainController extends BaseController {

    @Resource(name="complainService")
    private ComplainManager complainManager;

    @Resource(name="userService")
    private UserManager userService;

    @Resource(name="areamanageService")
    private AreaManageManager areamanageService;

    @RequestMapping(value="/golist.do")
    public ModelAndView golist() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd=this.getPageData();
        PageData pd3 = new PageData();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String starttime = pd.getString("starttime");
        if (null != starttime && !"".equals(starttime)) {
            pd3.put("starttime", starttime+" 00:00:00");
        }else if (null == starttime || "".equals(starttime)){
            pd3.put("starttime", dateFormat.format(date)+"-01 00:00:00");
            pd.put("starttime", dateFormat.format(date)+"-01");
        }
        String endtime = pd.getString("endtime");
        if (null != endtime && !"".equals(endtime)) {
            pd3.put("endtime", endtime+" 23:59:59");
        }else if (null == endtime || "".equals(endtime)){
            pd3.put("endtime", dateFormat.format(date)+"-31 23:59:59");
            pd.put("endtime", dateFormat.format(date)+"-31");
        }
        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)) {
                //是 展示全部数据

                System.out.println(pd+"pd");
                pd3.put("tsdept","1");
                pd3.put("tsclassify","1");
                List<PageData> getData = complainManager.getData(pd3);
                PageData pd2 = new PageData();
                List<PageData> getArea = complainManager.getArea(pd2);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : getArea) {
                    map.put(pageData.getString("AREA_CODE"),0);
                }
                for (PageData getDatum : getData) {
                    String tsdept = getDatum.getString("tsdept");
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (entry.getKey().equals(s)){
                                    Integer add=map.get(s);
                                    map.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map.get(tsdept);
                            map.put(tsdept,add+num);
                        }
                    }
                }
                List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                System.out.println(list);
                mv.addObject("pd", pd);
                mv.addObject("positon", getArea);
                mv.addObject("data", map);
                mv.addObject("data1", list);
                mv.setViewName("statismng/complaint/complaint_list");
            }else {
                //否 按部门科室进行展示
                System.out.println("科室统计");
                //获取部门科室列表


                UserPd.put("AREA_CODE",dwbm);
                PageData byAreaCode = areamanageService.findByAreaCode(UserPd);
                String area_level = byAreaCode.getString("AREA_LEVEL");
                String area_id = "";
                if ("3".equals(area_level)){
                    area_id = byAreaCode.getString("AREA_ID");
                }else if ("4".equals(area_level)){
                    area_id = byAreaCode.getString("PARENT_ID");
                }
                PageData area_idpd = new PageData();
                area_idpd.put("AREA_ID",area_id);
                Page page = new Page();
                page.setPd(area_idpd);
                page.setCurrentPage(1);
                page.setShowCount(999999);
                List<PageData> deptlist = areamanageService.list(page);
                ArrayList<String> areaCodeList = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    areaCodeList.add(pageData.getString("AREA_CODE"));
                }

                String strip = StringUtils.strip(areaCodeList.toString(), "[]");

                pd.put("tsdept","1");
                pd.put("tsclassify","1");
                pd.put("offices",strip);
                List<PageData> officeData = complainManager.getOfficeData(pd);
                System.out.println(officeData+":officeData");
                System.out.println(strip+":sb.toString()");
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : deptlist) {
                    map.put(pageData.getString("AREA_CODE"),0);
                }
                for (PageData officeDatum : officeData) {
                    String tsdept = officeDatum.getString("office");
                    Integer num = Integer.parseInt(officeDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (entry.getKey().equals(s)){
                                    Integer add=map.get(s);
                                    map.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map.get(tsdept);
                            map.put(tsdept,add+num);
                        }
                    }
                }
                List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon", deptlist);
                mv.addObject("data", map);
                mv.addObject("data1", list);
                mv.setViewName("statismng/complaint/complaint_list");

            }
        }


        return mv;
    }
    @RequestMapping(value="/tstypedata.do")
    public ModelAndView tsbmtsdata() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData PageData = new PageData();
        PageData pd = this.getPageData();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)) {
                List<PageData> area = complainManager.getArea(pd);
                for (PageData pageData : area) {
                    if (pageData.get("NAME").equals(pd.getString("tsdept"))){
                        pd.put("tsdept",pageData.get("AREA_CODE"));
                    }
                }
                PageData pd2 = new PageData();
                pd2.put("tsdept",pd.getString("tsdept"));
                pd2.put("tsbigtype",1);

                String starttime = pd.getString("starttime");
                if (null != starttime && !"".equals(starttime)) {
                    pd2.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime || "".equals(starttime)){
                    pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd2.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }

                pd2.put("tsclassify","1");
                List<PageData> getData = complainManager.getData(pd2);
                PageData pd3 = new PageData();
                pd3.put("bianma","014-");
                List<PageData> dictionaries = complainManager.getDictionaries(pd3);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : dictionaries) {
                    map.put(ClearBracket(pageData.getString("name")),0);
                }
                for (PageData getDatum : getData) {
                    String tsbigtype = ClearBracket(getDatum.getString("tsbigtype"));
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getKey().equals(tsbigtype)){
                            Integer add=map.get(tsbigtype);
                            map.put(tsbigtype,add+num);
                        }
                    }
                }
                Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Integer> next = iterator.next();
                    if (next.getValue()==0){
                        iterator.remove();
                    }
                }

                List<Map.Entry<String, Integer>> list1 = new ArrayList<>(map.entrySet());
                Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon1", dictionaries);
                mv.addObject("list1", list1);
                mv.addObject("data1", map);

                PageData pd4 = new PageData();
                pd4.put("tsdept",pd.getString("tsdept"));
                pd4.put("tstypename",1);
                String starttime2 = pd.getString("starttime");
                if (null != starttime2 && !"".equals(starttime2)) {
                    pd4.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime2 || "".equals(starttime2)){
                    pd4.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd4.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime2 = pd.getString("endtime");
                if (null != endtime2 && !"".equals(endtime2)) {
                    pd4.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime2 || "".equals(endtime2)){
                    pd4.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd4.put("tsclassify","1");
                List<PageData> getData1 = complainManager.getData(pd4);
                System.out.println(getData1);
                String tstypenamecode = pd.getString("tstypenamecode");
                if (StringUtils.isNotEmpty(tstypenamecode)){
                    PageData pageData = new PageData();
                    pageData.put("name",tstypenamecode);
                    List<PageData> getsmallbybig = complainManager.getsmallbybig(pageData);
                    System.out.println(getsmallbybig);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData data : getsmallbybig) {
                        map2.put(data.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    System.out.println(pd);

                    System.out.println(map2);
                    System.out.println(list2);
                    mv.addObject("positon2", getsmallbybig);
                    mv.addObject("data2", map2);
                    mv.addObject("list2", list2);
                }else {
                    PageData pd5 = new PageData();
                    pd5.put("bianma","014-");
                    List<PageData> dictionaries2 = complainManager.getDictionaries2(pd5);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData pageData : dictionaries2) {
                        map2.put(pageData.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });

                    mv.addObject("positon2", dictionaries2);
                    mv.addObject("data2", map2);
                    mv.addObject("list2", list2);
                }


                mv.addObject("area", area);
            }else {

                //获取部门科室列表
                UserPd.put("NAME",pd.getString("tsdept"));
                PageData areaByName = complainManager.getAreaByName(UserPd);
                String area_code = areaByName.getString("AREA_CODE");


                PageData pd2 = new PageData();
                pd2.put("tsdept",area_code);
                pd2.put("tsbigtype",1);

                String starttime = pd.getString("starttime");
                if (null != starttime && !"".equals(starttime)) {
                    pd2.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime || "".equals(starttime)){
                    pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd2.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }

                pd2.put("tsclassify","1");
                List<PageData> getData = complainManager.getOfficeData(pd2);
                PageData pd3 = new PageData();
                pd3.put("bianma","014-");
                List<PageData> dictionaries = complainManager.getDictionaries(pd3);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : dictionaries) {
                    map.put(ClearBracket(pageData.getString("name")),0);
                }
                for (PageData getDatum : getData) {
                    String tsbigtype = ClearBracket(getDatum.getString("tsbigtype"));
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getKey().equals(tsbigtype)){
                            Integer add=map.get(tsbigtype);
                            map.put(tsbigtype,add+num);
                        }
                    }
                }
                Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Integer> next = iterator.next();
                    if (next.getValue()==0){
                        iterator.remove();
                    }
                }

                List<Map.Entry<String, Integer>> list1 = new ArrayList<>(map.entrySet());
                Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon1", dictionaries);
                mv.addObject("list1", list1);
                mv.addObject("data1", map);

                PageData pd4 = new PageData();
                pd4.put("tsdept",area_code);
                pd4.put("tstypename",1);
                String starttime2 = pd.getString("starttime");
                if (null != starttime2 && !"".equals(starttime2)) {
                    pd4.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime2 || "".equals(starttime2)){
                    pd4.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd4.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime2 = pd.getString("endtime");
                if (null != endtime2 && !"".equals(endtime2)) {
                    pd4.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime2 || "".equals(endtime2)){
                    pd4.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd4.put("tsclassify","1");
                List<PageData> getData1 = complainManager.getOfficeData(pd4);
                System.out.println(getData1);
                String tstypenamecode = pd.getString("tstypenamecode");
                if (StringUtils.isNotEmpty(tstypenamecode)){
                    PageData pageData = new PageData();
                    pageData.put("name",tstypenamecode);
                    List<PageData> getsmallbybig = complainManager.getsmallbybig(pageData);

                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData data : getsmallbybig) {
                        map2.put(data.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    System.out.println(pd);

                    System.out.println(map2);
                    System.out.println(list2);
                    mv.addObject("positon2", getsmallbybig);
                    mv.addObject("data2", map2);
                    mv.addObject("list2", list2);
                }else {
                    PageData pd5 = new PageData();
                    pd5.put("bianma","014-");
                    List<PageData> dictionaries2 = complainManager.getDictionaries2(pd5);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData pageData : dictionaries2) {
                        map2.put(pageData.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });

                    mv.addObject("positon2", dictionaries2);
                    mv.addObject("data2", map2);
                    mv.addObject("list2", list2);
                }
//                mv.addObject("area", area);
            }
        }



        mv.setViewName("statismng/complaint/complaint_list2");
        return mv;

    }

    @RequestMapping(value="/goadviselist.do")
    public ModelAndView goadviselist() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String starttime = pd.getString("starttime");
        PageData pd3 = new PageData();
        if (null != starttime && !"".equals(starttime)) {
            pd3.put("starttime", starttime+" 00:00:00");
        }else if (null == starttime || "".equals(starttime)){
            pd3.put("starttime", dateFormat.format(date)+"-01 00:00:00");
            pd.put("starttime", dateFormat.format(date)+"-01");
        }
        String endtime = pd.getString("endtime");
        if (null != endtime && !"".equals(endtime)) {
            pd3.put("endtime", endtime+" 23:59:59");
        }else if (null == endtime || "".equals(endtime)){
            pd3.put("endtime", dateFormat.format(date)+"-31 23:59:59");
            pd.put("endtime", dateFormat.format(date)+"-31");
        }
        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)){

                pd3.put("tsdept","1");
                pd3.put("tsclassify","3");
                List<PageData> getData = complainManager.getData(pd3);
                PageData pd2 = new PageData();
                List<PageData> getArea = complainManager.getArea(pd2);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : getArea) {
                    map.put(pageData.getString("AREA_CODE"),0);
                }
                for (PageData getDatum : getData) {
                    String tsdept = getDatum.getString("tsdept");
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (entry.getKey().equals(s)){
                                    Integer add=map.get(s);
                                    map.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map.get(tsdept);
                            map.put(tsdept,add+num);
                        }
                    }
                }

                List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                System.out.println(pd);
                mv.addObject("pd", pd);
                mv.addObject("positon", getArea);
                mv.addObject("data", map);
                mv.addObject("data1", list);
                mv.setViewName("statismng/advise/advise_list");
            }else {
//否 按部门科室进行展示
                System.out.println("科室统计");
                //获取部门科室列表
                UserPd.put("AREA_CODE",dwbm);
                PageData byAreaCode = areamanageService.findByAreaCode(UserPd);
                String area_level = byAreaCode.getString("AREA_LEVEL");
                String area_id = "";
                if ("3".equals(area_level)){
                    area_id = byAreaCode.getString("AREA_ID");
                }else if ("4".equals(area_level)){
                    area_id = byAreaCode.getString("PARENT_ID");
                }
                PageData area_idpd = new PageData();
                area_idpd.put("AREA_ID",area_id);
                Page page = new Page();
                page.setPd(area_idpd);
                page.setCurrentPage(1);
                page.setShowCount(999999);
                List<PageData> deptlist = areamanageService.list(page);
                ArrayList<String> areaCodeList = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    areaCodeList.add(pageData.getString("AREA_CODE"));
                }

                String strip = StringUtils.strip(areaCodeList.toString(), "[]");

                pd.put("tsdept","1");
                pd.put("tsclassify","3");
                pd.put("offices",strip);
                List<PageData> officeData = complainManager.getOfficeData(pd);
                System.out.println(officeData+":officeData");
                System.out.println(strip+":sb.toString()");
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : deptlist) {
                    map.put(pageData.getString("AREA_CODE"),0);
                }
                for (PageData officeDatum : officeData) {
                    String tsdept = officeDatum.getString("office");
                    Integer num = Integer.parseInt(officeDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (entry.getKey().equals(s)){
                                    Integer add=map.get(s);
                                    map.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map.get(tsdept);
                            map.put(tsdept,add+num);
                        }
                    }
                }
                List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon", deptlist);
                mv.addObject("data", map);
                mv.addObject("data1", list);
                mv.setViewName("statismng/advise/advise_list");
            }
        }



        return mv;
    }

    @RequestMapping(value="/jytypedata.do")
    public ModelAndView jybmtsdata() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData PageData = new PageData();
        PageData pd = this.getPageData();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)){
                List<PageData> area = complainManager.getArea(pd);
                for (PageData pageData : area) {
                    if (pageData.get("NAME").equals(pd.getString("tsdept"))){
                        pd.put("tsdept",pageData.get("AREA_CODE"));
                    }
                }
                PageData pd2 = new PageData();
                pd2.put("tsdept",pd.getString("tsdept"));
                pd2.put("tsbigtype",1);

                String starttime = pd.getString("starttime");
                if (null != starttime && !"".equals(starttime)) {
                    pd2.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime || "".equals(starttime)){
                    pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd2.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd2.put("tsclassify","3");
                List<PageData> getData = complainManager.getData(pd2);
                System.out.println(getData+"getData");
                PageData pd3 = new PageData();
                pd3.put("bianma","014-");
                List<PageData> dictionaries = complainManager.getDictionaries(pd3);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : dictionaries) {
                    map.put(ClearBracket(pageData.getString("name")),0);
                }
                for (PageData getDatum : getData) {
                    String tsbigtype = ClearBracket(getDatum.getString("tsbigtype"));
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getKey().equals(tsbigtype)){
                            Integer add=map.get(tsbigtype);
                            map.put(tsbigtype,add+num);
                        }
                    }
                }
                Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Integer> next = iterator.next();
                    if (next.getValue()==0){
                        iterator.remove();
                    }
                }
                List<Map.Entry<String, Integer>> list1 = new ArrayList<>(map.entrySet());
                Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon1", dictionaries);
                mv.addObject("list1", list1);
                mv.addObject("data1", map);

                PageData pd4 = new PageData();
                pd4.put("tsdept",pd.getString("tsdept"));
                pd4.put("tstypename",1);
                String starttime2 = pd.getString("starttime");
                if (null != starttime2 && !"".equals(starttime2)) {
                    pd4.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime2 || "".equals(starttime2)){
                    pd4.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd4.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime2 = pd.getString("endtime");
                if (null != endtime2 && !"".equals(endtime2)) {
                    pd4.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime2 || "".equals(endtime2)){
                    pd4.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd4.put("tsclassify","3");
                List<PageData> getData1 = complainManager.getData(pd4);
                System.out.println(getData1+"getData1");


                String tstypenamecode = pd.getString("tstypenamecode");
                if (StringUtils.isNotEmpty(tstypenamecode)){
                    PageData pageData = new PageData();
                    pageData.put("name",tstypenamecode);
                    List<PageData> getsmallbybig = complainManager.getsmallbybig(pageData);
                    System.out.println(getsmallbybig);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData data : getsmallbybig) {
                        map2.put(data.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", getsmallbybig);
                    mv.addObject("data2", map2);
                    mv.addObject("list2", list2);
                }else {
                    PageData pd5 = new PageData();
                    pd5.put("bianma","014-");
                    List<PageData> dictionaries2 = complainManager.getDictionaries2(pd5);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData pageData : dictionaries2) {
                        map2.put(pageData.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", dictionaries2);
                    mv.addObject("data2", map2);
                    mv.addObject("area", area);
                    mv.addObject("list2", list2);
                }
                mv.setViewName("statismng/advise/advise_list2");
            }else {
                //获取部门科室列表
                UserPd.put("NAME",pd.getString("tsdept"));
                PageData areaByName = complainManager.getAreaByName(UserPd);
                String area_code = areaByName.getString("AREA_CODE");


                PageData pd2 = new PageData();
                pd2.put("tsdept",area_code);
                pd2.put("tsbigtype",1);

                String starttime = pd.getString("starttime");
                if (null != starttime && !"".equals(starttime)) {
                    pd2.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime || "".equals(starttime)){
                    pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd2.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd2.put("tsclassify","3");
                List<PageData> getData = complainManager.getOfficeData(pd2);
                System.out.println(getData+"getData");
                PageData pd3 = new PageData();
                pd3.put("bianma","014-");
                List<PageData> dictionaries = complainManager.getDictionaries(pd3);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : dictionaries) {
                    map.put(ClearBracket(pageData.getString("name")),0);
                }
                for (PageData getDatum : getData) {
                    String tsbigtype = ClearBracket(getDatum.getString("tsbigtype"));
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getKey().equals(tsbigtype)){
                            Integer add=map.get(tsbigtype);
                            map.put(tsbigtype,add+num);
                        }
                    }
                }
                Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Integer> next = iterator.next();
                    if (next.getValue()==0){
                        iterator.remove();
                    }
                }
                List<Map.Entry<String, Integer>> list1 = new ArrayList<>(map.entrySet());
                Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon1", dictionaries);
                mv.addObject("list1", list1);
                mv.addObject("data1", map);

                PageData pd4 = new PageData();
                pd4.put("tsdept",area_code);
                pd4.put("tstypename",1);
                String starttime2 = pd.getString("starttime");
                if (null != starttime2 && !"".equals(starttime2)) {
                    pd4.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime2 || "".equals(starttime2)){
                    pd4.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd4.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime2 = pd.getString("endtime");
                if (null != endtime2 && !"".equals(endtime2)) {
                    pd4.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime2 || "".equals(endtime2)){
                    pd4.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd4.put("tsclassify","3");
                List<PageData> getData1 = complainManager.getOfficeData(pd4);
                System.out.println(getData1+"getData1");


                String tstypenamecode = pd.getString("tstypenamecode");
                if (StringUtils.isNotEmpty(tstypenamecode)){
                    PageData pageData = new PageData();
                    pageData.put("name",tstypenamecode);
                    List<PageData> getsmallbybig = complainManager.getsmallbybig(pageData);
                    System.out.println(getsmallbybig);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData data : getsmallbybig) {
                        map2.put(data.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", getsmallbybig);
                    mv.addObject("data2", map2);
                    mv.addObject("list2", list2);
                }else {
                    PageData pd5 = new PageData();
                    pd5.put("bianma","014-");
                    List<PageData> dictionaries2 = complainManager.getDictionaries2(pd5);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData pageData : dictionaries2) {
                        map2.put(pageData.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", dictionaries2);
                    mv.addObject("data2", map2);

                    mv.addObject("list2", list2);
                }
                mv.setViewName("statismng/advise/advise_list2");
            }
        }





        return mv;

    }

    @RequestMapping(value="/gopraiselist.do")
    public ModelAndView gopraiselist() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)){
                PageData pd3 = new PageData();
                pd3.put("tsdept","1");
                String starttime = pd.getString("starttime");
                if (null != starttime && !"".equals(starttime)) {
                    pd3.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime || "".equals(starttime)){
                    pd3.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd3.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd3.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd3.put("tsclassify","2");
                List<PageData> getData = complainManager.getData(pd3);
                PageData pd2 = new PageData();
                List<PageData> getArea = complainManager.getArea(pd2);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : getArea) {
                    map.put(pageData.getString("AREA_CODE"),0);
                }
                for (PageData getDatum : getData) {
                    String tsdept = getDatum.getString("tsdept");
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (entry.getKey().equals(s)){
                                    Integer add=map.get(s);
                                    map.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map.get(tsdept);
                            map.put(tsdept,add+num);
                        }
                    }
                }

                List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                System.out.println(list);
                mv.addObject("pd", pd);
                mv.addObject("positon", getArea);
                mv.addObject("data", map);
                mv.addObject("data1", list);
                mv.setViewName("statismng/praise/praise_list");
            }else {
//否 按部门科室进行展示
                System.out.println("科室统计");
                //获取部门科室列表
                UserPd.put("AREA_CODE",dwbm);
                PageData byAreaCode = areamanageService.findByAreaCode(UserPd);
                String area_level = byAreaCode.getString("AREA_LEVEL");
                String area_id = "";
                if ("3".equals(area_level)){
                    area_id = byAreaCode.getString("AREA_ID");
                }else if ("4".equals(area_level)){
                    area_id = byAreaCode.getString("PARENT_ID");
                }
                PageData area_idpd = new PageData();
                area_idpd.put("AREA_ID",area_id);
                Page page = new Page();
                page.setPd(area_idpd);
                page.setCurrentPage(1);
                page.setShowCount(999999);
                List<PageData> deptlist = areamanageService.list(page);
                ArrayList<String> areaCodeList = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    areaCodeList.add(pageData.getString("AREA_CODE"));
                }

                String strip = StringUtils.strip(areaCodeList.toString(), "[]");

                pd.put("tsdept","1");
                pd.put("tsclassify","2");
                pd.put("offices",strip);
                List<PageData> officeData = complainManager.getOfficeData(pd);
                System.out.println(officeData+":officeData");
                System.out.println(strip+":sb.toString()");
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : deptlist) {
                    map.put(pageData.getString("AREA_CODE"),0);
                }
                for (PageData officeDatum : officeData) {
                    String tsdept = officeDatum.getString("office");
                    Integer num = Integer.parseInt(officeDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (entry.getKey().equals(s)){
                                    Integer add=map.get(s);
                                    map.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map.get(tsdept);
                            map.put(tsdept,add+num);
                        }
                    }
                }
                List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon", deptlist);
                mv.addObject("data", map);
                mv.addObject("data1", list);
                mv.setViewName("statismng/praise/praise_list");
            }

        }


        return mv;
    }

    @RequestMapping(value="/bytypedata.do")
    public ModelAndView bybmtsdata() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData PageData = new PageData();
        PageData pd = this.getPageData();

        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)){
                List<PageData> area = complainManager.getArea(pd);
                for (PageData pageData : area) {
                    if (pageData.get("NAME").equals(pd.getString("tsdept"))){
                        pd.put("tsdept",pageData.get("AREA_CODE"));
                    }
                }
                PageData pd2 = new PageData();
                pd2.put("tsdept",pd.getString("tsdept"));
                pd2.put("tsbigtype",1);
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                String starttime = pd.getString("starttime");
                if (null != starttime && !"".equals(starttime)) {
                    pd2.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime || "".equals(starttime)){
                    pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd2.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd2.put("tsclassify","2");
                List<PageData> getData = complainManager.getData(pd2);
                System.out.println(getData+"getData");
                PageData pd3 = new PageData();
                pd3.put("bianma","014-");
                List<PageData> dictionaries = complainManager.getDictionaries(pd3);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : dictionaries) {
                    map.put(ClearBracket(pageData.getString("name")),0);
                }
                for (PageData getDatum : getData) {
                    String tsbigtype = ClearBracket(getDatum.getString("tsbigtype"));
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getKey().equals(tsbigtype)){
                            Integer add=map.get(tsbigtype);
                            map.put(tsbigtype,add+num);
                        }
                    }
                }
                Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Integer> next = iterator.next();
                    if (next.getValue()==0){
                        iterator.remove();
                    }
                }
                List<Map.Entry<String, Integer>> list1 = new ArrayList<>(map.entrySet());
                Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon1", dictionaries);
                mv.addObject("list1", list1);
                mv.addObject("data1", map);

                PageData pd4 = new PageData();
                pd4.put("tsdept",pd.getString("tsdept"));
                pd4.put("tstypename",1);
                String starttime2 = pd.getString("starttime");
                if (null != starttime2 && !"".equals(starttime2)) {
                    pd4.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime2 || "".equals(starttime2)){
                    pd4.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd4.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime2 = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd4.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd4.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd4.put("tsclassify","2");
                List<PageData> getData1 = complainManager.getData(pd4);

                String tstypenamecode = pd.getString("tstypenamecode");
                if (StringUtils.isNotEmpty(tstypenamecode)){
                    PageData pageData = new PageData();
                    pageData.put("name",tstypenamecode);
                    List<PageData> getsmallbybig = complainManager.getsmallbybig(pageData);
                    System.out.println(getsmallbybig);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData data : getsmallbybig) {
                        map2.put(data.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", getsmallbybig);
                    mv.addObject("data2", map2);
                    mv.addObject("area", area);
                    mv.addObject("list2", list2);
                }else {
                    System.out.println(getData1+"getData1");
                    PageData pd5 = new PageData();
                    pd5.put("bianma","014-");
                    List<PageData> dictionaries2 = complainManager.getDictionaries2(pd5);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData pageData : dictionaries2) {
                        map2.put(pageData.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", dictionaries2);
                    mv.addObject("data2", map2);
                    mv.addObject("area", area);
                    mv.addObject("list2", list2);
                }

                mv.setViewName("statismng/praise/praise_list2");
            }else {
                //获取部门科室列表
                UserPd.put("NAME",pd.getString("tsdept"));
                PageData areaByName = complainManager.getAreaByName(UserPd);
                String area_code = areaByName.getString("AREA_CODE");


                PageData pd2 = new PageData();
                pd2.put("tsdept",area_code);
                pd2.put("tsbigtype",1);

                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                String starttime = pd.getString("starttime");
                if (null != starttime && !"".equals(starttime)) {
                    pd2.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime || "".equals(starttime)){
                    pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd2.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd2.put("tsclassify","2");
                List<PageData> getData = complainManager.getData(pd2);
                System.out.println(getData+"getData");
                PageData pd3 = new PageData();
                pd3.put("bianma","014-");
                List<PageData> dictionaries = complainManager.getDictionaries(pd3);
                HashMap<String, Integer> map = new HashMap<>();
                for (PageData pageData : dictionaries) {
                    map.put(ClearBracket(pageData.getString("name")),0);
                }
                for (PageData getDatum : getData) {
                    String tsbigtype = ClearBracket(getDatum.getString("tsbigtype"));
                    Integer num = Integer.parseInt(getDatum.get("num").toString());
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        if (entry.getKey().equals(tsbigtype)){
                            Integer add=map.get(tsbigtype);
                            map.put(tsbigtype,add+num);
                        }
                    }
                }
                Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Integer> next = iterator.next();
                    if (next.getValue()==0){
                        iterator.remove();
                    }
                }
                List<Map.Entry<String, Integer>> list1 = new ArrayList<>(map.entrySet());
                Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>()
                {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                    {
                        //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                        //按照value值，从大到小排序
                        return o2.getValue() - o1.getValue();

                        //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                    }
                });
                mv.addObject("pd", pd);
                mv.addObject("positon1", dictionaries);
                mv.addObject("list1", list1);
                mv.addObject("data1", map);

                PageData pd4 = new PageData();
                pd4.put("tsdept",area_code);
                pd4.put("tstypename",1);
                String starttime2 = pd.getString("starttime");
                if (null != starttime2 && !"".equals(starttime2)) {
                    pd4.put("starttime", starttime+" 00:00:00");
                }else if (null == starttime2 || "".equals(starttime2)){
                    pd4.put("starttime", dateFormat.format(date)+"-01 00:00:00");
                    pd4.put("starttime", dateFormat.format(date)+"-01");
                }
                String endtime2 = pd.getString("endtime");
                if (null != endtime && !"".equals(endtime)) {
                    pd4.put("endtime", endtime+" 23:59:59");
                }else if (null == endtime || "".equals(endtime)){
                    pd4.put("endtime", dateFormat.format(date)+"-31 23:59:59");
                    pd.put("endtime", dateFormat.format(date)+"-31");
                }
                pd4.put("tsclassify","2");
                List<PageData> getData1 = complainManager.getData(pd4);

                String tstypenamecode = pd.getString("tstypenamecode");
                if (StringUtils.isNotEmpty(tstypenamecode)){
                    PageData pageData = new PageData();
                    pageData.put("name",tstypenamecode);
                    List<PageData> getsmallbybig = complainManager.getsmallbybig(pageData);
                    System.out.println(getsmallbybig);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData data : getsmallbybig) {
                        map2.put(data.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", getsmallbybig);
                    mv.addObject("data2", map2);
//                    mv.addObject("area", area);
                    mv.addObject("list2", list2);
                }else {
                    System.out.println(getData1+"getData1");
                    PageData pd5 = new PageData();
                    pd5.put("bianma","014-");
                    List<PageData> dictionaries2 = complainManager.getDictionaries2(pd5);
                    HashMap<String, Integer> map2 = new HashMap<>();
                    for (PageData pageData : dictionaries2) {
                        map2.put(pageData.getString("name"),0);
                    }
                    for (PageData getDatum : getData1) {
                        String tstypename = getDatum.getString("tstypename");
                        Integer num = Integer.parseInt(getDatum.get("num").toString());
                        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                            if (entry.getKey().equals(tstypename)){
                                Integer add=map2.get(tstypename);
                                map2.put(tstypename,add+num);
                            }
                        }
                    }
                    Iterator<Map.Entry<String, Integer>> iterator2 = map2.entrySet().iterator();
                    while (iterator2.hasNext()){
                        Map.Entry<String, Integer> next = iterator2.next();
                        if (next.getValue()==0){
                            iterator2.remove();
                        }
                    }
                    List<Map.Entry<String, Integer>> list2 = new ArrayList<>(map2.entrySet());
                    Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>()
                    {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
                        {
                            //按照value值，重小到大排序
//                return o1.getValue() - o2.getValue();

                            //按照value值，从大到小排序
                            return o2.getValue() - o1.getValue();

                            //按照value值，用compareTo()方法默认是从小到大排序
//                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    mv.addObject("positon2", dictionaries2);
                    mv.addObject("data2", map2);
//                    mv.addObject("area", area);
                    mv.addObject("list2", list2);
                }

                mv.setViewName("statismng/praise/praise_list2");
            }
        }

        return mv;

    }

    @RequestMapping(value="/findGDZL.do")
    public ModelAndView findGDZL() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData PageData = new PageData();
        PageData pd = this.getPageData();
        PageData pd2 = new PageData();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String starttime = pd.getString("starttime");
        if (null != starttime && !"".equals(starttime)) {
            pd2.put("starttime", starttime+" 00:00:00");
        }else if (null == starttime || "".equals(starttime)){
            pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
            pd.put("starttime", dateFormat.format(date)+"-01");
        }
        String endtime = pd.getString("endtime");
        if (null != endtime && !"".equals(endtime)) {
            pd2.put("endtime", endtime+" 23:59:59");
        }else if (null == endtime || "".equals(endtime)){
            pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
            pd.put("endtime", dateFormat.format(date)+"-31");
        }
        ArrayList<Integer> judgetime = judgetime(pd.getString("starttime"), pd.getString("endtime"));

        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)) {
                //是 展示全部数据
                //工单总量
                List<PageData> getgdzl=null;
                String judge = judge(pd.getString("starttime"), pd.getString("endtime"));
                if (judge.equals("day")){
                    getgdzl = complainManager.getgdzlday(pd2);
                }else if (judge.equals("month")){
                    getgdzl = complainManager.getgdzlmonth(pd2);
                }else if (judge.equals("year")){
                    getgdzl = complainManager.getgdzlyear(pd2);
                }
                ArrayList<PageData> getgdzllist = new ArrayList<>();
                for (Integer integer : judgetime) {
                    PageData pageData = new PageData();
                    pageData.put("num",0);
                    pageData.put("MONTH",integer);
                    getgdzllist.add(pageData);
                }
                if (getgdzl!=null){
                    for (PageData pageData1 : getgdzllist) {
                        for (PageData pageData2 : getgdzl) {
                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
                            }
                        }
                    }
                }
                mv.addObject("getgdzl", getgdzllist);
                //投诉来源
//        PageData getcfts = complainManager.getcfts(pd);
//        PageData getzgds = complainManager.getzgds(pd);
//        PageData getsyw = complainManager.getsyw(pd);
//        PageData getmhzj = complainManager.getmhzj(pd);
//        PageData getrx = complainManager.getrx(pd);

//        pd3.put("工单总量",getzgds.get("num").toString());
//        pd3.put("315投诉电话量",getsyw.get("num").toString());
//        pd3.put("民航总局量",getmhzj.get("num").toString());
//        pd3.put("12345热线",getrx.get("num").toString());
//        PageData pd4 = new PageData();
//        pd4.put("实际工单量",Integer.parseInt(getzgds.get("num").toString())-Integer.parseInt(getcfts.get("num").toString()));
                PageData pd3 = new PageData();
                List<PageData> getsource = complainManager.getsource(pd);
                for (PageData pageData : getsource) {
                    String name = pageData.getString("name");
                    String vaule = pageData.get("num").toString();
                    pd3.put(name,vaule);
                }
                mv.addObject("getcfts", pd3);
//        mv.addObject("getsjgdzl", pd4);
                //快速统计
                List<PageData> getkscl = complainManager.getkscl(pd2);
                List<PageData> area = complainManager.getArea(pd);

                HashMap<String, Integer> map2 = new HashMap<>();

                for (PageData pageData : area) {
                    map2.put(pageData.getString("AREA_CODE"),0);
                }
                for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                    for (PageData pageData : getkscl) {
                        String tsdept = pageData.getString("tsdept");
                        Integer num =Integer.parseInt(pageData.get("num").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(entry.getKey())){
                                    Integer add=map2.get(s);
                                    map2.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map2.get(tsdept);
                            map2.put(tsdept,add+num);
                        }
                    }
                }

                mv.addObject("getkscl", map2);
                mv.addObject("area", area);

//                List<PageData> getsywtime =null;
//                List<PageData> getmhzjtime =null;
//                List<PageData> getrxtime =null;
                List<PageData> getcftstime =null;
                List<PageData> getkscltime =null;

                if (judge.equals("day")){
                    getcftstime =complainManager.getcftsday(pd);
//                    getsywtime =complainManager.getsywday(pd);
//                    getmhzjtime =complainManager.getmhzjday(pd);
//                    getrxtime =complainManager.getrxday(pd);
                    getkscltime = complainManager.getksclday(pd);
                }else if (judge.equals("month")){
                    getcftstime =complainManager.getcftsmonth(pd);
//                    getsywtime = complainManager.getsywmonth(pd);
//                    getmhzjtime =complainManager.getmhzjmonth(pd);
//                    getrxtime =complainManager.getrxmonth(pd);
                    getkscltime = complainManager.getksclmonth(pd);
                }else if (judge.equals("year")){
                    getcftstime =complainManager.getcftsyear(pd);
//                    getsywtime =complainManager.getsywyear(pd);
//                    getmhzjtime =complainManager.getmhzjyear(pd);
//                    getrxtime =complainManager.getrxyear(pd);
                    getkscltime = complainManager.getksclyear(pd);
                }
                ArrayList<PageData> getcftslist = new ArrayList<>();
                for (Integer integer : judgetime) {
                    PageData pageData = new PageData();
                    pageData.put("num",0);
                    pageData.put("MONTH",integer);
                    getcftslist.add(pageData);
                }
                if (getcftstime!=null){
                    for (PageData pageData1 : getcftslist) {
                        for (PageData pageData2 : getcftstime) {
                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
                            }
                        }
                    }
                }
                mv.addObject("getcftstime", getcftslist);
//                ArrayList<PageData> getsywlist = new ArrayList<>();
//                for (Integer integer : judgetime) {
//                    PageData pageData = new PageData();
//                    pageData.put("num",0);
//                    pageData.put("MONTH",integer);
//                    getsywlist.add(pageData);
//                }
//                if (getsywtime!=null){
//                    for (PageData pageData1 : getsywlist) {
//                        for (PageData pageData2 : getsywtime) {
//                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
//                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
//                            }
//                        }
//                    }
//                }
//                mv.addObject("getsywtime", getsywlist);

//                ArrayList<PageData> getmhzjlist = new ArrayList<>();
//                for (Integer integer : judgetime) {
//                    PageData pageData = new PageData();
//                    pageData.put("num",0);
//                    pageData.put("MONTH",integer);
//                    getmhzjlist.add(pageData);
//                }
//                if (getmhzjtime!=null){
//                    for (PageData pageData1 : getmhzjlist) {
//                        for (PageData pageData2 : getmhzjtime) {
//                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
//                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
//                            }
//                        }
//                    }
//                }
//                mv.addObject("getmhzjtime", getmhzjlist);

//                ArrayList<PageData> getrxlist = new ArrayList<>();
//                for (Integer integer : judgetime) {
//                    PageData pageData = new PageData();
//                    pageData.put("num",0);
//                    pageData.put("MONTH",integer);
//                    getrxlist.add(pageData);
//                }
//                if (getrxtime!=null){
//                    for (PageData pageData1 : getrxlist) {
//                        for (PageData pageData2 : getrxtime) {
//                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
//                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
//                            }
//                        }
//                    }
//                }
//                mv.addObject("getrxtime", getrxlist);

                ArrayList<PageData> getkscllist = new ArrayList<>();
                for (Integer integer : judgetime) {
                    PageData pageData = new PageData();
                    pageData.put("num",0);
                    pageData.put("MONTH",integer);
                    getkscllist.add(pageData);
                }
                if (getkscltime!=null){
                    for (PageData pageData1 : getkscllist) {
                        for (PageData pageData2 : getkscltime) {
                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
                            }
                        }
                    }
                }

                mv.addObject("getkscltime", getkscllist);

                PageData getkscltsman = complainManager.getkscltsman(pd);
                PageData getkscltsclassify = complainManager.getkscltsclassify(pd);
                System.out.println(getkscltsman+"getkscltsman");
                System.out.println(getkscltsclassify+"getkscltsclassify");

                mv.addObject("getkscltsman", getkscltsman);
                mv.addObject("getkscltsclassify", getkscltsclassify);

                mv.addObject("judgetime", judgetime);
                mv.addObject("pd", pd);
                mv.setViewName("statismng/ordertotal/ordertotal_list");
            }else {
                //获取部门科室列表


                UserPd.put("AREA_CODE",dwbm);
                PageData byAreaCode = areamanageService.findByAreaCode(UserPd);
                String area_level = byAreaCode.getString("AREA_LEVEL");
                String area_id = "";
                if ("3".equals(area_level)){
                    area_id = byAreaCode.getString("AREA_ID");
                }else if ("4".equals(area_level)){
                    area_id = byAreaCode.getString("PARENT_ID");
                }
                PageData area_idpd = new PageData();
                area_idpd.put("AREA_ID",area_id);
                Page page = new Page();
                page.setPd(area_idpd);
                page.setCurrentPage(1);
                page.setShowCount(999999);
                List<PageData> deptlist = areamanageService.list(page);
                ArrayList<String> areaCodeList = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    areaCodeList.add(pageData.getString("AREA_CODE"));
                }

                String strip = StringUtils.strip(areaCodeList.toString(), "[]");
                pd2.put("offices",strip);
                //工单总量
                List<PageData> getgdzl=null;
                String judge = judge(pd.getString("starttime"), pd.getString("endtime"));
                if (judge.equals("day")){
                    getgdzl = complainManager.getgdzlday(pd2);
                }else if (judge.equals("month")){
                    getgdzl = complainManager.getgdzlmonth(pd2);
                }else if (judge.equals("year")){
                    getgdzl = complainManager.getgdzlyear(pd2);
                }
                ArrayList<PageData> getgdzllist = new ArrayList<>();
                for (Integer integer : judgetime) {
                    PageData pageData = new PageData();
                    pageData.put("num",0);
                    pageData.put("MONTH",integer);
                    getgdzllist.add(pageData);
                }
                if (getgdzl!=null){
                    for (PageData pageData1 : getgdzllist) {
                        for (PageData pageData2 : getgdzl) {
                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
                            }
                        }
                    }
                }
                mv.addObject("getgdzl", getgdzllist);
                //工单来源
                PageData pd3 = new PageData();
                pd.put("offices",strip);
                List<PageData> getsource = complainManager.getsource(pd);
                for (PageData pageData : getsource) {
                    String name = pageData.getString("name");
                    String vaule = pageData.get("num").toString();
                    pd3.put(name,vaule);
                }
                mv.addObject("getcfts", pd3);
                //快速处理
                List<PageData> getkscl = complainManager.getkscl(pd2);

                HashMap<String, Integer> map2 = new HashMap<>();

                for (PageData pageData : deptlist) {
                    map2.put(pageData.getString("AREA_CODE"),0);
                }
                for (Map.Entry<String, Integer> entry : map2.entrySet()) {
                    for (PageData pageData : getkscl) {
                        String tsdept = pageData.getString("tsdept");
                        Integer num =Integer.parseInt(pageData.get("num").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(entry.getKey())){
                                    Integer add=map2.get(s);
                                    map2.put(s,add+num);
                                }
                            }
                        }else if (entry.getKey().equals(tsdept)){
                            Integer add=map2.get(tsdept);
                            map2.put(tsdept,add+num);
                        }
                    }
                }

                mv.addObject("getkscl", map2);
                mv.addObject("area", deptlist);

                List<PageData> getcftstime =null;
                List<PageData> getkscltime =null;

                if (judge.equals("day")){
                    getcftstime =complainManager.getcftsday(pd);
                    getkscltime = complainManager.getksclday(pd);
                }else if (judge.equals("month")){
                    getcftstime =complainManager.getcftsmonth(pd);
                    getkscltime = complainManager.getksclmonth(pd);
                }else if (judge.equals("year")){
                    getcftstime =complainManager.getcftsyear(pd);
                    getkscltime = complainManager.getksclyear(pd);
                }
                ArrayList<PageData> getcftslist = new ArrayList<>();
                for (Integer integer : judgetime) {
                    PageData pageData = new PageData();
                    pageData.put("num",0);
                    pageData.put("MONTH",integer);
                    getcftslist.add(pageData);
                }
                if (getcftstime!=null){
                    for (PageData pageData1 : getcftslist) {
                        for (PageData pageData2 : getcftstime) {
                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
                            }
                        }
                    }
                }
                mv.addObject("getcftstime", getcftslist);

                ArrayList<PageData> getkscllist = new ArrayList<>();
                for (Integer integer : judgetime) {
                    PageData pageData = new PageData();
                    pageData.put("num",0);
                    pageData.put("MONTH",integer);
                    getkscllist.add(pageData);
                }
                if (getkscltime!=null){
                    for (PageData pageData1 : getkscllist) {
                        for (PageData pageData2 : getkscltime) {
                            if (Integer.parseInt(pageData2.get("MONTH").toString())==Integer.parseInt(pageData1.get("MONTH").toString())){
                                pageData1.put("num",Integer.parseInt(pageData2.get("num").toString()));
                            }
                        }
                    }
                }

                mv.addObject("getkscltime", getkscllist);

                PageData getkscltsman = complainManager.getkscltsman(pd);
                PageData getkscltsclassify = complainManager.getkscltsclassify(pd);
                System.out.println(getkscltsman+"getkscltsman");
                System.out.println(getkscltsclassify+"getkscltsclassify");

                mv.addObject("getkscltsman", getkscltsman);
                mv.addObject("getkscltsclassify", getkscltsclassify);

                mv.addObject("judgetime", judgetime);
                mv.addObject("pd", pd);
                mv.setViewName("statismng/ordertotal/ordertotal_list");

            }
        }


        return mv;
    }

    public String printDifference(Integer different){

        //millisecondsSystem.out.println("different : " + different);

        Integer secondsInMilli = 1000;
        Integer minutesInMilli = secondsInMilli * 60;
        Integer hoursInMilli = minutesInMilli * 60;
        Integer daysInMilli = hoursInMilli * 24;

        Integer elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        Integer elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        Integer elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        Integer elapsedSeconds = different / secondsInMilli;

        String s=elapsedDays+"日"+elapsedHours+"时"+elapsedMinutes+"分"+ elapsedSeconds+"秒";
//        System.out.printf(
//                "%d days, %d hours, %d minuts, %d seconds%n",
//                elapsedDays,elapsedHours, elapsedMinutes, elapsedSeconds);
        return s;

    }

    public Integer day(Integer different){
        Integer i = different / 1000 / 60 / 60;
        return i;

    }

    @RequestMapping(value="/findGDCLZL.do")
    public ModelAndView findGDCLZL() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData pd2 = this.getPageData();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String starttime = pd.getString("starttime");
        if (null != starttime && !"".equals(starttime)) {
            pd2.put("starttime", starttime+" 00:00:00");
        }else if (null == starttime || "".equals(starttime)){
            pd2.put("starttime", dateFormat.format(date)+"-01 00:00:00");
            pd.put("starttime", dateFormat.format(date)+"-01");
        }
        String endtime = pd.getString("endtime");
        if (null != endtime && !"".equals(endtime)) {
            pd2.put("endtime", endtime+" 23:59:59");
        }else if (null == endtime || "".equals(endtime)){
            pd2.put("endtime", dateFormat.format(date)+"-31 23:59:59");
            pd.put("endtime", dateFormat.format(date)+"-31");
        }
        //获取当前用户部门
        String username = Jurisdiction.getUsername();
        PageData UserPd = new PageData();
        UserPd.put("USERNAME",username);
        PageData byUsername = userService.findByUsername(UserPd);
        if (!byUsername.isEmpty()) {
            String dwbm = byUsername.getString("DWBM");
            //判断是是否为安质部

            if ("350101".equals(dwbm) || "35".equals(dwbm)) {
                List<PageData> area = complainManager.getArea(pd);
                List<PageData> getpjsx = complainManager.getpjsx(pd);
                System.out.println(getpjsx+"getpjsx");
                ArrayList<PageData> list2 = new ArrayList<>();
                for (PageData pageData : area) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("pjsj",0);
                    pd1.put("num",0);
                    list2.add(pd1);
                }
                for (PageData pageData1 : list2) {
                    for (PageData pageData2 : getpjsx) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer pjsj =Integer.parseInt(pageData2.get("pjsj").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+1);
                                    pageData1.put("pjsj",Integer.parseInt(pageData1.get("pjsj").toString())+pjsj);
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+1);
                            pageData1.put("pjsj",Integer.parseInt(pageData1.get("pjsj").toString())+pjsj);
                        }
                    }
                }
                System.out.println(list2+"list2");
                for (PageData pageData : list2) {
                    Integer num = Integer.parseInt(pageData.get("num").toString());
                    Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                    if (num!=0){
                        pjsj = Integer.parseInt(pageData.get("pjsj").toString())/Integer.parseInt(pageData.get("num").toString());
                    }
                    pageData.put("pjsj",printDifference(pjsj));
                }

                List<PageData> getmaxclsj = complainManager.getmaxclsj(pd);
                System.out.println(getmaxclsj+"getmaxclsj");
                ArrayList<PageData> list3 = new ArrayList<>();
                for (PageData pageData : area) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("pjsj",0);
                    list3.add(pd1);
                }

                for (PageData pageData1 : list3) {
                    for (PageData pageData2 : getmaxclsj) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer pjsj =Integer.parseInt(pageData2.get("pjsj").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    if (Integer.parseInt(pageData1.get("pjsj").toString())<pjsj){
                                        pageData1.put("pjsj",pjsj);
                                    }
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            if (Integer.parseInt(pageData1.get("pjsj").toString())<pjsj){
                                pageData1.put("pjsj",pjsj);
                            }
                        }
                    }
                }
                System.out.println(list3+"list3");
                for (PageData pageData : list3) {
                    Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                    pageData.put("pjsj",printDifference(pjsj));
                }

                List<PageData> getminclsj = complainManager.getminclsj(pd);
                System.out.println(getminclsj+"getminclsj");
                ArrayList<PageData> list4 = new ArrayList<>();
                for (PageData pageData : area) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("pjsj",999999999);
                    list4.add(pd1);
                }
                for (PageData pageData1 : list4) {
                    for (PageData pageData2 : getminclsj) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer pjsj =Integer.parseInt(pageData2.get("pjsj").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    if (Integer.parseInt(pageData1.get("pjsj").toString())>pjsj){
                                        pageData1.put("pjsj",pjsj);
                                    }
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            if (Integer.parseInt(pageData1.get("pjsj").toString())>pjsj){
                                pageData1.put("pjsj",pjsj);
                            }
                        }
                    }
                }
                System.out.println(list4+"list4");
                for (PageData pageData : list4) {
                    Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                    if (pjsj==999999999){
                        pjsj=0;
                    }
                    pageData.put("pjsj",printDifference(pjsj));
                }


                List<PageData> getcsgd = complainManager.getcsgd(pd);
                ArrayList<PageData> list5 = new ArrayList<>();
                for (PageData pageData : area) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("num",0);
                    list5.add(pd1);
                }
                for (PageData pageData : getcsgd) {
                    for (PageData data : list5) {
                        Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                        String tsdept = pageData.getString("tsdept");
                        String a = pageData.getString("clsx");
                        if (!a.equals("") && a!=null){
                            String regEx = "[^0-9]";
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(a);
                            String trim = m.replaceAll("").trim();
                            Integer clsx = 0;
                            if (StringUtils.isNotEmpty(trim)) {
                                clsx = Integer.parseInt(trim);
                            }
                            if (tsdept.contains(",")){
                                for (String s : tsdept.split(",")) {
                                    if (data.getString("dept").equals(s)){
                                        Integer day = day(pjsj);
                                        if (day>clsx){
                                            data.put("num",Integer.parseInt(data.get("num").toString())+1);
                                        }
                                    }
                                }
                            }else if (data.getString("dept").equals(tsdept)){
                                Integer day = day(pjsj);
                                if (day>clsx){
                                    data.put("num",Integer.parseInt(data.get("num").toString())+1);
                                }
                            }
                        }
                    }
                }

                List<PageData> getzcll = complainManager.getzcll(pd);
                ArrayList<PageData> list6 = new ArrayList<>();
                for (PageData pageData : area) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("num",0);
                    list6.add(pd1);
                }
                for (PageData pageData1 : list6) {
                    for (PageData pageData2 : getzcll) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer num =Integer.parseInt(pageData2.get("num").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                        }
                    }
                }
                List<PageData> getdcll = complainManager.getdcll(pd);
                ArrayList<PageData> list7 = new ArrayList<>();
                for (PageData pageData : area) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("num",0);
                    list7.add(pd1);
                }
                for (PageData pageData1 : list7) {
                    for (PageData pageData2 : getdcll) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer num =Integer.parseInt(pageData2.get("num").toString()) ;
                        if (tsdept!=null){
                            if (tsdept.contains(",")){
                                for (String s : tsdept.split(",")) {
                                    if (s.equals(pageData1.getString("dept"))){
                                        pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                                    }
                                }
                            }else if (tsdept.equals(pageData1.getString("dept"))){
                                pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                            }
                        }

                    }
                }

                mv.addObject("getpjsx", list2);
                mv.addObject("getmaxclsj", list3);
                mv.addObject("getminclsj", list4);
                mv.addObject("getcsgd", list5);
                mv.addObject("getzcll", list6);
                mv.addObject("getdcll", list7);
                mv.addObject("area", area);
                mv.addObject("pd", pd);
            }else {
                //否 按部门科室进行展示
                System.out.println("科室统计");
                //获取部门科室列表


                UserPd.put("AREA_CODE",dwbm);
                PageData byAreaCode = areamanageService.findByAreaCode(UserPd);
                String area_level = byAreaCode.getString("AREA_LEVEL");
                String area_id = "";
                if ("3".equals(area_level)){
                    area_id = byAreaCode.getString("AREA_ID");
                }else if ("4".equals(area_level)){
                    area_id = byAreaCode.getString("PARENT_ID");
                }
                PageData area_idpd = new PageData();
                area_idpd.put("AREA_ID",area_id);
                Page page = new Page();
                page.setPd(area_idpd);
                page.setCurrentPage(1);
                page.setShowCount(999999);
                List<PageData> deptlist = areamanageService.list(page);
                ArrayList<String> areaCodeList = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    areaCodeList.add(pageData.getString("AREA_CODE"));
                }

                String strip = StringUtils.strip(areaCodeList.toString(), "[]");


                pd.put("offices",strip);
                List<PageData> getpjsx = complainManager.getpjsxoffice(pd);
                System.out.println(getpjsx+"getpjsx");
                ArrayList<PageData> list2 = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("pjsj",0);
                    pd1.put("num",0);
                    list2.add(pd1);
                }
                for (PageData pageData1 : list2) {
                    for (PageData pageData2 : getpjsx) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer pjsj =Integer.parseInt(pageData2.get("pjsj").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+1);
                                    pageData1.put("pjsj",Integer.parseInt(pageData1.get("pjsj").toString())+pjsj);
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+1);
                            pageData1.put("pjsj",Integer.parseInt(pageData1.get("pjsj").toString())+pjsj);
                        }
                    }
                }
                System.out.println(list2+"list2");
                for (PageData pageData : list2) {
                    Integer num = Integer.parseInt(pageData.get("num").toString());
                    Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                    if (num!=0){
                        pjsj = Integer.parseInt(pageData.get("pjsj").toString())/Integer.parseInt(pageData.get("num").toString());
                    }
                    pageData.put("pjsj",printDifference(pjsj));
                }

                List<PageData> getmaxclsj = complainManager.getmaxclsjoffice(pd);
                System.out.println(getmaxclsj+"getmaxclsj");
                ArrayList<PageData> list3 = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("pjsj",0);
                    list3.add(pd1);
                }

                for (PageData pageData1 : list3) {
                    for (PageData pageData2 : getmaxclsj) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer pjsj =Integer.parseInt(pageData2.get("pjsj").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    if (Integer.parseInt(pageData1.get("pjsj").toString())<pjsj){
                                        pageData1.put("pjsj",pjsj);
                                    }
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            if (Integer.parseInt(pageData1.get("pjsj").toString())<pjsj){
                                pageData1.put("pjsj",pjsj);
                            }
                        }
                    }
                }
                System.out.println(list3+"list3");
                for (PageData pageData : list3) {
                    Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                    pageData.put("pjsj",printDifference(pjsj));
                }

                List<PageData> getminclsj = complainManager.getminclsjoffice(pd);
                System.out.println(getminclsj+"getminclsj");
                ArrayList<PageData> list4 = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("pjsj",999999999);
                    list4.add(pd1);
                }
                for (PageData pageData1 : list4) {
                    for (PageData pageData2 : getminclsj) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer pjsj =Integer.parseInt(pageData2.get("pjsj").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    if (Integer.parseInt(pageData1.get("pjsj").toString())>pjsj){
                                        pageData1.put("pjsj",pjsj);
                                    }
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            if (Integer.parseInt(pageData1.get("pjsj").toString())>pjsj){
                                pageData1.put("pjsj",pjsj);
                            }
                        }
                    }
                }
                System.out.println(list4+"list4");
                for (PageData pageData : list4) {
                    Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                    if (pjsj==999999999){
                        pjsj=0;
                    }
                    pageData.put("pjsj",printDifference(pjsj));
                }


                List<PageData> getcsgd = complainManager.getcsgd(pd);
                ArrayList<PageData> list5 = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("num",0);
                    list5.add(pd1);
                }
                for (PageData pageData : getcsgd) {
                    for (PageData data : list5) {
                        Integer pjsj = Integer.parseInt(pageData.get("pjsj").toString());
                        String tsdept = pageData.getString("tsdept");
                        String a = pageData.getString("clsx");
                        if (!a.equals("") && a!=null){
                            String regEx = "[^0-9]";
                            Pattern p = Pattern.compile(regEx);
                            Matcher m = p.matcher(a);
                            String trim = m.replaceAll("").trim();
                            Integer clsx = 0;
                            if (StringUtils.isNotEmpty(trim)) {
                                clsx = Integer.parseInt(trim);
                            }
                            if (tsdept.contains(",")){
                                for (String s : tsdept.split(",")) {
                                    if (data.getString("dept").equals(s)){
                                        Integer day = day(pjsj);
                                        if (day>clsx){
                                            data.put("num",Integer.parseInt(data.get("num").toString())+1);
                                        }
                                    }
                                }
                            }else if (data.getString("dept").equals(tsdept)){
                                Integer day = day(pjsj);
                                if (day>clsx){
                                    data.put("num",Integer.parseInt(data.get("num").toString())+1);
                                }
                            }
                        }
                    }
                }

                List<PageData> getzcll = complainManager.getzcll(pd);
                ArrayList<PageData> list6 = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("num",0);
                    list6.add(pd1);
                }
                for (PageData pageData1 : list6) {
                    for (PageData pageData2 : getzcll) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer num =Integer.parseInt(pageData2.get("num").toString()) ;
                        if (tsdept.contains(",")){
                            for (String s : tsdept.split(",")) {
                                if (s.equals(pageData1.getString("dept"))){
                                    pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                                }
                            }
                        }else if (tsdept.equals(pageData1.getString("dept"))){
                            pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                        }
                    }
                }
                List<PageData> getdcll = complainManager.getdcll(pd);
                ArrayList<PageData> list7 = new ArrayList<>();
                for (PageData pageData : deptlist) {
                    PageData pd1 = new PageData();
                    pd1.put("dept",pageData.getString("AREA_CODE"));
                    pd1.put("num",0);
                    list7.add(pd1);
                }
                for (PageData pageData1 : list7) {
                    for (PageData pageData2 : getdcll) {
                        String tsdept = pageData2.getString("tsdept");
                        Integer num =Integer.parseInt(pageData2.get("num").toString()) ;
                        if (tsdept!=null){
                            if (tsdept.contains(",")){
                                for (String s : tsdept.split(",")) {
                                    if (s.equals(pageData1.getString("dept"))){
                                        pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                                    }
                                }
                            }else if (tsdept.equals(pageData1.getString("dept"))){
                                pageData1.put("num",Integer.parseInt(pageData1.get("num").toString())+num);
                            }
                        }

                    }
                }

                mv.addObject("getpjsx", list2);
                mv.addObject("getmaxclsj", list3);
                mv.addObject("getminclsj", list4);
                mv.addObject("getcsgd", list5);
                mv.addObject("getzcll", list6);
                mv.addObject("getdcll", list7);
                mv.addObject("area", deptlist);
                mv.addObject("pd", pd);
            }
        }

        mv.setViewName("statismng/ordertotal/ordertotal_list2");
        return mv;
    }

    public ArrayList<Integer> judgetime(String starttime,String endtime){

        Integer startmonth = Integer.parseInt(starttime.substring(5, 7));
        Integer endmonth   = Integer.parseInt(endtime.substring(5, 7));
        Integer startyear  = Integer.parseInt(starttime.substring(0, 4)) ;
        Integer endyear    = Integer.parseInt(endtime.substring(0, 4)) ;
        Integer startday   = Integer.parseInt(starttime.substring(8, 10));
        Integer endday     = Integer.parseInt(endtime.substring(8, 10)) ;

        ArrayList<Integer> strings = new ArrayList<>();
        if (startyear.equals(endyear) && startmonth.equals(endmonth)){
            Integer day =  endday - startday;
            for (int i = startday; i <= endday; i++) {
                strings.add(i);
            }
        }else if (startyear.equals(endyear) && startmonth!=endmonth){
            Integer month = endmonth - startmonth;
            for (int i = startmonth; i <= endmonth; i++) {
                strings.add(i);
            }
        }else if (startyear!=endyear){
            Integer year = endyear-startyear;
            for (int i = startyear; i <= endyear; i++) {
                strings.add(i);
            }
        }
        return strings;
    }

    public String judge(String starttime,String endtime){

        Integer startmonth = Integer.parseInt(starttime.substring(5, 7));
        Integer endmonth   = Integer.parseInt(endtime.substring(5, 7));
        Integer startyear  = Integer.parseInt(starttime.substring(0, 4)) ;
        Integer endyear    = Integer.parseInt(endtime.substring(0, 4)) ;
        Integer startday   = Integer.parseInt(starttime.substring(8, 10));
        Integer endday     = Integer.parseInt(endtime.substring(8, 10)) ;
        String s="";
        ArrayList<Integer> strings = new ArrayList<>();
        if (startyear.equals(endyear) && startmonth.equals(endmonth)){
            s="day";
        }else if (startyear.equals(endyear) && startmonth!=endmonth){
            s="month";
        }else if (startyear!=endyear){
            s="year";
        }
        return s;
    }

    /**
     *去掉括号及括号里的内容
     * */
    private String ClearBracket(String context) {
        if (context!="" && context!=null){
            // 修改原来的逻辑，防止右括号出现在左括号前面的位置
            int head = context.indexOf('（'); // 标记第一个使用左括号的位置
            if (head == -1) {
                return context; // 如果context中不存在括号，什么也不做，直接跑到函数底端返回初值str
            } else {
                int next = head + 1; // 从head+1起检查每个字符
                int count = 1; // 记录括号情况
                do {
                    System.out.println(context);
                    if (context.charAt(next) == '（') {
                        count++;
                    } else if (context.charAt(next) == '）') {
                        count--;
                    }
                    next++; // 更新即将读取的下一个字符的位置
                    if (count == 0) { // 已经找到匹配的括号
                        String temp = context.substring(head, next);
                        context = context.replace(temp, ""); // 用空内容替换，复制给context
                        head = context.indexOf('（'); // 找寻下一个左括号
                        next = head + 1; // 标记下一个左括号后的字符位置
                        count = 1; // count的值还原成1
                    }
                } while (head != -1); // 如果在该段落中找不到左括号了，就终止循环
            }
        }

        return context; // 返回更新后的context
    }


    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

}
