package com.yuzhe.service.imp;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.fh.dao.DAO;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.google.gson.JsonObject;
import com.yuzhe.service.ArticleManager;
import com.yuzhe.service.DictionariesManager;
import com.yuzhe.service.FileManager;
import com.yuzhe.util.HandleTime;
import com.yuzhe.util.HttpPostAndHeader;
import com.yuzhe.util.IdentifierUtil;
import com.yuzhe.util.NotNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-07 18:19
 **/
@Service("articleService")
@Transactional(propagation= Propagation.REQUIRES_NEW)
public class ArticleService implements ArticleManager {

    @Resource(name = "daoSupport")
    DAO dao;

    @Resource(name = "lsFileService")
    private FileManager fileService;

    @Resource(name = "dictionService")
    DictionariesManager dictionariesService;

    @Override
    public List<PageData> findArticlePage(Page Page) throws Exception {
        return (List<PageData>) dao.findForList("ArticleMapper.findArticlelistPage", Page);
    }

    @Override
    public List<PageData> findArticleList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ArticleMapper.findArticlelist", pd);
    }

    @Override
    public int deleteArticleById(PageData pd) throws Exception {
        int articleNumber = (int) dao.update("ArticleMapper.deleteArticleById", pd);
        return articleNumber ;
    }

    @Override
    public JSONObject saveArticle(PageData pd, JSONObject result, PageData pd_token) throws Exception {
        synchronized (this) {
            IdentifierUtil identifierUtil = new IdentifierUtil();
            //获取identifier也就是airportLostId
            pd.put("isReportloss", "0");
            String identifier = identifierUtil.getIdentifier(pd);
            pd.put("articleIdentifier", identifier);
            //优先保存至局方接口
            StringBuffer url = new StringBuffer();
            url.append("https://122.119.160.178:8443/sae/sae-liqp-api/1.0/api/uploadLostProperty");
            //变量名装换统一
            PageData pdData = new PageData();
            pdData.putAll(pd);
            pdData.put("lostPropertyName", String.valueOf(pd.get("articleName")));
            pdData.put("pickupTime", String.valueOf(pd.get("finderTime")).substring(0, 10));
            pdData.put("createTime", HandleTime.getDate());
            pdData.put("pickupSite", String.valueOf(pd.get("finderPlace")));
            pdData.put("isShow", String.valueOf(pd.get("isPublicity")));
            pdData.put("description", String.valueOf(pd.get("articleDetail")));
            pdData.put("lostPicture", String.valueOf(pd.get("lostPictureUrl")));
            pdData.put("registryPhoto", String.valueOf(pd.get("registryPhotoUrl")));
            pdData.put("status", "1");
            pdData.put("handPersonName", String.valueOf(pd.get("finderName")));
            pdData.put("handPersonPhone", String.valueOf(pd.get("finderTel")));
            pdData.put("airportLostId", identifier);
            pdData.put("airportCode", "HAK");
            //变量名转换结束
            JSONObject jsonObject = HttpPostAndHeader.getData(url.toString(), pdData, "post");
            String msg = jsonObject.getString("msg");
            if (!"success".equals(msg)) {
                result.put("success", "false");
                result.put("msg", "保存失败");
                return result;
            }
            //防止为空报错
            String[] strName =
                    new String[]{"receivePlace", "processDetail", "finderName", "finderPlace", "finderTime", "finderTel", "finderFlightNumber"};
            NotNullUtil.notNull(pd, strName);
            //获取指定天数后的日期
            PageData level = dictionariesService.findObjById(pd);
            String dayStr = level.getString("remark");
            boolean flag = dayStr.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
            if (flag == false) {
                result.put("success", "false");
                result.put("msg", "请传递正确的物品等级");
                return result;
            }
            Integer day = Integer.valueOf(dayStr);
            String articleDuedate = HandleTime.getFutureTime(day);
            pd.put("articleDuedate", articleDuedate);
            //键入创建者
            String createMan = pd_token.getString("ZXXM");
            String createId = pd_token.getString("ID");
            if (createMan == null) {
                createMan = "";
            }
            if (createId == null) {
                createId = "";
            }
            pd.put("createMan", createMan);
            pd.put("createId", createId);
            int saveFinder = (int) dao.save("FinderMapper.saveFinder", pd);
            int saveArticle = (int) dao.save("ArticleMapper.saveArticle", pd);
            int saveProcess = (int) dao.save("ProcessMapper.saveRegister", pd);

            if (!"".equals(pd.getString("ids")) && pd.getString("ids") != null) {
                String[] articleIds = String.valueOf(pd.get("ids")).split("-");
                pd.put("ids", articleIds);
                fileService.updateNullFile(pd);
            }
            if (saveFinder + saveArticle + saveProcess > 0) {
                result.put("success", "true");
                result.put("msg", "保存成功");
            } else {
                result.put("success", "true");
                result.put("msg", "保存失败");
            }

            return result;
        }
    }

    @Override
    public PageData findArticleById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ArticleMapper.findArticleById",pd);
    }

    @Override
    public int updateArticleById(PageData pd) throws Exception {
       int articleNum = (int) dao.update("ArticleMapper.updateArticleById", pd);
       int finderNum = (int) dao.update("FinderMapper.updateFinder", pd);
       return articleNum + finderNum;
    }

    @Override
    public int batchInsert(List<PageData> pd) throws Exception {
        return (int) dao.save("ArticleMapper.batchInsert", pd);
    }

    @Override
    public Integer insertError(List<PageData> pd) throws Exception {
        return (int) dao.save("ArticleMapper.insertError", pd);
    }



    @Override
    public void saveClaimant(PageData pd) throws Exception {
        dao.save("ArticleMapper.saveClaimant",pd);
    }

    @Override
    public void updArticleToClaimant(PageData pd) throws Exception {
        dao.update("ArticleMapper.updArticleToClaimant",pd);
    }

    @Override
    public void updArticleToReceive(PageData pd) throws Exception {
        dao.update("ArticleMapper.updArticleToReceive",pd);
    }

    @Override
    public int articleTodayCount(PageData pd) throws Exception {
        return (int) dao.findForObject("ArticleMapper.articleTodayCount", pd);
    }

    @Override
    public Integer toMailCount() throws Exception {
        return (Integer) dao.findForObject("ArticleMapper.toMailCount", new PageData());
    }

    @Override
    public Integer OverdueAgain(PageData pd) throws Exception {
        return (Integer) dao.update("ArticleMapper.OverdueAgain",pd);
    }

    @Override
    public Integer updateClaimant(PageData pd) throws Exception {
        return (Integer)dao.update("ArticleMapper.updateClaimant", pd);
    }

    @Override
    public Integer updateArticlePublicity(String ids) throws Exception {
        return (Integer)dao.update("ArticleMapper.updateArticlePublicity", ids);
    }

    @Override
    public List<PageData> findArticlesById(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ArticleMapper.findArticlesById", pd);
    }

    @Override
    public List<PageData> claimStatusCount(PageData pd) throws Exception {
        return  (List<PageData>) dao.findForList("ArticleMapper.claimStatusCount", pd);
    }

    @Override
    public PageData articleYearCount(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ArticleMapper.articleYearCount", pd);
    }
}