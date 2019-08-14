package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.core.annotation.Order;

import com.zhita.model.manage.Drainage_of_platform;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.OverdueClass;
import com.zhita.model.manage.Repayment;

public interface OperationalMapper {
	
	
	Integer OrderNum(Orderdetails orders);
	
	
	List<Orders> Ordersdata(Orderdetails orders);
	
	
	Integer datastatistics(Orderdetails order);
	
	
	Orders OneOrders(String orderCreateTime);
	
	
	Integer Datacollection(Orderdetails ordera);
	
	
	Integer Cuishoudata(Orderdetails ordera);
	
	
	Integer PhoneHuai(Orderdetails ordera);
	
	
	BigDecimal SelectRealborrowing(Orderdetails ordes);
	
	
	BigDecimal SelectRealityAccount(Orderdetails ordes);
	
	
	BigDecimal SelectAmountofbaddebts(Orderdetails ordes);
	
	
	List<Repayment> SelectRepayment(Orderdetails ordes);	
	
 
	List<Repayment> SelectRepaymentNum(Orderdetails ordes);
	
	
	Repayment RepaymentCollection(Orderdetails orde);
	
	
	List<Integer> ConnectionidAll(Orderdetails orde);
	
	
	List<Orders> CollectionDatas(Orderdetails orde);
	
	
	Integer CollectionDataNum(Orderdetails orde);
	
	
	List<Integer> CollectionNumberofreminders(Orderdetails ord);
	
	
	Integer SelecNumberCollection(Orderdetails ord);
	
	
	Orderdetails SelectOperNum(Orderdetails orde);
	
	
	List<Orders> SelectOrderBudeNum(Orderdetails ord);
	
	
	List<Orders> SelectOrderBude(Orderdetails ord);


	BigDecimal getlastLine(int ordersId);


	
	
	List<Drainage_of_platform> AllDrainage(Integer companyId);
	
	
	Orders GesamtbetragderDarlehen(Orders orde);
	
	
	Orders GesamtbetragderRvckzahlung(Orders orde);
	
	
	Orders GesamtbetraguberfalligerBetrag(Orders orde);
	
	
	Orders GesamtbetragderVerbindlichkeiten(Orders orde);
	
	
	Orders Pressformoney(Orderdetails ord);
	
	
	List<Integer> Beoverdue(Orderdetails ord);
	
	
	Repayment SelectNodeRepayment(Orderdetails orderdetails);
	
	
	List<OverdueClass> Overdue_class(Integer companyId);
	
	
	Orders ReayMoney(Orderdetails ord);
	
	
	Orders Gesamtb(Orderdetails ord);
	
	
	Orders CollMoney(Orderdetails ord);
	
	
	Orders HuaiMoney(Orderdetails ord);
	
	
	Orders OrderHuan(Orderdetails ord);
	
	
	Orders OneCollectionData(Orderdetails ord);
	
	
	Integer CollectionOrders(Orderdetails ord);
	
	
	Integer CollectionNumSSA(Orderdetails ord);
	
	
	Integer OrderOKCollection(Orderdetails ord);
	
}
