package com.zhita.model.manage;



//引流平台
public class Drainage_of_platform {
	
	private Integer id;
	
	private String drainageOfPlatformName;//名称
	
	private String deleted;
	
	private Integer companyId;//公司ID

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDrainageOfPlatformName() {
		return drainageOfPlatformName;
	}

	public void setDrainageOfPlatformName(String drainageOfPlatformName) {
		this.drainageOfPlatformName = drainageOfPlatformName;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
	
}
