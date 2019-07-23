package com.zhita.service.manage.homepagetongji;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Orderdetails;

public interface IntHomepagetongjiService {
	//首页统计
	public Map<String, Object> queryAll(Integer companyId);
	
	//回收率报表
	public Map<String, Object> recoveryStatement(Integer companyId,Integer page,String shouldrepayStartTime,String shouldrepayEndTime);
	
	List<Orderdetails> test();
}
