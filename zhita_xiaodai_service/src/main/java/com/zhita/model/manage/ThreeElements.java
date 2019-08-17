package com.zhita.model.manage;

public class ThreeElements {
    private Integer id;

    private Integer userid;

    private String code;

    private Integer certificationNumber;

    private String merchantCode;

    public ThreeElements(Integer id, Integer userid, String code, Integer certificationNumber, String merchantCode) {
        this.id = id;
        this.userid = userid;
        this.code = code;
        this.certificationNumber = certificationNumber;
        this.merchantCode = merchantCode;
    }

    public ThreeElements() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getCertificationNumber() {
        return certificationNumber;
    }

    public void setCertificationNumber(Integer certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }
}