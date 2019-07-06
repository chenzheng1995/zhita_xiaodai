package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.RetrialWindControl;

public interface RetrialWindControlMapper {
	//后台管理----添加功能
    int insert(RetrialWindControl record);

    int insertSelective(RetrialWindControl record);
    
    //后台管理---查询再审风控表所有信息
    List<RetrialWindControl> queryAll(Integer companyId);
    
    //后台管理---根据当前id查询当前对象现在
    RetrialWindControl selectByPrimaryKey(Integer id);
    
    //后台管理---更新功能
    int updateByPrimaryKey(RetrialWindControl record);
    

}