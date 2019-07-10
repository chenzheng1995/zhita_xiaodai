package com.zhita.model.manage;

import java.math.BigDecimal;
import java.util.List;


//催收表
public class Collection {
	
	private Integer collectionId;//催收Id
	
	private String orderNumber;
	
	private Integer collectionMemberId;//催收员ID
	
	private Integer orderId;//订单ID
	
	private String collectionStatus;//催收状态
	
	private String collectionTime;//催收时间
	
	private String deleted;//假删除
	
	private String name;//姓名
	
	private String phone;//手机号
	
	private BigDecimal promise_money;//承诺金额
	
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
	
	private Integer orderNum;//订单总数
	
	private Integer dialNum;//未拨打数
	    
	private Integer Notconnected;//未接通数

	private Integer connected;//接通数
	    
	private Integer Sameday;//当天未还款
	    
	private Integer Paymentmade;//当天已还款
	
	private Integer PaymentmadeData;//当天还款率
	
	private Integer CollCuiNum;//催收次数
	
	private Integer CollSum;//分配数
	
	private Integer Chenggnum;//承诺还款
	
	private Integer collNumdata;//催回率
	
	private String realtime;
	
	private String collection_time;
	
	private String orderCreateTime;
	
	private String describe;
	
	private String collectiondate;
	
	
	public String getCollectiondate() {
		return collectiondate;
	}

	public void setCollectiondate(String collectiondate) {
		this.collectiondate = collectiondate;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getCollection_time() {
		return collection_time;
	}

	public void setCollection_time(String collection_time) {
		this.collection_time = collection_time;
	}

	public String getRealtime() {
		return realtime;
	}

	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}

	public Integer getCollCuiNum() {
		return CollCuiNum;
	}

	public void setCollCuiNum(Integer collCuiNum) {
		CollCuiNum = collCuiNum;
	}

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

	public Integer getDialNum() {
		return dialNum;
	}

	public void setDialNum(Integer dialNum) {
		this.dialNum = dialNum;
	}

	public Integer getNotconnected() {
		return Notconnected;
	}

	public void setNotconnected(Integer notconnected) {
		Notconnected = notconnected;
	}

	public Integer getConnected() {
		return connected;
	}

	public void setConnected(Integer connected) {
		this.connected = connected;
	}

	public Integer getSameday() {
		return Sameday;
	}

	public void setSameday(Integer sameday) {
		Sameday = sameday;
	}

	public Integer getPaymentmade() {
		return Paymentmade;
	}

	public void setPaymentmade(Integer paymentmade) {
		Paymentmade = paymentmade;
	}

	public Integer getPaymentmadeData() {
		return PaymentmadeData;
	}

	public void setPaymentmadeData(Integer paymentmadeData) {
		PaymentmadeData = paymentmadeData;
	}

	public BigDecimal getPromise_money() {
		return promise_money;
	}

	public void setPromise_money(BigDecimal promise_money) {
		this.promise_money = promise_money;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getCollSum() {
		return CollSum;
	}

	public void setCollSum(Integer collSum) {
		CollSum = collSum;
	}

	public Integer getChenggnum() {
		return Chenggnum;
	}

	public void setChenggnum(Integer chenggnum) {
		Chenggnum = chenggnum;
	}

	public Integer getCollNumdata() {
		return collNumdata;
	}

	public void setCollNumdata(Integer collNumdata) {
		this.collNumdata = collNumdata;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	
	
}
