package com.zhita.controller.ChanpayQuickPay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.BaseParameter;
import com.zhita.chanpayutil.ChanPayUtil;


@Controller
@RequestMapping("Chanpay")
public class chanpaycollection extends BaseParameter{
	
	
		/**
		 * BankCommonName   银行卡
		 * AcctNo   卡号。
		 * AcctName  姓名
		 * TransAmt  金额
		 * LiceneceNo  身份证号
		 * Phone  手机号
		 * sys_userId   操作人
		 * deductionproportion  扣款比例
		 * orderId   订单ID
		 * deductionstatus  扣款状态
		 * deduction_money  扣款金额
		 */
		@ResponseBody
		@RequestMapping("AllSend")
		public Map<String, Object> send() {
			Map<String, Object> map1 = new HashMap<String, Object>();
			Map<String, String> map = this.requestBaseParameter();
			map.put("TransCode", "T20000"); // 交易码
			map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo()); // 商户网站唯一订单号
			map.put("CorpAcctNo", ""); // 企业账号（可空）
			map.put("BusinessType", "0"); // 业务类型
			map.put("ProtocolNo", ""); // 协议号（可空）        
			map.put("BankCommonName", "农业银行"); // 通用银行名称
			map.put("AccountType", "00"); // 账户类型
			
			//**************  银行四要素信息需要用真实数据  *******************
			map.put("AcctNo", ChanPayUtil.encrypt("6214835901884138",
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号
			map.put("AcctName", ChanPayUtil.encrypt("东新雨",
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
//			map.put("Province", "甘肃省"); // 省份信息
//			map.put("City", "兰州市"); // 城市信息
//			map.put("BranchBankName", "中国建设银行股份有限公司兰州新港城支行"); // 对手行行名
//			map.put("BranchBankCode", "105821005604");
//			map.put("DrctBankCode", "105821005604");
//			map.put("Currency", "CNY");
			map.put("TransAmt", "0.01");
			map.put("LiceneceType", "01");
			map.put("LiceneceNo", ChanPayUtil.encrypt("420621199905157170",
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
			map.put("Phone", ChanPayUtil.encrypt("13487139655",
					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//			map.put("AcctExp", "exp");
//			map.put("AcctCvv2", ChanPayUtil.encrypt("cvv",
//					BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//			map.put("CorpCheckNo", "201703061413");
//			map.put("Summary", "");
//			map.put("RoyaltyParameters", "");//分账扩展字段,用来实现代扣分账结算的字段，json格式,[{'userId':'200001220035','PID':'0','accountType':'201','amount':'0.01'},{'userId':'cjzfxsm3@yonyou.com','PID':'2','accountType':'201','amount':'0.01'}]
			map.put("CorpPushUrl", "http://172.20.11.16");
			map.put("PostScript", "用途");

			ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
					BaseConstant.MERCHANT_PRIVATE_KEY);
			return map1;
		}
	
	
}
