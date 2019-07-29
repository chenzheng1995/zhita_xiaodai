package com.zhita.model.manage;

import java.math.BigDecimal;


//延期表
public class Deferred {
	
	
	private Integer id;
	
	private Integer orderid;
	
	private BigDecimal interestOnArrears;
	
	private String deferBeforeReturntime;
	
	private Integer postponeDate;
	
	private String deferAfterReturntime;
	
	private String deleted;
	
	private Integer onceDeferredDay;
	
	private String deferredTime;
	
	private String orderNumber;

	public String getDeferredTime() {
		return deferredTime;
	}

	public void setDeferredTime(String deferredTime) {
		this.deferredTime = deferredTime;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getInterestOnArrears() {
		return interestOnArrears;
	}

	public void setInterestOnArrears(BigDecimal interestOnArrears) {
		this.interestOnArrears = interestOnArrears;
	}

	public String getDeferBeforeReturntime() {
		return deferBeforeReturntime;
	}

	public void setDeferBeforeReturntime(String deferBeforeReturntime) {
		this.deferBeforeReturntime = deferBeforeReturntime;
	}

	public Integer getPostponeDate() {
		return postponeDate;
	}

	public void setPostponeDate(Integer postponeDate) {
		this.postponeDate = postponeDate;
	}

	public String getDeferAfterReturntime() {
		return deferAfterReturntime;
	}

	public void setDeferAfterReturntime(String deferAfterReturntime) {
		this.deferAfterReturntime = deferAfterReturntime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Integer getOnceDeferredDay() {
		return onceDeferredDay;
	}

	public void setOnceDeferredDay(Integer onceDeferredDay) {
		this.onceDeferredDay = onceDeferredDay;
	}
	
	
}
