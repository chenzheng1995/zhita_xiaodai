package com.zhita.model.manage;

import java.math.BigDecimal;

//还款表报表实体类
public class RepayStatment {
	private String repaytime;//放款时间
	private BigDecimal paysummoney;//总计放款金额
	private int paysumcount;//总计放款人数
	private BigDecimal newpaymoney;//新客放款金额
	private int newpaycount;//新客放款人数
	private BigDecimal oldpaymoney;//老客放款金额
	private int oldpaycoun;//老客放款人数
	private int loanyetrepay;//已还人数
	private String oldborrowcvr;//复借率
	private int querypass;//当天通过人数
	private String borrowcvr;//当天借款率
	private String oldguestborrowcvr;//老客复借占比
	public String getRepaytime() {
		return repaytime;
	}
	public void setRepaytime(String repaytime) {
		this.repaytime = repaytime;
	}
	public BigDecimal getPaysummoney() {
		return paysummoney;
	}
	public void setPaysummoney(BigDecimal paysummoney) {
		this.paysummoney = paysummoney;
	}
	public int getPaysumcount() {
		return paysumcount;
	}
	public void setPaysumcount(int paysumcount) {
		this.paysumcount = paysumcount;
	}
	public BigDecimal getNewpaymoney() {
		return newpaymoney;
	}
	public void setNewpaymoney(BigDecimal newpaymoney) {
		this.newpaymoney = newpaymoney;
	}
	public int getNewpaycount() {
		return newpaycount;
	}
	public void setNewpaycount(int newpaycount) {
		this.newpaycount = newpaycount;
	}
	public BigDecimal getOldpaymoney() {
		return oldpaymoney;
	}
	public void setOldpaymoney(BigDecimal oldpaymoney) {
		this.oldpaymoney = oldpaymoney;
	}
	public int getOldpaycoun() {
		return oldpaycoun;
	}
	public void setOldpaycoun(int oldpaycoun) {
		this.oldpaycoun = oldpaycoun;
	}
	public int getLoanyetrepay() {
		return loanyetrepay;
	}
	public void setLoanyetrepay(int loanyetrepay) {
		this.loanyetrepay = loanyetrepay;
	}
	public String getOldborrowcvr() {
		return oldborrowcvr;
	}
	public void setOldborrowcvr(String oldborrowcvr) {
		this.oldborrowcvr = oldborrowcvr;
	}
	public int getQuerypass() {
		return querypass;
	}
	public void setQuerypass(int querypass) {
		this.querypass = querypass;
	}
	public String getBorrowcvr() {
		return borrowcvr;
	}
	public void setBorrowcvr(String borrowcvr) {
		this.borrowcvr = borrowcvr;
	}
	public String getOldguestborrowcvr() {
		return oldguestborrowcvr;
	}
	public void setOldguestborrowcvr(String oldguestborrowcvr) {
		this.oldguestborrowcvr = oldguestborrowcvr;
	}
	
}
