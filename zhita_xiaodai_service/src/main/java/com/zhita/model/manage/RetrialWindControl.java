package com.zhita.model.manage;

//再审风控表
public class RetrialWindControl {
    private Integer id;

    private Integer productid;//产品id
    
    private Integer companyid;//公司id

    private Integer howmanydaysapart;//间隔多少天

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

    public Integer getHowmanydaysapart() {
        return howmanydaysapart;
    }

    public void setHowmanydaysapart(Integer howmanydaysapart) {
        this.howmanydaysapart = howmanydaysapart;
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