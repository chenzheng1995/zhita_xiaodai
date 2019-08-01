package com.zhita.model.manage;

//订单查询模块需要的参数
public class OrderQueryParameter {
	private Integer companyid;//公司id
	private Integer page;//当前页page
	private Integer pagesize;//每页多少条
	private String ordernumber;//订单编号
	private String name;//姓名
	private String phone;//手机号
	private String idcard;//身份证号
	private String registestarttime;//注册开始时间
	private String registeendtime;//注册结束时间
	private Integer sourcename;//渠道名字id
	private Integer userid;//用户id
	private String registeClient;//注册客户端
	
	
	//不需要用的字段
	private String orderstarttime;//订单开始时间
	private String orderendtime;//订单结束时间
	private String riskmanagementype;//风控类型
	private String riskcontrolname;//风控名
	private String start;//开始分数
	private String end;//结束分数
	private Integer assessor;//审核员
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
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
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
	public Integer getSourcename() {
		return sourcename;
	}
	public void setSourcename(Integer sourcename) {
		this.sourcename = sourcename;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getRegistestarttime() {
		return registestarttime;
	}
	public void setRegistestarttime(String registestarttime) {
		this.registestarttime = registestarttime;
	}
	public String getRegisteendtime() {
		return registeendtime;
	}
	public void setRegisteendtime(String registeendtime) {
		this.registeendtime = registeendtime;
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
	public Integer getAssessor() {
		return assessor;
	}
	public void setAssessor(Integer assessor) {
		this.assessor = assessor;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getRegisteClient() {
		return registeClient;
	}
	public void setRegisteClient(String registeClient) {
		this.registeClient = registeClient;
	}
}
