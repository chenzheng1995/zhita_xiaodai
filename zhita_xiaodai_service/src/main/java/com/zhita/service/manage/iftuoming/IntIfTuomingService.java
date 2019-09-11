package com.zhita.service.manage.iftuoming;

import com.zhita.model.manage.Iftuoming;

public interface IntIfTuomingService {

	//后台管理----查询
    public Iftuoming queryAll(Integer companyId);
    
    //后台管理---修改功能
    public int updateByPrimaryKey(Iftuoming record);
}
