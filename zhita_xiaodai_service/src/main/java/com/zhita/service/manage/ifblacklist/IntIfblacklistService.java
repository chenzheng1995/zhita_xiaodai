package com.zhita.service.manage.ifblacklist;

import com.zhita.model.manage.Ifblacklist;

public interface IntIfblacklistService {
	
	//后台管理——列表查询
	public Ifblacklist queryAll(Integer companyId);
	
	//后台管理——编辑功能
    public int updateByPrimaryKey(Ifblacklist record);
}
