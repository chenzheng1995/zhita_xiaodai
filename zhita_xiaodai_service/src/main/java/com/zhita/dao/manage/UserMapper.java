package com.zhita.dao.manage;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User findphone(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int insertUser1(@Param("newPhone")String newPhone,@Param("loginStatus")String loginStatus,@Param("companyId")int companyId,@Param("registeClient")String registeClient,
			@Param("registrationTime")String registrationTime,@Param("merchantId")int merchantId,@Param("useMarket")String useMarket);

	int getId(@Param("newPhone")String newPhone,@Param("companyId") int companyId);

	int updateStatus(@Param("loginStatus")String loginStatus,@Param("newPhone") String newPhone,@Param("companyId") int companyId,@Param("loginTime") String loginTime);

	String getPwd(int id);

	int updatelogOutStatus(@Param("loginStatus")String loginStatus,@Param("userId") int userId,@Param("companyId") String companyId);
}