package com.yuzhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.fh.entity.Page;
import com.fh.service.system.user.UserManager;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yuzhe.enmu.Status;
import com.yuzhe.service.*;
import com.yuzhe.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Aliar
 * @Time: 2020-08-07 18:17
 **/
@RestController
public class ArticleWeb implements CommonIntefate {

    @Resource(name = "articleService")
    private ArticleManager articleService;

    @Resource(name = "dictionService")
    DictionariesManager dictionariesService;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "processService")
    private ProcessManager processService;

    @Resource(name="userService")
    private UserManager userService;

    @Resource(name = "lsFileService")
    private FileManager fileService;

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
        log.debug("进入类的所有数据" + data.toJSONString());
        JSONObject result=new JSONObject();
        PageData pd = new PageData();
        String cmd = data.getString("cmd")==null?"":data.getString("cmd");
        PageData pd_stoken=new PageData();
        JSONObject json = data.getJSONObject("data");
        pd_stoken.put("TOKENID", json.get("tokenid"));
//        try {
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        String username=(json.getString("username")==null?"":json.getString("username"));
        if(!"".equals(username)){
            pd_stoken.put("USERNAME", username);
            pd_token=userService.findByUsername(pd_stoken);
            if(pd_token!=null){
                pd_token.put("dept", pd_token.getString("DWBM"));
                pd_token.put("ZXYH", username);
            }
            pd.put("ZXID", username);
            pd.put("systype", "1");
        }else{
            if(pd_token!=null){
                pd.put("ZXID", pd_token.getString("ZXID"));
            }
        }
        pd.putAll(json);
        //数据统计不需要验证token
        if("claimStatusCount".equals(cmd)){
            List<PageData> countList = articleService.claimStatusCount(pd);
            PageData countPd = new PageData();
            String[] dataText = new String[2];
            for (int i = 0; i < countList.size(); i++) {
                dataText[i] = countList.get(i).getString("name");
            }
            JSONArray dataJson = JSONArray.parseArray(JSONArray.toJSON(countList).toString());
            countPd.put("dataText", dataText);
            countPd.put("data", dataJson);
            result.put("data", countPd);
            result.put(MSG, "数据获取成功");
            result.put(SUCCESS, TRUE);
            return result;
        }
        //数据统计不需要验证token
        if("articleYearCount".equals(cmd)){
            PageData countPd = articleService.articleYearCount(pd);
            List<PageData> countList = new LinkedList<PageData>();
            String[] dataText = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
            for(int i = 1; i  <= 12; i++){
                String value = String.valueOf(countPd.get(String.valueOf(i)));
                String name = dataText[i - 1];
                PageData node = new PageData();
                node.put("name", name);
                node.put("value", value);
                countList.add(node);
            }
            JSONArray dataJson = JSONArray.parseArray(JSONArray.toJSON(countList).toString());
            PageData countListPd = new PageData();
            countListPd.put("dataText", dataText);
            countListPd.put("data", dataJson);
            result.put("data",countListPd);
            result.put(MSG, "数据获取成功");
            result.put(SUCCESS, TRUE);
            return result;
        }
        if (null == pd_token) {
            result.put(SUCCESS, FALSE);
            result.put(MSG, "请重新登陆");
            return result;
        }
        if ("findArticlePage".equals(cmd)) {
            Page page = new Page();
            pageIndex = json.getInteger("pageIndex");
            pageSize = json.getInteger("pageSize");
            if(pageSize == null || "".equals(pageSize) || pageIndex == null || "".equals(pageIndex) ){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"pageSizeOrPageIndexNotFind");
                return result;
            }else {
                page.setCurrentPage(pageIndex);
                page.setShowCount(pageSize);
            }
            page.setPd(pd);
            List<PageData> articlePage = articleService.findArticlePage(page);
            if(articlePage.size() > 0){
                result.put("data",articlePage);
                result.put(SUCCESS,TRUE);
                result.put("pageId",pageIndex);
                result.put("pageCount",page.getTotalPage());
                result.put("recordCount",page.getTotalResult());
            }else{
                result.put(SUCCESS,FALSE);
                result.put(MSG,"暂无数据");
            }
        }

        if ("deleteArticleById".equals(cmd)) {
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

        if ("saveArticle".equals(cmd)) {
                articleService.saveArticle(pd, result, pd_token);
        }

        if ("articleDetail".equals(cmd)) {
            String id = String.valueOf(pd.get("id"));
            String articleIdentifier = String.valueOf(pd.get("articleIdentifier"));
            boolean flag = false;
            if(!(null == id || "".equals(id))){
                flag = true;
            }
            if(!(null == articleIdentifier || "".equals(articleIdentifier))){
                flag = true;
            }
            if(!flag) {
                result.put(SUCCESS, FALSE);
                result.put(MSG, "idOrArticleIdentifierIsNull");
                return result;
            }
            PageData article = articleService.findArticleById(pd);
            if(article != null){
                String lostPictureURL= Const.FILEPATHFILEOA+article.getString("lostPictureURL");
                String registryPhotoURL= Const.FILEPATHFILEOA+article.getString("registryPhotoURL");
                article.put("lostPictureURL",lostPictureURL);
                article.put("registryPhotoURL",registryPhotoURL);
                result.put("data", article);
                result.put(SUCCESS,TRUE);
            }else{
                result.put(SUCCESS,FALSE);
                result.put(MSG,"查无此物");
            }
        }

        if ("updateArticle".equals(cmd)) {
            log.debug("这是接收到的数据" + pd);
            //id判空
            String id = String.valueOf(pd.get("id"));
            if(null == pd.get("id")){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"idIsNull");
                return result;
            }
            //获取原数据
            PageData updateTarget = articleService.findArticleById(pd);
            String articleIdentifier = String.valueOf(updateTarget.get("articleIdentifier"));
            //优先保存至局方接口
            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/uploadLostProperty");
            //变量名装换统一
            PageData pdData = new PageData();
            pdData.putAll(pd);
            pdData.put("lostPropertyName",String.valueOf(pd.get("articleName")));
            pdData.put("pickupTime",String.valueOf(pd.get("finderTime")).substring(0,10));
            pdData.put("createTime",HandleTime.getDate());
            pdData.put("pickupSite",String.valueOf(pd.get("finderPlace")));
            pdData.put("isShow",String.valueOf(pd.get("isPublicity")));
            pdData.put("description",String.valueOf(pd.get("articleDetail")));
            String lostPictureUrl = String.valueOf(pd.get("lostPictureUrl"));
            if("null".equals(lostPictureUrl) || lostPictureUrl == null || "".equals(lostPictureUrl))
            {
                pdData.put("lostPicture",updateTarget.getString("lostPictureUrl"));
                pd.put("lostPictureUrl",updateTarget.getString("lostPictureUrl"));
            }else {
                pdData.put("lostPicture", lostPictureUrl);
                pd.put("lostPictureUrl", lostPictureUrl);
            }
            String registryPhoto = String.valueOf(pd.get("registryPhotoUrl"));
            if(registryPhoto != null & !"null".equals(registryPhoto) && !"".equals(registryPhoto)) {
                pdData.put("registryPhoto", registryPhoto);
            }
            pdData.put("status", HandleStatus.handleStatus(String.valueOf(pd.get("articleCliam"))));
            pdData.put("handPersonName",String.valueOf(pd.get("finderName")));
            pdData.put("handPersonPhone",String.valueOf(pd.get("finderTel")));
            pdData.put("airportLostId",articleIdentifier);
            pdData.put("airportCode","HAK");
            //变量名转换结束
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pdData, "post");
            String msg = jsonObject.getString("msg");
            if(!SUCCESS.equals(msg)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"保存失败");
                return result;
            }
            int processNum = 0;
            //键入创建者
            String finderId = String.valueOf(updateTarget.get("finderId"));
            pd.put("finderId" ,finderId);
            String createMan = pd_token.getString("ZXXM");
            String createId = pd_token.getString("ID");
            if(createMan == null) {
                createMan = "";
            }
            if(createId == null) {
                createId = "";
            }
            pd.put("createMan", createMan);
            pd.put("createId", createId);
            pd.put("articleId", id);
            if(updateTarget.containsKey("articleDuedate") && updateTarget.containsKey("articleLevel")) {
                String nowArticleDuedate = String.valueOf(pd.get("articleDuedate"));
                String oldArticleDuedate = String.valueOf(updateTarget.get("articleDuedate"));
                if (oldArticleDuedate.equals(nowArticleDuedate)) {
                    if (!updateTarget.getString("articleLevel").equals(String.valueOf(pd.get("articleLevel")))) {
                        String levelStr = String.valueOf(dictionariesService.findObjById(pd).get("remark"));
                        Integer levelInt = Integer.valueOf(levelStr);
                        //日期计算
                        String createTime = updateTarget.getString("createTime");
                        String newArticleDuedate = HandleTime.getFutureTime(createTime, levelInt);
                        pd.put("articleDuedate", newArticleDuedate);
                        pd.put("processDetail", "修改物品等级，到期时间从" + oldArticleDuedate.substring(0,10)  + "修改至" +newArticleDuedate.substring(0,10));
                        //保存修改记录到操作过程中
                        processNum = processService.saveProcessByUpdate(pd);
                    }
                } else {
                    //保存修改记录到操作过程中
                    pd.put("processDetail", "修改到期日期,从" + oldArticleDuedate.substring(0,10) + "修改至" + nowArticleDuedate.substring(0,10));
                    processNum = processService.saveProcessByUpdate(pd);
                }
            }else {
                result.put(SUCCESS,FALSE);
                result.put(MSG,"dataIsNull");
                return result;
            }
            //保存
            int updateNum = articleService.updateArticleById(pd);
            int resultNum = processNum + updateNum;
            if(!"".equals(pd.getString("ids")) && pd.getString("ids") != null) {
                String[] articleIds = StringUtil.StrList(String.valueOf(pd.get("ids")));
                pd.put("ids", articleIds);
                fileService.updateNullFile(pd);
            }
            //返回
            if(resultNum >= 2){
                result.put(SUCCESS,TRUE);
                result.put(MSG,"修改成功");
                log.info("修改成功，共修改" + resultNum + "条");
            }else {
                result.put(SUCCESS,FALSE);
                result.put(MSG,"修改失败");
                log.info("修改失败");
            }
            log.debug("pd" + pd);
            log.debug("pdData" + pdData);
        }
        //领取
        if("getTheGoods".equals(cmd)){
            //id判空
            String id = String.valueOf(pd.get("articleId"));
            if(null == pd.get("articleId")){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"idIsNull");
                return result;
            }
            //begin___________保持状态一致
            //获取失物原本的数据内容
            pd.put("id", String.valueOf(pd.get("articleId")));
            PageData oldArticle = articleService.findArticleById(pd);
            //失物属性名同步
            PageData pdData = new PageData();
            pdData.putAll(oldArticle);
            pdData.put("status", Status.receiveStatuOf(String.valueOf(pd.get("articleClaim"))));
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
            pd.put("createTime",new Date());
            pd.put("updateTime",new Date());
            //用来判断修改还是新增
            String claimant = String.valueOf(oldArticle.get("articleClaimant"));
            if(!ObjectUtils.isEmpty(claimant) &&!"".equals(claimant) && !"null".equals(claimant)){
                pd.put("id", claimant);
                articleService.updateClaimant(pd);
                pd.put("articleClaimant",claimant);
            }else {
                articleService.saveClaimant(pd);
                pd.put("articleClaimant",pd.get("id"));
            }
            articleService.updArticleToClaimant(pd);
            pd.put("createId",pd_token.get("ID"));
            processService.saveProcessByGet(pd);
            result.put("MSG", "领取成功");
            result.put("success","true");
        }
        //接收
        if("receive".equals(cmd)){
            //id判空
            String id = String.valueOf(pd.get("articleId"));
            if(null == id || "null".equals(id) || "".equals(id) ){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"idIsNull");
                return result;
            }
            pd.put("createId",pd_token.get("ID"));
            processService.saveProcessByReceive(pd);
            result.put(SUCCESS,TRUE);
        }


        //批量接收
        if("batchReceive".equals(cmd)){
            //id判空
            String ids = String.valueOf(pd.get("articleId"));
            if(null == ids || "null".equals(ids) || "".equals(ids)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"idIsNull");
                return result;
            }
            pd.put("createId",pd_token.get("ID"));
            pd.put("ids",ids);
            int successCount = processService.batchSaveProcessByReceive(pd);
            if(successCount >= 1) {
                result.put(SUCCESS, TRUE);
                result.put(MSG, "批量接收成功，共接收" + successCount + "件物品");
            }else{
                result.put(SUCCESS, FALSE);
                result.put(MSG, "批量接收失败");
            }
        }

        if("batchSetPublicity".equals(cmd)){
            String ids = String.valueOf(pd.get("ids"));
            String isPublicity = String.valueOf(pd.get("isPublicity"));
            if(null == ids || "null".equals(ids) || "".equals(ids)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"idIsNull");
                return result;
            }
            if(null == isPublicity || "null".equals(isPublicity) || "".equals(isPublicity)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"paramsIsNull");
                return result;
            }
            //局方数据同步
            //begin___________保持状态一致
            //获取失物原本的数据内容
            List<PageData> oldArticles = articleService.findArticlesById(pd);
            //失物属性名同步
            List<PageData> pdList = new ArrayList<PageData>();
            for (PageData oldArticle : oldArticles) {
            PageData pdData = new PageData();
            pdData.putAll(oldArticle);
            pdData.put("status", Status.receiveStatuOf(String.valueOf(pd.get("articleClaim"))));
            pdData.put("airportLostId",String.valueOf(oldArticle.get("articleIdentifier")));
            pdData.put("lostPropertyName",String.valueOf(oldArticle.get("articleName")));
            pdData.put("pickupTime",String.valueOf(oldArticle.get("finderTime")).substring(0,10));
            pdData.put("createTime",String.valueOf(oldArticle.get("createTime")));
            pdData.put("pickupSite",String.valueOf(oldArticle.get("finderPlace")));
            pdData.put("isShow",isPublicity);
            pdData.put("isPublicity",isPublicity);
            pdData.put("description",String.valueOf(oldArticle.get("articleDetail")));
            pdData.put("lostPicture",String.valueOf(oldArticle.get("lostPictureUrl")));
            pdData.put("registryPhoto",String.valueOf(oldArticle.get("registryPhotoUrl")));
            pdData.put("handPersonName",String.valueOf(oldArticle.get("finderName")));
            pdData.put("handPersonPhone",String.valueOf(oldArticle.get("finderTel")));
            pdData.put("airportCode",String.valueOf(oldArticle.get("airportCode")));
            pdList.add(pdData);
            }
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(pdList));
            //将数据传递至局方接口
            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/uploadBatchLostProperty");
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), jsonArray, "post");
            String msg = jsonObject.getString("msg");
            if(!SUCCESS.equals(msg)){
                result.put(SUCCESS,FALSE);
                result.put(MSG,"数据同步失败");
                return result;
            }
            //end_____________保持状态一致
            Integer successCount = articleService.updateArticlePublicity(ids);
            if(successCount >= 1) {
                result.put(SUCCESS, TRUE);
                result.put(MSG, "修改公示状态成功，共修改" + successCount + "件物品");
            }else{
                result.put(SUCCESS, FALSE);
                result.put(MSG, "修改公示状态失败，请重试");
            }
        }

        if("toMailCount".equals(cmd)){
            Integer mailCount = articleService.toMailCount();
            if(mailCount >= 0){
                PageData countPd = new PageData();
                countPd.put("mailCount",mailCount);
                result.put("data", countPd);
                result.put(SUCCESS,TRUE);
                result.put(MSG, "获取成功");
            }else{
                result.put(SUCCESS,FALSE);
                result.put(MSG, "获取失败");
            }
        }
//        }catch (Exception e) {
//            result.put(MSG, "异常");
//            result.put(SUCCESS, FALSE);
//        } finally {
//            return result;
//        }
         return result;

    }

}