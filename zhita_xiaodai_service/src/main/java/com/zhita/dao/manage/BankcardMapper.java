package com.zhita.dao.manage;

import java.util.Map;

import com.zhita.model.manage.Bankcard;

public interface BankcardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bankcard record);

    int insertSelective(Bankcard record);

    Bankcard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bankcard record);

    int updateByPrimaryKey(Bankcard record);

	Map<String, Object> getbankcard(int userId);

}