package com.yulun.entity;

import java.util.Date;

public class worktask {
    private Long id;
    private String telnum;
    private Date teltime;
    private String message;
    private String type;
    private String result;
    private String userid;
    private String czman;
    private Date czdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public Date getTeltime() {
        return teltime;
    }

    public void setTeltime(Date teltime) {
        this.teltime = teltime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCzman() {
        return czman;
    }

    public void setCzman(String czman) {
        this.czman = czman;
    }

    public Date getCzdate() {
        return czdate;
    }

    public void setCzdate(Date czdate) {
        this.czdate = czdate;
    }
}
