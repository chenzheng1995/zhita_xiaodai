package com.zhita.model.manage;

public class BankcardType {
    private Integer id;

    private Integer companyid;

    private String bankcardtypename;

    private String deleted;

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

    public String getBankcardtypename() {
        return bankcardtypename;
    }

    public void setBankcardtypename(String bankcardtypename) {
        this.bankcardtypename = bankcardtypename == null ? null : bankcardtypename.trim();
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }
}