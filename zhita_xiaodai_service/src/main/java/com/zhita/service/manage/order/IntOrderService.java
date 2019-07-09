package com.zhita.service.manage.order;

import java.util.Map;

public interface IntOrderService {
	
	//后台管理----机审订单     (公司id，page，订单号，订单开始时间，订单结束时间，风控反馈)
	public Map<String, Object> queryatrOrders(Integer companyId,Integer page,String orderNumber,String orderStartTime,String orderEndTime,String riskManagemenType);

}
