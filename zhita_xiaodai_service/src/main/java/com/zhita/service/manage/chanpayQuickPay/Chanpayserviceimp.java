package com.zhita.service.manage.chanpayQuickPay;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.util.Timestamps;


@Service
public class Chanpayserviceimp implements Chanpayservice{
	
	
	@Autowired
	private StatisticsDao stdao;

	
	/**
	 * 添加银行卡
	 */
	@Override
	public Integer AddBankcard(Bankcard bank) {
		return stdao.AddBankcard(bank);
	}

	/**
	 * 查询银行卡ID
	 */
	@Override
	public Integer SelectTrxId(Bankcard bank) {
		return stdao.SelectTrxId(bank);
	}

	
	/**
	 * 修改认证状态
	 */
	@Override
	public Integer UpdateChanpay(Integer id) {
		return stdao.UpdateStatu(id);
	}

	
	/**
	 * 添加还款记录
	 */
	@Override
	public Integer AddRepayment(Repayment repay) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		try {
			repay.setRepaymentDate(Timestamps.dateToStamp(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		repay.setPaymentbtiao("畅捷支付");
		repay.setOrderid(stdao.SelectOrderId(repay.getOrderNumber()));
		System.out.println(repay.getOrderid());
		return stdao.AddRepay(repay);
	}

	
	/**
	 * 修改订单状态
	 */
	@Override
	public Integer UpdateOrders(Orders ord) {
		ord.setId(stdao.SelectOrderId(ord.getOrderNumber()));
		Integer id = stdao.UpdateOrders(ord);
		if(id != null){
			Integer num = stdao.SelectUserdelayTimes(ord);
			Integer delaytimes = num+1;
			ord.setChenggNum(delaytimes);
			stdao.UpdateUser(ord);
		}else{
			return 0;
		}
		return 200;
	}

	@Override
	public Integer AddPayment_record(Payment_record pay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer AddDeferred(Deferred defe) {
		defe.setDeleted("0");
		defe.setOrderid(stdao.SelectOrderId(defe.getOrderNumber()));
		return stdao.AddDeferred(defe);
	}
	
	

}
