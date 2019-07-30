package com.zhita.model.manage;

import java.math.BigDecimal;


//延期设置表
public class Deferred_settings {
	
	private Integer id;
	
	private Integer productId;//产品id
	
	private Integer companyId;//公司ID
	
	private Integer maximumCanDeferredTime;//最多可延期次数
	
	private Integer onceDeferredDay;//单次延期天数
	
	private BigDecimal onceDeferredMoney;//单次延期金额
	
	private Integer overdueHowdayCanDeferred;//逾期多少天可延期
	
	private Integer status;//状态
	
	private Integer operator;//操作人
	
	private String operationTime;//操作时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getMaximumCanDeferredTime() {
		return maximumCanDeferredTime;
	}

	public void setMaximumCanDeferredTime(Integer maximumCanDeferredTime) {
		this.maximumCanDeferredTime = maximumCanDeferredTime;
	}

	public Integer getOnceDeferredDay() {
		return onceDeferredDay;
	}

	public void setOnceDeferredDay(Integer onceDeferredDay) {
		this.onceDeferredDay = onceDeferredDay;
	}

	public BigDecimal getOnceDeferredMoney() {
		return onceDeferredMoney;
	}

	public void setOnceDeferredMoney(BigDecimal onceDeferredMoney) {
		this.onceDeferredMoney = onceDeferredMoney;
	}

	public Integer getOverdueHowdayCanDeferred() {
		return overdueHowdayCanDeferred;
	}

	public void setOverdueHowdayCanDeferred(Integer overdueHowdayCanDeferred) {
		this.overdueHowdayCanDeferred = overdueHowdayCanDeferred;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	
	
	
}
