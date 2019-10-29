package com.zhita.service.manage.iffengkong;

import com.zhita.model.manage.Iffengkong;

public interface IntIffengkongService {
	
	//后台管理——列表查询
	public Iffengkong queryAll(Integer companyId);
	
	//后台管理——编辑功能
    public int updateByPrimaryKey(Iffengkong record);

}
