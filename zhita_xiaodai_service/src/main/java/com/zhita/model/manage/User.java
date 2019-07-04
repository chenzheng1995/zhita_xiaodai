package com.zhita.model.manage;

public class User {
    private Integer id;

    private Integer companyid;

    private Integer sourceid;

    private String name;

    private String phone;

    private String pwd;

    private String transactionpwd;

    private String registetime;

    private String logintime;

    private String loginstatus;

    private String registeclient;

    private String usemarket;

    private Integer drainageofplatformid;

    public User(Integer id, Integer companyid, Integer sourceid, String name, String phone, String pwd, String transactionpwd, String registetime, String logintime, String loginstatus, String registeclient, String usemarket, Integer drainageofplatformid) {
        this.id = id;
        this.companyid = companyid;
        this.sourceid = sourceid;
        this.name = name;
        this.phone = phone;
        this.pwd = pwd;
        this.transactionpwd = transactionpwd;
        this.registetime = registetime;
        this.logintime = logintime;
        this.loginstatus = loginstatus;
        this.registeclient = registeclient;
        this.usemarket = usemarket;
        this.drainageofplatformid = drainageofplatformid;
    }

    public User() {
        super();
    }

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

    public Integer getSourceid() {
        return sourceid;
    }

    public void setSourceid(Integer sourceid) {
        this.sourceid = sourceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getTransactionpwd() {
        return transactionpwd;
    }

    public void setTransactionpwd(String transactionpwd) {
        this.transactionpwd = transactionpwd == null ? null : transactionpwd.trim();
    }

    public String getRegistetime() {
        return registetime;
    }

    public void setRegistetime(String registetime) {
        this.registetime = registetime == null ? null : registetime.trim();
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime == null ? null : logintime.trim();
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus == null ? null : loginstatus.trim();
    }

    public String getRegisteclient() {
        return registeclient;
    }

    public void setRegisteclient(String registeclient) {
        this.registeclient = registeclient == null ? null : registeclient.trim();
    }

    public String getUsemarket() {
        return usemarket;
    }

    public void setUsemarket(String usemarket) {
        this.usemarket = usemarket == null ? null : usemarket.trim();
    }

    public Integer getDrainageofplatformid() {
        return drainageofplatformid;
    }

    public void setDrainageofplatformid(Integer drainageofplatformid) {
        this.drainageofplatformid = drainageofplatformid;
    }

	public User findphone(String newPhone, String companyId2) {
		// TODO Auto-generated method stub
		return null;
	}
}