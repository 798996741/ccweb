package com.yuzhe.controller.OverdueItems;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.user.UserManager;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.service.*;
import com.yuzhe.util.HandleTime;
import com.yuzhe.util.HttpPostAndHeader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class OverdueItemsController implements CommonIntefate{


    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

	@Resource(name = "overdueItemsService")
    private OverdueItemsManager overdueItemsManager;

    @Resource(name = "lsFileService")
    private FileManager fileService;

    @Resource(name="userService")
    private UserManager userService;

    @Resource(name = "articleService")
    private ArticleManager articleService;

    @Resource(name = "dictionService")
    DictionariesManager dictionariesService;

    @Resource(name = "processService")
    private ProcessManager processService;

    private final String SUCCESS = "success";
    private final String MSG = "msg";
    private final String TRUE = "true";
    private final String FALSE = "false";
    private Integer  pageIndex;
    private Integer  pageSize;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        Log log = LogFactory.getLog(this.getClass());
        JSONObject result = new JSONObject();
        PageData pd = new PageData();
        String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
        PageData pd_stoken = new PageData();
        JSONObject json = data.getJSONObject("data");
        pd_stoken.put("TOKENID", json.get("tokenid"));
//        try {
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String username = (json.getString("username") == null ? "" : json.getString("username"));
            if (!"".equals(username)) {
                pd_stoken.put("USERNAME", username);
                pd_token = userService.findByUsername(pd_stoken);
                if (pd_token != null) {
                    pd_token.put("dept", pd_token.getString("DWBM"));
                    pd_token.put("ZXYH", username);
                }
                pd.put("ZXID", username);
                pd.put("systype", "1");
            } else {
                if (pd_token != null) {
                    pd.put("ZXID", pd_token.getString("ZXID"));
                }
            }
            if (null == pd_token) {
                result.put(SUCCESS, FALSE);
                result.put(MSG, "请重新登陆");
                return result;
            }
            pd.putAll(json);
        if("getCount".equals(cmd)){
            Integer count=overdueItemsManager.OverdueCount();
            result.put("count", count);
            result.put("success","true");
            }

        if("deleteArticleById".equals(cmd)){
            String id = String.valueOf(pd.get("id"));
            if(null == id || "".equals(id)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"idIsNull");
                return result;
            }
            //begin___________保持状态一致
            //获取失物原本的数据内容
            PageData oldArticle = articleService.findArticleById(pd);
            //失物属性名同步
            PageData pdData = new PageData();
            pdData.putAll(oldArticle);
            pdData.put("status", 7);
            pdData.put("airportLostId",String.valueOf(oldArticle.get("articleIdentifier")));
            pdData.put("lostPropertyName",String.valueOf(oldArticle.get("articleName")));
            pdData.put("pickupTime",String.valueOf(oldArticle.get("finderTime")).substring(0,10));
            pdData.put("createTime",String.valueOf(oldArticle.get("createTime")));
            pdData.put("pickupSite",String.valueOf(oldArticle.get("finderPlace")));
            pdData.put("isShow",String.valueOf(oldArticle.get("isPublicity")));
            pdData.put("description",String.valueOf(oldArticle.get("articleDetail")));
            pdData.put("lostPicture",String.valueOf(oldArticle.get("lostPictureUrl")));
            pdData.put("registryPhoto",String.valueOf(oldArticle.get("registryPhotoUrl")));
            pdData.put("handPersonName",String.valueOf(oldArticle.get("finderName")));
            pdData.put("handPersonPhone",String.valueOf(oldArticle.get("finderTel")));
            pdData.put("airportCode",String.valueOf(oldArticle.get("airportCode")));
            //将数据传递至局方接口
            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/uploadLostProperty");
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pdData, "post");
            String msg = jsonObject.getString("msg");
            if(!SUCCESS.equals(msg)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"保存失败");
                return result;
            }
            //end_____________保持状态一致
            int deleteNumber = articleService.deleteArticleById(pd);
            if(!"".equals(pd.getString("articlId")) && pd.getString("articlId") != null) {
                fileService.delFilesByArticleId(pd);
            }
            log.info("删除失物管理及相关信息共" + deleteNumber + "条");
            if(deleteNumber > 0){
                result.put(SUCCESS,TRUE);
                result.put(MSG,"删除成功");
            }else {
                result.put(SUCCESS,FALSE);
                result.put(MSG,"删除失败");
            }
        }

        if("Overduedispose".equals(cmd)){
            //begin___________保持状态一致
            //获取失物原本的数据内容
            PageData oldArticle = articleService.findArticleById(pd);
            //失物属性名同步
            PageData pdData = new PageData();
            pdData.putAll(oldArticle);
            pdData.put("status", 6);
            pdData.put("airportLostId",String.valueOf(oldArticle.get("articleIdentifier")));
            pdData.put("lostPropertyName",String.valueOf(oldArticle.get("articleName")));
            pdData.put("pickupTime",String.valueOf(oldArticle.get("finderTime")).substring(0,10));
            pdData.put("createTime",String.valueOf(oldArticle.get("createTime")));
            pdData.put("pickupSite",String.valueOf(oldArticle.get("finderPlace")));
            pdData.put("isShow",String.valueOf(oldArticle.get("isPublicity")));
            pdData.put("description",String.valueOf(oldArticle.get("articleDetail")));
            pdData.put("lostPicture",String.valueOf(oldArticle.get("lostPictureUrl")));
            pdData.put("registryPhoto",String.valueOf(oldArticle.get("registryPhotoUrl")));
            pdData.put("handPersonName",String.valueOf(oldArticle.get("finderName")));
            pdData.put("handPersonPhone",String.valueOf(oldArticle.get("finderTel")));
            pdData.put("airportCode",String.valueOf(oldArticle.get("airportCode")));
            //将数据传递至局方接口
            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/uploadLostProperty");
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pdData, "post");
            String msg = jsonObject.getString("msg");
            if(!SUCCESS.equals(msg)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"数据同步失败");
                return result;
            }
            //end_____________保持状态一致
            pd.put("articleCliam","f15ce8ff-39ba-4e85-b124-5fce9a58cc5b");
            pd.put("isEliminate",1);
            pd.put("articleEliminateid",pd_token.get("ID"));
            pd.put("NowDate",new Date());
            pd.put("id",json.get("id"));
            overdueItemsManager.Overduedispose(pd);
            pd.put("articleId",json.get("id"));
            pd.put("userId",pd_token.get("ID"));
            pd.put("processType","f15ce8ff-39ba-4e85-b124-5fce9a58cc5b");
            pd.put("processDetail",json.get("processDetail"));
            pd.put("createTime",new Date());
            overdueItemsManager.SaveProcess(pd);
            result.put("success","true");
        }
        if("OverdueAgain".equals(cmd)){
            pd.putAll(json);
            pd.put("id", String.valueOf(json.get("id")));
            //根据物品等级重新定义到期时间
            pd.put("articleLevel", String.valueOf(pd.get("Level")));
            String levelStr = String.valueOf(dictionariesService.findObjById(pd).get("remark"));
            Integer levelInt = Integer.valueOf(levelStr);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = df.format(new Date());
            String newArticleDuedate = HandleTime.getFutureTime(nowTime, levelInt);
            pd.put("articleDuedate", newArticleDuedate);
            pd.put("processDetail", String.valueOf(pd.get("processDetail")));
            pd.put("createId",pd_token.get("ID"));
            //保存修改记录到操作过程中
            processService.saveProcessByAgain(pd);
            int num =  articleService.OverdueAgain(pd);
            if(num == 1 && String.valueOf(pd.get("flag")) != null){
                result.put(SUCCESS,TRUE);
                result.put(MSG,"重新入库成功");
                return result;
            }else{
                result.put(SUCCESS,FALSE);
                result.put(MSG,"重新入库失败");
                return result;
            }
        }
//        } catch (Exception e) {
//            result.put(MSG, "异常");
//            result.put(SUCCESS, FALSE);
//        } finally {
//            return result;
//        }
        return result;
    }
}
