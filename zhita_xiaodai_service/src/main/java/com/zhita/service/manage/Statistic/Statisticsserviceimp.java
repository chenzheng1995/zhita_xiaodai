package com.zhita.service.manage.Statistic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.BaseParameter;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.dao.manage.PaymentRecordMapper;
import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeductions;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.MouthBankName;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;
import com.zhita.util.PageUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;


@Service
public class Statisticsserviceimp extends BaseParameter implements Statisticsservice {
	
	
	@Autowired
	private StatisticsDao sdao;
	
	
	
	@Autowired
	private PaymentRecordMapper padao;
	

	
	/**
	 * BankCommonName   银行卡
	 * AcctNo   卡号。
	 * AcctName  姓名
	 * TransAmt  金额
	 * LiceneceNo  身份证号
	 * Phone  手机号
	 * sys_userId   操作人
	 * deductionproportion  扣款比例
	 * orderNumber   订单ID
	 */
	@Override
	public MouthBankName SendBankcomm(String BankCommonName,String AcctNo,String AcctName,String TransAmt,String LiceneceNo,
			String Phone,Integer sys_userId,Integer deductionproportion,String orderNumber,Integer orderId,Integer userId) {
			Map<String, String> map = this.requestBaseParameter();
			Bankdeductions ban = new Bankdeductions();
			ban.setSys_userId(sys_userId);
			ban.setDeductionproportion(deductionproportion);
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			ban.setDeduction_time(sim.format(new Date()));
			String time = sim.format(new Date());
			ban.setOrderId(orderId);
			ban.setUserId(userId);
			BigDecimal deduction_money = new BigDecimal(TransAmt);
			ban.setDeduction_money(deduction_money);
			sdao.Addbankdeduction(ban);
			
			map.put("TransCode", "T20000"); // 交易码
			map.put("OutTradeNo", orderNumber); // 商户网站唯一订单号
			map.put("CorpAcctNo", ""); // 企业账号（可空）
			map.put("BusinessType", "0"); // 业务类型
			map.put("ProtocolNo", ""); // 协议号（可空）        
			map.put("BankCommonName", BankCommonName); // 通用银行名称
			map.put("AccountType", "00"); // 账户类型
			
			//**************  银行四要素信息需要用真实数据  *******************
			map.put("AcctNo", ChanPayUtil.encrypt(AcctNo,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号
			map.put("AcctName", ChanPayUtil.encrypt(AcctName,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
			map.put("TransAmt", TransAmt);
			map.put("LiceneceType", "01");
			map.put("LiceneceNo", ChanPayUtil.encrypt(LiceneceNo,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
			map.put("Phone", ChanPayUtil.encrypt(Phone,
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
			map.put("CorpPushUrl", "http://172.20.11.16");
			map.put("PostScript", "用途");

			String ReturnMoth = ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
					BaseConstant.MERCHANT_PRIVATE_KEY);
			MouthBankName mo = JSON.parseObject(ReturnMoth, MouthBankName.class);
			mo.setTime(time);
			return mo;
		}

	@Override
	public Map<String, Object> AllCollection(Orderdetails order) {
		TuoMinUtil tm = new TuoMinUtil();
		PhoneDeal p = new PhoneDeal();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = sdao.OrderCollectionNum(order.getCompanyId());
		PageUtil pages = new PageUtil(order.getPage(),totalCount);
		order.setPage(pages.getPage());
			
			System.out.println(order.getDeferAfterReturntimeStatu_time()+order.getDeferAfterReturntimeEnd_time());
			if(order.getStatu_time() != null && order.getStatu_time() != "" && order.getEnd_time() != "" && order.getEnd_time()!= null){
				try {
					order.setStatu_time(Timestamps.dateToStamp1(order.getStatu_time()));
					order.setEnd_time(Timestamps.dateToStamp1(order.getEnd_time()));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		
			
		List<Orderdetails> ordeBank = sdao.AllBanl(order);
		for(int i=0;i<ordeBank.size();i++){
			if(ordeBank.get(i).getDeduction_money()==null){
				ordeBank.get(i).setDeduction_money(new BigDecimal(0));
			}
			ordeBank.get(i).setOrder_money(ordeBank.get(i).getRealityBorrowMoney().add(ordeBank.get(i).getInterestPenaltySum()));
			ordeBank.get(i).setOrderCreateTime(Timestamps.stampToDate(ordeBank.get(i).getOrderCreateTime()));
			ordeBank.get(i).setDeferAfterReturntime(Timestamps.stampToDate(ordeBank.get(i).getDeferAfterReturntime()));
			ordeBank.get(i).setSurplus_money(ordeBank.get(i).getShouldReapyMoney());
			String phon = p.decryption(ordeBank.get(i).getPhone());
			ordeBank.get(i).setPhone(tm.mobileEncrypt(phon));
		}
		map.put("Orderdetails", ordeBank);
		map.put("PageUtil", pages);
		return map;
	}

	@Override
	public String IDnumber(Integer userId) {
		return sdao.UserAll(userId);
	}

	@Override
	public Integer UpdateBank(Bankdeductions ban) {
		return sdao.UpdateBank(ban);
	}

	@Override
	public Map<String, Object> AllBankdeduData(Bankcard ban) {
		System.out.println();
		if(ban.getStatu_time()!=null && ban.getStatu_time()!="" && ban.getEnd_time()!=null && ban.getEnd_time()!=""){
			try {
				ban.setStatu_time(Timestamps.dateToStamp1(ban.getStatu_time()));
				ban.setEnd_time(Timestamps.dateToStamp1(ban.getEnd_time()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = sdao.SelectTotalCount(ban);
		PageUtil pages = new PageUtil(ban.getPage(), totalCount);
		ban.setPage(pages.getPage());
		List<Bankdeductions> bans = sdao.AllBank(ban);
		for(int i=0;i<bans.size();i++){
			bans.get(i).setCompanyId(ban.getCompanyId());
			bans.get(i).setChengNum(sdao.ChenggNum(bans.get(i)));//已选扣款用户总数
			bans.get(i).setShiNum(bans.get(i).getUserNum()-bans.get(i).getChengNum());//扣款失败用户数
			bans.get(i).setDeduction_money(sdao.ChenggMoney(bans.get(i)));//扣款金额
			bans.get(i).setChengMoney(sdao.SelectChengMoney(bans.get(i)));
			bans.get(i).setDeduction_time(Timestamps.stampToDate(bans.get(i).getDeduction_time()));
		}
		map.put("Bankdeduction", bans);
		map.put("pageutil", pages);
		return map;
	}

	@Override
	public Map<String, Object> AllBankdetail(Bankdeductions bank) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Bankdeductions> b = sdao.AllBan(bank);
		map.put("Bankdeduction", b);
		return map;
	}

	@Override
	public Map<String, Object> AllDetails(Bankdeductions bank) {
		List<Bankdeductions> bans = sdao.SelectBankKoukuan(bank);
		for(int i=0;i<bans.size();i++){
			bans.get(i).setCollection_money(bans.get(i).getRealityBorrowMoney().add(bans.get(i).getInterestPenaltySum()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Bankdeduction", bans);
		return map;
	}

	@Override
	public Integer UpdateOrderSurp(Orders o) {
		return sdao.UpdateOrderSuperl(o);
	}

	@Override
	public Integer SelectUserId(Integer userId) {
		return sdao.SelectUserId(userId);
	}

	
	/**
	 * 查询银行卡ID
	 */
	@Override
	public Integer SelectTrxId(Bankcard bank) {
		return sdao.SelectTrxId(bank);
	}
	
	
	
	/**
	 * 添加银行卡
	 */
	@Override
	public Integer AddBankcard(Bankcard bank) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			bank.setAuthentime(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sdao.AddBankcard(bank);
	}
	
	

	/**
	 * 修改认证状态
	 */
	@Override
	public Integer UpdateChanpay(Integer id) {
		return sdao.UpdateStatu(id);
	}

	@Override
	public Integer deleteBank(Integer userId) {
		return sdao.DeleteChan(userId);
	}

	/**
	 * 添加还款记录
	 */
	@Override
	public Integer AddRepayment(Repayment repay) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		repay.setCardnumber(sdao.Cardnumber(repay.getUserId()));
		try {
			repay.setRepaymentDate(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		repay.setPaymentbtiao("畅捷支付");
		Orders o = sdao.SelectOrderId(repay.getOrderNumber());
		repay.setOrderid(o.getId());
		System.out.println(repay.getOrderid());
		
		return sdao.AddRepay(repay);
	}

	/**
	 * 修改订单状态
	 */
	@Override
	public Integer UpdateOrders(Orders ord) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			ord.setRealtime(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer id = sdao.UpdateOrders(ord);//
		if(id != null){
			Integer delaytimes = 0;
			ord.setChenggNum(delaytimes);
			sdao.UpdateUser(ord);
		}else{
			return 0;
		}
		return 200;
	}

	@Override
	public Integer AddDeferred(Deferred defe) {
		defe.setDeleted("1");
		Orders o = sdao.SelectOrderId(defe.getOrderNumber());
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			defe.setDeferredTime(Timestamps.dateToStamp(sim.format(new Date())));
			defe.setDeferBeforeReturntime((Long.parseLong(Timestamps.dateToStamp2(defe.getDeferBeforeReturntime())))+86399000+"");
			defe.setDeferAfterReturntime((Long.parseLong(Timestamps.dateToStamp2(defe.getDeferAfterReturntime())))+86399000+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		defe.setOrderid(o.getId());
		return sdao.AddDeferred(defe);
	}


	/**
	 * 修改延期状态
	 */
	@Override
	public Integer UpdateDefeOrders(Orders ord) {
		Integer num = sdao.SelectUserdelayTimes(ord.getUserId());
		ord = sdao.SelectOrderId(ord.getOrderNumber());
		ord.setShouldReturnTime(sdao.DefeDefeAfertime(ord.getId()));
		sdao.UpdateDefe(ord);
		Integer delaytimes = num+1;
		System.out.println("数据:"+delaytimes);
		ord.setChenggNum(delaytimes);
		Integer updateId = sdao.DefeOrder(ord);
		System.out.println(updateId);
		Integer a = null;
		if(updateId!=null){
			a=sdao.UpdateUser(ord);
		}else{
			a=0;
		}
		return a;
	}

	@Override
	public void SelectId(String orderNumber) {
	Orders	ord = sdao.SelectOrderId(orderNumber);//根据编号查询订单ID
		System.out.println(ord.getCompanyId()+ord.getId()+ord.getUserId());
	}

	@Override
	public Integer SelectReaymentOrderId(String orderNumber) {
		return sdao.SelectRepaymentOrderId(orderNumber);
	}
		
		

	
	
	
	
	
}
