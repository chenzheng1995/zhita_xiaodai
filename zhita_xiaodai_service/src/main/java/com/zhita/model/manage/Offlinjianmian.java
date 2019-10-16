package com.zhita.model.manage;

import java.math.BigDecimal;


//线下减免调账表
public class Offlinjianmian {
	
	private Integer id;
	
	private Integer orderId;//订单ID
	
	private Integer fina_id;//操作人ID
	
	private BigDecimal offusermoney;//下线减免金额
	
	private String remarks;//备注
	
	private String sedn_time;
	
	private String account;
	
	private String orderNumber;
	
	private String name;
	
	private String phone;
	
	private String makeLoans;
	
	private BigDecimal offmoney;
	
	private BigDecimal orderMoney;
	
	private Integer thirdparty_id;
	
	private String serialnumber;

	public Integer getThirdparty_id() {
		return thirdparty_id;
	}

	public void setThirdparty_id(Integer thirdparty_id) {
		this.thirdparty_id = thirdparty_id;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getOffmoney() {
		return offmoney;
	}

	public void setOffmoney(BigDecimal offmoney) {
		this.offmoney = offmoney;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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

	public String getMakeLoans() {
		return makeLoans;
	}

	public void setMakeLoans(String makeLoans) {
		this.makeLoans = makeLoans;
	}

	public String getSedn_time() {
		return sedn_time;
	}

	public void setSedn_time(String sedn_time) {
		this.sedn_time = sedn_time;
	}

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
