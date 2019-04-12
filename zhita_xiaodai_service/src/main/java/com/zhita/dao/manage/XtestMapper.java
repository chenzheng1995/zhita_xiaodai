package com.zhita.dao.manage;

import com.zhita.model.manage.Xtest;

public interface XtestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Xtest record);

    int insertSelective(Xtest record);

    Xtest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Xtest record);

    int updateByPrimaryKey(Xtest record);

	int settest(String name);

	String gettest(int id);


}