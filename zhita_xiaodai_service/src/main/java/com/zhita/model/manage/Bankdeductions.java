package com.zhita.model.manage;

import java.math.BigDecimal;
import java.util.List;


//银行扣记录表
public class Bankdeductions {
	
	private Integer id;//id
	
	private Integer sys_userId;//操作人ID
	
	private Integer deductionproportion;//扣款比例
	
	private BigDecimal thirdpartypayment;//第三方支付费
	
	private Integer userId;//用户ID
	
	private Integer OrderId;//订单ID
	
	private String deductionstatus;//扣款状态
	
	private BigDecimal deduction_money;//扣款金额
	
	private String deduction_time;
	
	private String orderNumber;//订单编号
	
	private String name;//名称
	
	private String phone;//手机号
	
	private String bankcardName;//银行卡号
	
	private String deferAfterReturntime;//延期后应还时间
	
	private String orderCreateTime;//实借时间
	
	private String overdueGrade;//逾期等级
	
	private String startu_time;//延期后范围开始时间
	
	private String start_time;
	
	private String end_time;//延期后范围结束时间
	
	private String statu_timeOrder;//实借时间范围开始时间
	
	private String end_timeOrder;//实借时间范围结束时间
	
	private Integer page=1;
	
	private Integer pagesize=10;
	
	private Integer companyId;//公司ID
	
	private BigDecimal interestOnArrearsSum;//总延期费
	
	private Integer orderNum;//延期笔数
	
	private Integer realborrowing;//实借笔数
	
	private BigDecimal realexpenditure;//实借金额
	
	private Integer realreturn;//实还笔数
	
	private BigDecimal paymentamount;//实还金额
	
	private Integer overdueNum;//逾期数
	
	private Integer defeNum;//延期数
	
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
	
	private String orderstail;
	
	private List<Integer> userIds;
	
	private List<String> orderNumbers;
	
	private Integer userNum;//扣款数
	
	private Integer chengNum;//成功数
	
	private Integer shiNum;//失败数
	
	private String cdata;//成功率
	
	private String statu_time;
	
	private Integer branKnum;//银行扣款数
	
	private BigDecimal brankMoney;//银行扣款金额
	
	private BigDecimal ChengMoney;//成功扣款总金额
	
	private String account;
	
	private BigDecimal realityBorrowMoney;//实际借款金额
	
	private BigDecimal interestPenaltySum;//逾期总罚息
	
	private BigDecimal collection_money;//含逾应还总金额
	
	private String deferredTime;
	
	private String XianShangCoune;//线上统计
	
	private String XianJianmianCount;//线下统计
	
	private String BankMoneys;
	
	private BigDecimal xiansMoney;//线上总金额
	
	private BigDecimal xianxiaMoney;//线下总金额

	public BigDecimal getXiansMoney() {
		return xiansMoney;
	}

	public void setXiansMoney(BigDecimal xiansMoney) {
		this.xiansMoney = xiansMoney;
	}

	public BigDecimal getXianxiaMoney() {
		return xianxiaMoney;
	}

	public void setXianxiaMoney(BigDecimal xianxiaMoney) {
		this.xianxiaMoney = xianxiaMoney;
	}

	public String getBankMoneys() {
		return BankMoneys;
	}

	public void setBankMoneys(String bankMoneys) {
		BankMoneys = bankMoneys;
	}

	public String getXianShangCoune() {
		return XianShangCoune;
	}

	public void setXianShangCoune(String xianShangCoune) {
		XianShangCoune = xianShangCoune;
	}

	public String getXianJianmianCount() {
		return XianJianmianCount;
	}

	public void setXianJianmianCount(String xianJianmianCount) {
		XianJianmianCount = xianJianmianCount;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public Integer getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(Integer overdueNum) {
		this.overdueNum = overdueNum;
	}

	public Integer getDefeNum() {
		return defeNum;
	}

	public void setDefeNum(Integer defeNum) {
		this.defeNum = defeNum;
	}

	public String getDeferredTime() {
		return deferredTime;
	}

	public void setDeferredTime(String deferredTime) {
		this.deferredTime = deferredTime;
	}

	public BigDecimal getCollection_money() {
		return collection_money;
	}

	public void setCollection_money(BigDecimal collection_money) {
		this.collection_money = collection_money;
	}

	public BigDecimal getRealityBorrowMoney() {
		return realityBorrowMoney;
	}

	public void setRealityBorrowMoney(BigDecimal realityBorrowMoney) {
		this.realityBorrowMoney = realityBorrowMoney;
	}

	public BigDecimal getInterestPenaltySum() {
		return interestPenaltySum;
	}

	public void setInterestPenaltySum(BigDecimal interestPenaltySum) {
		this.interestPenaltySum = interestPenaltySum;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getChengMoney() {
		return ChengMoney;
	}

	public void setChengMoney(BigDecimal chengMoney) {
		ChengMoney = chengMoney;
	}

	public Integer getBranKnum() {
		return branKnum;
	}

	public void setBranKnum(Integer branKnum) {
		this.branKnum = branKnum;
	}

	public BigDecimal getBrankMoney() {
		return brankMoney;
	}

	public void setBrankMoney(BigDecimal brankMoney) {
		this.brankMoney = brankMoney;
	}

	public String getCdata() {
		return cdata;
	}

	public void setCdata(String cdata) {
		this.cdata = cdata;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Integer getChengNum() {
		return chengNum;
	}

	public void setChengNum(Integer chengNum) {
		this.chengNum = chengNum;
	}

	public Integer getShiNum() {
		return shiNum;
	}

	public void setShiNum(Integer shiNum) {
		this.shiNum = shiNum;
	}


	public String getStatu_time() {
		return statu_time;
	}

	public void setStatu_time(String statu_time) {
		this.statu_time = statu_time;
	}

	public List<String> getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(List<String> orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public String getOrderstail() {
		return orderstail;
	}

	public void setOrderstail(String orderstail) {
		this.orderstail = orderstail;
	}

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

	public String getDeduction_time() {
		return deduction_time;
	}

	public void setDeduction_time(String deduction_time) {
		this.deduction_time = deduction_time;
	}
	
	
}	
