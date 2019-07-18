package com.zhita.model.manage;


//银行卡表
public class Bankcard {
	
	private Integer id;
	
	private Integer userId;//用户ID
	
	private String attestationStatus;//认证状态（0未认证，1已认证，2认证中）
	
	private Integer bankcardTypeId;//储蓄卡类型id
	
	private String bankcardName;//储蓄卡账号
	
	private String tiedCardPhone;//绑卡手机号
	
	private String deleted;//假删除
	
	private String IDcardnumber;//身份证号
	
	private String cstmrnm;//持卡人姓名

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAttestationStatus() {
		return attestationStatus;
	}

	public void setAttestationStatus(String attestationStatus) {
		this.attestationStatus = attestationStatus;
	}

	public Integer getBankcardTypeId() {
		return bankcardTypeId;
	}

	public void setBankcardTypeId(Integer bankcardTypeId) {
		this.bankcardTypeId = bankcardTypeId;
	}

	public String getBankcardName() {
		return bankcardName;
	}

	public void setBankcardName(String bankcardName) {
		this.bankcardName = bankcardName;
	}

	public String getTiedCardPhone() {
		return tiedCardPhone;
	}

	public void setTiedCardPhone(String tiedCardPhone) {
		this.tiedCardPhone = tiedCardPhone;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getIDcardnumber() {
		return IDcardnumber;
	}

	public void setIDcardnumber(String iDcardnumber) {
		IDcardnumber = iDcardnumber;
	}

	public String getCstmrnm() {
		return cstmrnm;
	}

	public void setCstmrnm(String cstmrnm) {
		this.cstmrnm = cstmrnm;
	}
	
	
	
	private Integer bankcardtypeid;
	private String bankcardname;
	private String tiedcardphone;
	private String bankcardtypename;
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
	public String getBankcardtypename() {
		return bankcardtypename;
	}
	public void setBankcardtypename(String bankcardtypename) {
		this.bankcardtypename = bankcardtypename;
	}
	
}
