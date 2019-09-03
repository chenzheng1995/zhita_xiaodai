package com.zhita.dao.manage;

import com.zhita.model.manage.MagicWand3Info;

public interface MagicWand3InfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MagicWand3Info record);

    int insertSelective(MagicWand3Info record);

    MagicWand3Info selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MagicWand3Info record);

    int updateByPrimaryKey(MagicWand3Info record);
}