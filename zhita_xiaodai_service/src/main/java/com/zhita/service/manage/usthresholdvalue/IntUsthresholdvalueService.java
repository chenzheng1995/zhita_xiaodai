package com.zhita.service.manage.usthresholdvalue;

import java.util.Map;

public interface IntUsthresholdvalueService {
	
	//流量渠道最大阀值
	public Map<String,Object> usersourcemax(Integer companyId);
	
	//修改最大阀值
	public int upamaxthresholdvalue(Integer maxthresholdvalue);

	public int getmaxthresholdvalue(int companyId);
}
