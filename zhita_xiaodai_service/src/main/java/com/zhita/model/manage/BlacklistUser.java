package com.zhita.model.manage;

//黑名单用户表
public class BlacklistUser {
    private Integer id;

    private Integer companyid;//公司id

    private String name;//姓名

    private String phone;//手机号

    private String idcard;//身份证

    private Integer userid;//用户id
    
    private String operator;//操作人
    
    private String operationtime;//操作时间

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperationtime() {
		return operationtime;
	}

	public void setOperationtime(String operationtime) {
		this.operationtime = operationtime;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}