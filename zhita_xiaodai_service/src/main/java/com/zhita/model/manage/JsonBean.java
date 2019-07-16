package com.zhita.model.manage;

//用户运营商数据json串
public class JsonBean {
	private Integer error;
	private String msg;
	private WamgrJsonbean wd_api_mobilephone_getdatav2_response;
	
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public WamgrJsonbean getWd_api_mobilephone_getdatav2_response() {
		return wd_api_mobilephone_getdatav2_response;
	}
	public void setWd_api_mobilephone_getdatav2_response(WamgrJsonbean wd_api_mobilephone_getdatav2_response) {
		this.wd_api_mobilephone_getdatav2_response = wd_api_mobilephone_getdatav2_response;
	}
	
}
