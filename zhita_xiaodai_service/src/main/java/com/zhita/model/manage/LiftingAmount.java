package com.zhita.model.manage;

//续借提额表
public class LiftingAmount {
    private Integer id;
    
    private Integer productid;//产品id

    private Integer companyid;//公司id

    private Integer userhowmanyconsecutivepayments;//用户连续还款多少次以后

    private Integer increasethequota;//提高额度

    private String operator;//操作人

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

    public Integer getUserhowmanyconsecutivepayments() {
        return userhowmanyconsecutivepayments;
    }

    public void setUserhowmanyconsecutivepayments(Integer userhowmanyconsecutivepayments) {
        this.userhowmanyconsecutivepayments = userhowmanyconsecutivepayments;
    }

    public Integer getIncreasethequota() {
        return increasethequota;
    }

    public void setIncreasethequota(Integer increasethequota) {
        this.increasethequota = increasethequota;
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

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted == null ? null : deleted.trim();
    }
}