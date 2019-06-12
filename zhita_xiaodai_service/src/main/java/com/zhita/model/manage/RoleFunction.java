package com.zhita.model.manage;

//角色功能关联表
public class RoleFunction {
    private Integer id;//主键id

    private Integer roleid;//角色id

    private Integer functionid;//功能id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getFunctionid() {
        return functionid;
    }

    public void setFunctionid(Integer functionid) {
        this.functionid = functionid;
    }
}