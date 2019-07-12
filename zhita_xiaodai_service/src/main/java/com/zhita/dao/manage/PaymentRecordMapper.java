package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Bankdeduction;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.model.manage.Undertheline;

public interface PaymentRecordMapper {
	
	


    int deleteByPrimaryKey(Integer id);

    int insert(Payment_record record);

    int insertSelective(Payment_record record);

    Payment_record selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Payment_record record);

    int updateByPrimaryKey(Payment_record record);
    
    
    Integer TotalCountPayment(Payment_record record);
    
    
    List<Payment_record> PaymentAll(Payment_record record);
    
    
    Integer RepaymentTotalCount(Payment_record record);
    
    
    List<Payment_record> RepaymentAll(Payment_record record);
    
    
    Orderdetails SelectPaymentOrder(Orderdetails orderNumber);
    
    
    Orderdetails OrdeRepayment(Orderdetails orderNumber);
    
    
    Integer AddCAccount(Accountadjustment acount);
    
    
    Integer AccountTotalCount(Orderdetails orde);
    
    
    List<Accountadjustment> AllAccount(Orderdetails orde);
    
    
    List<Accountadjustment> AllStatu(Orderdetails orde);
    
    
    List<Accountadjustment> AllNotMoneyStatu(Orderdetails orde);
    
    
    List<Thirdparty_interface> SelectThird(Integer companyId);
    
    
    Integer AddUndertheline(Undertheline unde);
    
    
    Integer SelectUnderthTotalCount(Orderdetails order);

    
    List<Undertheline> AllUnderthe(Orderdetails order);
    
    
    Integer BankDeduOrderNum(Bankdeduction bank);
    
    
    List<Orderdetails> BankDeduOrder(Bankdeduction bank);
    
    
    List<Bankdeduction> BanAll(Integer orderId);
    
    
    Integer DelayTatolCount(Bankdeduction bank);
    
    
    List<Bankdeduction> DelayStatisc(Bankdeduction bank);
    
    
    Bankdeduction OneBank(Bankdeduction banl);
    
    
    BigDecimal YanMoney(Bankdeduction banl);
    
    
    List<Integer> Sys_userIds(Integer companyId);
}