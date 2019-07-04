package com.zhita.controller.Orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zhita.service.manage.orders.OrderService;


@Controller
@RequestMapping("orders")
public class OrderCollection {
	
	
	@Autowired
	private OrderService oservi;
	
	
	
	
	
	

}
