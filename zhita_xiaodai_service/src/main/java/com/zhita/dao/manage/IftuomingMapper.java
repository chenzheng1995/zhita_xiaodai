package com.zhita.dao.manage;

import com.zhita.model.manage.Iftuoming;

public interface IftuomingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Iftuoming record);

    int insertSelective(Iftuoming record);

    Iftuoming selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Iftuoming record);
    
    //后台管理---修改功能
    int updateByPrimaryKey(Iftuoming record);
    
    //后台管理----查询
    Iftuoming queryAll(Integer companyId);
}