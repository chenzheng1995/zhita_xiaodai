package com.zhita.model.manage;

//短信记录里item属性下的实体类
public class MsgDataJsonBean1 {
	private String business_name;//业务类型
	private String fee;//费用
	private String receiver_phone;//对方号码
	private String send_time;//发送时间
	private String special_offer;//套餐优惠
	private String trade_addr;//短信地址
	private String trade_type;//信息类型（1：短信2：彩信3：其他）
	private String trade_way;//短信状态（1：发送2：接收3：未识别状态）
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSpecial_offer() {
		return special_offer;
	}
	public void setSpecial_offer(String special_offer) {
		this.special_offer = special_offer;
	}
	public String getTrade_addr() {
		return trade_addr;
	}
	public void setTrade_addr(String trade_addr) {
		this.trade_addr = trade_addr;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTrade_way() {
		return trade_way;
	}
	public void setTrade_way(String trade_way) {
		this.trade_way = trade_way;
	}
	
}
