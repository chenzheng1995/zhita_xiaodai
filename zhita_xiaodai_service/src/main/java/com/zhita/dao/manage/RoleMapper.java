package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.Functions;
import com.zhita.model.manage.Role;
import com.zhita.model.manage.SecondFunction;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleid);
    
    //admin------角色——添加功能（通过权限id查询出对应权限表的三级菜单）
    String queryThirdmenuByFunctionId(Integer functionid);
    
    //admin------角色——添加功能（通过一级菜单查询二级菜单和三级菜单）
    List<SecondFunction> queryFunctionByFirstmenu(String firstlevelmenu);
    
    //admin------角色——添加功能（通过一级菜单进行分组  查询出functions表一共有几个模块的一级菜单）
    List<Functions> queryAllFunctions();
    
    //admin-----角色——添加功能（查询权限表所有信息）
    List<Functions> queryAllfun();
    
    //admin------角色——添加功能
    int insert(Role record);
    
    //admin-----角色——用户选择了权限 需要向中间表插入数据
    int insertFunction(Integer roleid,Integer functionid);

    int insertSelective(Role record);
    
    //admin-----角色——编辑功能（通过角色id查询角色对象）
    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);
    
    //admin----角色——更新保存功能
    int updateByPrimaryKey(Role record);
    
    //admin----角色——更新保存功能（查询出当前角色在中间表的id集合）
    List<Integer> queryMiddleId(Integer roleid);
    
    //admin----角色——更新保存功能(删除当前角色在中间表的数据)
    int delMiddleId(Integer roleid);
    
    //admin------角色——列表展示
    List<Role> queryAll();
    
    //admin------角色——模糊查询（角色状态）
    List<Role> queryAllByLike(String status);
    
    //admin------角色——查看权限功能（通过一级菜单查询二级菜单和三级菜单）
    List<SecondFunction> queryFunctionByFirstmenuAndRoleid(String firstlevelmenu,Integer roleid);
    
    //admin------角色——查看权限功能（通过一级菜单进行分组  查询出functions表一共有几个模块的一级菜单）
    List<Functions> queryFunctionByRoleid(Integer roleid);
    
    //admin-----角色——添加功能（根据角色id查询该角色的权限信息）
    List<Functions> queryAllfunByRoleid(Integer roleid);
}