package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Overdue;

public interface PostloanorderMapper {

	

	List<Orderdetails> allOrderdetails(Orderdetails order);
	
	
	Integer totalCount(Orderdetails order);
	
	
	List<Orderdetails> allBeoverdueOrderdetails(Orderdetails order);
	
	
	Integer BeoverduetotalCount(Orderdetails order);
	
	
	List<Orderdetails> SelectOrderDetails(Orderdetails order);
	
	
	Integer WeiNum(String shouldReturnTime);
	
	
	List<Integer> CollMemberId(Integer companyId);
	
	
	List<Integer> SelectNodetId(@Param("ids")List<Integer> ids);
	
	
	List<Orderdetails> OrderDetails(Orderdetails order);
	
	
	List<Collection> DateNum(Orderdetails order);
	
	
	List<Collection> CollDateNum(Orderdetails order); 
	
	
	List<Collection> MemberName(Orderdetails order);
	
	
	List<Collection> MemberNum(Orderdetails order);
	
	
	List<Orderdetails> MyOrderdue(Orderdetails order);
	
	
	Integer MyOrderNum(Orderdetails order);
	
	
	Integer OverdueStatu(Orderdetails overdue);
	
	
	Integer AddOverdue(Overdue ov);
}
