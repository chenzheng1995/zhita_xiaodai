package com.zhita.model.manage;

public class Applynumber {
    private Integer id;

    private Integer userid;

    private String applynumber;

    private String applytime;

    private String state;

    public Applynumber(Integer id, Integer userid, String applynumber, String applytime, String state) {
        this.id = id;
        this.userid = userid;
        this.applynumber = applynumber;
        this.applytime = applytime;
        this.state = state;
    }

    public Applynumber() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getApplynumber() {
        return applynumber;
    }

    public void setApplynumber(String applynumber) {
        this.applynumber = applynumber == null ? null : applynumber.trim();
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime == null ? null : applytime.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}