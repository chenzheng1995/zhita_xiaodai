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
	
	private String startu_time;//延期后范围开始时间
	
	private String end_time;//延期后范围结束时间
	
	private String statu_timeOrder;//实借时间范围开始时间
	
	private String end_timeOrder;//实借时间范围结束时间
	
	private Integer page=0;
	
	private Integer pagesize=10;
	
	private Integer companyId;//公司ID
	
	private BigDecimal interestOnArrearsSum;//总延期费
	
	private Integer orderNum;//延期笔数
	
	private Integer realborrowing;//实借笔数
	
	private BigDecimal realexpenditure;//实借金额
	
	private Integer realreturn;//实还笔数
	
	private BigDecimal paymentamount;//实还金额
	
	private BigDecimal overdueamount;//逾期金额
	
	private BigDecimal deferredamount;//延期金额
	
	private BigDecimal identityfee;//身份证认证费
	
	private Integer identityNum;//身份证笔数
	
	private BigDecimal facefee;//人脸识别费
	
	private Integer faceNum;//人脸识别笔数
	
	private BigDecimal operatorsfee;//运营商费
	
	private Integer operatorsNum;//运营商笔数
	
	private Integer riskmanagementNum;//风控笔数
	
	private BigDecimal riskmanagementfee;//风控费
	
	private Integer offlinemanualNum;//线下手动调账数
	
	private BigDecimal offlinemanualfee;//线下手动调账费
	
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

	public String getStartu_time() {
		return startu_time;
	}

	public void setStartu_time(String startu_time) {
		this.startu_time = startu_time;
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

	public BigDecimal getInterestOnArrearsSum() {
		return interestOnArrearsSum;
	}

	public void setInterestOnArrearsSum(BigDecimal interestOnArrearsSum) {
		this.interestOnArrearsSum = interestOnArrearsSum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getRealborrowing() {
		return realborrowing;
	}

	public void setRealborrowing(Integer realborrowing) {
		this.realborrowing = realborrowing;
	}

	public BigDecimal getRealexpenditure() {
		return realexpenditure;
	}

	public void setRealexpenditure(BigDecimal realexpenditure) {
		this.realexpenditure = realexpenditure;
	}

	public Integer getRealreturn() {
		return realreturn;
	}

	public void setRealreturn(Integer realreturn) {
		this.realreturn = realreturn;
	}

	public BigDecimal getPaymentamount() {
		return paymentamount;
	}

	public void setPaymentamount(BigDecimal paymentamount) {
		this.paymentamount = paymentamount;
	}

	public BigDecimal getOverdueamount() {
		return overdueamount;
	}

	public void setOverdueamount(BigDecimal overdueamount) {
		this.overdueamount = overdueamount;
	}

	public BigDecimal getDeferredamount() {
		return deferredamount;
	}

	public void setDeferredamount(BigDecimal deferredamount) {
		this.deferredamount = deferredamount;
	}

	public BigDecimal getIdentityfee() {
		return identityfee;
	}

	public void setIdentityfee(BigDecimal identityfee) {
		this.identityfee = identityfee;
	}

	public Integer getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(Integer identityNum) {
		this.identityNum = identityNum;
	}

	public BigDecimal getFacefee() {
		return facefee;
	}

	public void setFacefee(BigDecimal facefee) {
		this.facefee = facefee;
	}

	public Integer getFaceNum() {
		return faceNum;
	}

	public void setFaceNum(Integer faceNum) {
		this.faceNum = faceNum;
	}

	public BigDecimal getOperatorsfee() {
		return operatorsfee;
	}

	public void setOperatorsfee(BigDecimal operatorsfee) {
		this.operatorsfee = operatorsfee;
	}

	public Integer getOperatorsNum() {
		return operatorsNum;
	}

	public void setOperatorsNum(Integer operatorsNum) {
		this.operatorsNum = operatorsNum;
	}

	public Integer getRiskmanagementNum() {
		return riskmanagementNum;
	}

	public void setRiskmanagementNum(Integer riskmanagementNum) {
		this.riskmanagementNum = riskmanagementNum;
	}

	public BigDecimal getRiskmanagementfee() {
		return riskmanagementfee;
	}

	public void setRiskmanagementfee(BigDecimal riskmanagementfee) {
		this.riskmanagementfee = riskmanagementfee;
	}

	public Integer getOfflinemanualNum() {
		return offlinemanualNum;
	}

	public void setOfflinemanualNum(Integer offlinemanualNum) {
		this.offlinemanualNum = offlinemanualNum;
	}

	public BigDecimal getOfflinemanualfee() {
		return offlinemanualfee;
	}

	public void setOfflinemanualfee(BigDecimal offlinemanualfee) {
		this.offlinemanualfee = offlinemanualfee;
	}
	
	
}	
