package com.zhita.service.manage.finance;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.Offlinedelay;
import com.zhita.model.manage.Offlinetransfer;
import com.zhita.model.manage.Offlinjianmian;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.PriceTongji;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Thirdpricefind;

public interface FinanceService {
	
	
	Map<String, Object> AllPaymentrecord(Payment_record payrecord);
	
	
	Map<String, Object> Huankuan(Payment_record payrecord);

	
	Map<String, Object> OrderPayment(Orderdetails orderId);
	
	
	Map<String, Object> Accountadjus(Accountadjustment acc);
	
	
	Map<String, Object> OrderAccount(Orderdetails orderNumber);
	
	
	Map<String, Object> SelectOrderAccount(Orderdetails ordetail);
	
	
	Map<String, Object> SelectNoMoney(Orderdetails ordetail);
	
	
	Map<String, Object> SelectOkMoney(Orderdetails ordetail);
	
	
	Map<String, Object> Selectoffine(Orderdetails ordetail);
	
	
	Map<String, Object> ThirdpatyAll(Integer compayId);
	
	
	Map<String, Object> AddUnderthe(Offlinetransfer unde);
	
	
	Map<String, Object> SelectBankDeductOrders(Bankdeductions bank);
	
	
	Map<String, Object> AllBank(Integer orderId);
	
	
	Map<String, Object> AddBank(Bankdeductions banl);
	
	
	Map<String, Object> AllDelayStatis(Bankdeductions banl);
	
	
	Map<String, Object> Financialover(Bankdeductions banl);
	
	
	Map<String, Object> AddOffJianmian(Offlinjianmian off);
	
	
	Map<String, Object> SelectXiaOrder(Orderdetails ord);
	
	
	Map<String, Object> RepaymentAll(Integer compayId);
	
	
	Map<String, Object> CompanyDelay(Integer companyId);
	
	
	Map<String, Object> AddDelay(Offlinedelay off);
	
	
	Map<String, Object> Delaylabor(Offlinedelay of);
	
	 //后台管理---查询所有
    List<Thirdpricefind> queryall(Integer companyid);
    
    //后台管理----修改价格
    int updateprice(BigDecimal price,Integer id);
    
    //后台管理---费用统计
    Map<String, Object> pricetongji(Integer companyId,Integer page,String starttime,String endtime)throws ParseException;
    
    
    Map<String, Object> SelectAccOrders(String orderNumber);
    
    
    Map<String, Object> DeleteAccorders(Integer id);
    
    
    List<Payment_record> AllPaymentrecordexport(Payment_record pay);
    
    
    List<Payment_record> AllHuankuanexport(Payment_record repay);
    
    
    List<Accountadjustment> SelectOrderAccountSc(Orderdetails ordetail);
    
    
    List<Accountadjustment> SelectNoMoneyAc(Orderdetails orderdet);
    
    
    List<Accountadjustment> SelectOkMoneyAc(Orderdetails orderdet);
    
    
    List<Bankdeductions> AllDelayStatisAc(Bankdeductions bank);
    
    
    List<Offlinedelay> DelaylaborAc(Offlinedelay of);
    
    
    List<Offlinjianmian> SelectXiaOrderAc(Orderdetails ord);
}
