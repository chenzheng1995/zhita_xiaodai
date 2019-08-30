package com.zhita.model.manage;


//逾期前催收详情
public class Overdue {
	
	private Integer overdue_id;//催收ID
	
	private String overdue_phonestaus;//电话状态( 已接通   未接通)
	
	private Integer collectionMemberId;//催收员ID
	
	private String collectiondate;//分配催收时间
	
	private Integer orderId;//订单ID
	
	private String statu;
	
	private String deled;

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getDeled() {
		return deled;
	}

	public void setDeled(String deled) {
		this.deled = deled;
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

	public Integer getCollectionMemberId() {
		return collectionMemberId;
	}

	public void setCollectionMemberId(Integer collectionMemberId) {
		this.collectionMemberId = collectionMemberId;
	}

	public String getCollectiondate() {
		return collectiondate;
	}

	public void setCollectiondate(String collectiondate) {
		this.collectiondate = collectiondate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
}

