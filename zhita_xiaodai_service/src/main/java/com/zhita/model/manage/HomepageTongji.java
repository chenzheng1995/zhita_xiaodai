package com.zhita.model.manage;

import java.math.BigDecimal;

//回收率报表所需字段实体类
public class HomepageTongji {
	private String shouldtime;//应还日期
	private Integer shouldorder;//应还订单
	private Integer overduenotrepay;//逾前未还
	private Integer overduerepay;//逾前已还
	private Integer overdueafternotrepay;//逾后未还
	private Integer overdueafterrepay;//逾后已还
	private Integer baddebt;//已坏账
	private BigDecimal shouldmoney;//应还金额
	private BigDecimal realymoney;//实际还款金额
	private BigDecimal deferredmoney;//延期费
	private BigDecimal overduemoney;//逾期费
	private BigDecimal deratemoney;//减免金额
	private BigDecimal bankdeduction;//银行扣款金额
	private BigDecimal tobepaid;//待还金额
	private String overduecvr;//逾期率
	private String recovery;//回收率
	
	
	public String getShouldtime() {
		return shouldtime;
	}
	public void setShouldtime(String shouldtime) {
		this.shouldtime = shouldtime;
	}
	public Integer getShouldorder() {
		return shouldorder;
	}
	public void setShouldorder(Integer shouldorder) {
		this.shouldorder = shouldorder;
	}
	public Integer getOverduenotrepay() {
		return overduenotrepay;
	}
	public void setOverduenotrepay(Integer overduenotrepay) {
		this.overduenotrepay = overduenotrepay;
	}
	public Integer getOverduerepay() {
		return overduerepay;
	}
	public void setOverduerepay(Integer overduerepay) {
		this.overduerepay = overduerepay;
	}
	public Integer getOverdueafternotrepay() {
		return overdueafternotrepay;
	}
	public void setOverdueafternotrepay(Integer overdueafternotrepay) {
		this.overdueafternotrepay = overdueafternotrepay;
	}
	public Integer getOverdueafterrepay() {
		return overdueafterrepay;
	}
	public void setOverdueafterrepay(Integer overdueafterrepay) {
		this.overdueafterrepay = overdueafterrepay;
	}
	public Integer getBaddebt() {
		return baddebt;
	}
	public void setBaddebt(Integer baddebt) {
		this.baddebt = baddebt;
	}
	public BigDecimal getShouldmoney() {
		return shouldmoney;
	}
	public void setShouldmoney(BigDecimal shouldmoney) {
		this.shouldmoney = shouldmoney;
	}
	public BigDecimal getRealymoney() {
		return realymoney;
	}
	public void setRealymoney(BigDecimal realymoney) {
		this.realymoney = realymoney;
	}
	public BigDecimal getDeferredmoney() {
		return deferredmoney;
	}
	public void setDeferredmoney(BigDecimal deferredmoney) {
		this.deferredmoney = deferredmoney;
	}
	public BigDecimal getOverduemoney() {
		return overduemoney;
	}
	public void setOverduemoney(BigDecimal overduemoney) {
		this.overduemoney = overduemoney;
	}
	public BigDecimal getDeratemoney() {
		return deratemoney;
	}
	public void setDeratemoney(BigDecimal deratemoney) {
		this.deratemoney = deratemoney;
	}
	public BigDecimal getBankdeduction() {
		return bankdeduction;
	}
	public void setBankdeduction(BigDecimal bankdeduction) {
		this.bankdeduction = bankdeduction;
	}
	public BigDecimal getTobepaid() {
		return tobepaid;
	}
	public void setTobepaid(BigDecimal tobepaid) {
		this.tobepaid = tobepaid;
	}
	public String getOverduecvr() {
		return overduecvr;
	}
	public void setOverduecvr(String overduecvr) {
		this.overduecvr = overduecvr;
	}
	public String getRecovery() {
		return recovery;
	}
	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}

}
