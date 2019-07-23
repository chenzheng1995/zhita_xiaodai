package com.zhita.controller.ChanpayQuickPay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhita.util.BaseConstant;
import com.zhita.util.BaseParameter;
import com.zhita.util.ChanPayUtil1;



@Controller
@RequestMapping("sendPara")
public class ChanpaySend extends BaseParameter{
	
	
	@ResponseBody
	@RequestMapping("send")
	public Map<String, Object> SendMoney(String orderNumber,String AcctNo,String AcctName,String TransAmt){
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "T10000"); // 交易码
		map.put("OutTradeNo", ChanPayUtil1.generateOutTradeNo()); // 商户网站唯一订单号
		map.put("CorpAcctNo", "1223332343");  //可空
		map.put("BusinessType", "1"); // 业务类型：0对私 1对公
		map.put("BankCommonName", "招商银行"); // 通用银行名称
		map.put("BankCode", "308584000013");//对公必填
		map.put("AccountType", "00"); // 账户类型
		map.put("AcctNo", ChanPayUtil1.encrypt(AcctNo, BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号(此处需要用真实的账号信息)
		map.put("AcctName", ChanPayUtil1.encrypt(AcctName, BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
		map.put("TransAmt", TransAmt);
		map.put("CorpPushUrl", "http://192.168.0.169");		
		map.put("PostScript", "用途");
		ChanPayUtil1.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
		return map1;
	}
}
