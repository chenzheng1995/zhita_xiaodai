package com.zhita.service.manage.configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.ConfigurationMapper;

@Service
public class ConfigurationServiceImp implements ConfigurationService{

	@Autowired
	ConfigurationMapper configurationMapper;
	
	@Override
	public int setconfiguration(int userId, String phoneMarket, String phoneModel, String phoneRes, String lac,
			String loc, String uuid, String wifiIP, String wifiMac, String wifiName, String wrapName) {
		int num = configurationMapper.setconfiguration(userId,phoneMarket,phoneModel,phoneRes,lac,loc,uuid,wifiIP,wifiMac,wifiName,wrapName);
		return num;
	}

	@Override
	public int getId(int userId) {
		int number = configurationMapper.getId(userId);
		return number;
	}

	@Override
	public int updateconfiguration(int userId, String phoneMarket, String phoneModel, String phoneRes, String lac,
			String loc, String uuid, String wifiIP, String wifiMac, String wifiName, String wrapName) {
		int num = configurationMapper.updateconfiguration(userId,phoneMarket,phoneModel,phoneRes,lac,loc,uuid,wifiIP,wifiMac,wifiName,wrapName);
		return num;
	}

	@Override
	public Map<String, Object> getconfiguration(int userId) {
		Map<String, Object> map = configurationMapper.getconfiguration(userId);
		return map;
	}

}
