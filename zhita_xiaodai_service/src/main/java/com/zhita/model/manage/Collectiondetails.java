package com.zhita.model.manage;

import java.math.BigDecimal;


//催收详情表
public class Collectiondetails {
	
	private Integer collection_id;//催收详情ID
	
	private String collection_time;//催收时间
	
	private Integer user_type;//用户态度
	
	private BigDecimal collectionmoney;//承诺还款金额
	
	private Integer collectionMemberId;//催收员ID
	
	private Integer orderId;//订单ID
	
	private String user_neir;//用户名称

	public Integer getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(Integer collection_id) {
		this.collection_id = collection_id;
	}

	public String getCollection_time() {
		return collection_time;
	}

	public void setCollection_time(String collection_time) {
		this.collection_time = collection_time;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public BigDecimal getCollectionmoney() {
		return collectionmoney;
	}

	public void setCollectionmoney(BigDecimal collectionmoney) {
		this.collectionmoney = collectionmoney;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCollectionMemberId() {
		return collectionMemberId;
	}

	public void setCollectionMemberId(Integer collectionMemberId) {
		this.collectionMemberId = collectionMemberId;
	}

	public String getUser_neir() {
		return user_neir;
	}

	public void setUser_neir(String user_neir) {
		this.user_neir = user_neir;
	}
	
	
	
}
