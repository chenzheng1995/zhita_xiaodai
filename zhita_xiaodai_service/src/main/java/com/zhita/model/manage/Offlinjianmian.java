package com.zhita.model.manage;

import java.math.BigDecimal;


//线下减免调账表
public class Offlinjianmian {
	
	private Integer id;
	
	private Integer orderId;//订单ID
	
	private Integer fina_id;//操作人ID
	
	private BigDecimal offusermoney;//下线减免金额
	
	private String remarks;//备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getFina_id() {
		return fina_id;
	}

	public void setFina_id(Integer fina_id) {
		this.fina_id = fina_id;
	}

	public BigDecimal getOffusermoney() {
		return offusermoney;
	}

	public void setOffusermoney(BigDecimal offusermoney) {
		this.offusermoney = offusermoney;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
