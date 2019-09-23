package com.zhita.service.manage.overtextmessagingsetting;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.OverTextmessagingSetting;
import com.zhita.model.manage.TextmessagingTemplate;

public interface IntovertextsettingService {
	
	//后台管理---查询出短信发送内容模板表的数据
    public List<TextmessagingTemplate> querytexttemp();
    
    //后台管理---添加功能
    public int insert(OverTextmessagingSetting record);
    
    //后台管理---查询列表
    public Map<String,Object> queryAll(Integer companyId,Integer page);
    
    //后台管理---根据id查询
    public OverTextmessagingSetting selectByPrimaryKey(Integer id);
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(OverTextmessagingSetting record);
    
    //后台管理----修改假删除状态
    public int upaFalDel(Integer id);
}
