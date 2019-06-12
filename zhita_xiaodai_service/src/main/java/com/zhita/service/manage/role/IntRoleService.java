package com.zhita.service.manage.role;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Functions;
import com.zhita.model.manage.Role;

public interface IntRoleService {
	//admin------角色——列表展示
    public Map<String,Object> queryAll(Integer page);
    
    //admin------角色——模糊查询（角色状态）
    public Map<String,Object> queryAllByLike(String status,Integer page);
    
    //admin------角色——添加功能（先查询出所有的权限）
    public List<Functions> queryAllFunctions();
    
    //admin------角色——添加功能
    public int insert(Role record);
    
    //admin------角色——查看权限功能
    public List<Functions> queryFunctionsByRoleid(Integer roleid);
    
    //admin-----角色——编辑功能（通过角色id查询角色对象）
    public Map<String,Object> editByRoleid(Integer roleid);
    
    //admin----角色——更新保存功能
    public  int updateByPrimaryKey(Role record);
}
