package com.zhita.model.manage;

//联系客服表
public class ContactCustomerService {
    private Integer id;

    private Integer companyid;//公司id

    private String advertisingmap;//宣传图

    private String contactinformation;//联系方式

    private String qrcode;//二维码

    private String remarks;//备注

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

    public String getAdvertisingmap() {
        return advertisingmap;
    }

    public void setAdvertisingmap(String advertisingmap) {
        this.advertisingmap = advertisingmap == null ? null : advertisingmap.trim();
    }

    public String getContactinformation() {
        return contactinformation;
    }

    public void setContactinformation(String contactinformation) {
        this.contactinformation = contactinformation == null ? null : contactinformation.trim();
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

}