package com.zhita.service.manage.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.SysUserMapper;
import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.SysUser;

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
	public Integer findphone(String newPhone, int companyId) {
		Integer id = userMapper.findphone(newPhone, companyId); // 判断该用户是否存在
		return id;
	}
	
	//admin---登录——退出登录
	public int updateLoginStatus(Integer userId){
		int num=sysUserMapper.updateLoginStatus(userId);
		return num;

	}

	@Override
	public int insertUser1(String newPhone, String loginStatus, int companyId, String registeClient,
			String registrationTime, int merchantId, String useMarket,String operatorsAuthentication) {
		 int number = userMapper.insertUser1(newPhone, loginStatus, companyId, registeClient, registrationTime, merchantId, useMarket,operatorsAuthentication);
		return number;
	}

	@Override
	public int getId(String newPhone, int companyId) {
		int id = userMapper.getId(newPhone, companyId); //获取该用户的id
		return id;
	}

	@Override
	public int updateStatus(String loginStatus, String newPhone, int companyId, String loginTime) {
		 int num = userMapper.updateStatus(loginStatus, newPhone, companyId, loginTime);
		return num;
	}

	@Override
	public String getPwd(int id) {
		String pwd = userMapper.getPwd(id);
		return pwd;
	}

	@Override
	public int updatelogOutStatus(String loginStatus, int userId, String companyId) {
		int number = userMapper.updatelogOutStatus(loginStatus, userId, companyId);
		return number;
	}

	@Override
	public int updatePwd(String newPhone, String md5Pwd, int companyId) {
		int number = userMapper.updatePwd(newPhone, md5Pwd, companyId);
		return number;
	}

	@Override
	public String getMd5pwd(String newPhone, int companyId) {
		String dataMd5Pwd = userMapper.getMd5pwd(newPhone, companyId);
		return dataMd5Pwd;
	}

	@Override
	public int setPwd(int userId, String md5Pwd) {
		 int number = userMapper.setPwd(userId, md5Pwd);
		return number;
	}

	@Override
	public String getifBlacklist(String newPhone, int companyId) {
		String ifBlacklist =userMapper.getifBlacklist(newPhone,companyId);
		return ifBlacklist;
	}

	@Override
	public void updateifBlacklist1(String newPhone, int companyId) {
		userMapper.updateifBlacklist1(newPhone, companyId);
		
	}

	@Override
	public long getnumber(long todayZeroTimestamps, long tomorrowZeroTimestamps, int companyId) {
		long number1 = userMapper.getnumber(todayZeroTimestamps,tomorrowZeroTimestamps,companyId);
		return number1;
	}


	


}
