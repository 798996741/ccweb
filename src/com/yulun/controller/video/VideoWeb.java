package com.yulun.controller.video;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.BlacklistManager;
import com.yulun.service.VideoManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Demo 录像管理
 *
 * @author zhj
 * @date 2020/9/29
 */
public class VideoWeb implements CommonIntefate {

    @Resource(name = "blacklistService")
    private BlacklistManager blacklistService;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "videoService")
    private VideoManager videoService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        System.out.println("com  on   ===============");
        JSONObject result = new JSONObject();
        PageData param = new PageData();
        try {
            JSONObject jsondata = data.getJSONObject("data");
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData stoken = new PageData();
            stoken.put("TOKENID", jsondata.get("tokenid"));
            PageData pdtoken = zxlbService.findByTokenId(stoken);
            if (pdtoken != null) {
                if ("videolist".equals(cmd)) {
                    Page page = new Page();
                    Integer pageIndex = jsondata.getInteger("pageIndex");
                    Integer pageSize = jsondata.getInteger("pageSize");
                    page.setCurrentPage(pageIndex);
                    page.setShowCount(pageSize);
                    param.put("starttime", jsondata.getString("starttime"));
                    param.put("endtime", jsondata.getString("endtime"));
                    param.put("zxid", jsondata.getString("zxid"));
                    param.put("keywords", jsondata.getString("keywords"));
                    param.put("dept", pdtoken.getString("dept"));
                    page.setPd(param);
                    List<PageData> listpd = videoService.videolist(page);
                    if (listpd.size() > 0) {
                        stoken.put("param_code", "videosvr");
                        //获取视频服务器地址
                        PageData pdparam = blacklistService.findSysparam(stoken);
                        for (PageData pd : listpd) {
                            String url = pdparam.getString("param_value") + pd.getString("url") + "/sender.webm";
                            pd.put("url", url);
                        }
                        System.out.println(pdparam.getString("param_value"));
                        System.out.println(listpd.toString());
                        result.put("data", listpd);
                        result.put("pageId", pageIndex);
                        result.put("pageCount", page.getTotalPage());
                        result.put("recordCount", page.getTotalResult());
                        result.put("success", "true");
                    }else {
                        result.put("success", "false");
                        result.put("msg", "暂无数据");
                    }
                }else if("delvideo".equals(cmd)){
                    String[] id=jsondata.getString("id").split(",");
                    System.out.println(id.toString());
                    videoService.delVideoAll(id);
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
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
            result.put("msg", "操作异常");
        }
        return result;
    }

}
