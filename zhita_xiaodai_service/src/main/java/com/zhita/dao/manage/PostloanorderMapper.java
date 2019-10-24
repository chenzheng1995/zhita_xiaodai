package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;
import com.zhita.model.manage.Collection;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Overdue;

public interface PostloanorderMapper {

	

	List<Orderdetails> allOrderdetails(Orderdetails order);
	
	
	Integer totalCount(Orderdetails order);
	
	
	List<Orderdetails> allBeoverdueOrderdetails(Orderdetails order);
	
	
	Integer BeoverduetotalCount(Orderdetails order);
	
	
	List<Orderdetails> SelectOrderDetails(Orderdetails order);
	
	
	Integer WeiNum(Orderdetails companyId);
	
	
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
	
	
	List<Orderdetails> HuaiZhangOrdersAA(Orderdetails order);
	
	
	Integer OrderDefeNum(Orderdetails order);
	
	
	Integer UserOverdue(Overdue ov);
	
	
	List<Integer> OvOrderId(Integer companyId);
	
	
	
	Deferred OneDeferred(Orderdetails orde);
	
	
	Collection CollTimeData(Orderdetails orde);
	
	
	List<Collection> CollectionYIData(Orderdetails orde);
	
	
	Integer CoMentLLection(Orderdetails orde);
	
	
	BigDecimal ShijiMoney(Integer orderId);
	
	
	List<Integer> YiHuanOrderId(Orderdetails order);
	
	
	List<Integer> DefeOrderId(Orderdetails order);
	
	
	BigDecimal BankMoney(Integer orderId);
	
	
	BigDecimal JianMianmoney(Integer orderId);
	
	
	BigDecimal HuankuanMoney(Integer orderId);
	
	
	Integer SelectHuan(Integer companyId);
	
	
	
	Integer YiHuanOrdersTotalCountOO(Orderdetails order);
	
	
	
	Orderdetails SelectQianshouldReapyMoney(Integer orderId);
	
	
	List<Orderdetails> YiHuanOrdersAc(Orderdetails order);
	
	
	List<Orderdetails> HuaiZhangOrdersAAAc(Orderdetails order);
	
	
	List<Orderdetails> SelectOrderDetailsAc(Orderdetails order);
	
	
	List<Orderdetails> AOrderDetailsAc(Orderdetails order);
	
	
	List<Orderdetails> YiHuanOrdersOff(Orderdetails order);
	
	
	Integer WeiNumOrder(Orderdetails order);
	
	
	Integer UserNum(Integer userId);
	
	
	Integer DefeUserNum(Integer userId);
	
	
	Integer DeleteOverdue(Integer id);
	
	
	Integer SelectOrderDetailsNum(Orderdetails order);
	
	
	Integer StatusnOOrders(Orderdetails orders);
	
}
