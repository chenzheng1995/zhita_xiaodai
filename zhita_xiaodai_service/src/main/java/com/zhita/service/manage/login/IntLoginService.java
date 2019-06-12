package com.zhita.service.manage.login;

import java.util.List;

import com.zhita.model.manage.SysUser;

public interface IntLoginService {
	
	//admin-----登录——查询该用户   账号是否存在
/*    public SysUser queryByAccount(String account);*/
    
   //admin----登录——查询该用户   账号密码是否正确
   public SysUser queryByAccountAndPwd(String account,String pwd);
   
   //admin---登录——修改该用户的登录状态  登录时间(通过账号和密码)
   public int updateByAccountAndPwd(String loginstatus,String logintime,String account,String pwd);
   
   //admin----登录——通过手机号查询是否存在该用户
   public SysUser queryByPhone(String phone);
   
   //admin---登录——修改该用户的登录状态  登录时间（通过手机号）
   public int updateByPhone(String loginstatus,String logintime,String phone);
   
   //admin---登录——查询当前用户角色（通过账号和密码）
   public List<String> queryRoleByAccountAndPwd(String account,String pwd);
   
   //admin---登录——查询当前用户权限id（通过账号和密码）
   public List<Integer> queryFunctionsByAccountAndPwd(String account,String pwd);
   
   //admin---登录——查询当前用户角色（通过手机号）
   public List<String> queryRoleByPhone(String phone);
   
   //admin---登录——查询当前用户权限id（通过手机号）
   public List<Integer> queryFunctionsByPhone(String phone);
   
}
