package com.zhita.model.manage;

//逾期等级表
public class OverdueClass {
    private Integer id;

    private Integer companyid;//公司id

    private String grade;//等级

    private String describes;//对应逾期天数（区间段）

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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


}