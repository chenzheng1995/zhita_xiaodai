package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.ManageControlSettings;

public interface ManageControlSettingsMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(ManageControlSettings record);

    int insertSelective(ManageControlSettings record);
    
    //后台管理---根据主键id查询当前对象信息
    ManageControlSettings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManageControlSettings record);
    
    //后台管理---编辑保存功能
    int updateByPrimaryKey(ManageControlSettings record);
    
    //后台管理---查询风控设置管理表所有信息
    List<ManageControlSettings> queryAll(Integer companyId);
}