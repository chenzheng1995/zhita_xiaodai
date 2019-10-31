package com.zhita.service.manage.statement;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Source;

public interface IntstatementService {
	
	//后台管理——查询所有正常的渠道
	public List<Source> querysource(Integer companyId);
	
	//后台管理——还款表
	public Map<String,Object> paymentrecord(Integer companyId,Integer page,String repaystartTime,String repayendTime);
}
