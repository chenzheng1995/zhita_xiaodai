package com.zhita.model.manage;

//关于我们
public class Aboutus {
    private Integer id;

    private Integer companyid;//公司id

    private String logo;//app的logo

    private String productname;//公司或者产品名字

    private String appversion;//APP版本
    
    private String ifcoerceupdate;//是否强制更新（1：是2：否）

    private String androidlink;//android下载链接
    
    private String ioslink;//ios下载链接



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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion == null ? null : appversion.trim();
    }
 
    public String getIfcoerceupdate() {
        return ifcoerceupdate;
    }

    public void setIfcoerceupdate(String ifcoerceupdate) {
        this.ifcoerceupdate = ifcoerceupdate == null ? null : ifcoerceupdate.trim();
    }
    
    public String getAndroidlink() {
        return androidlink;
    }

    public void setAndroidlink(String androidlink) {
        this.androidlink = androidlink == null ? null : androidlink.trim();
    }
    
    
    public String getIoslink() {
        return ioslink;
    }

    public void setIoslink(String ioslink) {
        this.ioslink = ioslink == null ? null : ioslink.trim();
    }



}