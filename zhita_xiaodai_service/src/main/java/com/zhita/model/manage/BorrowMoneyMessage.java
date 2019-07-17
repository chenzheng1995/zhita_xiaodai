package com.zhita.model.manage;

import java.math.BigDecimal;

//借款信息表
public class BorrowMoneyMessage {
    private Integer id;
    
    private Integer productid;//产品id
    
    private Integer companyid;//公司id

    private String loantype;//贷款类型

    private BigDecimal canborrowlines;//可借额度

    private Integer lifeofloan;//借款期限

    private BigDecimal averagedailyinterest;//期限内日均利息

    private Integer platformfeeratio;//平台服务费比率

    private Integer operator;//操作人

    private String operationtime;//操作时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getLoantype() {
		return loantype;
	}

	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	public BigDecimal getCanborrowlines() {
		return canborrowlines;
	}

	public void setCanborrowlines(BigDecimal canborrowlines) {
		this.canborrowlines = canborrowlines;
	}

	public Integer getLifeofloan() {
		return lifeofloan;
	}

	public void setLifeofloan(Integer lifeofloan) {
		this.lifeofloan = lifeofloan;
	}

	public BigDecimal getAveragedailyinterest() {
		return averagedailyinterest;
	}

	public void setAveragedailyinterest(BigDecimal averagedailyinterest) {
		this.averagedailyinterest = averagedailyinterest;
	}

	public Integer getPlatformfeeratio() {
		return platformfeeratio;
	}

	public void setPlatformfeeratio(Integer platformfeeratio) {
		this.platformfeeratio = platformfeeratio;
	}


	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getOperationtime() {
		return operationtime;
	}

	public void setOperationtime(String operationtime) {
		this.operationtime = operationtime;
	}

}