package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;
import com.yulun.entity.MsgTemp;
import com.yulun.service.MsgTempManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("msgTempService")
public class MsgTempService implements MsgTempManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public void save(PageData pd) throws Exception {
        dao.save("MessageMapper.insertMagTemp",pd);
    }

    @Override
    public void delete(PageData pd) throws Exception {
        dao.delete("MessageMapper.deleteMagTemp",pd);
    }

    @Override
    public void edit(PageData pd) throws Exception {
        dao.update("MessageMapper.updateMagTemp",pd);
    }

    @Override
    public List<MsgTemp> listSubDictByParentId(String parentId) throws Exception {
        return (List<MsgTemp>) dao.findForList("MessageMapper.listSubDictByParentId",parentId);
    }

    @Override
    public List<MsgTemp> listAllDict(String parentId) throws Exception {
        List<MsgTemp> msgTemps = this.listSubDictByParentId(parentId);
        for (MsgTemp msgTemp : msgTemps) {
            msgTemp.setSubDict(this.listAllDict(msgTemp.getId()));
        }
        return msgTemps;
    }

    @Override
    public List<PageData> findMsgLogAlllistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.findMsgLogAlllistPage",page);
    }

    @Override
    public List<PageData> findMsgvipByIdlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.findMsgvipByIdlistPage",page);
    }

    @Override
    public List<PageData> findMsgcustByIdlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.findMsgcustByIdlistPage",page);
    }

    @Override
    public List<PageData> findMsgAlllistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.findMsgAlllistPage",page);
    }

    @Override
    public void insertMsgLog(PageData pd) throws Exception {
        dao.save("MessageMapper.insertMsgLog",pd);
    }

    @Override
    public void updateMsgLog(PageData pd) throws Exception {
        dao.update("MessageMapper.updateMsgLog",pd);
    }


    /*
     * 修改发送状态
     *  (non-Javadoc)
     * @see com.yulun.service.MsgTempManager#edit(com.fh.util.PageData)
     */
    @Override
    public void updateState(PageData pd) throws Exception {
        dao.update("MessageMapper.updateState",pd);
    }
    /*
     * 查询短信未发送的内容
     */
    @Override
    public List<PageData> findMsgByState(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.findMsgByState",pd);
    }

    @Override
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("MessageMapper.deleteAll",ArrayDATA_IDS);
    }

    @Override
    public List<PageData> getvipinfo(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.getvipinfo",pd);
    }

    @Override
    public List<PageData> getviptel(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.getviptel",pd);
    }

    @Override
    public List<PageData> getcustom(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.getcustom",pd);
    }

    @Override
    public List<PageData> getaddlist(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.getaddlist",pd);
    }

    @Override
    public List<PageData> findMsgLogAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("MessageMapper.findMsgLogAll",pd);
    }

    @Override
    public PageData gomsgdatils(PageData pd) throws Exception {
        return (PageData) dao.findForObject("MessageMapper.gomsgdatils",pd);
    }
}
