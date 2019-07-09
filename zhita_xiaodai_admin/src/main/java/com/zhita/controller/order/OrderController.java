package com.zhita.controller.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhita.service.manage.order.IntOrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IntOrderService intOrderService;
	
	//后台管理----机审订单     (公司id，page，订单号，订单开始时间，订单结束时间，风控反馈)
	public Map<String, Object> queryatrOrders(Integer companyId,Integer page,String orderNumber,String orderStartTime,String orderEndTime,String riskManagemenType){
		Map<String, Object> map=intOrderService.queryatrOrders(companyId, page, orderNumber, orderStartTime, orderEndTime, riskManagemenType);
		return map;
	}
}
