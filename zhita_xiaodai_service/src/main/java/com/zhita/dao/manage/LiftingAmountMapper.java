package com.zhita.dao.manage;

import com.zhita.model.manage.LiftingAmount;

public interface LiftingAmountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LiftingAmount record);

    int insertSelective(LiftingAmount record);

    LiftingAmount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiftingAmount record);

    int updateByPrimaryKey(LiftingAmount record);
}