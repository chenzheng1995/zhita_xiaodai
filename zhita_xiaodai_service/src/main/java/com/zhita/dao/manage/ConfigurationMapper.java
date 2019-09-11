package com.zhita.dao.manage;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Configuration;

public interface ConfigurationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Configuration record);

    int insertSelective(Configuration record);

    Configuration selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Configuration record);

    int updateByPrimaryKeyWithBLOBs(Configuration record);

    int updateByPrimaryKey(Configuration record);

	int setconfiguration(@Param("userId") int userId,@Param("phoneMarket") String phoneMarket,@Param("phoneModel") String phoneModel,@Param("phoneRes") String phoneRes,@Param("lac") String lac, @Param("loc")String loc,
			@Param("uuid")String uuid,@Param("wifiIP") String wifiIP,@Param("wifiMac") String wifiMac,@Param("wifiName") String wifiName,@Param("wrapName") String wrapName);

	int getId(int userId);
	
	int updateconfiguration(@Param("userId") int userId,@Param("phoneMarket") String phoneMarket,@Param("phoneModel") String phoneModel,@Param("phoneRes") String phoneRes,@Param("lac") String lac, @Param("loc")String loc,
			@Param("uuid")String uuid,@Param("wifiIP") String wifiIP,@Param("wifiMac") String wifiMac,@Param("wifiName") String wifiName,@Param("wrapName") String wrapName);

	Map<String, Object> getconfiguration(int userId);


}