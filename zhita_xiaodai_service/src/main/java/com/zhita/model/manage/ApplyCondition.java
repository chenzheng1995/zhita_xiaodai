package com.zhita.model.manage;

//申请条件配置表
public class ApplyCondition {
    private Integer id;

    private Integer companyid;//公司id

    private Integer minimumage;//身份证判定最小年龄

    private Integer maximumage;//身份证判定最大年龄

    private String refuseapplyprovince;//拒绝申请的省份

    private String allowsigningidcard;//允许签约的银行卡类型

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

    public Integer getMinimumage() {
        return minimumage;
    }

    public void setMinimumage(Integer minimumage) {
        this.minimumage = minimumage;
    }

    public Integer getMaximumage() {
        return maximumage;
    }

    public void setMaximumage(Integer maximumage) {
        this.maximumage = maximumage;
    }

    public String getRefuseapplyprovince() {
        return refuseapplyprovince;
    }

    public void setRefuseapplyprovince(String refuseapplyprovince) {
        this.refuseapplyprovince = refuseapplyprovince == null ? null : refuseapplyprovince.trim();
    }

    public String getAllowsigningidcard() {
        return allowsigningidcard;
    }

    public void setAllowsigningidcard(String allowsigningidcard) {
        this.allowsigningidcard = allowsigningidcard == null ? null : allowsigningidcard.trim();
    }
}