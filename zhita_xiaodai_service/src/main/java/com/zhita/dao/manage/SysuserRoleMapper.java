package com.zhita.dao.manage;

import com.zhita.model.manage.SysuserRole;

public interface SysuserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysuserRole record);

    int insertSelective(SysuserRole record);

    SysuserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysuserRole record);

    int updateByPrimaryKey(SysuserRole record);
}