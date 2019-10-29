package com.zhita.model.manage;

//是否让用户直接进入分控表
public class Iffengkong {
    private Integer id;

    private Integer companyid;//公司id

    private String iffengkong;//是否让用户直接进入分控（1：是；2；否）

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