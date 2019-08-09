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
	
	private String shouldReturnTime;//应还时间
	
	private String realtime;//还款时间（实还时间）
	
	private String ifcandefer;//是否可以延期（1：可以；2：不可以）
	
	private Integer canDeferNumberoftime;//可以延期的次数
	
	private String riskmanagementFraction;//风控分数
	
	private String overdueGrade;//逾期等级

	private String riskcontrolname;//风控名字
	
	private String orderStatus;//订单状态（0：期限中；1：已逾期；2：已延期；3：已还款）
	
	private String ifpeopleWhose;//是否人审（1；已人审；0：未人审）
	
	private String assessor;//审核员
	
	private String repaymentMoney;//还款金额
	
	private Integer howManyTimesBorMoney;//第几次借款----
	
	private Integer deferrTime;//延期次数
	
	private BigDecimal deferrMoney;//延期金额
	
	private String deferAfterReturntime;//延期后应还时间
	
	private Orderdetails orderdetails;//对应订单明细表
	
	private User user;//对应用户对象
	
	private BigDecimal realityBorrowMoney;//实际借款金额（元）
	
	private BigDecimal interestSum;//期内总利息（元）
	
	private String overdueNumberOfDays;//逾期天数
	
	private BigDecimal surplus_money;
	
	private Integer adoptcount;//放款通过数
	
	private Integer Loancount;//实际借款数
	
	private Integer Passrate;//放款通过率
	
	private Integer Beoverdue;//催收笔数
	
	private Integer pressformoney;//催收次数
	
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
	
	private Integer daysofrepayment;//还款天数
	
	private String operator_time;

	private String remittanceTime;
	
	private BigDecimal GesamtbetragderDarlehen;//总放款金额
	
	private Integer ZahlderGesamtdarlehen;//总放款笔数
	
	private BigDecimal GesamtbetragderRvckzahlung;//总还款金额
	
	private Integer GesamtbetragderNum;//总还款笔数
	
	private BigDecimal GesamtbetraguberfalligerBetrag;//总逾期金额
	
	private Integer GesamtbetraguberfallNum;//总逾期数
	
	private BigDecimal GesamtbetragderVerbindlichkeiten;//总坏账金额
	
	private Integer GesamtbetragdererNum;//总坏账数
	
	private Integer collection_count;
	
	private BigDecimal interestPenaltySum;
	
	private Integer chenggNum;
	
	private Integer chenggData;
	
	private String TransAmt;
	
	private String collectionData;

	public String getCollectionData() {
		return collectionData;
	}

	public void setCollectionData(String collectionData) {
		this.collectionData = collectionData;
	}

	public String getRemittanceTime() {
		return remittanceTime;
	}

	public void setRemittanceTime(String remittanceTime) {
		this.remittanceTime = remittanceTime;
	}

	public String getTransAmt() {
		return TransAmt;
	}

	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}

	public BigDecimal getSurplus_money() {
		return surplus_money;
	}

	public void setSurplus_money(BigDecimal surplus_money) {
		this.surplus_money = surplus_money;
	}

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

	public String getOverdueGrade() {
		return overdueGrade;
	}

	public void setOverdueGrade(String overdueGrade) {
		this.overdueGrade = overdueGrade;
	}

	public String getRiskcontrolname() {
		return riskcontrolname;
	}

	public void setRiskcontrolname(String riskcontrolname) {
		this.riskcontrolname = riskcontrolname;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getIfpeopleWhose() {
		return ifpeopleWhose;
	}

	public void setIfpeopleWhose(String ifpeopleWhose) {
		this.ifpeopleWhose = ifpeopleWhose;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public Orderdetails getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(Orderdetails orderdetails) {
		this.orderdetails = orderdetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(String repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}

	public Integer getHowManyTimesBorMoney() {
		return howManyTimesBorMoney;
	}

	public void setHowManyTimesBorMoney(Integer howManyTimesBorMoney) {
		this.howManyTimesBorMoney = howManyTimesBorMoney;
	}

	public Integer getDeferrTime() {
		return deferrTime;
	}

	public void setDeferrTime(Integer deferrTime) {
		this.deferrTime = deferrTime;
	}

	public BigDecimal getDeferrMoney() {
		return deferrMoney;
	}

	public void setDeferrMoney(BigDecimal deferrMoney) {
		this.deferrMoney = deferrMoney;
	}

	public String getDeferAfterReturntime() {
		return deferAfterReturntime;
	}

	public void setDeferAfterReturntime(String deferAfterReturntime) {
		this.deferAfterReturntime = deferAfterReturntime;
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

	public Integer getDaysofrepayment() {
		return daysofrepayment;
	}

	public void setDaysofrepayment(Integer daysofrepayment) {
		this.daysofrepayment = daysofrepayment;
	}

	public String getOperator_time() {
		return operator_time;
	}

	public void setOperator_time(String operator_time) {
		this.operator_time = operator_time;
	}

	public BigDecimal getGesamtbetragderDarlehen() {
		return GesamtbetragderDarlehen;
	}

	public void setGesamtbetragderDarlehen(BigDecimal gesamtbetragderDarlehen) {
		GesamtbetragderDarlehen = gesamtbetragderDarlehen;
	}

	public Integer getZahlderGesamtdarlehen() {
		return ZahlderGesamtdarlehen;
	}

	public void setZahlderGesamtdarlehen(Integer zahlderGesamtdarlehen) {
		ZahlderGesamtdarlehen = zahlderGesamtdarlehen;
	}

	public BigDecimal getGesamtbetragderRvckzahlung() {
		return GesamtbetragderRvckzahlung;
	}

	public void setGesamtbetragderRvckzahlung(BigDecimal gesamtbetragderRvckzahlung) {
		GesamtbetragderRvckzahlung = gesamtbetragderRvckzahlung;
	}

	public Integer getGesamtbetragderNum() {
		return GesamtbetragderNum;
	}

	public void setGesamtbetragderNum(Integer gesamtbetragderNum) {
		GesamtbetragderNum = gesamtbetragderNum;
	}

	public BigDecimal getGesamtbetraguberfalligerBetrag() {
		return GesamtbetraguberfalligerBetrag;
	}

	public void setGesamtbetraguberfalligerBetrag(BigDecimal gesamtbetraguberfalligerBetrag) {
		GesamtbetraguberfalligerBetrag = gesamtbetraguberfalligerBetrag;
	}

	public Integer getGesamtbetraguberfallNum() {
		return GesamtbetraguberfallNum;
	}

	public void setGesamtbetraguberfallNum(Integer gesamtbetraguberfallNum) {
		GesamtbetraguberfallNum = gesamtbetraguberfallNum;
	}

	public BigDecimal getGesamtbetragderVerbindlichkeiten() {
		return GesamtbetragderVerbindlichkeiten;
	}

	public void setGesamtbetragderVerbindlichkeiten(BigDecimal gesamtbetragderVerbindlichkeiten) {
		GesamtbetragderVerbindlichkeiten = gesamtbetragderVerbindlichkeiten;
	}

	public Integer getGesamtbetragdererNum() {
		return GesamtbetragdererNum;
	}

	public void setGesamtbetragdererNum(Integer gesamtbetragdererNum) {
		GesamtbetragdererNum = gesamtbetragdererNum;
	}

	public Integer getCollection_count() {
		return collection_count;
	}

	public void setCollection_count(Integer collection_count) {
		this.collection_count = collection_count;
	}

	public BigDecimal getInterestPenaltySum() {
		return interestPenaltySum;
	}

	public void setInterestPenaltySum(BigDecimal interestPenaltySum) {
		this.interestPenaltySum = interestPenaltySum;
	}

	public Integer getChenggNum() {
		return chenggNum;
	}

	public void setChenggNum(Integer chenggNum) {
		this.chenggNum = chenggNum;
	}

	public Integer getChenggData() {
		return chenggData;
	}

	public void setChenggData(Integer chenggData) {
		this.chenggData = chenggData;
	}

	public BigDecimal getRealityBorrowMoney() {
		return realityBorrowMoney;
	}

	public void setRealityBorrowMoney(BigDecimal realityBorrowMoney) {
		this.realityBorrowMoney = realityBorrowMoney;
	}

	public BigDecimal getInterestSum() {
		return interestSum;
	}

	public void setInterestSum(BigDecimal interestSum) {
		this.interestSum = interestSum;
	}

	public String getOverdueNumberOfDays() {
		return overdueNumberOfDays;
	}

	public void setOverdueNumberOfDays(String overdueNumberOfDays) {
		this.overdueNumberOfDays = overdueNumberOfDays;
	}

}