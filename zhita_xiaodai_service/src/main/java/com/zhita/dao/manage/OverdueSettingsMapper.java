package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.OverdueSettings;

public interface OverdueSettingsMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(OverdueSettings record);

    int insertSelective(OverdueSettings record);

    //后台管理---根据当前id查询出当前对象信息
    OverdueSettings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverdueSettings record);
    
    //后台管理---修改保存
    int updateByPrimaryKey(OverdueSettings record);
    
    //后台管理---查询逾期信息表
    List<OverdueSettings> queryAll(Integer companyId);
    
    //后台管理---修改当前对象的假删除状态
    int upaFalseDel(String operator,String operationTime,Integer id);
}