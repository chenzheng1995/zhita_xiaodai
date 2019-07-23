package com.zhita.model.manage;


//银行卡分类表
public class Bankcard_type {

	
	private Integer id;
	
	private Integer companyId;//公司id
	
	private String bankcardTypeName;//银行卡分类名称
	
	private String deleted;

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

	public String getBankcardTypeName() {
		return bankcardTypeName;
	}

	public void setBankcardTypeName(String bankcardTypeName) {
		this.bankcardTypeName = bankcardTypeName;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	
}
