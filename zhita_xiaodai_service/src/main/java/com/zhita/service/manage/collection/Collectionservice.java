package com.zhita.service.manage.collection;


import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Collectiondetails;
import com.zhita.model.manage.Orderdetails;

public interface Collectionservice {
	
	
	Map<String, Object> Collectionmember(Integer companyId);
	
	
	Map<String, Object> UpdateColl(Collection col);
	
	
	Map<String, Object> BeoverdueYi(Orderdetails order);
	
	
	Map<String, Object> Colldetails(Orderdetails order);
	
	
	Map<String, Object> Collectionmemberdetails(Collection coll);
	
	
	Map<String, Object> FenpeiWeiCollection(Collection col);
	
	
	Map<String, Object> YiCollection(Collection col);
	
	
	Map<String, Object> AddColloetails(Collection col);
	
	
	Map<String, Object> CollectionmemberUser(Collection collectio);

	
	Map<String, Object> JieShuCollection(Integer orderId);
	
	
	Map<String, Object> AddCollOrders(Collectiondetails col);
	
	
	Map<String, Object> allBeoverdueConnection(Collection col);
	
	 
	List<Orderdetails> allBeoverdueConnectionCollection(Collection col);
	
	
	Integer SelectTotalCount(Collection col);
	
	
	List<Orderdetails> BeoverdueYiCollection(Orderdetails col);
	
	
	List<Collection> ColldetailsYiCollection(Collection coll);
	
	
	List<Collection> CollectionmemberUserlv(Collection coll);
	
	
	List<Orderdetails> FenpeiWeiCollectionAc(Collection coll);
	
	
	List<Orderdetails> YiCollectionAc(Collection col);
	
	
}
