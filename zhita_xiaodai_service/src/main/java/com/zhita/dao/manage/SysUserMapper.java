package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.Role;
import com.zhita.model.manage.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer userid);
    
    //admin-----系统用户——添加功能（先查询出所有角色）
    List<Role> queryAllRole();
    
    //admin-----系统用户——添加功能（先查询出所有公司）
    List<Company> queryAllCompany();
    
    //admin------系统用户——添加功能
    int insert(SysUser record);
    
    //admin------系统用户——添加功能（用户选择了角色，需要向中间表插入数据）
    int insertRole(Integer userid,Integer roleid);

    int insertSelective(SysUser record);
    
    //admin----系统用户——修改功能(通过主键id查询对象)
    SysUser selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(SysUser record);
    
    //admin----系统用户——修改功能(修改保存)
    int updateByPrimaryKey(SysUser record);
    
    //admin----系统用户——修改功能(查询出当前用户在中间表的id集合)
    List<Integer> queryMiddleId(Integer userid);
    
    //admin----系统用户——修改功能(删除当前用户在中间表的数据)
    int delMiddleId(Integer userid);
    
    //admin------系统用户——列表展示
    List<SysUser> queryAll(Integer companyId);
    
    //admin------系统用户——模糊查询（账号和账号状态）
    List<SysUser> queryAllByLike(@Param("companyId") Integer companyId,@Param("account") String account,@Param("status") String status);
    
    //admin-----系统用户——设置账号状态为开启
    int upaStatusOpen(Integer id);
    
    //admin-----系统用户——设置账号状态为关闭
    int upaStatusClose(Integer id);
    
 /*   //admin-----登录——查询该用户   账号是否存在
    SysUser queryByAccount(String account);*/
    
    //admin---系统用户——修改假删除状态
    int upaFalseDel(Integer id);
    
    //admin----登录——查询该用户   账号密码是否正确
    SysUser queryByAccountAndPwd(String account,String pwd);
    
    //admin---登录——修改该用户的登录状态  登录时间(通过账号和密码)
    int updateByAccountAndPwd(String loginstatus,String logintime,String account,String pwd);
    
    //admin----登录——通过手机号查询是否存在该用户
    SysUser queryByPhone(String phone);
    
    //admin---登录——修改该用户的登录状态  登录时间（通过手机号）
    int updateByPhone(String loginstatus,String logintime,String phone);
    
    //admin---登录——查询当前用户角色（通过账号和密码）
    List<String> queryRoleByAccountAndPwd(String account,String pwd);
    
    //admin---登录——查询当前用户权限id（通过账号和密码）
    List<Integer> queryFunctionsByAccountAndPwd(String account,String pwd);
    
    //admin---登录——查询当前用户角色（通过手机号）
    List<String> queryRoleByPhone(String phone);
    
    //admin---登录——查询当前用户权限id（通过手机号）
    List<Integer> queryFunctionsByPhone(String phone);
    
    //admin---登录——退出登录
    int updateLoginStatus(Integer userId);
    
   
    

}