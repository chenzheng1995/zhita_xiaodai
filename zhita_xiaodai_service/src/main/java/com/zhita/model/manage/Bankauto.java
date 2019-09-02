package com.zhita.model.manage;

//银行卡自动扣款设置表
public class Bankauto {
    private Integer id;

    private Integer companyid;//公司id

    private Integer overdueday;//逾期天数

    private String timing;//时间点

    private String rate;//比率

    private String deleted;//假删除（1：删除；0：没删除）

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

    public Integer getOverdueday() {
        return overdueday;
    }

    public void setOverdueday(Integer overdueday) {
        this.overdueday = overdueday;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing == null ? null : timing.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }
}