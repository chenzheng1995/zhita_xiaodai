package com.zhita.service.manage.textemplate;

import java.util.List;

import com.zhita.model.manage.TextmessagingTemplate;

public interface IntTextemplateService {
	
	//后台管理---短信模板列表查询
    public List<TextmessagingTemplate> queryAll();
    
    //后台管理---添加功能
    public int insert(TextmessagingTemplate record);
    
    //后台管理---根据id查询
    public TextmessagingTemplate selectByPrimaryKey(Integer id);

    //后台管理 ----保存功能
    public int updateByPrimaryKey(TextmessagingTemplate record);
    
    //后台管理---更新假删除功能
    public int upaFalDel(Integer id);
}
