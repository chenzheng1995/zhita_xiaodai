package com.zhita.model.manage;

import java.util.List;


//催收表
public class Collection {
	
	private Integer collectionId;//催收Id
	
	private Integer collectionMemberId;//催收员ID
	
	private Integer orderId;//订单ID
	
	private String collectionStatus;//催收状态
	
	private String collectionTime;//催收时间
	
	private String deleted;//假删除
	
	private String name;//姓名
	
	private String phone;//手机号
	
	private Integer page=0;

	private Integer pagesize=10;
	
	private List<Integer> ids;
	
	private String start_time;//开始时间
	
	private String end_time;//结束时间
	
	private String borrowMoneyType;//'借款类型（立即贷和分期贷）'
	
	private Integer collection_count;
	
	private String reallyName;
	
	private Integer collCount;
	
	private Integer companyId;//公司ID
	
	private Integer id;//订单ID
	
	
	
	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public Integer getCollectionMemberId() {
		return collectionMemberId;
	}

	public void setCollectionMemberId(Integer collectionMemberId) {
		this.collectionMemberId = collectionMemberId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(String collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
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

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getBorrowMoneyType() {
		return borrowMoneyType;
	}

	public void setBorrowMoneyType(String borrowMoneyType) {
		this.borrowMoneyType = borrowMoneyType;
	}

	public Integer getCollection_count() {
		return collection_count;
	}

	public void setCollection_count(Integer collection_count) {
		this.collection_count = collection_count;
	}

	public String getReallyName() {
		return reallyName;
	}

	public void setReallyName(String reallyName) {
		this.reallyName = reallyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCollCount() {
		return collCount;
	}

	public void setCollCount(Integer collCount) {
		this.collCount = collCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
