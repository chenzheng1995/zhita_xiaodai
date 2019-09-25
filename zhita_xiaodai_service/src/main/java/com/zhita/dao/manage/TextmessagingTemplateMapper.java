package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.TextmessagingTemplate;

public interface TextmessagingTemplateMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(TextmessagingTemplate record);

    int insertSelective(TextmessagingTemplate record);

    	
    //后台管理---根据id查询
    TextmessagingTemplate selectByPrimaryKey(Integer id);

    
    int updateByPrimaryKeySelective(TextmessagingTemplate record);
    
    //后台管理 ----保存功能
    int updateByPrimaryKey(TextmessagingTemplate record);
    
    //后台管理---短信模板列表查询
    List<TextmessagingTemplate> queryAll();
    
    //后台管理---更新假删除功能
    int upaFalDel(Integer id);
}