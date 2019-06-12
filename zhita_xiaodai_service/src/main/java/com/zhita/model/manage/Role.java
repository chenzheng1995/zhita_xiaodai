package com.zhita.model.manage;

import java.util.List;

//角色表
public class Role {
    private Integer roleid;//角色id

    private String rolename;//角色名称

    private String roledescribe;//角色描述

    private String status;//角色状态（1：开启2：关闭）
    
    private List<Functions> listfunctions;//一个角色有多个权限
    
    private String listfunctionIdString;//做添加操作时   存权限id的字符串

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getRoledescribe() {
        return roledescribe;
    }

    public void setRoledescribe(String roledescribe) {
        this.roledescribe = roledescribe == null ? null : roledescribe.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public List<Functions> getListfunctions() {
		return listfunctions;
	}

	public void setListfunctions(List<Functions> listfunctions) {
		this.listfunctions = listfunctions;
	}

	public String getListfunctionIdString() {
		return listfunctionIdString;
	}

	public void setListfunctionIdString(String listfunctionIdString) {
		this.listfunctionIdString = listfunctionIdString;
	}
    
}