package com.zhita.model.manage;

public class Iffengkong {
    private Integer id;

    private Integer companyid;

    private String iffengkong;

    public Iffengkong(Integer id, Integer companyid, String iffengkong) {
        this.id = id;
        this.companyid = companyid;
        this.iffengkong = iffengkong;
    }

    public Iffengkong() {
        super();
    }

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

    public String getIffengkong() {
        return iffengkong;
    }

    public void setIffengkong(String iffengkong) {
        this.iffengkong = iffengkong == null ? null : iffengkong.trim();
    }
}