package com.zhita.controller.chanpayquickpay;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.MouthBankName;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.service.manage.Statistic.Statisticsservice;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;


@Controller
@RequestMapping("Chanpay")
public class chanpaycollection{
	
	
	@Autowired
	private Statisticsservice ster;
	
	
	@Autowired
	private Chanpayservice chanser;
	
	
	
	/**
	 * sys_userId  操作人ID
	 * deductionproportion  扣款比例
	 * companyId  公司ID
	 * 
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SendBank")
	public Map<String, Object> MouthBank(@Param("orderIds")String orderIds,@Param("companyId")Integer companyId,@Param("deductionproportion")Integer deductionproportion,@Param("sys_userId")Integer sys_userId){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("OrderIds:"+orderIds);
		if(orderIds != null && companyId != null && deductionproportion != null && sys_userId != null){
			String[] orderId = orderIds.split(",");//订单ID
			System.out.println(orderId.length);
			if(orderId.length != 0){
				for(int i=0;i<orderId.length;i++){
					Orders ords= new Orders();
					ords.setId(Integer.valueOf(orderId[i]));
					ords.setCompanyId(companyId);
					Orders o = chanser.OneOrders(ords);
					Bankcard ba = new Bankcard();
					ba.setCompanyId(o.getCompanyId());
					ba.setUserId(o.getUserId());
					Bankcard ban = chanser.SelectBank(ba);//获取当前用户的银行卡信息
					
					
					
					String LiceneceNo = ster.IDnumber(o.getUserId());//身份证信息
					
					NumberFormat numberFormat = NumberFormat.getInstance();
					numberFormat.setMaximumFractionDigits(2);
					String data = (numberFormat.format((float) deductionproportion / (float) 100));//算出扣款比例
					BigDecimal da = new BigDecimal(data);
					if(o.getSurplus_money()!=null){//剩余金额不等于空    修改订单状态      剩余金额=  剩余金额 - 扣款金额
						
						o.setTransAmt(String.valueOf(o.getSurplus_money().multiply(da)));//扣款金额
						o.setSurplus_money(o.getSurplus_money().subtract(o.getSurplus_money().multiply(da)));
						ster.UpdateOrderSurp(o);
					}else{//剩余金额不等于空    修改订单状态   剩余金额=   实借金额 - 扣款金额
						o.setSurplus_money(o.getRealityBorrowMoney().subtract(o.getSurplus_money().multiply(da)));
						ster.UpdateOrderSurp(o);
					}
					
					
					
					MouthBankName mou = ster.SendBankcomm(ban.getBankcardTypeName(), ban.getBankcardName(), ban.getCstmrnm(), o.getTransAmt(), 
							LiceneceNo, ban.getTiedCardPhone(), sys_userId,
							deductionproportion, o.getOrderNumber(), o.getId(), o.getUserId());//扣款接口
					
					String as = mou.getOriginalRetCode();
					Bankdeductions bas = new Bankdeductions();
					bas.setOrderId(o.getId());
					bas.setDeduction_time(mou.getTime());
					bas.setUserId(o.getUserId());
					if(as != "000000"){
						bas.setDeductionstatus("扣款成功");
						
					}else{
						bas.setDeductionstatus("扣款失败");
					}
					ster.UpdateBank(bas);
					map.put("code", "200");
					map.put("desc", "已扣款");
				}
			}else{
				map.put("code", "400");
				map.put("desc", "数据异常");
			}
			
			
		}else{
			map.put("code", "0");
			map.put("desc", "数据不能空");
		}
		
			
		
		
		return map;
	}
	
		
	
	
	
	
	/**
	 * 查询银行扣款
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectBankName")
	public Map<String, Object> SelectBanK(Orderdetails order){
		return ster.AllCollection(order);
	}
	
	
	
	/**
	 * orderNumber
	 * companyId
	 * 一键扣款详情
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllBankDetails")
	public Map<String, Object> AllBankDetail(Bankdeductions bank){
		return ster.AllBankdetail(bank);
	}
	
	
	/**
	 * orderNumber
	 * companyId
	 * 扣款清单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllDetails")
	public Map<String, Object> AllDetails(Bankdeductions bank){
		return ster.AllDetails(bank);
	}
	
	
	
	
	/**
	 * 查询扣款记录
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BankdeduDta")
	public Map<String, Object> BankdeduData(Bankcard bank){
		return ster.AllBankdeduData(bank);
	}
}
