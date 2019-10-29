package com.zhita.dao.manage;

import com.zhita.model.manage.Iffengkong;

public interface IffengkongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Iffengkong record);

    int insertSelective(Iffengkong record);

    Iffengkong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Iffengkong record);
    
    //后台管理——编辑功能
    int updateByPrimaryKey(Iffengkong record);

	String getiffengkong(int companyId);
	
	//后台管理——列表查询
	Iffengkong queryAll(Integer companyId);
	
}