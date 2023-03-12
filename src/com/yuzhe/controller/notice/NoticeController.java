package com.yuzhe.controller.notice;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.NoticeManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author LEN0V0
 */
public class NoticeController implements CommonIntefate {

    @Resource(name = "lostAndFoundNoticeService")
    private NoticeManager lostAndFoundNoticeService;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        PageData param = new PageData();
        try{
            JSONObject jsondata = data.getJSONObject("data");
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData stoken = new PageData();
            stoken.put("TOKENID", jsondata.get("tokenid"));
            PageData pdtoken = zxlbService.findByTokenId(stoken);
            param.putAll(jsondata);
            if(pdtoken!=null){
                if ("saveNotice".equals(cmd)){
                    List<PageData> listpd=lostAndFoundNoticeService.getAllUser();
                    String zxids="";
                    for (PageData pd:listpd) {
                        zxids+=pd.getString("zxid")+",";
                    }
                    param.put("userid",zxids);
                    param.put("createtime",new Date());
                    param.put("setname",pdtoken.get("ZXID"));
                    String uuid32 = getUUID32();
                    param.put("id", uuid32);
                    lostAndFoundNoticeService.saveNotice(param);
                    result.put("success", "true");
                    result.put("msg","新增成功");
                }else if("noticeList".equals(cmd)){
                    Page page = new Page();
                    Integer pageIndex = jsondata.getInteger("pageIndex");
                    Integer pageSize = jsondata.getInteger("pageSize");
                    page.setCurrentPage(pageIndex);
                    page.setShowCount(pageSize);
                    page.setPd(param);
                    List<PageData> listpd =lostAndFoundNoticeService.noticeList(page);
                    if (listpd.size() > 0){
                        result.put("data", listpd);
                        result.put("pageId", pageIndex);
                        result.put("pageCount", page.getTotalPage());
                        result.put("recordCount", page.getTotalResult());
                        result.put("success", "true");
                    }else {
                        result.put("success", "false");
                        result.put("msg", "暂无数据");
                    }
                }else if("findNoticeById".equals(cmd)){
                    PageData pd=lostAndFoundNoticeService.findNoticeById(param);
                    result.put("data", pd);
                    result.put("success", "true");
                }else if("updNotice".equals(cmd)){
                    lostAndFoundNoticeService.updNotice(param);
                    result.put("success", "true");
                    result.put("msg","修改成功");
                }else if("delNotice".equals(cmd)){
                    String[] id=param.getString("noticeid").split(",");
                    lostAndFoundNoticeService.delNotice(id);
                    result.put("success", "true");
                    result.put("msg","删除成功");
                }else{
                    result.put("success","false");
                    result.put("msg","访问异常");
                }
            }else {
                result.put("success", "false");
                result.put("msg", "登录超时，请重新登录");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("success", "false");
            result.put("msg", "操作异常");
        }
        return result;
    }

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
}
