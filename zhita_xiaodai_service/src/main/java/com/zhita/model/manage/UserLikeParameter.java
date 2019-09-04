package com.zhita.model.manage;

//用户模块    模糊查询参数
public class UserLikeParameter {
	private Integer companyId;//公司id
	private Integer page;//当前页
	private Integer pagesize;//每页多少条
	private String name;//姓名
	private String phone;//手机号
	private String registeTimeStart;//注册开始时间
	private String registeTimeEnd;//注册结束时间
	private String userattestationstatus;//用户认证状态
	private String bankattestationstatus;//银行卡认证状态
	private String operaattestationstatus;//运营商认证状态
	private String applynumber;//申请编号
	private String applytimestart;//申请时间开始
	private String applytimeend;//申请时间结束
	private Integer operatorid;//审核员id
	private String registeClient;//注册客户端
	private Integer sourceid;//渠道id
	private String shareOfState;//风控状态（即用户状态）（0，机审未通过。1需要人工审核。2，机审通过。3，人审未通过。4，人审通过。5，未进风控。6，分控中）
	private String clientstatus;//客户状态（0：未借款；1：新客户；2：老客户）
	private String state;//状态（“0”表示有效，“1”表示无效）
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public String getRegisteTimeStart() {
		return registeTimeStart;
	}
	public void setRegisteTimeStart(String registeTimeStart) {
		this.registeTimeStart = registeTimeStart;
	}
	public String getRegisteTimeEnd() {
		return registeTimeEnd;
	}
	public void setRegisteTimeEnd(String registeTimeEnd) {
		this.registeTimeEnd = registeTimeEnd;
	}
	public String getUserattestationstatus() {
		return userattestationstatus;
	}
	public void setUserattestationstatus(String userattestationstatus) {
		this.userattestationstatus = userattestationstatus;
	}
	public String getBankattestationstatus() {
		return bankattestationstatus;
	}
	public void setBankattestationstatus(String bankattestationstatus) {
		this.bankattestationstatus = bankattestationstatus;
	}
	public String getOperaattestationstatus() {
		return operaattestationstatus;
	}
	public void setOperaattestationstatus(String operaattestationstatus) {
		this.operaattestationstatus = operaattestationstatus;
	}
	public String getApplynumber() {
		return applynumber;
	}
	public void setApplynumber(String applynumber) {
		this.applynumber = applynumber;
	}
	public String getApplytimestart() {
		return applytimestart;
	}
	public void setApplytimestart(String applytimestart) {
		this.applytimestart = applytimestart;
	}
	public String getApplytimeend() {
		return applytimeend;
	}
	public void setApplytimeend(String applytimeend) {
		this.applytimeend = applytimeend;
	}
	public Integer getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(Integer operatorid) {
		this.operatorid = operatorid;
	}
	public String getRegisteClient() {
		return registeClient;
	}
	public void setRegisteClient(String registeClient) {
		this.registeClient = registeClient;
	}
	public Integer getSourceid() {
		return sourceid;
	}
	public void setSourceid(Integer sourceid) {
		this.sourceid = sourceid;
	}
	public String getShareOfState() {
		return shareOfState;
	}
	public void setShareOfState(String shareOfState) {
		this.shareOfState = shareOfState;
	}
	public String getClientstatus() {
		return clientstatus;
	}
	public void setClientstatus(String clientstatus) {
		this.clientstatus = clientstatus;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
