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
	
	private String authentime;//认证时间
	
	private Integer companyId;//公司ID
	
	private String bankcardTypeName;//银行卡名称
	
	private Integer page=0;
	
	private Integer pagesize=10;
	
	private Integer userNum;//扣款数
	
	private Integer chengNum;//成功数
	
	private Integer shiNum;//失败数
	
	private Integer data;//成功率
	
	private String statu_time;
	
	private String end_time;
	
	private String name;

	public String getAuthentime() {
		return authentime;
	}

	public void setAuthentime(String authentime) {
		this.authentime = authentime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatu_time() {
		return statu_time;
	}

	public void setStatu_time(String statu_time) {
		this.statu_time = statu_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Integer getChengNum() {
		return chengNum;
	}

	public void setChengNum(Integer chengNum) {
		this.chengNum = chengNum;
	}

	public Integer getShiNum() {
		return shiNum;
	}

	public void setShiNum(Integer shiNum) {
		this.shiNum = shiNum;
	}

	public Integer getData() {
		return data;
	}

	public void setData(Integer data) {
		this.data = data;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getBankcardTypeName() {
		return bankcardTypeName;
	}

	public void setBankcardTypeName(String bankcardTypeName) {
		this.bankcardTypeName = bankcardTypeName;
	}

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
	
//	private String bankcardtypename;
//	private String bankcardname;
//	private Integer bankcardtypeid;
//	private String tiedcardphone;
//	public Integer getBankcardtypeid() {
//		return bankcardtypeid;
//	}
//	public void setBankcardtypeid(Integer bankcardtypeid) {
//		this.bankcardtypeid = bankcardtypeid;
//	}
//	public String getBankcardname() {
//		return bankcardname;
//	}
//	public void setBankcardname(String bankcardname) {
//		this.bankcardname = bankcardname;
//	}
//	public String getTiedcardphone() {
//		return tiedcardphone;
//	}
//	public void setTiedcardphone(String tiedcardphone) {
//		this.tiedcardphone = tiedcardphone;
//	}
//	public String getBankcardtypename() {
//		return bankcardtypename;
//	}
//	public void setBankcardtypename(String bankcardtypename) {
//		this.bankcardtypename = bankcardtypename;
//	}
//
//	public String getAuthentime() {
//		return authentime;
//	}
//
//	public void setAuthentime(String authentime) {
//		this.authentime = authentime;
//	}
	
}
