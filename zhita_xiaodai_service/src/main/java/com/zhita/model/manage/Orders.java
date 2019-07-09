package com.zhita.model.manage;

import java.math.BigDecimal;
import java.util.List;

//订单表
public class Orders {
	private Integer id;
	
	private Integer companyId;//公司ID
	
	private Integer userId;//用户ID
	
	private String orderNumber;//订单号（不是自增，有固定的生成格式）
	
	private String borrowMoneyUse;//借钱用途
	
	private String borrowRepayBankcard;//借还银行
	
	private String bankPhone;//银行手机号
	
	private String orderCreateTime;//订单生成时间(实借时间)
	
	private String riskManagemenType;//风控类型（通过已借款    通过未借款    拒绝   待审核四种方式）
	
	private String borrowTimeLimit;//借款期限
	
	private String borrowMoneyWay;//贷款方式（立即贷和分期贷）
	
	private String borrowMoneyState;//还款状态（已还和未还）(1:已还；0：未还)
	
	private String shouldReturnTime;//应还时间
	
	private String realtime;//还款时间（实还时间）
	
	private String ifcandefer;//是否可以延期（1：可以；2：不可以）
	
	private Integer canDeferNumberoftime;//可以延期的次数
	
	private String riskmanagementFraction;//风控分数
	
	private String ifoverdue;//是否逾期
	
	private String overdueGrade;//逾期等级
	
	
	private Integer adoptcount;//放款通过数
	
	private Integer Loancount;//实际借款数
	
	private Integer Passrate;//放款通过率
	
	private Integer Beoverdue;//逾期笔数
	
	private Integer pressformoney;//催款笔数
	
	private Integer Baddebt;//坏账笔数
	
	private BigDecimal Realborrowing;//实借金额
	
	private BigDecimal Realreturn;//实还金额
	
	private BigDecimal Delay;//延期金额
	
	private BigDecimal Overdueamount;//逾期金额
	
	private BigDecimal Amountofbaddebts;//坏账金额
	
	private List<Integer> memberid;//催收员ID
	
	private BigDecimal makeLoans;//实际放款金额
	
	private BigDecimal realityAccount;//实际到账金额
	
	private BigDecimal Actualrevenue;//实际营收

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBorrowMoneyUse() {
		return borrowMoneyUse;
	}

	public void setBorrowMoneyUse(String borrowMoneyUse) {
		this.borrowMoneyUse = borrowMoneyUse;
	}

	public String getBorrowRepayBankcard() {
		return borrowRepayBankcard;
	}

	public void setBorrowRepayBankcard(String borrowRepayBankcard) {
		this.borrowRepayBankcard = borrowRepayBankcard;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getRiskManagemenType() {
		return riskManagemenType;
	}

	public void setRiskManagemenType(String riskManagemenType) {
		this.riskManagemenType = riskManagemenType;
	}

	public String getBorrowTimeLimit() {
		return borrowTimeLimit;
	}

	public void setBorrowTimeLimit(String borrowTimeLimit) {
		this.borrowTimeLimit = borrowTimeLimit;
	}

	public String getBorrowMoneyWay() {
		return borrowMoneyWay;
	}

	public void setBorrowMoneyWay(String borrowMoneyWay) {
		this.borrowMoneyWay = borrowMoneyWay;
	}

	public String getBorrowMoneyState() {
		return borrowMoneyState;
	}

	public void setBorrowMoneyState(String borrowMoneyState) {
		this.borrowMoneyState = borrowMoneyState;
	}

	public String getShouldReturnTime() {
		return shouldReturnTime;
	}

	public void setShouldReturnTime(String shouldReturnTime) {
		this.shouldReturnTime = shouldReturnTime;
	}

	public String getRealtime() {
		return realtime;
	}

	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}

	public String getIfcandefer() {
		return ifcandefer;
	}

	public void setIfcandefer(String ifcandefer) {
		this.ifcandefer = ifcandefer;
	}

	public Integer getCanDeferNumberoftime() {
		return canDeferNumberoftime;
	}

	public void setCanDeferNumberoftime(Integer canDeferNumberoftime) {
		this.canDeferNumberoftime = canDeferNumberoftime;
	}

	public String getRiskmanagementFraction() {
		return riskmanagementFraction;
	}

	public void setRiskmanagementFraction(String riskmanagementFraction) {
		this.riskmanagementFraction = riskmanagementFraction;
	}

	public String getIfoverdue() {
		return ifoverdue;
	}

	public void setIfoverdue(String ifoverdue) {
		this.ifoverdue = ifoverdue;
	}

	public String getOverdueGrade() {
		return overdueGrade;
	}

	public void setOverdueGrade(String overdueGrade) {
		this.overdueGrade = overdueGrade;
	}

	public Integer getAdoptcount() {
		return adoptcount;
	}

	public void setAdoptcount(Integer adoptcount) {
		this.adoptcount = adoptcount;
	}

	public Integer getLoancount() {
		return Loancount;
	}

	public void setLoancount(Integer loancount) {
		Loancount = loancount;
	}

	public Integer getPassrate() {
		return Passrate;
	}

	public void setPassrate(Integer passrate) {
		Passrate = passrate;
	}

	public Integer getBeoverdue() {
		return Beoverdue;
	}

	public void setBeoverdue(Integer beoverdue) {
		Beoverdue = beoverdue;
	}

	public Integer getPressformoney() {
		return pressformoney;
	}

	public void setPressformoney(Integer pressformoney) {
		this.pressformoney = pressformoney;
	}

	public Integer getBaddebt() {
		return Baddebt;
	}

	public void setBaddebt(Integer baddebt) {
		Baddebt = baddebt;
	}

	public BigDecimal getRealborrowing() {
		return Realborrowing;
	}

	public void setRealborrowing(BigDecimal realborrowing) {
		Realborrowing = realborrowing;
	}

	public BigDecimal getRealreturn() {
		return Realreturn;
	}

	public void setRealreturn(BigDecimal realreturn) {
		Realreturn = realreturn;
	}

	public BigDecimal getDelay() {
		return Delay;
	}

	public void setDelay(BigDecimal delay) {
		Delay = delay;
	}

	public BigDecimal getOverdueamount() {
		return Overdueamount;
	}

	public void setOverdueamount(BigDecimal overdueamount) {
		Overdueamount = overdueamount;
	}

	public BigDecimal getAmountofbaddebts() {
		return Amountofbaddebts;
	}

	public void setAmountofbaddebts(BigDecimal amountofbaddebts) {
		Amountofbaddebts = amountofbaddebts;
	}

	public List<Integer> getMemberid() {
		return memberid;
	}

	public void setMemberid(List<Integer> memberid) {
		this.memberid = memberid;
	}

	public BigDecimal getMakeLoans() {
		return makeLoans;
	}

	public void setMakeLoans(BigDecimal makeLoans) {
		this.makeLoans = makeLoans;
	}

	public BigDecimal getRealityAccount() {
		return realityAccount;
	}

	public void setRealityAccount(BigDecimal realityAccount) {
		this.realityAccount = realityAccount;
	}

	public BigDecimal getActualrevenue() {
		return Actualrevenue;
	}

	public void setActualrevenue(BigDecimal actualrevenue) {
		Actualrevenue = actualrevenue;
	}

}
