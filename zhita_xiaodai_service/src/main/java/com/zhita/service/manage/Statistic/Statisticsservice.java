package com.zhita.service.manage.Statistic;


import java.util.Map;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeduction;
import com.zhita.model.manage.MouthBankName;
import com.zhita.model.manage.Orderdetails;

public interface Statisticsservice {
	
	
	
	MouthBankName SendBankcomm(String BankCommonName,String AcctNo,String AcctName,String TransAmt,String LiceneceNo,
			String Phone,Integer sys_userId,Integer deductionproportion,String orderNumber,Integer orderId,Integer userId);
	
	
	Map<String, Object> AllCollection(Orderdetails order);
	
	
	
	String IDnumber(Integer userId);
	
	
	Integer UpdateBank(Bankdeduction ban);
	
	
	Map<String, Object> AllBankdeduData(Bankcard ban);
}
