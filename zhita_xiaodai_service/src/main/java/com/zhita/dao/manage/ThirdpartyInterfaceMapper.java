package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.ThirdpartyInterface;

public interface ThirdpartyInterfaceMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(ThirdpartyInterface record);

    int insertSelective(ThirdpartyInterface record);
    
    //后台管理---根据id查询当前对象信息
    ThirdpartyInterface selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirdpartyInterface record);
    
    //后台管理---编辑保存功能
    int updateByPrimaryKey(ThirdpartyInterface record);
    
    //后台管理---查询出第三方接口配置表所有信息
    List<ThirdpartyInterface> queryAll(Integer companyId);
}