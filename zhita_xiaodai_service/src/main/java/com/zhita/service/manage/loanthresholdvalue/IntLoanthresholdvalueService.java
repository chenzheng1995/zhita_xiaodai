package com.zhita.service.manage.loanthresholdvalue;

import java.util.Map;

public interface IntLoanthresholdvalueService {
	
	//放款渠道最大阀值
	public Map<String,Object> loanmax(Integer companyId);
	
	//修改最大阀值
	public int upamaxthresholdvalue(Integer maxthresholdvalue);
	
	//后台管理---查看当天放款金额
	public Map<String,Object> queryloantoday(Integer companyId);
	
	//后台管理----修改放款渠道状态
    public int upaloanstatus(String status,Integer companyId);
}
