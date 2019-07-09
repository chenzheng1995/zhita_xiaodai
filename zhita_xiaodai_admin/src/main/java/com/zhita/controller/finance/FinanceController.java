package com.zhita.controller.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Accountadjustment;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.PaymentRecord;
import com.zhita.model.manage.Undertheline;
import com.zhita.service.manage.finance.FinanceService;



@Controller
@RequestMapping("fina")
public class FinanceController {

	
	@Autowired
	private FinanceService fianser;
	
	
	
	
	/**
	 * 放款实时流水
	 * @param payrecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Allpayment_record")
	public Map<String, Object> Allpayment(PaymentRecord payrecord){
		return fianser.AllPaymentrecord(payrecord);
	}
	
	
	
	
	
	/**
	 * 放款实时流水订单详情
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("PaymentOrder")
	public Map<String, Object> OrderPayment(Integer orderId){
		return fianser.OrderPayment(orderId);
	}
	
	
	
	
	/**
	 * 还款实时流水
	 * @param payrecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("HuanKuan")
	public Map<String, Object> HuanKuan(PaymentRecord payrecord){
		return fianser.Huankuan(payrecord);
	}
	
	
	
	
	
	/**
	 * 渠道查询
	 * @param compayId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("ThirdpatyAll")
	public Map<String, Object> Thirdpaty(Integer compayId){
		return fianser.ThirdpatyAll(compayId);
	}
	
	
	
	
	
	/**
	 * 添加线上调账
	 * @param acc
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddAcount")
	public Map<String, Object> AddAccount(Accountadjustment acc){
		return fianser.Accountadjus(acc);
	}
	
	
	
	
	
	/**
	 * 根据订单编号查询订单详情
	 * @param orderNumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping("OrderAcount")
	public Map<String, Object> OrderAccount(String orderNumber){
		return fianser.OrderAccount(orderNumber);
	}
	
	
	
	
	
	
	
	/**
	 * 查询线上调账订单
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectOrderAccount")
	public Map<String, Object> SelectOrderAccount(Orderdetails ordetail){
		return fianser.SelectOrderAccount(ordetail);
	}
	
	
	
	
	
	
	/**
	 * 线上已还清订单
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectNoMoney")
	public Map<String, Object> SelectNoMoneyAccount(Orderdetails ordetail){
		return fianser.SelectNoMoney(ordetail);
	}
	
	
	
	
	
	
	
	/**
	 * 线上未还清订单
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectOkMoney")
	public Map<String, Object> OkMoneyAccount(Orderdetails ordetail){
		return fianser.SelectOkMoney(ordetail);
	}
	
	
	
	
	
	
	/**
	 * 新增线下调账
	 * @param unde
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddUndert")
	public Map<String, Object> AddUndert(Undertheline unde){
		return fianser.AddUnderthe(unde);
	}
	
	
	
	
	
	/**
	 * 线下已换清单记录表
	 * @param ordetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Orderoffline")
	public Map<String, Object> OrderOffine(Orderdetails ordetail){
		return fianser.Selectoffine(ordetail);
	}
	
	
	
	
	
	
	
}
