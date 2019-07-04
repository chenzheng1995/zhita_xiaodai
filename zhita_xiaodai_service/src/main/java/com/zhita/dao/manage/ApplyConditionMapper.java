package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.ApplyCondition;

public interface ApplyConditionMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(ApplyCondition record);

    int insertSelective(ApplyCondition record);

    //后台管理---根据id查询出当前对象
    ApplyCondition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplyCondition record);

    //后台管理---编辑功能
    int updateByPrimaryKey(ApplyCondition record);
    
    //后台管理---查询申请条件配置表所有信息
    List<ApplyCondition> queryAll(Integer companyId);
}