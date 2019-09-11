package com.zhita.controller.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.dao.manage.ConfigurationMapper;
import com.zhita.service.manage.configuration.ConfigurationService;

//@Controller
//@RequestMapping("/configuration")
//public class ConfigurationController {
//
//	@Autowired
//	ConfigurationService configurationService;
//	
//	@RequestMapping("/setconfiguration")
//	@ResponseBody
//	@Transactional
//    public Map<String, Object> setconfiguration(String jsonString,int userId){
//    	Map<String, Object> map = new HashMap<>();
//		JSONObject jsonObject = JSONObject.parseObject(jsonString);
//		String phoneMarket = jsonObject.getString("phoneMarket");
//		String phoneModel = jsonObject.getString("phoneModel");
//		String phoneRes = jsonObject.getString("phoneRes");
//		String phoneStand = jsonObject.getString("phoneStand"); 
//		JSONObject jsonObject1 = JSONObject.parseObject(phoneStand);
//		String lac = jsonObject1.getString("lac");
//		String loc = jsonObject1.getString("loc");
//		String uuid = jsonObject.getString("uuid");
//		String wifiIP = jsonObject.getString("wifiIP");
//		String wifiMac = jsonObject.getString("wifiMac");
//		String wifiName = jsonObject.getString("wifiName");
//		String wrapName = jsonObject.getString("wrapName");
//		int num = configurationService.setconfiguration(userId,phoneMarket,phoneModel,phoneRes,lac,loc,uuid,wifiIP,wifiMac,wifiName,wrapName);
//		if (num==1) {
//			map.put("Ncode","2000");
//			map.put("code","200");
//			map.put("msg","数据插入成功");
//		}else {
//			map.put("Ncode","405");
//			map.put("code","405");
//			map.put("msg","数据插入失败");
//		}
//		
//		return map;
//
//    }
//}
