package com.fh.controller.activiti.querywork;

import com.fh.controller.base.BaseController;
import com.fh.service.activiti.querywork.QueryWorkManage;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping(value = "/querywork")
public class QueryWorkController extends BaseController {
    @Resource(name = "queryWorkService")
    private QueryWorkManage queryWorkManage;
    @Resource(name = "areamanageService")
    private AreaManageManager areamanageService;


    @RequestMapping(value = "/gettxbm")
    public ModelAndView gettxbm() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String starttime = pd.getString("starttime");
        if (starttime != null && !"".equals(starttime)) {
            pd.put("starttime", starttime + " 00:00:00");
        }
        String endtime = pd.getString("endtime");
        if (endtime != null && !"".equals(endtime)) {
            pd.put("endtime", endtime + " 00:00:00");
        }
        List<PageData> gettxbm = queryWorkManage.gettxbm(pd);
        List<PageData> list = areamanageService.listAll(pd);



        HashMap<String, Integer> map = new HashMap();
        for (PageData pageData : list) {
            map.put(pageData.getString("AREA_ID"), 0);
        }

        for (PageData pageData : gettxbm) {
            String tsdept = pageData.getString("tsdept");
            if (!tsdept.contains(",")) {
                for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                    if (Entry.getKey().equals(pageData.getString("tsdept"))) {
                        Integer value = Entry.getValue();
                        Integer num = parse(pageData.get("num"));
                        int i = value + num;
                        map.put(Entry.getKey(), i);
                    }
                }
            } else {
                String[] split = tsdept.split(",");
                for (String s : split) {
                    for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                        if (Entry.getKey().equals(s)) {
                            Integer value = Entry.getValue();
                            Integer num = parse(pageData.get("num"));
                            int i = value + num;
                            map.put(Entry.getKey(), i);
                        }
                    }
                }
            }
        }
        HashMap<String, Integer> map2 = new HashMap();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            for (PageData pageData : list) {

                String area_id = pageData.getString("AREA_ID");
                String name = pageData.getString("NAME");
                if (area_id.equals(entry.getKey())) {
                    map2.put(name, entry.getValue());
                }
            }
        }
        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(map2.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });


        System.out.println("map2"+map2);
        System.out.println("list2"+list2);
        mv.addObject("map", map2);
        mv.addObject("pd", pd);
        mv.addObject("sortlist", list2);
        mv.setViewName("activiti/querywork/querywork_list");
        return mv;
    }

    @RequestMapping(value = "/gettxdl")
    public ModelAndView gettxdl() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String starttime = pd.getString("starttime");
        if (starttime != null && !"".equals(starttime)) {
            pd.put("starttime", starttime + " 00:00:00");
        }
        String endtime = pd.getString("endtime");
        if (endtime != null && !"".equals(endtime)) {
            pd.put("endtime", endtime + " 00:00:00");
        }
        List<PageData> list = areamanageService.listAll(pd);
        String dept = pd.getString("dept");
        for (PageData pageData : list) {
            String name = pageData.getString("NAME");
            if (name.equals(dept)){
                pd.put("dept",pageData.getString("AREA_ID"));
            }
        }
        List<PageData> gettxdl = queryWorkManage.gettxdl(pd);
        List<PageData> getbigtype = queryWorkManage.getbigtype(pd);

        HashMap<String, Integer> map = new HashMap();
        for (PageData pageData : getbigtype) {
            map.put(pageData.getString("NAME"), 0);
        }
        for (PageData pageData : gettxdl) {
            String tsbigtype = pageData.getString("tsbigtype");

            for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                if (Entry.getKey().equals(tsbigtype)) {
                    Integer value = Entry.getValue();
                    Integer num = parse(pageData.get("num"));
                    int i = value + num;
                    map.put(Entry.getKey(), i);
                }
            }

        }
        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println(map);
        System.out.println(list2);
        mv.addObject("map", map);
        mv.addObject("pd", pd);
        mv.addObject("sortlist", list2);
        mv.setViewName("activiti/querywork/querywork_list");
        return mv;
    }

    @RequestMapping(value = "/gettxxl")
    public ModelAndView gettxxl() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String starttime = pd.getString("starttime");
        if (starttime != null && !"".equals(starttime)) {
            pd.put("starttime", starttime + " 00:00:00");
        }
        String endtime = pd.getString("endtime");
        if (endtime != null && !"".equals(endtime)) {
            pd.put("endtime", endtime + " 00:00:00");
        }
        List<PageData> list = areamanageService.listAll(pd);
        String dept = pd.getString("dept");
        for (PageData pageData : list) {
            String name = pageData.getString("NAME");
            if (name.equals(dept)){
                pd.put("dept",pageData.getString("AREA_ID"));
            }
        }
        List<PageData> gettxxl = queryWorkManage.gettxxl(pd);
        List<PageData> getsmalltype = queryWorkManage.getsmalltype(pd);
        HashMap<String, Integer> map = new HashMap();
        for (PageData pageData : getsmalltype) {
            map.put(pageData.getString("NAME"), 0);
        }
        for (PageData pageData : gettxxl) {
            String tstypename = pageData.getString("tstypename");
            for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                if (Entry.getKey().equals(tstypename)) {
                    Integer value = Entry.getValue();
                    Integer num = parse(pageData.get("num"));
                    int i = value + num;
                    map.put(Entry.getKey(), i);
                }
            }
        }

        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        System.out.println(map);
        System.out.println(list2);
        mv.setViewName("activiti/querywork/querywork_list");
        mv.addObject("map", map);
        mv.addObject("pd", pd);
        mv.addObject("sortlist", list2);
        return mv;
    }

    public Integer parse(Object s) {
        return Integer.parseInt(s.toString());
    }

    @RequestMapping(value = "/getjybm")
    public ModelAndView getjybm() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String starttime = pd.getString("starttime");
        if (starttime != null && !"".equals(starttime)) {
            pd.put("starttime", starttime + " 00:00:00");
        }
        String endtime = pd.getString("endtime");
        if (endtime != null && !"".equals(endtime)) {
            pd.put("endtime", endtime + " 00:00:00");
        }
        List<PageData> getjybm = queryWorkManage.getjybm(pd);
        List<PageData> list = areamanageService.listAll(pd);
        HashMap<String, Integer> map = new HashMap();
        for (PageData pageData : list) {
            map.put(pageData.getString("AREA_ID"), 0);
        }
        for (PageData pageData : getjybm) {
            String tsdept = pageData.getString("tsdept");
            if (!tsdept.contains(",")) {
                for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                    if (Entry.getKey().equals(pageData.getString("tsdept"))) {
                        Integer value = Entry.getValue();
                        Integer num = parse(pageData.get("num"));
                        int i = value + num;
                        map.put(Entry.getKey(), i);
                    }
                }
            } else {
                String[] split = tsdept.split(",");
                for (String s : split) {
                    for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                        if (Entry.getKey().equals(s)) {
                            Integer value = Entry.getValue();
                            Integer num = parse(pageData.get("num"));
                            int i = value + num;
                            map.put(Entry.getKey(), i);
                        }
                    }
                }
            }
        }
        HashMap<String, Integer> map2 = new HashMap();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            for (PageData pageData : list) {

                String area_id = pageData.getString("AREA_ID");
                String name = pageData.getString("NAME");
                if (area_id.equals(entry.getKey())) {
                    map2.put(name, entry.getValue());
                }
            }
        }
        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(map2.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        System.out.println(map);
        System.out.println(list2);
        mv.addObject("map", map2);
        mv.addObject("pd", pd);
        mv.addObject("sortlist", list2);
        mv.setViewName("activiti/querywork/querywork_list");
        return mv;
    }
    @RequestMapping(value = "/getjydl")
    public ModelAndView getjydl() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String starttime = pd.getString("starttime");
        if (starttime != null && !"".equals(starttime)) {
            pd.put("starttime", starttime + " 00:00:00");
        }
        String endtime = pd.getString("endtime");
        if (endtime != null && !"".equals(endtime)) {
            pd.put("endtime", endtime + " 00:00:00");
        }
        List<PageData> list = areamanageService.listAll(pd);
        String dept = pd.getString("dept");
        for (PageData pageData : list) {
            String name = pageData.getString("NAME");
            if (name.equals(dept)){
                pd.put("dept",pageData.getString("AREA_ID"));
            }
        }
        List<PageData> getbigtype = queryWorkManage.getbigtype(pd);
        List<PageData> getjydl = queryWorkManage.getjydl(pd);
        HashMap<String, Integer> map = new HashMap();
        for (PageData pageData : getbigtype) {
            map.put(pageData.getString("NAME"), 0);
        }
        for (PageData pageData : getjydl) {
            String tsbigtype = pageData.getString("tsbigtype");

            for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                if (Entry.getKey().equals(tsbigtype)) {
                    Integer value = Entry.getValue();
                    Integer num = parse(pageData.get("num"));
                    int i = value + num;
                    map.put(Entry.getKey(), i);
                }
            }
        }
        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println(map);
        System.out.println(list2);
        mv.addObject("map", map);
        mv.addObject("pd", pd);
        mv.addObject("sortlist", list2);
        mv.setViewName("activiti/querywork/querywork_list");
        return mv;
    }

    @RequestMapping(value = "/getjyxl")
    public ModelAndView getjyxl() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String starttime = pd.getString("starttime");
        if (starttime != null && !"".equals(starttime)) {
            pd.put("starttime", starttime + " 00:00:00");
        }
        String endtime = pd.getString("endtime");
        if (endtime != null && !"".equals(endtime)) {
            pd.put("endtime", endtime + " 00:00:00");
        }
        List<PageData> list = areamanageService.listAll(pd);
        String dept = pd.getString("dept");
        for (PageData pageData : list) {
            String name = pageData.getString("NAME");
            if (name.equals(dept)){
                pd.put("dept",pageData.getString("AREA_ID"));
            }
        }
        List<PageData> getsmalltype = queryWorkManage.getsmalltype(pd);
        List<PageData> getjyxl = queryWorkManage.getjyxl(pd);
        HashMap<String, Integer> map = new HashMap();
        for (PageData pageData : getsmalltype) {
            map.put(pageData.getString("NAME"), 0);
        }
        for (PageData pageData : getjyxl) {
            String tstypename = pageData.getString("tstypename");
            for (Map.Entry<String, Integer> Entry : map.entrySet()) {
                if (Entry.getKey().equals(tstypename)) {
                    Integer value = Entry.getValue();
                    Integer num = parse(pageData.get("num"));
                    int i = value + num;
                    map.put(Entry.getKey(), i);
                }
            }
        }
        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        System.out.println(map);
        System.out.println(list2);
        mv.setViewName("activiti/querywork/querywork_list");
        mv.addObject("map", map);
        mv.addObject("pd", pd);
        mv.addObject("sortlist", list2);
        return mv;
    }

}
