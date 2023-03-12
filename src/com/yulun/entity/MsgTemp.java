package com.yulun.entity;

import com.fh.entity.system.Dictionaries;

import java.util.List;

public class MsgTemp {
    private String id;
    private String tempid;
    private String content;
    private String parentid;
    private String tempname;
    private Dictionaries dict;
    private List<MsgTemp> subDict;
    private String target;

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname;
    }

    public Dictionaries getDict() {
        return dict;
    }

    public void setDict(Dictionaries dict) {
        this.dict = dict;
    }

    public List<MsgTemp> getSubDict() {
        return subDict;
    }

    public void setSubDict(List<MsgTemp> subDict) {
        this.subDict = subDict;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
