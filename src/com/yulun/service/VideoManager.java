package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface VideoManager {

    List<PageData> videolist(Page page) throws Exception;

    void delVideoAll(String[] ids)throws Exception;
}
