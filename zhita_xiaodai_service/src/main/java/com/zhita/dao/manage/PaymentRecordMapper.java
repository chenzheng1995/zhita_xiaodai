package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.PaymentRecord;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.model.manage.Undertheline;

public interface PaymentRecordMapper {
	
	


    int deleteByPrimaryKey(Integer id);

    int insert(PaymentRecord record);

    int insertSelective(PaymentRecord record);

    PaymentRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PaymentRecord record);

    int updateByPrimaryKey(PaymentRecord record);
    
    
    Integer TotalCountPayment(PaymentRecord record);
    
    
    List<PaymentRecord> PaymentAll(PaymentRecord record);
    
    
    Integer RepaymentTotalCount(PaymentRecord record);
    
    
    List<Repayment> RepaymentAll(PaymentRecord record);
    
    
    Orderdetails SelectPaymentOrder(Integer orderId);
    
    
    Orderdetails OrdeRepayment(String orderNumber);
    
    
    Integer AddCAccount(Accountadjustment acount);
    
    
    Integer AccountTotalCount(Orderdetails orde);
    
    
    List<Accountadjustment> AllAccount(Orderdetails orde);
    
    
    List<Accountadjustment> AllStatu(Orderdetails orde);
    
    
    List<Accountadjustment> AllNotMoneyStatu(Orderdetails orde);
    
    
    List<Thirdparty_interface> SelectThird(Integer companyId);
    
    
    Integer AddUndertheline(Undertheline unde);
    
}