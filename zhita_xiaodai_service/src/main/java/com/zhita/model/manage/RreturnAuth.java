package com.zhita.model.manage;

import com.zhita.model.manage.Result;

public class RreturnAuth {
	
	private Integer Code;
	
	private String CodeDetail;
	
	private Result Msg;
	
	private String Data;

	public Integer getCode() {
		return Code;
	}

	public void setCode(Integer code) {
		Code = code;
	}

	public String getCodeDetail() {
		return CodeDetail;
	}

	public void setCodeDetail(String codeDetail) {
		CodeDetail = codeDetail;
	}

	public Result getMsg() {
		return Msg;
	}

	public void setMsg(Result msg) {
		Msg = msg;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	
	
}
