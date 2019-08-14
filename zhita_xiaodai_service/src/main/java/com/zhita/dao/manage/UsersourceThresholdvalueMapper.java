package com.zhita.dao.manage;

import com.zhita.model.manage.UsersourceThresholdvalue;

public interface UsersourceThresholdvalueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UsersourceThresholdvalue record);

    int insertSelective(UsersourceThresholdvalue record);

    UsersourceThresholdvalue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UsersourceThresholdvalue record);

    int updateByPrimaryKey(UsersourceThresholdvalue record);
    
    //后台管理-----当天所有渠道的注册数
    int queryAllSourceRegistetoday(Integer companyId,String registeStartTime,String registeEndTime);
    
    //后台管理-----user表所有的注册数量
    int queryAllSourceRegiste(Integer companyId);
    
    //后台管理---user表最早的注册时间
    String queryregistetimemin(Integer companyId);
    
    //后台管理----查询流量用户最大阈值表    最大阀值
    int maxthresholdvalue(Integer companyId);
    
    //后台管理----修改最大阀值
    int upamaxthresholdvalue(Integer maxthresholdvalue);

	int getmaxthresholdvalue(int companyId);
    
}