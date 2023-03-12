package com.yuzhe.controller.report;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.FileManager;
import com.yuzhe.service.LostManager;
import com.yuzhe.service.ProcessManager;
import com.yuzhe.service.StatisticsManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportController implements CommonIntefate {

    @Resource(name="lostService")
    private LostManager lostManager;

    @Resource(name = "articleStatisticsService")
    private StatisticsManager statisticsService;

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name="userService")
    private UserManager userService;

    @Resource(name="lsFileService")
    private FileManager fileService;


    @Resource(name = "processService")
    private ProcessManager processService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsondata = data.getJSONObject("data");
        JSONObject result=new JSONObject();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String num="L"+formatter.format(new Date());
        try{
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData param=new PageData();
            PageData pdall=new PageData();
            String tokenid=jsondata.getString("tokenid");
            param.put("TOKENID",tokenid);
            PageData pdtoken=zxlbService.findByTokenId(param);
            String username=(jsondata.getString("username")==null?"":jsondata.getString("username"));
            if(!"".equals(username)){
                param.put("USERNAME", username);
                pdtoken=userService.findByUsername(param);
                if(pdtoken!=null){
                    pdtoken.put("dept", pdtoken.getString("DWBM"));
                    pdtoken.put("ZXYH", username);
                }
                pdall.put("ZXID", username);
                pdall.put("systype", "1");
            }else{
                if(pdtoken!=null){
                    pdall.put("ZXID", pdtoken.getString("ZXID"));
                }
            }
            if(pdtoken!=null){
                if("saveReport".equals(cmd)){       //报失新增
                    pdall.putAll(jsondata);
                    //物品编号
                    pdall.put("articleIdentifier",num);
                    //物品状态
                    pdall.put("articleCliam","47c119c9-35f8-11eb-84a3-fa163e003af7");
                    //默认属于报失
                    pdall.put("isReportloss",1);
                    //记录人id
                    pdall.put("createMan",pdtoken.get("ZXXM"));
                    pdall.put("createId",pdtoken.get("ID"));
                    //记录时间
                    pdall.put("createTime",new Date());
                    pdall.put("updateTime",new Date());
                    lostManager.addLoster(pdall);
                    //报失人id
                    pdall.put("losterId",pdall.get("id"));
                    lostManager.addReport(pdall);
                    //追加物品编号至文件表
                    String[] ids=jsondata.getString("ids").split("-");
                    for (int i=0;i<ids.length;i++){
                        pdall.put("fileId",ids[i]);
                        lostManager.UpdFileAddNumber(pdall);
                    }
                    result.put("success","true");
                }else if("delFileById".equals(cmd)){        //附件删除
                    pdall.put("id",jsondata.get("id"));
                    PageData pd=lostManager.findFileById(pdall);
                    String path=request.getServletContext().getRealPath("uploadFiles/uploadFile/")+pd.getString("file_path");
                    File f=new File(path);
                    f.delete();
                    lostManager.delFileById(pdall);
                    result.put("success","true");
                }else if("allItemLevel".equals(cmd)){
                    JSONArray ja=new JSONArray();
                    List<PageData> pdlist=statisticsService.FindLevel();
                    for (PageData pd:pdlist ) {
                        JSONObject jb=new JSONObject();
                        jb.put("level",pd.get("dictionaries_id"));
                        jb.put("name",pd.get("name"));
                        ja.add(jb);
                    }
                    result.put("data",ja);
                    result.put("success","true");
                }else if("itemState".equals(cmd)){
                    JSONArray ja=new JSONArray();
                    List<PageData> pdlist=lostManager.itemState();
                    for (PageData pd:pdlist ) {
                        JSONObject jb=new JSONObject();
                        jb.put("state",pd.get("dictionaries_id"));
                        jb.put("name",pd.get("name"));
                        ja.add(jb);
                    }
                    result.put("data",ja);
                    result.put("success","true");
                }else if("reportListPage".equals(cmd)){
                	pdall.put("path",request.getServletContext().getRealPath("uploadFiles/uploadFile/"));
                    System.out.println("物品报失列表");
                    Page page=new Page();
                    page.setCurrentPage(jsondata.getInteger("pageIndex"));
                    page.setShowCount(jsondata.getInteger("pageSize"));
                    pdall.putAll(jsondata);
                    page.setPd(pdall);
                    List<PageData> pdlist=lostManager.reportListPage(page);
                    result.put("data",pdlist);
                    result.put("success","true");
                }else if("getReportById".equals(cmd)){
                    pdall.putAll(jsondata);
                    pdall=lostManager.getReportById(pdall);
                    result.put("data",pdall);
                    result.put("success","true");
                }else if("UpdReportById".equals(cmd)){
                    pdall.putAll(jsondata);
                    pdall.put("updateTime",new Date());
                    lostManager.UpdReportById(pdall);
                    result.put("success","true");
                }else if("DelReportById".equals(cmd)){
                	pdall.put("path",request.getServletContext().getRealPath("uploadFiles/uploadFile/"));
                    pdall.put("id",jsondata.get("id"));
                    pdall.put("articlId",jsondata.get("number"));
                    lostManager.DelReportById(pdall);
                    //删除对应文件
                    fileService.delFilesByArticleId(pdall);
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
