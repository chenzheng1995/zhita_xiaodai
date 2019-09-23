package com.zhita.model.manage;

//认证信息二级属性的关联表
public class AuthenSecondattributes {
    private Integer id;

    private Integer authenid;//认证id

    private String secondattributes;//二级属性

    private String status;//状态（1：开启；2：关闭）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthenid() {
        return authenid;
    }

    public void setAuthenid(Integer authenid) {
        this.authenid = authenid;
    }

    public String getSecondattributes() {
        return secondattributes;
    }

    public void setSecondattributes(String secondattributes) {
        this.secondattributes = secondattributes == null ? null : secondattributes.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}