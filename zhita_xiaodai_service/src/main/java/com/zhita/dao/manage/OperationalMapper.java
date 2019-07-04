package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;

public interface OperationalMapper {
	
	
	List<Orders> OrderNum(Orderdetails orders);
	
	
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
	
	
	List<Orderdetails> CollectionDatas(Orderdetails orde);
	
	
	List<Orderdetails> CollectionDataNum(Orderdetails orde);
	
	
	List<Integer> CollectionNumberofreminders(Orderdetails ord);
	
	
	Integer SelecNumberCollection(Orderdetails ord);
	

	
	
	List<Orders> SelectOrderBudeNum(Orderdetails ord);
	
	
	List<Orders> SelectOrderBude(Orderdetails ord);
	
	
	
}
