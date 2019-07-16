package com.zhita.model.manage;

//银行卡表
public class Bankcard {
	private Integer id;
	private Integer userid;
	private Integer bankcardtypeid;
	private String bankcardname;
	private String tiedcardphone;
	private String deleted;
	private String bankcardtypename;
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
	public Integer getBankcardtypeid() {
		return bankcardtypeid;
	}
	public void setBankcardtypeid(Integer bankcardtypeid) {
		this.bankcardtypeid = bankcardtypeid;
	}
	public String getBankcardname() {
		return bankcardname;
	}
	public void setBankcardname(String bankcardname) {
		this.bankcardname = bankcardname;
	}
	public String getTiedcardphone() {
		return tiedcardphone;
	}
	public void setTiedcardphone(String tiedcardphone) {
		this.tiedcardphone = tiedcardphone;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getBankcardtypename() {
		return bankcardtypename;
	}
	public void setBankcardtypename(String bankcardtypename) {
		this.bankcardtypename = bankcardtypename;
	}
	
}
