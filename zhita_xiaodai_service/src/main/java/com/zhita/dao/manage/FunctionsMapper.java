package com.zhita.dao.manage;

import com.zhita.model.manage.Functions;

public interface FunctionsMapper {
    int deleteByPrimaryKey(Integer functionid);

    int insert(Functions record);

    int insertSelective(Functions record);

    Functions selectByPrimaryKey(Integer functionid);

    int updateByPrimaryKeySelective(Functions record);

    int updateByPrimaryKey(Functions record);
}