package com.yuzhe.service.imp;

import com.fh.dao.DAO;
import com.fh.util.PageData;
import com.yuzhe.service.ProcessManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Aliar
 * @Time: 2020-08-13 11:26
 **/
@Service("processService")
public class ProcessService implements ProcessManager {

    @Resource(name = "daoSupport")
    DAO dao;

    @Override
    public int deleteProcessById(PageData pd) throws Exception {
        return (int) dao.delete("ProcessMapper.deleteProcessById", pd);
    }

    @Override
    public List<PageData> findProcessById(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ProcessMapper.findProcess" , pd);
    }

    @Override
    public int saveProcessByUpdate(PageData pd_token) throws Exception {
        return (int) dao.save("ProcessMapper.saveProcessByUpdate" ,pd_token);
    }

    @Override
    public void saveProcessByGet(PageData pd) throws Exception {
        dao.save("ProcessMapper.saveProcessByGet",pd);
    }

    @Override
    public void saveProcessByReceive(PageData pd) throws Exception {
        dao.save("ProcessMapper.saveProcessByReceive",pd);
    }

    @Override
    public void saveProcessByAgain(PageData pd) throws Exception {
        dao.save("ProcessMapper.saveProcessByAgain", pd);
    }

    @Override
    public int batchSaveProcessByReceive(PageData pd) throws Exception {
        return (int) dao.save("ProcessMapper.batchSaveProcessByReceive",pd);
    }

}