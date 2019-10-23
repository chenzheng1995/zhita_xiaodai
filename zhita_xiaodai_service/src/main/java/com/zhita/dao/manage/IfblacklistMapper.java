package com.zhita.dao.manage;

import com.zhita.model.manage.Ifblacklist;

public interface IfblacklistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ifblacklist record);

    int insertSelective(Ifblacklist record);

    Ifblacklist selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(Ifblacklist record);

    //后台管理——编辑功能
    int updateByPrimaryKey(Ifblacklist record);

	String getifblacklist(int companyId);
	
	//后台管理——列表查询
	Ifblacklist queryAll(Integer companyId);
	
}