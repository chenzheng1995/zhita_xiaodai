package com.zhita.model.manage;

//系统用户角色关联表
public class SysuserRole {
    private Integer id;//主键id

    private Integer userid;//系统用户id

    private Integer roleid;//角色id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }
}