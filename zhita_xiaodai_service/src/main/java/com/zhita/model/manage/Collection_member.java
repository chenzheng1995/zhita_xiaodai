package com.zhita.model.manage;

//催收员表
public class Collection_member {
	
	private Integer collectionMemberId;//催收员ID
	
	private Integer companyId;//公司ID
	
	private String reallyName;//真是姓名
	
	private String userName;//用户名
	
	private String jobNumber;//工号
	
	private Integer collectionSum;//催收总数
	
	private Integer waitCollection;//待催收数
	
	private Integer collectionSucceed;//催收成功数
	
	private Integer yesterdayCollection;//昨日催收数
	
	private String status;//'状态（1：开启；2：关闭）',

	public Integer getCollectionMemberId() {
		return collectionMemberId;
	}

	public void setCollectionMemberId(Integer collectionMemberId) {
		this.collectionMemberId = collectionMemberId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getReallyName() {
		return reallyName;
	}

	public void setReallyName(String reallyName) {
		this.reallyName = reallyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Integer getCollectionSum() {
		return collectionSum;
	}

	public void setCollectionSum(Integer collectionSum) {
		this.collectionSum = collectionSum;
	}

	public Integer getWaitCollection() {
		return waitCollection;
	}

	public void setWaitCollection(Integer waitCollection) {
		this.waitCollection = waitCollection;
	}

	public Integer getCollectionSucceed() {
		return collectionSucceed;
	}

	public void setCollectionSucceed(Integer collectionSucceed) {
		this.collectionSucceed = collectionSucceed;
	}

	public Integer getYesterdayCollection() {
		return yesterdayCollection;
	}

	public void setYesterdayCollection(Integer yesterdayCollection) {
		this.yesterdayCollection = yesterdayCollection;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
