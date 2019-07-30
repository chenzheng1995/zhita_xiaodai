package com.zhita.model.manage;

import java.math.BigDecimal;

public class DeferredAndOrder {
	private Integer orderid;
	private String ordernumber;
	private BigDecimal interestOnArrears;
	private String deferAfterReturntime;
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public BigDecimal getInterestOnArrears() {
		return interestOnArrears;
	}
	public void setInterestOnArrears(BigDecimal interestOnArrears) {
		this.interestOnArrears = interestOnArrears;
	}
	public String getDeferAfterReturntime() {
		return deferAfterReturntime;
	}
	public void setDeferAfterReturntime(String deferAfterReturntime) {
		this.deferAfterReturntime = deferAfterReturntime;
	}
	
	
}
