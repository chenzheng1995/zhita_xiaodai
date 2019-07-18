package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.BlackDemarcationLine;
import com.zhita.model.manage.OverdueClass;

public interface OverdueClassMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(OverdueClass record);

    int insertSelective(OverdueClass record);
    
    //后台管理---根据id查询出当前对象信息
    OverdueClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverdueClass record);
    
    //后台管理---修改保存功能
    int updateByPrimaryKey(OverdueClass record);
    
    //后台管理 --- 查询逾期等级表所有信息
    List<OverdueClass> queryAll(Integer companyId);
    
    //后台管理---查询黑名单分界线表的信息
    BlackDemarcationLine query();
    
    //后台管理---修改黑名单分界线的值
    int update(Integer blacklinevalue);
    //后台管理---修改假删除功能
    int upaFalseDel(Integer id);
}