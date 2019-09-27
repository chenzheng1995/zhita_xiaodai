package com.zhita.service.manage.operational;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;

import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;


public interface OperationalService {
	
	
	Map<String, Object> PlatformsNu(Orderdetails order);
	
	
	Map<String, Object> HuanKuan(Orderdetails order);
	
	
	Map<String, Object> CollectionData(Orderdetails orde);
	
	
	Map<String, Object> OrderBudget(Orderdetails orde);
	
	
	Map<String, Object> AllDrainageOf(Integer companyId);
	
	
	Map<String, Object> AllOverdueclass(Integer companyId);


	BigDecimal getlastLine(int ordersId);
	
	
	List<Orders> PlatformsNuexport(Orderdetails or);
	
	
	List<Orders> HuanKuanexport(Orderdetails or);
	
	
	List<Orders> CollectionDataexport(Orderdetails or);

}
