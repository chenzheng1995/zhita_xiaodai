package com.zhita.model.manage;

//借款信息表
public class BorrowMoneyMessage {
    private Integer id;
    
    private Integer productid;//产品id
    
    private Integer companyid;//公司id

    private String loantype;//贷款类型

    private Integer canborrowlines;//可借额度

    private Integer lifeofloan;//借款期限

    private Double averagedailyinterest;//期限内日均利息

    private Integer platformfeeratio;//平台服务费比率

    private String operator;//操作人

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
        this.loantype = loantype == null ? null : loantype.trim();
    }

    public Integer getCanborrowlines() {
        return canborrowlines;
    }

    public void setCanborrowlines(Integer canborrowlines) {
        this.canborrowlines = canborrowlines;
    }

    public Integer getLifeofloan() {
        return lifeofloan;
    }

    public void setLifeofloan(Integer lifeofloan) {
        this.lifeofloan = lifeofloan;
    }

    public Double getAveragedailyinterest() {
        return averagedailyinterest;
    }

    public void setAveragedailyinterest(Double averagedailyinterest) {
        this.averagedailyinterest = averagedailyinterest;
    }

    public Integer getPlatformfeeratio() {
        return platformfeeratio;
    }

    public void setPlatformfeeratio(Integer platformfeeratio) {
        this.platformfeeratio = platformfeeratio;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime == null ? null : operationtime.trim();
    }
}