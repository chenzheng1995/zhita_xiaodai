package com.zhita.model.manage;

import com.zhita.model.manage.Result;

public class RreturnAuth {
	
	private Integer error_code;
	
	private String reason;
	
	private Result result;

	public Integer getError_code() {
		return error_code;
	}

	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
