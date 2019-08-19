package com.zhita.model.manage;

//第三方分类表
public class Thirdtype {
    private Integer id;//第三方分类id
    
    private Integer companyid;//公司id

    private String thirdtypename;//第三方分类名称

    private String deleted;//假删除（1：删除；0：没删除）

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getThirdtypename() {
		return thirdtypename;
	}

	public void setThirdtypename(String thirdtypename) {
		this.thirdtypename = thirdtypename;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}