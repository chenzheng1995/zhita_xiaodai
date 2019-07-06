package com.zhita.dao.manage;

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
}