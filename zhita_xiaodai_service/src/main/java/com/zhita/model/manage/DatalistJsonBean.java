package com.zhita.model.manage;

import java.util.List;

public class DatalistJsonBean {
	//private List<> billdata;
	//private List<> familydata;
	//private        monthinfo;
	private List<MsgDataJsonBean> msgdata;//短信记录
	//private List<> netdata;
	//private List<> netlogdata;
	//private List<> rechargedata;
	private List<TelDataJsonBean> teldata;//通话时间记录
	//private        userdata;
	public List<MsgDataJsonBean> getMsgdata() {
		return msgdata;
	}
	public void setMsgdata(List<MsgDataJsonBean> msgdata) {
		this.msgdata = msgdata;
	}
	public List<TelDataJsonBean> getTeldata() {
		return teldata;
	}
	public void setTeldata(List<TelDataJsonBean> teldata) {
		this.teldata = teldata;
	}

}
