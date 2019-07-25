package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import redis.clients.jedis.BinaryClient.LIST_POSITION;

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
	
	
	List<Integer> SelectNodetId(Orderdetails order);
	
	
	List<Orderdetails> AOrderDetails(Orderdetails order);
	
	
	List<Collection> DateNum(Orderdetails order);
	
	
	List<Collection> CollDateNum(Orderdetails order); 
	
	
	Integer UserOrderStatuSame(Orderdetails order);
	
	
	List<Collection> MemberName(Orderdetails order);
	
	
	List<Collection> MemberNum(Orderdetails order);
	
	
	List<Orderdetails> MyOrderdue(Orderdetails order);
	
	
	Integer MyOrderNum(Orderdetails order);
	
	
	Integer OverdueStatu(Orderdetails overdue);
	
	
	Integer AddOverdue(Overdue ov);
	
	
	Integer connectedNum(Orderdetails order);
	
	
	Integer OrderIdNum(Orderdetails order);


	List<Integer> OverIdNum(Orderdetails order);
	
	
	Integer StatusOrders(Orderdetails order);
	
	
	Integer UserDianlNum(Orderdetails order);
	
	
	Integer Userphonestaus(Orderdetails order);

	
	Integer UserOrderStatu(Orderdetails order);
	
	
	Integer UserNumOrder(Orderdetails order);
	
	
	Integer YiHuanOrdersTotalCount(Orderdetails order);
	
	
	List<Orderdetails> YiHuanOrders(Orderdetails order);
	
	
	Integer CollecOrdersTotalCount(Orderdetails order);
	
	
	List<Orderdetails> CollecOrders(Orderdetails orders);
	
	
	Integer Phone_num(Orderdetails order);
	
	
	Integer HuaiZhangOrdersTotalCount(Orderdetails order);
	
	
	List<Orderdetails> HuaiZhangOrders(Orderdetails order);
	
	
	Integer OrderDefeNum(Orderdetails order);
	
	
	Integer UserOverdue(Overdue ov);
}
