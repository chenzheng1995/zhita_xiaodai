package com.zhita.dao.manage;

import com.zhita.model.manage.Iffengkong;

public interface IffengkongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Iffengkong record);

    int insertSelective(Iffengkong record);

    Iffengkong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Iffengkong record);

    int updateByPrimaryKey(Iffengkong record);

	String getiffengkong(int companyId);
}