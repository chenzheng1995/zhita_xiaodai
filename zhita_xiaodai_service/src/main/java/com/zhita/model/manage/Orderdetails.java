package com.zhita.model.manage;

import java.math.BigDecimal;
import java.util.List;


//订单明细表
public class Orderdetails {
	
	private Integer id;//明细ID
	
	private Integer orderId;//订单表ID
	
	private BigDecimal realityBorrowMoney;//实际借款金额（元）
	
	private BigDecimal makeLoans;//放款金额(元)
	
	private BigDecimal interestDay;//期内日均利息（0.06%）
	
	private BigDecimal interestSum;//期内总利息（元）
	
	private String overdueNumberOfDays;//逾期天数
	
	private BigDecimal interestPenaltyDay;//日均罚息（0.1%）
	
	private BigDecimal interestPenaltySum;//逾期总罚息（元）
	
	private BigDecimal interestInAll;//总利息（元）
	
	private BigDecimal shouldReapyMoney;//期限内应还金额（元）
	
	private BigDecimal technicalServiceMoney;//技术服务费
	
	private BigDecimal realityAccount;//实际到账
	
	private String rejectMonadCause;//拒单原因
	
	private String DrainageOfPlatform;//引流平台
	
	private String registerClient;//注册客户端
	
	
	
	
	private String borrowMoneyState;//还款状态（已还和未还）
	
	private String name;//姓名
	
	private String phone;//手机号
	
	private String realtime;//实还时间
	
	private String borrowingtime;//实借时间
	
	private String returntime;//应还时间
	
	private Integer page=0;
	
	private Integer pagesize=10;
	
    private int totalCount;     //总记录数
    
    private int totalPageCount;      //总页数
    
    private String borrowMoneyType;//贷款方式
    
    private String grade;//催收等级
    
    private String collectionStatus;//催收状态
    
    private Integer collectionMemberId;//催收员ID
    
    private String start_time;//开始时间
    
    private String end_time;//结束时间
    
    private String orderNumber;//订单号
    
    private String reallyName;//催收人名称
    
    private String collectionTime;//催收时间
    
    private Integer borrowTimeLimit;//借款期限
    
    private String borrowMoneyWay;//'贷款方式（立即贷和分期贷）',
    
    private String deferBeforeReturntime;//延期前应还时间
    
    private String deferAfterReturntime;//延期后应还时间
    
    private String orderCreateTime;//实借时间
    
    private Integer companyId;//公司ID
    
    private Integer describe;//逾期等级对应的逾期天数
    
    private String shouldReturnTime;//应还时间
    
    private String pipelinenumber;//流水号
    
    private List<Integer> ids;
    
    private String collectiondate;
    
    private Integer overdue_id;
    
    private String overdue_phonestaus;
    
    private Integer collection_count;
    
    private String riskManagemenType;//风控类型（通过已借款 通过未借款 成功 拒绝四种方式）
    
    private Integer  Numberofreminders;//催收笔数
    
    private Integer NumberCollection;//催收次数
    
    private Integer CollectionSuccess;//催收成功数
    
    private Integer Baddebt;//坏账数
    
    private Integer CollectionSuccessdata;//催收成功率
    
    private String accounttime;//调账时间

    private String repaymentSource;//还款渠道
    
    private String statu;//状态
    
    private BigDecimal surplus_money;//剩余金额
    
    private Integer onceDeferredDay;//每次延期天数
    
    private String canDeferNumberoftime;//延期天数
    
    private BigDecimal interestOnArrears;//延期利息
    
    private BigDecimal interMoney;//延期金额
    
    private BigDecimal OrderSum_money;//实还金额
    
    private Integer phone_num;//电话催收次数
    
    private Integer whid;//白名单标识
    
    private Integer blaid;//黑名单标识
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getRealityBorrowMoney() {
		return realityBorrowMoney;
	}

	public void setRealityBorrowMoney(BigDecimal realityBorrowMoney) {
		this.realityBorrowMoney = realityBorrowMoney;
	}

	public BigDecimal getMakeLoans() {
		return makeLoans;
	}

	public void setMakeLoans(BigDecimal makeLoans) {
		this.makeLoans = makeLoans;
	}

	public BigDecimal getInterestDay() {
		return interestDay;
	}

	public void setInterestDay(BigDecimal interestDay) {
		this.interestDay = interestDay;
	}

	public BigDecimal getInterestSum() {
		return interestSum;
	}

	public void setInterestSum(BigDecimal interestSum) {
		this.interestSum = interestSum;
	}

	public String getOverdueNumberOfDays() {
		return overdueNumberOfDays;
	}

	public void setOverdueNumberOfDays(String overdueNumberOfDays) {
		this.overdueNumberOfDays = overdueNumberOfDays;
	}

	public BigDecimal getInterestPenaltyDay() {
		return interestPenaltyDay;
	}

	public void setInterestPenaltyDay(BigDecimal interestPenaltyDay) {
		this.interestPenaltyDay = interestPenaltyDay;
	}

	public BigDecimal getInterestPenaltySum() {
		return interestPenaltySum;
	}

	public void setInterestPenaltySum(BigDecimal interestPenaltySum) {
		this.interestPenaltySum = interestPenaltySum;
	}

	public BigDecimal getInterestInAll() {
		return interestInAll;
	}

	public void setInterestInAll(BigDecimal interestInAll) {
		this.interestInAll = interestInAll;
	}

	public BigDecimal getShouldReapyMoney() {
		return shouldReapyMoney;
	}

	public void setShouldReapyMoney(BigDecimal shouldReapyMoney) {
		this.shouldReapyMoney = shouldReapyMoney;
	}

	public BigDecimal getTechnicalServiceMoney() {
		return technicalServiceMoney;
	}

	public void setTechnicalServiceMoney(BigDecimal technicalServiceMoney) {
		this.technicalServiceMoney = technicalServiceMoney;
	}

	public String getRejectMonadCause() {
		return rejectMonadCause;
	}

	public void setRejectMonadCause(String rejectMonadCause) {
		this.rejectMonadCause = rejectMonadCause;
	}

	public String getDrainageOfPlatform() {
		return DrainageOfPlatform;
	}

	public void setDrainageOfPlatform(String drainageOfPlatform) {
		DrainageOfPlatform = drainageOfPlatform;
	}

	public String getRegisterClient() {
		return registerClient;
	}

	public void setRegisterClient(String registerClient) {
		this.registerClient = registerClient;
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

	public String getRealtime() {
		return realtime;
	}

	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}

	public String getBorrowingtime() {
		return borrowingtime;
	}

	public void setBorrowingtime(String borrowingtime) {
		this.borrowingtime = borrowingtime;
	}

	public String getReturntime() {
		return returntime;
	}

	public void setReturntime(String returntime) {
		this.returntime = returntime;
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public String getBorrowMoneyType() {
		return borrowMoneyType;
	}

	public void setBorrowMoneyType(String borrowMoneyType) {
		this.borrowMoneyType = borrowMoneyType;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(String collectionStatus) {
		this.collectionStatus = collectionStatus;
	}

	public Integer getCollectionMemberId() {
		return collectionMemberId;
	}

	public void setCollectionMemberId(Integer collectionMemberId) {
		this.collectionMemberId = collectionMemberId;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getReallyName() {
		return reallyName;
	}

	public void setReallyName(String reallyName) {
		this.reallyName = reallyName;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public Integer getBorrowTimeLimit() {
		return borrowTimeLimit;
	}

	public void setBorrowTimeLimit(Integer borrowTimeLimit) {
		this.borrowTimeLimit = borrowTimeLimit;
	}

	public String getBorrowMoneyWay() {
		return borrowMoneyWay;
	}

	public void setBorrowMoneyWay(String borrowMoneyWay) {
		this.borrowMoneyWay = borrowMoneyWay;
	}

	public String getDeferBeforeReturntime() {
		return deferBeforeReturntime;
	}

	public void setDeferBeforeReturntime(String deferBeforeReturntime) {
		this.deferBeforeReturntime = deferBeforeReturntime;
	}

	public String getDeferAfterReturntime() {
		return deferAfterReturntime;
	}

	public void setDeferAfterReturntime(String deferAfterReturntime) {
		this.deferAfterReturntime = deferAfterReturntime;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getDescribe() {
		return describe;
	}

	public void setDescribe(Integer describe) {
		this.describe = describe;
	}

	public String getShouldReturnTime() {
		return shouldReturnTime;
	}

	public void setShouldReturnTime(String shouldReturnTime) {
		this.shouldReturnTime = shouldReturnTime;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getOverdue_id() {
		return overdue_id;
	}

	public void setOverdue_id(Integer overdue_id) {
		this.overdue_id = overdue_id;
	}

	public String getOverdue_phonestaus() {
		return overdue_phonestaus;
	}

	public void setOverdue_phonestaus(String overdue_phonestaus) {
		this.overdue_phonestaus = overdue_phonestaus;
	}

	public Integer getCollection_count() {
		return collection_count;
	}

	public void setCollection_count(Integer collection_count) {
		this.collection_count = collection_count;
	}

	public String getRiskManagemenType() {
		return riskManagemenType;
	}

	public void setRiskManagemenType(String riskManagemenType) {
		this.riskManagemenType = riskManagemenType;
	}

	public Integer getNumberofreminders() {
		return Numberofreminders;
	}

	public void setNumberofreminders(Integer numberofreminders) {
		Numberofreminders = numberofreminders;
	}

	public Integer getNumberCollection() {
		return NumberCollection;
	}

	public void setNumberCollection(Integer numberCollection) {
		NumberCollection = numberCollection;
	}

	public Integer getCollectionSuccess() {
		return CollectionSuccess;
	}

	public void setCollectionSuccess(Integer collectionSuccess) {
		CollectionSuccess = collectionSuccess;
	}

	public Integer getBaddebt() {
		return Baddebt;
	}

	public void setBaddebt(Integer baddebt) {
		Baddebt = baddebt;
	}

	public Integer getCollectionSuccessdata() {
		return CollectionSuccessdata;
	}

	public void setCollectionSuccessdata(Integer collectionSuccessdata) {
		CollectionSuccessdata = collectionSuccessdata;
	}

	public String getPipelinenumber() {
		return pipelinenumber;
	}

	public void setPipelinenumber(String pipelinenumber) {
		this.pipelinenumber = pipelinenumber;
	}

	public String getAccounttime() {
		return accounttime;
	}

	public void setAccounttime(String accounttime) {
		this.accounttime = accounttime;
	}

	public String getRepaymentSource() {
		return repaymentSource;
	}

	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource = repaymentSource;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getCollectiondate() {
		return collectiondate;
	}

	public void setCollectiondate(String collectiondate) {
		this.collectiondate = collectiondate;
	}
	
	public BigDecimal getSurplus_money() {
		return surplus_money;
	}

	public void setSurplus_money(BigDecimal surplus_money) {
		this.surplus_money = surplus_money;
	}

	public BigDecimal getRealityAccount() {
		return realityAccount;
	}

	public void setRealityAccount(BigDecimal realityAccount) {
		this.realityAccount = realityAccount;
	}

	public Integer getOnceDeferredDay() {
		return onceDeferredDay;
	}

	public void setOnceDeferredDay(Integer onceDeferredDay) {
		this.onceDeferredDay = onceDeferredDay;
	}

	public String getCanDeferNumberoftime() {
		return canDeferNumberoftime;
	}

	public void setCanDeferNumberoftime(String canDeferNumberoftime) {
		this.canDeferNumberoftime = canDeferNumberoftime;
	}

	public BigDecimal getInterestOnArrears() {
		return interestOnArrears;
	}

	public void setInterestOnArrears(BigDecimal interestOnArrears) {
		this.interestOnArrears = interestOnArrears;
	}

	public BigDecimal getInterMoney() {
		return interMoney;
	}

	public void setInterMoney(BigDecimal interMoney) {
		this.interMoney = interMoney;
	}

	public BigDecimal getOrderSum_money() {
		return OrderSum_money;
	}

	public void setOrderSum_money(BigDecimal orderSum_money) {
		OrderSum_money = orderSum_money;
	}

	public Integer getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(Integer phone_num) {
		this.phone_num = phone_num;
	}

	public Integer getWhid() {
		return whid;
	}

	public void setWhid(Integer whid) {
		this.whid = whid;
	}

	public Integer getBlaid() {
		return blaid;
	}

	public void setBlaid(Integer blaid) {
		this.blaid = blaid;
	}
	
	
	
}
