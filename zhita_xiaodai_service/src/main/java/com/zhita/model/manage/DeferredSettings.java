package com.zhita.model.manage;

import java.math.BigDecimal;

//延期设置表
public class DeferredSettings {
    private Integer id;
    
    private Integer productid;//产品id

    private Integer companyid;//公司id

    private Integer maximumcandeferredtime;//最多可延期次数

    private Integer oncedeferredday;//单次延期天数

    private BigDecimal oncedeferredmoney;//单次延期费用

    private Integer overduehowdaycandeferred;//逾期多少天前可延期
    
    private String status;//状态（1：开启2；关闭）

    private Integer operator;//操作人id

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

	public Integer getMaximumcandeferredtime() {
		return maximumcandeferredtime;
	}

	public void setMaximumcandeferredtime(Integer maximumcandeferredtime) {
		this.maximumcandeferredtime = maximumcandeferredtime;
	}

	public Integer getOncedeferredday() {
		return oncedeferredday;
	}

	public void setOncedeferredday(Integer oncedeferredday) {
		this.oncedeferredday = oncedeferredday;
	}


	public BigDecimal getOncedeferredmoney() {
		return oncedeferredmoney;
	}

	public void setOncedeferredmoney(BigDecimal oncedeferredmoney) {
		this.oncedeferredmoney = oncedeferredmoney;
	}

	public Integer getOverduehowdaycandeferred() {
		return overduehowdaycandeferred;
	}

	public void setOverduehowdaycandeferred(Integer overduehowdaycandeferred) {
		this.overduehowdaycandeferred = overduehowdaycandeferred;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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