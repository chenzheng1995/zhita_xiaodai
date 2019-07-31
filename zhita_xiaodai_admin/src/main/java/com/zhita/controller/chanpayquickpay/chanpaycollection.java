package com.zhita.controller.chanpayquickpay;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeduction;
import com.zhita.model.manage.MouthBankName;
import com.zhita.model.manage.Orderdetails;
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
	public Map<String, Object> MouthBank(String bank){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Orderdetails> orde = JSON.parseArray(bank, Orderdetails.class);
		for(int i=0;i<orde.size();i++){
			Bankcard ba = new Bankcard();
			ba.setCompanyId(orde.get(i).getCompanyId());
			ba.setUserId(orde.get(i).getUserId());
			Bankcard ban = chanser.SelectBank(ba);//获取当前用户的银行卡信息
			String LiceneceNo = ster.IDnumber(orde.get(i).getUserId());
			MouthBankName mou = ster.SendBankcomm(ban.getBankcardTypeName(), orde.get(i).getBankcardName(), orde.get(i).getTrueName(), orde.get(i).getTransAmt(), 
					LiceneceNo, orde.get(i).getPhone(), orde.get(i).getSys_userId(),
					orde.get(i).getDeductionproportion(), orde.get(i).getOrderNumber(), orde.get(i).getOrderId(), orde.get(i).getUserId());//扣款接口
			NumberFormat numberFormat = NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(2);
			String data = (numberFormat.format((float) orde.get(i).getDeductionproportion() / (float) 100));
			BigDecimal da = new BigDecimal(data);
			orde.get(i).setTransAmt(String.valueOf(orde.get(i).getSurplus_money().multiply(da)));
			String as = mou.getOriginalRetCode();
			Bankdeduction bas = new Bankdeduction();
			bas.setOrderId(orde.get(i).getOrderId());
			bas.setDeduction_time(mou.getTime());
			bas.setUserId(orde.get(i).getUserId());
			if(as != "000000"){
				bas.setDeductionstatus("扣款成功");
			}else{
				bas.setDeductionstatus("扣款失败");
			}
			ster.UpdateBank(bas);
			
		}
		map.put("code", "200");
		map.put("desc", "已扣款");
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
	public Map<String, Object> AllBankDetail(Bankdeduction bank){
		return ster.AllBankdetail(bank);
	}
	
	
	/**
	 * orderNumber
	 * companyId
	 * 一键扣款详情
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllDetails")
	public Map<String, Object> AllDetails(Bankdeduction bank){
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
