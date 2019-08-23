package com.zhita.model.manage;

import java.math.BigDecimal;

//支付表
public class Payment_record {
	
	private Integer id;
	
	private Integer orderId;//订单ID
	
	private String remittanceTime;//打款时间
	
	private String professionalWork;//业务

	private String status;//状态
	
	private String pipelinenumber;//流水号
	
	private String deleted;//假删除
	
	private String name;//姓名
	
	private String phone;//手机号
	
	private String start_time;//开始时间
	
	private Integer thirdparty_id;
	
	private String end_time;//结束时间
	
	private String loanSource;//支付渠道 (支付方式 微信  支付宝  银行卡)
	
	private Integer page=1;
	
	private Integer pagesize=10;
	
	private Integer companyId;//公司ID
	
	private String sourceName;//放宽渠道名称
	
	private BigDecimal paymentmoney;//放款金额
	
	private String orderNumber;//订单编号
	
	private String repaymentDate;
	
	private BigDecimal repaymentMoney;
	
	private String paymentbtiao;
	
	private String thname;
	
	private String cardnumber;//银行卡号
	
	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getThname() {
		return thname;
	}

	public void setThname(String thname) {
		this.thname = thname;
	}

	public String getPaymentbtiao() {
		return paymentbtiao;
	}

	public void setPaymentbtiao(String paymentbtiao) {
		this.paymentbtiao = paymentbtiao;
	}

	public String getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public BigDecimal getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(BigDecimal repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
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

	public String getRemittanceTime() {
		return remittanceTime;
	}

	public void setRemittanceTime(String remittanceTime) {
		this.remittanceTime = remittanceTime;
	}

	public String getProfessionalWork() {
		return professionalWork;
	}

	public void setProfessionalWork(String professionalWork) {
		this.professionalWork = professionalWork;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getPipelinenumber() {
		return pipelinenumber;
	}

	public void setPipelinenumber(String pipelinenumber) {
		this.pipelinenumber = pipelinenumber;
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

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public BigDecimal getPaymentmoney() {
		return paymentmoney;
	}

	public void setPaymentmoney(BigDecimal paymentmoney) {
		this.paymentmoney = paymentmoney;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getLoanSource() {
		return loanSource;
	}

	public void setLoanSource(String loanSource) {
		this.loanSource = loanSource;
	}

	public Integer getThirdparty_id() {
		return thirdparty_id;
	}

	public void setThirdparty_id(Integer thirdparty_id) {
		this.thirdparty_id = thirdparty_id;
	}
	
	
}
