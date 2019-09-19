package com.zhita.service.manage.newpayment;

import java.math.BigDecimal;
import java.util.Map;

import com.zhita.model.manage.Thirdparty_interface;

public interface NewPaymentservice {
	
	
	Thirdparty_interface SelectPaymentName(Integer companyId);
	
	
	
	Map<String, Object> Newpayment(BigDecimal amount,String billId,Integer userId,Integer companyId);
	
	
	
	Map<String, Object> Payment(BigDecimal amount,String returnUrl,Integer companyId,Integer userId);

}
