package com.zhita.service.manage.operational;

import java.util.Map;
import com.zhita.model.manage.Orderdetails;


public interface OperationalService {
	
	
	Map<String, Object> PlatformsNu(Orderdetails order);
	
	
	Map<String, Object> HuanKuan(Orderdetails order);
	
	
	Map<String, Object> CollectionData(Orderdetails orde);
	
	
	Map<String, Object> OrderBudget(Orderdetails orde);

}
