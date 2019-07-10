package com.zhita.model.manage;

import java.math.BigDecimal;


//银行扣记录表
public class Bankdeduction {
	
	private Integer id;//id
	
	private Integer sys_userId;//操作人ID
	
	private Integer deductionproportion;//扣款比例
	
	private BigDecimal thirdpartypayment;//第三方支付费
	
	private Integer userId;//用户ID
	
	private Integer OrderId;//订单ID
	
	private String deductionstatus;//扣款状态
	
	private BigDecimal deduction_money;//扣款金额
	
	private String orderNumber;//订单编号
	
	private String name;//名称
	
	private String phone;//手机号
	
	private String bankcardName;//银行卡号
	
	private String deferAfterReturntime;//延期后应还时间
	
	private String orderCreateTime;//实借时间
	
	private String overdueGrade;//逾期等级
	
	private String statu_time;//延期后范围开始时间
	
	private String end_time;//延期后范围结束时间
	
	private String statu_timeOrder;//实借时间范围开始时间
	
	private String end_timeOrder;//实借时间范围结束时间
	
	private Integer page=0;
	
	private Integer pagesize=10;
	
	private Integer companyId;//公司ID

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSys_userId() {
		return sys_userId;
	}

	public void setSys_userId(Integer sys_userId) {
		this.sys_userId = sys_userId;
	}

	public Integer getDeductionproportion() {
		return deductionproportion;
	}

	public void setDeductionproportion(Integer deductionproportion) {
		this.deductionproportion = deductionproportion;
	}

	public BigDecimal getThirdpartypayment() {
		return thirdpartypayment;
	}

	public void setThirdpartypayment(BigDecimal thirdpartypayment) {
		this.thirdpartypayment = thirdpartypayment;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrderId() {
		return OrderId;
	}

	public void setOrderId(Integer orderId) {
		OrderId = orderId;
	}

	public String getDeductionstatus() {
		return deductionstatus;
	}

	public void setDeductionstatus(String deductionstatus) {
		this.deductionstatus = deductionstatus;
	}

	public BigDecimal getDeduction_money() {
		return deduction_money;
	}

	public void setDeduction_money(BigDecimal deduction_money) {
		this.deduction_money = deduction_money;
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

	public String getBankcardName() {
		return bankcardName;
	}

	public void setBankcardName(String bankcardName) {
		this.bankcardName = bankcardName;
	}

	public String getDeferAfterReturntime() {
		return deferAfterReturntime;
	}

	public void setDeferAfterReturntime(String deferAfterReturntime) {
		this.deferAfterReturntime = deferAfterReturntime;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOverdueGrade() {
		return overdueGrade;
	}

	public void setOverdueGrade(String overdueGrade) {
		this.overdueGrade = overdueGrade;
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

	public String getStatu_timeOrder() {
		return statu_timeOrder;
	}

	public void setStatu_timeOrder(String statu_timeOrder) {
		this.statu_timeOrder = statu_timeOrder;
	}

	public String getEnd_timeOrder() {
		return end_timeOrder;
	}

	public void setEnd_timeOrder(String end_timeOrder) {
		this.end_timeOrder = end_timeOrder;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
}	
