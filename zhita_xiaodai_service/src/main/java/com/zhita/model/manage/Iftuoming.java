package com.zhita.model.manage;

public class Iftuoming {
    private Integer id;

    private Integer companyid;

    private String iftuoming;//是否需要脱名（1：是；2；否）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getIftuoming() {
        return iftuoming;
    }

    public void setIftuoming(String iftuoming) {
        this.iftuoming = iftuoming == null ? null : iftuoming.trim();
    }
}