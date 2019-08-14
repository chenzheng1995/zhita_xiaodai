package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeductions;
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
	
	
	Integer SelectUserdelayTimes(Integer userId);
	
	
	Integer UpdateUser(Orders ord);
	
	
	Orders SelectOrderId(String orderNumber);
	
	
	Integer AddPaymentRecord(Payment_record pay);
	
	
	Integer UpdatePaymentrecord(Payment_record pay);
	
	
	Bankcard SelectBanl(Bankcard userId);
	
	
	List<Orderdetails> AllBanl(Orderdetails order);
	
	
	Integer OrderCollectionNum(Integer companyId);
	
	
	String UserAll(Integer userId);
	
	
	Integer Addbankdeduction(Bankdeductions ban);
	
	
	Integer UpdateBank(Bankdeductions ban);
	
	
	Integer SelectTotalCount(Bankcard ban);
	
	
	List<Bankdeductions> AllBank(Bankcard ban);
	
	
	Integer ChenggNum(Bankdeductions ban);
	
	
	BigDecimal ChenggMoney(Bankdeductions ban);
	
	
	List<Bankdeductions> Deails(Bankdeductions ban);
	
	
	Orders OneOrders(Orders ord);
	
	
	Integer UpdateOrderSuperl(Orders ord);
	
	
	List<Bankdeductions> AllBan(Bankdeductions ban);
	
	
	BigDecimal SelectChengMoney(Bankdeductions ban);
	
	
	List<Bankdeductions> SelectBankKoukuan(Bankdeductions ban);
	
	
	Integer DeleteChan(Integer userId);
	
	
	Integer SelectUserId(Integer userId);
	
	
	Integer SelectDefeDay(Integer companyId);
	
	
	Integer DefeOrder(Orders ord);
	
	
	String SelectDefeBefore(Integer orderId);
	
	
	String DefeDefeAfertime(Integer orderId);
	
	
	String SelectLoanStatus(Integer companyId);
	
	
	Integer SelectMaxMoney(Integer companyId);
	
	
	BigDecimal SumPayMoney(Orderdetails ord);
}
