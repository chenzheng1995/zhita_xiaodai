package com.zhita.controller.ChanpayQuickPay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.BaseParameter;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.model.manage.ReturnChanpay;
import com.zhita.model.manage.ShortReturn;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;




@Controller
@RequestMapping("sendPara")
public class ChanpaySend extends BaseParameter{
	
	@Autowired
	private Chanpayservice chanser;
	
	
	/**
	 * 放款
	 * @param orderNumber
	 * @param AcctNo
	 * @param AcctName
	 * @param TransAmt
	 * @return
	 */
	@ResponseBody
	@RequestMapping("send")
	public Map<String, Object> SendMoney(String orderNumber,String AcctNo,String AcctName,String TransAmt){
		Map<String, Object> map1 = new HashMap<String, Object>();
		if(orderNumber != null && AcctNo != null && AcctName != null && TransAmt != null){
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "T10000"); // 交易码
		map.put("OutTradeNo", orderNumber); // 商户网站唯一订单号
		map.put("CorpAcctNo", "1223332343");  //可空
		map.put("BusinessType", "0"); // 业务类型：0对私 1对公
		map.put("BankCommonName", "招商银行"); // 通用银行名称
		map.put("BankCode", "");//对公必填
		map.put("AccountType", "00"); // 账户类型
		map.put("AcctNo", ChanPayUtil.encrypt(AcctNo, BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号(此处需要用真实的账号信息)
		map.put("AcctName", ChanPayUtil.encrypt(AcctName, BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
		map.put("TransAmt", TransAmt);
		map.put("CorpPushUrl", "http://172.20.11.16");		
		map.put("PostScript", "转账");
		ReturnChanpay rea = ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
		map1.put("ReturnChanpay", rea);
		map1.put("code", 200);
		}else{
			map1.put("ReturnChanpay", "orderNumber,AcctNo,AcctName,TransAmt 不能为空");
			map1.put("code", 0);
		}
		
		return map1;
		
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping("Mingxi")
	public Map<String, Object> SendMing(String OriOutTradeNo,String BeginDate,String EndDate) {
		Map<String, String> map = this.requestBaseParameter();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("TransCode", "C10001");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());//官网唯一订单号
		map.put("OriOutTradeNo", OriOutTradeNo);//原交易订单号
		map.put("BeginIdx", "0");//查询起始位置，从0开始
		map.put("QueryNum", "1");//查询记录条数
		map.put("Status", "");//交易状态   1-成功   2-失败   3-处理中
		map.put("TransFlag", "2");//交易类型 1-代收；2-代付
		map.put("BeginDate", BeginDate);//起始时间
		map.put("EndDate", EndDate);//结束时间
		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
		
		try {
			Map<String, String> sPara = ChanPayUtil.buildRequestPara(map,
					"RSA", BaseConstant.MERCHANT_PRIVATE_KEY, BaseConstant.CHARSET);
			String resultString = ChanPayUtil.buildRequest(sPara, "RSA",
					BaseConstant.MERCHANT_PRIVATE_KEY, BaseConstant.CHARSET, BaseConstant.GATEWAY_URL);
			ShortReturn sreturn = JSON.parseObject(resultString,ShortReturn.class);
			map1.put("ShortReturn", sreturn);
			map1.put("code", 200);
			System.out.println(resultString);  //在调试对账文件，如果日志里面不希望显示对账文件注释掉即可
		} catch (Exception e) {
			System.out.println("发demo出现异常----"+e);
			e.printStackTrace();
		}
		return map1;
	}
	
	
	
	
	
	
	
	
	
}
