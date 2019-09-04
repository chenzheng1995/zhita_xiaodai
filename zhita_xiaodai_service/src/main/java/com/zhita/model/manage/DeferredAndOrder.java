package com.zhita.model.manage;

import java.math.BigDecimal;

public class DeferredAndOrder {
	private Integer orderid;//订单id
	private String ordernumber;//订单编号
	private BigDecimal interestOnArrears;//延期费
	private String deferAfterReturntime;//延期时间
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
