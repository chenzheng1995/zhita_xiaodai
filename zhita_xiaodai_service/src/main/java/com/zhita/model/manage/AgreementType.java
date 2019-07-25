package com.zhita.model.manage;

//协议分类表
public class AgreementType {
    private Integer id;

    private Integer companyid;//公司id

    private String agreementtype;//协议分类

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

    public String getAgreementtype() {
        return agreementtype;
    }

    public void setAgreementtype(String agreementtype) {
        this.agreementtype = agreementtype == null ? null : agreementtype.trim();
    }
}