package com.zhita.service.manage.Statistic;

import java.math.BigDecimal;
import java.text.NumberFormat;
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
import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Bankdeduction;
import com.zhita.model.manage.MouthBankName;
import com.zhita.model.manage.Orderdetails;
import com.zhita.util.PageUtil;


@Service
public class Statisticsserviceimp extends BaseParameter implements Statisticsservice {
	
	
	@Autowired
	private StatisticsDao sdao;
	

	
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
			Bankdeduction ban = new Bankdeduction();
			ban.setSys_userId(sys_userId);
			ban.setDeductionproportion(deductionproportion);
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = sdao.OrderCollectionNum(order.getCompanyId());
		PageUtil pages = new PageUtil(order.getPage(),totalCount);
		order.setPage(pages.getPage());
		List<Orderdetails> ordeBank = sdao.AllBanl(order);
		for(int i=0;i<ordeBank.size();i++){
			ordeBank.get(i).setOrder_money(ordeBank.get(i).getRealityBorrowMoney().add(ordeBank.get(i).getInterestPenaltySum()));
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
	public Integer UpdateBank(Bankdeduction ban) {
		return sdao.UpdateBank(ban);
	}

	@Override
	public Map<String, Object> AllBankdeduData(Bankcard ban) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = sdao.SelectTotalCount(ban);
		PageUtil pages = new PageUtil(ban.getPage(), totalCount);
		ban.setPage(pages.getPage());
		List<Bankdeduction> bans = sdao.AllBank(ban);
		for(int i=0;i<bans.size();i++){
			bans.get(i).setChengNum(sdao.ChenggNum(bans.get(i)));
			bans.get(i).setShiNum(bans.get(i).getUserNum()-bans.get(i).getChengNum());
			bans.get(i).setDeduction_money(sdao.ChenggMoney(bans.get(i)));
			NumberFormat numberFormat = NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits(2);
			bans.get(i).setCdata(numberFormat.format(((float) bans.get(i).getChengNum() / (float) bans.get(i).getChengNum()) * 100));
		}
		map.put("Bankdeduction", bans);
		map.put("pageutil", pages);
		return map;
	}

	@Override
	public Map<String, Object> AllBankdetail(Bankdeduction bank) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		return null;
	}

	@Override
	public Map<String, Object> AllDetails(Bankdeduction bank) {
		// TODO Auto-generated method stub
		return null;
	}
		
		

	
	
	
	
	
}
