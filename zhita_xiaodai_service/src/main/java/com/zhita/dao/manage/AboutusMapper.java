package com.zhita.dao.manage;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Aboutus;

public interface AboutusMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(Aboutus record);

    int insertSelective(Aboutus record);
    
    //后台管理---根据id查询当前对象信息
    Aboutus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Aboutus record);
    
    //后台管理---更新功能
    int updateByPrimaryKey(Aboutus record);
    
    //后台管理---查询所有
    List<Aboutus> queryAll(Integer companyId);
    
    //后台管理---根据id查询当前对象的logo字段
    String queryAppLogo(Integer id);

	Map<String, Object> getaboutus(int companyId);
    
}