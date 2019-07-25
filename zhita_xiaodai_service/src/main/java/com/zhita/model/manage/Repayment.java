package com.zhita.model.manage;

import java.math.BigDecimal;
import java.util.List;


//还款表
public class Repayment {
	
	private Integer id;//还款ID
	
	private Integer orderid;//订单ID
	
	private BigDecimal repaymentMoney;//还款金额
	
	private String repaymentDate;//还款时间
	
	private Integer repayment_Count;//还款订单数
	
	private String pipelinenumber;//流水号
	
	private Integer thirdparty_id;//还款渠道 (支付方式 微信  支付宝  银行卡)
	
	private List<Integer> ids;
	
	private Integer repaymeny_collectiondata;//逾期还款率
	
	private Integer companyId;//公司ID
	
	private Integer collectionMemberId;//催收员ID
	
	private Integer collection_count;//逾期还款笔数
	
	private BigDecimal repaymentSumMoney;//还款总金额
	
	private BigDecimal Collection_money;//逾期还款金额
	
	private String operator_time;
	
	private BigDecimal interestPenaltySum;
	
	private BigDecimal realityAccount;
	
	private String orderCreateTime;
	
	private Integer CouNum;
	
	private String orderNumber;
	
	private String paymentbtiao;
	
	
	public String getPaymentbtiao() {
		return paymentbtiao;
	}

	public void setPaymentbtiao(String paymentbtiao) {
		this.paymentbtiao = paymentbtiao;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCouNum() {
		return CouNum;
	}

	public void setCouNum(Integer couNum) {
		CouNum = couNum;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public BigDecimal getRealityAccount() {
		return realityAccount;
	}

	public void setRealityAccount(BigDecimal realityAccount) {
		this.realityAccount = realityAccount;
	}

	public BigDecimal getInterestPenaltySum() {
		return interestPenaltySum;
	}

	public void setInterestPenaltySum(BigDecimal interestPenaltySum) {
		this.interestPenaltySum = interestPenaltySum;
	}

	public String getOperator_time() {
		return operator_time;
	}

	public void setOperator_time(String operator_time) {
		this.operator_time = operator_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(BigDecimal repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}

	public String getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public Integer getRepayment_Count() {
		return repayment_Count;
	}

	public void setRepayment_Count(Integer repayment_Count) {
		this.repayment_Count = repayment_Count;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getCollectionMemberId() {
		return collectionMemberId;
	}

	public void setCollectionMemberId(Integer collectionMemberId) {
		this.collectionMemberId = collectionMemberId;
	}

	public Integer getCollection_count() {
		return collection_count;
	}

	public void setCollection_count(Integer collection_count) {
		this.collection_count = collection_count;
	}

	public BigDecimal getRepaymentSumMoney() {
		return repaymentSumMoney;
	}

	public void setRepaymentSumMoney(BigDecimal repaymentSumMoney) {
		this.repaymentSumMoney = repaymentSumMoney;
	}

	public Integer getRepaymeny_collectiondata() {
		return repaymeny_collectiondata;
	}

	public void setRepaymeny_collectiondata(Integer repaymeny_collectiondata) {
		this.repaymeny_collectiondata = repaymeny_collectiondata;
	}

	public BigDecimal getCollection_money() {
		return Collection_money;
	}

	public void setCollection_money(BigDecimal collection_money) {
		Collection_money = collection_money;
	}

	public String getPipelinenumber() {
		return pipelinenumber;
	}

	public void setPipelinenumber(String pipelinenumber) {
		this.pipelinenumber = pipelinenumber;
	}

	public Integer getThirdparty_id() {
		return thirdparty_id;
	}

	public void setThirdparty_id(Integer thirdparty_id) {
		this.thirdparty_id = thirdparty_id;
	}
	
	
	
}
