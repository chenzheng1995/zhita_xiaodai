package com.zhita.model.manage;

import java.math.BigDecimal;


//id
public class Offlinedelay {
	
	private Integer id;
	
	private String delay_time;	//设置延期后应还时间
	
	private BigDecimal extensionfee;//线下延期金额
	
	private String remarks;//备注
	
	private String operating_time;//操作时间
	
	private Integer sys_userId;//操作人ID
	
	private Integer orderId;//订单ID
	
	private String name;//姓名
	
	private String phone;//手机号
	
	private String statu_time;
	
	private String end_time;
	
	private Integer page=0;
	
	private Integer pagesize=10;
	
	private String account;
	
	private String idcard_number;
	
	private String deferAfterReturntime;
	
	private Integer overdueNumberOfDays;
	
	private Integer DefeNum;//延期次数
	
	private BigDecimal DefeMoney;//延期金额
	
	private Integer onceDeferredDay;//单次延期天数
	
	private Integer companyId;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getOnceDeferredDay() {
		return onceDeferredDay;
	}

	public void setOnceDeferredDay(Integer onceDeferredDay) {
		this.onceDeferredDay = onceDeferredDay;
	}

	public Integer getDefeNum() {
		return DefeNum;
	}

	public void setDefeNum(Integer defeNum) {
		DefeNum = defeNum;
	}

	public BigDecimal getDefeMoney() {
		return DefeMoney;
	}

	public void setDefeMoney(BigDecimal defeMoney) {
		DefeMoney = defeMoney;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIdcard_number() {
		return idcard_number;
	}

	public void setIdcard_number(String idcard_number) {
		this.idcard_number = idcard_number;
	}

	public String getDeferAfterReturntime() {
		return deferAfterReturntime;
	}

	public void setDeferAfterReturntime(String deferAfterReturntime) {
		this.deferAfterReturntime = deferAfterReturntime;
	}

	public Integer getOverdueNumberOfDays() {
		return overdueNumberOfDays;
	}

	public void setOverdueNumberOfDays(Integer overdueNumberOfDays) {
		this.overdueNumberOfDays = overdueNumberOfDays;
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

	public String getStatu_time() {
		return statu_time;
	}

	public void setStatu_time(String statu_time) {
		this.statu_time = statu_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getOperating_time() {
		return operating_time;
	}

	public void setOperating_time(String operating_time) {
		this.operating_time = operating_time;
	}

	public Integer getSys_userId() {
		return sys_userId;
	}

	public void setSys_userId(Integer sys_userId) {
		this.sys_userId = sys_userId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDelay_time() {
		return delay_time;
	}

	public void setDelay_time(String delay_time) {
		this.delay_time = delay_time;
	}

	public BigDecimal getExtensionfee() {
		return extensionfee;
	}

	public void setExtensionfee(BigDecimal extensionfee) {
		this.extensionfee = extensionfee;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
