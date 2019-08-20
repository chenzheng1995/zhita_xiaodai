package com.zhita.dao.manage;

import com.zhita.model.manage.Thirdtype;

public interface ThirdtypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Thirdtype record);

    int insertSelective(Thirdtype record);

    Thirdtype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Thirdtype record);

    int updateByPrimaryKey(Thirdtype record);
}