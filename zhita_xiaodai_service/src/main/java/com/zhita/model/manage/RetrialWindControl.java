package com.zhita.model.manage;

//再审风控表
public class RetrialWindControl {
    private Integer id;

    private Integer productid;//产品id
    
    private Integer companyid;//公司id

    private Integer howmanydaysapart;//间隔多少天

    private Integer operator;//操作人id

    private String operationtime;//操作时间
    
    private String ifrestore;//是否让用户的额度还原成初始额度（1：是；0：不是）

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

	public String getIfrestore() {
		return ifrestore;
	}

	public void setIfrestore(String ifrestore) {
		this.ifrestore = ifrestore;
	}
    
}