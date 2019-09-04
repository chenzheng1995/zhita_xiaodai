package com.zhita.model.manage;

import java.math.BigDecimal;


//废弃订单详情
public class Discardorderdetails {
	
	private Integer id;
	
	private Integer orderId;
	
	private BigDecimal realityBorrowMoney;
	
	private BigDecimal makeLoans;
	
	private BigDecimal interestDay;
	
	private BigDecimal interestSum;
	
	private String overdueNumberOfDays;
	
	private BigDecimal interestPenaltyDay;
	
	private BigDecimal interestPenaltySum;
	
	private BigDecimal interestInAll;
	
	private BigDecimal shouldReapyMoney;
	
	private BigDecimal technicalServiceMoney;
	
	private BigDecimal realityAccount;
	
	private String rejectMonadCause;
	
	private String DrainageOfPlatform;
	
	private String registerClient;
	
	private BigDecimal surplus_money;

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

	public BigDecimal getRealityBorrowMoney() {
		return realityBorrowMoney;
	}

	public void setRealityBorrowMoney(BigDecimal realityBorrowMoney) {
		this.realityBorrowMoney = realityBorrowMoney;
	}

	public BigDecimal getMakeLoans() {
		return makeLoans;
	}

	public void setMakeLoans(BigDecimal makeLoans) {
		this.makeLoans = makeLoans;
	}

	public BigDecimal getInterestDay() {
		return interestDay;
	}

	public void setInterestDay(BigDecimal interestDay) {
		this.interestDay = interestDay;
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

	public BigDecimal getInterestPenaltyDay() {
		return interestPenaltyDay;
	}

	public void setInterestPenaltyDay(BigDecimal interestPenaltyDay) {
		this.interestPenaltyDay = interestPenaltyDay;
	}

	public BigDecimal getInterestPenaltySum() {
		return interestPenaltySum;
	}

	public void setInterestPenaltySum(BigDecimal interestPenaltySum) {
		this.interestPenaltySum = interestPenaltySum;
	}

	public BigDecimal getInterestInAll() {
		return interestInAll;
	}

	public void setInterestInAll(BigDecimal interestInAll) {
		this.interestInAll = interestInAll;
	}

	public BigDecimal getShouldReapyMoney() {
		return shouldReapyMoney;
	}

	public void setShouldReapyMoney(BigDecimal shouldReapyMoney) {
		this.shouldReapyMoney = shouldReapyMoney;
	}

	public BigDecimal getTechnicalServiceMoney() {
		return technicalServiceMoney;
	}

	public void setTechnicalServiceMoney(BigDecimal technicalServiceMoney) {
		this.technicalServiceMoney = technicalServiceMoney;
	}

	public BigDecimal getRealityAccount() {
		return realityAccount;
	}

	public void setRealityAccount(BigDecimal realityAccount) {
		this.realityAccount = realityAccount;
	}

	public String getRejectMonadCause() {
		return rejectMonadCause;
	}

	public void setRejectMonadCause(String rejectMonadCause) {
		this.rejectMonadCause = rejectMonadCause;
	}

	public String getDrainageOfPlatform() {
		return DrainageOfPlatform;
	}

	public void setDrainageOfPlatform(String drainageOfPlatform) {
		DrainageOfPlatform = drainageOfPlatform;
	}

	public String getRegisterClient() {
		return registerClient;
	}

	public void setRegisterClient(String registerClient) {
		this.registerClient = registerClient;
	}

	public BigDecimal getSurplus_money() {
		return surplus_money;
	}

	public void setSurplus_money(BigDecimal surplus_money) {
		this.surplus_money = surplus_money;
	}
	
	
}
