package com.zhita.model.manage;

public class Ifblacklist {
    private Integer id;

    private Integer companyid;

    private String ifblacklist;

    public Ifblacklist(Integer id, Integer companyid, String ifblacklist) {
        this.id = id;
        this.companyid = companyid;
        this.ifblacklist = ifblacklist;
    }

    public Ifblacklist() {
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

    public String getIfblacklist() {
        return ifblacklist;
    }

    public void setIfblacklist(String ifblacklist) {
        this.ifblacklist = ifblacklist == null ? null : ifblacklist.trim();
    }
}