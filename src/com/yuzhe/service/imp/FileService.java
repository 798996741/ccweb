package com.yuzhe.service.imp;

import com.fh.dao.DAO;
import com.fh.util.PageData;
import com.yuzhe.service.FileManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 10:56
 **/
@Service("lsFileService")
public class FileService implements FileManager {

    @Resource(name = "daoSupport")
    DAO dao;

    @Override
    public List<PageData> FileList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("FileMapper.FileList",pd);
    }

    @Override
    public void delFilesByArticleId(PageData pd) throws Exception {
        List<PageData> listpd= this.FileList(pd);
        dao.delete("FileMapper.delFilesByArticleId",pd);

        System.out.println(listpd.toString());
        for (PageData pds:listpd) {
            File f=new File(pd.getString("path")+pds.getString("file_path"));
            f.delete();
        }
    }
    @Override 
    public int updateNullFile(PageData pd) throws Exception {
        int updateNum = (int)dao.update("FileMapper.updateNullFile", pd);
        return updateNum;
    }

    @Override
    public void delFileToArticleIdIsNull(PageData pd) throws Exception {
        List<PageData> listpd= (List<PageData>) dao.findForList("FileMapper.findFileToArticleIdIsNull",new PageData());
        dao.delete("FileMapper.delFileToArticleIdIsNull",new PageData());
        for (PageData pds:listpd) {
            File f=new File(pd.getString("path")+pds.getString("file_path"));
            //System.out.println("===+++++====删除文件"+pds.getString("ImgURL"));
            f.delete();
        }
    }
}