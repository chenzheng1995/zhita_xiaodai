package com.zhita.service.manage.postloanorders;

import io.protostuff.StringSerializer.STRING;

import java.util.Map;

import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Overdue;

public interface Postloanorderservice {
	
	Map<String, Object> allpostOrders(Orderdetails details);
	
	
	Map<String, Object> allpostOrdersBeoverdue(Orderdetails details);
	
	
	Map<String, Object> SelectCollection(Orderdetails order);

	
	Map<String, Object> CollectionOrderdet(Orderdetails order);
	
	
	Map<String, Object> CollectionRecovery(Orderdetails order);
	
	
	Map<String, Object> OverdueUser(Orderdetails order);
	
	
	Map<String, Object> MyOverdues(Orderdetails collectionMemberId);
	
	
	Map<String, Object> UpdateOverdue(Orderdetails order);
	
	
	Map<String, Object> UpdateOrder(Orderdetails order);
	
	
	Map<String, Object> YiHuanOrders(Orderdetails order);
	
	
	Map<String, Object> HuaiZhangOrders(Orderdetails order);
	
	
	Map<String, Object> CollecOrders(Orderdetails order);
	
	
	Map<String, Object> WanchengUser(Overdue ov);
}
