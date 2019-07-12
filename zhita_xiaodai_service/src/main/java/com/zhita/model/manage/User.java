package com.zhita.model.manage;

import java.math.BigDecimal;

//用户表
public class User {
    private Integer id;

    private Integer companyid;//公司id

    private Integer sourceid;//渠道id（关联渠道表）
    
    private String sourcename;//渠道名字

    private String name;//姓名

    private String phone;//手机号

    private String pwd;//密码

    private String transactionpwd;//交易密码

    private String registetime;//注册时间

    private String logintime;//登录时间

    private String loginstatus;//登录状态（1：登陆； 0：未登陆）

    private String registeclient;//注册客户端

    private String usemarket;//应用市场

    private Integer drainageofplatformid;//引流平台id
    
    private String userattestationstatus;//用户个人信息认证状态
    
    private String bankattestationstatus;//银行卡认证状态
    
    private String operaattestationstatus;//运营商认证状态
    
    private String ifblacklist;//是否是黑名单（1：是；0：不是）
    
    private Integer riskControlPoints;//分控分数
    
    private BigDecimal canBorrowLines;//可借额度

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
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTransactionpwd() {
		return transactionpwd;
	}

	public void setTransactionpwd(String transactionpwd) {
		this.transactionpwd = transactionpwd;
	}

	public String getRegistetime() {
		return registetime;
	}

	public void setRegistetime(String registetime) {
		this.registetime = registetime;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getLoginstatus() {
		return loginstatus;
	}

	public void setLoginstatus(String loginstatus) {
		this.loginstatus = loginstatus;
	}

	public String getRegisteclient() {
		return registeclient;
	}

	public void setRegisteclient(String registeclient) {
		this.registeclient = registeclient;
	}

	public String getUsemarket() {
		return usemarket;
	}

	public void setUsemarket(String usemarket) {
		this.usemarket = usemarket;
	}

	public Integer getDrainageofplatformid() {
		return drainageofplatformid;
	}

	public void setDrainageofplatformid(Integer drainageofplatformid) {
		this.drainageofplatformid = drainageofplatformid;
	}

	public String getUserattestationstatus() {
		return userattestationstatus;
	}

	public void setUserattestationstatus(String userattestationstatus) {
		this.userattestationstatus = userattestationstatus;
	}

	public String getBankattestationstatus() {
		return bankattestationstatus;
	}

	public void setBankattestationstatus(String bankattestationstatus) {
		this.bankattestationstatus = bankattestationstatus;
	}

	public String getOperaattestationstatus() {
		return operaattestationstatus;
	}

	public void setOperaattestationstatus(String operaattestationstatus) {
		this.operaattestationstatus = operaattestationstatus;
	}

	public String getIfblacklist() {
		return ifblacklist;
	}

	public void setIfblacklist(String ifblacklist) {
		this.ifblacklist = ifblacklist;
	}
	
	public Integer getRiskControlPoints() {
		return riskControlPoints;
	}

	public void setRiskControlPoints(Integer riskControlPoints) {
		this.riskControlPoints = riskControlPoints;
	}

	public String getSourcename() {
		return sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}
	
	public BigDecimal getCanBorrowLines() {
		return canBorrowLines;
	}

	public void setCanBorrowLines(BigDecimal canBorrowLines) {
		this.canBorrowLines = canBorrowLines;
	}

}