package com.zhita.dao.manage;

import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Thirdparty_interface;

public interface NewMapper {
	
	//查询放款名称  和  还款名称
	Thirdparty_interface NewloanRepayment(Integer companyId);
	
	
	
	//修改还款状态
	Integer Repaymentsa(Repayment orderNumber);

	
	//获取订单ID
	Integer getOrderId(String orderNumber);
	
	
	
	Integer Updatepaymemt(Payment_record pay);
	
	
	
	Orders getOrders(String orderNumber);
	
	
	
	Payment_record getPayment(Integer orderId);
	
	
	Integer UpdateOrders(Integer id);
}
