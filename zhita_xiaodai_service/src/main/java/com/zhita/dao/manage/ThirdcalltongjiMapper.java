package com.zhita.dao.manage;

import com.zhita.model.manage.Thirdcalltongji;

public interface ThirdcalltongjiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Thirdcalltongji record);

    int insertSelective(Thirdcalltongji record);

    Thirdcalltongji selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Thirdcalltongji record);

    int updateByPrimaryKey(Thirdcalltongji record);
}