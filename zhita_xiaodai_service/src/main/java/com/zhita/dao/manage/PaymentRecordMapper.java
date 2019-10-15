package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Deferred_settings;
import com.zhita.model.manage.Discardorderdetails;
import com.zhita.model.manage.Discardorders;
import com.zhita.model.manage.Loan_setting;
import com.zhita.model.manage.Offlinedelay;
import com.zhita.model.manage.Offlinetransfer;
import com.zhita.model.manage.Offlinjianmian;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Repayment_setting;
import com.zhita.model.manage.Undertheline;
import com.zhita.model.manage.User;



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
    
    
    List<Loan_setting> SelectThird(Integer companyId);
    
    
    Integer AddUndertheline(Offlinetransfer unde);
    
    
    Integer SelectUnderthTotalCount(Orderdetails order);

    
    List<Offlinetransfer> AllUnderthe(Orderdetails order);
    
    
    Integer BankDeduOrderNum(Bankdeductions bank);
    
    
    List<Orderdetails> BankDeduOrder(Bankdeductions bank);
    
    
    List<Bankdeductions> BanAll(Integer orderId);
    
    
    Integer DelayTatolCount(Bankdeductions bank);
    
    
    List<Bankdeductions> DelayStatisc(Bankdeductions bank);
    
    
    Bankdeductions OneBank(Bankdeductions banl);
    
    
    BigDecimal YanMoney(Bankdeductions banl);
    
    
    List<Integer> Sys_userIds(Integer companyId);
    
    
    Integer AddOffJianMian(Offlinjianmian off);
    
    
    List<Offlinjianmian> XiaOrder(Orderdetails order);
    
    
    List<Integer> XiaTotalCount(Orderdetails ord);
    
    
    List<Repayment_setting> SelectRepay(Integer companyId);
    
    
    Integer UpdateOrderType(Integer orderId);
    
    
    Bankdeductions SelectBank(Bankdeductions ban);
    
    
    Deferred_settings OneCompanyDeferr(Integer companyId);
    
    
    Deferred DeleteNumMoney(Integer orderId);
    
    
    Integer AddDelay(Offlinedelay of);
    
    
    Integer OffTotalCount(Offlinedelay of);
    
    
    List<Offlinedelay> Allofflinedelay(Offlinedelay of);
    
    
    Bankdeductions DefeRRe(Bankdeductions ban);
    
    
    Bankdeductions BankdeduCtionsData(Bankdeductions banl);
    
    
    Bankdeductions OnDefe(Bankdeductions banl);
    
    
    Bankdeductions Onrepayment(Bankdeductions banl);
    
    
    Bankdeductions OneCollection(Bankdeductions banl);
    
    
    Bankdeductions OneMoney(Bankdeductions banl);
    
    
    Bankdeductions BankMoney(Bankdeductions banl);
    
    
    String LoanName(Integer channel);
    
    
    String DeferrAdefe(Integer orderid);
    
    
    BigDecimal OrderMoneySum(Integer orderId);
    
    
    String RepaymentName(Integer channel);
    
    
    Integer UpdateOrdermoney(Accountadjustment acc);
    
    
    Integer UpdateOrderTime(Offlinedelay off);
    
    
    Integer OrdersStatus(Offlinjianmian off);
    
    
    BigDecimal SelectSumamountmoney(Integer orderId);
    
    
    Accountadjustment Maxtotalamount(Integer orderId);
    
    
    List<Accountadjustment> SelectAccOrders(String orderNumber);
    
    
    Integer UserDefeNum(Integer orderId);
    
    
    Integer UpdateOrdermoneyTime (Accountadjustment acc);
    
    
    User SelectOneUser(Integer userId);
    
    
    Integer OrdersStatusAA(Accountadjustment acc);
    
    
    String OrderStatuOrder(Integer orderId);
    
    
    String SelectShouReturnTime(Integer orderId);
    
    
    Integer DeleteOrderNumber(String orderNumber);
    
    
    Integer DeleteOrderDetailsNumber(String orderNumber);
    
    
    Bankdeductions Xianshang(Bankdeductions banl);
    
    
    Bankdeductions XianJianmian(Bankdeductions banl);
    
    
    Orders OneOrdersUser(Integer orderId);
    
    
    Bankdeductions BankMoneys(Bankdeductions banl);
    
    
    Bankdeductions DefeMoeny(Bankdeductions ban);
    
    
    Integer OrderStatus(Integer orderId);
    
    
    Orderdetails OneOrderdetails(Integer id);
    
    
    Orders OneOrders(String orderNumber);
    
    
    Integer Adddiscardorders(Orders order);
    
    
    Integer Adddiscardordertails(Orderdetails orderdetils);
    
    
    String OrderShouldTime(Integer id);
    
    
    String DefeTime(Integer orderId);
    
    
    String offDefetime(Integer orderId);
    
    
    String RepaymentStatus(Integer orderId);
    
    
    String DefeStatus(Integer orderId);
    
    
    Integer PaymentStatus(Integer orderId);
    
    
    String SelectPaymentStatus(Integer orderId);
    
    
    Accountadjustment SelectOrderId(Integer id);
    
    
    
    Integer UpdateOrdertails(Orderdetails orderdetails);
    
    
    
    Integer DeleteOrderAcc(Integer id);
    
    
    Integer selectPatyId(String name);
    
    
    List<Payment_record> PaymentAllAc(Payment_record pay);
    
    
    List<Payment_record> RepaymentAllAc(Payment_record pay);
    
    
    List<Accountadjustment> AllAccountAc(Orderdetails orderi);
    
    
    List<Accountadjustment> AllStatuAc(Orderdetails orderi);
    
    
    List<Accountadjustment> AllNotMoneyStatuAc(Orderdetails orderi);
    
    
    List<Offlinedelay> AllofflinedelayAc(Offlinedelay of);
    
    
    List<Offlinjianmian> XiaOrderAc(Orderdetails order);
    
    
    Orderdetails SelectCollectionMoney(Integer orderId);
    
    
    String getPhone(String orderNumber);
    
    
    Deferred getDelete(String orderNumber);
    
    
    String getOrderNumberDefe(Integer id);
    
    
    Integer getUserid(Integer id);
    
    
    BigDecimal getOrdersTechnicalserviceMoney(Integer orderId);
    
    
    Repayment getRepayment(String reoaybtai);
    
    
    Integer SelectDisorderId(String orderNumber);
    
}