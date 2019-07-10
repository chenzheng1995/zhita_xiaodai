package com.zhita.model.manage;

//订单查询模块需要的参数
public class OrderQueryParameter {
	private Integer companyid;//公司id
	private Integer page;//当前页page
	private String ordernumber;//订单编号
	private String orderstarttime;//订单开始时间
	private String orderendtime;//订单结束时间
	private String riskmanagementype;//风控类型
	private String riskcontrolname;//风控名
	private String start;//开始分数
	private String end;//结束分数
	private String assessor;//审核员
	private Integer sourcename;//渠道名字
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrderstarttime() {
		return orderstarttime;
	}
	public void setOrderstarttime(String orderstarttime) {
		this.orderstarttime = orderstarttime;
	}
	public String getOrderendtime() {
		return orderendtime;
	}
	public void setOrderendtime(String orderendtime) {
		this.orderendtime = orderendtime;
	}
	public String getRiskmanagementype() {
		return riskmanagementype;
	}
	public void setRiskmanagementype(String riskmanagementype) {
		this.riskmanagementype = riskmanagementype;
	}
	public String getRiskcontrolname() {
		return riskcontrolname;
	}
	public void setRiskcontrolname(String riskcontrolname) {
		this.riskcontrolname = riskcontrolname;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getAssessor() {
		return assessor;
	}
	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}
	public Integer getSourcename() {
		return sourcename;
	}
	public void setSourcename(Integer sourcename) {
		this.sourcename = sourcename;
	}
	@Override
	public String toString() {
		return "OrderQueryParameter [companyid=" + companyid + ", page=" + page + ", ordernumber=" + ordernumber
				+ ", orderstarttime=" + orderstarttime + ", orderendtime=" + orderendtime + ", riskmanagementype="
				+ riskmanagementype + ", riskcontrolname=" + riskcontrolname + ", start=" + start + ", end=" + end
				+ ", assessor=" + assessor + ", sourcename=" + sourcename + "]";
	}
	
}
