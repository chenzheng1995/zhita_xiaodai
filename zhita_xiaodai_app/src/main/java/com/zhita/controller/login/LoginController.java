package com.zhita.controller.login;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import com.zhita.model.manage.User;
import com.zhita.service.manage.blacklistuser.IntBlacklistuserService;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.service.manage.thirdpartyint.IntThirdpartyintService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/app_login")
public class LoginController {
	@Autowired
	IntLoginService loginService;

	@Autowired
	IntSourceService intSourceService;

	@Autowired
	IntThirdpartyintService intThirdpartyintService;

	@Autowired
	IntBlacklistuserService intBlacklistuserService;

	@Autowired
	IntOrderService intOrderService;

	private String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

//    // 发送验证码
//    @RequestMapping("/sendSMS")
//    @ResponseBody
//    public Map<String, String> sendSMS(String phone, String company, HttpServletRequest request) {
//        String currentIp = getIpAddress(request);
//        RedisClientUtil redis = new RedisClientUtil();
//        Map<String, String> map = new HashMap<>();
//        if (null == redis.getSourceClick(currentIp)) {
//            SMSUtil smsUtil = new SMSUtil();
//            String state = smsUtil.sendSMS(phone, "json", company);
//            if (state.equals("提交成功")) {
//                redis.set(currentIp, "1", 3600);
//            }
//            map.put("msg", state);
//            return map;
//        } else {
//            map.put("msg", "发送失败");
//            return map;
//        }
//
//    }

	@RequestMapping("/sendShortMessage")
	@ResponseBody
	public Map<String, String> sendShortMessage(String phone, int companyId, String appNumber, String code) {
		Map<String, String> map = new HashMap<>();
		map.put("Ncode","2000");
		DateFormat format = new SimpleDateFormat("yyyy/M/d");
		String result = MD5Utils.getMD5(phone + appNumber + format.format(new Date()) + "@xiaodai");
		if (result.length() == 31) {
			result = 0 + MD5Utils.getMD5(phone + appNumber + format.format(new Date()) + "@xiaodai");
		}
		if (result.equals(code)) {
			YunTongXunUtil yunTongXunUtil = new YunTongXunUtil();
			String state = yunTongXunUtil.sendSMS(phone);
			map.put("Code","200");
			map.put("msg", state);
			return map;
		} else {
			map.put("msg", "发送失败");
			map.put("Code","405");
			return map;
		}

	}

	// 验证码登陆

	/**
	 * @param phone         手机号
	 * @param code          验证码
	 * @param company       公司名
	 * @param registeClient 软件类型
	 * @return
	 */
	@RequestMapping("/codelogin")
	@ResponseBody
	@Transactional
	public Map<String, Object> codeLogin(String phone, String code, int companyId, String registeClient,
			String sourceName, String useMarket) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Ncode","2000");
		String loginStatus = "1";
		PhoneDeal phoneDeal = new PhoneDeal();
		String newPhone = phoneDeal.encryption(phone);
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code) || StringUtils.isEmpty(companyId)
				|| StringUtils.isEmpty(registeClient) || StringUtils.isEmpty(sourceName)
				|| StringUtils.isEmpty(useMarket)) {
			map.put("msg", "phone,code,companyId,registrationType,sourceName和useMarket不能为空");
			return map;
		} else {
//        	String ifBlacklist =loginService.getifBlacklist(newPhone,companyId);
			Integer id = loginService.findphone(newPhone, companyId); // 判断该用户是否存在
			int num1 = intBlacklistuserService.getid(phone, companyId);
 			if (num1 == 1 || num1 > 1) {
 				String ifBlacklist = loginService.getifBlacklist(newPhone, companyId);
 				if(ifBlacklist==null) {
						map.put("msg", "手机号黑名单 ");
						map.put("code", "407");
						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
						return map;
 				}else {
 	 				if ("0".equals(ifBlacklist)) {
 	 					loginService.updateifBlacklist1(newPhone, companyId);
 	 				} else {
 	 					int userId = loginService.getId(newPhone, companyId);
 	 					int orderStatus = intOrderService.getorderStatus(userId, companyId);
 	 					if (orderStatus != 1) {
 	 						map.put("msg", "手机号黑名单 ");
 	 						map.put("code", "407");
 	 						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
 	 						return map;
 	 					}
 	 				}
				}

 			} else {

//			int num1 = sourceDadSonService.getSourceDadSon(sourceId,sonSourceName,company);
//			if (num1 == 0) {
//			sourceDadSonService.setSourceDadSon(sourceId,sonSourceName,company);
//			}

				RedisClientUtil redisClientUtil = new RedisClientUtil();
				String key = phone + "xiaodaiKey";
				String redisCode = redisClientUtil.get(key);
				if (redisCode == null) {
					map.put("msg", "验证码已过期，请重新发送");
					map.put("code", "402");
					return map;
				}
				if (redisCode.equals(code)) {
					redisClientUtil.delkey(key);// 验证码正确就从redis里删除这个key
					String registrationTime = System.currentTimeMillis() + ""; // 获取当前时间戳
					if (id == null) {
						String operatorsAuthentication = intThirdpartyintService.getOperatorsAuthentication(companyId);
						int merchantId = intSourceService.getsourceId(sourceName);
						int number = loginService.insertUser1(newPhone, loginStatus, companyId, registeClient,
								registrationTime, merchantId, useMarket, operatorsAuthentication);
						if (number == 1) {
							id = loginService.getId(newPhone, companyId); // 获取该用户的id
							map.put("msg", "用户登录成功，数据插入成功，让用户添加密码");
							map.put("code", "201");
							map.put("loginStatus", loginStatus);
							map.put("userId", id);
							map.put("phone", phone);
						} else {
							map.put("msg", "用户登录失败，用户数据插入失败");
							map.put("code", "405");
						}
					} else {
						String loginTime = System.currentTimeMillis() + "";
						int num = loginService.updateStatus(loginStatus, newPhone, companyId, loginTime);
						if (num == 1) {
							id = loginService.getId(newPhone, companyId); // 获取该用户的id
							String pwd = loginService.getPwd(id);
							if (pwd == null) {
								map.put("msg", "用户登录成功，登录状态修改成功，让用户添加密码");
								map.put("code", "201");
								map.put("loginStatus", loginStatus);
								map.put("userId", id);
								map.put("phone", phone);
							} else {
								map.put("msg", "用户登录成功，登录状态修改成功");
								map.put("code", "200");
								map.put("loginStatus", loginStatus);
								map.put("userId", id);
								map.put("phone", phone);
							}
						} else {
							map.put("msg", "用户登录失败，登录状态修改失败");
							map.put("code", "406");
						}
					}
				} else {
					map.put("msg", "验证码错误");
					map.put("code", "403");
					return map;
				}

			}
			return map;
		}

	}

	// 忘记密码

	/**
	 * @param phone 手机号
	 * @param pwd   密码
	 * @param code  验证码
	 * @return
	 */
	@RequestMapping("/forgotpwd")
	@ResponseBody
	@Transactional
	public Map<String, Object> forgotpwd(String phone, String pwd, String code, int companyId, String sourceName,
			String useMarket) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Ncode","2000");
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(code)) {
			map.put("msg", "phone,pwd或code不能为空");
			map.put("code", "401");
			return map;
		} else {
			PhoneDeal phoneDeal = new PhoneDeal();
			String newPhone = phoneDeal.encryption(phone);
			RedisClientUtil redisClientUtil = new RedisClientUtil();
			String key = phone + "xiaodaiKey";
			String redisCode = redisClientUtil.get(key);
			if (redisCode == null) {
				map.put("msg", "验证码已过期，请重新发送");
				map.put("code", "402");
				return map;
			}
			if (redisCode.equals(code)) {
				redisClientUtil.delkey(key);// 验证码正确就从redis里删除这个key
				MD5Util md5Util = new MD5Util();
				String md5Pwd = md5Util.EncoderByMd5(pwd);
				int number = loginService.updatePwd(newPhone, md5Pwd, companyId);
				if (number == 1) {
					int id = loginService.getId(newPhone, companyId); // 获取该用户的id
					map.put("msg", "密码修改成功");
					map.put("userId", id);
					map.put("phone", phone);
					map.put("code", "200");
				} else {
					map.put("msg", "密码修改失败");
					map.put("code", "405");
				}
			} else {
				map.put("msg", "验证码输入错误");
				map.put("code", "403");
			}
		}

		return map;

	}

	/**
	 * @param phone 手机号
	 * @param pwd   密码
	 * @return
	 */
	@RequestMapping("/pwdlogin")
	@ResponseBody
	@Transactional
	public Map<String, Object> pwdLogin(String phone, String pwd, int companyId, String sourceName, String useMarket,
			String registeClient) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Ncode","2000");
		String loginStatus = "1";
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
			map.put("msg", "phone或pwd不能为空");
			return map;
		} else {
			PhoneDeal phoneDeal = new PhoneDeal();
			String newPhone = phoneDeal.encryption(phone);
			Integer id = loginService.findphone(newPhone, companyId); // 判断该用户是否存在
			int num1 = intBlacklistuserService.getid(phone, companyId);
 			if (num1 == 1 || num1 > 1) {
 				String ifBlacklist = loginService.getifBlacklist(newPhone, companyId);
 				if(ifBlacklist==null) {
						map.put("msg", "手机号黑名单 ");
						map.put("code", "407");
						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
						return map;
 				}else {
 	 				if ("0".equals(ifBlacklist)) {
 	 					loginService.updateifBlacklist1(newPhone, companyId);
 	 				} else {
 	 					int userId = loginService.getId(newPhone, companyId);
 	 					int orderStatus = intOrderService.getorderStatus(userId, companyId);
 	 					if (orderStatus != 1) {
 	 						map.put("msg", "手机号黑名单 ");
 	 						map.put("code", "407");
 	 						map.put("prompt", "您暂时不符合借款要求，请三个月之后再来尝试");
 	 						return map;
 	 					}
 	 				}
				}

 			}
				if (id == null) {
					String registrationTime = System.currentTimeMillis() + ""; // 获取当前时间戳
					String operatorsAuthentication = intThirdpartyintService.getOperatorsAuthentication(companyId);
					int merchantId = intSourceService.getsourceId(sourceName);
					int num = loginService.insertUser1(newPhone, loginStatus, companyId, registeClient,
							registrationTime, merchantId, useMarket, operatorsAuthentication);
					if (num == 1) {
						id = loginService.getId(newPhone, companyId); // 获取该用户的id
						map.put("msg", "用户登录成功，数据插入成功");
						map.put("code", "201");
						map.put("loginStatus", loginStatus);
						map.put("userId", id);
						map.put("phone", phone);
					} else {
						map.put("msg", "用户登录失败，用户数据插入失败");
						map.put("code", "405");
					}
				} else {
					MD5Util md5Util = new MD5Util();
					String dataMd5Pwd = loginService.getMd5pwd(newPhone, companyId);
					String Md5Pwd = md5Util.EncoderByMd5(pwd); // md5加密
					if (Md5Pwd.equals(dataMd5Pwd)) {
						String loginTime = System.currentTimeMillis() + "";
						int num = loginService.updateStatus(loginStatus, newPhone, companyId, loginTime);
						if (num == 1) {
							id = loginService.getId(newPhone, companyId); // 获取该用户的id
							map.put("msg", "用户登录成功，登录状态修改成功");
							map.put("code", "200");
							map.put("loginStatus", loginStatus);
							map.put("userId", id);
							map.put("phone", phone);
						} else {
							map.put("msg", "用户登录失败，登录状态修改失败");
							map.put("code", "406");
						}
					} else {
						map.put("msg", "密码错误");
						map.put("code", "403");
						return map;
					}

				}

			return map;
		}
	}

	// 用户登录之后，发现该账号没密码，让他添加密码
	@RequestMapping("/setpwd")
	@ResponseBody
	@Transactional
	public Map<String, Object> setPwd(String pwd, int userId, String sourceName, String useMarket) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Ncode","2000");
		if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(userId)) {
			map.put("msg", "pwd或userId不能为空");
			return map;
		} else {
			MD5Util md5Util = new MD5Util();
			String md5Pwd = md5Util.EncoderByMd5(pwd);
			int number = loginService.setPwd(userId, md5Pwd);
			if (number == 1) {
				map.put("msg", "密码添加成功");
				map.put("code", "200");
			} else {
				map.put("msg", "密码添加失败");
				map.put("code", "400");
			}

		}

		return map;

	}

	// 用户找回密码的时候，判断是否存在该用户
	@RequestMapping("/getuser")
	@ResponseBody
	@Transactional
	public Map<String, Object> getuser(String phone, int companyId, String sourceName, String useMarket) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Ncode","2000");
		PhoneDeal phoneDeal = new PhoneDeal();
		String newPhone = phoneDeal.encryption(phone);
		Integer id = loginService.findphone(newPhone, companyId); // 判断该用户是否存在
		if (id == null) {
			map.put("msg", "用户名不存在,请先注册");
			map.put("code", "405");
		} else {
			map.put("msg", "用户存在");
			map.put("code", "201");
		}
		return map;

	}

	// 退出登录

	/**
	 * @return
	 */
	@RequestMapping("/logOut")
	@ResponseBody
	@Transactional
	public Map<String, String> appLogOut(int userId, String companyId) {
		Map<String, String> map = new HashMap<>();
		map.put("Ncode","2000");
		if (StringUtils.isEmpty(userId)) {
			map.put("msg", "userId不能为空");
			return map;
		} else {
			String loginStatus = "0";
			int number = loginService.updatelogOutStatus(loginStatus, userId, companyId);
			if (number == 1) {
				map.put("msg", "用户退出成功，登录状态修改成功");
				map.put("code", "200");
				map.put("loginStatus", loginStatus);
			} else {
				map.put("msg", "用户退出失败，登录状态修改失败");
				map.put("code", "400");
			}
		}

		return map;

	}

}
