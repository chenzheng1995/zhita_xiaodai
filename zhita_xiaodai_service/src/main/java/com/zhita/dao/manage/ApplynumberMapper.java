package com.zhita.dao.manage;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Applynumber;

public interface ApplynumberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Applynumber record);

    int insertSelective(Applynumber record);

    Applynumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Applynumber record);

    int updateByPrimaryKey(Applynumber record);

	void setapplynumber(@Param("userId") int userId,@Param("timStamp") String timStamp,@Param("applynumber") String applynumber,@Param("state") String state);

	void updatestate(int userId);
}