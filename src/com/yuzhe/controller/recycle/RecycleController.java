package com.yuzhe.controller.recycle;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.ArticleManager;
import com.yuzhe.service.RecycleManager;
import com.yuzhe.service.imp.ArticleService;
import com.yuzhe.util.HandleStatus;
import com.yuzhe.util.HandleTime;
import com.yuzhe.util.HttpPostAndHeader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LEN0V0
 */
public class RecycleController implements CommonIntefate {

    @Resource(name="recycleService")
    private RecycleManager recycleService;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "articleService")
    private ArticleManager articleService;

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
                if ("recycleList".equals(cmd)){
                    Page page = new Page();
                    Integer pageIndex = jsondata.getInteger("pageIndex");
                    Integer pageSize = jsondata.getInteger("pageSize");
                    page.setCurrentPage(pageIndex);
                    page.setShowCount(pageSize);
                    page.setPd(param);
                    List<PageData> listpd = recycleService.recycleList(page);
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
                }else if("restore".equals(cmd)){
                    String[] ids=param.getString("id").split(",");
                    //从局方的接口恢复数据内容
                    //优先保存至局方接口
                    StringBuffer url = new StringBuffer();
                    url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/uploadLostProperty");
                    //变量名装换统一
                    PageData pdData = null;
                    for (String id : ids) {
                        pdData = new PageData();
                        pdData.put("id", id);
                        PageData deletedArticle = articleService.findArticleById(pdData);
                        pdData.putAll(deletedArticle);
                        pdData.put("lostPropertyName",String.valueOf(deletedArticle.get("articleName")));
                        pdData.put("pickupTime",String.valueOf(deletedArticle.get("finderTime")).substring(0,10));
                        pdData.put("createTime", String.valueOf(deletedArticle.get("createTime")));
                        pdData.put("pickupSite",String.valueOf(deletedArticle.get("finderPlace")));
                        pdData.put("isShow",String.valueOf(deletedArticle.get("isPublicity")));
                        pdData.put("description",String.valueOf(deletedArticle.get("articleDetail")));
                        pdData.put("lostPicture",String.valueOf(deletedArticle.get("lostPictureUrl")));
                        pdData.put("registryPhoto",String.valueOf(deletedArticle.get("registryPhotoUrl")));
                        pdData.put("status", HandleStatus.handleStatus(String.valueOf(deletedArticle.get("articleName"))));
                        pdData.put("handPersonName",String.valueOf(deletedArticle.get("finderName")));
                        pdData.put("handPersonPhone",String.valueOf(deletedArticle.get("finderTel")));
                        pdData.put("airportLostId",String.valueOf(deletedArticle.get("articleIdentifier")));
                        pdData.put("airportCode",String.valueOf(deletedArticle.get("airportCode")));
                        //变量名转换结束
                        JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pdData, "post");
                        String msg = jsonObject.getString("msg");
                        if(!"success".equals(msg)){
                            result.put("success","false");
                            result.put("msg","保存失败");
                            return result;
                        }
                    }
                    recycleService.restore(ids);
                    result.put("success", "true");
                    result.put("msg","还原成功");
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
}
