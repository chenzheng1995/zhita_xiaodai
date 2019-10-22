package com.zhita.dao.manage;

import com.zhita.model.manage.Ifblacklist;

public interface IfblacklistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ifblacklist record);

    int insertSelective(Ifblacklist record);

    Ifblacklist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ifblacklist record);

    int updateByPrimaryKey(Ifblacklist record);

	String getifblacklist(int companyId);
}