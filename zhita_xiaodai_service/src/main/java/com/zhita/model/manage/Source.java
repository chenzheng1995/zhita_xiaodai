package com.zhita.model.manage;

//渠道表
public class Source {
    private Integer id;

    private Integer companyid;//公司id

    private String sourcename;//渠道名称

    private String account;//账号

    private String pwd;//密码

    private String link;//链接

    private String status;//状态(1开启，2关闭)

    private String discount;//折扣率

    private Integer templateid;//模板id
    
    private String name;//模板名字
    
    private Integer managecontrolid;//风控id
    
    private String rmmodlename;//风控名字

    private String deleted;//假删除（删除：1，没删除：0）

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

	public String getSourcename() {
		return sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Integer getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Integer templateid) {
		this.templateid = templateid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getManagecontrolid() {
		return managecontrolid;
	}

	public void setManagecontrolid(Integer managecontrolid) {
		this.managecontrolid = managecontrolid;
	}

	public String getRmmodlename() {
		return rmmodlename;
	}

	public void setRmmodlename(String rmmodlename) {
		this.rmmodlename = rmmodlename;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}