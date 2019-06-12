package com.zhita.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.controller.shiro.PhoneToken;
import com.zhita.model.manage.SysUser;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.SMSUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private IntLoginService intLoginService;
	
	
	
	//后台管理----登录验证  以及授权(用户名和密码)
	@ResponseBody
	@RequestMapping(value="/loginap")
	public Map<String, Object> loginap(String account,String pwd,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)) {
			map.put("msg", "用户名或密码不能为空");
		}else {
			UsernamePasswordToken token=new UsernamePasswordToken(account,pwd);
			Subject subject = SecurityUtils.getSubject();
			try {
	               //执行认证操作. 
	               subject.login(token);
	           }catch (AccountException e) {
	           	System.out.println("-----------------------");
	            map.put("msg", "用户名、密码错误或账号被锁定");
	          }
			SysUser sysUser=intLoginService.queryByAccountAndPwd(account,pwd);	
			if(sysUser!=null){
				String loginstatus="1";
				String logintime = System.currentTimeMillis()+"";  //获取当前时间戳
				int num=intLoginService.updateByAccountAndPwd(loginstatus, logintime, account, pwd);
				if(num==1){
					map.put("msg", "用户登录成功,登录状态修改成功");
					map.put("loginStatus", loginstatus);
				}else{
					map.put("msg", "用户登录失败，登录状态修改失败");
				}
			}
		}
		return map;
	}
	
	//后台管理----登录验证  以及授权(手机号和验证码)
	@ResponseBody
	@RequestMapping(value="/loginpc")
	public Map<String, Object> loginpc(String phone,String code,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		int a=0;
		
		RedisClientUtil redisClientUtil = new RedisClientUtil();
		String key = phone+"Key";
		String redisCode = redisClientUtil.get(key);
		
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
			map.put("msg", "phone或验证码不能为空");
		}else {
			PhoneToken token=new PhoneToken(phone);
	        Subject subject = SecurityUtils.getSubject();
	        	try {
	                //执行认证操作. 
	                subject.login(token);
	                a=1;
	            }catch (UnknownAccountException e) {
	            	map.put("msg", "没有此手机号");
	           }
	        	System.out.println("最外层："+"a:"+a+"rediscode:"+redisCode+"code:"+code);
	        	if(a==1) {
	        		if(redisCode==null||"".equals(redisCode)){
	        			map.put("msg", "验证码过期，请重新发送");
	        		}else{
	        			if(redisCode.equals(code)) {
		                    String loginStatus="1";
		                	String registrationTime = System.currentTimeMillis()+"";  //获取当前时间戳
		                	
		                	int num=intLoginService.updateByPhone(loginStatus,registrationTime,phone);
							if (num == 1) {	
								map.put("msg", "用户登录成功，登录状态修改成功");
								map.put("loginStatus", loginStatus);
							} else {
								map.put("msg", "用户登录失败，登录状态修改失败");
							}
	        			}else {
	        				map.put("msg2", "验证码错误");
	        			}
	        		}
	        	}
		}
		return map;
	}
	
	//发送验证码
	@ResponseBody
	@RequestMapping("/sendSMS")
	public Map<String, String> sendSMS(String phone,String companyName){
		Map<String, String> map = new HashMap<>();
		SMSUtil smsUtil = new SMSUtil();
		String state = smsUtil.sendSMS(phone, "json",companyName);
	    map.put("msg",state);		
		return map;	
	}
}
