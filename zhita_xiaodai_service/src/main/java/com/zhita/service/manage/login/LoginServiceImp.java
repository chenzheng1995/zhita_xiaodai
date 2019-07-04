package com.zhita.service.manage.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.SysUserMapper;
import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.SysUser;
import com.zhita.model.manage.User;

@Service
public class LoginServiceImp implements IntLoginService{
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	UserMapper userMapper;
	
	
	//admin-----登录——查询该用户   账号是否存在
	//@Override
	/*public SysUser queryByAccount(String account) {
		SysUser sysUser=sysUserMapper.queryByAccount(account);
		return sysUser;
	}*/

	//admin----登录——查询该用户   账号密码是否正确
	@Override
	public SysUser queryByAccountAndPwd(String account, String pwd) {
		SysUser sysUser=sysUserMapper.queryByAccountAndPwd(account, pwd);
		return sysUser;
	}
	
	//admin---登录——修改该用户的登录状态  登录时间(通过账号和密码)
	@Override
	public int updateByAccountAndPwd(String loginstatus, String logintime, String account, String pwd) {
		int sum=sysUserMapper.updateByAccountAndPwd(loginstatus, logintime, account, pwd);
		return sum;
	}
	
	//admin----登录——通过手机号查询是否存在该用户
	@Override
	public SysUser queryByPhone(String phone) {
		SysUser sysUser=sysUserMapper.queryByPhone(phone);
		return sysUser;
	}
	
	//admin---登录——修改该用户的登录状态  登录时间（通过手机号）
	public int updateByPhone(String loginstatus,String logintime,String phone){
		int sum=sysUserMapper.updateByPhone(loginstatus, logintime, phone);
		return sum;
	}
	
	//admin---登录——查询当前用户角色（通过账号和密码）
	@Override
	public List<String> queryRoleByAccountAndPwd(String account, String pwd) {
		List<String> list=sysUserMapper.queryRoleByAccountAndPwd(account, pwd);
		return list;
	}
	
	//admin---登录——查询当前用户权限id（通过账号和密码）
	@Override
	public List<Integer> queryFunctionsByAccountAndPwd(String account,String pwd){
		List<Integer> list=sysUserMapper.queryFunctionsByAccountAndPwd(account, pwd);
		return list;
	}
	
	//admin---登录——查询当前用户角色（通过手机号）
	@Override
	public List<String> queryRoleByPhone(String phone){
		List<String> list=sysUserMapper.queryRoleByPhone(phone);
		return list;
	}
	   
	//admin---登录——查询当前用户权限id（通过手机号）
	@Override
	public List<Integer> queryFunctionsByPhone(String phone){
		List<Integer> list=sysUserMapper.queryFunctionsByPhone(phone);
		return list;
	}

	@Override
	public User findphone(String newPhone, String companyId) {
		User user = userMapper.findphone(newPhone, companyId); // 判断该用户是否存在
		return user;
	}
}
