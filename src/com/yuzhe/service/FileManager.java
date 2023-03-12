package com.yuzhe.service;

import com.fh.util.PageData;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 10:56
 **/
public interface FileManager {

    List<PageData> FileList(PageData pd) throws Exception;

    void delFilesByArticleId(PageData pd) throws Exception;

    int updateNullFile(PageData pd) throws Exception;

    void delFileToArticleIdIsNull(PageData pd) throws Exception;

}
