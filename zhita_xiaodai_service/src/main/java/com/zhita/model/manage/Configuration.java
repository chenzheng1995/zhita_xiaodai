package com.zhita.model.manage;

public class Configuration {
    private Integer id;

    private Integer userid;

    private String wifiname;

    private String wifiip;

    private String wifimac;

    private String phonemarket;

    private String phonemodel;

    private String phoneres;

    private String lac;

    private String loc;

    private String wrapname;
    
    private String uuid;

    public Configuration(Integer id, Integer userid, String wifiname, String wifiip, String wifimac, String phonemarket, String phonemodel, String phoneres, String lac, String loc, String wrapname,String uuid) {
        this.id = id;
        this.userid = userid;
        this.wifiname = wifiname;
        this.wifiip = wifiip;
        this.wifimac = wifimac;
        this.phonemarket = phonemarket;
        this.phonemodel = phonemodel;
        this.phoneres = phoneres;
        this.lac = lac;
        this.loc = loc;
        this.wrapname = wrapname;
        this.uuid = uuid;
    }

    public Configuration() {
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

    public String getWifiname() {
        return wifiname;
    }

    public void setWifiname(String wifiname) {
        this.wifiname = wifiname == null ? null : wifiname.trim();
    }

    public String getWifiip() {
        return wifiip;
    }

    public void setWifiip(String wifiip) {
        this.wifiip = wifiip == null ? null : wifiip.trim();
    }

    public String getWifimac() {
        return wifimac;
    }

    public void setWifimac(String wifimac) {
        this.wifimac = wifimac == null ? null : wifimac.trim();
    }

    public String getPhonemarket() {
        return phonemarket;
    }

    public void setPhonemarket(String phonemarket) {
        this.phonemarket = phonemarket == null ? null : phonemarket.trim();
    }

    public String getPhonemodel() {
        return phonemodel;
    }

    public void setPhonemodel(String phonemodel) {
        this.phonemodel = phonemodel == null ? null : phonemodel.trim();
    }

    public String getPhoneres() {
        return phoneres;
    }

    public void setPhoneres(String phoneres) {
        this.phoneres = phoneres == null ? null : phoneres.trim();
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac == null ? null : lac.trim();
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc == null ? null : loc.trim();
    }

    public String getWrapname() {
        return wrapname;
    }

    public void setWrapname(String wrapname) {
        this.wrapname = wrapname == null ? null : wrapname.trim();
    }
    
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }
}