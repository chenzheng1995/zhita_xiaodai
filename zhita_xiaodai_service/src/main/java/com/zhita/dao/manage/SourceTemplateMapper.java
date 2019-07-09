package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.SourceTemplate;

public interface SourceTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SourceTemplate record);

    int insertSelective(SourceTemplate record);

    SourceTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SourceTemplate record);

    int updateByPrimaryKey(SourceTemplate record);
    
    //后台管理---根据模板名字得到模板id
    Integer getid(String templateName);
    
    //后台管理---查询所有模板信息
    List<SourceTemplate> queryAllTemp();
}