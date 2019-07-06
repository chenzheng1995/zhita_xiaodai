package com.zhita.service.manage.overduesettings;

import java.util.List;

import com.zhita.model.manage.OverdueSettings;

public interface IntOverduesetService {
    //后台管理---查询逾期信息表
   public List<OverdueSettings> queryAll(Integer companyId);
   
   //后台管理---添加功能
   public int insert(OverdueSettings record);
   
   //后台管理---根据当前id查询出当前用户信息
   public OverdueSettings selectByPrimaryKey(Integer id);
   
   //后台管理---修改保存
   public int updateByPrimaryKey(OverdueSettings record);
   
   //后台管理---修改当前对象的假删除状态
   public int upaFalseDel(String operator,Integer id);
}
