package com.zhita.controller.bankcard;

import java.util.Map;

import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.BaseParameter;
import com.zhita.chanpayutil.ChanPayUtil;


/**
 * 
 * @ClassName: T10000Demo
 * @Description: 同步单笔代付接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class T10000Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "T10000"); // 交易码
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo()); // 商户网站唯一订单号
		map.put("CorpAcctNo", "1223332343");  //可空
		map.put("BusinessType", "0"); // 业务类型：0对私 1对公
		map.put("BankCommonName", "农业银行"); // 通用银行名称
		map.put("BankCode", "");//对公必填
		map.put("AccountType", "00"); // 账户类型
		map.put("AcctNo", ChanPayUtil.encrypt("6214835901884138", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号(此处需要用真实的账号信息)
		map.put("AcctName", ChanPayUtil.encrypt("东新雨", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
		map.put("TransAmt", "0.01");
		
		//************** 以下信息可空  *******************
//		map.put("Province", "甘肃省"); // 省份信息
//		map.put("City", "兰州市"); // 城市信息
//		map.put("BranchBankName", "中国建设银行股份有限公司兰州新港城支行"); // 对手行行名
//		map.put("BranchBankCode", "105821005604");
//		map.put("DrctBankCode", "105821005604");
//		map.put("Currency", "CNY");
//		map.put("LiceneceType", "01");
//		map.put("LiceneceNo", ChanPayUtil.encrypt("622225199209190017", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//		map.put("Phone", ChanPayUtil.encrypt("17001090000", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//		map.put("AcctExp", "exp");
//		map.put("AcctCvv2", ChanPayUtil.encrypt("cvv", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//		map.put("CorpCheckNo", "201703061413");
//		map.put("Summary", "");
		
		map.put("CorpPushUrl", "http://172.20.11.16");		
		map.put("PostScript", "用途");

		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}

//	@org.junit.Test
//	public void testSend() {
//		T10000Demo demo = new T10000Demo();
//		demo.send();
//	}	
	
	
	
	public static void main(String[] args) {
		T10000Demo demo = new T10000Demo();
		demo.send();
	}
}
