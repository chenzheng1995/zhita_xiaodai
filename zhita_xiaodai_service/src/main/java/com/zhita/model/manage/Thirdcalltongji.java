package com.zhita.model.manage;

//第三方调用次数统计表
public class Thirdcalltongji {
    private Integer id;

    private Integer companyid;//公司id
    
    private Integer thirdtypeid;//第三方分类的id

    private String date;//日期

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

	public Integer getThirdtypeid() {
		return thirdtypeid;
	}

	public void setThirdtypeid(Integer thirdtypeid) {
		this.thirdtypeid = thirdtypeid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}