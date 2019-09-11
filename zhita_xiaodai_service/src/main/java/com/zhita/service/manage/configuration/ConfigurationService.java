package com.zhita.service.manage.configuration;

import java.util.Map;

import org.springframework.stereotype.Service;


public interface ConfigurationService {

	int setconfiguration(int userId, String phoneMarket, String phoneModel, String phoneRes, String lac, String loc,
			String uuid, String wifiIP, String wifiMac, String wifiName, String wrapName);

	int getId(int userId);

	int updateconfiguration(int userId, String phoneMarket, String phoneModel, String phoneRes, String lac, String loc,
			String uuid, String wifiIP, String wifiMac, String wifiName, String wrapName);

	Map<String, Object> getconfiguration(int userId);



}
