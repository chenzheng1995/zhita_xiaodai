package com.zhita.service.manage.retrialwindcontrol;

import java.util.List;

import com.zhita.model.manage.RetrialWindControl;

public interface IntRetrialwinconService {
	//后台管理---查询再审风控表所有信息
    public List<RetrialWindControl> queryAll(Integer companyId);
    
	//后台管理----添加功能
    public int insert(RetrialWindControl record);
    
    //后台管理---根据当前id查询当前对象现在
    public RetrialWindControl selectByPrimaryKey(Integer id);
    
    //后台管理---更新功能
    public int updateByPrimaryKey(RetrialWindControl record);
}
