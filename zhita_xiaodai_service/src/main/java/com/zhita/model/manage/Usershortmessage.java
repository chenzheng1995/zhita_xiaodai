package com.zhita.model.manage;

public class Usershortmessage {
	
	private Integer id;
	
	private String send_time;
	
	private String user_type;
	
	private String usernum;
	
	private String short_text;
	
	private Integer sys_userId;
	
	private Integer companyId;
	
	private String phone;
	
	private Integer page=0;
	
	private Integer pagesize=10;
	
	private String registeClient;

	public String getRegisteClient() {
		return registeClient;
	}

	public void setRegisteClient(String registeClient) {
		this.registeClient = registeClient;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getSys_userId() {
		return sys_userId;
	}

	public void setSys_userId(Integer sys_userId) {
		this.sys_userId = sys_userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUsernum() {
		return usernum;
	}

	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}

	public String getShort_text() {
		return short_text;
	}

	public void setShort_text(String short_text) {
		this.short_text = short_text;
	}
	
	

}
