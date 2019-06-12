package com.zhita.model.manage;

import java.util.List;

//权限表 ---第二层权限单独的实体类
public class SecondFunction {
	private String functionid;//功能id字符串
	
	private String secondLevelmenu;
	
	private String thirdLevelmenu;
	
	private List<ThirdFunction> thirdfunctionlist;
	
	public String getFunctionid() {
		return functionid;
	}
	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}
	public String getSecondLevelmenu() {
		return secondLevelmenu;
	}
	public void setSecondLevelmenu(String secondLevelmenu) {
		this.secondLevelmenu = secondLevelmenu;
	}
	public String getThirdLevelmenu() {
		return thirdLevelmenu;
	}
	public void setThirdLevelmenu(String thirdLevelmenu) {
		this.thirdLevelmenu = thirdLevelmenu;
	}
	public List<ThirdFunction> getThirdfunctionlist() {
		return thirdfunctionlist;
	}
	public void setThirdfunctionlist(List<ThirdFunction> thirdfunctionlist) {
		this.thirdfunctionlist = thirdfunctionlist;
	}
	
	
	
}
