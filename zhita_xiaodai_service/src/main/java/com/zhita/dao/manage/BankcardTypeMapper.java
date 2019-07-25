package com.zhita.dao.manage;

import com.zhita.model.manage.BankcardType;

public interface BankcardTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BankcardType record);

    int insertSelective(BankcardType record);


    BankcardType selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(BankcardType record);

    int updateByPrimaryKey(BankcardType record);

	String getbankcardTypeName(int bankcardTypeId);
}