package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeduction;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;

public interface StatisticsDao {
	
	Integer AddBankcard(Bankcard bank);
	
	
	Integer SelectTrxId(Bankcard bank);
	
	
	Integer UpdateStatu(Integer id);
	
	
	Integer AddRepay(Repayment repay);
	
	
	Integer UpdateOrders(Orders ord);
	
	
	Integer AddDeferred(Deferred defe);
	
	
	Integer SelectUserdelayTimes(Orders ord);
	
	
	Integer UpdateUser(Orders ord);
	
	
	Integer SelectOrderId(String orderNumber);
	
	
	Integer AddPaymentRecord(Payment_record pay);
	
	
	Integer UpdatePaymentrecord(Payment_record pay);
	
	
	Bankcard SelectBanl(Bankcard userId);
	
	
	List<Orderdetails> AllBanl(Orderdetails order);
	
	
	Integer OrderCollectionNum(Integer companyId);
	
	
	String UserAll(Integer userId);
	
	
	Integer Addbankdeduction(Bankdeduction ban);
	
	
	Integer UpdateBank(Bankdeduction ban);
	
	
	Integer SelectTotalCount(Bankcard ban);
	
	
	List<Bankdeduction> AllBank(Bankcard ban);
	
	
	Integer ChenggNum(Bankdeduction ban);
	
	
	BigDecimal ChenggMoney(Bankdeduction ban);
	
	
	
	List<Bankdeduction> Deails(Bankdeduction ban);
	
}
