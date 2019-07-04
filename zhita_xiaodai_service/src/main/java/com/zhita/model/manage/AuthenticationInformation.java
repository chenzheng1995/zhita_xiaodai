package com.zhita.model.manage;

//认证信息表
public class AuthenticationInformation {
    private Integer id;

    private Integer companyid;//公司id

    private String authenticationname;//认证名称
    
    private String icon;//图标

    private String ifauthentication;//是否需认证（1：需认证；2：免认证）

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

    public String getAuthenticationname() {
        return authenticationname;
    }

    public void setAuthenticationname(String authenticationname) {
        this.authenticationname = authenticationname == null ? null : authenticationname.trim();
    }
    

    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIfauthentication() {
        return ifauthentication;
    }

    public void setIfauthentication(String ifauthentication) {
        this.ifauthentication = ifauthentication == null ? null : ifauthentication.trim();
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }
}