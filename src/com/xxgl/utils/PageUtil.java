package com.xxgl.utils;

import java.util.List;

import com.fh.util.PageData;

/**
 * 自定义List分页工具
 * @author huangjianling
 */
public class PageUtil {

    /**
     * 开始分页
     * @param list
     * @param pageNum 页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static PageData startPage(List<PageData> list, Integer pageNum,
            Integer pageSize) {
    	PageData pd=new PageData();
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        if(pageNum>=pageCount){
        	pageNum=pageCount;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List<PageData> pageList = list.subList(fromIndex, toIndex);
        pd.put("pageList", pageList);
        pd.put("pageCount", pageCount);
        pd.put("pageNum", pageNum);
        return pd;
    }
}