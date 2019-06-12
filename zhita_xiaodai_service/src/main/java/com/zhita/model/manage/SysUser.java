package com.zhita.model.manage;

import java.util.List;

//系统用户表
public class SysUser {
    private Integer userid;//系统用户id

    private String companyid;//公司id

    private String account;//账号

    private String pwd;//密码

    private String phone;//手机号

    private String loginstate;//登录状态（1：登陆； 0：未登陆）

    private String logintime;//登录时间

    private String status;//账号状态（1：开启；2：关闭）
    
    private List<Role> listrole;//一个用户有多个角色
    
    private String listRoleIdString;//做添加操作时  存角色id的字符串

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getLoginstate() {
        return loginstate;
    }

    public void setLoginstate(String loginstate) {
        this.loginstate = loginstate == null ? null : loginstate.trim();
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime == null ? null : logintime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public List<Role> getListrole() {
		return listrole;
	}

	public void setListrole(List<Role> listrole) {
		this.listrole = listrole;
	}

	public String getListRoleIdString() {
		return listRoleIdString;
	}

	public void setListRoleIdString(String listRoleIdString) {
		this.listRoleIdString = listRoleIdString;
	}
    
}