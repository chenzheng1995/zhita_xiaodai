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

	User findphone(@Param("newPhone")String newPhone,@Param("companyId") String companyId);
}