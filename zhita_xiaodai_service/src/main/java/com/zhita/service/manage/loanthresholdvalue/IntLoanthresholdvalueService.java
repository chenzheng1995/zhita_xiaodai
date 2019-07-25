package com.zhita.service.manage.loanthresholdvalue;

import java.util.Map;

public interface IntLoanthresholdvalueService {
	
	//放款渠道最大阀值
	public Map<String,Object> loanmax(Integer companyId);
	
	//修改最大阀值
	public int upamaxthresholdvalue(Integer maxthresholdvalue);
}
