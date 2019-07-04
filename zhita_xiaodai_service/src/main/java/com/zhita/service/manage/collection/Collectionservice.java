package com.zhita.service.manage.collection;

import java.util.Map;

import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Orderdetails;

public interface Collectionservice {
	
	Map<String, Object> allBeoverdueConnection(Collection coll);
	
	
	Map<String, Object> Collectionmember(Integer companyId);
	
	
	Map<String, Object> UpdateColl(Collection col);
	
	
	Map<String, Object> BeoverdueYi(Orderdetails order);
	
	
	Map<String, Object> Colldetails(Orderdetails order);
	
	
	Map<String, Object> Collectionmemberdetails(Collection coll);
	
	
	Map<String, Object> FenpeiWeiCollection(Collection col);
	
	
	Map<String, Object> YiCollection(Collection col);
	
	
	Map<String, Object> AddColloetails(Collectiondetails col);
	
	
	Map<String, Object> AllCollectiondetail(Integer orderId);
	
	
	Map<String, Object> CollectionmemberUser(Collection collectio);

}
