package com.zhita.dao.manage;

import com.zhita.model.manage.OverdueSettings;

public interface OverdueSettingsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OverdueSettings record);

    int insertSelective(OverdueSettings record);

    OverdueSettings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverdueSettings record);

    int updateByPrimaryKey(OverdueSettings record);
}