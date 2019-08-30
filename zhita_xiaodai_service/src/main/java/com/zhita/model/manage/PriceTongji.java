package com.zhita.model.manage;

import java.math.BigDecimal;

//第三方费用统计实体类
public class PriceTongji {
	private String date;//日期
	private int verificationcode;//验证码数
	private BigDecimal verificationprice;//验证码费用
	private int idcard;//身份证数
	private BigDecimal idcardprice;//身份证费用
	private int faceid;//人脸数
	private BigDecimal faceidprice;//人脸费用
	private int threeelements;//三要素
	private BigDecimal threeelementsprice;//三要素费用
	private int operator;//运营商数
	private BigDecimal operatorprice;//运营商费用
	private int riskmanagement;//风控数
	private BigDecimal riskmanagementprice;//风控费用
	private int note;//短信群发数
	private BigDecimal noteprice;//短信群发费用
	private BigDecimal sum;//总计
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getVerificationcode() {
		return verificationcode;
	}
	public void setVerificationcode(int verificationcode) {
		this.verificationcode = verificationcode;
	}
	public BigDecimal getVerificationprice() {
		return verificationprice;
	}
	public void setVerificationprice(BigDecimal verificationprice) {
		this.verificationprice = verificationprice;
	}
	public int getIdcard() {
		return idcard;
	}
	public void setIdcard(int idcard) {
		this.idcard = idcard;
	}
	public BigDecimal getIdcardprice() {
		return idcardprice;
	}
	public void setIdcardprice(BigDecimal idcardprice) {
		this.idcardprice = idcardprice;
	}
	public int getFaceid() {
		return faceid;
	}
	public void setFaceid(int faceid) {
		this.faceid = faceid;
	}
	public BigDecimal getFaceidprice() {
		return faceidprice;
	}
	public void setFaceidprice(BigDecimal faceidprice) {
		this.faceidprice = faceidprice;
	}
	public int getThreeelements() {
		return threeelements;
	}
	public void setThreeelements(int threeelements) {
		this.threeelements = threeelements;
	}
	public BigDecimal getThreeelementsprice() {
		return threeelementsprice;
	}
	public void setThreeelementsprice(BigDecimal threeelementsprice) {
		this.threeelementsprice = threeelementsprice;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public BigDecimal getOperatorprice() {
		return operatorprice;
	}
	public void setOperatorprice(BigDecimal operatorprice) {
		this.operatorprice = operatorprice;
	}
	public int getRiskmanagement() {
		return riskmanagement;
	}
	public void setRiskmanagement(int riskmanagement) {
		this.riskmanagement = riskmanagement;
	}
	public BigDecimal getRiskmanagementprice() {
		return riskmanagementprice;
	}
	public void setRiskmanagementprice(BigDecimal riskmanagementprice) {
		this.riskmanagementprice = riskmanagementprice;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public BigDecimal getNoteprice() {
		return noteprice;
	}
	public void setNoteprice(BigDecimal noteprice) {
		this.noteprice = noteprice;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	
}
