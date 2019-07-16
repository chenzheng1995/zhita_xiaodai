package com.zhita.model.manage;

import java.util.List;

//短信记录
public class MsgDataJsonBean {
	private List<MsgDataJsonBean1> items;
	private String month;//月份
	private String total_size;//条数
	
	public List<MsgDataJsonBean1> getItems() {
		return items;
	}
	public void setItems(List<MsgDataJsonBean1> items) {
		this.items = items;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getTotal_size() {
		return total_size;
	}
	public void setTotal_size(String total_size) {
		this.total_size = total_size;
	}
	
	
}
