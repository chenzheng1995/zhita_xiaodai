package com.zhita.model.manage;


//第三方支付返回
public class ReturnChanpay {
	
	private String AcceptStatus;//返回状态
	
	private String TrxId;//商户网站唯一订单号
	
	private String OrderTrxid;//畅捷流水号
	
	private String InstUrl;//跳转地址
	
	private String Status;//S成功 F失败 P 处理中
	
	private String AppRetMsg;//返回内容
	
	private String AppRetcode;
	
	private String InputCharset;//编码格式
	
	private String PartnerId;
	
	private String RetCode;
	
	private String RetMsg;//操作状态
	
	private String Sign;
	
	private String SignType;
	
	private String TradeDate;//操作年份
	
	private String TradeTime;

	public String getTradeTime() {
		return TradeTime;
	}

	public void setTradeTime(String tradeTime) {
		TradeTime = tradeTime;
	}

	public String getAcceptStatus() {
		return AcceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		AcceptStatus = acceptStatus;
	}

	public String getTrxId() {
		return TrxId;
	}

	public void setTrxId(String trxId) {
		TrxId = trxId;
	}

	public String getOrderTrxid() {
		return OrderTrxid;
	}

	public void setOrderTrxid(String orderTrxid) {
		OrderTrxid = orderTrxid;
	}

	public String getInstUrl() {
		return InstUrl;
	}

	public void setInstUrl(String instUrl) {
		InstUrl = instUrl;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getAppRetMsg() {
		return AppRetMsg;
	}

	public void setAppRetMsg(String appRetMsg) {
		AppRetMsg = appRetMsg;
	}

	public String getAppRetcode() {
		return AppRetcode;
	}

	public void setAppRetcode(String appRetcode) {
		AppRetcode = appRetcode;
	}

	public String getInputCharset() {
		return InputCharset;
	}

	public void setInputCharset(String inputCharset) {
		InputCharset = inputCharset;
	}

	public String getPartnerId() {
		return PartnerId;
	}

	public void setPartnerId(String partnerId) {
		PartnerId = partnerId;
	}

	public String getRetCode() {
		return RetCode;
	}

	public void setRetCode(String retCode) {
		RetCode = retCode;
	}

	public String getRetMsg() {
		return RetMsg;
	}

	public void setRetMsg(String retMsg) {
		RetMsg = retMsg;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getSignType() {
		return SignType;
	}

	public void setSignType(String signType) {
		SignType = signType;
	}

	public String getTradeDate() {
		return TradeDate;
	}

	public void setTradeDate(String tradeDate) {
		TradeDate = tradeDate;
	}
	
	
	
	
}
