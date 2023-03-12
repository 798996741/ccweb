package com.yuzhe.service;



import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-07 18:19
 **/
public interface ArticleManager {
    List<PageData> findArticlePage(Page page) throws Exception;

    List<PageData> findArticleList(PageData pd) throws Exception;

    int deleteArticleById(PageData pd) throws Exception;

    JSONObject saveArticle(PageData pd, JSONObject result, PageData pd_token) throws Exception;

    PageData findArticleById(PageData pd) throws Exception;

    int updateArticleById(PageData pd) throws Exception;

    int batchInsert(List<PageData> pd) throws Exception;

    Integer insertError(List<PageData> pd) throws Exception;


    //新增领取人
    void saveClaimant(PageData pd) throws Exception;

    //领取后修改物品信息
    void updArticleToClaimant(PageData pd) throws Exception;

    //接收后修改物品状态
    void updArticleToReceive(PageData pd) throws Exception;

    int articleTodayCount(PageData pd)  throws Exception;

    Integer toMailCount()  throws Exception;

    Integer OverdueAgain(PageData pd) throws Exception;

    Integer updateClaimant(PageData pd) throws Exception;

    /**
     * 批量修改公示状态
     * @param ids
     * @return 修改公示状态的数量
     */
    Integer updateArticlePublicity(String ids)  throws Exception;

    /**
     * 根据id号批量查找物品
     * @param pd
     * @return
     */
    List<PageData> findArticlesById(PageData pd) throws Exception;

    /**
     * 查询领取状态统计的数量：未领取、已领取
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> claimStatusCount(PageData pd) throws Exception;

    /**
     * 查询某年每个月的数据
     * @param pd
     * @return
     * @throws Exception
     */
    PageData articleYearCount(PageData pd) throws Exception;
}
