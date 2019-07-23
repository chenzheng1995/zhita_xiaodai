package com.zhita.service.manage.bankcard;

import java.util.Map;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Orders;

public interface BankcardService {
	
	Map<String, Object> AddBankcard(Bankcard bank);
	
	
	Map<String, Object> AddOrders(Orders orde);




}
