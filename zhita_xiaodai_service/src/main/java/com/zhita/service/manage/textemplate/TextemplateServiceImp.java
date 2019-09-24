package com.zhita.service.manage.textemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.TextmessagingTemplateMapper;
import com.zhita.model.manage.TextmessagingTemplate;

@Service
public class TextemplateServiceImp implements IntTextemplateService{
	
	@Autowired
	private TextmessagingTemplateMapper textmessagingTemplateMapper;
	
	//后台管理---短信模板列表查询
    public List<TextmessagingTemplate> queryAll(){
    	return textmessagingTemplateMapper.queryAll();
    }
    
    //后台管理---添加功能
    public int insert(TextmessagingTemplate record){
    	return textmessagingTemplateMapper.insert(record);
    }
    
    //后台管理---根据id查询
    public TextmessagingTemplate selectByPrimaryKey(Integer id){
    	return textmessagingTemplateMapper.selectByPrimaryKey(id);
    }

    //后台管理 ----保存功能
    public int updateByPrimaryKey(TextmessagingTemplate record){
    	return textmessagingTemplateMapper.updateByPrimaryKey(record);
    }
    
    //后台管理---更新假删除功能
    public int upaFalDel(Integer id){
    	return textmessagingTemplateMapper.upaFalDel(id);
    }
}
