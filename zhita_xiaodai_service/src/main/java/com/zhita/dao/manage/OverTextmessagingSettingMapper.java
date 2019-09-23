package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.OverTextmessagingSetting;
import com.zhita.model.manage.TextmessagingTemplate;

public interface OverTextmessagingSettingMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---查询列表数量
    int queryAllcount(Integer companyId);
    
    //后台管理---查询列表
    List<OverTextmessagingSetting> queryAll(Integer companyId,Integer page,Integer pagesize);
    
    //后台管理---添加功能
    int insert(OverTextmessagingSetting record);

    int insertSelective(OverTextmessagingSetting record);
    
    //后台管理---根据id查询
    OverTextmessagingSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverTextmessagingSetting record);
    
    //后台管理---编辑功能
    int updateByPrimaryKey(OverTextmessagingSetting record);
    
    //后台管理---查询出短信发送内容模板表的数据
    List<TextmessagingTemplate> querytexttemp();
    
    //后台管理----修改假删除状态
    int upaFalDel(Integer id);
}