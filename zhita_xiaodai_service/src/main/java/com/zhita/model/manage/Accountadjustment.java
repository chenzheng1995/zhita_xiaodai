package com.zhita.model.manage;

import java.math.BigDecimal;

//调账表(线上调账表)
public class Accountadjustment {
	
	private Integer id;
	
	private Integer orderId;//订单ID
	
	private BigDecimal totalamount;//减免后应还总金额(线上调账)
	
	private BigDecimal amountmoney;//减免金额
	
	private String remarks;//备注
	
	private String accounttime;//减免后应还时间
	
	private Integer beoverdue;//减免后应还延期天数
	
	private String orderNumber;//订单号
	
	private String name;//姓名
	
	private String phone;//手机号
	
	private Integer rename_id;//还款渠道
	
	private Integer overdueNumberOfDays;
	
	private String statu;
	
	private String idcardFaceAuthentication;
	
	private Integer thirdparty_id;
	
	private String amou_time;
	
	private Integer sys_uerId;
	
	private String thname;
	
	public String getThname() {
		return thname;
	}

	public void setThname(String thname) {
		this.thname = thname;
	}

	public String getAmou_time() {
		return amou_time;
	}

	public Integer getOverdueNumberOfDays() {
		return overdueNumberOfDays;
	}

	public void setOverdueNumberOfDays(Integer overdueNumberOfDays) {
		this.overdueNumberOfDays = overdueNumberOfDays;
	}

	public void setAmou_time(String amou_time) {
		this.amou_time = amou_time;
	}

	public Integer getSys_uerId() {
		return sys_uerId;
	}

	public void setSys_uerId(Integer sys_uerId) {
		this.sys_uerId = sys_uerId;
	}

	public Integer getThirdparty_id() {
		return thirdparty_id;
	}

	public void setThirdparty_id(Integer thirdparty_id) {
		this.thirdparty_id = thirdparty_id;
	}

	public String getIdcardFaceAuthentication() {
		return idcardFaceAuthentication;
	}

	public void setIdcardFaceAuthentication(String idcardFaceAuthentication) {
		this.idcardFaceAuthentication = idcardFaceAuthentication;
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

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public BigDecimal getAmountmoney() {
		return amountmoney;
	}

	public void setAmountmoney(BigDecimal amountmoney) {
		this.amountmoney = amountmoney;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAccounttime() {
		return accounttime;
	}

	public void setAccounttime(String accounttime) {
		this.accounttime = accounttime;
	}

	public Integer getBeoverdue() {
		return beoverdue;
	}

	public void setBeoverdue(Integer beoverdue) {
		this.beoverdue = beoverdue;
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


	public Integer getRename_id() {
		return rename_id;
	}

	public void setRename_id(Integer rename_id) {
		this.rename_id = rename_id;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}
	
}
