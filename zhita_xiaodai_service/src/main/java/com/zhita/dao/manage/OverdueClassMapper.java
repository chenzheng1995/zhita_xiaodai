package com.zhita.dao.manage;

import com.zhita.model.manage.OverdueClass;

public interface OverdueClassMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(OverdueClass record);

    int insertSelective(OverdueClass record);
    
    //后台管理---根据id查询出当前对象信息
    OverdueClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverdueClass record);
    
    //后台管理---修改保存功能
    int updateByPrimaryKey(OverdueClass record);
    
    //后台管理 --- 查询逾期等级表所有信息
    int queryAll(Integer companyId);
}