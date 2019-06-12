package com.zhita.model.manage;

import java.util.List;

//功能表
public class Functions {
	private Integer id;
	
    private String firstlevelmenu;//一级菜单

    private String secondlevelmenu;//二级菜单

    private String thirdlevelmenu;//三级菜单

    private String status;//功能状态（1：开启2：关闭）
    
    private List<SecondFunction> secondfunctionlist;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstlevelmenu() {
		return firstlevelmenu;
	}

	public void setFirstlevelmenu(String firstlevelmenu) {
		this.firstlevelmenu = firstlevelmenu;
	}

	public String getSecondlevelmenu() {
		return secondlevelmenu;
	}

	public void setSecondlevelmenu(String secondlevelmenu) {
		this.secondlevelmenu = secondlevelmenu;
	}

	public String getThirdlevelmenu() {
		return thirdlevelmenu;
	}

	public void setThirdlevelmenu(String thirdlevelmenu) {
		this.thirdlevelmenu = thirdlevelmenu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SecondFunction> getSecondfunctionlist() {
		return secondfunctionlist;
	}

	public void setSecondfunctionlist(List<SecondFunction> secondfunctionlist) {
		this.secondfunctionlist = secondfunctionlist;
	}
    
}