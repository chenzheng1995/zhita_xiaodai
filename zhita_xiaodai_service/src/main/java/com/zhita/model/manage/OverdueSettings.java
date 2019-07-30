package com.zhita.model.manage;

import java.math.BigDecimal;

//逾期设置表
public class OverdueSettings {
    private Integer id;

    private Integer productid;//产品id
    
    private Integer companyid;//公司id

    private Integer overduehowmanydaysage;//逾期第N天前

    private BigDecimal penaltyinterestrates;//罚息利率

    private Integer operator;//操作人

    private String operationtime;//操作时间

    private String deleted;//假删除（1：删除；0：没删除）

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

    public Integer getOverduehowmanydaysage() {
        return overduehowmanydaysage;
    }

    public void setOverduehowmanydaysage(Integer overduehowmanydaysage) {
        this.overduehowmanydaysage = overduehowmanydaysage;
    }

    public BigDecimal getPenaltyinterestrates() {
		return penaltyinterestrates;
	}

	public void setPenaltyinterestrates(BigDecimal penaltyinterestrates) {
		this.penaltyinterestrates = penaltyinterestrates;
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
        this.operationtime = operationtime == null ? null : operationtime.trim();
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }
}