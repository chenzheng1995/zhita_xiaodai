package com.zhita.service.manage.newpayment.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	
	public String getMap(String key){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUCCESS", "代付成功");
		map.put("NOTPAY", "待审核");
		map.put("WAITPC", "已审核");
		map.put("TOPC", "已提交");
		map.put("PAYING", "付款中");
		map.put("PAYERROR", "代付失败");
		String values = (String) map.get(key);
		return values;
	}
	
	
	
	public String getCodeMap(String key){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SUCCESS", "接口调用成功");
		map.put("NOAUTH", "商户无此接口权限");
		map.put("NOTENOUGH", "余额不足,商户手续费余额不足");
		map.put("SYSTEMERROR", "系统错误系统超时，请用相同参数重新调用");
		map.put("MCH_NOT_EXIST", "商户不存在");
		map.put("LACK_PARAMS", "缺少必要的请求参数");
		map.put("SIGNERROR", "签名错误");
		map.put("TRADE_NOT_EXIST", "交易不存在");
		map.put("MCH_BOUND_MEMBER", "核对商户用户绑定信息");
		map.put("NOT_ORDER", "请核对订单号");
		String values = (String) map.get(key);
		return values;
	}
	
	
	
	
	public String getBankMap(String key){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("工商银行", "ICBC");
		map.put("农业银行", "ABC");
		map.put("中国银行", "BOC");
		map.put("建设银行", "CCB");
		map.put("中信银行", "CITIC");
		map.put("交通银行", "COMM");
		map.put("光大银行", "CEB");
		map.put("华夏银行", "HXBANK");
		map.put("民生银行", "CMBC");
		map.put("广发银行", "GDB");
		map.put("兴业银行", "CIB");
		map.put("浦发银行", "SPDB");
		map.put("平安银行", "SPABANK");
		map.put("上海银行", "SHBANK");
		String values = (String) map.get(key);
		return values;
	}

}
