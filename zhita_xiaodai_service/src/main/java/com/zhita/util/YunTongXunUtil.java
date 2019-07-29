package com.zhita.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class YunTongXunUtil {
	public String sendSMS (String mobile) {
		String account = "N7893961";
		String password = "xap6Mikey";


		Map<String,Object> cmap =new HashMap<String, Object>();
		cmap.put("0", "提交成功");
		cmap.put("101", "无此用户");
		cmap.put("102", "密码错误");
		cmap.put("103", "提交过快（提交速度超过流速限制）");
		cmap.put("104", "系统忙（因平台侧原因，暂时无法处理提交的短信）");
		cmap.put("105", "敏感短信（短信内容包含敏感词）");
		cmap.put("106", "消息长度错（>536或<=0）");
		cmap.put("107", "包含错误的手机号码");
		cmap.put("108", "手机号码个数错（群发>50000或<=0）");
		cmap.put("109", "无发送额度（该用户可用短信数已使用完）");
		cmap.put("110", "不在发送时间内");
		cmap.put("113", "扩展码格式错（非数字或者长度不对）");
		cmap.put("114", "可用参数组个数错误（小于最小设定值或者大于1000）;变量参数组大于20个");
		cmap.put("116", "签名不合法或未带签名（在更换自己的签名需要在平台上报备后方可使用该签名）");
		cmap.put("117", "IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
		cmap.put("118", "用户没有相应的发送权限（账号被禁止发送）");
		cmap.put("119", "用户已过期");
		cmap.put("120", "违反防盗用策略(日发送限制)");
		cmap.put("123", "发送类型错误");
		cmap.put("124", "白模板匹配错误");
		cmap.put("125", "匹配驳回模板，提交失败");
		cmap.put("127", "定时发送时间格式错误");
		cmap.put("128", "内容编码失败");
		cmap.put("129", "JSON格式错误");
		cmap.put("130", "请求参数错误（缺少必填参数）");
		String state = "未知问题";
		PostUtil postUtil = new PostUtil();
		String code =((int)((Math.random()*9+1)*1000))+"";
		String content = "【鼎坤】您的验证码是："+code+"。请不要把验证码泄露给其他人。";
		String param = "{'account':'"+account+"','password':'"+password+"','msg':'"+content+"','phone':'"+mobile+"','report':'true'}";
		String jsonString = postUtil.post("http://smssh1.253.com/msg/send/json", param);
		Map<String, Object> map = JSONObject.parseObject(jsonString);
		String mapCode = map.get("code")+"";

		//遍历map中的键
		for (String key : cmap.keySet()) {
			if(key.equals(mapCode)) {
				RedisClientUtil redisClientUtil = new RedisClientUtil();
				redisClientUtil.set(mobile+"xiaodaiKey", code);
				state =(String) cmap.get(key) ;
			}
		}

		return state;

	}

	}

